package grug.grugmodloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.server.ServerLifecycleHooks;

public class Grug {
    private native void loadGlobalLibraries();

    private native void initGrugAdapter();

    private native boolean grugInit(String modApiJsonPath, String modsDirPath, String dllDirPath, long onFnTimeLimitMs);

    private native void setOnFnsToFastMode();

    private native boolean grugRegenerateModifiedMods();

    private native boolean errorHasChanged();
    private native boolean loadingErrorInGrugFile();
    private native String errorMsg();
    private native String errorPath();
    private native int errorGrugCLineNumber();

    private native int getGrugReloadsSize();
    private native void fillReloadData(ReloadData reloadData, int i);

    private native void gameFunctionErrorHappened(String message);

    private native void fillRootGrugDir(GrugDir root);
    private native void fillGrugDir(GrugDir dir, long parentDirAddress, int dirIndex);
    private native void fillGrugFile(GrugFile file, long parentDirAddress, int fileIndex);

    public native void callInitGlobals(long initGlobalsFn, byte[] globals, long id);

    public native void getEntityFile(String entity, GrugFile file);

    public native boolean block_entity_has_on_spawn(long onFns);
    public native void block_entity_on_spawn(long onFns, byte[] globals);

    public native boolean block_entity_has_on_tick(long onFns);
    public native void block_entity_on_tick(long onFns, byte[] globals);

    public native boolean block_entity_has_on_neighbor_changed(long onFns);
    public native void block_entity_on_neighbor_changed(long onFns, byte[] globals, long state, long level, long pos, long blockIn, long fromPos, boolean isMoving);

    private ReloadData reloadData = new ReloadData();

    // TODO: This does not recycle indices of despawned entities,
    // TODO: which means this will eventually wrap around back to 0
    private static Map<GrugEntityType, Integer> nextEntityIndices = new HashMap<>();
    private static Map<Long, Object> entityData = new HashMap<>();

    public static Map<String, List<GrugEntity>> grugEntitiesMap = new HashMap<String, List<GrugEntity>>();

    // This is deliberately not initialized.
    // This variable gets assigned an entity's HashSet of child IDs before on_ functions are called.
    // This allows on_ functions to add copies of entities to global data structures, like HashSets.
    public static Set<Long> globalEntities;

    // This is deliberately not initialized.
    // This variable gets assigned an entity's HashSet of child IDs before init_globals() is called,
    // and it gets assigned a new HashSet<Long> of on_ fn entities, before an on_ function is called.
    public static Set<Long> fnEntities;

    private static HashMap<Long, HashMap<Object, Long>> allHashMapObjects = new HashMap<>();
    private static HashMap<Long, HashMap<Object, Long>> allHashSetObjects = new HashMap<>();

    // These are read by grug's game tests.
    public static String gameFunctionError = null;
    public static String sentMessage = null;

    public Grug() {
        try {
            extractAndLoadNativeLibrary("libglobal_library_loader.so");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        loadGlobalLibraries();

        try {
            extractAndLoadNativeLibrary("libadapter.so");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        initGrugAdapter();

        if (grugInit("../mod_api.json", "../mods", "mod_dlls", 10000)) {
            throw new RuntimeException("grugInit() error: " + errorMsg() + " (detected by grug.c:" + errorGrugCLineNumber() + ")");
        }

        // We need to regenerate the mods before the first modded entities are instantiated,
        // since they call getEntityFile() in their constructors
        if (grugRegenerateModifiedMods()) {
            if (loadingErrorInGrugFile()) {
                throw new RuntimeException("grug loading error: " + errorMsg() + "\nDetected in " + errorPath() + " by grug.c:" + errorGrugCLineNumber());
            } else {
                throw new RuntimeException("grug loading error: " + errorMsg() + "\nDetected by grug.c:" + errorGrugCLineNumber());
            }
        }

        for (GrugEntityType entityType : GrugEntityType.values()) {
            nextEntityIndices.put(entityType, 0);
        }
    }

    // This function was written for the tests in GameTests.java.
    // The reason that GameTests.java doesn't just recreate the Grug class for every test,
    // where all of the static variables in the Grug class could be made non-static,
    // is because that would cause the constructor to call grugInit() a second time, which grug.c does not allow.
    public static void resetVariables() {
        for (GrugEntityType entityType : nextEntityIndices.keySet()) {
            nextEntityIndices.put(entityType, 0);
        }
        entityData.clear();
        grugEntitiesMap.clear();
        fnEntities = null;
        globalEntities = null;
        allHashMapObjects.clear();
        allHashSetObjects.clear();
        gameFunctionError = null;
        sentMessage = null;
    }

    private void extractAndLoadNativeLibrary(String libraryName) throws IOException {
        // Get the native library file from the JAR's resources
        String libraryPathInJar = "/natives/" + libraryName;
        InputStream libraryInputStream = getClass().getResourceAsStream(libraryPathInJar);

        if (libraryInputStream == null) {
            throw new IOException("Native library not found in the JAR: " + libraryName);
        }

        // Create a temporary file
        String baseName = libraryName.substring(0, libraryName.lastIndexOf('.'));
        String extension = libraryName.substring(libraryName.lastIndexOf('.'));
        Path tempLibraryPath = Files.createTempFile(baseName, extension);

        // Extract the library to the temporary file
        try (OutputStream out = new FileOutputStream(tempLibraryPath.toFile())) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = libraryInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        // Make sure the extracted file is executable (for Linux/Mac)
        tempLibraryPath.toFile().setExecutable(true);

        // Load the library from the temporary file
        System.load(tempLibraryPath.toAbsolutePath().toString());
    }

    public void runtimeErrorHandler(String reason, int type, String onFnName, String onFnPath) {
        sendMessageToEveryone(
            Component.literal("grug runtime error in ").withColor(ChatFormatting.RED.getColor())
            .append(Component.literal(onFnName + "()").withColor(0xc3e88d))
            .append(Component.literal(": ").withColor(ChatFormatting.RED.getColor()))
            .append(Component.literal(reason).withColor(ChatFormatting.WHITE.getColor()))
            .append("\nDetected in ")
            .append(Component.literal(onFnPath).withColor(ChatFormatting.DARK_AQUA.getColor()))
        );
    }

    public void onTick() {
        if (grugRegenerateModifiedMods()) {
            if (loadingErrorInGrugFile()) {
                sendMessageToEveryone(
                    Component.literal("grug loading error: ").withColor(ChatFormatting.RED.getColor())
                    .append(Component.literal(errorMsg()).withColor(ChatFormatting.WHITE.getColor()))
                    .append(Component.literal("\nDetected in ").withColor(ChatFormatting.RED.getColor()))
                    .append(Component.literal(errorPath()).withColor(ChatFormatting.DARK_AQUA.getColor()))
                    .append(Component.literal(" by "))
                    .append(Component.literal("grug.c:" + errorGrugCLineNumber()).withColor(ChatFormatting.DARK_AQUA.getColor()))
                );
            } else {
                sendMessageToEveryone(
                    Component.literal("grug loading error: ").withColor(ChatFormatting.RED.getColor())
                    .append(Component.literal(errorMsg()).withColor(ChatFormatting.WHITE.getColor()))
                    .append(Component.literal("\nDetected by "))
                    .append(Component.literal("grug.c:" + errorGrugCLineNumber()).withColor(ChatFormatting.DARK_AQUA.getColor()))
                );
            }

            return;
        }

        reloadModifiedEntities();

        // TODO: Implement
        // reloadModifiedResources();

        // TODO: None of these seem to log the mspf
        // TODO: Display the mspf, where if it's colored green or red, depending on whether it's above 50 mspf
        // System.out.println("fps: " + Minecraft.getInstance().getFps());
        // System.out.println("frame time: " + Minecraft.getInstance().getFrameTime());
        // System.out.println("frame time ns: " + Minecraft.getInstance().getFrameTimeNs());
        // System.out.println("delta frame time: " + Minecraft.getInstance().getDeltaFrameTime());
    }

    public void reloadModifiedEntities() {
        int reloadsSize = getGrugReloadsSize();

        for (int reloadIndex = 0; reloadIndex < reloadsSize; reloadIndex++) {
            fillReloadData(reloadData, reloadIndex);

            GrugFile file = reloadData.file;

            List<GrugEntity> grugEntities = grugEntitiesMap.get(file.entity);
            if (grugEntities == null) {
                continue;
            }

            for (GrugEntity grugEntity : grugEntities) {
                removeEntities(grugEntity.childEntities);
                grugEntity.childEntities.clear();

                grugEntity.globals = new byte[file.globalsSize];

                globalEntities = grugEntity.childEntities;
                fnEntities = grugEntity.childEntities;
                callInitGlobals(file.initGlobalsFn, grugEntity.globals, grugEntity.id);

                grugEntity.onFns = file.onFns;

                if (!block_entity_has_on_spawn(grugEntity.onFns)) {
                    continue;
                }

                globalEntities = grugEntity.childEntities;
                fnEntities = new HashSet<>();

                block_entity_on_spawn(grugEntity.onFns, grugEntity.globals);

                removeEntities(fnEntities);
            }
        }
    }

    public static void sendMessageToEveryone(Component message) {
        GrugModLoader.logger.debug("sendMessageToEveryone(message={})", message);

        // Used by game tests.
        sentMessage = message.getString();

        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if (server == null) {
            return;
        }

        for (ServerLevel level : server.getAllLevels()) {
            for (ServerPlayer player : level.players()) {
                player.sendSystemMessage(message);
            }
        }
    }

    public static void gameFunctionErrorHappened(String gameFunctionName, String message) {
        gameFunctionError = gameFunctionName + "(): " + message;

        GrugModLoader.grug.gameFunctionErrorHappened(gameFunctionError);
    }

    public static long addEntity(GrugEntityType entityType, Object entityInstance) {
        GrugModLoader.logger.debug("addEntity(entityType={}, entityInstance={})", entityType, entityInstance);
        assert entityInstance != null;

        int entityIndex = nextEntityIndices.get(entityType);

        nextEntityIndices.put(entityType, entityIndex + 1);

        long id = getEntityID(entityType, entityIndex);

        entityData.put(id, entityInstance);

        GrugModLoader.logger.debug("Returning {}", id);
        return id;
    }

    public static void removeEntity(long id) {
        GrugModLoader.logger.debug("removeEntity(id={})", id);
        entityData.remove(id);
    }

    public static void removeEntities(Iterable<Long> entities) {
        GrugModLoader.logger.debug("removeEntities(entities={})", entities);
        for (long entity : entities) {
            removeEntity(entity);
        }
    }

    private static long getEntityID(GrugEntityType entityType, int entityIndex) {
        GrugModLoader.logger.debug("getEntityID(entityType={}, entityIndex={})", entityType, entityIndex);
        return (long)entityType.ordinal() << 32 | entityIndex;
    }

    public static GrugEntityType getEntityType(long id) {
        GrugModLoader.logger.debug("getEntityType(id={})", id);
        return GrugEntityType.get((int)(id >> 32));
    }

    // private int getEntityIndex(long id) {
    //     GrugModLoader.logger.debug("getEntityIndex(id={})", id);
    //     return (int)(id & 0xffffffff);
    // }

    // TODO: I'm sure this can be done cleaner
    public static boolean isEntityTypeInstanceOf(GrugEntityType derived, GrugEntityType base) {
        GrugModLoader.logger.debug("isEntityTypeInstanceOf(derived={}, base={})", derived, base);
        if (derived == base) {
            return true;
        }
        if (derived == GrugEntityType.ItemEntity && base == GrugEntityType.Entity) {
            return true;
        }
        return false;
    }

    private static void assertEntityType(long id, GrugEntityType expectedEntityType) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("assertEntityType(id={}, expectedEntityType={})", id, expectedEntityType);
        GrugEntityType entityType = getEntityType(id);
        if (!isEntityTypeInstanceOf(entityType, expectedEntityType)) {
            throw new AssertEntityTypeException(entityType, expectedEntityType);
        }
    }

    public static void newHashMapObjects(long hashMapId) {
        GrugModLoader.logger.debug("newHashMapObjects(hashMapId={})", hashMapId);
        allHashMapObjects.put(hashMapId, new HashMap<>());
    }

    public static void newHashSetObjects(long hashSetId) {
        GrugModLoader.logger.debug("newHashSetObjects(hashSetId={})", hashSetId);
        allHashSetObjects.put(hashSetId, new HashMap<>());
    }

    public static HashMap<Object, Long> getHashMapObjects(long hashMapId) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getHashMapObjects(hashMapId={})", hashMapId);
        assertEntityType(hashMapId, GrugEntityType.HashMap);
        HashMap<Object, Long> objects = allHashMapObjects.get(hashMapId);
        assert objects != null;
        return objects;
    }

    public static HashMap<Object, Long> getHashSetObjects(long hashSetId) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getHashSetObjects(hashSetId={})", hashSetId);
        HashMap<Object, Long> objects = allHashSetObjects.get(hashSetId);
        if (objects == null) {
            assertEntityType(hashSetId, GrugEntityType.HashSet);
            assert false; // Unreachable.
        }
        return objects;
    }

    public Block getBlock(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getBlock(id={})", id);
        assertEntityType(id, GrugEntityType.Block);
        Block block = (Block)entityData.get(id);
        assert block != null;
        return block;
    }

    public BlockEntity getBlockEntity(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getBlockEntity(id={})", id);
        assertEntityType(id, GrugEntityType.BlockEntity);
        BlockEntity blockEntity = (BlockEntity)entityData.get(id);
        assert blockEntity != null;
        return blockEntity;
    }

    public BlockPos getBlockPos(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getBlockPos(id={})", id);
        assertEntityType(id, GrugEntityType.BlockPos);
        BlockPos blockPos = (BlockPos)entityData.get(id);
        assert blockPos != null;
        return blockPos;
    }

    public BlockState getBlockState(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getBlockState(id={})", id);
        assertEntityType(id, GrugEntityType.BlockState);
        BlockState blockState = (BlockState)entityData.get(id);
        assert blockState != null;
        return blockState;
    }

    public Integer getBoxedI32(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getBoxedI32(id={})", id);
        assertEntityType(id, GrugEntityType.BoxedI32);
        Integer boxedI32 = (Integer)entityData.get(id);
        assert boxedI32 != null;
        return boxedI32;
    }

    public Entity getEntity(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getEntity(id={})", id);
        assertEntityType(id, GrugEntityType.Entity);
        Entity entity = (Entity)entityData.get(id);
        assert entity != null;
        return entity;
    }

    public GrugEntry getEntry(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getEntry(id={})", id);
        assertEntityType(id, GrugEntityType.Entry);
        GrugEntry iteration = (GrugEntry)entityData.get(id);
        assert iteration != null;
        return iteration;
    }

    @SuppressWarnings("unchecked")
    public HashMap<Long, Long> getHashMap(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getHashMap(id={})", id);
        assertEntityType(id, GrugEntityType.HashMap);
        HashMap<Long, Long> hashMap = (HashMap<Long, Long>)entityData.get(id);
        assert hashMap != null;
        return hashMap;
    }

    @SuppressWarnings("unchecked")
    public HashSet<Long> getHashSet(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getHashSet(id={})", id);
        assertEntityType(id, GrugEntityType.HashSet);
        HashSet<Long> hashSet = (HashSet<Long>)entityData.get(id);
        assert hashSet != null;
        return hashSet;
    }

    public Item getItem(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getItem(id={})", id);
        assertEntityType(id, GrugEntityType.Item);
        Item item = (Item)entityData.get(id);
        assert item != null;
        return item;
    }

    public ItemEntity getItemEntity(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getItemEntity(id={})", id);
        assertEntityType(id, GrugEntityType.ItemEntity);
        ItemEntity itemEntity = (ItemEntity)entityData.get(id);
        assert itemEntity != null;
        return itemEntity;
    }

    public ItemStack getItemStack(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getItemStack(id={})", id);
        assertEntityType(id, GrugEntityType.ItemStack);
        ItemStack itemStack = (ItemStack)entityData.get(id);
        assert itemStack != null;
        return itemStack;
    }

    public GrugIterator getIterator(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getIterator(id={})", id);
        assertEntityType(id, GrugEntityType.Iterator);
        GrugIterator iterator = (GrugIterator)entityData.get(id);
        assert iterator != null;
        return iterator;
    }

    public Level getLevel(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getLevel(id={})", id);
        assertEntityType(id, GrugEntityType.Level);
        Level level = (Level)entityData.get(id);
        assert level != null;
        return level;
    }

    public Object getObject(long id) {
        GrugModLoader.logger.debug("getObject(id={})", id);
        Object object = entityData.get(id);
        assert object != null;
        return object;
    }

    public ResourceLocation getResourceLocation(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getResourceLocation(id={})", id);
        assertEntityType(id, GrugEntityType.ResourceLocation);
        ResourceLocation resourceLocation = (ResourceLocation)entityData.get(id);
        assert resourceLocation != null;
        return resourceLocation;
    }

    public Vec3 getVec3(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getVec3(id={})", id);
        assertEntityType(id, GrugEntityType.Vec3);
        Vec3 vec3 = (Vec3)entityData.get(id);
        assert vec3 != null;
        return vec3;
    }
}

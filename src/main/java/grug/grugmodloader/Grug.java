package grug.grugmodloader;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
    public static WeakGrugValueMap entityData = new WeakGrugValueMap();

    public static Map<String, List<GrugEntity>> grugEntitiesMap = new HashMap<String, List<GrugEntity>>();

    // This is deliberately not initialized.
    // This variable gets assigned an entity's ArrayList of child GrugObjects before init_globals() is called,
    // and it gets assigned a new ArrayList<GrugObject> of on_ fn entities, before an on_ function is called.
    public static List<GrugObject> fnEntities;

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

    // This function was written for the game tests in the gametests directory.
    // The reason that the game tests don't just recreate the Grug class for every test,
    // where all of the static variables in the Grug class could be made non-static,
    // is because that would cause the constructor to call grugInit() a second time, which grug.c does not allow.
    public static void resetVariables() {
        for (GrugEntityType entityType : nextEntityIndices.keySet()) {
            nextEntityIndices.put(entityType, 0);
        }
        entityData.clear();
        grugEntitiesMap.clear();
        fnEntities = null;
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
                fnEntities = grugEntity.childEntities;

                // Every GrugEntity's own GrugObject is always at index 0 of its childEntities.
                GrugObject self = fnEntities.get(0);
                // Clear all other GrugObjects from childEntities.
                fnEntities.clear();
                // Put self back.
                fnEntities.add(self);

                grugEntity.globals = new byte[file.globalsSize];

                callInitGlobals(file.initGlobalsFn, grugEntity.globals, grugEntity.id);

                grugEntity.onFns = file.onFns;

                if (!block_entity_has_on_spawn(grugEntity.onFns)) {
                    continue;
                }

                fnEntities = new ArrayList<>();

                block_entity_on_spawn(grugEntity.onFns, grugEntity.globals);
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

    public static long addEntity(GrugEntityType type, Object object) {
        GrugModLoader.logger.debug("addEntity(type={}, object={})", type, object);
        assert object != null;

        GrugObject grugObject = new GrugObject(type, object);

        int entityIndex = nextEntityIndices.get(grugObject.type);

        nextEntityIndices.put(grugObject.type, entityIndex + 1);

        long id = getEntityID(grugObject.type, entityIndex);

        fnEntities.add(grugObject);

        entityData.put(id, grugObject);

        GrugModLoader.logger.debug("Returning {}", id);
        return id;
    }

    private static long getEntityID(GrugEntityType entityType, int entityIndex) {
        GrugModLoader.logger.debug("getEntityID(entityType={}, entityIndex={})", entityType, entityIndex);
        return (long)entityType.ordinal() << 32 | entityIndex;
    }

    public static GrugEntityType getEntityType(long id) {
        GrugModLoader.logger.debug("getEntityType(id={})", id);
        return GrugEntityType.get((int)(id >> 32));
    }

    private static int getEntityIndex(long id) {
        GrugModLoader.logger.debug("getEntityIndex(id={})", id);
        return (int)(id & 0xffffffff);
    }

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

    public static Block getBlock(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getBlock(id={})", id);
        assertEntityType(id, GrugEntityType.Block);
        Block block = (Block)entityData.get(id).object;
        assert block != null;
        return block;
    }

    public static BlockEntity getBlockEntity(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getBlockEntity(id={})", id);
        assertEntityType(id, GrugEntityType.BlockEntity);
        BlockEntity blockEntity = (BlockEntity)entityData.get(id).object;
        assert blockEntity != null;
        return blockEntity;
    }

    public static BlockPos getBlockPos(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getBlockPos(id={})", id);
        assertEntityType(id, GrugEntityType.BlockPos);
        BlockPos blockPos = (BlockPos)entityData.get(id).object;
        assert blockPos != null;
        return blockPos;
    }

    public static BlockState getBlockState(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getBlockState(id={})", id);
        assertEntityType(id, GrugEntityType.BlockState);
        BlockState blockState = (BlockState)entityData.get(id).object;
        assert blockState != null;
        return blockState;
    }

    public static Integer getBoxedI32(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getBoxedI32(id={})", id);
        assertEntityType(id, GrugEntityType.BoxedI32);
        Integer boxedI32 = (Integer)entityData.get(id).object;
        assert boxedI32 != null;
        return boxedI32;
    }

    public static Entity getEntity(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getEntity(id={})", id);
        assertEntityType(id, GrugEntityType.Entity);
        Entity entity = (Entity)entityData.get(id).object;
        assert entity != null;
        return entity;
    }

    public static Entry<GrugObject, GrugObject> getEntry(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getEntry(id={})", id);
        assertEntityType(id, GrugEntityType.Entry);
        @SuppressWarnings("unchecked")
        Entry<GrugObject, GrugObject> iteration = (Entry<GrugObject, GrugObject>)entityData.get(id).object;
        assert iteration != null;
        return iteration;
    }

    public static GrugObject getGrugObject(long id) {
        GrugModLoader.logger.debug("getObject(id={})", id);
        GrugObject grugObject = entityData.get(id);
        assert grugObject != null;
        return grugObject;
    }

    public static HashMap<GrugObject, GrugObject> getHashMap(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getHashMap(id={})", id);
        assertEntityType(id, GrugEntityType.HashMap);
        @SuppressWarnings("unchecked")
        HashMap<GrugObject, GrugObject> hashMap = (HashMap<GrugObject, GrugObject>)entityData.get(id).object;
        assert hashMap != null;
        return hashMap;
    }

    public static HashSet<GrugObject> getHashSet(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getHashSet(id={})", id);
        assertEntityType(id, GrugEntityType.HashSet);
        @SuppressWarnings("unchecked")
        HashSet<GrugObject> hashSet = (HashSet<GrugObject>)entityData.get(id).object;
        assert hashSet != null;
        return hashSet;
    }

    public static Item getItem(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getItem(id={})", id);
        assertEntityType(id, GrugEntityType.Item);
        Item item = (Item)entityData.get(id).object;
        assert item != null;
        return item;
    }

    public static ItemEntity getItemEntity(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getItemEntity(id={})", id);
        assertEntityType(id, GrugEntityType.ItemEntity);
        ItemEntity itemEntity = (ItemEntity)entityData.get(id).object;
        assert itemEntity != null;
        return itemEntity;
    }

    public static ItemStack getItemStack(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getItemStack(id={})", id);
        assertEntityType(id, GrugEntityType.ItemStack);
        ItemStack itemStack = (ItemStack)entityData.get(id).object;
        assert itemStack != null;
        return itemStack;
    }

    public static GrugIterator getIterator(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getIterator(id={})", id);
        assertEntityType(id, GrugEntityType.Iterator);
        GrugIterator iterator = (GrugIterator)entityData.get(id).object;
        assert iterator != null;
        return iterator;
    }

    public static Level getLevel(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getLevel(id={})", id);
        assertEntityType(id, GrugEntityType.Level);
        Level level = (Level)entityData.get(id).object;
        assert level != null;
        return level;
    }

    public static ResourceLocation getResourceLocation(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getResourceLocation(id={})", id);
        assertEntityType(id, GrugEntityType.ResourceLocation);
        ResourceLocation resourceLocation = (ResourceLocation)entityData.get(id).object;
        assert resourceLocation != null;
        return resourceLocation;
    }

    public static Vec3 getVec3(long id) throws AssertEntityTypeException {
        GrugModLoader.logger.debug("getVec3(id={})", id);
        assertEntityType(id, GrugEntityType.Vec3);
        Vec3 vec3 = (Vec3)entityData.get(id).object;
        assert vec3 != null;
        return vec3;
    }
}

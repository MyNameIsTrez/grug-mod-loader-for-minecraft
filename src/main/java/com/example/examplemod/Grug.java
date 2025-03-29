package com.example.examplemod;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.ServerLifecycleHooks;

public class Grug {
    private native void loadGlobalLibraries();

    private native void initGrugAdapter();

    private native boolean grugInit(String modApiJsonPath, String modsDirPath);

    private native boolean grugRegenerateModifiedMods();

    private native boolean errorHasChanged();
    private native boolean loadingErrorInGrugFile();
    private native String errorMsg();
    private native String errorPath();
    private native int errorGrugCLineNumber();

    private native int getGrugReloadsSize();
    private native void fillReloadData(ReloadData reloadData, int i);

    private native void fillRootGrugDir(GrugDir root);
    private native void fillGrugDir(GrugDir dir, long parentDirAddress, int dirIndex);
    private native void fillGrugFile(GrugFile file, long parentDirAddress, int fileIndex);

    public native void callInitGlobals(long initGlobalsFn, byte[] globals, long id);

    public native void getEntityFile(String entity, GrugFile file);

    // TODO: This does not recycle indices of despawned entities,
    // TODO: which means this will eventually wrap around back to 0
    private static Map<Integer, Integer> nextEntityIndices = new HashMap<>();
    private static Map<Long, Object> entityData = new HashMap<>();

    public native boolean blockEntity_has_onTick(long onFns);
    public native void blockEntity_onTick(long onFns, byte[] globals);

    private ReloadData reloadData = new ReloadData();

    public static Map<String, List<GrugEntity>> grugEntitiesMap = new HashMap<String, List<GrugEntity>>();

    // The List this constructs is never actually used.
    // This variable gets assigned an entity's list of child IDs before init_globals() is called,
    // and it gets assigned the below onFnEntities before an on_ fn is called.
    public static List<Long> fnEntities;

    // Cleared at the end of every on_ fn call.
    public static List<Long> onFnEntities;

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

        if (grugInit("../mod_api.json", "../mods")) {
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

        // TODO: Unhardcode
        int entityType = 7;
        nextEntityIndices.put(entityType, 0);

        // TODO: Unhardcode
        entityType = 42;
        nextEntityIndices.put(entityType, 0);
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

    public void runtimeErrorHandler(String reason, int type, String on_fn_name, String on_fn_path) {
        sendMessageToEveryone(
            Component.literal("grug runtime error in ").withColor(ChatFormatting.RED.getColor())
            .append(Component.literal(on_fn_name + "()").withColor(0xc3e88d))
            .append(Component.literal(": ").withColor(ChatFormatting.RED.getColor()))
            .append(Component.literal(reason).withColor(ChatFormatting.WHITE.getColor()))
            .append("\nDetected in ")
            .append(Component.literal(on_fn_path).withColor(ChatFormatting.DARK_AQUA.getColor()))
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
            // }

            return;
        }

        reloadModifiedEntities();

        // reloadModifiedResources();
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
                grugEntity.globals = new byte[file.globalsSize];
                callInitGlobals(file.initGlobalsFn, grugEntity.globals, grugEntity.id);

                grugEntity.onFns = file.onFns;
            }
        }
    }

    private static void sendMessageToEveryone(Component message) {
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

    public static long addEntity(int entityType, Object entityInstance) {
        System.out.println("nextEntityIndices: " + nextEntityIndices);
        System.out.println("entityData: " + entityData);
        System.out.println("entityInstance: " + entityInstance);

        int entityIndex = nextEntityIndices.get(entityType);

        nextEntityIndices.put(entityType, entityIndex + 1);

        long id = getEntityID(entityType, entityIndex);

        entityData.put(id, entityInstance);

        System.out.println("entityType: " + entityType);
        System.out.println("entityIndex: " + entityIndex);
        System.out.println("returned id: " + id);

        return id;
    }

    public static void removeEntity(long id) {
        System.out.println("id: " + id);
        // System.out.println("getEntityType(id): " + getEntityType(id));
        // System.out.println("getEntityIndex(id): " + getEntityIndex(id));

        entityData.remove(id);
    }

    public static void removeEntities(List<Long> entities) {
        for (long entity : entities) {
            removeEntity(entity);
        }
    }

    private static long getEntityID(int entityType, int entityIndex) {
        return (long)entityType << 32 | entityIndex;
    }

    private static int getEntityType(long id) {
        return (int)(id >> 32);
    }

    // TODO: Remove?
    // private int getEntityIndex(long id) {
    //     return (int)(id & 0xffffffff);
    // }

    private GrugBlockEntity getGrugBlockEntity(long id) {
        int entityType = getEntityType(id);

        // TODO: Print a nice error message, instead of crashing
        assert entityType == 7;

        return (GrugBlockEntity)entityData.get(id);
    }

    private BlockPos getBlockPos(long id) {
        int entityType = getEntityType(id);

        // TODO: Print a nice error message, instead of crashing
        assert entityType == 42;

        return (BlockPos)entityData.get(id);
    }

    // TODO: Move this method to GameFunctions.java, and remove the `gameFn_` prefix from the method's name
    private long gameFn_getWorldPositionOfBlockEntity(long blockEntityId) {
        return getGrugBlockEntity(blockEntityId).worldPositionId;
    }

    // TODO: Move this method to GameFunctions.java, and remove the `gameFn_` prefix from the method's name
    private long gameFn_getBlockPosAbove(long blockPosId) {
        // The .above() call allocates a new BlockPos
        BlockPos above = getBlockPos(blockPosId).above();

        int entityType = 42;
        long aboveId = Grug.addEntity(entityType, above);
        fnEntities.add(aboveId);

        return aboveId;
    }

    // TODO: Move this method to GameFunctions.java, and remove the `gameFn_` prefix from the method's name
    private int gameFn_getBlockPosX(long id) {
        return getBlockPos(id).getX();
    }

    // TODO: Move this method to GameFunctions.java, and remove the `gameFn_` prefix from the method's name
    private int gameFn_getBlockPosY(long id) {
        return getBlockPos(id).getY();
    }

    // TODO: Move this method to GameFunctions.java, and remove the `gameFn_` prefix from the method's name
    private int gameFn_getBlockPosZ(long id) {
        return getBlockPos(id).getZ();
    }

    // TODO: Move this method to GameFunctions.java, and remove the `gameFn_` prefix from the method's name
    private void gameFn_printId(long id) {
        sendMessageToEveryone(Component.literal(Long.toString(id)));
    }

    // TODO: Move this method to GameFunctions.java, and remove the `gameFn_` prefix from the method's name
    private void gameFn_printString(String str) {
        sendMessageToEveryone(Component.literal(str));
    }

    // TODO: Move this method to GameFunctions.java, and remove the `gameFn_` prefix from the method's name
    private void gameFn_printI32(int n) {
        sendMessageToEveryone(Component.literal(Integer.toString(n)));
        // System.out.println("n: " + n);
    }
}

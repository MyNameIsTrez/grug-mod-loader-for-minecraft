package com.example.examplemod;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import net.minecraft.ChatFormatting;
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

    private native void callDefineFn(long defineFn);
    private native void callInitGlobals(long initGlobalsFn, byte[] globals, int id);

    public native void getEntityFile(String entityName, GrugFile file);

    public native boolean blockEntity_has_onTick(long onFns);
    public native void blockEntity_onTick(long onFns, byte[] globals);

    private ReloadData reloadData = new ReloadData();

    // TODO: Get rid of this temporary stuff!
    public static GrugBlockEntity tempFooBlockEntity = new GrugBlockEntity();
    public static long tempFooBlockEntityDll;
    public static byte[] tempFooBlockEntityGlobals;
    static boolean tempFooBlockEntityInitialized = false;

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
            throw new RuntimeException("grugInit() error: " + errorMsg() + " (detected in grug.c:" + errorGrugCLineNumber() + ")");
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
            // if (errorHasChanged()) {
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

        if (!tempFooBlockEntityInitialized) {
            initTempFooBlockEntity();
            tempFooBlockEntityInitialized = true;
        }
    }

    public void reloadModifiedEntities() {
        int reloadsSize = getGrugReloadsSize();

        for (int reloadIndex = 0; reloadIndex < reloadsSize; reloadIndex++) {
            fillReloadData(reloadData, reloadIndex);

            GrugFile file = reloadData.file;

            if (reloadData.oldDll == tempFooBlockEntityDll) {
                tempFooBlockEntityDll = file.dll;

                tempFooBlockEntityGlobals = new byte[file.globalsSize];
                callInitGlobals(file.initGlobalsFn, tempFooBlockEntityGlobals, 0);

                tempFooBlockEntity.onFns = file.onFns;
            }
        }
    }

    // TODO: Get rid of this temporary function!
    public void initTempFooBlockEntity() {
        ArrayList<GrugFile> blockEntityFiles = new ArrayList<GrugFile>();

        GrugDir root = new GrugDir();
        fillRootGrugDir(root);

        getTypeFilesImpl(root, "block_entity", blockEntityFiles);

        // printBlockEntities(blockEntityFiles);

        GrugFile file = blockEntityFiles.get(0);

        callDefineFn(file.defineFn);
        tempFooBlockEntity = new GrugBlockEntity(EntityDefinitions.blockEntity);

        tempFooBlockEntity.onFns = file.onFns;

        tempFooBlockEntityDll = file.dll;

        tempFooBlockEntityGlobals = new byte[file.globalsSize];
        callInitGlobals(file.initGlobalsFn, tempFooBlockEntityGlobals, 0);
    }

    private void getTypeFilesImpl(GrugDir dir, String defineType, ArrayList<GrugFile> typeFiles) {
        for (int i = 0; i < dir.dirsSize; i++) {
            GrugDir subdir = new GrugDir();
            fillGrugDir(subdir, dir.address, i);

            getTypeFilesImpl(subdir, defineType, typeFiles);
        }

        for (int i = 0; i < dir.filesSize; i++) {
            GrugFile file = new GrugFile();
            fillGrugFile(file, dir.address, i);

            if (file.defineType.equals(defineType)) {
                typeFiles.add(file);
            }
        }
    }

    private void sendMessageToEveryone(Component message) {
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

    // private void printBlockEntities(ArrayList<GrugFile> blockEntityFiles) {
    //     for (int i = 0; i < blockEntityFiles.size(); i++) {
    //         GrugFile file = blockEntityFiles.get(i);

    //         callDefineFn(file.defineFn);

    //         BlockEntity blockEntity = EntityDefinitions.blockEntity;

    //         // TODO: Right now the blockEntity doesn't have any fields,
    //         // TODO: but print them here when it does
    //         // System.out.println((i + 1) + ". " + tool.name + ", costing " + tool.buyGoldValue + " gold");
    //     }

    //     System.out.println();
    // }

    // TODO: Move this method to GameFunctions.java, and remove the `gameFn_` prefix from the method's name
    private long gameFn_getWorldPosition(long blockEntityId) {
        // TODO: Figure out how to look up the world position of *any* block entity
        //
        // TODO: Return the address of the world position class instance, somehow:
        // TODO: maybe I need to add a special `long getAddr(void *);` fn to adapter.c
        return blockEntityId;
    }

    // TODO: Move this method to GameFunctions.java, and remove the `gameFn_` prefix from the method's name
    private void gameFn_printId(long id) {
        sendMessageToEveryone(Component.literal(Long.toString(id)));
    }

    // TODO: Move this method to GameFunctions.java, and remove the `gameFn_` prefix from the method's name
    private void gameFn_printString(String str) {
        sendMessageToEveryone(Component.literal(str));
    }
}

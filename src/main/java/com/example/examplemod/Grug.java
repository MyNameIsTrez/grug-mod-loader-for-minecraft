package com.example.examplemod;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

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
            e.printStackTrace();
            throw new RuntimeException("Failed to load native library.", e);
        }

        loadGlobalLibraries();

        try {
            extractAndLoadNativeLibrary("libadapter.so");
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to load native library.", e);
        }

        initGrugAdapter();

        if (grugInit("../mod_api.json", "../mods")) {
            throw new RuntimeException("grugInit() error: " + errorMsg() + " (detected in grug.c:" + errorGrugCLineNumber() + ")");
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
        System.out.println("Native library loaded from: " + tempLibraryPath);
    }

    public void runtimeErrorHandler(String reason, int type, String on_fn_name, String on_fn_path) {
        System.err.println("grug runtime error in " + on_fn_name + "(): " + reason + ", in " + on_fn_path);
    }

    public void onTick() {
        if (grugRegenerateModifiedMods()) {
            // if (errorHasChanged()) {
            if (loadingErrorInGrugFile()) {
                System.err.println("grug loading error: " + errorMsg() + ", in " + errorPath() + " (detected in grug.c:" + errorGrugCLineNumber() + ")");
            } else {
                System.err.println("grug loading error: " + errorMsg() + " (detected in grug.c:" + errorGrugCLineNumber() + ")");
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
        System.out.println("Initializing tempFooBlockEntity");

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
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if (server == null) {
            return;
        }

        Component message = Component.literal(Long.toString(id));

        for (ServerLevel level : server.getAllLevels()) {
            for (ServerPlayer player : level.players()) {
                player.sendSystemMessage(message);
            }
        }
    }

    // TODO: Move this method to GameFunctions.java, and remove the `gameFn_` prefix from the method's name
    private void gameFn_printString(String str) {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();

        if (server == null) {
            return;
        }

        Component message = Component.literal(str);

        for (ServerLevel level : server.getAllLevels()) {
            for (ServerPlayer player : level.players()) {
                player.sendSystemMessage(message);
            }
        }
    }
}

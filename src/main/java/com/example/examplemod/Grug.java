package com.example.examplemod;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

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

        if (grugInit("../mod_api.json", "mods")) {
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
        System.out.println("onTick()");

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

        System.out.println("No errors! :)");

        // reloadModifiedEntities();

        // reloadModifiedResources();

        // update();
    }
}

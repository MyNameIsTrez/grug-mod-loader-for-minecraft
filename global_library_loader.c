#include <jni.h>

#include <dlfcn.h>
#include <stdlib.h>

JNIEXPORT void JNICALL Java_game_Game_loadGlobalLibraries(JNIEnv *javaEnv, jobject javaObject) {
    (void)javaEnv;
    (void)javaObject;

    if (!dlopen("./grug/libgrug.so", RTLD_NOW | RTLD_GLOBAL)) {
        fprintf(stderr, "dlopen: %s\n", dlerror());
        exit(EXIT_FAILURE);
    }

    if (!dlopen("./libadapter.so", RTLD_NOW | RTLD_GLOBAL)) {
        fprintf(stderr, "dlopen: %s\n", dlerror());
        exit(EXIT_FAILURE);
    }
}

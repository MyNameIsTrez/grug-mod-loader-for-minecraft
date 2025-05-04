#include <jni.h>

#include <dlfcn.h>
#include <stdlib.h>

JNIEXPORT void JNICALL Java_grug_grugmodloader_Grug_loadGlobalLibraries(JNIEnv *javaEnv, jobject javaObject) {
    (void)javaEnv;
    (void)javaObject;

    if (!dlopen("../src/main/resources/natives/libgrug.so", RTLD_NOW | RTLD_GLOBAL)) {
        fprintf(stderr, "dlopen: %s\n", dlerror());
        exit(EXIT_FAILURE);
    }

    if (!dlopen("../src/main/resources/natives/libadapter.so", RTLD_NOW | RTLD_GLOBAL)) {
        fprintf(stderr, "dlopen: %s\n", dlerror());
        exit(EXIT_FAILURE);
    }
}

#include <jni.h>

#include "grug/grug.h"

#include <assert.h>
#include <stdbool.h>
#include <stdio.h>

typedef char* string;
typedef int32_t i32;
typedef uint64_t id;

struct block_entity_on_fns {
    void (*on_tick)(void *globals);
};

JNIEnv *global_env;
jobject global_obj;

jmethodID runtime_error_handler_id;

jobject block_definition_obj;

jobject block_entity_definition_obj;


void game_fn_define_block() {
}

void game_fn_define_block_entity() {
}

void runtime_error_handler(char *reason, enum grug_runtime_error_type type, char *on_fn_name, char *on_fn_path) {
    jstring java_reason = (*global_env)->NewStringUTF(global_env, reason);
    jint java_type = type;
    jstring java_on_fn_name = (*global_env)->NewStringUTF(global_env, on_fn_name);
    jstring java_on_fn_path = (*global_env)->NewStringUTF(global_env, on_fn_path);

    (*global_env)->CallVoidMethod(global_env, global_obj, runtime_error_handler_id, java_reason, java_type, java_on_fn_name, java_on_fn_path);
}

JNIEXPORT void JNICALL Java_game_Game_grugSetRuntimeErrorHandler(JNIEnv *env, jobject obj) {
    (void)env;
    (void)obj;

    grug_set_runtime_error_handler(runtime_error_handler);
}

JNIEXPORT jboolean JNICALL Java_game_Game_errorHasChanged(JNIEnv *env, jobject obj) {
    (void)env;
    (void)obj;

    return grug_error.has_changed;
}

JNIEXPORT jboolean JNICALL Java_game_Game_loadingErrorInGrugFile(JNIEnv *env, jobject obj) {
    (void)env;
    (void)obj;

    return grug_loading_error_in_grug_file;
}

JNIEXPORT jstring JNICALL Java_game_Game_errorMsg(JNIEnv *env, jobject obj) {
    (void)env;
    (void)obj;

    return (*global_env)->NewStringUTF(global_env, grug_error.msg);
}

JNIEXPORT jstring JNICALL Java_game_Game_errorPath(JNIEnv *env, jobject obj) {
    (void)env;
    (void)obj;

    return (*global_env)->NewStringUTF(global_env, grug_error.path);
}

JNIEXPORT jstring JNICALL Java_game_Game_onFnName(JNIEnv *env, jobject obj) {
    (void)env;
    (void)obj;

    return (*global_env)->NewStringUTF(global_env, grug_on_fn_name);
}

JNIEXPORT jstring JNICALL Java_game_Game_onFnPath(JNIEnv *env, jobject obj) {
    (void)env;
    (void)obj;

    return (*global_env)->NewStringUTF(global_env, grug_on_fn_path);
}

JNIEXPORT jint JNICALL Java_game_Game_errorGrugCLineNumber(JNIEnv *env, jobject obj) {
    (void)env;
    (void)obj;

    return grug_error.grug_c_line_number;
}

JNIEXPORT jboolean JNICALL Java_game_Game_grugRegenerateModifiedMods(JNIEnv *env, jobject obj) {
    (void)env;
    (void)obj;

    return grug_regenerate_modified_mods();
}

JNIEXPORT jint JNICALL Java_game_Game_getGrugReloadsSize(JNIEnv *env, jobject obj) {
    (void)env;
    (void)obj;

    return grug_reloads_size;
}

JNIEXPORT void JNICALL Java_game_Game_fillReloadData(JNIEnv *env, jobject obj, jobject reload_data_object, jint reload_index) {
    (void)obj;

    struct grug_modified c_reload_data = grug_reloads[reload_index];

    jclass reload_data_class = (*env)->GetObjectClass(env, reload_data_object);

    jfieldID path_fid = (*env)->GetFieldID(env, reload_data_class, "path", "Ljava/lang/String;");
    jstring path = (*env)->NewStringUTF(env, c_reload_data.path);
    (*env)->SetObjectField(env, reload_data_object, path_fid, path);

    jfieldID old_dll_fid = (*env)->GetFieldID(env, reload_data_class, "oldDll", "J");
    (*env)->SetLongField(env, reload_data_object, old_dll_fid, (jlong)c_reload_data.old_dll);

    jfieldID file_fid = (*env)->GetFieldID(env, reload_data_class, "file", "Lgame/GrugFile;");
    jobject file_object = (*env)->GetObjectField(env, reload_data_object, file_fid);

    jclass file_class = (*env)->GetObjectClass(env, file_object);

    struct grug_file c_file = c_reload_data.file;

    jfieldID name_fid = (*env)->GetFieldID(env, file_class, "name", "Ljava/lang/String;");
    jstring name = (*env)->NewStringUTF(env, c_file.name);
    (*env)->SetObjectField(env, file_object, name_fid, name);

    jfieldID dll_fid = (*env)->GetFieldID(env, file_class, "dll", "J");
    (*env)->SetLongField(env, file_object, dll_fid, (jlong)c_file.dll);

    jfieldID define_fn_fid = (*env)->GetFieldID(env, file_class, "defineFn", "J");
    (*env)->SetLongField(env, file_object, define_fn_fid, (jlong)c_file.define_fn);

    jfieldID globals_size_fid = (*env)->GetFieldID(env, file_class, "globalsSize", "I");
    (*env)->SetIntField(env, file_object, globals_size_fid, (jint)c_file.globals_size);

    jfieldID init_globals_fn_fid = (*env)->GetFieldID(env, file_class, "initGlobalsFn", "J");
    (*env)->SetLongField(env, file_object, init_globals_fn_fid, (jlong)c_file.init_globals_fn);

    jfieldID define_type_fid = (*env)->GetFieldID(env, file_class, "defineType", "Ljava/lang/String;");
    jstring define_type = (*env)->NewStringUTF(env, c_file.define_type);
    (*env)->SetObjectField(env, file_object, define_type_fid, define_type);

    jfieldID on_fns_fid = (*env)->GetFieldID(env, file_class, "onFns", "J");
    (*env)->SetLongField(env, file_object, on_fns_fid, (jlong)c_file.on_fns);

    jfieldID resource_mtimes_fid = (*env)->GetFieldID(env, file_class, "resourceMtimes", "J");
    (*env)->SetLongField(env, file_object, resource_mtimes_fid, (jlong)c_file.resource_mtimes);
}

JNIEXPORT void JNICALL Java_game_Game_callInitGlobals(JNIEnv *env, jobject obj, jlong init_globals_fn, jbyteArray globals, jint entity_id) {
    (void)obj;

    jbyte *globals_bytes = (*env)->GetByteArrayElements(env, globals, NULL);

    ((grug_init_globals_fn_t)init_globals_fn)(globals_bytes, entity_id);

    (*env)->ReleaseByteArrayElements(env, globals, globals_bytes, 0);
}

JNIEXPORT void JNICALL Java_game_Game_init(JNIEnv *env, jobject obj) {
    global_env = env;
    global_obj = obj;

    jclass javaClass = (*env)->GetObjectClass(env, obj);

    runtime_error_handler_id = (*env)->GetMethodID(env, javaClass, "runtimeErrorHandler", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V");

    jclass entity_definitions_class = (*env)->FindClass(env, "game/EntityDefinitions");

    jfieldID block_definition_fid = (*env)->GetStaticFieldID(env, entity_definitions_class, "block", "Lgame/Block;");

    block_definition_obj = (*env)->GetStaticObjectField(env, entity_definitions_class, block_definition_fid);

    block_definition_obj = (*env)->NewGlobalRef(env, block_definition_obj);

    jfieldID block_entity_definition_fid = (*env)->GetStaticFieldID(env, entity_definitions_class, "block_entity", "Lgame/BlockEntity;");

    block_entity_definition_obj = (*env)->GetStaticObjectField(env, entity_definitions_class, block_entity_definition_fid);

    block_entity_definition_obj = (*env)->NewGlobalRef(env, block_entity_definition_obj);

}

JNIEXPORT void JNICALL Java_game_Game_fillRootGrugDir(JNIEnv *env, jobject obj, jobject dir_object) {
    (void)obj;

    jclass dir_class = (*env)->GetObjectClass(env, dir_object);

    jfieldID name_fid = (*env)->GetFieldID(env, dir_class, "name", "Ljava/lang/String;");
    jstring name = (*env)->NewStringUTF(env, grug_mods.name);
    (*env)->SetObjectField(env, dir_object, name_fid, name);

    jfieldID dirs_size_fid = (*env)->GetFieldID(env, dir_class, "dirsSize", "I");
    (*env)->SetIntField(env, dir_object, dirs_size_fid, (jint)grug_mods.dirs_size);

    jfieldID files_size_fid = (*env)->GetFieldID(env, dir_class, "filesSize", "I");
    (*env)->SetIntField(env, dir_object, files_size_fid, (jint)grug_mods.files_size);

    jfieldID address_fid = (*env)->GetFieldID(env, dir_class, "address", "J");
    (*env)->SetLongField(env, dir_object, address_fid, (jlong)&grug_mods);
}

JNIEXPORT void JNICALL Java_game_Game_fillGrugDir(JNIEnv *env, jobject obj, jobject dir_object, jlong parent_dir_address, jint dir_index) {
    (void)obj;

    jclass dir_class = (*env)->GetObjectClass(env, dir_object);

    struct grug_mod_dir *parent_dir = (struct grug_mod_dir *)parent_dir_address;

    struct grug_mod_dir dir = parent_dir->dirs[dir_index];

    jfieldID name_fid = (*env)->GetFieldID(env, dir_class, "name", "Ljava/lang/String;");
    jstring name = (*env)->NewStringUTF(env, dir.name);
    (*env)->SetObjectField(env, dir_object, name_fid, name);

    jfieldID dirs_size_fid = (*env)->GetFieldID(env, dir_class, "dirsSize", "I");
    (*env)->SetIntField(env, dir_object, dirs_size_fid, (jint)dir.dirs_size);

    jfieldID files_size_fid = (*env)->GetFieldID(env, dir_class, "filesSize", "I");
    (*env)->SetIntField(env, dir_object, files_size_fid, (jint)dir.files_size);

    jfieldID address_fid = (*env)->GetFieldID(env, dir_class, "address", "J");
    (*env)->SetLongField(env, dir_object, address_fid, (jlong)&parent_dir->dirs[dir_index]);
}

JNIEXPORT void JNICALL Java_game_Game_fillGrugFile(JNIEnv *env, jobject obj, jobject file_object, jlong parent_dir_address, jint file_index) {
    (void)obj;

    jclass file_class = (*env)->GetObjectClass(env, file_object);

    struct grug_mod_dir *parent_dir = (struct grug_mod_dir *)parent_dir_address;

    struct grug_file file = parent_dir->files[file_index];

    jfieldID name_fid = (*env)->GetFieldID(env, file_class, "name", "Ljava/lang/String;");
    jstring name = (*env)->NewStringUTF(env, file.name);
    (*env)->SetObjectField(env, file_object, name_fid, name);

    jfieldID dll_fid = (*env)->GetFieldID(env, file_class, "dll", "J");
    (*env)->SetLongField(env, file_object, dll_fid, (jlong)file.dll);

    jfieldID define_fn_fid = (*env)->GetFieldID(env, file_class, "defineFn", "J");
    (*env)->SetLongField(env, file_object, define_fn_fid, (jlong)file.define_fn);

    jfieldID globals_size_fid = (*env)->GetFieldID(env, file_class, "globalsSize", "I");
    (*env)->SetIntField(env, file_object, globals_size_fid, (jint)file.globals_size);

    jfieldID init_globals_fn_fid = (*env)->GetFieldID(env, file_class, "initGlobalsFn", "J");
    (*env)->SetLongField(env, file_object, init_globals_fn_fid, (jlong)file.init_globals_fn);

    jfieldID define_type_fid = (*env)->GetFieldID(env, file_class, "defineType", "Ljava/lang/String;");
    jstring define_type = (*env)->NewStringUTF(env, file.define_type);
    (*env)->SetObjectField(env, file_object, define_type_fid, define_type);

    jfieldID on_fns_fid = (*env)->GetFieldID(env, file_class, "onFns", "J");
    (*env)->SetLongField(env, file_object, on_fns_fid, (jlong)file.on_fns);

    jfieldID resource_mtimes_fid = (*env)->GetFieldID(env, file_class, "resourceMtimes", "J");
    (*env)->SetLongField(env, file_object, resource_mtimes_fid, (jlong)file.resource_mtimes);
}

JNIEXPORT void JNICALL Java_game_Game_callDefineFn(JNIEnv *env, jobject obj, jlong define_fn) {
    (void)env;
    (void)obj;

    ((grug_define_fn_t)define_fn)();
}

JNIEXPORT void JNICALL Java_game_Game_toggleOnFnsMode(JNIEnv *env, jobject obj) {
    (void)env;
    (void)obj;

    grug_toggle_on_fns_mode();
}

JNIEXPORT jboolean JNICALL Java_game_Game_areOnFnsInSafeMode(JNIEnv *env, jobject obj) {
    (void)env;
    (void)obj;

    return grug_are_on_fns_in_safe_mode();
}

JNIEXPORT jboolean JNICALL Java_game_Game_block_entity_1has_1onTick(JNIEnv *env, jobject obj, jlong on_fns) {
    (void)env;
    (void)obj;

    return ((struct block_entity_on_fns *)on_fns)->on_tick != NULL;
}

JNIEXPORT void JNICALL Java_game_Game_block_entity_1onTick(JNIEnv *env, jobject obj, jlong on_fns, jbyteArray globals) {
    (void)obj;

    jbyte *globals_bytes = (*env)->GetByteArrayElements(env, globals, NULL);

    ((struct block_entity_on_fns *)on_fns)->on_tick(globals_bytes);

    (*env)->ReleaseByteArrayElements(env, globals, globals_bytes, 0);
}

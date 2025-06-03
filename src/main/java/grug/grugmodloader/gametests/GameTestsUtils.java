package grug.grugmodloader.gametests;

import java.util.HashMap;
import java.util.HashSet;

import grug.grugmodloader.AssertEntityTypeException;
import grug.grugmodloader.FooBlock;
import grug.grugmodloader.FooBlockEntity;
import grug.grugmodloader.GrugEntityType;
import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;

public class GameTestsUtils {
    private static GameTestHelper h;

    public static void reset(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = new HashSet<>();

        h = helper;
    }

    // Utils

    public static Block get_block(String resourceLocationString) {
        return ForgeRegistries.BLOCKS.getValue(new ResourceLocation(resourceLocationString));
    }

    public static long get_foo_block_entity() {
        BlockPos block_pos = new BlockPos(1, 2, 3);

        FooBlock foo_block = GrugModLoader.FOO_BLOCK.get();
        BlockState block_state = foo_block.defaultBlockState();

        BlockEntityType<FooBlockEntity> foo_block_entity_type = GrugModLoader.FOO_BLOCK_ENTITY.get();

        BlockEntity foo_block_entity = foo_block_entity_type.create(block_pos, block_state);

        foo_block_entity.setLevel(h.getLevel());

        foo_block_entity.onLoad();

        return Grug.addEntity(GrugEntityType.BlockEntity, foo_block_entity);
    }

    public static long get_item_entity() {
        long item_stack = item_stack(item(resource_location("diamond")));

        return item_entity(get_level(), 0, 0, 0, item_stack);
    }

    public static long get_level() {
        return Grug.addEntity(GrugEntityType.Level, h.getLevel());
    }

    public static HashMap<Object, Long> get_hash_set_objects(long hashSetId) {
        try {
            return Grug.getHashSetObjects(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            // Unreachable
            h.fail(assertEntityTypeException.getMessage());
        }
        return null;
    }

    public static Object get_object(long id) {
        Object object = GrugModLoader.grug.getObject(id);
        h.assertTrue(object != null, "object was not supposed to be null for id " + id);
        return object;
    }

    // Asserters

    public static void assert_block_not_present(Block block, BlockPos blockPos) {
        h.assertBlockNotPresent(block, blockPos);
    }

    public static void assert_block_present(Block block, BlockPos blockPos) {
        h.assertBlockPresent(block, blockPos);
    }

    public static void assert_error_id(long id) {
        h.assertTrue(id == -1, "Expected the error id -1, but got " + id);
    }

    public static void assert_not_error_id(long id) {
        h.assertTrue(id != -1, "Expected the id to not be -1");
    }

    public static void assert_item_entity_not_present() {
        h.assertEntityNotPresent(EntityType.ITEM);
    }

    public static void assert_item_entity_present() {
        h.assertEntityPresent(EntityType.ITEM);
    }

    public static void assert_fn_entities_contains(long id) {
        h.assertTrue(Grug.fnEntities.contains(id), "Grug.fnEntities did not contain " + id);
    }

    public static void assert_game_function_error(String expectedErrorString) {
        h.assertTrue(Grug.gameFunctionError.equals(expectedErrorString), "Expected Grug.gameFunctionError to be \"" + expectedErrorString + "\", but got \"" + Grug.gameFunctionError + "\"");
    }

    public static void assert_message(String expectedMessage) {
        h.assertTrue(Grug.sentMessage.equals(expectedMessage), "Expected Grug.sentMessage to be \"" + expectedMessage + "\", but got \"" + Grug.sentMessage + "\"");
    }

    public static void assert_no_error() {
        h.assertTrue(Grug.gameFunctionError == null, "Expected Grug.gameFunctionError to be null, but got \"" + Grug.gameFunctionError + "\"");
    }

    // Game function wrappers

    public static long block(long resourceLocation) {
        long block = GameFunctions.block(resourceLocation);
        h.assertTrue(block != -1, "Invalid block " + block);
        assert_fn_entities_contains(block);
        return block;
    }

    public static int block_flag_update_all() {
        int flag = GameFunctions.block_flag_update_all();
        h.assertTrue(flag == Block.UPDATE_ALL, "Did not return Block.UPDATE_ALL, but " + flag);
        return flag;
    }

    public static long block_pos(int x, int y, int z) {
        long blockPos = GameFunctions.block_pos(x, y, z);
        h.assertTrue(blockPos != -1, "Invalid blockPos " + blockPos);
        assert_fn_entities_contains(blockPos);
        return blockPos;
    }

    public static long box_i32(int i32) {
        long box = GameFunctions.box_i32(i32);
        h.assertTrue(box != -1, "Invalid box " + box);
        assert_fn_entities_contains(box);
        return box;
    }

    public static void destroy_and_drop_block(long blockPosId, long level) {
        GameFunctions.destroy_and_drop_block(blockPosId, level);
    }

    public static void destroy_block(long blockPosId, long level) {
        GameFunctions.destroy_block(blockPosId, level);
    }

    public static long get_block_entity_level(long blockEntity) {
        return GameFunctions.get_block_entity_level(blockEntity);
    }

    public static long get_block_pos_of_block_entity(long blockEntity) {
        long blockPos = GameFunctions.get_block_pos_of_block_entity(blockEntity);;
        h.assertTrue(blockPos != -1, "Invalid blockPos " + blockPos);
        assert_fn_entities_contains(blockPos);
        return blockPos;
    }

    public static long get_block_pos_above(long block_pos) {
        return GameFunctions.get_block_pos_above(block_pos);
    }

    public static long get_block_pos_above_n(long block_pos, int n) {
        return GameFunctions.get_block_pos_above_n(block_pos, n);
    }

    public static long get_block_pos_center(long block_pos) {
        return GameFunctions.get_block_pos_center(block_pos);
    }

    public static int get_block_pos_x(long block_pos) {
        return GameFunctions.get_block_pos_x(block_pos);
    }

    public static int get_block_pos_y(long block_pos) {
        return GameFunctions.get_block_pos_y(block_pos);
    }

    public static int get_block_pos_z(long block_pos) {
        return GameFunctions.get_block_pos_z(block_pos);
    }

    public static long get_block_state(long blockPos, long level) {
        return GameFunctions.get_block_state(blockPos, level);
    }

    public static long get_default_block_state(long block) {
        long blockState = GameFunctions.get_default_block_state(block);
        h.assertTrue(blockState != -1, "Invalid blockState " + blockState);
        assert_fn_entities_contains(blockState);
        return blockState;
    }

    public static int get_hash_map_size(long hashMap) {
        return GameFunctions.get_hash_map_size(hashMap);
    }

    public static String get_hash_map_string(long hashMap) {
        return GameFunctions.get_hash_map_string(hashMap);
    }

    public static int get_hash_set_size(long hashSet) {
        return GameFunctions.get_hash_set_size(hashSet);
    }

    public static String get_hash_set_string(long hashSet) {
        return GameFunctions.get_hash_set_string(hashSet);
    }

    public static String get_item_entity_name(long item) {
        return GameFunctions.get_item_entity_name(item);
    }

    public static String get_item_name(long item) {
        return GameFunctions.get_item_name(item);
    }

    public static String get_item_stack_name(long item) {
        return GameFunctions.get_item_stack_name(item);
    }

    public static String get_level_name(long level) {
        return GameFunctions.get_level_name(level);
    }

    public static String get_resource_location_string(long resourceLocation) {
        return GameFunctions.get_resource_location_string(resourceLocation);
    }

    public static float get_vec3_x(long vec3) {
        return GameFunctions.get_vec3_x(vec3);
    }

    public static float get_vec3_y(long vec3) {
        return GameFunctions.get_vec3_y(vec3);
    }

    public static float get_vec3_z(long vec3) {
        return GameFunctions.get_vec3_z(vec3);
    }

    public static long hash_map() {
        long hashMap = GameFunctions.hash_map();
        h.assertTrue(hashMap != -1, "Invalid hashMap " + hashMap);
        assert_fn_entities_contains(hashMap);
        return hashMap;
    }

    public static void hash_map_put(long hashMap, long key, long value) {
        GameFunctions.hash_map_put(hashMap, key, value);
    }

    public static long hash_set() {
        long hashSet = GameFunctions.hash_set();
        h.assertTrue(hashSet != -1, "Invalid hashSet " + hashSet);
        assert_fn_entities_contains(hashSet);
        return hashSet;
    }

    public static void hash_set_add(long hashSet, long value) {
        GameFunctions.hash_set_add(hashSet, value);
    }

    public static void hash_set_clear(long hashSet) {
        GameFunctions.hash_set_clear(hashSet);
    }

    public static boolean hash_set_has(long hashSet, long value) {
        return GameFunctions.hash_set_has(hashSet, value);
    }

    public static boolean is_air(long blockState) {
        return GameFunctions.is_air(blockState);
    }

    public static boolean is_client_side(long level) {
        return GameFunctions.is_client_side(level);
    }

    public static long item(long resourceLocation) {
        long item = GameFunctions.item(resourceLocation);
        h.assertTrue(item != -1, "Invalid item " + item);
        assert_fn_entities_contains(item);
        return item;
    }

    public static long item_entity(long level, int x, int y, int z, long itemStack) {
        long itemEntity = GameFunctions.item_entity(level, x, y, z, itemStack);
        h.assertTrue(itemEntity != -1, "Invalid itemEntity " + itemEntity);
        assert_fn_entities_contains(itemEntity);
        return itemEntity;
    }

    public static long item_stack(long item) {
        long itemStack = GameFunctions.item_stack(item);
        h.assertTrue(itemStack != -1, "Invalid itemStack " + itemStack);
        assert_fn_entities_contains(itemStack);
        return itemStack;
    }

    public static long iteration(long iterator) {
        long element = GameFunctions.iteration(iterator);
        h.assertTrue(element != -1, "Invalid element " + element);
        assert_fn_entities_contains(element);
        return element;
    }

    public static long iterator(long iterable) {
        long iterator = GameFunctions.iterator(iterable);
        h.assertTrue(iterator != -1, "Invalid iterator " + iterator);
        assert_fn_entities_contains(iterator);
        return iterator;
    }

    public static void iterator_remove(long iterator) {
        GameFunctions.iterator_remove(iterator);
    }

    public static void place_block(long blockState, long block_pos, int flag, long level) {
        GameFunctions.place_block(blockState, block_pos, flag, level);
    }

    public static void print_bool(boolean b) {
        GameFunctions.print_bool(b);
    }

    public static void print_f32(float f) {
        GameFunctions.print_f32(f);
    }

    public static void print_i32(int i) {
        GameFunctions.print_i32(i);
    }

    public static void print_id(long id) {
        GameFunctions.print_id(id);
    }

    public static void print_string(String string) {
        GameFunctions.print_string(string);
    }

    public static void remove_block(long blockPos, long level) {
        GameFunctions.remove_block(blockPos, level);
    }

    public static long resource_location(String resourceLocationString) {
        long resourceLocation = GameFunctions.resource_location(resourceLocationString);
        h.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);
        assert_fn_entities_contains(resourceLocation);
        return resourceLocation;
    }

    public static void set_entity_delta_movement(long entity, long vec3) {
        GameFunctions.set_entity_delta_movement(entity, vec3);
    }

    public static void spawn_entity(long entity, long level) {
        GameFunctions.spawn_entity(entity, level);
    }

    public static int unbox_i32(long box) {
        return GameFunctions.unbox_i32(box);
    }

    public static long vec3(float x, float y, float z) {
        long vec3 = GameFunctions.vec3(x, y, z);
        h.assertTrue(vec3 != -1, "Invalid vec3 " + vec3);
        assert_fn_entities_contains(vec3);
        return vec3;
    }

    public static long vec3_zero() {
        long vec3 = GameFunctions.vec3_zero();
        h.assertTrue(vec3 != -1, "Invalid vec3 " + vec3);
        assert_fn_entities_contains(vec3);
        return vec3;
    }
}

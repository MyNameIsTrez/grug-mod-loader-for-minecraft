package grug.grugmodloader.gametests;

import java.util.HashMap;
import java.util.HashSet;

import grug.grugmodloader.AssertEntityTypeException;
import grug.grugmodloader.EntityType;
import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
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

    public static long get_level() {
        return Grug.addEntity(EntityType.Level, h.getLevel());
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

    public static void assert_fn_entities_contains(long id) {
        h.assertTrue(Grug.fnEntities.contains(id), "Grug.fnEntities did not contain " + id);
    }

    public static void assert_game_function_error(String expectedErrorString) {
        h.assertTrue(Grug.gameFunctionError.equals(expectedErrorString), "Grug.gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");
    }

    // Game function wrappers

    public static long block(long resourceLocation) {
        long block = GameFunctions.block(resourceLocation);
        h.assertTrue(block != -1, "Invalid block " + block);
        return block;
    }

    public static void block_flag_update_all() {
        int flag = GameFunctions.block_flag_update_all();
        h.assertTrue(flag == Block.UPDATE_ALL, "Did not return Block.UPDATE_ALL, but " + flag);
    }

    public static long block_pos(int x, int y, int z) {
        long blockPos = GameFunctions.block_pos(x, y, z);
        h.assertTrue(blockPos != -1, "Invalid blockPos " + blockPos);
        return blockPos;
    }

    public static long box_i32(int i32) {
        long box = GameFunctions.box_i32(i32);
        h.assertTrue(box != -1, "Invalid box " + box);
        return box;
    }

    public static void destroy_block(long blockPosId, long level) {
        GameFunctions.destroy_block(blockPosId, level);
    }

    public static long get_default_block_state(long block) {
        return GameFunctions.get_default_block_state(block);
    }

    public static String get_hash_set_string(long hashSet) {
        return GameFunctions.get_hash_set_string(hashSet);
    }

    public static long hash_set() {
        long hashSet = GameFunctions.hash_set();
        h.assertTrue(hashSet != -1, "Invalid hashSet " + hashSet);
        return hashSet;
    }

    public static void hash_set_add(long hashSet, long value) {
        GameFunctions.hash_set_add(hashSet, value);
    }

    public static boolean hash_set_has(long hashSet, long value) {
        return GameFunctions.hash_set_has(hashSet, value);
    }

    public static int hash_set_size(long hashSet) {
        return GameFunctions.get_hash_set_size(hashSet);
    }

    public static boolean is_client_side(long level) {
        return GameFunctions.is_client_side(level);
    }

    public static long item(long resourceLocation) {
        long item = GameFunctions.item(resourceLocation);
        h.assertTrue(item != -1, "Invalid item " + item);
        return item;
    }

    public static long item_entity(long level, int x, int y, int z, long itemStack) {
        long itemEntity = GameFunctions.item_entity(level, x, y, z, itemStack);
        h.assertTrue(itemEntity != -1, "Invalid itemEntity " + itemEntity);
        return itemEntity;
    }

    public static long item_stack(long item) {
        long itemStack = GameFunctions.item_stack(item);
        h.assertTrue(itemStack != -1, "Invalid itemStack " + itemStack);
        return itemStack;
    }

    public static long resource_location(String resourceLocationString) {
        long resourceLocation = GameFunctions.resource_location(resourceLocationString);
        h.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);
        return resourceLocation;
    }
}

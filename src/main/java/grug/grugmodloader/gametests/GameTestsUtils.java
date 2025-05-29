package grug.grugmodloader.gametests;

import java.util.HashSet;

import grug.grugmodloader.EntityType;
import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
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

    public static void destroy_block(long blockPosId) {
        GameFunctions.destroy_block(blockPosId, get_level());
    }

    public static long resource_location(String resourceLocationString) {
        long resourceLocation = GameFunctions.resource_location(resourceLocationString);
        h.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);
        return resourceLocation;
    }
}

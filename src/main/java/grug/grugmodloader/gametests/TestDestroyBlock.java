package grug.grugmodloader.gametests;

import java.util.HashSet;

import grug.grugmodloader.EntityType;
import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.registries.ForgeRegistries;

@GameTestHolder(GrugModLoader.MODID)
public class TestDestroyBlock {
    /*
    ```grug
    on_a() {
        block: id = block(resource_location("diamond_block"))

        relative_diamond_block_pos: id = block_pos(0, 1, 0)

        assert_block_present(block, relative_diamond_block_pos)

        absolute_diamond_block_pos: id = absolute_pos(relative_diamond_block_pos)

        x: i32 = get_block_pos_x(absolute_diamond_block_pos)
        y: i32 = get_block_pos_y(absolute_diamond_block_pos)
        z: i32 = get_block_pos_z(absolute_diamond_block_pos)

        block_pos: id = block_pos(x, y, z)

        level: id = get_game_test_info_server_level()

        destroy_block(block_pos, level)

        assert_block_not_present(block, relative_diamond_block_pos)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void destroy_block(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relativeDiamondBlockPos = new BlockPos(0, 1, 0);

        helper.assertBlockPresent(block, relativeDiamondBlockPos);

        BlockPos absoluteDiamondBlockPos = helper.absolutePos(relativeDiamondBlockPos);

        int x = absoluteDiamondBlockPos.getX();
        int y = absoluteDiamondBlockPos.getY();
        int z = absoluteDiamondBlockPos.getZ();

        long blockPosId = GameFunctions.block_pos(x, y, z);
        helper.assertTrue(blockPosId != -1, "Invalid blockPosId " + blockPosId);

        ServerLevel level = helper.getLevel();

        long levelId = Grug.addEntity(EntityType.Level, level);

        GameFunctions.destroy_block(blockPosId, levelId);

        helper.assertBlockNotPresent(block, relativeDiamondBlockPos);

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        block: id = block(resource_location("diamond_block"))

        relative_diamond_block_pos: id = block_pos(0, 1, 0)

        assert_block_present(block, relative_diamond_block_pos)

        box: id = box_i32(1)

        destroy_block(box, box)

        assert_game_function_error("destroy_block(): Expected block_pos, but got boxed_i32")

        assert_block_present(block, relative_diamond_block_pos)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void destroy_block_expected_block_pos(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relativeDiamondBlockPos = new BlockPos(0, 1, 0);

        helper.assertBlockPresent(block, relativeDiamondBlockPos);

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);

        GameFunctions.destroy_block(box, box);

        helper.assertTrue(Grug.gameFunctionError.equals("destroy_block(): Expected block_pos, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        helper.assertBlockPresent(block, relativeDiamondBlockPos);

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        block: id = block(resource_location("diamond_block"))

        relative_diamond_block_pos: id = block_pos(0, 1, 0)

        assert_block_present(block, relative_diamond_block_pos)

        absolute_diamond_block_pos: id = absolute_pos(relative_diamond_block_pos)

        x: i32 = get_block_pos_x(absolute_diamond_block_pos)
        y: i32 = get_block_pos_y(absolute_diamond_block_pos)
        z: i32 = get_block_pos_z(absolute_diamond_block_pos)

        block_pos: id = block_pos(x, y, z)

        box: id = box_i32(1)

        destroy_block(block_pos, box)

        assert_game_function_error("destroy_block(): Expected level, but got boxed_i32")

        assert_block_not_present(block, relative_diamond_block_pos)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void destroy_block_expected_level(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relativeDiamondBlockPos = new BlockPos(0, 1, 0);

        helper.assertBlockPresent(block, relativeDiamondBlockPos);

        BlockPos absoluteDiamondBlockPos = helper.absolutePos(relativeDiamondBlockPos);

        int x = absoluteDiamondBlockPos.getX();
        int y = absoluteDiamondBlockPos.getY();
        int z = absoluteDiamondBlockPos.getZ();

        long blockPosId = GameFunctions.block_pos(x, y, z);
        helper.assertTrue(blockPosId != -1, "Invalid blockPosId " + blockPosId);

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);

        GameFunctions.destroy_block(blockPosId, box);

        helper.assertTrue(Grug.gameFunctionError.equals("destroy_block(): Expected level, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        helper.assertBlockPresent(block, relativeDiamondBlockPos);

        helper.succeed();
    }
}

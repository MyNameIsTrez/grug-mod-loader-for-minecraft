package grug.grugmodloader.gametests;

import grug.grugmodloader.EntityType;
import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.registries.ForgeRegistries;

@GameTestHolder(GrugModLoader.MODID)
public class TestDestroyBlock extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void destroy_block(GameTestHelper h) {
        reset(h);

        Block block = get_block("diamond_block");

        BlockPos relative = new BlockPos(0, 1, 0);

        assert_block_present(block, relative);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long block_pos = block_pos(x, y, z);

        destroy_block(block_pos);

        assert_block_not_present(block, relative);

        h.succeed();
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
    public static void destroy_block_expected_block_pos(GameTestHelper h) {
        reset(h);

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relativeDiamondBlockPos = new BlockPos(0, 1, 0);

        h.assertBlockPresent(block, relativeDiamondBlockPos);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);

        GameFunctions.destroy_block(box, box);

        h.assertTrue(Grug.gameFunctionError.equals("destroy_block(): Expected block_pos, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        h.assertBlockPresent(block, relativeDiamondBlockPos);

        h.succeed();
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
    public static void destroy_block_expected_level(GameTestHelper h) {
        reset(h);

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relativeDiamondBlockPos = new BlockPos(0, 1, 0);

        h.assertBlockPresent(block, relativeDiamondBlockPos);

        BlockPos absoluteDiamondBlockPos = h.absolutePos(relativeDiamondBlockPos);

        int x = absoluteDiamondBlockPos.getX();
        int y = absoluteDiamondBlockPos.getY();
        int z = absoluteDiamondBlockPos.getZ();

        long blockPosId = GameFunctions.block_pos(x, y, z);
        h.assertTrue(blockPosId != -1, "Invalid blockPosId " + blockPosId);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);

        GameFunctions.destroy_block(blockPosId, box);

        h.assertTrue(Grug.gameFunctionError.equals("destroy_block(): Expected level, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        h.assertBlockPresent(block, relativeDiamondBlockPos);

        h.succeed();
    }
}

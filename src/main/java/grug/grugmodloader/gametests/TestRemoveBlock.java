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
public class TestRemoveBlock extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        block: id = block(resource_location("diamond_block"))

        relative: id = block_pos(0, 1, 0)

        assert_block_present(block, relative)

        absolute: id = absolute_pos(relative)

        x: i32 = get_block_pos_x(absolute)
        y: i32 = get_block_pos_y(absolute)
        z: i32 = get_block_pos_z(absolute)

        block_pos: id = block_pos(x, y, z)

        remove_block(block_pos, get_level())

        assert_block_not_present(block, relative)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void remove_block(GameTestHelper h) {
        reset(h);

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relative = new BlockPos(0, 1, 0);

        h.assertBlockPresent(block, relative);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long blockPosId = GameFunctions.block_pos(x, y, z);
        h.assertTrue(blockPosId != -1, "Invalid blockPosId " + blockPosId);

        long level = Grug.addEntity(EntityType.Level, h.getLevel());

        GameFunctions.remove_block(blockPosId, level);

        h.assertBlockNotPresent(block, relative);

        h.succeed();
    }

    /*
    ```grug
    on_a() {
        block: id = block(resource_location("diamond_block"))

        relative: id = block_pos(0, 1, 0)

        assert_block_present(block, relative)

        box: id = box_i32(1)

        remove_block(box, box)

        assert_game_function_error("remove_block(): Expected block_pos, but got boxed_i32")

        assert_block_present(block, relative)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void remove_block_expected_block_pos(GameTestHelper h) {
        reset(h);

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relative = new BlockPos(0, 1, 0);

        h.assertBlockPresent(block, relative);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);

        GameFunctions.remove_block(box, box);

        h.assertTrue(Grug.gameFunctionError.equals("remove_block(): Expected block_pos, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        h.assertBlockPresent(block, relative);

        h.succeed();
    }

    /*
    ```grug
    on_a() {
        block: id = block(resource_location("diamond_block"))

        relative: id = block_pos(0, 1, 0)

        assert_block_present(block, relative)

        absolute: id = absolute_pos(relative)

        x: i32 = get_block_pos_x(absolute)
        y: i32 = get_block_pos_y(absolute)
        z: i32 = get_block_pos_z(absolute)

        block_pos: id = block_pos(x, y, z)

        box: id = box_i32(1)

        remove_block(block_pos, box)

        assert_game_function_error("remove_block(): Expected level, but got boxed_i32")

        assert_block_not_present(block, relative)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void remove_block_expected_level(GameTestHelper h) {
        reset(h);

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relative = new BlockPos(0, 1, 0);

        h.assertBlockPresent(block, relative);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long blockPosId = GameFunctions.block_pos(x, y, z);
        h.assertTrue(blockPosId != -1, "Invalid blockPosId " + blockPosId);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);

        GameFunctions.remove_block(blockPosId, box);

        h.assertTrue(Grug.gameFunctionError.equals("remove_block(): Expected level, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        h.assertBlockPresent(block, relative);

        h.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.EntityType;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestRemoveBlock extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void remove_block(GameTestHelper h) {
        reset(h);

        Block block = get_block("diamond_block");

        BlockPos relative = new BlockPos(0, 1, 0);

        h.assertBlockPresent(block, relative);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long blockPosId = block_pos(x, y, z);

        long level = Grug.addEntity(EntityType.Level, h.getLevel());

        remove_block(blockPosId, level);

        h.assertBlockNotPresent(block, relative);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void remove_block_expected_block_pos(GameTestHelper h) {
        reset(h);

        Block block = get_block("diamond_block");

        BlockPos relative = new BlockPos(0, 1, 0);

        h.assertBlockPresent(block, relative);

        long box = box_i32(1);

        remove_block(box, box);

        assert_game_function_error("remove_block(): Expected block_pos, but got boxed_i32");

        h.assertBlockPresent(block, relative);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void remove_block_expected_level(GameTestHelper h) {
        reset(h);

        Block block = get_block("diamond_block");

        BlockPos relative = new BlockPos(0, 1, 0);

        h.assertBlockPresent(block, relative);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long blockPosId = block_pos(x, y, z);

        long box = box_i32(1);

        remove_block(blockPosId, box);

        assert_game_function_error("remove_block(): Expected level, but got boxed_i32");

        h.assertBlockPresent(block, relative);

        h.succeed();
    }
}

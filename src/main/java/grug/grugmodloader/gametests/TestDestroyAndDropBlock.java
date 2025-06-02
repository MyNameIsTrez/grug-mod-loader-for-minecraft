package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestDestroyAndDropBlock extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void destroy_and_drop_block(GameTestHelper h) {
        reset(h);

        Block block = get_block("diamond_block");

        BlockPos relative = new BlockPos(0, 1, 0);

        assert_block_present(block, relative);
        assert_item_entity_not_present();

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long block_pos = block_pos(x, y, z);

        destroy_and_drop_block(block_pos, get_level());

        assert_block_not_present(block, relative);
        assert_item_entity_present();

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void destroy_and_drop_block_expected_block_pos(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        destroy_and_drop_block(box, box);

        assert_game_function_error("destroy_and_drop_block(): Expected block_pos, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void destroy_and_drop_block_expected_level(GameTestHelper h) {
        reset(h);

        BlockPos relative = new BlockPos(0, 1, 0);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long block_pos = block_pos(x, y, z);

        destroy_and_drop_block(block_pos, box_i32(1));

        assert_game_function_error("destroy_and_drop_block(): Expected level, but got boxed_i32");

        h.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetBlockPosAbove extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_pos_above(GameTestHelper h) {
        reset(h);

        long above = get_block_pos_above(block_pos(1, 2, 3));

        int x = get_block_pos_x(above);
        int y = get_block_pos_y(above);
        int z = get_block_pos_z(above);

        h.assertTrue(x == 1, "block_pos X was not 1, but " + x);
        h.assertTrue(y == 3, "block_pos Y was not 3, but " + y);
        h.assertTrue(z == 3, "block_pos Z was not 3, but " + z);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_pos_above_expected_block_pos(GameTestHelper h) {
        reset(h);

        assert_error_id(GameFunctions.get_block_pos_above(box_i32(1)));

        assert_game_function_error("get_block_pos_above(): Expected block_pos, but got boxed_i32");

        h.succeed();
    }
}

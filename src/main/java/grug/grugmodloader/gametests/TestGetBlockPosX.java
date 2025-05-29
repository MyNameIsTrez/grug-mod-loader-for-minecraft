package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

public class TestGetBlockPosX extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block(GameTestHelper h) {
        reset(h);

        int x = get_block_pos_x(block_pos(1, 2, 3));

        h.assertTrue(x == 1, "block_pos X was not 1, but " + x);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_pos_x_expected_vec3(GameTestHelper h) {
        reset(h);

        int x = get_block_pos_x(box_i32(1));

        h.assertTrue(x == -1, "block_pos X was not -1, but " + x);

        assert_game_function_error("get_block_pos_x(): Expected block_pos, but got boxed_i32");

        h.succeed();
    }
}

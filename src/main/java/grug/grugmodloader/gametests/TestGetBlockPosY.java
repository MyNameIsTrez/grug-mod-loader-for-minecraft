package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetBlockPosY extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block(GameTestHelper h) {
        reset(h);

        int y = get_block_pos_y(block_pos(1, 2, 3));

        h.assertTrue(y == 2, "block_pos Y was not 2, but " + y);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_pos_y_expected_vec3(GameTestHelper h) {
        reset(h);

        int y = get_block_pos_y(box_i32(1));

        h.assertTrue(y == -1, "block_pos Y was not -1, but " + y);

        assert_game_function_error("get_block_pos_y(): Expected block_pos, but got boxed_i32");

        h.succeed();
    }
}

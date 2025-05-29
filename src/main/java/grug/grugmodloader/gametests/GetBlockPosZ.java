package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

public class GetBlockPosZ extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block(GameTestHelper h) {
        reset(h);

        int z = get_block_pos_z(block_pos(1, 2, 3));

        h.assertTrue(z == 3, "block_pos Z was not 3, but " + z);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_pos_z_expected_vec3(GameTestHelper h) {
        reset(h);

        int z = get_block_pos_z(box_i32(1));

        h.assertTrue(z == -1, "block_pos Z was not -1, but " + z);

        assert_game_function_error("get_block_pos_z(): Expected block_pos, but got boxed_i32");

        h.succeed();
    }
}

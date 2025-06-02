package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetBlockPosCenter extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_pos_center(GameTestHelper h) {
        reset(h);

        long center = get_block_pos_center(block_pos(1, 2, 3));

        float x = get_vec3_x(center);
        float y = get_vec3_y(center);
        float z = get_vec3_z(center);

        h.assertTrue(x == 1.5, "vec3 X was not 1.5, but " + x);
        h.assertTrue(y == 2.5, "vec3 Y was not 2.5, but " + y);
        h.assertTrue(z == 3.5, "vec3 Z was not 3.5, but " + z);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_pos_center_expected_block_pos(GameTestHelper h) {
        reset(h);

        assert_error_id(GameFunctions.get_block_pos_center(box_i32(1)));

        assert_game_function_error("get_block_pos_center(): Expected block_pos, but got boxed_i32");

        h.succeed();
    }
}

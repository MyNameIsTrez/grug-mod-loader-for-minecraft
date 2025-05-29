package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

public class TestVec3X extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_vec3_x(GameTestHelper h) {
        reset(h);

        float x = get_vec3_x(vec3_zero());
        h.assertTrue(x == 0, "vec3 X was not 0, but " + x);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_vec3_x_expected_vec3(GameTestHelper h) {
        reset(h);

        float x = get_vec3_x(box_i32(1));
        h.assertTrue(x == -1, "vec3 X was not -1, but " + x);

        assert_game_function_error("get_vec3_x(): Expected vec3, but got boxed_i32");

        h.succeed();
    }
}

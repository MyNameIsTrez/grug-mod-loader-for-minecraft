package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;

public class TestVec3Y extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_vec3_y(GameTestHelper h) {
        reset(h);

        float y = get_vec3_y(vec3_zero());
        h.assertTrue(y == 0, "vec3 Y was not 0, but " + y);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_vec3_y_expected_vec3(GameTestHelper h) {
        reset(h);

        float y = get_vec3_y(box_i32(1));
        h.assertTrue(y == -1, "vec3 Y was not -1, but " + y);

        assert_game_function_error("get_vec3_y(): Expected vec3, but got boxed_i32");

        h.succeed();
    }
}

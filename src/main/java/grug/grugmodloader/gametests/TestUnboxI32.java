package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestUnboxI32 extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void unbox_i32(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        h.assertTrue(unbox_i32(box) == 1, "box did not contain the value 1");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void unbox_i32_expected_boxed_i32(GameTestHelper h) {
        reset(h);

        int unboxed_i32 = GameFunctions.unbox_i32(resource_location("diamond_block"));
        h.assertTrue(unboxed_i32 == -1, "Expected an invalid unboxed i32, but got " + unboxed_i32);

        assert_game_function_error("unbox_i32(): Expected boxed_i32, but got resource_location");

        h.succeed();
    }
}

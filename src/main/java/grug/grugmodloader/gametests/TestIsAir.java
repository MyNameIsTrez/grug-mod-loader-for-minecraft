package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIsAir extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_air_true(GameTestHelper h) {
        reset(h);

        long blockState = GameFunctions.get_default_block_state(GameFunctions.block(GameFunctions.resource_location("air")));

        h.assertTrue(GameFunctions.is_air(blockState), "Expected air, but got " + blockState);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_air_false(GameTestHelper h) {
        reset(h);

        long blockState = GameFunctions.get_default_block_state(GameFunctions.block(GameFunctions.resource_location("diamond_block")));

        h.assertFalse(GameFunctions.is_air(blockState), "Expected not air, but got " + blockState);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_air_expected_block_state(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        h.assertFalse(GameFunctions.is_air(box), "Expected not air, but got " + box);

        assert_game_function_error("is_air(): Expected block_state, but got boxed_i32");

        h.succeed();
    }
}

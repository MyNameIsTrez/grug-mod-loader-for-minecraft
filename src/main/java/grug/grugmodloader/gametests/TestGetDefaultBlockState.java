package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetDefaultBlockState extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_default_block_state(GameTestHelper h) {
        reset(h);

        long block_state = get_default_block_state(block(resource_location("air")));

        h.assertTrue(is_air(block_state), "Expected air, but got " + block_state);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_default_block_state_expected_block(GameTestHelper h) {
        reset(h);

        assert_error_id(GameFunctions.get_default_block_state(box_i32(1)));

        assert_game_function_error("get_default_block_state(): Expected block, but got boxed_i32");

        h.succeed();
    }
}

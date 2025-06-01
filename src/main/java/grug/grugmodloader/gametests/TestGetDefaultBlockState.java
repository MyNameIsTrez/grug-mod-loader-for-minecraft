package grug.grugmodloader.gametests;

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

        long box = box_i32(1);

        h.assertFalse(is_air(box), "Expected not air, but got " + box);

        assert_game_function_error("is_air(): Expected block_state, but got boxed_i32");

        h.succeed();
    }
}

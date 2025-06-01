package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetBlockState extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_state(GameTestHelper h) {
        reset(h);

        long block_state = get_block_state(block_pos(0, 0, 0), get_level());

        h.assertTrue(is_air(block_state), "Expected air, but got " + block_state);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_state_expected_block_pos(GameTestHelper h) {
        long box = box_i32(1);

        long block_state = GameFunctions.get_block_state(box, box);
        assert_error_id(block_state);

        assert_game_function_error("get_block_state(): Expected block_pos, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_state_expected_level(GameTestHelper h) {
        long box = box_i32(1);

        long block_state = GameFunctions.get_block_state(block_pos(0, 0, 0), box);
        assert_error_id(block_state);

        assert_game_function_error("get_block_state(): Expected level, but got boxed_i32");

        h.succeed();
    }
}

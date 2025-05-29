package grug.grugmodloader.gametests;

import grug.grugmodloader.EntityType;
import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIsClientSide extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_client_side_false(GameTestHelper h) {
        reset(h);

        long level = get_level();

        h.assertFalse(is_client_side(level), "Expected to not be client side, but not the case in level " + level);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_client_side_expected_level(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        h.assertFalse(GameFunctions.is_client_side(box), "Expected to not be client side, but not the case for box " + box);

        assert_game_function_error("is_client_side(): Expected level, but got boxed_i32");

        h.succeed();
    }
}

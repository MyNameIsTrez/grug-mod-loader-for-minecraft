package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestBlock extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block(GameTestHelper h) {
        reset(h);

        long resource_location = resource_location("diamond_block");

        block(resource_location);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_expected_resource_location(GameTestHelper h) {
        reset(h);

        assert_error_id(GameFunctions.block(box_i32(1)));

        assert_game_function_error("block(): Expected resource_location, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_error_invalid_resource_location(GameTestHelper h) {
        reset(h);

        long resource_location = resource_location("foo");

        assert_error_id(GameFunctions.block(resource_location));

        assert_game_function_error("block(): Invalid resource_location");

        h.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestResourceLocation extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void resource_location(GameTestHelper h) {
        reset(h);

        resource_location("diamond_block");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void resource_location_weird_but_valid_resource_location_string(GameTestHelper h) {
        reset(h);

        resource_location("foo");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void resource_location_invalid_resource_location_string(GameTestHelper h) {
        reset(h);

        assert_error_id(GameFunctions.resource_location("@"));

        assert_game_function_error("resource_location(): Invalid resource_location_string \"@\"");

        h.succeed();
    }
}

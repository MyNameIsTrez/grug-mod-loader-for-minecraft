package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetResourceLocationString extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_resource_location_string(GameTestHelper h) {
        reset(h);

        String resource_location_string = get_resource_location_string(resource_location("diamond"));

        h.assertTrue(resource_location_string.equals("minecraft:diamond"), "Expected resource_location string \"minecraft:diamond\", but got \"" + resource_location_string + "\"");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_resource_location_string_expected_resource_location(GameTestHelper h) {
        reset(h);

        String resource_location_string = get_resource_location_string(box_i32(1));

        h.assertTrue(resource_location_string.equals(""), "Expected resource location string to be an empty string, but got \"" + resource_location_string + "\"");

        assert_game_function_error("get_resource_location_string(): Expected resource_location, but got boxed_i32");

        h.succeed();
    }
}

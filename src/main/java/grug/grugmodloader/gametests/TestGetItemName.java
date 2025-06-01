package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetItemName extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_item_name(GameTestHelper h) {
        reset(h);

        String item_name = get_item_name(item(resource_location("diamond")));

        h.assertTrue(item_name.equals("Diamond"), "Expected item name \"Diamond\", but got \"" + item_name + "\"");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_item_name_expected_item(GameTestHelper h) {
        reset(h);

        String item_name = get_item_name(box_i32(1));

        h.assertTrue(item_name.equals(""), "Expected item name to be an empty string, but got \"" + item_name + "\"");

        assert_game_function_error("get_item_name(): Expected item, but got boxed_i32");

        h.succeed();
    }
}

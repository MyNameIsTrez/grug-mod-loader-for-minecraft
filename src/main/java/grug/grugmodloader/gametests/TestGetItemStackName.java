package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetItemStackName extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_item_stack_name(GameTestHelper h) {
        reset(h);

        String item_stack_name = get_item_stack_name(item_stack(item(resource_location("diamond"))));

        h.assertTrue(item_stack_name.equals("Diamond"), "Expected item stack name \"Diamond\", but got \"" + item_stack_name + "\"");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_item_stack_name_expected_item(GameTestHelper h) {
        reset(h);

        String item_stack_name = get_item_stack_name(box_i32(1));

        h.assertTrue(item_stack_name.equals(""), "Expected item stack name to be an empty string, but got \"" + item_stack_name + "\"");

        assert_game_function_error("get_item_stack_name(): Expected item_stack, but got boxed_i32");

        h.succeed();
    }
}

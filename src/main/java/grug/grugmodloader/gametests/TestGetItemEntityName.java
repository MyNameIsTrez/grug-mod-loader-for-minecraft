package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetItemEntityName extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_item_entity_name(GameTestHelper h) {
        reset(h);

        long item_stack = item_stack(item(resource_location("diamond")));

        long item_entity = item_entity(get_level(), 0, 0, 0, item_stack);

        String item_entity_name = get_item_entity_name(item_entity);

        h.assertTrue(item_entity_name.equals("Diamond"), "Expected item name \"Diamond\", but got \"" + item_entity_name + "\"");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_item_entity_name_expected_item_entity(GameTestHelper h) {
        reset(h);

        String item_entity_name = get_item_entity_name(box_i32(1));

        h.assertTrue(item_entity_name.equals(""), "Expected item entity name to be an empty string, but got \"" + item_entity_name + "\"");

        assert_game_function_error("get_item_entity_name(): Expected item_entity, but got boxed_i32");

        h.succeed();
    }
}

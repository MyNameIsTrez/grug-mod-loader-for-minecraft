package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestItemEntity extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_entity(GameTestHelper h) {
        reset(h);

        long item_stack = item_stack(item(resource_location("diamond")));

        item_entity(get_level(), 0, 0, 0, item_stack);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_entity_expected_level(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        long item_entity = GameFunctions.item_entity(box, 0, 0, 0, box);
        assert_error_id(item_entity);

        assert_game_function_error("item_entity(): Expected level, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_entity_expected_item_stack(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        long item_entity = GameFunctions.item_entity(get_level(), 0, 0, 0, box);
        assert_error_id(item_entity);

        assert_game_function_error("item_entity(): Expected item_stack, but got boxed_i32");

        h.succeed();
    }
}

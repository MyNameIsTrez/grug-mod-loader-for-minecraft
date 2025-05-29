package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestItemStack extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        resource_location: id = resource_location("diamond")

        item: id = item(resource_location)

        item_stack: id = item_stack(item)

        assert_fn_entities_contains(item_stack)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_stack(GameTestHelper h) {
        reset(h);

        long resourceLocation = GameFunctions.resource_location("diamond");
        h.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long item = GameFunctions.item(resourceLocation);
        h.assertTrue(item != -1, "Invalid item " + item);

        long itemStack = GameFunctions.item_stack(item);
        h.assertTrue(itemStack != -1, "Invalid itemStack " + itemStack);

        assert_fn_entities_contains(itemStack);

        h.succeed();
    }

    /*
    ```grug
    on_a() {
        box: id = box_i32(1)

        item_stack: id = item_stack(box)
        assert_error_id(item_stack)

        assert_game_function_error("item_stack(): Expected item, but got boxed_i32")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_stack_expected_item(GameTestHelper h) {
        reset(h);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);

        long itemStack = GameFunctions.item_stack(box);
        h.assertTrue(itemStack == -1, "Expected an invalid itemStack, but got " + itemStack);

        h.assertTrue(Grug.gameFunctionError.equals("item_stack(): Expected item, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        h.succeed();
    }
}

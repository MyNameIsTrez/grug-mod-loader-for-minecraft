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
    public static void item_stack(GameTestHelper helper) {
        reset();

        long resourceLocation = GameFunctions.resource_location("diamond");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long item = GameFunctions.item(resourceLocation);
        helper.assertTrue(item != -1, "Invalid item " + item);

        long itemStack = GameFunctions.item_stack(item);
        helper.assertTrue(itemStack != -1, "Invalid itemStack " + itemStack);

        helper.assertTrue(Grug.fnEntities.contains(itemStack), "fnEntities did not contain " + itemStack);

        helper.succeed();
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
    public static void item_stack_expected_item(GameTestHelper helper) {
        reset();

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);

        long itemStack = GameFunctions.item_stack(box);
        helper.assertTrue(itemStack == -1, "Expected an invalid itemStack, but got " + itemStack);

        helper.assertTrue(Grug.gameFunctionError.equals("item_stack(): Expected item, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        helper.succeed();
    }
}

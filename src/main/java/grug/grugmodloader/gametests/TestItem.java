package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestItem extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        resource_location: id = resource_location("diamond")

        item: id = item(resource_location)

        assert_fn_entities_contains(item)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item(GameTestHelper helper) {
        reset();

        long resourceLocation = GameFunctions.resource_location("diamond");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long item = GameFunctions.item(resourceLocation);
        helper.assertTrue(item != -1, "Invalid item " + item);

        helper.assertTrue(Grug.fnEntities.contains(item), "fnEntities did not contain " + item);

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        box: id = box_i32(1)

        item: id = item(box)
        assert_error_id(item)

        assert_game_function_error("item(): Expected resource_location, but got boxed_i32")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_expected_resource_location(GameTestHelper helper) {
        reset();

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);

        long item = GameFunctions.item(box);
        helper.assertTrue(item == -1, "Expected an invalid item, but got " + item);

        helper.assertTrue(Grug.gameFunctionError.equals("item(): Expected resource_location, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        resource_location: id = resource_location("foo")

        item: id = item(resource_location)
        assert_error_id(item)

        assert_game_function_error("item(): Invalid resource_location")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_error_invalid_resource_location(GameTestHelper helper) {
        reset();

        long resourceLocation = GameFunctions.resource_location("foo");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long item = GameFunctions.item(resourceLocation);
        helper.assertTrue(item == -1, "Expected an invalid item, but got " + item);

        helper.assertTrue(Grug.gameFunctionError.equals("item(): Invalid resource_location"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        helper.succeed();
    }
}

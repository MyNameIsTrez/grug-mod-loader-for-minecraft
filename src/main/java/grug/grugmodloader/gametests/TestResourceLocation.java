package grug.grugmodloader.gametests;

import java.util.HashSet;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestResourceLocation {
    /*
    ```grug
    on_a() {
        resource_location: id = resource_location("white_concrete")

        assert_fn_entities_contains(resource_location)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void resource_location(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long resourceLocation = GameFunctions.resource_location("white_concrete");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        helper.assertTrue(Grug.fnEntities.contains(resourceLocation), "fnEntities did not contain " + resourceLocation);

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        resource_location: id = resource_location("foo")

        assert_fn_entities_contains(resource_location)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void resource_location_weird_but_valid_resource_location_string(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long resourceLocation = GameFunctions.resource_location("foo");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        helper.assertTrue(Grug.fnEntities.contains(resourceLocation), "fnEntities did not contain " + resourceLocation);

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        resource_location: id = resource_location("@")
        assert_error_id(resource_location)

        assert_game_function_error("resource_location(): Invalid resource_location_string")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void resource_location_invalid_resource_location_string(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long resourceLocation = GameFunctions.resource_location("@");
        helper.assertTrue(resourceLocation == -1, "Expected an invalid resourceLocation, but got " + resourceLocation);

        helper.assertTrue(Grug.gameFunctionError.equals("resource_location(): Invalid resource_location_string"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        helper.succeed();
    }
}

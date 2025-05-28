package grug.grugmodloader.gametests;

import java.util.HashSet;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestBlock {
    /*
    ```grug
    on_a() {
        resource_location: id = resource_location("diamond_block")

        block: id = block(resource_location)

        assert_fn_entities_contains(block)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long resourceLocation = GameFunctions.resource_location("diamond_block");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long block = GameFunctions.block(resourceLocation);
        helper.assertTrue(block != -1, "Invalid block " + block);

        helper.assertTrue(Grug.fnEntities.contains(block), "fnEntities did not contain " + block);

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        box: id = box_i32(1)

        block: id = block(box)
        assert_error_id(block)

        assert_game_function_error("block(): Expected resource_location, but got boxed_i32")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_expected_resource_location(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);

        long block = GameFunctions.block(box);
        helper.assertTrue(block == -1, "Expected an invalid block, but got " + block);

        helper.assertTrue(Grug.gameFunctionError.equals("block(): Expected resource_location, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        resource_location: id = resource_location("foo")

        block: id = block(resource_location)
        assert_error_id(block)

        assert_game_function_error("block(): Invalid resource_location")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_error_invalid_resource_location(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long resourceLocation = GameFunctions.resource_location("foo");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long block = GameFunctions.block(resourceLocation);
        helper.assertTrue(block == -1, "Expected an invalid block, but got " + block);

        helper.assertTrue(Grug.gameFunctionError.equals("block(): Invalid resource_location"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        helper.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIsAir extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        assert(is_air(get_default_block_state(block(resource_location("air")))))
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_air_true(GameTestHelper helper) {
        reset();

        long resourceLocation = GameFunctions.resource_location("air");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long block = GameFunctions.block(resourceLocation);
        helper.assertTrue(block != -1, "Invalid block " + block);

        long blockState = GameFunctions.get_default_block_state(block);

        helper.assertTrue(GameFunctions.is_air(blockState), "Expected air, but got " + blockState);

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        assert(not is_air(get_default_block_state(block(resource_location("diamond_block")))))
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_air_false(GameTestHelper helper) {
        reset();

        long resourceLocation = GameFunctions.resource_location("diamond_block");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long block = GameFunctions.block(resourceLocation);
        helper.assertTrue(block != -1, "Invalid block " + block);

        long blockState = GameFunctions.get_default_block_state(block);

        helper.assertTrue(!GameFunctions.is_air(blockState), "Expected not air, but got " + blockState);

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        assert(!is_air(box_i32(1)))

        assert_game_function_error("is_air(): Expected block_state, but got boxed_i32")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_air_expected_block_state(GameTestHelper helper) {
        reset();

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);

        helper.assertTrue(!GameFunctions.is_air(box), "Expected not air, but got " + box);

        helper.assertTrue(Grug.gameFunctionError.equals("is_air(): Expected block_state, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        helper.succeed();
    }
}

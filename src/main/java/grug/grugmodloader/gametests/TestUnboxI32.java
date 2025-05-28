package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestUnboxI32 extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        box: id = box_i32(1)

        assert_fn_entities_contains(box)

        assert(unbox_i32(box) == 1)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void unbox_i32(GameTestHelper helper) {
        reset();

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);

        helper.assertTrue(Grug.fnEntities.contains(box), "fnEntities did not contain " + box);

        helper.assertTrue(GameFunctions.unbox_i32(box) == 1, "box did not contain the value 1");

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        resource_location: id = resource_location("diamond_block")

        unboxed_i32: i32 = unbox_i32(resource_location)
        assert(unboxed_i32 == -1)

        assert_game_function_error("unbox_i32(): Expected boxed_i32, but got resource_location")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void unbox_i32_expected_boxed_i32(GameTestHelper helper) {
        reset();

        long resourceLocation = GameFunctions.resource_location("diamond_block");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        int unboxedI32 = GameFunctions.unbox_i32(resourceLocation);
        helper.assertTrue(unboxedI32 == -1, "Expected an invalid unboxed i32, but got " + unboxedI32);

        helper.assertTrue(Grug.gameFunctionError.equals("unbox_i32(): Expected boxed_i32, but got resource_location"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        helper.succeed();
    }
}

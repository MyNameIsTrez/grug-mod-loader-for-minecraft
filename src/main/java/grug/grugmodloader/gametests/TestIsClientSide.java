package grug.grugmodloader.gametests;

import grug.grugmodloader.EntityType;
import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIsClientSide extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        assert(not is_client_side(get_level()))
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_client_side_false(GameTestHelper helper) {
        reset();

        long level = Grug.addEntity(EntityType.Level, helper.getLevel());

        helper.assertTrue(!GameFunctions.is_client_side(level), "Expected to not be client side, but not the case in level " + level);

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        assert(not is_client_side(box_i32(1)))
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_client_side_expected_level(GameTestHelper helper) {
        reset();

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);

        helper.assertTrue(!GameFunctions.is_client_side(box), "Expected to not be client side, but not the case for box " + box);

        helper.assertTrue(Grug.gameFunctionError.equals("is_client_side(): Expected level, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        helper.succeed();
    }
}

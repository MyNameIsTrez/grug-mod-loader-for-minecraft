package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestItemStack extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_stack(GameTestHelper h) {
        reset(h);

        item_stack(item(resource_location("diamond")));

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_stack_expected_item(GameTestHelper h) {
        reset(h);

        long item_stack = GameFunctions.item_stack(box_i32(1));
        h.assertTrue(item_stack == -1, "Expected an invalid item_stack, but got " + item_stack);

        assert_game_function_error("item_stack(): Expected item, but got boxed_i32");

        h.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestItem extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item(GameTestHelper h) {
        reset(h);

        item(resource_location("diamond"));

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_expected_resource_location(GameTestHelper h) {
        reset(h);

        long item = GameFunctions.item(box_i32(1));
        assert_error_id(item);

        assert_game_function_error("item(): Expected resource_location, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_error_invalid_resource_location(GameTestHelper h) {
        reset(h);

        long item = GameFunctions.item(resource_location("foo"));
        assert_error_id(item);

        assert_game_function_error("item(): Invalid resource_location");

        h.succeed();
    }
}

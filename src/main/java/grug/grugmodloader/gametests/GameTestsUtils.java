package grug.grugmodloader.gametests;

import java.util.HashSet;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import net.minecraft.gametest.framework.GameTestHelper;

public class GameTestsUtils {
    public static void reset() {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = new HashSet<>();
    }

    public static void assert_error_id(GameTestHelper h, long id) {
        h.assertTrue(id == -1, "Expected the error id -1, but got " + id);
    }

    public static void assert_fn_entities_contains(GameTestHelper h, long id) {
        h.assertTrue(Grug.fnEntities.contains(id), "Grug.fnEntities did not contain " + id);
    }

    public static void assert_game_function_error(GameTestHelper h, String expectedErrorString) {
        h.assertTrue(Grug.gameFunctionError.equals(expectedErrorString), "Grug.gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");
    }

    public static long block(GameTestHelper h, long resourceLocation) {
        long block = GameFunctions.block(resourceLocation);
        h.assertTrue(block != -1, "Invalid block " + block);
        return block;
    }

    public static long box_i32(GameTestHelper h, int i32) {
        long box = GameFunctions.box_i32(i32);
        h.assertTrue(box != -1, "Invalid box " + box);
        return box;
    }

    public static long resource_location(GameTestHelper h, String resourceLocationString) {
        long resourceLocation = GameFunctions.resource_location(resourceLocationString);
        h.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);
        return resourceLocation;
    }
}

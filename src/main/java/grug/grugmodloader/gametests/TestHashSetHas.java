package grug.grugmodloader.gametests;

import java.util.HashSet;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashSetHas extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        hash_set: id = hash_set()

        hash_set_add(hash_set, box_i32(1))

        assert(hash_set_has(hash_set, box_i32(1)))
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_has(GameTestHelper helper) {
        reset();

        long hashSetId = GameFunctions.hash_set();
        helper.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);
        GameFunctions.hash_set_add(hashSetId, box);

        long boxSecond = GameFunctions.box_i32(1);
        helper.assertTrue(boxSecond != -1, "Invalid boxSecond " + boxSecond);
        helper.assertTrue(GameFunctions.hash_set_has(hashSetId, boxSecond), "hashSetId did not contain boxSecond " + boxSecond);

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        hash_set: id = hash_set()

        hash_set_add(hash_set, box_i32(1))

        assert(!hash_set_has(hash_set, box_i32(2)))
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_has_not(GameTestHelper helper) {
        reset();

        long hashSetId = GameFunctions.hash_set();
        helper.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);
        GameFunctions.hash_set_add(hashSetId, box);

        long boxSecond = GameFunctions.box_i32(2);
        helper.assertTrue(boxSecond != -1, "Invalid boxSecond " + boxSecond);
        helper.assertFalse(GameFunctions.hash_set_has(hashSetId, boxSecond), "hashSetId did contain boxSecond " + boxSecond);

        helper.succeed();
    }

    /*
    ```grug
    on_a() {
        box: id = box_i32(1)

        assert(!hash_set_has(box, box))

        assert_game_function_error("hash_set_has(): Expected hash_set, but got boxed_i32")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_has_expected_hash_set(GameTestHelper helper) {
        reset();

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);
        helper.assertFalse(GameFunctions.hash_set_has(box, box), "hash_set_has() was expected to return false");

        helper.assertTrue(Grug.gameFunctionError.equals("hash_set_has(): Expected hash_set, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        helper.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashSetHas extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_has(GameTestHelper h) {
        reset(h);

        long hash_set = GameFunctions.hash_set();

        GameFunctions.hash_set_add(hash_set, GameFunctions.box_i32(1));

        long box = GameFunctions.box_i32(1);
        h.assertTrue(GameFunctions.hash_set_has(hash_set, box), "hash_set did not contain box " + box);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_has_not(GameTestHelper h) {
        reset(h);

        long hash_set = GameFunctions.hash_set();

        GameFunctions.hash_set_add(hash_set, GameFunctions.box_i32(1));

        long box = GameFunctions.box_i32(2);
        h.assertFalse(GameFunctions.hash_set_has(hash_set, box), "hash_set did contain box " + box);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_has_expected_hash_set(GameTestHelper h) {
        reset(h);

        long box = GameFunctions.box_i32(1);

        h.assertFalse(GameFunctions.hash_set_has(box, box), "hash_set_has() was expected to return false");

        assert_game_function_error("hash_set_has(): Expected hash_set, but got boxed_i32");

        h.succeed();
    }
}

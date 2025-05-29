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

        long hashSet = GameFunctions.hash_set();

        GameFunctions.hash_set_add(hashSet, GameFunctions.box_i32(1));

        long box2 = GameFunctions.box_i32(1);
        h.assertTrue(GameFunctions.hash_set_has(hashSet, box2), "hashSet did not contain box2 " + box2);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_has_not(GameTestHelper h) {
        reset(h);

        long hashSet = GameFunctions.hash_set();

        GameFunctions.hash_set_add(hashSet, GameFunctions.box_i32(1));

        long box2 = GameFunctions.box_i32(2);
        h.assertFalse(GameFunctions.hash_set_has(hashSet, box2), "hashSet did contain box2 " + box2);

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

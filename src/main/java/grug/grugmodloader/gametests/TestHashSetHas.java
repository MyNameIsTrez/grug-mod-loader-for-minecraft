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

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));

        long box = box_i32(1);
        h.assertTrue(hash_set_has(hash_set, box), "hash_set did not contain box " + box);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_has_not(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));

        long box = box_i32(2);
        h.assertFalse(hash_set_has(hash_set, box), "hash_set did contain box " + box);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_has_expected_hash_set(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        h.assertFalse(GameFunctions.hash_set_has(box, box), "hash_set_has() was expected to return false");

        assert_game_function_error("hash_set_has(): Expected hash_set, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_has_nested(GameTestHelper h) {
        reset(h);

        long hash_set_inner_1 = hash_set();
        hash_set_add(hash_set_inner_1, box_i32(1));

        // Identical to hash_set_inner_1
        long hash_set_inner_2 = hash_set();
        hash_set_add(hash_set_inner_2, box_i32(1));

        long hash_set_outer = hash_set();
        hash_set_add(hash_set_outer, hash_set_inner_1);

        h.assertTrue(hash_set_has(hash_set_outer, hash_set_inner_2), "hash_set_outer did not contain hash_set_inner_2");

        h.succeed();
    }
}

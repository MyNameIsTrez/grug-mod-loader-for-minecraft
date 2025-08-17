package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIterating extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterating_true(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));

        long iterator = iterator_hash_set(hash_set);

        h.assertTrue(iterating(iterator), "Expected to still be iterating");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterating_false_empty(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        long iterator = iterator_hash_set(hash_set);

        h.assertFalse(iterating(iterator), "Expected to have nothing to iterate over");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterating_false_exhausted(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));

        long iterator = iterator_hash_set(hash_set);

        iteration(iterator);

        h.assertFalse(iterating(iterator), "Expected to have nothing to iterate over");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterating_expected_iterator(GameTestHelper h) {
        reset(h);

        h.assertFalse(GameFunctions.iterating(box_i32(1)), "Expected iterating() to have returned false");

        assert_game_function_error("iterating(): Expected iterator, but got boxed_i32");

        h.succeed();
    }
}

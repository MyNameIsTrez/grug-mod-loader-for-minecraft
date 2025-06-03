package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIteratorRemove extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterator_remove(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));

        long iterator = iterator(hash_set);

        iteration(iterator);

        int size = get_hash_set_size(hash_set);
        h.assertTrue(size == 1, "Expected hash_set size 1, but got " + size);

        iterator_remove(iterator);
        assert_no_error();

        size = get_hash_set_size(hash_set);
        h.assertTrue(size == 0, "Expected hash_set size 0, but got " + size);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterator_remove_before_iteration(GameTestHelper h) {
        reset(h);

        long iterator = iterator(hash_set());

        iterator_remove(iterator);

        assert_game_function_error("iterator_remove(): Can only be called once, per call to iteration()");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterator_remove_expected_iterator(GameTestHelper h) {
        reset(h);

        iterator_remove(box_i32(1));

        assert_game_function_error("iterator_remove(): Expected iterator, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterator_remove_error_called_more_than_once(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));

        long iterator = iterator(hash_set);

        iteration(iterator);

        iterator_remove(iterator);
        assert_no_error();

        iterator_remove(iterator);

        assert_game_function_error("iterator_remove(): Can only be called once, per call to iteration()");

        h.succeed();
    }
}

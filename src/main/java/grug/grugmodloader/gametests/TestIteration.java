package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIteration extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_hash_set(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));

        long iterator = iterator(hash_set);

        long boxed_i32 = iteration(iterator);

        int value = unbox_i32(boxed_i32);
        h.assertTrue(value == 1, "Expected boxed_i32 to hold the value 1, but it held " + value);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_hash_set_empty(GameTestHelper h) {
        reset(h);

        long iterator = iterator(hash_set());

        assert_error_id(GameFunctions.iteration(iterator));

        assert_game_function_error("iteration(): Only call iteration() after iterating() returns true");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_hash_map(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        long iterator = iterator(hash_map);

        assert_not_error_id(iteration(iterator));

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_hash_map_empty(GameTestHelper h) {
        reset(h);

        long iterator = iterator(hash_map());

        assert_error_id(GameFunctions.iteration(iterator));

        assert_game_function_error("iteration(): Only call iteration() after iterating() returns true");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_hash_map_modified_during_iteration(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        long iterator = iterator(hash_map);

        hash_map_remove_key(hash_map, box_i32(1));
        assert_no_error();

        assert_error_id(GameFunctions.iteration(iterator));

        assert_game_function_error("iteration(): The iterable was modified during iteration");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_expected_iterator(GameTestHelper h) {
        reset(h);

        assert_error_id(GameFunctions.iteration(box_i32(1)));

        assert_game_function_error("iteration(): Expected iterator, but got boxed_i32");

        h.succeed();
    }
}

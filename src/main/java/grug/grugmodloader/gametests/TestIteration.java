package grug.grugmodloader.gametests;

import java.util.ArrayList;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
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

        long iterator = iterator_hash_set(hash_set);

        long boxed_i32 = iteration(iterator);

        int value = unbox_i32(boxed_i32);
        h.assertTrue(value == 1, "Expected boxed_i32 to hold the value 1, but it held " + value);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_hash_set_empty(GameTestHelper h) {
        reset(h);

        long iterator = iterator_hash_set(hash_set());

        assert_error_id(GameFunctions.iteration(iterator));

        assert_game_function_error("iteration(): Only call iteration() after iterating() returns true");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_hash_map(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        long iterator = iterator_hash_map(hash_map);

        assert_not_error_id(iteration(iterator));

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_hash_map_empty(GameTestHelper h) {
        reset(h);

        long iterator = iterator_hash_map(hash_map());

        assert_error_id(GameFunctions.iteration(iterator));

        assert_game_function_error("iteration(): Only call iteration() after iterating() returns true");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_hash_map_modified_during_iteration(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        long iterator = iterator_hash_map(hash_map);

        hash_map_remove_key(hash_map, box_i32(1));
        assert_no_error();

        assert_error_id(GameFunctions.iteration(iterator));

        assert_game_function_error("iteration(): The iterable was modified during iteration");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_hash_set_local_containing_global_box(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = new ArrayList<>();

        long box = box_i32(1);

        Grug.fnEntities = new ArrayList<>();

        long hash_set = hash_set();

        hash_set_add(hash_set, box);

        long iterator = iterator_hash_set(hash_set);

        long boxed_i32 = iteration(iterator);

        int value = unbox_i32(boxed_i32);
        h.assertTrue(value == 1, "Expected boxed_i32 to hold the value 1, but it held " + value);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_hash_set_global_containing_global_box(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = new ArrayList<>();

        long hash_set = hash_set();
        long box = box_i32(1);

        Grug.fnEntities = new ArrayList<>();

        hash_set_add(hash_set, box);

        long iterator = iterator_hash_set(hash_set);

        long boxed_i32 = iteration(iterator);

        int value = unbox_i32(boxed_i32);
        h.assertTrue(value == 1, "Expected boxed_i32 to hold the value 1, but it held " + value);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iteration_hash_set_global_containing_local_box(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = new ArrayList<>();

        long hash_set = hash_set();

        Grug.fnEntities = new ArrayList<>();

        long box = box_i32(1);

        hash_set_add(hash_set, box);

        long iterator = iterator_hash_set(hash_set);

        long boxed_i32 = iteration(iterator);

        int value = unbox_i32(boxed_i32);
        h.assertTrue(value == 1, "Expected boxed_i32 to hold the value 1, but it held " + value);

        h.succeed();
    }
}

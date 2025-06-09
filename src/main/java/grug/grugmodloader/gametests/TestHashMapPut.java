package grug.grugmodloader.gametests;

import java.util.HashSet;

import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashMapPut extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_put(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        h.assertTrue(hash_map_has_key(hash_map, box_i32(1)), "hash_map did not contain key box_i32(1)");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_put_duplicate(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(42));
        hash_map_put(hash_map, box_i32(1), box_i32(69));

        h.assertTrue(hash_map_has_key(hash_map, box_i32(1)), "hash_map did not contain key box_i32(1)");

        int size = get_hash_map_size(hash_map);
        h.assertTrue(size == 1, "size was expected to be 1, but was " + size);

        long value = unbox_i32(hash_map_get(hash_map, box_i32(1)));
        h.assertTrue(value == 69, "hash_map its key box_i32(1) did not have the boxed value 69, but " + value);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_put_expected_hash_map(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        hash_map_put(box, box, box);

        assert_game_function_error("hash_map_put(): Expected hash_map, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_put_local_hash_map_containing_local_box_in_local_hash_map(GameTestHelper h) {
        reset(h);

        long hash_map_inner = hash_map();
        hash_map_put(hash_map_inner, box_i32(1), box_i32(2));

        long hash_map_outer = hash_map();
        hash_map_put(hash_map_outer, box_i32(3), hash_map_inner);

        long hash_map_inner_copy = entry_value(iteration(iterator(hash_map_outer)));

        h.assertTrue(hash_map_has_key(hash_map_inner_copy, box_i32(1)), "hash_map_inner_copy did not contain key box_i32(1)");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_put_local_hash_map_in_itself(GameTestHelper h) {
        reset(h);

        long local_hash_map = hash_map();

        hash_map_put(local_hash_map, box_i32(1), local_hash_map);

        String expected = "{GrugObject{type=BoxedI32, object=1}=GrugObject{type=HashMap, object={GrugObject{type=BoxedI32, object=1}=[cyclic]}}}";

        String hash_map_string = get_hash_map_string(local_hash_map);

        h.assertTrue(hash_map_string.equals(expected), "Expected hash_map_string to be \"" + expected + "\", but got \"" + hash_map_string + "\"");

        // This is just testing that this doesn't cause a hang
        entry_value(iteration(iterator(local_hash_map)));

        h.succeed();
    }
}

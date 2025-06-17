package grug.grugmodloader.gametests;

import java.util.ArrayList;

import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestEntrySetValue extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void entry_set_value(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        entry_set_value(iteration(iterator(hash_map)), box_i32(3));

        int got = unbox_i32(hash_map_get(hash_map, box_i32(1)));
        h.assertTrue(got == 3, "Expected hash_map[1] to be 3, but it was " + got);

        int size = get_hash_map_size(hash_map);
        h.assertTrue(size == 1, "Expected hash_map size to be 1, but it was " + size);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void entry_set_value_in_global_map(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = new ArrayList<>();

        long hash_map = hash_map();

        Grug.fnEntities = new ArrayList<>();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        entry_set_value(iteration(iterator(hash_map)), box_i32(3));

        int got = unbox_i32(hash_map_get(hash_map, box_i32(1)));
        h.assertTrue(got == 3, "Expected hash_map[1] to be 3, but it was " + got);

        int size = get_hash_map_size(hash_map);
        h.assertTrue(size == 1, "Expected hash_map size to be 1, but it was " + size);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void entry_set_value_expected_entry(GameTestHelper h) {
        reset(h);

        entry_set_value(box_i32(1), box_i32(1));

        assert_game_function_error("entry_set_value(): Expected entry, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void entry_set_value_of_removed_key(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        long entry = iteration(iterator(hash_map));

        hash_map_remove_key(hash_map, box_i32(1));
        assert_no_error();

        entry_set_value(entry, box_i32(3));

        int size = get_hash_map_size(hash_map);
        h.assertTrue(size == 0, "Expected hash_map size to be 0, but it was " + size);

        h.succeed();
    }

    // "The behavior of this call is undefined
    // if the mapping has already been removed from the map
    // (by the iterator's remove operation)."
    // and
    // "IllegalStateException - implementations may, but are not required to,
    // throw this exception if the entry has been removed from the backing map."
    // come from the docs of java.util.Map.Entry.setValue()
    //
    // Even though the behavior is undefined, grug does not detect it for the modder yet.
    // TODO: Let grug throw a runtime error here, and update this test accordingly.
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void entry_set_value_of_removed_entry(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        long iterator = iterator(hash_map);

        long entry = iteration(iterator);

        iterator_remove(iterator);
        assert_no_error();

        entry_set_value(entry, box_i32(3));

        int size = get_hash_map_size(hash_map);
        h.assertTrue(size == 0, "Expected hash_map size to be 0, but it was " + size);

        h.succeed();
    }
}

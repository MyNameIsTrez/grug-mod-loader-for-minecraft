package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestEntryKey extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void entry_key(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        long iterator = iterator(hash_map);

        long entry = iteration(iterator);

        long key = entry_key(entry);

        int key_value = unbox_i32(key);
        h.assertTrue(key_value == 1, "Expected key_value to be 1, but it was " + key_value);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void entry_key_expected_entry(GameTestHelper h) {
        reset(h);

        assert_error_id(GameFunctions.entry_key(box_i32(1)));

        assert_game_function_error("entry_key(): Expected entry, but got boxed_i32");

        h.succeed();
    }
}

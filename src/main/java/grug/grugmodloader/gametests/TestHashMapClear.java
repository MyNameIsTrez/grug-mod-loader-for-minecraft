package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashMapClear extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_clear(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        h.assertTrue(hash_map_has_key(hash_map, box_i32(1)), "hash_map did not contain key box_i32(1)");

        hash_map_clear(hash_map);

        h.assertFalse(hash_map_has_key(hash_map, box_i32(1)), "hash_map contained key box_i32(1)");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_clear_expected_hash_map(GameTestHelper h) {
        reset(h);

        hash_map_clear(box_i32(1));

        assert_game_function_error("hash_map_clear(): Expected hash_map, but got boxed_i32");

        h.succeed();
    }
}

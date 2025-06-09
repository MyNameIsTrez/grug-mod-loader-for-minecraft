package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashMapGet extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_get(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        long boxed_value = hash_map_get(hash_map, box_i32(1));

        int value = unbox_i32(boxed_value);

        h.assertTrue(value == 2, "hash_map its key box_i32(1) did not have the value box_i32(2)");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_get_expected_hash_map(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        hash_map_get(box, box);

        assert_game_function_error("hash_map_get(): Expected hash_map, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_get_error_hash_map_does_not_contain_key(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        assert_error_id(hash_map_get(hash_map, box_i32(1)));

        assert_game_function_error("hash_map_get(): hash_map does not contain key");

        h.succeed();
    }
}

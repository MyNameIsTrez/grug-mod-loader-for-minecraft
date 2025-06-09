package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashMapCopy extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_copy(GameTestHelper h) {
        reset(h);

        long hash_map_1 = hash_map();

        hash_map_put(hash_map_1, box_i32(1), box_i32(42));

        long hash_map_2 = hash_map();

        hash_map_put(hash_map_2, box_i32(2), box_i32(69));

        hash_map_copy(hash_map_1, hash_map_2);

        h.assertTrue(hash_map_has_key(hash_map_2, box_i32(1)), "hash_map_2 did not contain key box_i32(1)");
        h.assertFalse(hash_map_has_key(hash_map_2, box_i32(2)), "hash_map_2 contained key box_i32(2)");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_copy_empty(GameTestHelper h) {
        reset(h);

        long hash_map_1 = hash_map();
        long hash_map_2 = hash_map();

        hash_map_put(hash_map_2, box_i32(1), box_i32(42));

        hash_map_copy(hash_map_1, hash_map_2);

        int size = get_hash_map_size(hash_map_2);

        h.assertTrue(size == 0, "hash_map_2 its size was not 0, but " + size);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_copy_expected_hash_map_from(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        hash_map_copy(box, box);

        assert_game_function_error("hash_map_copy(): Expected hash_map, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_copy_expected_hash_map_to(GameTestHelper h) {
        reset(h);

        hash_map_copy(hash_map(), box_i32(1));

        assert_game_function_error("hash_map_copy(): Expected hash_map, but got boxed_i32");

        h.succeed();
    }
}

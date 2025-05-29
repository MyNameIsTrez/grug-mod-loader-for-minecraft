package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetHashMapSize extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_hash_map_size_empty_map(GameTestHelper h) {
        reset(h);

        int size = get_hash_map_size(hash_map());

        h.assertTrue(size == 0, "hash_map size was not 0, but " + size);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_hash_map_size_1(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(42), box_i32(1));

        int size = get_hash_map_size(hash_map);

        h.assertTrue(size == 1, "hash_map size was not 1, but " + size);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_hash_map_size_2(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(42), box_i32(1));
        hash_map_put(hash_map, box_i32(69), box_i32(2));

        int size = get_hash_map_size(hash_map);

        h.assertTrue(size == 2, "hash_map size was not 2, but " + size);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_hash_map_size_expected_hash_map(GameTestHelper h) {
        reset(h);

        int size = get_hash_map_size(box_i32(1));

        h.assertTrue(size == -1, "hash_map size was not -1, but " + size);

        assert_game_function_error("get_hash_map_size(): Expected hash_map, but got boxed_i32");

        h.succeed();
    }
}

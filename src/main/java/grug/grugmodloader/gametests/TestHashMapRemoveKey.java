package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashMapRemoveKey extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_remove_key(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(42));
        hash_map_put(hash_map, box_i32(2), box_i32(69));

        hash_map_remove_key(hash_map, box_i32(1));

        h.assertFalse(hash_map_has_key(hash_map, box_i32(1)), "hash_map contained key box_i32(1)");
        h.assertTrue(hash_map_has_key(hash_map, box_i32(2)), "hash_map did not contain key box_i32(2)");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_remove_key_hash_map_does_not_contain_key(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        hash_map_remove_key(hash_map, box_i32(2));
        assert_no_error();

        h.assertTrue(hash_map_has_key(hash_map, box_i32(1)), "hash_map did not contain box_i32(1)");

        h.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashMapHasKey extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_has_key(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        long box = box_i32(1);
        h.assertTrue(hash_map_has_key(hash_map, box), "hash_map did not contain key " + box);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_has_key_not(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        long box = box_i32(2);
        h.assertFalse(hash_map_has_key(hash_map, box), "hash_map did contain key " + box);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_map_has_key_expected_hash_map(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        h.assertFalse(GameFunctions.hash_map_has_key(box, box), "hash_map_has_key() was expected to return false");

        assert_game_function_error("hash_map_has_key(): Expected hash_map, but got boxed_i32");

        h.succeed();
    }
}

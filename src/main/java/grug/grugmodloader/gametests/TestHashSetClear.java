package grug.grugmodloader.gametests;

import java.util.HashSet;

import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashSetClear extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_clear(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));

        h.assertTrue(hash_set_has(hash_set, box_i32(1)), "hash_set did not contain box");

        hash_set_clear(hash_set);

        h.assertFalse(hash_set_has(hash_set, box_i32(1)), "hash_set contained box");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_clear_expected_hash_set(GameTestHelper h) {
        reset(h);

        hash_set_clear(box_i32(1));

        assert_game_function_error("hash_set_clear(): Expected hash_set, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_clear_global(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = Grug.globalEntities;

        long hash_set = hash_set();

        Grug.fnEntities = new HashSet<>();

        hash_set_add(hash_set, box_i32(1));

        h.assertTrue(hash_set_has(hash_set, box_i32(1)), "hash_set did not contain box");

        hash_set_clear(hash_set);

        h.assertFalse(hash_set_has(hash_set, box_i32(1)), "hash_set contained box");

        h.succeed();
    }
}

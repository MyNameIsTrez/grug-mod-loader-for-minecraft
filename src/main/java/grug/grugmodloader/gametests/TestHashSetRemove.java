package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashSetRemove extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_remove(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));
        hash_set_add(hash_set, box_i32(2));

        hash_set_remove(hash_set, box_i32(1));

        h.assertFalse(hash_set_has(hash_set, box_i32(1)), "hash_set contained box_i32(1)");
        h.assertTrue(hash_set_has(hash_set, box_i32(2)), "hash_set did not contain box_i32(2)");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_remove_hash_set_does_not_contain_value(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));

        hash_set_remove(hash_set, box_i32(2));
        assert_no_error();

        h.assertTrue(hash_set_has(hash_set, box_i32(1)), "hash_set did not contain box_i32(1)");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_remove_expected_hash_set(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        hash_set_remove(box, box);

        assert_game_function_error("hash_set_remove(): Expected hash_set, but got boxed_i32");

        h.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashSetCopy extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_copy(GameTestHelper h) {
        reset(h);

        long hash_set_1 = hash_set();

        hash_set_add(hash_set_1, box_i32(1));

        long hash_set_2 = hash_set();

        hash_set_add(hash_set_2, box_i32(2));

        hash_set_copy(hash_set_1, hash_set_2);

        h.assertTrue(hash_set_has(hash_set_2, box_i32(1)), "hash_set_2 did not contain box_i32(1)");
        h.assertFalse(hash_set_has(hash_set_2, box_i32(2)), "hash_set_2 contained box_i32(2)");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_copy_empty(GameTestHelper h) {
        reset(h);

        long hash_set_1 = hash_set();
        long hash_set_2 = hash_set();

        hash_set_add(hash_set_2, box_i32(1));

        hash_set_copy(hash_set_1, hash_set_2);

        int size = get_hash_set_size(hash_set_2);

        h.assertTrue(size == 0, "hash_set_2 its size was not 0, but " + size);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_copy_expected_hash_set_from(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        hash_set_copy(box, box);

        assert_game_function_error("hash_set_copy(): Expected hash_set, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_copy_expected_hash_set_to(GameTestHelper h) {
        reset(h);

        hash_set_copy(hash_set(), box_i32(1));

        assert_game_function_error("hash_set_copy(): Expected hash_set, but got boxed_i32");

        h.succeed();
    }
}

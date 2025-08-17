package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetHashSetSize extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_hash_set_size_empty_set(GameTestHelper h) {
        reset(h);

        int size = get_hash_set_size(hash_set());

        h.assertTrue(size == 0, "hash_set size was not 0, but " + size);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_hash_set_size_1(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));

        int size = get_hash_set_size(hash_set);

        h.assertTrue(size == 1, "hash_set size was not 1, but " + size);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_hash_set_size_2(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));
        hash_set_add(hash_set, box_i32(2));

        int size = get_hash_set_size(hash_set);

        h.assertTrue(size == 2, "hash_set size was not 2, but " + size);

        h.succeed();
    }
}

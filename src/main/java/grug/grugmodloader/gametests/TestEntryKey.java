package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestEntryKey extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void entry_key(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        long iterator = iterator_hash_map(hash_map);

        long entry = iteration(iterator);

        long key = entry_key(entry);

        int unboxed_key = unbox_i32(key);
        h.assertTrue(unboxed_key == 1, "Expected unboxed_key to be 1, but it was " + unboxed_key);

        h.succeed();
    }
}

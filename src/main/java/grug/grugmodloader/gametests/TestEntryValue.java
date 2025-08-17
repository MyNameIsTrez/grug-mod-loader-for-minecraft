package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestEntryValue extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void entry_value(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(1), box_i32(2));

        long iterator = iterator_hash_map(hash_map);

        long entry = iteration(iterator);

        long value = entry_value(entry);

        int unboxed_value = unbox_i32(value);
        h.assertTrue(unboxed_value == 2, "Expected unboxed_value to be 2, but it was " + unboxed_value);

        h.succeed();
    }
}

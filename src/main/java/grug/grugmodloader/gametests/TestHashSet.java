package grug.grugmodloader.gametests;

import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashSet extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set(GameTestHelper h) {
        reset(h);

        hash_set();

        h.succeed();
    }

    // This test failed back when entityData wrongly used a WeakHashMap that stored key Longs,
    // as those Longs immediately became eligible for garbage collection after addEntity() returned.
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_not_prematurely_garbage_collected(GameTestHelper h) {
        reset(h);

        hash_set();

        for (int i = 0; i < 10; i++) {
            System.gc();

            if (Grug.entityData.size() == 0) {
                h.fail("Expected entityData.size() to be 1, but was 0, at i=" + i);
            }
        }

        h.succeed();
    }
}

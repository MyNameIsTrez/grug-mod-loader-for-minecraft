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

    // This test failed back when Grug.entityData wrongly used a WeakHashMap that stored key Longs,
    // as those Longs immediately became eligible for garbage collection after addEntity() returned.
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_not_prematurely_garbage_collected(GameTestHelper h) throws InterruptedException {
        reset(h);

        hash_set();

        System.gc();
        Thread.sleep(100); // Give GC time

        h.assertTrue(Grug.entityData.size() > 0, "Expected entityData.size() to not be 0");

        h.succeed();
    }

    // This test failed back when Grug.fnEntities wrongly used a HashSet,
    // as two `hash_set()` calls their hash sets may compare `equals()`.
    // This caused `Grug.fnEntities(hashSetGrugObject1)` followed by `Grug.fnEntities(hashSetGrugObject2)`
    // to overwrite each other.
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_equal_fn_entities_dont_overwrite_each_other(GameTestHelper h) throws InterruptedException {
        reset(h);

        hash_set();
        hash_set();

        int size = Grug.fnEntities.size();
        h.assertTrue(size == 2, "Expected fnEntities.size() to be 2, but got " + size);

        h.succeed();
    }
}

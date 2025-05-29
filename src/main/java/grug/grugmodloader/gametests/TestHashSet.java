package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashSet extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        assert_fn_entities_contains(hash_set);

        h.succeed();
    }
}

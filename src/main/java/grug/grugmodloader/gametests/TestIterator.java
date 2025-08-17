package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIterator extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterator_hash_set(GameTestHelper h) {
        reset(h);

        assert_not_error_id(iterator_hash_set(hash_set()));

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterator_hash_map(GameTestHelper h) {
        reset(h);

        assert_not_error_id(iterator_hash_map(hash_map()));

        h.succeed();
    }
}

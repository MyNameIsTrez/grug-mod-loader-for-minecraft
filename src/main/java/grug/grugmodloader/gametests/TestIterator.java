package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIterator extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterator_hash_set(GameTestHelper h) {
        reset(h);

        assert_not_error_id(iterator(hash_set()));

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterator_hash_map(GameTestHelper h) {
        reset(h);

        assert_not_error_id(iterator(hash_map()));

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void iterator_expected_iterable(GameTestHelper h) {
        reset(h);

        assert_error_id(GameFunctions.iterator(box_i32(1)));

        assert_game_function_error("iterator(): Expected iterable, but got boxed_i32");

        h.succeed();
    }
}

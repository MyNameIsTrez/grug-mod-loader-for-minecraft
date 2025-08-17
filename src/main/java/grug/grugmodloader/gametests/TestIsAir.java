package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIsAir extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_air_true(GameTestHelper h) {
        reset(h);

        long block_state = get_default_block_state(block(resource_location("air")));

        h.assertTrue(is_air(block_state), "Expected air, but got " + block_state);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_air_false(GameTestHelper h) {
        reset(h);

        long block_state = get_default_block_state(block(resource_location("diamond_block")));

        h.assertFalse(is_air(block_state), "Expected not air, but got " + block_state);

        h.succeed();
    }
}

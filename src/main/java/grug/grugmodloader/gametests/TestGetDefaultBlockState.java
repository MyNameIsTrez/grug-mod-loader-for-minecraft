package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetDefaultBlockState extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_default_block_state(GameTestHelper h) {
        reset(h);

        long block_state = get_default_block_state(block(resource_location("air")));

        h.assertTrue(is_air(block_state), "Expected air, but got " + block_state);

        h.succeed();
    }
}

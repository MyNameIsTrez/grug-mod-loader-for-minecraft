package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetBlockState extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_state(GameTestHelper h) {
        reset(h);

        long block_state = get_block_state(block_pos(0, 1000, 0), get_level());

        h.assertTrue(is_air(block_state), "Expected air, but got " + block_state);

        h.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetBlockPosX extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block(GameTestHelper h) {
        reset(h);

        int x = get_block_pos_x(block_pos(1, 2, 3));

        h.assertTrue(x == 1, "block_pos X was not 1, but " + x);

        h.succeed();
    }
}

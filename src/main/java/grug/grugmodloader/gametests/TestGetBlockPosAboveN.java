package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetBlockPosAboveN extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_pos_above_n(GameTestHelper h) {
        reset(h);

        long above = get_block_pos_above_n(block_pos(1, 2, 3), 2);

        int x = get_block_pos_x(above);
        int y = get_block_pos_y(above);
        int z = get_block_pos_z(above);

        h.assertTrue(x == 1, "block_pos X was not 1, but " + x);
        h.assertTrue(y == 4, "block_pos Y was not 4, but " + y);
        h.assertTrue(z == 3, "block_pos Z was not 3, but " + z);

        h.succeed();
    }
}

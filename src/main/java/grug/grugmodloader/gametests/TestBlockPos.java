package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestBlockPos extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_pos(GameTestHelper h) {
        reset(h);

        block_pos(0, 0, 0);

        h.succeed();
    }
}

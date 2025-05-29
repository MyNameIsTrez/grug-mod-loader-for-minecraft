package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestFlagUpdateAll extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_flag_update_all(GameTestHelper h) {
        reset(h);

        block_flag_update_all();

        h.succeed();
    }
}

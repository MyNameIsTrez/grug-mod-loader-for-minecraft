package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestPrintF32 extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void print_f32(GameTestHelper h) {
        reset(h);

        print_f32(1);

        assert_message("1.0");

        h.succeed();
    }
}

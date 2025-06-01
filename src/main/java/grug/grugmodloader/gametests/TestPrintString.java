package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestPrintString extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void print_string(GameTestHelper h) {
        reset(h);

        print_string("foo");

        assert_message("foo");

        h.succeed();
    }
}

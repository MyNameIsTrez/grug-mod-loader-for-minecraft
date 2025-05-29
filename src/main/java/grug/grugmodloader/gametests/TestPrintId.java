package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestPrintId extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void print_id(GameTestHelper h) {
        reset(h);

        print_id(box_i32(1));

        h.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestBoxI32 extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void box_i32(GameTestHelper h) {
        reset(h);

        box_i32(1);

        h.succeed();
    }
}

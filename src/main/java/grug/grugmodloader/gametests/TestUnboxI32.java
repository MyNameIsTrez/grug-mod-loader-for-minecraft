package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestUnboxI32 extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void unbox_i32(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        h.assertTrue(unbox_i32(box) == 1, "box did not contain the value 1");

        h.succeed();
    }
}

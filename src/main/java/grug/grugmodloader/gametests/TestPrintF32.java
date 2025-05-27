package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestPrintF32 {
    /*
    ```grug
    on_a() {
        print_f32(1.0)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void print_f32(GameTestHelper helper) {
        Grug.resetVariables();

        GameFunctions.print_f32(1);

        helper.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestPrintBool {
    /*
    ```grug
    on_a() {
        print_bool(true)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void print_bool(GameTestHelper helper) {
        Grug.resetVariables();

        GameFunctions.print_bool(true);

        helper.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestPrintString {
    /*
    ```grug
    on_a() {
        print_string("foo")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void print_string(GameTestHelper helper) {
        Grug.resetVariables();

        GameFunctions.print_string("foo");

        helper.succeed();
    }
}

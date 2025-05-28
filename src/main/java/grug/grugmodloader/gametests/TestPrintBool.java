package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestPrintBool extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        print_bool(true)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void print_bool(GameTestHelper helper) {
        reset();

        GameFunctions.print_bool(true);

        helper.succeed();
    }
}

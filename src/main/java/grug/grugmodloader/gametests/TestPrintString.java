package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestPrintString extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        print_string("foo")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void print_string(GameTestHelper h) {
        reset(h);

        GameFunctions.print_string("foo");

        h.succeed();
    }
}

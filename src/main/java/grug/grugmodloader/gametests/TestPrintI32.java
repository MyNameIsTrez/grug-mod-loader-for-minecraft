package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestPrintI32 extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        print_i32(1)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void print_i32(GameTestHelper h) {
        reset(h);

        GameFunctions.print_i32(1);

        h.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestPrintId extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        box: id = box_i32(1)

        assert_fn_entities_contains(box)

        print_id(box)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void print_id(GameTestHelper h) {
        reset(h);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);

        h.assertTrue(Grug.fnEntities.contains(box), "fnEntities did not contain " + box);

        GameFunctions.print_id(box);

        h.succeed();
    }
}

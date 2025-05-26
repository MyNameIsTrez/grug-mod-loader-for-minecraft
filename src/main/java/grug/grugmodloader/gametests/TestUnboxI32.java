package grug.grugmodloader.gametests;

import java.util.HashSet;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestUnboxI32 {
    /*
    ```grug
    on_a() {
        box: id = box_i32(1)

        assert_fn_entities_contains(box)

        assert(unbox_i32(box) == 1)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void unbox_i32(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);

        helper.assertTrue(Grug.fnEntities.contains(box), "fnEntities did not contain box " + box);

        helper.assertTrue(GameFunctions.unbox_i32(box) == 1, "box did not contain the value 1");

        helper.succeed();
    }
}

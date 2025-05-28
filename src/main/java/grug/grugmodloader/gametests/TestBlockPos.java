package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestBlockPos extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        block_pos: id = block_pos(0, 0, 0)

        assert_fn_entities_contains(block_pos)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_pos(GameTestHelper helper) {
        reset();

        long blockPos = GameFunctions.block_pos(0, 0, 0);
        helper.assertTrue(blockPos != -1, "Invalid blockPos " + blockPos);

        helper.assertTrue(Grug.fnEntities.contains(blockPos), "fnEntities did not contain " + blockPos);

        helper.succeed();
    }
}

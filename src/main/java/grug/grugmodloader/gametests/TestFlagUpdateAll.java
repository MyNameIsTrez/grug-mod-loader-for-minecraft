package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestFlagUpdateAll extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        # I'm not sure how assert_enum() would be implemented in Java
        assert_enum(block_flag_update_all(), "Block.UPDATE_ALL")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_flag_update_all(GameTestHelper h) {
        int flag = GameFunctions.block_flag_update_all();
        h.assertTrue(flag == Block.UPDATE_ALL, "Did not return Block.UPDATE_ALL, but " + flag);

        h.succeed();
    }
}

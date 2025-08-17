package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIsClientSide extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void is_client_side_false(GameTestHelper h) {
        reset(h);

        long level = get_level();

        h.assertFalse(is_client_side(level), "Expected to not be client side, but not the case in level " + level);

        h.succeed();
    }
}

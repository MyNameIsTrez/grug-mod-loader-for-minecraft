package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestVec3X extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_vec3_x(GameTestHelper h) {
        reset(h);

        float x = get_vec3_x(vec3(1, 2, 3));

        h.assertTrue(x == 1, "vec3 X was not 1, but " + x);

        h.succeed();
    }
}

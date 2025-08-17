package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestVec3Z extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_vec3_z(GameTestHelper h) {
        reset(h);

        float z = get_vec3_z(vec3(1, 2, 3));

        h.assertTrue(z == 3, "vec3 Z was not 3, but " + z);

        h.succeed();
    }
}

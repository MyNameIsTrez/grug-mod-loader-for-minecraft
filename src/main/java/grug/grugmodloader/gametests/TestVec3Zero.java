package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestVec3Zero extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void vec3_zero(GameTestHelper h) {
        reset(h);

        long vec3 = vec3_zero();

        h.assertTrue(GameFunctions.get_vec3_x(vec3) == 0, "vec3 its X value was not 0");
        h.assertTrue(GameFunctions.get_vec3_y(vec3) == 0, "vec3 its Y value was not 0");
        h.assertTrue(GameFunctions.get_vec3_z(vec3) == 0, "vec3 its Z value was not 0");

        h.succeed();
    }
}

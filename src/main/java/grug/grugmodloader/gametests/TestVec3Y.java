package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestVec3Y extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_vec3_y(GameTestHelper h) {
        reset(h);

        float y = get_vec3_y(vec3(1, 2, 3));

        h.assertTrue(y == 2, "vec3 Y was not 2, but " + y);

        h.succeed();
    }
}

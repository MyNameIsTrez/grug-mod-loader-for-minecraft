package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestVec3Zero extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        vec3: id = vec3_zero()

        assert_fn_entities_contains(vec3)

        assert(get_vec3_x(vec3) == 0)
        assert(get_vec3_y(vec3) == 0)
        assert(get_vec3_z(vec3) == 0)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void vec3_zero(GameTestHelper helper) {
        reset();

        long vec3 = GameFunctions.vec3_zero();
        helper.assertTrue(vec3 != -1, "Invalid vec3 " + vec3);

        helper.assertTrue(Grug.fnEntities.contains(vec3), "fnEntities did not contain " + vec3);

        helper.assertTrue(GameFunctions.get_vec3_x(vec3) == 0, "vec3 its X value was not 0");
        helper.assertTrue(GameFunctions.get_vec3_y(vec3) == 0, "vec3 its Y value was not 0");
        helper.assertTrue(GameFunctions.get_vec3_z(vec3) == 0, "vec3 its Z value was not 0");

        helper.succeed();
    }
}

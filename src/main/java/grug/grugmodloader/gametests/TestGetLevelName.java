package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetLevelName extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_level_name(GameTestHelper h) {
        reset(h);

        String level_name = get_level_name(get_level());

        h.assertTrue(level_name.equals("minecraft:overworld"), "Expected level name \"minecraft:overworld\", but got \"" + level_name + "\"");

        h.succeed();
    }
}

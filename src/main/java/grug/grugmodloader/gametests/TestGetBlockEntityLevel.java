package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetBlockEntityLevel extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_entity_level(GameTestHelper h) {
        reset(h);

        long block_entity = get_foo_block_entity();

        long level = get_block_entity_level(block_entity);

        String level_name = get_level_name(level);

        h.assertTrue(level_name.equals("minecraft:overworld"), "Expected level name \"minecraft:overworld\", but got \"" + level_name + "\"");

        h.succeed();
    }
}

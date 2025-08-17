package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetItemEntityName extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_item_entity_name(GameTestHelper h) {
        reset(h);

        String item_entity_name = get_item_entity_name(get_item_entity());

        h.assertTrue(item_entity_name.equals("Diamond"), "Expected item name \"Diamond\", but got \"" + item_entity_name + "\"");

        h.succeed();
    }
}

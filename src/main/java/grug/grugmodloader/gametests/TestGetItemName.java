package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetItemName extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_item_name(GameTestHelper h) {
        reset(h);

        String item_name = get_item_name(item(resource_location("diamond")));

        h.assertTrue(item_name.equals("Diamond"), "Expected item name \"Diamond\", but got \"" + item_name + "\"");

        h.succeed();
    }
}

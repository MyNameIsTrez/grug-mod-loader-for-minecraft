package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetItemStackName extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_item_stack_name(GameTestHelper h) {
        reset(h);

        String item_stack_name = get_item_stack_name(item_stack(item(resource_location("diamond"))));

        h.assertTrue(item_stack_name.equals("Diamond"), "Expected item stack name \"Diamond\", but got \"" + item_stack_name + "\"");

        h.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestItemEntity extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_entity(GameTestHelper h) {
        reset(h);

        long item_stack = item_stack(item(resource_location("diamond")));

        item_entity(get_level(), 0, 0, 0, item_stack);

        h.succeed();
    }
}

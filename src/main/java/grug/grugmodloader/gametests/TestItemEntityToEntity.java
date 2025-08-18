package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestItemEntityToEntity extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_entity_to_entity(GameTestHelper h) {
        reset(h);

        long item_stack = item_stack(item(resource_location("diamond")));

        long item_entity = item_entity(get_level(), 0, 0, 0, item_stack);

        long entity = item_entity_to_entity(item_entity);

        h.assertTrue(item_entity == entity, "Expected item_entity " + item_entity + " and entity " + entity + " to be equal");

        h.succeed();
    }
}

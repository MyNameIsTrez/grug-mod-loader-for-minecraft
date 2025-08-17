package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestAssertException;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestItemStack extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_stack(GameTestHelper h) {
        reset(h);

        item_stack(item(resource_location("diamond")));

        h.succeed();
    }

    // When two item stacks merge, the original item stacks and entities are turned to Air.
    @GameTest(template = GrugModLoader.MODID+":floor")
    public static void item_stack_item_stacking(GameTestHelper h) {
        reset(h);

        // We need to spawn the item in the bounding box of the structure's bounds,
        // as assertEntityPresent() only checks for items within the bounds.
        BlockPos relative = new BlockPos(1, 2, 1);
        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long item = item(resource_location("diamond"));

        long item_stack_1 = item_stack(item);
        long item_stack_2 = item_stack(item);

        long item_entity_1 = item_entity(get_level(), x, y, z, item_stack_1);
        long item_entity_2 = item_entity(get_level(), x, y, z, item_stack_2);

        set_entity_delta_movement(item_entity_1, vec3_zero());
        set_entity_delta_movement(item_entity_2, vec3_zero());

        assert_item_entity_not_present();

        spawn_entity(item_entity_1, get_level());
        spawn_entity(item_entity_2, get_level());

        // Automatically succeed when there is exactly one item entity present
        h.succeedWhen(() -> {
            int itemCount = h.getEntities(EntityType.ITEM).size();
            if (itemCount != 1) {
                throw new GameTestAssertException("Expected 1 item entity, found " + itemCount);
            }
        });
    }
}

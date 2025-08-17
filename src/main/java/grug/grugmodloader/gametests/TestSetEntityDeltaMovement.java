package grug.grugmodloader.gametests;

import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestSetEntityDeltaMovement extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void set_entity_delta_movement(GameTestHelper h) {
        reset(h);

        long item_entity_id = get_item_entity();

        set_entity_delta_movement(item_entity_id, vec3(1, 2, 3));

        ItemEntity item_entity = Grug.getItemEntity(item_entity_id);

        Vec3 delta = item_entity.getDeltaMovement();

        h.assertTrue(delta.x == 1, "delta.x was not 1, but " + delta.x);
        h.assertTrue(delta.y == 2, "delta.y was not 2, but " + delta.y);
        h.assertTrue(delta.z == 3, "delta.z was not 3, but " + delta.z);

        h.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestSpawnEntity extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void spawn_entity(GameTestHelper h) {
        reset(h);

        // We need to spawn the item in the bounding box of the structure's bounds,
        // as assertEntityPresent() only checks for items within the bounds.
        BlockPos relative = new BlockPos(0, 1, 0);
        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long item_stack = item_stack(item(resource_location("diamond")));

        long item_entity = item_entity(get_level(), x, y, z, item_stack);

        assert_item_entity_not_present();

        spawn_entity(item_entity, get_level());
        assert_no_error();

        assert_item_entity_present();

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void spawn_entity_expected_entity(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        spawn_entity(box, box);

        assert_game_function_error("spawn_entity(): Expected entity, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void spawn_entity_expected_level(GameTestHelper h) {
        reset(h);

        spawn_entity(get_item_entity(), box_i32(1));

        assert_game_function_error("spawn_entity(): Expected level, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void spawn_entity_called_twice_on_same_entity(GameTestHelper h) {
        reset(h);

        // We need to spawn the item in the bounding box of the structure's bounds,
        // as assertEntityPresent() only checks for items within the bounds.
        BlockPos relative = new BlockPos(0, 1, 0);
        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long item_stack = item_stack(item(resource_location("diamond")));

        long item_entity = item_entity(get_level(), x, y, z, item_stack);

        assert_item_entity_not_present();

        spawn_entity(item_entity, get_level());
        assert_no_error();

        spawn_entity(item_entity, get_level());
        assert_game_function_error("spawn_entity(): Failed to spawn");

        h.succeed();
    }
}

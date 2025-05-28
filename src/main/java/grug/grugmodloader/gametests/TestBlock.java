package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestBlock extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block(GameTestHelper h) {
        reset();

        long resource_location = resource_location(h, "diamond_block");

        long block = block(h, resource_location);

        assert_fn_entities_contains(h, block);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_expected_resource_location(GameTestHelper h) {
        reset();

        long box = box_i32(h, 1);

        long block = GameFunctions.block(box);
        assert_error_id(h, block);

        assert_game_function_error(h, "block(): Expected resource_location, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_error_invalid_resource_location(GameTestHelper h) {
        reset();

        long resource_location = resource_location(h, "foo");

        long block = GameFunctions.block(resource_location);
        assert_error_id(h, block);

        assert_game_function_error(h, "block(): Invalid resource_location");

        h.succeed();
    }
}

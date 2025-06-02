package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetBlockPosOfBlockEntity extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_pos_of_block_entity(GameTestHelper h) {
        reset(h);

        long block_entity = get_foo_block_entity();

        long block_pos = get_block_pos_of_block_entity(block_entity);

        int x = get_block_pos_x(block_pos);
        int y = get_block_pos_y(block_pos);
        int z = get_block_pos_z(block_pos);

        h.assertTrue(x == 1, "Expected world position X to be 1, but was " + x);
        h.assertTrue(y == 2, "Expected world position Y to be 2, but was " + y);
        h.assertTrue(z == 3, "Expected world position Z to be 3, but was " + z);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_pos_of_block_entity_expected_block_entity(GameTestHelper h) {
        reset(h);

        assert_error_id(GameFunctions.get_block_pos_of_block_entity(box_i32(1)));

        assert_game_function_error("get_block_pos_of_block_entity(): Expected block_entity, but got boxed_i32");

        h.succeed();
    }
}

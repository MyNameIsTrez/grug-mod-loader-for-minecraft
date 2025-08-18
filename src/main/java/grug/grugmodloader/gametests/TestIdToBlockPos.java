package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIdToBlockPos extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void id_to_block_pos(GameTestHelper h) {
        reset(h);

        long block_pos = block_pos(0, 0, 0);
        h.assertTrue(id_to_block_pos(block_pos) == block_pos, "Expected id_to_block_pos(block_pos) == block_pos");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void id_to_block_pos_error_invalid_id(GameTestHelper h) {
        reset(h);

        long box_i32 = box_i32(1);
        id_to_block_pos(box_i32);
        assert_game_function_error("id_to_block_pos(): Expected block_pos, but got boxed_i32");

        h.succeed();
    }
}

package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestIdToBoxedI32 extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void id_to_boxed_i32(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);
        h.assertTrue(id_to_boxed_i32(box) == box, "Expected id_to_boxed_i32(box) == box");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void id_to_boxed_i32_error_invalid_id(GameTestHelper h) {
        reset(h);

        long block_pos = block_pos(0, 0, 0);
        id_to_boxed_i32(block_pos);
        assert_game_function_error("id_to_boxed_i32(): Expected boxed_i32, but got block_pos");

        h.succeed();
    }
}

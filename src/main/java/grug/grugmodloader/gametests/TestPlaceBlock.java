package grug.grugmodloader.gametests;

import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.registries.ForgeRegistries;

@GameTestHolder(GrugModLoader.MODID)
public class TestPlaceBlock extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void place_block(GameTestHelper h) {
        reset(h);

        long block_id = block(resource_location("diamond_block"));

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relative = new BlockPos(0, 2, 0);

        h.assertBlockNotPresent(block, relative);

        long block_state = get_default_block_state(block_id);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        int flag = block_flag_update_all();

        place_block(block_state, block_pos(x, y, z), flag, get_level());

        h.assertBlockPresent(block, relative);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void place_block_expected_block_state(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        place_block(box, box, 0, box);

        assert_game_function_error("place_block(): Expected block_state, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void place_block_expected_block_pos(GameTestHelper h) {
        reset(h);

        long block_id = block(resource_location("diamond_block"));

        long block_state = get_default_block_state(block_id);

        long box = box_i32(1);

        place_block(block_state, box, 0, box);

        assert_game_function_error("place_block(): Expected block_pos, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void place_block_weird_but_valid_flags(GameTestHelper h) {
        reset(h);

        long block_id = block(resource_location("diamond_block"));

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relative = new BlockPos(0, 2, 0);

        h.assertBlockNotPresent(block, relative);

        long block_state = get_default_block_state(block_id);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long block_pos = block_pos(x, y, z);

        place_block(block_state, block_pos, 2147483647, get_level());

        h.assertBlockPresent(block, relative);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void place_block_expected_level(GameTestHelper h) {
        reset(h);

        long block_id = block(resource_location("diamond_block"));

        BlockPos relative = new BlockPos(0, 2, 0);

        long block_state = get_default_block_state(block_id);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long block_pos = block_pos(x, y, z);

        int flag = block_flag_update_all();

        GameFunctions.place_block(block_state, block_pos, flag, box_i32(1));

        assert_game_function_error("place_block(): Expected level, but got boxed_i32");

        h.succeed();
    }
}

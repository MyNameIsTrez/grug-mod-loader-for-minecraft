package grug.grugmodloader.gametests;

import grug.grugmodloader.EntityType;
import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
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

        long blockId = block(resource_location("diamond_block"));

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relative = new BlockPos(0, 2, 0);

        h.assertBlockNotPresent(block, relative);

        long blockState = get_default_block_state(blockId);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        int flag = block_flag_update_all();

        place_block(blockState, block_pos(x, y, z), flag, get_level());

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

        long blockId = block(resource_location("diamond_block"));

        long blockState = GameFunctions.get_default_block_state(blockId);

        long box = box_i32(1);

        place_block(blockState, box, 0, box);

        assert_game_function_error("place_block(): Expected block_pos, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void place_block_weird_but_valid_flags(GameTestHelper h) {
        reset(h);

        long blockId = block(resource_location("diamond_block"));

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relative = new BlockPos(0, 2, 0);

        h.assertBlockNotPresent(block, relative);

        long blockState = get_default_block_state(blockId);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long blockPos = block_pos(x, y, z);

        GameFunctions.place_block(blockState, blockPos, 2147483647, get_level());

        h.assertBlockPresent(block, relative);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void place_block_expected_level(GameTestHelper h) {
        reset(h);

        long blockId = GameFunctions.block(resource_location("diamond_block"));

        BlockPos relative = new BlockPos(0, 2, 0);

        long blockState = get_default_block_state(blockId);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long blockPos = block_pos(x, y, z);

        int flag = block_flag_update_all();

        GameFunctions.place_block(blockState, blockPos, flag, box_i32(1));

        h.succeed();
    }
}

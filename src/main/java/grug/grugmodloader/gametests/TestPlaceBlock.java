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
    /*
    ```grug
    on_a() {
        block: id = block(resource_location("diamond_block"))

        relative: id = block_pos(0, 1, 0)

        assert_block_not_present(block, relative)

        block_state: id = get_default_block_state(block)

        absolute: id = absolute_pos(relative)

        x: i32 = get_block_pos_x(absolute)
        y: i32 = get_block_pos_y(absolute)
        z: i32 = get_block_pos_z(absolute)

        block_pos: id = block_pos(x, y, z)

        flag: i32 = block_flag_update_all()

        place_block(block_state, block_pos, flag, get_level())

        assert_block_present(block, relative)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void place_block(GameTestHelper h) {
        reset(h);

        long resourceLocation = GameFunctions.resource_location("diamond_block");
        h.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long blockId = GameFunctions.block(resourceLocation);
        h.assertTrue(blockId != -1, "Invalid blockId " + blockId);

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relative = new BlockPos(0, 2, 0);

        h.assertBlockNotPresent(block, relative);

        long blockState = GameFunctions.get_default_block_state(blockId);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long blockPos = GameFunctions.block_pos(x, y, z);
        h.assertTrue(blockPos != -1, "Invalid blockPos " + blockPos);

        int flag = GameFunctions.block_flag_update_all();
        h.assertTrue(flag == Block.UPDATE_ALL, "Did not return Block.UPDATE_ALL, but " + flag);

        long level = Grug.addEntity(EntityType.Level, h.getLevel());

        GameFunctions.place_block(blockState, blockPos, flag, level);

        h.assertBlockPresent(block, relative);

        h.succeed();
    }

    /*
    ```grug
    on_a() {
        box: id = box_i32(1)

        place_block(box, box, 0, box)

        assert_game_function_error("place_block(): Expected block_state, but got boxed_i32")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void place_block_expected_block_state(GameTestHelper h) {
        reset(h);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);

        GameFunctions.place_block(box, box, 0, box);

        h.assertTrue(Grug.gameFunctionError.equals("place_block(): Expected block_state, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        h.succeed();
    }

    /*
    ```grug
    on_a() {
        block: id = block(resource_location("diamond_block"))

        block_state: id = get_default_block_state(block)

        box: id = box_i32(1)

        place_block(block_state, box, 0, box)

        assert_game_function_error("place_block(): Expected block_pos, but got boxed_i32")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void place_block_expected_block_pos(GameTestHelper h) {
        reset(h);

        long resourceLocation = GameFunctions.resource_location("diamond_block");
        h.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long blockId = GameFunctions.block(resourceLocation);
        h.assertTrue(blockId != -1, "Invalid blockId " + blockId);

        long blockState = GameFunctions.get_default_block_state(blockId);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);

        GameFunctions.place_block(blockState, box, 0, box);

        h.assertTrue(Grug.gameFunctionError.equals("place_block(): Expected block_pos, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        h.succeed();
    }

    /*
    ```grug
    on_a() {
        block: id = block(resource_location("diamond_block"))

        relative: id = block_pos(0, 1, 0)

        assert_block_not_present(block, relative)

        block_state: id = get_default_block_state(block)

        absolute: id = absolute_pos(relative)

        x: i32 = get_block_pos_x(absolute)
        y: i32 = get_block_pos_y(absolute)
        z: i32 = get_block_pos_z(absolute)

        block_pos: id = block_pos(x, y, z)

        flag: i32 = block_flag_update_all()

        place_block(block_state, block_pos, 2147483647, get_level())

        assert_block_present(block, relative)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void place_block_weird_but_valid_flags(GameTestHelper h) {
        reset(h);

        long resourceLocation = GameFunctions.resource_location("diamond_block");
        h.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long blockId = GameFunctions.block(resourceLocation);
        h.assertTrue(blockId != -1, "Invalid blockId " + blockId);

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relative = new BlockPos(0, 2, 0);

        h.assertBlockNotPresent(block, relative);

        long blockState = GameFunctions.get_default_block_state(blockId);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long blockPos = GameFunctions.block_pos(x, y, z);
        h.assertTrue(blockPos != -1, "Invalid blockPos " + blockPos);

        long level = Grug.addEntity(EntityType.Level, h.getLevel());

        GameFunctions.place_block(blockState, blockPos, 2147483647, level);

        h.assertBlockPresent(block, relative);

        h.succeed();
    }

    /*
    ```grug
    on_a() {
        block: id = block(resource_location("diamond_block"))

        relative: id = block_pos(0, 1, 0)

        assert_block_not_present(block, relative)

        block_state: id = get_default_block_state(block)

        absolute: id = absolute_pos(relative)

        x: i32 = get_block_pos_x(absolute)
        y: i32 = get_block_pos_y(absolute)
        z: i32 = get_block_pos_z(absolute)

        block_pos: id = block_pos(x, y, z)

        flag: i32 = block_flag_update_all()

        box: id = box_i32(1)

        place_block(block_state, block_pos, flag, box)

        assert_block_present(block, relative)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void place_block_expected_level(GameTestHelper h) {
        reset(h);

        long resourceLocation = GameFunctions.resource_location("diamond_block");
        h.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long blockId = GameFunctions.block(resourceLocation);
        h.assertTrue(blockId != -1, "Invalid blockId " + blockId);

        BlockPos relative = new BlockPos(0, 2, 0);

        long blockState = GameFunctions.get_default_block_state(blockId);

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long blockPos = GameFunctions.block_pos(x, y, z);
        h.assertTrue(blockPos != -1, "Invalid blockPos " + blockPos);

        int flag = GameFunctions.block_flag_update_all();
        h.assertTrue(flag == Block.UPDATE_ALL, "Did not return Block.UPDATE_ALL, but " + flag);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);

        GameFunctions.place_block(blockState, blockPos, flag, box);

        h.succeed();
    }
}

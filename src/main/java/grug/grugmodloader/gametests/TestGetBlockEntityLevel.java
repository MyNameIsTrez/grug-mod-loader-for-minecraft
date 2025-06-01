package grug.grugmodloader.gametests;

import grug.grugmodloader.FooBlock;
import grug.grugmodloader.FooBlockEntity;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugEntityType;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetBlockEntityLevel extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_entity_level(GameTestHelper h) {
        reset(h);

        BlockPos block_pos = new BlockPos(0, 0, 0);

        FooBlock foo_block = GrugModLoader.FOO_BLOCK.get();
        BlockState block_state = foo_block.defaultBlockState();

        BlockEntityType<FooBlockEntity> foo_block_entity_type = GrugModLoader.FOO_BLOCK_ENTITY.get();
        BlockEntity foo_block_entity = foo_block_entity_type.create(block_pos, block_state);

        foo_block_entity.setLevel(h.getLevel());

        long block_entity = Grug.addEntity(GrugEntityType.BlockEntity, foo_block_entity);

        long level = get_block_entity_level(block_entity);

        String level_name = get_level_name(level);

        h.assertTrue(level_name.equals("minecraft:overworld"), "Expected level name \"minecraft:overworld\", but got \"" + level_name + "\"");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_block_entity_level_expected_block_entity(GameTestHelper h) {
        reset(h);

        assert_error_id(get_block_entity_level(box_i32(1)));

        assert_game_function_error("get_block_entity_level(): Expected block_entity, but got boxed_i32");

        h.succeed();
    }
}

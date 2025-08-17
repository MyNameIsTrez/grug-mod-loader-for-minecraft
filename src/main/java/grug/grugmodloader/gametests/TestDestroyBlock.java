package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestDestroyBlock extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void destroy_block(GameTestHelper h) {
        reset(h);

        Block block = get_block("diamond_block");

        BlockPos relative = new BlockPos(0, 1, 0);

        assert_block_present(block, relative);
        assert_item_entity_not_present();

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long block_pos = block_pos(x, y, z);

        destroy_block(block_pos, get_level());

        assert_block_not_present(block, relative);
        assert_item_entity_not_present();

        h.succeed();
    }
}

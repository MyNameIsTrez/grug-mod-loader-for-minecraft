package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestRemoveBlock extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void remove_block(GameTestHelper h) {
        reset(h);

        Block block = get_block("diamond_block");

        BlockPos relative = new BlockPos(0, 1, 0);

        h.assertBlockPresent(block, relative);
        assert_item_entity_not_present();

        BlockPos absolute = h.absolutePos(relative);

        int x = absolute.getX();
        int y = absolute.getY();
        int z = absolute.getZ();

        long block_pos_id = block_pos(x, y, z);

        remove_block(block_pos_id, get_level());

        h.assertBlockNotPresent(block, relative);
        assert_item_entity_not_present();

        h.succeed();
    }
}

package grug.grugmodloader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class FooBlock extends Block implements EntityBlock {
    public FooBlock(Properties properties) {
        super(properties);
        // System.out.println("In FooBlock its constructor");
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        // This gets printed twice; printing the stack trace shows it's once for the client, and once for the server:
        // https://docs.minecraftforge.net/en/latest/concepts/sides/
        // System.out.println("Calling FooBlockEntity its constructor");
        // for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
        //     System.out.println(ste);
        // }
        return new FooBlockEntity(pos, state);
        // return GrugModLoader.FOO_BLOCK_ENTITY.get().create(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : (level0, pos0, state0, blockEntity) -> ((FooBlockEntity)blockEntity).tick();
    }
}

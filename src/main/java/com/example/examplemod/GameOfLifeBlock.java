package com.example.examplemod;

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

public class GameOfLifeBlock extends Block implements EntityBlock {
    public GameOfLifeBlock(Properties properties) {
        super(properties);
        // System.out.println("In GameOfLifeBlock its constructor");
    }

    @Override
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new GameOfLifeBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide() ? null : (level0, pos0, state0, blockEntity) -> ((GameOfLifeBlockEntity)blockEntity).tick();
    }

    @Override
    protected void neighborChanged(BlockState state, Level level, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, level, pos, blockIn, fromPos, isMoving);
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof GameOfLifeBlockEntity)) {
            throw new RuntimeException("neighborChanged of GameOfLifeBlock expected its blockEntity to be GameOfLifeBlockEntity");
        }
        GameOfLifeBlockEntity gameOfLifeBlockEntity = (GameOfLifeBlockEntity)blockEntity;
        gameOfLifeBlockEntity.neighborChanged(state, level, pos, blockIn, fromPos, isMoving);
    }
}

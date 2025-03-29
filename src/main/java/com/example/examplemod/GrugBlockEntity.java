package com.example.examplemod;

import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class GrugBlockEntity extends BlockEntity {
    public GrugEntity grugEntity = new GrugEntity();
    public long worldPositionId;
    public List<Long> childEntities;

    public GrugBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
}

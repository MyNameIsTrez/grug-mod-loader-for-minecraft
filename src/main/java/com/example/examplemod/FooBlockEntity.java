package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FooBlockEntity extends BlockEntity {
    public FooBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleMod.FOO_BLOCK_ENTITY.get(), pos, state);
    }

    public void tick() {
        System.out.println("Hello from \"tick\"!");
    }
}

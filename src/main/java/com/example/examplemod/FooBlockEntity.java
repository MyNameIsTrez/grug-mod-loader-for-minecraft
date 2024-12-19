package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class FooBlockEntity extends BlockEntity {
    private int ticks;

    public FooBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleMod.FOO_BLOCK_ENTITY.get(), pos, state);
    }

    public void tick() {
        if (++this.ticks % 10 == 0) {
            this.ticks = 0;

            System.out.println("Spawning diamond");

            Vec3 spawnPos = worldPosition.above(2).getCenter();
            ItemEntity diamond = new ItemEntity(level, spawnPos.x, spawnPos.y, spawnPos.z, new ItemStack(Items.DIAMOND));
            diamond.setDeltaMovement(Vec3.ZERO);
            level.addFreshEntity(diamond);
        }
    }
}

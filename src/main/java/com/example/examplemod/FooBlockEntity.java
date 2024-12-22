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
        System.out.println("In FooBlockEntity its tick()");

        if (!Grug.blockEntity_has_onTick(Grug.tempFooBlockEntity.onFns)) {
            return;
        }

        Grug.blockEntity_onTick(Grug.tempFooBlockEntity.onFns, Grug.tempFooBlockEntityGlobals);

        // TODO: Call tick() from the shared object!

        // this.ticks++;
        // if (ticks % 10 != 0) {
        //     return;
        // }
        // this.ticks = 0;

        // System.out.println("Spawning diamond");

        // Vec3 spawnPos = worldPosition.above(2).getCenter();
        // ItemEntity diamond = new ItemEntity(level, spawnPos.x, spawnPos.y, spawnPos.z, new ItemStack(Items.DIAMOND));
        // diamond.setDeltaMovement(Vec3.ZERO);
        // level.addFreshEntity(diamond);
    }
}

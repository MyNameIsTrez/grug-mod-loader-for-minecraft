package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FooBlockEntity extends BlockEntity {
    boolean initialized = true;

    public FooBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleMod.FOO_BLOCK_ENTITY.get(), pos, state);

        GrugFile file = new GrugFile();
        ExampleMod.grug.getEntityFile("foo:foo_block", file);
        System.out.println("file: " + file);

        /* TODO:
         * Create onFns, tempFooBlockEntityDll, tempFooBlockEntityGlobals here,
         * instead of in Grug.java, which'll allow the globals its ID
         * to be the address of `this`
         *
         * This'll allow Grug.gameFn_getWorldPosition() to access this.worldPosition
         *
         * This constructor will also probably need to add itself (`this`) to an ArrayList
         * of the Grug class, so that Grug can update the onFns in reloadModifiedEntities()
         */
    }

    public void tick() {
        if (!initialized) {
            return;
        }

        if (!ExampleMod.grug.blockEntity_has_onTick(Grug.tempFooBlockEntity.onFns)) {
            return;
        }

        ExampleMod.grug.blockEntity_onTick(Grug.tempFooBlockEntity.onFns, Grug.tempFooBlockEntityGlobals);
    }
}

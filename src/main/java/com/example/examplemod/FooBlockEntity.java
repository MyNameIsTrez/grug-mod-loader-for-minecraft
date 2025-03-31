package com.example.examplemod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class FooBlockEntity extends GrugBlockEntity {
    public FooBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleMod.FOO_BLOCK_ENTITY.get(), pos, state);

        List<GrugEntity> grugEntities = Grug.grugEntitiesMap.get("foo:foo_block_entity");
        if (grugEntities == null) {
            grugEntities = new ArrayList<GrugEntity>();
            Grug.grugEntitiesMap.put("foo:foo_block_entity", grugEntities);
        }
        grugEntity.entitiesIndex = grugEntities.size();
        grugEntities.add(grugEntity);

        grugEntity.id = Grug.addEntity(EntityType.BlockEntity, this);

        worldPositionId = Grug.addEntity(EntityType.BlockPos, worldPosition);

        GrugFile file = new GrugFile();
        ExampleMod.grug.getEntityFile("foo:foo_block_entity", file);

        grugEntity.globals = new byte[file.globalsSize];

        Grug.fnEntities = grugEntity.childEntities;
        Grug.gameFunctionErrorHappened = false;
        ExampleMod.grug.callInitGlobals(file.initGlobalsFn, grugEntity.globals, grugEntity.id);
        Grug.fnEntities = Grug.onFnEntities;

        grugEntity.onFns = file.onFns;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        System.out.println("Removing block entity");

        Grug.removeEntity(grugEntity.id);
        Grug.removeEntity(worldPositionId);
        Grug.removeEntities(grugEntity.childEntities);

        // Swap-remove itself from Grug.entities
        List<GrugEntity> grugEntities = Grug.grugEntitiesMap.get("foo:foo_block_entity");

        // assert grugEntities != null; // I haven't been able to figure out how to enable asserts
        if (grugEntities == null) {
            throw new RuntimeException("Swap-removing from Grug's entities failed");
        }

        GrugEntity lastEntity = grugEntities.removeLast();

        // This check prevents the .set() right below it from throwing
        // when grugEntity.entitiesIndex == grugEntities.size()
        if (grugEntity.entitiesIndex < grugEntities.size()) {
            grugEntities.set(grugEntity.entitiesIndex, lastEntity);

            lastEntity.entitiesIndex = grugEntity.entitiesIndex;
        }
    }

    public void tick() {
        if (!ExampleMod.grug.blockEntity_has_onTick(grugEntity.onFns)) {
            return;
        }

        Grug.gameFunctionErrorHappened = false;
        ExampleMod.grug.blockEntity_onTick(grugEntity.onFns, grugEntity.globals);
        Grug.removeEntities(Grug.onFnEntities);
        Grug.onFnEntities.clear();
    }
}

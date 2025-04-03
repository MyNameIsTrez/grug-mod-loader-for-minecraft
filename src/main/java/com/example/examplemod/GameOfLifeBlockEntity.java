package com.example.examplemod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class GameOfLifeBlockEntity extends GrugBlockEntity {
    public GameOfLifeBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleMod.GAME_OF_LIFE_BLOCK_ENTITY.get(), pos, state);

        List<GrugEntity> grugEntities = Grug.grugEntitiesMap.get("foo:game_of_life_block_entity");
        if (grugEntities == null) {
            grugEntities = new ArrayList<GrugEntity>();
            Grug.grugEntitiesMap.put("foo:game_of_life_block_entity", grugEntities);
        }
        grugEntity.entitiesIndex = grugEntities.size();
        grugEntities.add(grugEntity);

        grugEntity.id = Grug.addEntity(EntityType.BlockEntity, this);

        worldPositionId = Grug.addEntity(EntityType.BlockPos, worldPosition);

        GrugFile file = new GrugFile();
        ExampleMod.grug.getEntityFile("foo:game_of_life_block_entity", file);

        grugEntity.globals = new byte[file.globalsSize];

        Grug.fnEntities = grugEntity.childEntities;
        Grug.gameFunctionErrorHappened = false;
        ExampleMod.grug.callInitGlobals(file.initGlobalsFn, grugEntity.globals, grugEntity.id);
        Grug.fnEntities = Grug.onFnEntities;

        grugEntity.onFns = file.onFns;

        spawn();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        // System.out.println("Removing block entity");

        Grug.removeEntity(grugEntity.id);
        Grug.removeEntity(worldPositionId);
        Grug.removeEntities(grugEntity.childEntities);

        // Swap-remove itself from Grug.entities
        List<GrugEntity> grugEntities = Grug.grugEntitiesMap.get("foo:game_of_life_block_entity");

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

    public void spawn() {
        if (!ExampleMod.grug.block_entity_has_on_spawn(grugEntity.onFns)) {
            return;
        }

        Grug.gameFunctionErrorHappened = false;
        ExampleMod.grug.block_entity_on_spawn(grugEntity.onFns, grugEntity.globals);
        Grug.removeEntities(Grug.onFnEntities);
        Grug.onFnEntities.clear();
    }

    public void tick() {
        if (!ExampleMod.grug.block_entity_has_on_tick(grugEntity.onFns)) {
            return;
        }

        Grug.gameFunctionErrorHappened = false;
        ExampleMod.grug.block_entity_on_tick(grugEntity.onFns, grugEntity.globals);
        Grug.removeEntities(Grug.onFnEntities);
        Grug.onFnEntities.clear();
    }
}

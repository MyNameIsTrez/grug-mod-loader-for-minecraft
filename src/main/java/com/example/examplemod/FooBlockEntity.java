package com.example.examplemod;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

public class FooBlockEntity extends GrugBlockEntity {
    public FooBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleMod.FOO_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void onLoad() {
        super.onLoad();

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

        Set<Long> oldGlobalEntities = Grug.globalEntities;
        Grug.globalEntities = grugEntity.childEntities;
        Set<Long> oldFnEntities = Grug.fnEntities;
        Grug.fnEntities = grugEntity.childEntities;
        Set<Long> oldFnIteratedIterables = Grug.fnIteratedIterables;
        Grug.fnIteratedIterables = new HashSet<>();

        Grug.gameFunctionErrorHappened = false;
        ExampleMod.grug.callInitGlobals(file.initGlobalsFn, grugEntity.globals, grugEntity.id);

        Grug.globalEntities = oldGlobalEntities;
        Grug.fnEntities = oldFnEntities;
        Grug.fnIteratedIterables = oldFnIteratedIterables;

        grugEntity.onFns = file.onFns;

        spawn();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        Grug.removeEntity(grugEntity.id);
        Grug.removeEntity(worldPositionId);
        Grug.removeEntities(grugEntity.childEntities);

        // Swap-remove itself from Grug.entities
        List<GrugEntity> grugEntities = Grug.grugEntitiesMap.get("foo:foo_block_entity");

        assert grugEntities != null;

        GrugEntity lastEntity = grugEntities.removeLast();

        // This check prevents the .set() from throwing
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

        Set<Long> oldGlobalEntities = Grug.globalEntities;
        Grug.globalEntities = grugEntity.childEntities;
        Set<Long> oldFnEntities = Grug.fnEntities;
        Grug.fnEntities = new HashSet<>();
        Set<Long> oldFnIteratedIterables = Grug.fnIteratedIterables;
        Grug.fnIteratedIterables = new HashSet<>();

        Grug.gameFunctionErrorHappened = false;
        ExampleMod.grug.block_entity_on_spawn(grugEntity.onFns, grugEntity.globals);

        Grug.globalEntities = oldGlobalEntities;
        Grug.removeEntities(Grug.fnEntities);
        Grug.fnEntities = oldFnEntities;
        Grug.fnIteratedIterables = oldFnIteratedIterables;
    }

    public void tick() {
        if (!ExampleMod.grug.block_entity_has_on_tick(grugEntity.onFns)) {
            return;
        }

        Set<Long> oldGlobalEntities = Grug.globalEntities;
        Grug.globalEntities = grugEntity.childEntities;
        Set<Long> oldFnEntities = Grug.fnEntities;
        Grug.fnEntities = new HashSet<>();
        Set<Long> oldFnIteratedIterables = Grug.fnIteratedIterables;
        Grug.fnIteratedIterables = new HashSet<>();

        Grug.gameFunctionErrorHappened = false;
        ExampleMod.grug.block_entity_on_tick(grugEntity.onFns, grugEntity.globals);

        Grug.globalEntities = oldGlobalEntities;
        Grug.removeEntities(Grug.fnEntities);
        Grug.fnEntities = oldFnEntities;
        Grug.fnIteratedIterables = oldFnIteratedIterables;
    }
}

package com.example.examplemod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FooBlockEntity extends GrugBlockEntity {
    public FooBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleMod.FOO_BLOCK_ENTITY.get(), pos, state);

        GrugFile file = new GrugFile();
        ExampleMod.grug.getEntityFile("foo:foo_block_entity", file);
        // System.out.println("file: " + file);

        List<GrugEntity> grugEntities = Grug.grugEntitiesMap.get("foo:foo_block_entity");
        if (grugEntities == null) {
            grugEntities = new ArrayList<GrugEntity>();
            Grug.grugEntitiesMap.put("foo:foo_block_entity", grugEntities);
        }
        grugEntity.entitiesIndex = grugEntities.size();
        grugEntities.add(grugEntity);

        // TODO: Let the generator only call callDefineFn() when the entity has fields
        // ExampleMod.grug.callDefineFn(file.defineFn);
        // EntityDefinitions.blockEntity.xyz;

        // TODO: Unhardcode
        int entityType = 7;
        grugEntity.id = Grug.addEntity(entityType, this);

        // TODO: Unhardcode
        entityType = 42;
        worldPositionId = Grug.addEntity(entityType, worldPosition);

        grugEntity.globals = new byte[file.globalsSize];

        ExampleMod.grug.callInitGlobals(file.initGlobalsFn, grugEntity.globals, grugEntity.id);

        grugEntity.onFns = file.onFns;
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

        System.out.println("Removing block entity");

        Grug.removeEntity(grugEntity.id);
        Grug.removeEntity(worldPositionId);

        // Swap-remove itself from Grug.entities
        List<GrugEntity> grugEntities = Grug.grugEntitiesMap.get("foo:foo_block_entity");
        assert grugEntities != null;

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

        Grug.blockEntity_onTick(grugEntity.onFns, grugEntity.globals);
    }
}

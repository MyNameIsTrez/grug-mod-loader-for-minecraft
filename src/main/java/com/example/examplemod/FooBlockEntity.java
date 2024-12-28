package com.example.examplemod;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FooBlockEntity extends BlockEntity {
    public GrugEntity grugEntity = new GrugEntity();

    public FooBlockEntity(BlockPos pos, BlockState state) {
        super(ExampleMod.FOO_BLOCK_ENTITY.get(), pos, state);

        GrugFile file = new GrugFile();
        ExampleMod.grug.getEntityFile("foo:foo_block_entity", file);
        // System.out.println("file: " + file);

        List<GrugEntity> grugEntities = Grug.entities.get(file.entity);
        if (grugEntities == null) {
            grugEntities = new ArrayList<GrugEntity>();
            Grug.entities.put(file.entity, grugEntities);
        }
        grugEntities.add(grugEntity);

        // TODO: Let the generator only call callDefineFn() when the entity has fields
        // ExampleMod.grug.callDefineFn(file.defineFn);
        // EntityDefinitions.blockEntity.xyz;

        // TODO: getWorldPosition() needs this put in a map `id_to_entity`, where entity is any Object
        // TODO: Is there some way to constrain it to something more specific than Object, maybe w/ a new type?
        // TODO: How can getWorldPosition() throw when the Object's type is not BlockEntity?
        grugEntity.id = Grug.getNextEntityID();

        grugEntity.globals = new byte[file.globalsSize];

        ExampleMod.grug.callInitGlobals(file.initGlobalsFn, grugEntity.globals, grugEntity.id);

        grugEntity.onFns = file.onFns;
    }

    public void tick() {
        if (!ExampleMod.grug.blockEntity_has_onTick(grugEntity.onFns)) {
            return;
        }

        ExampleMod.grug.blockEntity_onTick(grugEntity.onFns, grugEntity.globals);
    }
}

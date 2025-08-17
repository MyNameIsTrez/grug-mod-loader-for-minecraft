package grug.grugmodloader;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FooBlockEntity extends BlockEntity {
    public GrugEntity grugEntity = new GrugEntity();

    public FooBlockEntity(BlockPos pos, BlockState state) {
        super(GrugModLoader.FOO_BLOCK_ENTITY.get(), pos, state);
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

        List<GrugObject> oldFnEntities = Grug.fnEntities;
        Grug.fnEntities = grugEntity.childEntities;

        grugEntity.id = Grug.addEntity(GrugEntityType.BlockEntity, this);

        GrugFile file = new GrugFile();
        GrugModLoader.grug.getEntityFile("foo:foo_block_entity", file);

        grugEntity.globals = new byte[file.globalsSize];

        GrugModLoader.grug.callInitGlobals(file.initGlobalsFn, grugEntity.globals, grugEntity.id);

        Grug.fnEntities = oldFnEntities;

        grugEntity.onFns = file.onFns;

        spawn();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();

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
        if (!GrugModLoader.grug.BlockEntity_has_on_spawn(grugEntity.onFns)) {
            return;
        }

        List<GrugObject> oldFnEntities = Grug.fnEntities;
        Grug.fnEntities = new ArrayList<>();

        GrugModLoader.grug.BlockEntity_on_spawn(grugEntity.onFns, grugEntity.globals);

        Grug.fnEntities = oldFnEntities;
    }

    public void tick() {
        if (!GrugModLoader.grug.BlockEntity_has_on_tick(grugEntity.onFns)) {
            return;
        }

        List<GrugObject> oldFnEntities = Grug.fnEntities;
        Grug.fnEntities = new ArrayList<>();

        GrugModLoader.grug.BlockEntity_on_tick(grugEntity.onFns, grugEntity.globals);

        Grug.fnEntities = oldFnEntities;
    }
}

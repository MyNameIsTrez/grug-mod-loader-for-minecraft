package grug.grugmodloader;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GameOfLifeBlockEntity extends BlockEntity {
    public GrugEntity grugEntity = new GrugEntity();

    public GameOfLifeBlockEntity(BlockPos pos, BlockState state) {
        super(GrugModLoader.GAME_OF_LIFE_BLOCK_ENTITY.get(), pos, state);
    }

    @Override
    public void onLoad() {
        super.onLoad();

        GrugState state = GrugState.get();

        List<GrugEntity> grugEntities = state.getGrugEntities("foo:game_of_life_block_entity");
        if (grugEntities == null) {
            grugEntities = new ArrayList<GrugEntity>();
            state.putEntities("foo:game_of_life_block_entity", grugEntities);
        }
        grugEntity.entitiesIndex = grugEntities.size();
        grugEntities.add(grugEntity);

        List<GrugObject> oldFnEntities = state.getFnEntities();
        state.setFnEntities(grugEntity.childEntities);

        grugEntity.id = Grug.addEntity(GrugEntityType.BlockEntity, this);

        GrugFile file = new GrugFile();
        GrugModLoader.grug.getEntityFile("foo:game_of_life_block_entity", file);

        grugEntity.globals = new byte[file.globalsSize];

        GrugModLoader.grug.callInitGlobals(file.initGlobalsFn, grugEntity.globals, grugEntity.id);

        state.setFnEntities(oldFnEntities);

        grugEntity.onFns = file.onFns;

        spawn();
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        GrugState.get().removeEntity("foo:game_of_life_block_entity", grugEntity.entitiesIndex);
    }

    public void spawn() {
        if (!GrugModLoader.grug.block_entity_has_on_spawn(grugEntity.onFns)) {
            return;
        }

        GrugState state = GrugState.get();
        List<GrugObject> oldFnEntities = state.getFnEntities();
        state.newFnEntities();

        GrugModLoader.grug.block_entity_on_spawn(grugEntity.onFns, grugEntity.globals);

        state.setFnEntities(oldFnEntities);
    }

    public void tick() {
        if (!GrugModLoader.grug.block_entity_has_on_tick(grugEntity.onFns)) {
            return;
        }

        GrugState state = GrugState.get();
        List<GrugObject> oldFnEntities = state.getFnEntities();
        state.newFnEntities();

        GrugModLoader.grug.block_entity_on_tick(grugEntity.onFns, grugEntity.globals);

        state.setFnEntities(oldFnEntities);
    }

    protected void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block blockIn, BlockPos fromBlockPos, boolean isMoving) {
        if (!GrugModLoader.grug.block_entity_has_on_neighbor_changed(grugEntity.onFns)) {
            return;
        }

        GrugState state = GrugState.get();
        List<GrugObject> oldFnEntities = state.getFnEntities();
        state.newFnEntities();

        long blockStateId = Grug.addEntity(GrugEntityType.BlockState, blockState);
        long levelId = Grug.addEntity(GrugEntityType.Level, level);
        long blockPosId = Grug.addEntity(GrugEntityType.BlockPos, blockPos);
        long blockInId = Grug.addEntity(GrugEntityType.Block, blockIn);
        long fromBlockPosId = Grug.addEntity(GrugEntityType.BlockPos, fromBlockPos);

        GrugModLoader.grug.block_entity_on_neighbor_changed(grugEntity.onFns, grugEntity.globals, blockStateId, levelId, blockPosId, blockInId, fromBlockPosId, isMoving);

        state.setFnEntities(oldFnEntities);
    }
}

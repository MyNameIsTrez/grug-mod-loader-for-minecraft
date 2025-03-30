package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

class GameFunctions {
    private static long getBlockEntityLevel(long blockEntityId) {
        Level level = ExampleMod.grug.getGrugBlockEntity(blockEntityId).getLevel();

        long levelId = Grug.addEntity(EntityType.Level, level);
        Grug.fnEntities.add(levelId);

        return levelId;
    }

    private static long getBlockPosAbove(long blockPosId) {
        // The .above() call allocates a new BlockPos
        BlockPos above = ExampleMod.grug.getBlockPos(blockPosId).above();

        long aboveId = Grug.addEntity(EntityType.BlockPos, above);
        Grug.fnEntities.add(aboveId);

        return aboveId;
    }

    private static long getBlockPosAboveN(long blockPosId, int n) {
        // The .above() call allocates a new BlockPos
        BlockPos above = ExampleMod.grug.getBlockPos(blockPosId).above(n);

        long aboveId = Grug.addEntity(EntityType.BlockPos, above);
        Grug.fnEntities.add(aboveId);

        return aboveId;
    }

    private static long getBlockPosCenter(long blockPosId) {
        // The .getCenter() call allocates a new Vec3
        Vec3 center = ExampleMod.grug.getBlockPos(blockPosId).getCenter();

        long centerId = Grug.addEntity(EntityType.Vec3, center);
        Grug.fnEntities.add(centerId);

        return centerId;
    }

    private static int getBlockPosX(long id) {
        return ExampleMod.grug.getBlockPos(id).getX();
    }

    private static int getBlockPosY(long id) {
        return ExampleMod.grug.getBlockPos(id).getY();
    }

    private static int getBlockPosZ(long id) {
        return ExampleMod.grug.getBlockPos(id).getZ();
    }

    private static float getVec3X(long id) {
        return (float)ExampleMod.grug.getVec3(id).x();
    }

    private static float getVec3Y(long id) {
        return (float)ExampleMod.grug.getVec3(id).y();
    }

    private static float getVec3Z(long id) {
        return (float)ExampleMod.grug.getVec3(id).z();
    }

    private static long getWorldPositionOfBlockEntity(long blockEntityId) {
        return ExampleMod.grug.getGrugBlockEntity(blockEntityId).worldPositionId;
    }

    private static void printF32(float f) {
        Grug.sendMessageToEveryone(Component.literal(Float.toString(f)));
    }

    private static void printI32(int n) {
        Grug.sendMessageToEveryone(Component.literal(Integer.toString(n)));
    }

    private static void printId(long id) {
        Grug.sendMessageToEveryone(Component.literal(Long.toString(id)));
    }

    private static void printLevelName(long levelId) {
        Level level = ExampleMod.grug.getLevel(levelId);

        Grug.sendMessageToEveryone(Component.literal(level.dimensionTypeRegistration().getRegisteredName()));
    }

    private static void printString(String str) {
        Grug.sendMessageToEveryone(Component.literal(str));
    }
}

package com.example.examplemod;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

class GameFunctions {
    private static long getWorldPositionOfBlockEntity(long blockEntityId) {
        return ExampleMod.grug.getGrugBlockEntity(blockEntityId).worldPositionId;
    }

    private static long getBlockPosAbove(long blockPosId) {
        // The .above() call allocates a new BlockPos
        BlockPos above = ExampleMod.grug.getBlockPos(blockPosId).above();

        int entityType = 42;
        long aboveId = Grug.addEntity(entityType, above);
        Grug.fnEntities.add(aboveId);

        return aboveId;
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

    private static void printId(long id) {
        Grug.sendMessageToEveryone(Component.literal(Long.toString(id)));
    }

    private static void printString(String str) {
        Grug.sendMessageToEveryone(Component.literal(str));
    }

    private static void printI32(int n) {
        Grug.sendMessageToEveryone(Component.literal(Integer.toString(n)));
        // System.out.println("n: " + n);
    }
}

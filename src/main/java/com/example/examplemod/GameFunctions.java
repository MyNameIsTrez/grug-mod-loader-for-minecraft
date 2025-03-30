package com.example.examplemod;

import net.minecraft.ChatFormatting;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

class GameFunctions {
    private static long getBlockEntityLevel(long blockEntityId) {
        // This does not allocate a new Level
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

    private static String getItemName(long id) {
        Item item = ExampleMod.grug.getItem(id);

        return item.getName(new ItemStack(item)).getString();
    }

    private static String getItemStackName(long id) {
        ItemStack itemStack = ExampleMod.grug.getItemStack(id);

        return itemStack.getItem().getName(itemStack).getString(); // Returns "Diamond"
        // return itemStack.toString(); // Returns "1 diamond"
        // return itemStack.getHoverName().getString(); // Returns "Diamond"
        // return itemStack.getDisplayName().getString(); // Returns "[Diamond]"
    }

    private static String getLevelName(long id) {
        Level level = ExampleMod.grug.getLevel(id);
        return level.dimensionTypeRegistration().getRegisteredName();
    }

    private static String getResourceLocationString(long id) {
        ResourceLocation resourceLocation;
        try {
            resourceLocation = ExampleMod.grug.getResourceLocation(id);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendErrorMessageToEveryone("get_resource_location_string(): " + assertEntityTypeException.getMessage());
            return "";
        }
        return resourceLocation.toString();
    }

    private static float getVec3X(long id) {
        try {
            return (float)ExampleMod.grug.getVec3(id).x();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendErrorMessageToEveryone("get_vec3_x(): " + assertEntityTypeException.getMessage());
            return 0;
        }
    }

    private static float getVec3Y(long id) {
        try {
            return (float)ExampleMod.grug.getVec3(id).y();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendErrorMessageToEveryone("get_vec3_y(): " + assertEntityTypeException.getMessage());
            return 0;
        }
    }

    private static float getVec3Z(long id) {
        try {
            return (float)ExampleMod.grug.getVec3(id).z();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendErrorMessageToEveryone("get_vec3_z(): " + assertEntityTypeException.getMessage());
            return 0;
        }
    }

    private static long getWorldPositionOfBlockEntity(long blockEntityId) {
        return ExampleMod.grug.getGrugBlockEntity(blockEntityId).worldPositionId;
    }

    private static long item(long resourceLocation) {
        // Translates "minecraft:diamond" to Items.DIAMOND
        Item item = ForgeRegistries.ITEMS.getValue(ExampleMod.grug.getResourceLocation(resourceLocation));

        long itemId = Grug.addEntity(EntityType.Item, item);
        Grug.fnEntities.add(itemId);

        return itemId;
    }

    private static long itemStack(long item) {
        ItemStack itemStack = new ItemStack(ExampleMod.grug.getItem(item));

        long itemStackId = Grug.addEntity(EntityType.ItemStack, itemStack);
        Grug.fnEntities.add(itemStackId);

        return itemStackId;
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

    private static void printString(String str) {
        Grug.sendMessageToEveryone(Component.literal(str));
    }

    private static long resourceLocation(String resourceLocationString) {
        // Allocates a new ResourceLocation
        ResourceLocation resourceLocation;
        try {
            resourceLocation = new ResourceLocation(resourceLocationString);
        } catch (ResourceLocationException resourceLocationException) {
            Grug.sendErrorMessageToEveryone("get_resource_location(\"" + resourceLocationString + "\") has an invalid resource_location_string argument");
            return 0;
        }

        long resourceLocationId = Grug.addEntity(EntityType.ResourceLocation, resourceLocation);
        Grug.fnEntities.add(resourceLocationId);

        return resourceLocationId;
    }
}

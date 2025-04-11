package com.example.examplemod;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.ResourceLocationException;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

class GameFunctions {
    private static long block(long resourceLocation) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Block block;
        try {
            ResourceLocation resourceLocationInstance = ExampleMod.grug.getResourceLocation(resourceLocation);

            if (!ForgeRegistries.BLOCKS.containsKey(resourceLocationInstance)) {
                Grug.sendGameFunctionErrorToEveryone("block", "invalid resource_location argument");
                Grug.gameFunctionErrorHappened = true;
                return 0;
            }

            block = ForgeRegistries.BLOCKS.getValue(resourceLocationInstance);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("block", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        long blockId = Grug.addEntity(EntityType.Block, block);
        Grug.fnEntities.add(blockId);

        return blockId;
    }

    private static long block_pos(int x, int y, int z) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        BlockPos blockPos = new BlockPos(x, y, z);

        long blockPosId = Grug.addEntity(EntityType.BlockPos, blockPos);
        Grug.fnEntities.add(blockPosId);

        return blockPosId;
    }

    private static int block_flag_update_all() {
        return Block.UPDATE_ALL;
    }

    private static long box_i32(int i32) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Integer boxedI32 = i32;

        long boxId = Grug.addEntity(EntityType.BoxedI32, boxedI32);
        Grug.fnEntities.add(boxId);

        return boxId;
    }

    private static void destroy_block(long blockPosId, long levelId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        BlockPos blockPos;
        Level level;
        try {
            blockPos = ExampleMod.grug.getBlockPos(blockPosId);
            level = ExampleMod.grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("destroy_block", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        level.destroyBlock(blockPos, false);
    }

    private static void destroy_and_drop_block(long blockPosId, long levelId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        BlockPos blockPos;
        Level level;
        try {
            blockPos = ExampleMod.grug.getBlockPos(blockPosId);
            level = ExampleMod.grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("destroy_and_drop_block", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        level.destroyBlock(blockPos, true);
    }

    private static long get_block_entity_level(long blockEntityId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Level level;
        try {
            level = ExampleMod.grug.getBlockEntity(blockEntityId).getLevel();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_block_entity_level", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        long levelId = Grug.addEntity(EntityType.Level, level);
        Grug.fnEntities.add(levelId);

        return levelId;
    }

    private static long get_block_pos_above(long blockPosId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        BlockPos above;
        try {
            // The .above() call allocates a new BlockPos
            above = ExampleMod.grug.getBlockPos(blockPosId).above();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_block_pos_above", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        long aboveId = Grug.addEntity(EntityType.BlockPos, above);
        Grug.fnEntities.add(aboveId);

        return aboveId;
    }

    private static long get_block_pos_above_n(long blockPosId, int n) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        BlockPos above;
        try {
            // The .above() call allocates a new BlockPos
            above = ExampleMod.grug.getBlockPos(blockPosId).above(n);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_block_pos_above_n", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        long aboveId = Grug.addEntity(EntityType.BlockPos, above);
        Grug.fnEntities.add(aboveId);

        return aboveId;
    }

    private static long get_block_pos_center(long blockPosId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Vec3 center;
        try {
            // The .getCenter() call allocates a new Vec3
            center = ExampleMod.grug.getBlockPos(blockPosId).getCenter();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_block_pos_center", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        long centerId = Grug.addEntity(EntityType.Vec3, center);
        Grug.fnEntities.add(centerId);

        return centerId;
    }

    private static int get_block_pos_x(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        try {
            return ExampleMod.grug.getBlockPos(id).getX();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_block_pos_x", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }
    }

    private static int get_block_pos_y(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        try {
            return ExampleMod.grug.getBlockPos(id).getY();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_block_pos_y", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }
    }

    private static int get_block_pos_z(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        try {
            return ExampleMod.grug.getBlockPos(id).getZ();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_block_pos_z", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }
    }

    private static long get_block_state(long blockPosId, long levelId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Level level;
        BlockPos blockPos;
        try {
            level = ExampleMod.grug.getLevel(levelId);
            blockPos = ExampleMod.grug.getBlockPos(blockPosId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_block_state", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        BlockState blockState = level.getBlockState(blockPos);

        long blockStateId = Grug.addEntity(EntityType.BlockState, blockState);
        Grug.fnEntities.add(blockStateId);

        return blockStateId;
    }

    private static long get_default_block_state(long blockId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Block block;
        try {
            block = ExampleMod.grug.getBlock(blockId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_default_block_state", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        BlockState blockState = block.defaultBlockState();

        long blockStateId = Grug.addEntity(EntityType.BlockState, blockState);
        Grug.fnEntities.add(blockStateId);

        return blockStateId;
    }

    private static int get_hash_map_size(long hashMapId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        HashMap<Object, Object> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_hash_map_size", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        return hashMap.size();
    }

    private static String get_hash_map_string(long hashMapId) {
        if (Grug.gameFunctionErrorHappened) {
            return "";
        }

        HashMap<Object, Object> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_hash_map_string", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return "";
        }

        return hashMap.toString();
    }

    private static int get_hash_set_size(long hashSetId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        HashSet<Object> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_hash_set_size", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        return hashSet.size();
    }

    private static String get_hash_set_string(long hashSetId) {
        if (Grug.gameFunctionErrorHappened) {
            return "";
        }

        HashSet<Object> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_hash_set_string", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return "";
        }

        return hashSet.toString();
    }

    private static String get_item_name(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return "";
        }

        Item item;
        try {
            item = ExampleMod.grug.getItem(id);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_item_name", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return "";
        }

        return item.getName(new ItemStack(item)).getString();
    }

    private static String get_item_entity_name(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return "";
        }

        try {
            return ExampleMod.grug.getItemEntity(id).getName().getString();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_item_entity_name", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return "";
        }
    }

    private static String get_item_stack_name(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return "";
        }

        ItemStack itemStack;
        try {
            itemStack = ExampleMod.grug.getItemStack(id);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_item_stack_name", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return "";
        }

        return itemStack.getItem().getName(itemStack).getString(); // Returns "Diamond"
        // return itemStack.toString(); // Returns "1 diamond"
        // return itemStack.getHoverName().getString(); // Returns "Diamond"
        // return itemStack.getDisplayName().getString(); // Returns "[Diamond]"
    }

    private static String get_level_name(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return "";
        }

        Level level;
        try {
            level = ExampleMod.grug.getLevel(id);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_level_name", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return "";
        }

        return level.dimensionTypeRegistration().getRegisteredName();
    }

    private static String get_resource_location_string(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return "";
        }

        ResourceLocation resourceLocation;
        try {
            resourceLocation = ExampleMod.grug.getResourceLocation(id);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_resource_location_string", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return "";
        }

        return resourceLocation.toString();
    }

    private static float get_vec3_x(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        try {
            return (float)ExampleMod.grug.getVec3(id).x();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_vec3_x", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }
    }

    private static float get_vec3_y(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        try {
            return (float)ExampleMod.grug.getVec3(id).y();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_vec3_y", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }
    }

    private static float get_vec3_z(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        try {
            return (float)ExampleMod.grug.getVec3(id).z();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_vec3_z", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }
    }

    private static long get_world_position_of_block_entity(long blockEntityId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        try {
            return ExampleMod.grug.getBlockEntity(blockEntityId).worldPositionId;
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_world_position_of_block_entity", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }
    }

    private static long hash_map() {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        HashMap<Object, Object> hashMap = new HashMap<>();

        long hashMapId = Grug.addEntity(EntityType.HashMap, hashMap);
        Grug.fnEntities.add(hashMapId);

        return hashMapId;
    }

    private static void hash_map_clear(long hashMapId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashMap<Object, Object> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_clear", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        hashMap.clear();
    }

    private static void hash_map_copy(long hashMapFromId, long hashMapToId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashMap<Object, Object> hashMapFrom;
        HashMap<Object, Object> hashMapTo;
        try {
            hashMapFrom = ExampleMod.grug.getHashMap(hashMapFromId);
            hashMapTo = ExampleMod.grug.getHashMap(hashMapToId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_copy", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        hashMapTo.clear();
        hashMapTo.putAll(hashMapFrom);
    }

    private static boolean hash_map_has_key(long hashMapId, long key) {
        if (Grug.gameFunctionErrorHappened) {
            return false;
        }

        HashMap<Object, Object> hashMap;
        Object object;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
            object = ExampleMod.grug.getObject(key);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_has_key", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return false;
        }

        return hashMap.containsKey(object);
    }

    private static void hash_map_put(long hashMapId, long keyId, long valueId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashMap<Object, Object> hashMap;
        Object keyObject;
        Object valueObject;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
            keyObject = ExampleMod.grug.getObject(keyId);
            valueObject = ExampleMod.grug.getObject(valueId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_put", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        hashMap.put(keyObject, valueObject);
    }

    private static void hash_map_remove_key(long hashMapId, long value) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashMap<Object, Object> hashMap;
        Object object;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
            object = ExampleMod.grug.getObject(value);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_remove_key", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        hashMap.remove(object);
    }

    private static long hash_set() {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        HashSet<Object> hashSet = new HashSet<>();

        long hashSetId = Grug.addEntity(EntityType.HashSet, hashSet);
        Grug.fnEntities.add(hashSetId);

        return hashSetId;
    }

    private static void hash_set_add(long hashSetId, long value) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashSet<Object> hashSet;
        Object object;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
            object = ExampleMod.grug.getObject(value);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_add", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        hashSet.add(object);
    }

    private static void hash_set_clear(long hashSetId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashSet<Object> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_clear", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        hashSet.clear();
    }

    private static void hash_set_copy(long hashSetFromId, long hashSetToId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashSet<Object> hashSetFrom;
        HashSet<Object> hashSetTo;
        try {
            hashSetFrom = ExampleMod.grug.getHashSet(hashSetFromId);
            hashSetTo = ExampleMod.grug.getHashSet(hashSetToId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_copy", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        hashSetTo.clear();
        hashSetTo.addAll(hashSetFrom);
    }

    private static boolean hash_set_has(long hashSetId, long value) {
        if (Grug.gameFunctionErrorHappened) {
            return false;
        }

        HashSet<Object> hashSet;
        Object object;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
            object = ExampleMod.grug.getObject(value);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_has", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return false;
        }

        return hashSet.contains(object);
    }

    private static void hash_set_remove(long hashSetId, long value) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashSet<Object> hashSet;
        Object object;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
            object = ExampleMod.grug.getObject(value);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_remove", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        hashSet.remove(object);
    }

    private static boolean is_air(long blockStateId) {
        if (Grug.gameFunctionErrorHappened) {
            return false;
        }

        BlockState blockState;
        try {
            blockState = ExampleMod.grug.getBlockState(blockStateId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("is_air", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return false;
        }

        return blockState.isAir();
    }

    private static boolean is_client_side(long levelId) {
        if (Grug.gameFunctionErrorHappened) {
            return false;
        }

        Level level;
        try {
            level = ExampleMod.grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("is_client_side", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return false;
        }

        return level.isClientSide();
    }

    private static long item(long resourceLocation) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Item item;
        try {
            ResourceLocation resourceLocationInstance = ExampleMod.grug.getResourceLocation(resourceLocation);

            if (!ForgeRegistries.ITEMS.containsKey(resourceLocationInstance)) {
                Grug.sendGameFunctionErrorToEveryone("item", "invalid resource_location argument");
                Grug.gameFunctionErrorHappened = true;
                return 0;
            }

            item = ForgeRegistries.ITEMS.getValue(resourceLocationInstance);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("item", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        long itemId = Grug.addEntity(EntityType.Item, item);
        Grug.fnEntities.add(itemId);

        return itemId;
    }

    private static long item_entity(long level, float x, float y, float z, long itemStack) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        ItemEntity itemEntity;
        try {
            itemEntity = new ItemEntity(ExampleMod.grug.getLevel(level), x, y, z, ExampleMod.grug.getItemStack(itemStack));
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("item_entity", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        long itemEntityId = Grug.addEntity(EntityType.ItemEntity, itemEntity);
        Grug.fnEntities.add(itemEntityId);

        return itemEntityId;
    }

    private static long item_stack(long item) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        ItemStack itemStack;
        try {
            itemStack = new ItemStack(ExampleMod.grug.getItem(item));
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("item_stack", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        long itemStackId = Grug.addEntity(EntityType.ItemStack, itemStack);
        Grug.fnEntities.add(itemStackId);

        return itemStackId;
    }

    private static boolean iterating(long iteratorId) {
        if (Grug.gameFunctionErrorHappened) {
            return false;
        }

        GrugIterator grugIterator;
        try {
            grugIterator = ExampleMod.grug.getIterator(iteratorId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iterating", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return false;
        }

        return grugIterator.iterator.hasNext();
    }

    private static long iteration(long iteratorId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        GrugIterator grugIterator;
        try {
            grugIterator = ExampleMod.grug.getIterator(iteratorId);

            if (grugIterator.containerType != EntityType.HashSet) {
                throw new AssertEntityTypeException(grugIterator.containerType, EntityType.HashSet);
            }
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iteration", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        Object object = grugIterator.iterator.next();

        long containedId = Grug.addEntity(grugIterator.containedType, object);
        Grug.fnEntities.add(containedId);

        return containedId;
    }

    private static long iteration_key(long iteratorId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        GrugIterator grugIterator;
        try {
            grugIterator = ExampleMod.grug.getIterator(iteratorId);

            if (grugIterator.containerType != EntityType.HashMap) {
                throw new AssertEntityTypeException(grugIterator.containerType, EntityType.HashMap);
            }
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iteration_key", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        @SuppressWarnings("unchecked")
        Object object = ((Entry<Object, Object>)grugIterator.iterator.next()).getKey();

        long containedId = Grug.addEntity(grugIterator.containedType, object);
        Grug.fnEntities.add(containedId);

        return containedId;
    }

    private static long iteration_value(long iteratorId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        GrugIterator grugIterator;
        try {
            grugIterator = ExampleMod.grug.getIterator(iteratorId);

            if (grugIterator.containerType != EntityType.HashMap) {
                throw new AssertEntityTypeException(grugIterator.containerType, EntityType.HashMap);
            }
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iteration_value", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        @SuppressWarnings("unchecked")
        Object object = ((Entry<Object, Object>)grugIterator.iterator.next()).getValue();

        long containedId = Grug.addEntity(grugIterator.containedType, object);
        Grug.fnEntities.add(containedId);

        return containedId;
    }

    private static long iterator(long iterable) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        EntityType containerType = Grug.getEntityType(iterable);
        Iterator<?> iterator;

        try {
            if (Grug.isEntityTypeInstanceOf(containerType, EntityType.HashMap)) {
                iterator = ExampleMod.grug.getHashMap(iterable).entrySet().iterator();
            } else if (Grug.isEntityTypeInstanceOf(containerType, EntityType.HashSet)) {
                iterator = ExampleMod.grug.getHashSet(iterable).iterator();
            } else {
                Grug.sendGameFunctionErrorToEveryone("iterator", "Expected an iterable, but got " + StringUtils.getSnakeCase(containerType));
                Grug.gameFunctionErrorHappened = true;
                return 0;
            }
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iterator", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        // TODO: I think I'll need to create GrugHashMap and GrugHashSet, solely so they can save the containedType
        GrugIterator grugIterator = new GrugIterator(iterator, containerType, containedType);

        long grugIteratorId = Grug.addEntity(EntityType.Iterator, grugIterator);

        Grug.fnEntities.add(grugIteratorId);

        return grugIteratorId;
    }

    private static void place_block(long blockStateId, long blockPosId, int flags, long levelId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        BlockState blockState;
        BlockPos blockPos;
        Level level;
        try {
            blockState = ExampleMod.grug.getBlockState(blockStateId);
            blockPos = ExampleMod.grug.getBlockPos(blockPosId);
            level = ExampleMod.grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("place_block", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        level.setBlock(blockPos, blockState, flags);
    }

    private static void print_bool(boolean b) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(Boolean.toString(b)));
    }

    private static void print_f32(float f) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(Float.toString(f)));
    }

    private static void print_i32(int n) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(Integer.toString(n)));
    }

    private static void print_id(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(Long.toString(id)));
    }

    private static void print_string(String str) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(str));
    }

    private static void remove_block(long blockPosId, long levelId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        BlockPos blockPos;
        Level level;
        try {
            blockPos = ExampleMod.grug.getBlockPos(blockPosId);
            level = ExampleMod.grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("remove_block", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        level.removeBlock(blockPos, false);
    }

    private static void remove_moving_block(long blockPosId, long levelId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        BlockPos blockPos;
        Level level;
        try {
            blockPos = ExampleMod.grug.getBlockPos(blockPosId);
            level = ExampleMod.grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("remove_moving_block", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        level.removeBlock(blockPos, true);
    }

    private static long resource_location(String resourceLocationString) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        ResourceLocation resourceLocation;
        try {
            resourceLocation = new ResourceLocation(resourceLocationString);
        } catch (ResourceLocationException resourceLocationException) {
            Grug.sendGameFunctionErrorToEveryone("resource_location", "invalid resource_location_string argument");
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        long resourceLocationId = Grug.addEntity(EntityType.ResourceLocation, resourceLocation);
        Grug.fnEntities.add(resourceLocationId);

        return resourceLocationId;
    }

    private static void set_entity_delta_movement(long entity, long vec3) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        Entity entityInstance;
        Vec3 vec3Instance;
        try {
            entityInstance = ExampleMod.grug.getEntity(entity);
            vec3Instance = ExampleMod.grug.getVec3(vec3);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("set_item_entity_delta_movement", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        entityInstance.setDeltaMovement(vec3Instance);
    }

    private static void spawn_entity(long entity, long level) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        Entity entityInstance;
        Level levelInstance;
        try {
            entityInstance = ExampleMod.grug.getEntity(entity);
            levelInstance = ExampleMod.grug.getLevel(level);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("spawn_entity", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        levelInstance.addFreshEntity(entityInstance);
    }

    private static int unbox_i32(long box) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        return ExampleMod.grug.getBoxedI32(box);
    }

    private static long vec3_zero() {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        long vec3ZeroId = Grug.addEntity(EntityType.Vec3, Vec3.ZERO);
        Grug.fnEntities.add(vec3ZeroId);
        return vec3ZeroId;
    }
}

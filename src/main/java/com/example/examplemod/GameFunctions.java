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
    public static long block(long resourceLocation) {
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

    public static long block_pos(int x, int y, int z) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        BlockPos blockPos = new BlockPos(x, y, z);

        long blockPosId = Grug.addEntity(EntityType.BlockPos, blockPos);
        Grug.fnEntities.add(blockPosId);

        return blockPosId;
    }

    public static int block_flag_update_all() {
        return Block.UPDATE_ALL;
    }

    public static long box_i32(int i32) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Integer boxedI32 = i32;

        long boxId = Grug.addEntity(EntityType.BoxedI32, boxedI32);
        Grug.fnEntities.add(boxId);

        return boxId;
    }

    public static void destroy_block(long blockPosId, long levelId) {
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

    public static void destroy_and_drop_block(long blockPosId, long levelId) {
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

    public static long get_block_entity_level(long blockEntityId) {
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

    public static long get_block_pos_above(long blockPosId) {
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

    public static long get_block_pos_above_n(long blockPosId, int n) {
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

    public static long get_block_pos_center(long blockPosId) {
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

    public static int get_block_pos_x(long id) {
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

    public static int get_block_pos_y(long id) {
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

    public static int get_block_pos_z(long id) {
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

    public static long get_block_state(long blockPosId, long levelId) {
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

    public static long get_default_block_state(long blockId) {
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

    public static int get_hash_map_size(long hashMapId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        HashMap<Long, Long> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_hash_map_size", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        return hashMap.size();
    }

    public static String get_hash_map_string(long hashMapId) {
        if (Grug.gameFunctionErrorHappened) {
            return "";
        }

        HashMap<Long, Long> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_hash_map_string", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return "";
        }

        return hashMap.toString();
    }

    public static int get_hash_set_size(long hashSetId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_hash_set_size", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        return hashSet.size();
    }

    public static String get_hash_set_string(long hashSetId) {
        if (Grug.gameFunctionErrorHappened) {
            return "";
        }

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_hash_set_string", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return "";
        }

        return hashSet.toString();
    }

    public static String get_item_name(long id) {
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

    public static String get_item_entity_name(long id) {
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

    public static String get_item_stack_name(long id) {
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

    public static String get_level_name(long id) {
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

    public static String get_resource_location_string(long id) {
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

    public static float get_vec3_x(long id) {
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

    public static float get_vec3_y(long id) {
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

    public static float get_vec3_z(long id) {
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

    public static long get_world_position_of_block_entity(long blockEntityId) {
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

    public static long hash_map() {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        HashMap<Long, Long> hashMap = new HashMap<>();

        long hashMapId = Grug.addEntity(EntityType.HashMap, hashMap);
        Grug.fnEntities.add(hashMapId);

        Grug.newHashMapKeyObjects(hashMapId);

        return hashMapId;
    }

    public static void hash_map_clear(long hashMapId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashMap<Long, Long> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_clear", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        if (Grug.globalEntities.contains(hashMapId)) {
            for (Entry<Long, Long> entry : hashMap.entrySet()) {
                Grug.globalEntities.remove(entry.getKey());
                Grug.globalEntities.remove(entry.getValue());
            }
        } else {
            for (Entry<Long, Long> entry : hashMap.entrySet()) {
                Grug.fnEntities.remove(entry.getKey());
                Grug.fnEntities.remove(entry.getValue());
            }
        }

        Grug.removeEntities(hashMap.values());
        hashMap.clear();
        Grug.getHashMapKeyObjects(hashMapId).clear();
    }

    public static void hash_map_copy(long hashMapFromId, long hashMapToId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashMap<Long, Long> hashMapFrom;
        HashMap<Long, Long> hashMapTo;
        try {
            hashMapFrom = ExampleMod.grug.getHashMap(hashMapFromId);
            hashMapTo = ExampleMod.grug.getHashMap(hashMapToId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_copy", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        if (Grug.globalEntities.contains(hashMapToId)) {
            for (Entry<Long, Long> entry : hashMapTo.entrySet()) {
                Grug.globalEntities.remove(entry.getKey());
                Grug.globalEntities.remove(entry.getValue());
            }
        } else {
            for (Entry<Long, Long> entry : hashMapTo.entrySet()) {
                Grug.fnEntities.remove(entry.getKey());
                Grug.fnEntities.remove(entry.getValue());
            }
        }

        Grug.removeEntities(hashMapTo.keySet());
        Grug.removeEntities(hashMapTo.values());
        hashMapTo.clear();

        HashSet<Object> hashMapToKeyObjects = Grug.getHashMapKeyObjects(hashMapToId);
        hashMapToKeyObjects.clear();

        for (Entry<Long, Long> entry : hashMapFrom.entrySet()) {
            Long keyId = entry.getKey();
            Long valueId = entry.getValue();

            Object keyObject = ExampleMod.grug.getObject(keyId);
            Object valueObject = ExampleMod.grug.getObject(valueId);

            long newKeyId = Grug.addEntity(Grug.getEntityType(keyId), keyObject);
            if (Grug.globalEntities.contains(hashMapToId)) {
                Grug.globalEntities.add(newKeyId);
            } else {
                Grug.fnEntities.add(newKeyId);
            }

            long newValueId = Grug.addEntity(Grug.getEntityType(valueId), valueObject);
            if (Grug.globalEntities.contains(hashMapToId)) {
                Grug.globalEntities.add(newValueId);
            } else {
                Grug.fnEntities.add(newValueId);
            }

            hashMapTo.put(newKeyId, newValueId);

            hashMapToKeyObjects.add(keyObject);
        }
    }

    public static long hash_map_get(long hashMapId, long keyId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        HashMap<Long, Long> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_get", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        Long key = hashMap.get(keyId);

        if (key == null) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_get", "Invalid key");
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        return key;
    }

    public static boolean hash_map_has_key(long hashMapId, long key) {
        if (Grug.gameFunctionErrorHappened) {
            return false;
        }

        Object object;
        try {
            object = ExampleMod.grug.getObject(key);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_has_key", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return false;
        }

        return Grug.getHashMapKeyObjects(hashMapId).contains(object);
    }

    public static void hash_map_put(long hashMapId, long keyId, long valueId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashMap<Long, Long> hashMap;
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

        HashSet<Object> hashMapKeyObjects = Grug.getHashMapKeyObjects(hashMapId);

        if (hashMapKeyObjects.contains(keyObject)) {
            // For simplicity and as an optimization, we don't replace any existing key Object

            long newValueId = Grug.addEntity(Grug.getEntityType(valueId), valueObject);
            if (Grug.globalEntities.contains(hashMapId)) {
                Grug.globalEntities.add(newValueId);
            } else {
                Grug.fnEntities.add(newValueId);
            }

            hashMap.put(keyId, newValueId);
        } else {
            hashMapKeyObjects.add(keyObject);

            long newKeyId = Grug.addEntity(Grug.getEntityType(keyId), keyObject);
            if (Grug.globalEntities.contains(hashMapId)) {
                Grug.globalEntities.add(newKeyId);
            } else {
                Grug.fnEntities.add(newKeyId);
            }

            long newValueId = Grug.addEntity(Grug.getEntityType(valueId), valueObject);
            if (Grug.globalEntities.contains(hashMapId)) {
                Grug.globalEntities.add(newValueId);
            } else {
                Grug.fnEntities.add(newValueId);
            }

            hashMap.put(newKeyId, newValueId);
        }
    }

    public static void hash_map_remove_key(long hashMapId, long keyId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashMap<Long, Long> hashMap;
        Object keyObject;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
            keyObject = ExampleMod.grug.getObject(keyId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_remove_key", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        Long valueId = hashMap.get(keyId);

        if (Grug.globalEntities.contains(hashMapId)) {
            Grug.globalEntities.remove(keyId);
            Grug.globalEntities.remove(valueId);
        } else {
            Grug.fnEntities.remove(keyId);
            Grug.fnEntities.remove(valueId);
        }

        Long value = hashMap.remove(keyId);

        if (value == null) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_remove_key", "Invalid key");
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        Grug.getHashMapKeyObjects(hashMapId).remove(keyObject);
    }

    public static long hash_set() {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        HashSet<Long> hashSet = new HashSet<>();

        long hashSetId = Grug.addEntity(EntityType.HashSet, hashSet);
        Grug.fnEntities.add(hashSetId);

        Grug.newHashSetObjects(hashSetId);

        return hashSetId;
    }

    public static void hash_set_add(long hashSetId, long valueId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashSet<Long> hashSet;
        Object valueObject;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
            valueObject = ExampleMod.grug.getObject(valueId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_add", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        HashSet<Object> hashSetObjects = Grug.getHashSetObjects(hashSetId);

        if (!hashSetObjects.contains(valueObject)) {
            hashSetObjects.add(valueObject);

            long newValueId = Grug.addEntity(Grug.getEntityType(valueId), valueObject);
            if (Grug.globalEntities.contains(hashSetId)) {
                Grug.globalEntities.add(newValueId);
            } else {
                Grug.fnEntities.add(newValueId);
            }

            hashSet.add(newValueId);
        }
    }

    public static void hash_set_clear(long hashSetId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_clear", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        if (Grug.globalEntities.contains(hashSetId)) {
            for (Long id : hashSet) {
                Grug.globalEntities.remove(id);
            }
        } else {
            for (Long id : hashSet) {
                Grug.fnEntities.remove(id);
            }
        }

        Grug.removeEntities(hashSet);
        hashSet.clear();
        Grug.getHashSetObjects(hashSetId).clear();
    }

    public static void hash_set_copy(long hashSetFromId, long hashSetToId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashSet<Long> hashSetFrom;
        HashSet<Long> hashSetTo;
        try {
            hashSetFrom = ExampleMod.grug.getHashSet(hashSetFromId);
            hashSetTo = ExampleMod.grug.getHashSet(hashSetToId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_copy", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        if (Grug.globalEntities.contains(hashSetToId)) {
            for (Long id : hashSetTo) {
                Grug.globalEntities.remove(id);
            }
        } else {
            for (Long id : hashSetTo) {
                Grug.fnEntities.remove(id);
            }
        }

        Grug.removeEntities(hashSetTo);
        hashSetTo.clear();

        HashSet<Object> hashSetToObjects = Grug.getHashSetObjects(hashSetToId);
        hashSetToObjects.clear();

        for (Long valueId : hashSetFrom) {
            Object valueObject = ExampleMod.grug.getObject(valueId);

            long newValueId = Grug.addEntity(Grug.getEntityType(valueId), valueObject);
            if (Grug.globalEntities.contains(hashSetToId)) {
                Grug.globalEntities.add(newValueId);
            } else {
                Grug.fnEntities.add(newValueId);
            }
            hashSetTo.add(newValueId);

            hashSetToObjects.add(valueObject);
        }
    }

    public static boolean hash_set_has(long hashSetId, long value) {
        if (Grug.gameFunctionErrorHappened) {
            return false;
        }

        Object object;
        try {
            object = ExampleMod.grug.getObject(value);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_has", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return false;
        }

        return Grug.getHashSetObjects(hashSetId).contains(object);
    }

    public static void hash_set_remove(long hashSetId, long valueId) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        HashSet<Long> hashSet;
        Object object;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
            object = ExampleMod.grug.getObject(valueId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_remove", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        boolean containedValue = hashSet.remove(valueId);

        if (!containedValue) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_remove", "hash_set did not contain value");
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        if (Grug.globalEntities.contains(hashSetId)) {
            Grug.globalEntities.remove(valueId);
        } else {
            Grug.fnEntities.remove(valueId);
        }

        Grug.getHashSetObjects(hashSetId).remove(object);
    }

    public static boolean is_air(long blockStateId) {
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

    public static boolean is_client_side(long levelId) {
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

    public static long item(long resourceLocation) {
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

    public static long item_entity(long level, float x, float y, float z, long itemStack) {
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

    public static long item_stack(long item) {
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

    public static boolean iterating(long iteratorId) {
        if (Grug.gameFunctionErrorHappened) {
            return false;
        }

        Iterator iterator;
        try {
            iterator = ExampleMod.grug.getHashMapIterator(iteratorId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iterating", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return false;
        }

        return iterator.hasNext();
    }

    public static long iteration(long iteratorId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        EntityType iteratorType = Grug.getEntityType(iteratorId);
        Iterator<?> iterator;
        try {
            if (iteratorType == EntityType.HashMapIterator) {
                iterator = ExampleMod.grug.getHashMapIterator(iteratorId);
            } else if (iteratorType == EntityType.HashMapIterator) {
                iterator = ExampleMod.grug.getHashSetIterator(iteratorId);
            } else {
                Grug.sendGameFunctionErrorToEveryone("iterator", "Expected an iterator, but got " + StringUtils.getSnakeCase(iteratorType));
                Grug.gameFunctionErrorHappened = true;
                return 0;
            }
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iteration", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        if (iteratorType == EntityType.HashMapIterator) {
            // TODO: Add a test that asserts whether we do or don't allow saving
            //       an Entry<Long, Long> in a global. I'm not sure if Java by itself even allows it?

            Entry<Long, Long> iteration = (Entry<Long, Long>)iterator.next();

            long iterationId = Grug.addEntity(EntityType.Iteration, iteration);
            Grug.fnEntities.add(iterationId);

            return iterationId;
        } else if (iteratorType == EntityType.HashSetIterator) {
            return (long)iterator.next();
        }

        throw new RuntimeException("iteration() failed to handle an iterator type, which means iteration() needs to be updated");
    }

    public static long iteration_key(long iterationId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Entry<Long, Long> iteration;
        try {
            iteration = ExampleMod.grug.getIteration(iterationId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iteration_key", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        return iteration.getKey();
    }

    public static long iteration_value(long iterationId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Entry<Long, Long> iteration;
        try {
            iteration = ExampleMod.grug.getIteration(iterationId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iteration_value", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        return iteration.getValue();
    }

    public static long iterator(long iterable) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        EntityType containerType = Grug.getEntityType(iterable);
        Iterator<?> iterator;
        long grugIteratorId;

        try {
            if (containerType == EntityType.HashMap) {
                iterator = ExampleMod.grug.getHashMap(iterable).entrySet().iterator();
                grugIteratorId = Grug.addEntity(EntityType.HashMapIterator, iterator);
            } else if (containerType == EntityType.HashSet) {
                iterator = ExampleMod.grug.getHashSet(iterable).iterator();
                grugIteratorId = Grug.addEntity(EntityType.HashSetIterator, iterator);
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

        Grug.fnEntities.add(grugIteratorId);

        return grugIteratorId;
    }

    public static void place_block(long blockStateId, long blockPosId, int flags, long levelId) {
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

    public static void print_bool(boolean b) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(Boolean.toString(b)));
    }

    public static void print_f32(float f) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(Float.toString(f)));
    }

    public static void print_i32(int n) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(Integer.toString(n)));
    }

    public static void print_id(long id) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(Long.toString(id)));
    }

    public static void print_string(String str) {
        if (Grug.gameFunctionErrorHappened) {
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(str));
    }

    public static void remove_block(long blockPosId, long levelId) {
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

    public static void remove_moving_block(long blockPosId, long levelId) {
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

    public static long resource_location(String resourceLocationString) {
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

    public static void set_entity_delta_movement(long entity, long vec3) {
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

    public static void spawn_entity(long entity, long level) {
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

    public static int unbox_i32(long box) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        return ExampleMod.grug.getBoxedI32(box);
    }

    public static long vec3_zero() {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        long vec3ZeroId = Grug.addEntity(EntityType.Vec3, Vec3.ZERO);
        Grug.fnEntities.add(vec3ZeroId);
        return vec3ZeroId;
    }
}

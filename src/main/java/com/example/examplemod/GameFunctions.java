package com.example.examplemod;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import com.example.examplemod.GrugIterator.IterableType;

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
        ExampleMod.logger.debug("block(resourceLocation={})", resourceLocation);

        Block block;
        try {
            ResourceLocation resourceLocationInstance = ExampleMod.grug.getResourceLocation(resourceLocation);

            if (!ForgeRegistries.BLOCKS.containsKey(resourceLocationInstance)) {
                Grug.gameFunctionErrorHappened("block", "invalid resource_location argument");
                return -1;
            }

            block = ForgeRegistries.BLOCKS.getValue(resourceLocationInstance);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("block", assertEntityTypeException.getMessage());
            return -1;
        }

        long blockId = Grug.addEntity(EntityType.Block, block);
        Grug.fnEntities.add(blockId);

        ExampleMod.logger.debug("Returning {}", blockId);
        return blockId;
    }

    public static long block_pos(int x, int y, int z) {
        ExampleMod.logger.debug("block_pos(x={}, y={}, z={})", x, y, z);

        BlockPos blockPos = new BlockPos(x, y, z);

        long blockPosId = Grug.addEntity(EntityType.BlockPos, blockPos);
        Grug.fnEntities.add(blockPosId);

        ExampleMod.logger.debug("Returning {}", blockPosId);
        return blockPosId;
    }

    public static int block_flag_update_all() {
        ExampleMod.logger.debug("block_flag_update_all()");
        int flag = Block.UPDATE_ALL;
        ExampleMod.logger.debug("Returning {}", flag);
        return flag;
    }

    public static long box_i32(int i32) {
        ExampleMod.logger.debug("box_i32(i32={})", i32);

        Integer boxedI32 = i32;

        long boxId = Grug.addEntity(EntityType.BoxedI32, boxedI32);
        Grug.fnEntities.add(boxId);

        ExampleMod.logger.debug("Returning {}", boxId);
        return boxId;
    }

    public static void destroy_block(long blockPosId, long levelId) {
        ExampleMod.logger.debug("destroy_block(blockPosId={}, levelId={})", blockPosId, levelId);

        BlockPos blockPos;
        Level level;
        try {
            blockPos = ExampleMod.grug.getBlockPos(blockPosId);
            level = ExampleMod.grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("destroy_block", assertEntityTypeException.getMessage());
            return;
        }

        level.destroyBlock(blockPos, false);
    }

    public static void destroy_and_drop_block(long blockPosId, long levelId) {
        ExampleMod.logger.debug("destroy_and_drop_block(blockPosId={}, levelId={})", blockPosId, levelId);

        BlockPos blockPos;
        Level level;
        try {
            blockPos = ExampleMod.grug.getBlockPos(blockPosId);
            level = ExampleMod.grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("destroy_and_drop_block", assertEntityTypeException.getMessage());
            return;
        }

        level.destroyBlock(blockPos, true);
    }

    public static long entry_key(long entryId) {
        ExampleMod.logger.debug("entry_key(entryId={})", entryId);

        GrugEntry grugEntry;
        try {
            grugEntry = ExampleMod.grug.getEntry(entryId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("entry_key", assertEntityTypeException.getMessage());
            return -1;
        }

        Long key = grugEntry.entry.getKey();
        ExampleMod.logger.debug("Returning {}", key);
        return key;
    }

    public static void entry_set_value(long entryId, long valueId) {
        ExampleMod.logger.debug("entry_set_value(entryId={}, valueId={})", entryId, valueId);

        GrugEntry grugEntry;
        try {
            grugEntry = ExampleMod.grug.getEntry(entryId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("entry_set_value", assertEntityTypeException.getMessage());
            return;
        }

        // The below code removes the old entity value, replacing it with a new entity.

        Object valueObject = ExampleMod.grug.getObject(valueId);

        long newValueId = Grug.addEntity(Grug.getEntityType(valueId), valueObject);

        Long oldValueId = grugEntry.entry.setValue(newValueId);
        assert oldValueId != null;

        if (grugEntry.isHashMapGlobal) {
            Grug.globalEntities.remove(oldValueId);
            Grug.globalEntities.add(newValueId);
        } else {
            Grug.fnEntities.remove(oldValueId);
            Grug.fnEntities.add(newValueId);
        }
    }

    public static long entry_value(long entryId) {
        ExampleMod.logger.debug("entry_value(entryId={})", entryId);

        GrugEntry grugEntry;
        try {
            grugEntry = ExampleMod.grug.getEntry(entryId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("entry_value", assertEntityTypeException.getMessage());
            return -1;
        }

        Long value = grugEntry.entry.getValue();
        ExampleMod.logger.debug("Returning {}", value);
        return value;
    }

    public static long get_block_entity_level(long blockEntityId) {
        ExampleMod.logger.debug("get_block_entity_level(blockEntityId={})", blockEntityId);

        Level level;
        try {
            level = ExampleMod.grug.getBlockEntity(blockEntityId).getLevel();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_entity_level", assertEntityTypeException.getMessage());
            return -1;
        }

        long levelId = Grug.addEntity(EntityType.Level, level);
        Grug.fnEntities.add(levelId);

        ExampleMod.logger.debug("Returning {}", levelId);
        return levelId;
    }

    public static long get_block_pos_above(long blockPosId) {
        ExampleMod.logger.debug("get_block_pos_above(blockPosId={})", blockPosId);

        BlockPos above;
        try {
            // The .above() call allocates a new BlockPos
            above = ExampleMod.grug.getBlockPos(blockPosId).above();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_above", assertEntityTypeException.getMessage());
            return -1;
        }

        long aboveId = Grug.addEntity(EntityType.BlockPos, above);
        Grug.fnEntities.add(aboveId);

        ExampleMod.logger.debug("Returning {}", aboveId);
        return aboveId;
    }

    public static long get_block_pos_above_n(long blockPosId, int n) {
        ExampleMod.logger.debug("get_block_pos_above_n(blockPosId={}, n={})", blockPosId, n);

        BlockPos above;
        try {
            // The .above() call allocates a new BlockPos
            above = ExampleMod.grug.getBlockPos(blockPosId).above(n);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_above_n", assertEntityTypeException.getMessage());
            return -1;
        }

        long aboveId = Grug.addEntity(EntityType.BlockPos, above);
        Grug.fnEntities.add(aboveId);

        ExampleMod.logger.debug("Returning {}", aboveId);
        return aboveId;
    }

    public static long get_block_pos_center(long blockPosId) {
        ExampleMod.logger.debug("get_block_pos_center(blockPosId={})", blockPosId);

        Vec3 center;
        try {
            // The .getCenter() call allocates a new Vec3
            center = ExampleMod.grug.getBlockPos(blockPosId).getCenter();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_center", assertEntityTypeException.getMessage());
            return -1;
        }

        long centerId = Grug.addEntity(EntityType.Vec3, center);
        Grug.fnEntities.add(centerId);

        ExampleMod.logger.debug("Returning {}", centerId);
        return centerId;
    }

    public static int get_block_pos_x(long id) {
        ExampleMod.logger.debug("get_block_pos_x(id={})", id);

        int x;
        try {
            x = ExampleMod.grug.getBlockPos(id).getX();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_x", assertEntityTypeException.getMessage());
            return -1;
        }

        ExampleMod.logger.debug("Returning {}", x);
        return x;
    }

    public static int get_block_pos_y(long id) {
        ExampleMod.logger.debug("get_block_pos_y(id={})", id);

        int y;
        try {
            y = ExampleMod.grug.getBlockPos(id).getY();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_y", assertEntityTypeException.getMessage());
            return -1;
        }

        ExampleMod.logger.debug("Returning {}", y);
        return y;
    }

    public static int get_block_pos_z(long id) {
        ExampleMod.logger.debug("get_block_pos_z(id={})", id);

        int z;
        try {
            z = ExampleMod.grug.getBlockPos(id).getZ();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_z", assertEntityTypeException.getMessage());
            return -1;
        }

        ExampleMod.logger.debug("Returning {}", z);
        return z;
    }

    public static long get_block_state(long blockPosId, long levelId) {
        ExampleMod.logger.debug("get_block_state(blockPosId={}, levelId={})", blockPosId, levelId);

        Level level;
        BlockPos blockPos;
        try {
            level = ExampleMod.grug.getLevel(levelId);
            blockPos = ExampleMod.grug.getBlockPos(blockPosId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_state", assertEntityTypeException.getMessage());
            return -1;
        }

        BlockState blockState = level.getBlockState(blockPos);

        long blockStateId = Grug.addEntity(EntityType.BlockState, blockState);
        Grug.fnEntities.add(blockStateId);

        ExampleMod.logger.debug("Returning {}", blockStateId);
        return blockStateId;
    }

    public static long get_default_block_state(long blockId) {
        ExampleMod.logger.debug("get_default_block_state(blockId={})", blockId);

        Block block;
        try {
            block = ExampleMod.grug.getBlock(blockId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_default_block_state", assertEntityTypeException.getMessage());
            return -1;
        }

        BlockState blockState = block.defaultBlockState();

        long blockStateId = Grug.addEntity(EntityType.BlockState, blockState);
        Grug.fnEntities.add(blockStateId);

        ExampleMod.logger.debug("Returning {}", blockStateId);
        return blockStateId;
    }

    public static int get_hash_map_size(long hashMapId) {
        ExampleMod.logger.debug("get_hash_map_size(hashMapId={})", hashMapId);

        HashMap<Long, Long> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_hash_map_size", assertEntityTypeException.getMessage());
            return -1;
        }

        int size = hashMap.size();
        ExampleMod.logger.debug("Returning {}", size);
        return size;
    }

    public static String get_hash_map_string(long hashMapId) {
        ExampleMod.logger.debug("get_hash_map_string(hashMapId={})", hashMapId);

        HashMap<Long, Long> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_hash_map_string", assertEntityTypeException.getMessage());
            return "";
        }

        String str = hashMap.toString();
        ExampleMod.logger.debug("Returning {}", str);
        return str;
    }

    public static int get_hash_set_size(long hashSetId) {
        ExampleMod.logger.debug("get_hash_set_size(hashSetId={})", hashSetId);

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_hash_set_size", assertEntityTypeException.getMessage());
            return -1;
        }

        int size = hashSet.size();
        ExampleMod.logger.debug("Returning {}", size);
        return size;
    }

    public static String get_hash_set_string(long hashSetId) {
        ExampleMod.logger.debug("get_hash_set_string(hashSetId={})", hashSetId);

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_hash_set_string", assertEntityTypeException.getMessage());
            return "";
        }

        String str = hashSet.toString();
        ExampleMod.logger.debug("Returning {}", str);
        return str;
    }

    public static String get_item_name(long id) {
        ExampleMod.logger.debug("get_item_name(id={})", id);

        Item item;
        try {
            item = ExampleMod.grug.getItem(id);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_item_name", assertEntityTypeException.getMessage());
            return "";
        }

        String name = item.getName(new ItemStack(item)).getString();
        ExampleMod.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_item_entity_name(long id) {
        ExampleMod.logger.debug("get_item_entity_name(id={})", id);

        String name;
        try {
            name = ExampleMod.grug.getItemEntity(id).getName().getString();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_item_entity_name", assertEntityTypeException.getMessage());
            return "";
        }

        ExampleMod.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_item_stack_name(long id) {
        ExampleMod.logger.debug("get_item_stack_name(id={})", id);

        ItemStack itemStack;
        try {
            itemStack = ExampleMod.grug.getItemStack(id);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_item_stack_name", assertEntityTypeException.getMessage());
            return "";
        }

        // String name = itemStack.toString(); // "1 diamond"
        // String name = itemStack.getHoverName().getString(); // "Diamond"
        // String name = itemStack.getDisplayName().getString(); // "[Diamond]"
        String name = itemStack.getItem().getName(itemStack).getString(); // "Diamond"

        ExampleMod.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_level_name(long id) {
        ExampleMod.logger.debug("get_level_name(id={})", id);

        Level level;
        try {
            level = ExampleMod.grug.getLevel(id);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_level_name", assertEntityTypeException.getMessage());
            return "";
        }

        String name = level.dimensionTypeRegistration().getRegisteredName();
        ExampleMod.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_resource_location_string(long id) {
        ExampleMod.logger.debug("get_resource_location_string(id={})", id);

        ResourceLocation resourceLocation;
        try {
            resourceLocation = ExampleMod.grug.getResourceLocation(id);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_resource_location_string", assertEntityTypeException.getMessage());
            return "";
        }

        String str = resourceLocation.toString();
        ExampleMod.logger.debug("Returning {}", str);
        return str;
    }

    public static float get_vec3_x(long id) {
        ExampleMod.logger.debug("get_vec3_x(id={})", id);

        float x;
        try {
            x = (float)ExampleMod.grug.getVec3(id).x();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_vec3_x", assertEntityTypeException.getMessage());
            return -1;
        }

        ExampleMod.logger.debug("Returning {}", x);
        return x;
    }

    public static float get_vec3_y(long id) {
        ExampleMod.logger.debug("get_vec3_y(id={})", id);

        float y;
        try {
            y = (float)ExampleMod.grug.getVec3(id).y();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_vec3_y", assertEntityTypeException.getMessage());
            return -1;
        }

        ExampleMod.logger.debug("Returning {}", y);
        return y;
    }

    public static float get_vec3_z(long id) {
        ExampleMod.logger.debug("get_vec3_z(id={})", id);

        float z;
        try {
            z = (float)ExampleMod.grug.getVec3(id).z();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_vec3_z", assertEntityTypeException.getMessage());
            return -1;
        }

        ExampleMod.logger.debug("Returning {}", z);
        return z;
    }

    public static long get_world_position_of_block_entity(long blockEntityId) {
        ExampleMod.logger.debug("get_world_position_of_block_entity(blockEntityId={})", blockEntityId);

        long worldPositionId;
        try {
            worldPositionId = ExampleMod.grug.getBlockEntity(blockEntityId).worldPositionId;
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_world_position_of_block_entity", assertEntityTypeException.getMessage());
            return -1;
        }

        ExampleMod.logger.debug("Returning {}", worldPositionId);
        return worldPositionId;
    }

    public static long hash_map() {
        ExampleMod.logger.debug("hash_map()");

        HashMap<Long, Long> hashMap = new HashMap<>();

        long hashMapId = Grug.addEntity(EntityType.HashMap, hashMap);
        Grug.fnEntities.add(hashMapId);

        Grug.newHashMapObjects(hashMapId);

        ExampleMod.logger.debug("Returning {}", hashMapId);
        return hashMapId;
    }

    public static void hash_map_clear(long hashMapId) {
        ExampleMod.logger.debug("hash_map_clear(hashMapId={})", hashMapId);

        HashMap<Long, Long> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_map_clear", assertEntityTypeException.getMessage());
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

        Grug.removeEntities(hashMap.keySet());
        Grug.removeEntities(hashMap.values());
        hashMap.clear();
        Grug.getHashMapObjects(hashMapId).clear();
    }

    public static void hash_map_copy(long hashMapFromId, long hashMapToId) {
        ExampleMod.logger.debug("hash_map_copy(hashMapFromId={}, hashMapToId={})", hashMapFromId, hashMapToId);

        HashMap<Long, Long> hashMapFrom;
        HashMap<Long, Long> hashMapTo;
        try {
            hashMapFrom = ExampleMod.grug.getHashMap(hashMapFromId);
            hashMapTo = ExampleMod.grug.getHashMap(hashMapToId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_map_copy", assertEntityTypeException.getMessage());
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

        HashMap<Object, Long> hashMapToObjects = Grug.getHashMapObjects(hashMapToId);
        hashMapToObjects.clear();

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

            hashMapToObjects.put(keyObject, newKeyId);
        }
    }

    public static long hash_map_get(long hashMapId, long keyId) {
        ExampleMod.logger.debug("hash_map_get(hashMapId={}, keyId={})", hashMapId, keyId);

        HashMap<Long, Long> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_map_get", assertEntityTypeException.getMessage());
            return -1;
        }

        Object keyObject = ExampleMod.grug.getObject(keyId);
        assert keyObject != null;
        
        HashMap<Object, Long> objects = Grug.getHashMapObjects(hashMapId);

        Long realKeyId = objects.get(keyObject);
        if (realKeyId == null) {
            Grug.gameFunctionErrorHappened("hash_map_get", "hash_map does not contain key");
            return -1;
        }

        Long valueId = hashMap.get(realKeyId);
        assert valueId != null;

        ExampleMod.logger.debug("Returning {}", valueId);
        return valueId;
    }

    public static boolean hash_map_has_key(long hashMapId, long keyId) {
        ExampleMod.logger.debug("hash_map_has_key(hashMapId={}, keyId={})", hashMapId, keyId);

        Object object = ExampleMod.grug.getObject(keyId);

        boolean hasKey = Grug.getHashMapObjects(hashMapId).containsKey(object);
        ExampleMod.logger.debug("Returning {}", hasKey);
        return hasKey;
    }

    public static void hash_map_put(long hashMapId, long keyId, long valueId) {
        ExampleMod.logger.debug("hash_map_put(hashMapId={}, keyId={}, valueId={})", hashMapId, keyId, valueId);

        HashMap<Long, Long> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_map_put", assertEntityTypeException.getMessage());
            return;
        }

        Object keyObject = ExampleMod.grug.getObject(keyId);
        assert keyObject != null;

        HashMap<Object, Long> objects = Grug.getHashMapObjects(hashMapId);

        Object valueObject = ExampleMod.grug.getObject(valueId);
        assert valueObject != null;

        if (objects.containsKey(keyObject)) {
            Long realKeyId = objects.get(keyObject);
            assert realKeyId != null;

            // As an optimization, we don't free the existing key.
            Long oldValueId = hashMap.get(realKeyId);
            if (Grug.globalEntities.contains(hashMapId)) {
                Grug.globalEntities.remove(oldValueId);
            } else {
                Grug.fnEntities.remove(oldValueId);
            }

            long newValueId = Grug.addEntity(Grug.getEntityType(valueId), valueObject);
            if (Grug.globalEntities.contains(hashMapId)) {
                Grug.globalEntities.add(newValueId);
            } else {
                Grug.fnEntities.add(newValueId);
            }

            hashMap.put(realKeyId, newValueId);
        } else {
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
            objects.put(keyObject, newKeyId);
        }
    }

    public static void hash_map_remove_key(long hashMapId, long keyId) {
        ExampleMod.logger.debug("hash_map_remove_key(hashMapId={}, keyId={})", hashMapId, keyId);

        HashMap<Long, Long> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_map_remove_key", assertEntityTypeException.getMessage());
            return;
        }

        Object keyObject = ExampleMod.grug.getObject(keyId);
        assert keyObject != null;

        HashMap<Object, Long> objects = Grug.getHashMapObjects(hashMapId);

        Long realKeyId = objects.get(keyObject);
        if (realKeyId == null) {
            Grug.gameFunctionErrorHappened("hash_map_remove_key", "hash_map does not contain key");
            return;
        }

        objects.remove(keyObject);

        Long valueId = hashMap.remove(realKeyId);
        assert valueId != null;

        if (Grug.globalEntities.contains(hashMapId)) {
            Grug.globalEntities.remove(realKeyId);
            Grug.globalEntities.remove(valueId);
        } else {
            Grug.fnEntities.remove(realKeyId);
            Grug.fnEntities.remove(valueId);
        }
    }

    public static long hash_set() {
        ExampleMod.logger.debug("hash_set()");

        HashSet<Long> hashSet = new HashSet<>();

        long hashSetId = Grug.addEntity(EntityType.HashSet, hashSet);
        Grug.fnEntities.add(hashSetId);

        Grug.newHashSetObjects(hashSetId);

        ExampleMod.logger.debug("Returning {}", hashSetId);
        return hashSetId;
    }

    public static void hash_set_add(long hashSetId, long valueId) {
        ExampleMod.logger.debug("hash_set_add(hashSetId={}, valueId={})", hashSetId, valueId);

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_set_add", assertEntityTypeException.getMessage());
            return;
        }

        Object valueObject = ExampleMod.grug.getObject(valueId);

        HashMap<Object, Long> objects = Grug.getHashSetObjects(hashSetId);

        if (!objects.containsKey(valueObject)) {
            long newValueId = Grug.addEntity(Grug.getEntityType(valueId), valueObject);
            if (Grug.globalEntities.contains(hashSetId)) {
                Grug.globalEntities.add(newValueId);
            } else {
                Grug.fnEntities.add(newValueId);
            }

            hashSet.add(newValueId);
            objects.put(valueObject, newValueId);
        }
    }

    public static void hash_set_clear(long hashSetId) {
        ExampleMod.logger.debug("hash_set_clear(hashSetId={})", hashSetId);

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_set_clear", assertEntityTypeException.getMessage());
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
        ExampleMod.logger.debug("hash_set_copy(hashSetFromId={}, hashSetToId={})", hashSetFromId, hashSetToId);

        HashSet<Long> hashSetFrom;
        HashSet<Long> hashSetTo;
        try {
            hashSetFrom = ExampleMod.grug.getHashSet(hashSetFromId);
            hashSetTo = ExampleMod.grug.getHashSet(hashSetToId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_set_copy", assertEntityTypeException.getMessage());
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

        HashMap<Object, Long> hashSetToObjects = Grug.getHashSetObjects(hashSetToId);
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

            hashSetToObjects.put(valueObject, newValueId);
        }
    }

    public static boolean hash_set_has(long hashSetId, long valueId) {
        ExampleMod.logger.debug("hash_set_has(hashSetId={}, valueId={})", hashSetId, valueId);

        HashMap<Object, Long> objects;
        try {
            objects = Grug.getHashSetObjects(hashSetId);
            assert objects != null;
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_set_has", assertEntityTypeException.getMessage());
            return false;
        }

        Object object = ExampleMod.grug.getObject(valueId);
        
        boolean hasValue = objects.containsKey(object);
        ExampleMod.logger.debug("Returning {}", hasValue);
        return hasValue;
    }

    public static void hash_set_remove(long hashSetId, long valueId) {
        ExampleMod.logger.debug("hash_set_remove(hashSetId={}, valueId={})", hashSetId, valueId);

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_set_remove", assertEntityTypeException.getMessage());
            return;
        }

        Object valueObject = ExampleMod.grug.getObject(valueId);
        assert valueObject != null;

        HashMap<Object, Long> objects = Grug.getHashSetObjects(hashSetId);

        Long realValueId = objects.get(valueObject);
        if (realValueId == null) {
            Grug.gameFunctionErrorHappened("hash_set_remove", "hash_set does not contain value");
            return;
        }

        objects.remove(valueObject);

        boolean containedValue = hashSet.remove(realValueId);
        assert containedValue;

        if (Grug.globalEntities.contains(hashSetId)) {
            Grug.globalEntities.remove(realValueId);
        } else {
            Grug.fnEntities.remove(realValueId);
        }
    }

    public static boolean is_air(long blockStateId) {
        ExampleMod.logger.debug("is_air(blockStateId={})", blockStateId);

        BlockState blockState;
        try {
            blockState = ExampleMod.grug.getBlockState(blockStateId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("is_air", assertEntityTypeException.getMessage());
            return false;
        }

        boolean isAir = blockState.isAir();
        ExampleMod.logger.debug("Returning {}", isAir);
        return isAir;
    }

    public static boolean is_client_side(long levelId) {
        ExampleMod.logger.debug("is_client_side(levelId={})", levelId);

        Level level;
        try {
            level = ExampleMod.grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("is_client_side", assertEntityTypeException.getMessage());
            return false;
        }

        boolean isClientSide = level.isClientSide();
        ExampleMod.logger.debug("Returning {}", isClientSide);
        return isClientSide;
    }

    public static long item(long resourceLocation) {
        ExampleMod.logger.debug("item(resourceLocation={})", resourceLocation);

        Item item;
        try {
            ResourceLocation resourceLocationInstance = ExampleMod.grug.getResourceLocation(resourceLocation);

            if (!ForgeRegistries.ITEMS.containsKey(resourceLocationInstance)) {
                Grug.gameFunctionErrorHappened("item", "invalid resource_location argument");
                return -1;
            }

            item = ForgeRegistries.ITEMS.getValue(resourceLocationInstance);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("item", assertEntityTypeException.getMessage());
            return -1;
        }

        long itemId = Grug.addEntity(EntityType.Item, item);
        Grug.fnEntities.add(itemId);

        ExampleMod.logger.debug("Returning {}", itemId);
        return itemId;
    }

    public static long item_entity(long level, float x, float y, float z, long itemStack) {
        ExampleMod.logger.debug("item_entity(level={}, x={}, y={}, z={}, itemStack={})", level, x, y, z, itemStack);

        ItemEntity itemEntity;
        try {
            itemEntity = new ItemEntity(ExampleMod.grug.getLevel(level), x, y, z, ExampleMod.grug.getItemStack(itemStack));
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("item_entity", assertEntityTypeException.getMessage());
            return -1;
        }

        long itemEntityId = Grug.addEntity(EntityType.ItemEntity, itemEntity);
        Grug.fnEntities.add(itemEntityId);

        ExampleMod.logger.debug("Returning {}", itemEntityId);
        return itemEntityId;
    }

    public static long item_stack(long item) {
        ExampleMod.logger.debug("item_stack(item={})", item);

        ItemStack itemStack;
        try {
            itemStack = new ItemStack(ExampleMod.grug.getItem(item));
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("item_stack", assertEntityTypeException.getMessage());
            return -1;
        }

        long itemStackId = Grug.addEntity(EntityType.ItemStack, itemStack);
        Grug.fnEntities.add(itemStackId);

        ExampleMod.logger.debug("Returning {}", itemStackId);
        return itemStackId;
    }

    public static boolean iterating(long iteratorId) {
        ExampleMod.logger.debug("iterating(iteratorId={})", iteratorId);

        GrugIterator grugIterator;
        try {
            grugIterator = ExampleMod.grug.getIterator(iteratorId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("iterating", assertEntityTypeException.getMessage());
            return false;
        }

        boolean hasNext = grugIterator.iterator.hasNext();
        ExampleMod.logger.debug("Returning {}", hasNext);
        return hasNext;
    }

    public static long iteration(long iteratorId) {
        ExampleMod.logger.debug("iteration(iteratorId={})", iteratorId);

        GrugIterator grugIterator;
        try {
            grugIterator = ExampleMod.grug.getIterator(iteratorId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("iteration", assertEntityTypeException.getMessage());
            return -1;
        }

        long entryOrValueId;
        if (grugIterator.iterableType == IterableType.HashMap) {
            // TODO: Add a test that asserts whether we do or don't allow saving a GrugEntry in a global
            // TODO: I'm not sure if Java by itself even allows it?

            Entry<Long, Long> entry;
            try {
                entry = (Entry<Long, Long>)grugIterator.iterator.next();
            } catch (ConcurrentModificationException err) {
                Grug.gameFunctionErrorHappened("iteration", "the iterable was modified during iteration");
                return -1;
            }

            entryOrValueId = Grug.addEntity(EntityType.Entry, new GrugEntry(entry, grugIterator.isIterableGlobal));
            Grug.fnEntities.add(entryOrValueId);
        } else if (grugIterator.iterableType == IterableType.HashSet) {
            entryOrValueId = (long)grugIterator.iterator.next();
        } else {
            throw new RuntimeException("iteration() failed to handle an iterator type, which means iteration() needs to be updated");
        }

        ExampleMod.logger.debug("Returning {}", entryOrValueId);
        return entryOrValueId;
    }

    public static long iterator(long iterableId) {
        ExampleMod.logger.debug("iterator(iterableId={})", iterableId);

        EntityType containerType = Grug.getEntityType(iterableId);
        Iterator<?> iterator;
        IterableType iterableType;
        try {
            if (containerType == EntityType.HashMap) {
                iterator = ExampleMod.grug.getHashMap(iterableId).entrySet().iterator();
                iterableType = IterableType.HashMap;
            } else if (containerType == EntityType.HashSet) {
                iterator = ExampleMod.grug.getHashSet(iterableId).iterator();
                iterableType = IterableType.HashSet;
            } else {
                Grug.gameFunctionErrorHappened("iterator", "Expected an iterable, but got " + StringUtils.getSnakeCase(containerType));
                return -1;
            }
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("iterator", assertEntityTypeException.getMessage());
            return -1;
        }

        GrugIterator grugIterator = new GrugIterator(iterator, iterableType, Grug.globalEntities.contains(iterableId));
        long grugIteratorId = Grug.addEntity(EntityType.Iterator, grugIterator);
        Grug.fnEntities.add(grugIteratorId);

        ExampleMod.logger.debug("Returning {}", grugIteratorId);
        return grugIteratorId;
    }

    public static void iterator_remove(long iteratorId) {
        ExampleMod.logger.debug("iterator_remove(iteratorId={})", iteratorId);

        GrugIterator grugIterator;
        try {
            grugIterator = ExampleMod.grug.getIterator(iteratorId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("iterator_remove", assertEntityTypeException.getMessage());
            return;
        }

        try {
            grugIterator.iterator.remove();
        } catch (IllegalStateException err) {
            Grug.gameFunctionErrorHappened("iterator_remove", "can only be called once, per call to iteration()");
            return;
        }
    }

    public static void place_block(long blockStateId, long blockPosId, int flags, long levelId) {
        ExampleMod.logger.debug("place_block(blockStateId={}, blockPosId={}, flags={}, levelId={})", blockStateId, blockPosId, flags, levelId);

        BlockState blockState;
        BlockPos blockPos;
        Level level;
        try {
            blockState = ExampleMod.grug.getBlockState(blockStateId);
            blockPos = ExampleMod.grug.getBlockPos(blockPosId);
            level = ExampleMod.grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("place_block", assertEntityTypeException.getMessage());
            return;
        }

        level.setBlock(blockPos, blockState, flags);
    }

    public static void print_bool(boolean b) {
        ExampleMod.logger.debug("print_bool(b={})", b);

        Grug.sendMessageToEveryone(Component.literal(Boolean.toString(b)));
    }

    public static void print_f32(float f) {
        ExampleMod.logger.debug("print_f32(f={})", f);

        Grug.sendMessageToEveryone(Component.literal(Float.toString(f)));
    }

    public static void print_i32(int n) {
        ExampleMod.logger.debug("print_i32(n={})", n);

        Grug.sendMessageToEveryone(Component.literal(Integer.toString(n)));
    }

    public static void print_id(long id) {
        ExampleMod.logger.debug("print_id(id={})", id);

        Grug.sendMessageToEveryone(Component.literal(Long.toString(id)));
    }

    public static void print_string(String str) {
        ExampleMod.logger.debug("print_string(str={})", str);

        Grug.sendMessageToEveryone(Component.literal(str));
    }

    public static void remove_block(long blockPosId, long levelId) {
        ExampleMod.logger.debug("remove_block(blockPosId={}, levelId={})", blockPosId, levelId);

        BlockPos blockPos;
        Level level;
        try {
            blockPos = ExampleMod.grug.getBlockPos(blockPosId);
            level = ExampleMod.grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("remove_block", assertEntityTypeException.getMessage());
            return;
        }

        level.removeBlock(blockPos, false);
    }

    public static void remove_moving_block(long blockPosId, long levelId) {
        ExampleMod.logger.debug("remove_moving_block(blockPosId={}, levelId={})", blockPosId, levelId);

        BlockPos blockPos;
        Level level;
        try {
            blockPos = ExampleMod.grug.getBlockPos(blockPosId);
            level = ExampleMod.grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("remove_moving_block", assertEntityTypeException.getMessage());
            return;
        }

        level.removeBlock(blockPos, true);
    }

    public static long resource_location(String resourceLocationString) {
        ExampleMod.logger.debug("resource_location(resourceLocationString={})", resourceLocationString);

        ResourceLocation resourceLocation;
        try {
            resourceLocation = new ResourceLocation(resourceLocationString);
        } catch (ResourceLocationException resourceLocationException) {
            Grug.gameFunctionErrorHappened("resource_location", "invalid resource_location_string argument");
            return -1;
        }

        long resourceLocationId = Grug.addEntity(EntityType.ResourceLocation, resourceLocation);
        Grug.fnEntities.add(resourceLocationId);

        ExampleMod.logger.debug("Returning {}", resourceLocationId);
        return resourceLocationId;
    }

    public static void set_entity_delta_movement(long entity, long vec3) {
        ExampleMod.logger.debug("set_entity_delta_movement(entity={}, vec3={})", entity, vec3);

        Entity entityInstance;
        Vec3 vec3Instance;
        try {
            entityInstance = ExampleMod.grug.getEntity(entity);
            vec3Instance = ExampleMod.grug.getVec3(vec3);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("set_item_entity_delta_movement", assertEntityTypeException.getMessage());
            return;
        }

        entityInstance.setDeltaMovement(vec3Instance);
    }

    public static void spawn_entity(long entity, long level) {
        ExampleMod.logger.debug("spawn_entity(entity={}, level={})", entity, level);

        Entity entityInstance;
        Level levelInstance;
        try {
            entityInstance = ExampleMod.grug.getEntity(entity);
            levelInstance = ExampleMod.grug.getLevel(level);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("spawn_entity", assertEntityTypeException.getMessage());
            return;
        }

        levelInstance.addFreshEntity(entityInstance);
    }

    public static int unbox_i32(long box) {
        ExampleMod.logger.debug("unbox_i32(box={})", box);

        int unboxedI32 = ExampleMod.grug.getBoxedI32(box);
        ExampleMod.logger.debug("Returning {}", unboxedI32);
        return unboxedI32;
    }

    public static long vec3_zero() {
        ExampleMod.logger.debug("vec3_zero()");

        long vec3ZeroId = Grug.addEntity(EntityType.Vec3, Vec3.ZERO);
        Grug.fnEntities.add(vec3ZeroId);

        ExampleMod.logger.debug("Returning {}", vec3ZeroId);
        return vec3ZeroId;
    }
}

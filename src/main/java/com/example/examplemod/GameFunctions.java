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
        ExampleMod.logger.debug("block(resourceLocation={})", resourceLocation);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        ExampleMod.logger.debug("Returning {}", blockId);
        return blockId;
    }

    public static long block_pos(int x, int y, int z) {
        ExampleMod.logger.debug("block_pos(x={}, y={}, z={})", x, y, z);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

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
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

        Integer boxedI32 = i32;

        long boxId = Grug.addEntity(EntityType.BoxedI32, boxedI32);
        Grug.fnEntities.add(boxId);

        ExampleMod.logger.debug("Returning {}", boxId);
        return boxId;
    }

    public static void destroy_block(long blockPosId, long levelId) {
        ExampleMod.logger.debug("destroy_block(blockPosId={}, levelId={})", blockPosId, levelId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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
        ExampleMod.logger.debug("destroy_and_drop_block(blockPosId={}, levelId={})", blockPosId, levelId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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
        ExampleMod.logger.debug("get_block_entity_level(blockEntityId={})", blockEntityId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        ExampleMod.logger.debug("Returning {}", levelId);
        return levelId;
    }

    public static long get_block_pos_above(long blockPosId) {
        ExampleMod.logger.debug("get_block_pos_above(blockPosId={})", blockPosId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        ExampleMod.logger.debug("Returning {}", aboveId);
        return aboveId;
    }

    public static long get_block_pos_above_n(long blockPosId, int n) {
        ExampleMod.logger.debug("get_block_pos_above_n(blockPosId={}, n={})", blockPosId, n);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        ExampleMod.logger.debug("Returning {}", aboveId);
        return aboveId;
    }

    public static long get_block_pos_center(long blockPosId) {
        ExampleMod.logger.debug("get_block_pos_center(blockPosId={})", blockPosId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        ExampleMod.logger.debug("Returning {}", centerId);
        return centerId;
    }

    public static int get_block_pos_x(long id) {
        ExampleMod.logger.debug("get_block_pos_x(id={})", id);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

        int x;
        try {
            x = ExampleMod.grug.getBlockPos(id).getX();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_block_pos_x", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        ExampleMod.logger.debug("Returning {}", x);
        return x;
    }

    public static int get_block_pos_y(long id) {
        ExampleMod.logger.debug("get_block_pos_y(id={})", id);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

        int y;
        try {
            y = ExampleMod.grug.getBlockPos(id).getY();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_block_pos_y", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        ExampleMod.logger.debug("Returning {}", y);
        return y;
    }

    public static int get_block_pos_z(long id) {
        ExampleMod.logger.debug("get_block_pos_z(id={})", id);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

        int z;
        try {
            z = ExampleMod.grug.getBlockPos(id).getZ();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_block_pos_z", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        ExampleMod.logger.debug("Returning {}", z);
        return z;
    }

    public static long get_block_state(long blockPosId, long levelId) {
        ExampleMod.logger.debug("get_block_state(blockPosId={}, levelId={})", blockPosId, levelId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        ExampleMod.logger.debug("Returning {}", blockStateId);
        return blockStateId;
    }

    public static long get_default_block_state(long blockId) {
        ExampleMod.logger.debug("get_default_block_state(blockId={})", blockId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        ExampleMod.logger.debug("Returning {}", blockStateId);
        return blockStateId;
    }

    public static int get_hash_map_size(long hashMapId) {
        ExampleMod.logger.debug("get_hash_map_size(hashMapId={})", hashMapId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        int size = hashMap.size();
        ExampleMod.logger.debug("Returning {}", size);
        return size;
    }

    public static String get_hash_map_string(long hashMapId) {
        ExampleMod.logger.debug("get_hash_map_string(hashMapId={})", hashMapId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        String str = hashMap.toString();
        ExampleMod.logger.debug("Returning {}", str);
        return str;
    }

    public static int get_hash_set_size(long hashSetId) {
        ExampleMod.logger.debug("get_hash_set_size(hashSetId={})", hashSetId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        int size = hashSet.size();
        ExampleMod.logger.debug("Returning {}", size);
        return size;
    }

    public static String get_hash_set_string(long hashSetId) {
        ExampleMod.logger.debug("get_hash_set_string(hashSetId={})", hashSetId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        String str = hashSet.toString();
        ExampleMod.logger.debug("Returning {}", str);
        return str;
    }

    public static String get_item_name(long id) {
        ExampleMod.logger.debug("get_item_name(id={})", id);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        String name = item.getName(new ItemStack(item)).getString();
        ExampleMod.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_item_entity_name(long id) {
        ExampleMod.logger.debug("get_item_entity_name(id={})", id);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return "";
        }

        String name;
        try {
            name = ExampleMod.grug.getItemEntity(id).getName().getString();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_item_entity_name", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return "";
        }

        ExampleMod.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_item_stack_name(long id) {
        ExampleMod.logger.debug("get_item_stack_name(id={})", id);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        // String name = itemStack.toString(); // "1 diamond"
        // String name = itemStack.getHoverName().getString(); // "Diamond"
        // String name = itemStack.getDisplayName().getString(); // "[Diamond]"
        String name = itemStack.getItem().getName(itemStack).getString(); // "Diamond"

        ExampleMod.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_level_name(long id) {
        ExampleMod.logger.debug("get_level_name(id={})", id);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        String name = level.dimensionTypeRegistration().getRegisteredName();
        ExampleMod.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_resource_location_string(long id) {
        ExampleMod.logger.debug("get_resource_location_string(id={})", id);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        String str = resourceLocation.toString();
        ExampleMod.logger.debug("Returning {}", str);
        return str;
    }

    public static float get_vec3_x(long id) {
        ExampleMod.logger.debug("get_vec3_x(id={})", id);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

        float x;
        try {
            x = (float)ExampleMod.grug.getVec3(id).x();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_vec3_x", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        ExampleMod.logger.debug("Returning {}", x);
        return x;
    }

    public static float get_vec3_y(long id) {
        ExampleMod.logger.debug("get_vec3_y(id={})", id);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

        float y;
        try {
            y = (float)ExampleMod.grug.getVec3(id).y();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_vec3_y", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        ExampleMod.logger.debug("Returning {}", y);
        return y;
    }

    public static float get_vec3_z(long id) {
        ExampleMod.logger.debug("get_vec3_z(id={})", id);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

        float z;
        try {
            z = (float)ExampleMod.grug.getVec3(id).z();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_vec3_z", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        ExampleMod.logger.debug("Returning {}", z);
        return z;
    }

    public static long get_world_position_of_block_entity(long blockEntityId) {
        ExampleMod.logger.debug("get_world_position_of_block_entity(blockEntityId={})", blockEntityId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

        long worldPositionId;
        try {
            worldPositionId = ExampleMod.grug.getBlockEntity(blockEntityId).worldPositionId;
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_world_position_of_block_entity", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        ExampleMod.logger.debug("Returning {}", worldPositionId);
        return worldPositionId;
    }

    public static long hash_map() {
        ExampleMod.logger.debug("hash_map()");
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

        HashMap<Long, Long> hashMap = new HashMap<>();

        long hashMapId = Grug.addEntity(EntityType.HashMap, hashMap);
        Grug.fnEntities.add(hashMapId);

        Grug.newHashMapObjects(hashMapId);

        ExampleMod.logger.debug("Returning {}", hashMapId);
        return hashMapId;
    }

    public static void hash_map_clear(long hashMapId) {
        ExampleMod.logger.debug("hash_map_clear(hashMapId={})", hashMapId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        Grug.removeEntities(hashMap.keySet());
        Grug.removeEntities(hashMap.values());
        hashMap.clear();
        Grug.getHashMapObjects(hashMapId).clear();
    }

    public static void hash_map_copy(long hashMapFromId, long hashMapToId) {
        ExampleMod.logger.debug("hash_map_copy(hashMapFromId={}, hashMapToId={})", hashMapFromId, hashMapToId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        Object keyObject = ExampleMod.grug.getObject(keyId);
        assert keyObject != null;
        
        HashMap<Object, Long> objects = Grug.getHashMapObjects(hashMapId);

        Long realKeyId = objects.get(keyObject);
        if (realKeyId == null) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_get", "hash_map does not contain key");
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        Long valueId = hashMap.get(realKeyId);
        assert valueId != null;

        ExampleMod.logger.debug("Returning {}", valueId);
        return valueId;
    }

    public static boolean hash_map_has_key(long hashMapId, long keyId) {
        ExampleMod.logger.debug("hash_map_has_key(hashMapId={}, keyId={})", hashMapId, keyId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return false;
        }

        Object object = ExampleMod.grug.getObject(keyId);

        boolean hasKey = Grug.getHashMapObjects(hashMapId).containsKey(object);
        ExampleMod.logger.debug("Returning {}", hasKey);
        return hasKey;
    }

    public static void hash_map_put(long hashMapId, long keyId, long valueId) {
        ExampleMod.logger.debug("hash_map_put(hashMapId={}, keyId={}, valueId={})", hashMapId, keyId, valueId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return;
        }

        HashMap<Long, Long> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_put", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
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
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return;
        }

        HashMap<Long, Long> hashMap;
        try {
            hashMap = ExampleMod.grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_remove_key", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        Object keyObject = ExampleMod.grug.getObject(keyId);
        assert keyObject != null;

        HashMap<Object, Long> objects = Grug.getHashMapObjects(hashMapId);

        Long realKeyId = objects.get(keyObject);
        if (realKeyId == null) {
            Grug.sendGameFunctionErrorToEveryone("hash_map_remove_key", "hash_map does not contain key");
            Grug.gameFunctionErrorHappened = true;
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
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

        HashSet<Long> hashSet = new HashSet<>();

        long hashSetId = Grug.addEntity(EntityType.HashSet, hashSet);
        Grug.fnEntities.add(hashSetId);

        Grug.newHashSetObjects(hashSetId);

        ExampleMod.logger.debug("Returning {}", hashSetId);
        return hashSetId;
    }

    public static void hash_set_add(long hashSetId, long valueId) {
        ExampleMod.logger.debug("hash_set_add(hashSetId={}, valueId={})", hashSetId, valueId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return;
        }

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_add", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        Object valueObject = ExampleMod.grug.getObject(valueId);

        HashMap<Object, Long> objects = Grug.getHashSetObjects(hashSetId);

        if (!objects.containsKey(valueObject)) {
            objects.put(valueObject, valueId);

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
        ExampleMod.logger.debug("hash_set_clear(hashSetId={})", hashSetId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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
        ExampleMod.logger.debug("hash_set_copy(hashSetFromId={}, hashSetToId={})", hashSetFromId, hashSetToId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return false;
        }

        Object object = ExampleMod.grug.getObject(valueId);

        boolean hasValue = Grug.getHashSetObjects(hashSetId).containsKey(object);
        ExampleMod.logger.debug("Returning {}", hasValue);
        return hasValue;
    }

    public static void hash_set_remove(long hashSetId, long valueId) {
        ExampleMod.logger.debug("hash_set_remove(hashSetId={}, valueId={})", hashSetId, valueId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return;
        }

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_remove", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return;
        }

        Object valueObject = ExampleMod.grug.getObject(valueId);
        assert valueObject != null;

        HashMap<Object, Long> objects = Grug.getHashSetObjects(hashSetId);

        Long realValueId = objects.get(valueObject);
        if (realValueId == null) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_remove", "hash_set does not contain value");
            Grug.gameFunctionErrorHappened = true;
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
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        boolean isAir = blockState.isAir();
        ExampleMod.logger.debug("Returning {}", isAir);
        return isAir;
    }

    public static boolean is_client_side(long levelId) {
        ExampleMod.logger.debug("is_client_side(levelId={})", levelId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        boolean isClientSide = level.isClientSide();
        ExampleMod.logger.debug("Returning {}", isClientSide);
        return isClientSide;
    }

    public static long item(long resourceLocation) {
        ExampleMod.logger.debug("item(resourceLocation={})", resourceLocation);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        ExampleMod.logger.debug("Returning {}", itemId);
        return itemId;
    }

    public static long item_entity(long level, float x, float y, float z, long itemStack) {
        ExampleMod.logger.debug("item_entity(level={}, x={}, y={}, z={}, itemStack={})", level, x, y, z, itemStack);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        ExampleMod.logger.debug("Returning {}", itemEntityId);
        return itemEntityId;
    }

    public static long item_stack(long item) {
        ExampleMod.logger.debug("item_stack(item={})", item);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        ExampleMod.logger.debug("Returning {}", itemStackId);
        return itemStackId;
    }

    public static boolean iterating(long iteratorId) {
        ExampleMod.logger.debug("iterating(iteratorId={})", iteratorId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return false;
        }

        Iterator<?> iterator;
        try {
            iterator = ExampleMod.grug.getHashMapIterator(iteratorId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iterating", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return false;
        }

        boolean hasNext = iterator.hasNext();
        ExampleMod.logger.debug("Returning {}", hasNext);
        return hasNext;
    }

    public static long iteration(long iteratorId) {
        ExampleMod.logger.debug("iteration(iteratorId={})", iteratorId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        long iterationIdOrValue;
        if (iteratorType == EntityType.HashMapIterator) {
            // TODO: Add a test that asserts whether we do or don't allow saving
            //       an Entry<Long, Long> in a global. I'm not sure if Java by itself even allows it?

            Entry<Long, Long> iteration = (Entry<Long, Long>)iterator.next();

            iterationIdOrValue = Grug.addEntity(EntityType.Iteration, iteration);
            Grug.fnEntities.add(iterationIdOrValue);
        } else if (iteratorType == EntityType.HashSetIterator) {
            iterationIdOrValue = (long)iterator.next();
        } else {
            throw new RuntimeException("iteration() failed to handle an iterator type, which means iteration() needs to be updated");
        }

        ExampleMod.logger.debug("Returning {}", iterationIdOrValue);
        return iterationIdOrValue;
    }

    public static long iteration_key(long iterationId) {
        ExampleMod.logger.debug("iteration_key(iterationId={})", iterationId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        Long key = iteration.getKey();
        ExampleMod.logger.debug("Returning {}", key);
        return key;
    }

    public static long iteration_value(long iterationId) {
        ExampleMod.logger.debug("iteration_value(iterationId={})", iterationId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        Long value = iteration.getValue();
        ExampleMod.logger.debug("Returning {}", value);
        return value;
    }

    public static long iterator(long iterable) {
        ExampleMod.logger.debug("iterator(iterable={})", iterable);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        ExampleMod.logger.debug("Returning {}", grugIteratorId);
        return grugIteratorId;
    }

    public static void place_block(long blockStateId, long blockPosId, int flags, long levelId) {
        ExampleMod.logger.debug("place_block(blockStateId={}, blockPosId={}, flags={}, levelId={})", blockStateId, blockPosId, flags, levelId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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
        ExampleMod.logger.debug("print_bool(b={})", b);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(Boolean.toString(b)));
    }

    public static void print_f32(float f) {
        ExampleMod.logger.debug("print_f32(f={})", f);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(Float.toString(f)));
    }

    public static void print_i32(int n) {
        ExampleMod.logger.debug("print_i32(n={})", n);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(Integer.toString(n)));
    }

    public static void print_id(long id) {
        ExampleMod.logger.debug("print_id(id={})", id);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(Long.toString(id)));
    }

    public static void print_string(String str) {
        ExampleMod.logger.debug("print_string(str={})", str);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return;
        }

        Grug.sendMessageToEveryone(Component.literal(str));
    }

    public static void remove_block(long blockPosId, long levelId) {
        ExampleMod.logger.debug("remove_block(blockPosId={}, levelId={})", blockPosId, levelId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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
        ExampleMod.logger.debug("remove_moving_block(blockPosId={}, levelId={})", blockPosId, levelId);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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
        ExampleMod.logger.debug("resource_location(resourceLocationString={})", resourceLocationString);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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

        ExampleMod.logger.debug("Returning {}", resourceLocationId);
        return resourceLocationId;
    }

    public static void set_entity_delta_movement(long entity, long vec3) {
        ExampleMod.logger.debug("set_entity_delta_movement(entity={}, vec3={})", entity, vec3);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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
        ExampleMod.logger.debug("spawn_entity(entity={}, level={})", entity, level);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
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
        ExampleMod.logger.debug("unbox_i32(box={})", box);
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

        int unboxedI32 = ExampleMod.grug.getBoxedI32(box);
        ExampleMod.logger.debug("Returning {}", unboxedI32);
        return unboxedI32;
    }

    public static long vec3_zero() {
        ExampleMod.logger.debug("vec3_zero()");
        if (Grug.gameFunctionErrorHappened) {
            ExampleMod.logger.debug("gameFunctionErrorHappened");
            return 0;
        }

        long vec3ZeroId = Grug.addEntity(EntityType.Vec3, Vec3.ZERO);
        Grug.fnEntities.add(vec3ZeroId);

        ExampleMod.logger.debug("Returning {}", vec3ZeroId);
        return vec3ZeroId;
    }
}

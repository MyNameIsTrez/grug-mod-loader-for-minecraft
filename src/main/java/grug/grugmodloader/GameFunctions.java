package grug.grugmodloader;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Map.Entry;

import grug.grugmodloader.GrugIterator.IterableType;
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
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

public class GameFunctions {
    public static long block(long resourceLocation) {
        GrugModLoader.logger.debug("block(resourceLocation={})", resourceLocation);

        Block block;
        try {
            ResourceLocation resourceLocationInstance = Grug.getResourceLocation(resourceLocation);

            if (!ForgeRegistries.BLOCKS.containsKey(resourceLocationInstance)) {
                Grug.gameFunctionErrorHappened("block", "Invalid resource_location");
                return -1;
            }

            block = ForgeRegistries.BLOCKS.getValue(resourceLocationInstance);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("block", assertEntityTypeException.getMessage());
            return -1;
        }

        long blockId = Grug.addEntity(GrugEntityType.Block, block);

        GrugModLoader.logger.debug("Returning {}", blockId);
        return blockId;
    }

    public static long block_pos(int x, int y, int z) {
        GrugModLoader.logger.debug("block_pos(x={}, y={}, z={})", x, y, z);

        BlockPos blockPos = new BlockPos(x, y, z);

        long blockPosId = Grug.addEntity(GrugEntityType.BlockPos, blockPos);

        GrugModLoader.logger.debug("Returning {}", blockPosId);
        return blockPosId;
    }

    public static int block_flag_update_all() {
        GrugModLoader.logger.debug("block_flag_update_all()");
        int flag = Block.UPDATE_ALL;
        GrugModLoader.logger.debug("Returning {}", flag);
        return flag;
    }

    public static long box_i32(int i32) {
        GrugModLoader.logger.debug("box_i32(i32={})", i32);

        Integer boxedI32 = i32;

        long boxId = Grug.addEntity(GrugEntityType.BoxedI32, boxedI32);

        GrugModLoader.logger.debug("Returning {}", boxId);
        return boxId;
    }

    public static void destroy_and_drop_block(long blockPosId, long levelId) {
        GrugModLoader.logger.debug("destroy_and_drop_block(blockPosId={}, levelId={})", blockPosId, levelId);

        BlockPos blockPos;
        Level level;
        try {
            blockPos = Grug.getBlockPos(blockPosId);
            level = Grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("destroy_and_drop_block", assertEntityTypeException.getMessage());
            return;
        }

        level.destroyBlock(blockPos, true);
    }

    public static void destroy_block(long blockPosId, long levelId) {
        GrugModLoader.logger.debug("destroy_block(blockPosId={}, levelId={})", blockPosId, levelId);

        BlockPos blockPos;
        Level level;
        try {
            blockPos = Grug.getBlockPos(blockPosId);
            level = Grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("destroy_block", assertEntityTypeException.getMessage());
            return;
        }

        level.destroyBlock(blockPos, false);
    }

    public static long entry_key(long entryId) {
        GrugModLoader.logger.debug("entry_key(entryId={})", entryId);

        Entry<GrugObject, GrugObject> grugEntry;
        try {
            grugEntry = Grug.getEntry(entryId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("entry_key", assertEntityTypeException.getMessage());
            return -1;
        }

        GrugObject key = grugEntry.getKey();

        long keyId = Grug.addEntity(key.type, key.object);
        GrugModLoader.logger.debug("Returning {}", keyId);
        return keyId;
    }

    public static void entry_set_value(long entryId, long valueId) {
        GrugModLoader.logger.debug("entry_set_value(entryId={}, valueId={})", entryId, valueId);

        Entry<GrugObject, GrugObject> grugEntry;
        try {
            grugEntry = Grug.getEntry(entryId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("entry_set_value", assertEntityTypeException.getMessage());
            return;
        }

        GrugObject valueObject = Grug.getGrugObject(valueId);

        GrugObject oldValueObject = grugEntry.setValue(valueObject);
        assert oldValueObject != null;
    }

    public static long entry_value(long entryId) {
        GrugModLoader.logger.debug("entry_value(entryId={})", entryId);

        Entry<GrugObject, GrugObject> grugEntry;
        try {
            grugEntry = Grug.getEntry(entryId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("entry_value", assertEntityTypeException.getMessage());
            return -1;
        }

        GrugObject value = grugEntry.getValue();

        // TODO: This calls `new GrugObject()`, which is unnecessary, as valueObject already exists, so create Grug.addExistingEntity()
        long valueId = Grug.addEntity(value.type, value.object);

        GrugModLoader.logger.debug("Returning {}", valueId);
        return valueId;
    }

    public static long get_block_entity_level(long blockEntityId) {
        GrugModLoader.logger.debug("get_block_entity_level(blockEntityId={})", blockEntityId);

        Level level;
        try {
            level = Grug.getBlockEntity(blockEntityId).getLevel();
            assert level != null; // null when game tests forget to call .setLevel() on a BlockEntity
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_entity_level", assertEntityTypeException.getMessage());
            return -1;
        }

        long levelId = Grug.addEntity(GrugEntityType.Level, level);

        GrugModLoader.logger.debug("Returning {}", levelId);
        return levelId;
    }

    public static long get_block_pos_above(long blockPosId) {
        GrugModLoader.logger.debug("get_block_pos_above(blockPosId={})", blockPosId);

        BlockPos above;
        try {
            // The .above() call allocates a new BlockPos
            above = Grug.getBlockPos(blockPosId).above();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_above", assertEntityTypeException.getMessage());
            return -1;
        }

        long aboveId = Grug.addEntity(GrugEntityType.BlockPos, above);

        GrugModLoader.logger.debug("Returning {}", aboveId);
        return aboveId;
    }

    public static long get_block_pos_above_n(long blockPosId, int n) {
        GrugModLoader.logger.debug("get_block_pos_above_n(blockPosId={}, n={})", blockPosId, n);

        BlockPos above;
        try {
            // The .above() call allocates a new BlockPos
            above = Grug.getBlockPos(blockPosId).above(n);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_above_n", assertEntityTypeException.getMessage());
            return -1;
        }

        long aboveId = Grug.addEntity(GrugEntityType.BlockPos, above);

        GrugModLoader.logger.debug("Returning {}", aboveId);
        return aboveId;
    }

    public static long get_block_pos_center(long blockPosId) {
        GrugModLoader.logger.debug("get_block_pos_center(blockPosId={})", blockPosId);

        Vec3 center;
        try {
            // The .getCenter() call allocates a new Vec3
            center = Grug.getBlockPos(blockPosId).getCenter();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_center", assertEntityTypeException.getMessage());
            return -1;
        }

        long centerId = Grug.addEntity(GrugEntityType.Vec3, center);

        GrugModLoader.logger.debug("Returning {}", centerId);
        return centerId;
    }

    public static long get_block_pos_of_block_entity(long blockEntityId) {
        GrugModLoader.logger.debug("get_block_pos_of_block_entity(blockEntityId={})", blockEntityId);

        BlockEntity blockEntity;
        try {
            blockEntity = Grug.getBlockEntity(blockEntityId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_of_block_entity", assertEntityTypeException.getMessage());
            return -1;
        }

        long blockPosId = Grug.addEntity(GrugEntityType.BlockPos, blockEntity.getBlockPos());

        GrugModLoader.logger.debug("Returning {}", blockPosId);
        return blockPosId;
    }

    public static int get_block_pos_x(long blockPosId) {
        GrugModLoader.logger.debug("get_block_pos_x(blockPosId={})", blockPosId);

        int x;
        try {
            x = Grug.getBlockPos(blockPosId).getX();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_x", assertEntityTypeException.getMessage());
            return -1;
        }

        GrugModLoader.logger.debug("Returning {}", x);
        return x;
    }

    public static int get_block_pos_y(long blockPosId) {
        GrugModLoader.logger.debug("get_block_pos_y(blockPosId={})", blockPosId);

        int y;
        try {
            y = Grug.getBlockPos(blockPosId).getY();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_y", assertEntityTypeException.getMessage());
            return -1;
        }

        GrugModLoader.logger.debug("Returning {}", y);
        return y;
    }

    public static int get_block_pos_z(long blockPosId) {
        GrugModLoader.logger.debug("get_block_pos_z(blockPosId={})", blockPosId);

        int z;
        try {
            z = Grug.getBlockPos(blockPosId).getZ();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_pos_z", assertEntityTypeException.getMessage());
            return -1;
        }

        GrugModLoader.logger.debug("Returning {}", z);
        return z;
    }

    public static long get_block_state(long blockPosId, long levelId) {
        GrugModLoader.logger.debug("get_block_state(blockPosId={}, levelId={})", blockPosId, levelId);

        BlockPos blockPos;
        Level level;
        try {
            blockPos = Grug.getBlockPos(blockPosId);
            level = Grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_block_state", assertEntityTypeException.getMessage());
            return -1;
        }

        BlockState blockState = level.getBlockState(blockPos);

        long blockStateId = Grug.addEntity(GrugEntityType.BlockState, blockState);

        GrugModLoader.logger.debug("Returning {}", blockStateId);
        return blockStateId;
    }

    public static long get_default_block_state(long blockId) {
        GrugModLoader.logger.debug("get_default_block_state(blockId={})", blockId);

        Block block;
        try {
            block = Grug.getBlock(blockId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_default_block_state", assertEntityTypeException.getMessage());
            return -1;
        }

        BlockState blockState = block.defaultBlockState();

        long blockStateId = Grug.addEntity(GrugEntityType.BlockState, blockState);

        GrugModLoader.logger.debug("Returning {}", blockStateId);
        return blockStateId;
    }

    public static int get_hash_map_size(long hashMapId) {
        GrugModLoader.logger.debug("get_hash_map_size(hashMapId={})", hashMapId);

        HashMap<GrugObject, GrugObject> hashMap;
        try {
            hashMap = Grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_hash_map_size", assertEntityTypeException.getMessage());
            return -1;
        }

        int size = hashMap.size();
        GrugModLoader.logger.debug("Returning {}", size);
        return size;
    }

    public static String get_hash_map_string(long hashMapId) {
        GrugModLoader.logger.debug("get_hash_map_string(hashMapId={})", hashMapId);

        HashMap<GrugObject, GrugObject> hashMap;
        try {
            hashMap = Grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_hash_map_string", assertEntityTypeException.getMessage());
            return "";
        }

        String str = hashMap.toString();
        GrugModLoader.logger.debug("Returning {}", str);
        return str;
    }

    public static int get_hash_set_size(long hashSetId) {
        GrugModLoader.logger.debug("get_hash_set_size(hashSetId={})", hashSetId);

        HashSet<GrugObject> hashSet;
        try {
            hashSet = Grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_hash_set_size", assertEntityTypeException.getMessage());
            return -1;
        }

        int size = hashSet.size();
        GrugModLoader.logger.debug("Returning {}", size);
        return size;
    }

    public static String get_hash_set_string(long hashSetId) {
        GrugModLoader.logger.debug("get_hash_set_string(hashSetId={})", hashSetId);

        HashSet<GrugObject> hashSet;
        try {
            hashSet = Grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_hash_set_string", assertEntityTypeException.getMessage());
            return "";
        }

        String str = hashSet.toString();
        GrugModLoader.logger.debug("Returning {}", str);
        return str;
    }

    public static String get_item_name(long itemId) {
        GrugModLoader.logger.debug("get_item_name(itemId={})", itemId);

        Item item;
        try {
            item = Grug.getItem(itemId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_item_name", assertEntityTypeException.getMessage());
            return "";
        }

        String name = item.getName(new ItemStack(item)).getString();
        GrugModLoader.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_item_entity_name(long itemEntityId) {
        GrugModLoader.logger.debug("get_item_entity_name(itemEntityId={})", itemEntityId);

        String name;
        try {
            name = Grug.getItemEntity(itemEntityId).getName().getString();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_item_entity_name", assertEntityTypeException.getMessage());
            return "";
        }

        GrugModLoader.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_item_stack_name(long itemStackId) {
        GrugModLoader.logger.debug("get_item_stack_name(itemStackId={})", itemStackId);

        ItemStack itemStack;
        try {
            itemStack = Grug.getItemStack(itemStackId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_item_stack_name", assertEntityTypeException.getMessage());
            return "";
        }

        // String name = itemStack.toString(); // "1 diamond"
        // String name = itemStack.getHoverName().getString(); // "Diamond"
        // String name = itemStack.getDisplayName().getString(); // "[Diamond]"
        String name = itemStack.getItem().getName(itemStack).getString(); // "Diamond"

        GrugModLoader.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_level_name(long levelId) {
        GrugModLoader.logger.debug("get_level_name(levelId={})", levelId);

        Level level;
        try {
            level = Grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_level_name", assertEntityTypeException.getMessage());
            return "";
        }

        String name = level.dimensionTypeRegistration().getRegisteredName();
        GrugModLoader.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_resource_location_string(long resourceLocationId) {
        GrugModLoader.logger.debug("get_resource_location_string(resourceLocationId={})", resourceLocationId);

        ResourceLocation resourceLocation;
        try {
            resourceLocation = Grug.getResourceLocation(resourceLocationId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_resource_location_string", assertEntityTypeException.getMessage());
            return "";
        }

        String str = resourceLocation.toString();
        GrugModLoader.logger.debug("Returning {}", str);
        return str;
    }

    public static float get_vec3_x(long vec3Id) {
        GrugModLoader.logger.debug("get_vec3_x(vec3Id={})", vec3Id);

        float x;
        try {
            x = (float)Grug.getVec3(vec3Id).x();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_vec3_x", assertEntityTypeException.getMessage());
            return -1;
        }

        GrugModLoader.logger.debug("Returning {}", x);
        return x;
    }

    public static float get_vec3_y(long vec3Id) {
        GrugModLoader.logger.debug("get_vec3_y(vec3Id={})", vec3Id);

        float y;
        try {
            y = (float)Grug.getVec3(vec3Id).y();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_vec3_y", assertEntityTypeException.getMessage());
            return -1;
        }

        GrugModLoader.logger.debug("Returning {}", y);
        return y;
    }

    public static float get_vec3_z(long vec3Id) {
        GrugModLoader.logger.debug("get_vec3_z(vec3Id={})", vec3Id);

        float z;
        try {
            z = (float)Grug.getVec3(vec3Id).z();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("get_vec3_z", assertEntityTypeException.getMessage());
            return -1;
        }

        GrugModLoader.logger.debug("Returning {}", z);
        return z;
    }

    public static long hash_map() {
        GrugModLoader.logger.debug("hash_map()");

        HashMap<GrugObject, GrugObject> hashMap = new HashMap<>();

        long hashMapId = Grug.addEntity(GrugEntityType.HashMap, hashMap);

        GrugModLoader.logger.debug("Returning {}", hashMapId);
        return hashMapId;
    }

    public static void hash_map_clear(long hashMapId) {
        GrugModLoader.logger.debug("hash_map_clear(hashMapId={})", hashMapId);

        HashMap<GrugObject, GrugObject> hashMap;
        try {
            hashMap = Grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_map_clear", assertEntityTypeException.getMessage());
            return;
        }

        hashMap.clear();
    }

    public static void hash_map_copy(long hashMapFromId, long hashMapToId) {
        GrugModLoader.logger.debug("hash_map_copy(hashMapFromId={}, hashMapToId={})", hashMapFromId, hashMapToId);

        HashMap<GrugObject, GrugObject> hashMapFrom;
        HashMap<GrugObject, GrugObject> hashMapTo;
        try {
            hashMapFrom = Grug.getHashMap(hashMapFromId);
            hashMapTo = Grug.getHashMap(hashMapToId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_map_copy", assertEntityTypeException.getMessage());
            return;
        }

        hashMapTo.clear();

        for (Entry<GrugObject, GrugObject> entry : hashMapFrom.entrySet()) {
            hashMapTo.put(entry.getKey(), entry.getValue());
        }
    }

    public static long hash_map_get(long hashMapId, long keyId) {
        GrugModLoader.logger.debug("hash_map_get(hashMapId={}, keyId={})", hashMapId, keyId);

        HashMap<GrugObject, GrugObject> hashMap;
        try {
            hashMap = Grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_map_get", assertEntityTypeException.getMessage());
            return -1;
        }

        GrugObject keyObject = Grug.getGrugObject(keyId);
        assert keyObject != null;

        GrugObject valueObject = hashMap.get(keyObject);
        if (valueObject == null) {
            Grug.gameFunctionErrorHappened("hash_map_get", "hash_map does not contain key");
            return -1;
        }

        // TODO: This calls `new GrugObject()`, which is unnecessary, as valueObject already exists, so create Grug.addExistingEntity()
        long valueId = Grug.addEntity(valueObject.type, valueObject.object);

        GrugModLoader.logger.debug("Returning {}", valueId);
        return valueId;
    }

    public static boolean hash_map_has_key(long hashMapId, long keyId) {
        GrugModLoader.logger.debug("hash_map_has_key(hashMapId={}, keyId={})", hashMapId, keyId);

        HashMap<GrugObject, GrugObject> objects;
        try {
            objects = Grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_map_has_key", assertEntityTypeException.getMessage());
            return false;
        }

        GrugObject object = Grug.getGrugObject(keyId);

        boolean hasKey = objects.containsKey(object);
        GrugModLoader.logger.debug("Returning {}", hasKey);
        return hasKey;
    }

    public static void hash_map_put(long hashMapId, long keyId, long valueId) {
        GrugModLoader.logger.debug("hash_map_put(hashMapId={}, keyId={}, valueId={})", hashMapId, keyId, valueId);

        HashMap<GrugObject, GrugObject> hashMap;
        try {
            hashMap = Grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_map_put", assertEntityTypeException.getMessage());
            return;
        }

        GrugObject keyObject = Grug.getGrugObject(keyId);

        GrugObject valueObject = Grug.getGrugObject(valueId);

        hashMap.put(keyObject, valueObject);
    }

    public static void hash_map_remove_key(long hashMapId, long keyId) {
        GrugModLoader.logger.debug("hash_map_remove_key(hashMapId={}, keyId={})", hashMapId, keyId);

        HashMap<GrugObject, GrugObject> hashMap;
        try {
            hashMap = Grug.getHashMap(hashMapId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_map_remove_key", assertEntityTypeException.getMessage());
            return;
        }

        hashMap.remove(Grug.getGrugObject(keyId));
    }

    public static long hash_set() {
        GrugModLoader.logger.debug("hash_set()");

        HashSet<GrugObject> hashSet = new HashSet<>();

        long hashSetId = Grug.addEntity(GrugEntityType.HashSet, hashSet);

        GrugModLoader.logger.debug("Returning {}", hashSetId);
        return hashSetId;
    }

    public static void hash_set_add(long hashSetId, long valueId) {
        GrugModLoader.logger.debug("hash_set_add(hashSetId={}, valueId={})", hashSetId, valueId);

        HashSet<GrugObject> hashSet;
        try {
            hashSet = Grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_set_add", assertEntityTypeException.getMessage());
            return;
        }

        hashSet.add(Grug.getGrugObject(valueId));
    }

    public static void hash_set_clear(long hashSetId) {
        GrugModLoader.logger.debug("hash_set_clear(hashSetId={})", hashSetId);

        HashSet<GrugObject> hashSet;
        try {
            hashSet = Grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_set_clear", assertEntityTypeException.getMessage());
            return;
        }

        hashSet.clear();
    }

    public static void hash_set_copy(long hashSetFromId, long hashSetToId) {
        GrugModLoader.logger.debug("hash_set_copy(hashSetFromId={}, hashSetToId={})", hashSetFromId, hashSetToId);

        HashSet<GrugObject> hashSetFrom;
        HashSet<GrugObject> hashSetTo;
        try {
            hashSetFrom = Grug.getHashSet(hashSetFromId);
            hashSetTo = Grug.getHashSet(hashSetToId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_set_copy", assertEntityTypeException.getMessage());
            return;
        }

        hashSetTo.clear();

        for (GrugObject value : hashSetFrom) {
            hashSetTo.add(value);
        }
    }

    public static boolean hash_set_has(long hashSetId, long valueId) {
        GrugModLoader.logger.debug("hash_set_has(hashSetId={}, valueId={})", hashSetId, valueId);

        HashSet<GrugObject> hashSet;
        try {
            hashSet = Grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_set_has", assertEntityTypeException.getMessage());
            return false;
        }

        GrugObject object = Grug.getGrugObject(valueId);

        boolean hasValue = hashSet.contains(object);
        GrugModLoader.logger.debug("Returning {}", hasValue);
        return hasValue;
    }

    public static void hash_set_remove(long hashSetId, long valueId) {
        GrugModLoader.logger.debug("hash_set_remove(hashSetId={}, valueId={})", hashSetId, valueId);

        HashSet<GrugObject> hashSet;
        try {
            hashSet = Grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("hash_set_remove", assertEntityTypeException.getMessage());
            return;
        }

        hashSet.remove(Grug.getGrugObject(valueId));
    }

    public static boolean is_air(long blockStateId) {
        GrugModLoader.logger.debug("is_air(blockStateId={})", blockStateId);

        BlockState blockState;
        try {
            blockState = Grug.getBlockState(blockStateId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("is_air", assertEntityTypeException.getMessage());
            return false;
        }

        boolean isAir = blockState.isAir();
        GrugModLoader.logger.debug("Returning {}", isAir);
        return isAir;
    }

    public static boolean is_client_side(long levelId) {
        GrugModLoader.logger.debug("is_client_side(levelId={})", levelId);

        Level level;
        try {
            level = Grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("is_client_side", assertEntityTypeException.getMessage());
            return false;
        }

        boolean isClientSide = level.isClientSide();
        GrugModLoader.logger.debug("Returning {}", isClientSide);
        return isClientSide;
    }

    public static long item(long resourceLocation) {
        GrugModLoader.logger.debug("item(resourceLocation={})", resourceLocation);

        Item item;
        try {
            ResourceLocation resourceLocationInstance = Grug.getResourceLocation(resourceLocation);

            if (!ForgeRegistries.ITEMS.containsKey(resourceLocationInstance)) {
                Grug.gameFunctionErrorHappened("item", "Invalid resource_location");
                return -1;
            }

            item = ForgeRegistries.ITEMS.getValue(resourceLocationInstance);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("item", assertEntityTypeException.getMessage());
            return -1;
        }

        long itemId = Grug.addEntity(GrugEntityType.Item, item);

        GrugModLoader.logger.debug("Returning {}", itemId);
        return itemId;
    }

    public static long item_entity(long level, float x, float y, float z, long itemStack) {
        GrugModLoader.logger.debug("item_entity(level={}, x={}, y={}, z={}, itemStack={})", level, x, y, z, itemStack);

        ItemEntity itemEntity;
        try {
            itemEntity = new ItemEntity(Grug.getLevel(level), x, y, z, Grug.getItemStack(itemStack));
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("item_entity", assertEntityTypeException.getMessage());
            return -1;
        }

        long itemEntityId = Grug.addEntity(GrugEntityType.ItemEntity, itemEntity);

        GrugModLoader.logger.debug("Returning {}", itemEntityId);
        return itemEntityId;
    }

    public static long item_stack(long item) {
        GrugModLoader.logger.debug("item_stack(item={})", item);

        ItemStack itemStack;
        try {
            itemStack = new ItemStack(Grug.getItem(item));
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("item_stack", assertEntityTypeException.getMessage());
            return -1;
        }

        long itemStackId = Grug.addEntity(GrugEntityType.ItemStack, itemStack);

        GrugModLoader.logger.debug("Returning {}", itemStackId);
        return itemStackId;
    }

    public static boolean iterating(long iteratorId) {
        GrugModLoader.logger.debug("iterating(iteratorId={})", iteratorId);

        GrugIterator grugIterator;
        try {
            grugIterator = Grug.getIterator(iteratorId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("iterating", assertEntityTypeException.getMessage());
            return false;
        }

        boolean hasNext = grugIterator.iterator.hasNext();
        GrugModLoader.logger.debug("Returning {}", hasNext);
        return hasNext;
    }

    public static long iteration(long iteratorId) {
        GrugModLoader.logger.debug("iteration(iteratorId={})", iteratorId);

        GrugIterator grugIterator;
        try {
            grugIterator = Grug.getIterator(iteratorId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("iteration", assertEntityTypeException.getMessage());
            return -1;
        }

        long element;

        try {
            if (grugIterator.iterableType == IterableType.HashMap) {
                Entry<GrugObject, GrugObject> entry;
                try {
                    @SuppressWarnings("unchecked")
                    Entry<GrugObject, GrugObject> localEntry = (Entry<GrugObject, GrugObject>)grugIterator.iterator.next();
                    entry = localEntry;
                } catch (ConcurrentModificationException err) {
                    Grug.gameFunctionErrorHappened("iteration", "The iterable was modified during iteration");
                    return -1;
                }

                element = Grug.addEntity(GrugEntityType.Entry, entry);
            } else {
                assert grugIterator.iterableType == IterableType.HashSet;

                GrugObject grugObject = (GrugObject)grugIterator.iterator.next();
                element = Grug.addEntity(grugObject.type, grugObject.object);
            }
        } catch (NoSuchElementException err) {
            Grug.gameFunctionErrorHappened("iteration", "Only call iteration() after iterating() returns true");
            return -1;
        }

        GrugModLoader.logger.debug("Returning {}", element);
        return element;
    }

    public static long iterator(long iterableId) {
        GrugModLoader.logger.debug("iterator(iterableId={})", iterableId);

        GrugEntityType containerType = Grug.getEntityType(iterableId);
        Iterator<?> iterator;
        IterableType iterableType;
        try {
            if (containerType == GrugEntityType.HashSet) {
                iterator = Grug.getHashSet(iterableId).iterator();
                iterableType = IterableType.HashSet;
            } else if (containerType == GrugEntityType.HashMap) {
                iterator = Grug.getHashMap(iterableId).entrySet().iterator();
                iterableType = IterableType.HashMap;
            } else {
                throw new AssertEntityTypeException(containerType);
            }
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("iterator", assertEntityTypeException.getMessage());
            return -1;
        }

        GrugIterator grugIterator = new GrugIterator(iterator, iterableType);
        long grugIteratorId = Grug.addEntity(GrugEntityType.Iterator, grugIterator);

        GrugModLoader.logger.debug("Returning {}", grugIteratorId);
        return grugIteratorId;
    }

    public static void iterator_remove(long iteratorId) {
        GrugModLoader.logger.debug("iterator_remove(iteratorId={})", iteratorId);

        GrugIterator grugIterator;
        try {
            grugIterator = Grug.getIterator(iteratorId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("iterator_remove", assertEntityTypeException.getMessage());
            return;
        }

        try {
            grugIterator.iterator.remove();
        } catch (IllegalStateException err) {
            Grug.gameFunctionErrorHappened("iterator_remove", "Can only be called once, per call to iteration()");
            return;
        }
    }

    public static void place_block(long blockStateId, long blockPosId, int flags, long levelId) {
        GrugModLoader.logger.debug("place_block(blockStateId={}, blockPosId={}, flags={}, levelId={})", blockStateId, blockPosId, flags, levelId);

        BlockState blockState;
        BlockPos blockPos;
        Level level;
        try {
            blockState = Grug.getBlockState(blockStateId);
            blockPos = Grug.getBlockPos(blockPosId);
            level = Grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("place_block", assertEntityTypeException.getMessage());
            return;
        }

        level.setBlock(blockPos, blockState, flags);
    }

    public static void print_bool(boolean b) {
        GrugModLoader.logger.debug("print_bool(b={})", b);

        Grug.sendMessageToEveryone(Component.literal(Boolean.toString(b)));
    }

    public static void print_f32(float f) {
        GrugModLoader.logger.debug("print_f32(f={})", f);

        Grug.sendMessageToEveryone(Component.literal(Float.toString(f)));
    }

    public static void print_i32(int n) {
        GrugModLoader.logger.debug("print_i32(n={})", n);

        Grug.sendMessageToEveryone(Component.literal(Integer.toString(n)));
    }

    public static void print_id(long id) {
        GrugModLoader.logger.debug("print_id(id={})", id);

        Grug.sendMessageToEveryone(Component.literal(Long.toString(id)));
    }

    public static void print_string(String str) {
        GrugModLoader.logger.debug("print_string(str={})", str);

        Grug.sendMessageToEveryone(Component.literal(str));
    }

    public static void remove_block(long blockPosId, long levelId) {
        GrugModLoader.logger.debug("remove_block(blockPosId={}, levelId={})", blockPosId, levelId);

        BlockPos blockPos;
        Level level;
        try {
            blockPos = Grug.getBlockPos(blockPosId);
            level = Grug.getLevel(levelId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("remove_block", assertEntityTypeException.getMessage());
            return;
        }

        level.removeBlock(blockPos, false);
    }

    public static long resource_location(String resourceLocationString) {
        GrugModLoader.logger.debug("resource_location(resourceLocationString={})", resourceLocationString);

        ResourceLocation resourceLocation;
        try {
            resourceLocation = new ResourceLocation(resourceLocationString);
        } catch (ResourceLocationException resourceLocationException) {
            Grug.gameFunctionErrorHappened("resource_location", "Invalid resource_location_string \"" + resourceLocationString + "\"");
            return -1;
        }

        long resourceLocationId = Grug.addEntity(GrugEntityType.ResourceLocation, resourceLocation);

        GrugModLoader.logger.debug("Returning {}", resourceLocationId);
        return resourceLocationId;
    }

    public static void set_entity_delta_movement(long entity, long vec3) {
        GrugModLoader.logger.debug("set_entity_delta_movement(entity={}, vec3={})", entity, vec3);

        Entity entityInstance;
        Vec3 vec3Instance;
        try {
            entityInstance = Grug.getEntity(entity);
            vec3Instance = Grug.getVec3(vec3);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("set_entity_delta_movement", assertEntityTypeException.getMessage());
            return;
        }

        entityInstance.setDeltaMovement(vec3Instance);
    }

    public static void spawn_entity(long entity, long level) {
        GrugModLoader.logger.debug("spawn_entity(entity={}, level={})", entity, level);

        Entity entityInstance;
        Level levelInstance;
        try {
            entityInstance = Grug.getEntity(entity);
            levelInstance = Grug.getLevel(level);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("spawn_entity", assertEntityTypeException.getMessage());
            return;
        }

        levelInstance.addFreshEntity(entityInstance);
    }

    public static int unbox_i32(long box) {
        GrugModLoader.logger.debug("unbox_i32(box={})", box);

        int unboxedI32;
        try {
            unboxedI32 = Grug.getBoxedI32(box);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.gameFunctionErrorHappened("unbox_i32", assertEntityTypeException.getMessage());
            return -1;
        }

        GrugModLoader.logger.debug("Returning {}", unboxedI32);
        return unboxedI32;
    }

    public static long vec3(float x, float y, float z) {
        GrugModLoader.logger.debug("vec3()");

        Vec3 vec3 = new Vec3(x, y, z);

        long vec3Id = Grug.addEntity(GrugEntityType.Vec3, vec3);

        GrugModLoader.logger.debug("Returning {}", vec3Id);
        return vec3Id;
    }

    public static long vec3_zero() {
        GrugModLoader.logger.debug("vec3_zero()");

        long vec3ZeroId = Grug.addEntity(GrugEntityType.Vec3, Vec3.ZERO);

        GrugModLoader.logger.debug("Returning {}", vec3ZeroId);
        return vec3ZeroId;
    }
}

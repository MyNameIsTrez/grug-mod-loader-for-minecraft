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

        ResourceLocation resourceLocationInstance = Grug.getResourceLocation(resourceLocation);

        if (!ForgeRegistries.BLOCKS.containsKey(resourceLocationInstance)) {
            Grug.gameFunctionErrorHappened("block", "Invalid resource_location");
            return -1;
        }

        Block block = ForgeRegistries.BLOCKS.getValue(resourceLocationInstance);

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

        BlockPos blockPos = Grug.getBlockPos(blockPosId);
        Level level = Grug.getLevel(levelId);

        level.destroyBlock(blockPos, true);
    }

    public static void destroy_block(long blockPosId, long levelId) {
        GrugModLoader.logger.debug("destroy_block(blockPosId={}, levelId={})", blockPosId, levelId);

        BlockPos blockPos = Grug.getBlockPos(blockPosId);
        Level level = Grug.getLevel(levelId);

        level.destroyBlock(blockPos, false);
    }

    public static long entry_key(long entryId) {
        GrugModLoader.logger.debug("entry_key(entryId={})", entryId);

        Entry<GrugObject, GrugObject> grugEntry = Grug.getEntry(entryId);

        GrugObject key = grugEntry.getKey();

        long keyId = Grug.addEntity(key.type, key.object);
        GrugModLoader.logger.debug("Returning {}", keyId);
        return keyId;
    }

    public static void entry_set_value(long entryId, long valueId) {
        GrugModLoader.logger.debug("entry_set_value(entryId={}, valueId={})", entryId, valueId);

        Entry<GrugObject, GrugObject> grugEntry = Grug.getEntry(entryId);

        GrugObject valueObject = Grug.getGrugObject(valueId);

        GrugObject oldValueObject = grugEntry.setValue(valueObject);
        assert oldValueObject != null;
    }

    public static long entry_value(long entryId) {
        GrugModLoader.logger.debug("entry_value(entryId={})", entryId);

        Entry<GrugObject, GrugObject> grugEntry = Grug.getEntry(entryId);

        GrugObject value = grugEntry.getValue();

        // TODO: This calls `new GrugObject()`, which is unnecessary, as valueObject already exists, so create Grug.addExistingEntity()
        long valueId = Grug.addEntity(value.type, value.object);

        GrugModLoader.logger.debug("Returning {}", valueId);
        return valueId;
    }

    public static long get_block_entity_level(long blockEntityId) {
        GrugModLoader.logger.debug("get_block_entity_level(blockEntityId={})", blockEntityId);

        Level level = Grug.getBlockEntity(blockEntityId).getLevel();
        assert level != null; // null when game tests forget to call .setLevel() on a BlockEntity

        long levelId = Grug.addEntity(GrugEntityType.Level, level);

        GrugModLoader.logger.debug("Returning {}", levelId);
        return levelId;
    }

    public static long get_block_pos_above(long blockPosId) {
        GrugModLoader.logger.debug("get_block_pos_above(blockPosId={})", blockPosId);

        // The .above() call allocates a new BlockPos
        BlockPos above = Grug.getBlockPos(blockPosId).above();

        long aboveId = Grug.addEntity(GrugEntityType.BlockPos, above);

        GrugModLoader.logger.debug("Returning {}", aboveId);
        return aboveId;
    }

    public static long get_block_pos_above_n(long blockPosId, int n) {
        GrugModLoader.logger.debug("get_block_pos_above_n(blockPosId={}, n={})", blockPosId, n);

        // The .above() call allocates a new BlockPos
        BlockPos above = Grug.getBlockPos(blockPosId).above(n);

        long aboveId = Grug.addEntity(GrugEntityType.BlockPos, above);

        GrugModLoader.logger.debug("Returning {}", aboveId);
        return aboveId;
    }

    public static long get_block_pos_center(long blockPosId) {
        GrugModLoader.logger.debug("get_block_pos_center(blockPosId={})", blockPosId);

        // The .getCenter() call allocates a new Vec3
        Vec3 center = Grug.getBlockPos(blockPosId).getCenter();

        long centerId = Grug.addEntity(GrugEntityType.Vec3, center);

        GrugModLoader.logger.debug("Returning {}", centerId);
        return centerId;
    }

    public static long get_block_pos_of_block_entity(long blockEntityId) {
        GrugModLoader.logger.debug("get_block_pos_of_block_entity(blockEntityId={})", blockEntityId);

        BlockEntity blockEntity = Grug.getBlockEntity(blockEntityId);

        long blockPosId = Grug.addEntity(GrugEntityType.BlockPos, blockEntity.getBlockPos());

        GrugModLoader.logger.debug("Returning {}", blockPosId);
        return blockPosId;
    }

    public static int get_block_pos_x(long blockPosId) {
        GrugModLoader.logger.debug("get_block_pos_x(blockPosId={})", blockPosId);

        int x = Grug.getBlockPos(blockPosId).getX();

        GrugModLoader.logger.debug("Returning {}", x);
        return x;
    }

    public static int get_block_pos_y(long blockPosId) {
        GrugModLoader.logger.debug("get_block_pos_y(blockPosId={})", blockPosId);

        int y = Grug.getBlockPos(blockPosId).getY();

        GrugModLoader.logger.debug("Returning {}", y);
        return y;
    }

    public static int get_block_pos_z(long blockPosId) {
        GrugModLoader.logger.debug("get_block_pos_z(blockPosId={})", blockPosId);

        int z = Grug.getBlockPos(blockPosId).getZ();

        GrugModLoader.logger.debug("Returning {}", z);
        return z;
    }

    public static long get_block_state(long blockPosId, long levelId) {
        GrugModLoader.logger.debug("get_block_state(blockPosId={}, levelId={})", blockPosId, levelId);

        BlockPos blockPos = Grug.getBlockPos(blockPosId);
        Level level = Grug.getLevel(levelId);

        BlockState blockState = level.getBlockState(blockPos);

        long blockStateId = Grug.addEntity(GrugEntityType.BlockState, blockState);

        GrugModLoader.logger.debug("Returning {}", blockStateId);
        return blockStateId;
    }

    public static long get_default_block_state(long blockId) {
        GrugModLoader.logger.debug("get_default_block_state(blockId={})", blockId);

        Block block = Grug.getBlock(blockId);

        BlockState blockState = block.defaultBlockState();

        long blockStateId = Grug.addEntity(GrugEntityType.BlockState, blockState);

        GrugModLoader.logger.debug("Returning {}", blockStateId);
        return blockStateId;
    }

    public static int get_hash_map_size(long hashMapId) {
        GrugModLoader.logger.debug("get_hash_map_size(hashMapId={})", hashMapId);

        HashMap<GrugObject, GrugObject> hashMap = Grug.getHashMap(hashMapId);

        int size = hashMap.size();
        GrugModLoader.logger.debug("Returning {}", size);
        return size;
    }

    public static String get_hash_map_string(long hashMapId) {
        GrugModLoader.logger.debug("get_hash_map_string(hashMapId={})", hashMapId);

        HashMap<GrugObject, GrugObject> hashMap = Grug.getHashMap(hashMapId);

        String str = hashMap.toString();
        GrugModLoader.logger.debug("Returning {}", str);
        return str;
    }

    public static int get_hash_set_size(long hashSetId) {
        GrugModLoader.logger.debug("get_hash_set_size(hashSetId={})", hashSetId);

        HashSet<GrugObject> hashSet = Grug.getHashSet(hashSetId);

        int size = hashSet.size();
        GrugModLoader.logger.debug("Returning {}", size);
        return size;
    }

    public static String get_hash_set_string(long hashSetId) {
        GrugModLoader.logger.debug("get_hash_set_string(hashSetId={})", hashSetId);

        HashSet<GrugObject> hashSet = Grug.getHashSet(hashSetId);

        String str = hashSet.toString();
        GrugModLoader.logger.debug("Returning {}", str);
        return str;
    }

    public static String get_item_name(long itemId) {
        GrugModLoader.logger.debug("get_item_name(itemId={})", itemId);

        Item item = Grug.getItem(itemId);

        String name = item.getName(new ItemStack(item)).getString();
        GrugModLoader.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_item_entity_name(long itemEntityId) {
        GrugModLoader.logger.debug("get_item_entity_name(itemEntityId={})", itemEntityId);

        String name = Grug.getItemEntity(itemEntityId).getName().getString();

        GrugModLoader.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_item_stack_name(long itemStackId) {
        GrugModLoader.logger.debug("get_item_stack_name(itemStackId={})", itemStackId);

        ItemStack itemStack = Grug.getItemStack(itemStackId);

        // String name = itemStack.toString(); // "1 diamond"
        // String name = itemStack.getHoverName().getString(); // "Diamond"
        // String name = itemStack.getDisplayName().getString(); // "[Diamond]"
        String name = itemStack.getItem().getName(itemStack).getString(); // "Diamond"

        GrugModLoader.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_level_name(long levelId) {
        GrugModLoader.logger.debug("get_level_name(levelId={})", levelId);

        Level level = Grug.getLevel(levelId);

        String name = level.dimensionTypeRegistration().getRegisteredName();
        GrugModLoader.logger.debug("Returning {}", name);
        return name;
    }

    public static String get_resource_location_string(long resourceLocationId) {
        GrugModLoader.logger.debug("get_resource_location_string(resourceLocationId={})", resourceLocationId);

        ResourceLocation resourceLocation = Grug.getResourceLocation(resourceLocationId);

        String str = resourceLocation.toString();
        GrugModLoader.logger.debug("Returning {}", str);
        return str;
    }

    public static float get_vec3_x(long vec3Id) {
        GrugModLoader.logger.debug("get_vec3_x(vec3Id={})", vec3Id);

        float x = (float)Grug.getVec3(vec3Id).x();

        GrugModLoader.logger.debug("Returning {}", x);
        return x;
    }

    public static float get_vec3_y(long vec3Id) {
        GrugModLoader.logger.debug("get_vec3_y(vec3Id={})", vec3Id);

        float y = (float)Grug.getVec3(vec3Id).y();

        GrugModLoader.logger.debug("Returning {}", y);
        return y;
    }

    public static float get_vec3_z(long vec3Id) {
        GrugModLoader.logger.debug("get_vec3_z(vec3Id={})", vec3Id);

        float z = (float)Grug.getVec3(vec3Id).z();

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

        HashMap<GrugObject, GrugObject> hashMap = Grug.getHashMap(hashMapId);

        hashMap.clear();
    }

    public static void hash_map_copy(long hashMapFromId, long hashMapToId) {
        GrugModLoader.logger.debug("hash_map_copy(hashMapFromId={}, hashMapToId={})", hashMapFromId, hashMapToId);

        HashMap<GrugObject, GrugObject> hashMapFrom = Grug.getHashMap(hashMapFromId);
        HashMap<GrugObject, GrugObject> hashMapTo = Grug.getHashMap(hashMapToId);

        hashMapTo.clear();

        for (Entry<GrugObject, GrugObject> entry : hashMapFrom.entrySet()) {
            hashMapTo.put(entry.getKey(), entry.getValue());
        }
    }

    public static long hash_map_get(long hashMapId, long keyId) {
        GrugModLoader.logger.debug("hash_map_get(hashMapId={}, keyId={})", hashMapId, keyId);

        HashMap<GrugObject, GrugObject> hashMap = Grug.getHashMap(hashMapId);

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

        HashMap<GrugObject, GrugObject> objects = Grug.getHashMap(hashMapId);

        GrugObject object = Grug.getGrugObject(keyId);

        boolean hasKey = objects.containsKey(object);
        GrugModLoader.logger.debug("Returning {}", hasKey);
        return hasKey;
    }

    public static void hash_map_put(long hashMapId, long keyId, long valueId) {
        GrugModLoader.logger.debug("hash_map_put(hashMapId={}, keyId={}, valueId={})", hashMapId, keyId, valueId);

        HashMap<GrugObject, GrugObject> hashMap = Grug.getHashMap(hashMapId);

        GrugObject keyObject = Grug.getGrugObject(keyId);

        GrugObject valueObject = Grug.getGrugObject(valueId);

        hashMap.put(keyObject, valueObject);
    }

    public static void hash_map_remove_key(long hashMapId, long keyId) {
        GrugModLoader.logger.debug("hash_map_remove_key(hashMapId={}, keyId={})", hashMapId, keyId);

        HashMap<GrugObject, GrugObject> hashMap = Grug.getHashMap(hashMapId);

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

        HashSet<GrugObject> hashSet = Grug.getHashSet(hashSetId);

        hashSet.add(Grug.getGrugObject(valueId));
    }

    public static void hash_set_clear(long hashSetId) {
        GrugModLoader.logger.debug("hash_set_clear(hashSetId={})", hashSetId);

        HashSet<GrugObject> hashSet = Grug.getHashSet(hashSetId);

        hashSet.clear();
    }

    public static void hash_set_copy(long hashSetFromId, long hashSetToId) {
        GrugModLoader.logger.debug("hash_set_copy(hashSetFromId={}, hashSetToId={})", hashSetFromId, hashSetToId);

        HashSet<GrugObject> hashSetFrom = Grug.getHashSet(hashSetFromId);
        HashSet<GrugObject> hashSetTo = Grug.getHashSet(hashSetToId);

        hashSetTo.clear();

        for (GrugObject value : hashSetFrom) {
            hashSetTo.add(value);
        }
    }

    public static boolean hash_set_has(long hashSetId, long valueId) {
        GrugModLoader.logger.debug("hash_set_has(hashSetId={}, valueId={})", hashSetId, valueId);

        HashSet<GrugObject> hashSet = Grug.getHashSet(hashSetId);

        GrugObject object = Grug.getGrugObject(valueId);

        boolean hasValue = hashSet.contains(object);
        GrugModLoader.logger.debug("Returning {}", hasValue);
        return hasValue;
    }

    public static void hash_set_remove(long hashSetId, long valueId) {
        GrugModLoader.logger.debug("hash_set_remove(hashSetId={}, valueId={})", hashSetId, valueId);

        HashSet<GrugObject> hashSet = Grug.getHashSet(hashSetId);

        hashSet.remove(Grug.getGrugObject(valueId));
    }

    public static long id_to_block_pos(long id) {
        GrugModLoader.logger.debug("id_to_block_pos(id={})", id);

        try {
            Grug.assertEntityType(id, GrugEntityType.BlockPos);
        } catch (AssertEntityTypeException e) {
            Grug.gameFunctionErrorHappened("id_to_block_pos", e.getMessage());
            return -1;
        }

        GrugModLoader.logger.debug("Returning {}", id);
        return id;
    }

    public static long id_to_boxed_i32(long id) {
        GrugModLoader.logger.debug("id_to_boxed_i32(id={})", id);

        try {
            Grug.assertEntityType(id, GrugEntityType.BoxedI32);
        } catch (AssertEntityTypeException e) {
            Grug.gameFunctionErrorHappened("id_to_boxed_i32", e.getMessage());
            return -1;
        }

        GrugModLoader.logger.debug("Returning {}", id);
        return id;
    }

    public static boolean is_air(long blockStateId) {
        GrugModLoader.logger.debug("is_air(blockStateId={})", blockStateId);

        BlockState blockState = Grug.getBlockState(blockStateId);

        boolean isAir = blockState.isAir();
        GrugModLoader.logger.debug("Returning {}", isAir);
        return isAir;
    }

    public static boolean is_client_side(long levelId) {
        GrugModLoader.logger.debug("is_client_side(levelId={})", levelId);

        Level level = Grug.getLevel(levelId);

        boolean isClientSide = level.isClientSide();
        GrugModLoader.logger.debug("Returning {}", isClientSide);
        return isClientSide;
    }

    public static long item(long resourceLocation) {
        GrugModLoader.logger.debug("item(resourceLocation={})", resourceLocation);

        ResourceLocation resourceLocationInstance = Grug.getResourceLocation(resourceLocation);

        if (!ForgeRegistries.ITEMS.containsKey(resourceLocationInstance)) {
            Grug.gameFunctionErrorHappened("item", "Invalid resource_location");
            return -1;
        }

        Item item = ForgeRegistries.ITEMS.getValue(resourceLocationInstance);

        long itemId = Grug.addEntity(GrugEntityType.Item, item);

        GrugModLoader.logger.debug("Returning {}", itemId);
        return itemId;
    }

    public static long item_entity(long level, float x, float y, float z, long itemStack) {
        GrugModLoader.logger.debug("item_entity(level={}, x={}, y={}, z={}, itemStack={})", level, x, y, z, itemStack);

        ItemEntity itemEntity = new ItemEntity(Grug.getLevel(level), x, y, z, Grug.getItemStack(itemStack));

        long itemEntityId = Grug.addEntity(GrugEntityType.ItemEntity, itemEntity);

        GrugModLoader.logger.debug("Returning {}", itemEntityId);
        return itemEntityId;
    }

    // TODO: Remove this function, once inheritance is handled by mod_api.json!
    public static long item_entity_to_entity(long itemEntity) {
        GrugModLoader.logger.debug("item_entity_to_entity(itemEntity={})", itemEntity);

        // TODO: Should this return a new instance?
        // Entity entity = Grug.getItemEntity(itemEntity);
        // long entityId = Grug.addEntity(GrugEntityType.Entity, entity);
        long entityId = itemEntity;

        GrugModLoader.logger.debug("Returning {}", entityId);
        return entityId;
    }

    public static long item_stack(long item) {
        GrugModLoader.logger.debug("item_stack(item={})", item);

        ItemStack itemStack = new ItemStack(Grug.getItem(item));

        long itemStackId = Grug.addEntity(GrugEntityType.ItemStack, itemStack);

        GrugModLoader.logger.debug("Returning {}", itemStackId);
        return itemStackId;
    }

    public static boolean iterating(long iteratorId) {
        GrugModLoader.logger.debug("iterating(iteratorId={})", iteratorId);

        GrugIterator grugIterator = Grug.getIterator(iteratorId);

        boolean hasNext = grugIterator.iterator.hasNext();
        GrugModLoader.logger.debug("Returning {}", hasNext);
        return hasNext;
    }

    public static long iteration(long iteratorId) {
        GrugModLoader.logger.debug("iteration(iteratorId={})", iteratorId);

        GrugIterator grugIterator = Grug.getIterator(iteratorId);

        long element;

        try {
            if (grugIterator.iterableType == IterableType.HashMap) {
                Entry<GrugObject, GrugObject> entry;
                try {
                    @SuppressWarnings("unchecked")
                    Entry<GrugObject, GrugObject> localEntry = (Entry<GrugObject, GrugObject>)grugIterator.iterator.next();
                    entry = localEntry;
                } catch (ConcurrentModificationException e) {
                    Grug.gameFunctionErrorHappened("iteration", "The iterable was modified during iteration");
                    return -1;
                }

                element = Grug.addEntity(GrugEntityType.Entry, entry);
            } else {
                assert grugIterator.iterableType == IterableType.HashSet;

                GrugObject grugObject = (GrugObject)grugIterator.iterator.next();
                element = Grug.addEntity(grugObject.type, grugObject.object);
            }
        } catch (NoSuchElementException e) {
            Grug.gameFunctionErrorHappened("iteration", "Only call iteration() after iterating() returns true");
            return -1;
        }

        GrugModLoader.logger.debug("Returning {}", element);
        return element;
    }

    public static long iterator_hash_map(long hashMapId) {
        GrugModLoader.logger.debug("iterator_hash_map(hashMapId={})", hashMapId);

        Iterator<?> iterator = Grug.getHashMap(hashMapId).entrySet().iterator();

        GrugIterator grugIterator = new GrugIterator(iterator, IterableType.HashMap);
        long grugIteratorId = Grug.addEntity(GrugEntityType.Iterator, grugIterator);

        GrugModLoader.logger.debug("Returning {}", grugIteratorId);
        return grugIteratorId;
    }

    public static long iterator_hash_set(long hashSetId) {
        GrugModLoader.logger.debug("iterator_hash_set(hashSetId={})", hashSetId);

        Iterator<?> iterator = Grug.getHashSet(hashSetId).iterator();

        GrugIterator grugIterator = new GrugIterator(iterator, IterableType.HashSet);
        long grugIteratorId = Grug.addEntity(GrugEntityType.Iterator, grugIterator);

        GrugModLoader.logger.debug("Returning {}", grugIteratorId);
        return grugIteratorId;
    }

    public static void iterator_remove(long iteratorId) {
        GrugModLoader.logger.debug("iterator_remove(iteratorId={})", iteratorId);

        GrugIterator grugIterator = Grug.getIterator(iteratorId);

        try {
            grugIterator.iterator.remove();
        } catch (IllegalStateException e) {
            Grug.gameFunctionErrorHappened("iterator_remove", "Can only be called once, per call to iteration()");
            return;
        }
    }

    public static void place_block(long blockStateId, long blockPosId, int flags, long levelId) {
        GrugModLoader.logger.debug("place_block(blockStateId={}, blockPosId={}, flags={}, levelId={})", blockStateId, blockPosId, flags, levelId);

        BlockState blockState = Grug.getBlockState(blockStateId);
        BlockPos blockPos = Grug.getBlockPos(blockPosId);
        Level level = Grug.getLevel(levelId);

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

        BlockPos blockPos = Grug.getBlockPos(blockPosId);
        Level level = Grug.getLevel(levelId);

        level.removeBlock(blockPos, false);
    }

    public static long resource_location(String resourceLocationString) {
        GrugModLoader.logger.debug("resource_location(resourceLocationString={})", resourceLocationString);

        ResourceLocation resourceLocation;
        try {
            resourceLocation = new ResourceLocation(resourceLocationString);
        } catch (ResourceLocationException e) {
            Grug.gameFunctionErrorHappened("resource_location", "Invalid resource_location_string \"" + resourceLocationString + "\"");
            return -1;
        }

        long resourceLocationId = Grug.addEntity(GrugEntityType.ResourceLocation, resourceLocation);

        GrugModLoader.logger.debug("Returning {}", resourceLocationId);
        return resourceLocationId;
    }

    public static void set_entity_delta_movement(long entity, long vec3) {
        GrugModLoader.logger.debug("set_entity_delta_movement(entity={}, vec3={})", entity, vec3);

        Entity entityInstance = Grug.getEntity(entity);
        Vec3 vec3Instance = Grug.getVec3(vec3);

        entityInstance.setDeltaMovement(vec3Instance);
    }

    public static void spawn_entity(long entity, long level) {
        GrugModLoader.logger.debug("spawn_entity(entity={}, level={})", entity, level);

        Entity entityInstance = Grug.getEntity(entity);
        Level levelInstance = Grug.getLevel(level);

        boolean spawned = levelInstance.addFreshEntity(entityInstance);

        if (!spawned) {
            Grug.gameFunctionErrorHappened("spawn_entity", "Failed to spawn");
        }
    }

    public static int unbox_i32(long box) {
        GrugModLoader.logger.debug("unbox_i32(box={})", box);

        int unboxedI32 = Grug.getBoxedI32(box);

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

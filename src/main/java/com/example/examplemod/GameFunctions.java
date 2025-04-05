package com.example.examplemod;

import java.util.HashSet;
import java.util.Iterator;

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

    private static int get_hash_set_size(long hashSetId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_remove", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        return hashSet.size();
    }

    private static String get_hash_set_string(long hashSetId) {
        if (Grug.gameFunctionErrorHappened) {
            return "";
        }

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_remove", assertEntityTypeException.getMessage());
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

    private static long hash_set() {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        HashSet<Long> hashSet = new HashSet<Long>();

        long hashSetId = Grug.addEntity(EntityType.HashSet, hashSet);
        Grug.fnEntities.add(hashSetId);

        return hashSetId;
    }

    private static void hash_set_add(long hashSetId, long id) {
        if (Grug.gameFunctionErrorHappened) {
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

        hashSet.add(id);
    }

    private static void hash_set_clear(long hashSetId) {
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

        hashSet.clear();
    }

    private static void hash_set_copy(long hashSetFromId, long hashSetToId) {
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

        hashSetTo.clear();
        hashSetTo.addAll(hashSetFrom);
    }

    private static boolean hash_set_has(long hashSetId, long id) {
        if (Grug.gameFunctionErrorHappened) {
            return false;
        }

        HashSet<Long> hashSet;
        try {
            hashSet = ExampleMod.grug.getHashSet(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("hash_set_has", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return false;
        }

        return hashSet.contains(id);
    }

    private static void hash_set_remove(long hashSetId, long id) {
        if (Grug.gameFunctionErrorHappened) {
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

        hashSet.remove(id);
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

        Iterator<Long> iterator;
        try {
            iterator = ExampleMod.grug.getIterator(iteratorId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iterating", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return false;
        }

        return iterator.hasNext();
    }

    private static long iteration(long iteratorId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Iterator<Long> iterator;
        try {
            iterator = ExampleMod.grug.getIterator(iteratorId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iteration", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        return iterator.next();
    }

    private static long iterator(long iterable) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Iterator<Long> iterator;
        try {
            EntityType entityType = Grug.getEntityType(iterable);

            if (!Grug.isEntityTypeInstanceOf(entityType, EntityType.HashSet)) {
                Grug.sendGameFunctionErrorToEveryone("iterator", "Expected hash_set, but got " + StringUtils.getSnakeCase(entityType));
                Grug.gameFunctionErrorHappened = true;
                return 0;
            }

            iterator = ExampleMod.grug.getHashSet(iterable).iterator();
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("iterator", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }

        long iteratorId = Grug.addEntity(EntityType.Iterator, iterator);
        Grug.fnEntities.add(iteratorId);

        return iteratorId;
    }

    private static void place_block(long blockStateId, long blockPosId, long levelId) {
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

        level.setBlock(blockPos, blockState, Block.UPDATE_ALL);
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

        // Not using .removeBlock(), as its second arg is "boolean isMoving"
        boolean dropBlock = false;
        level.destroyBlock(blockPos, dropBlock);
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

    private static long vec3_zero() {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        long vec3ZeroId = Grug.addEntity(EntityType.Vec3, Vec3.ZERO);
        Grug.fnEntities.add(vec3ZeroId);
        return vec3ZeroId;
    }
}

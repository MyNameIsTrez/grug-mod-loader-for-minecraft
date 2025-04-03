package com.example.examplemod;

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
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

class GameFunctions {
    private static long block(long resourceLocation) {
        return 0;
    }

    private static long block_pos(int x, int y, int z) {
        return 0;
    }

    private static long get_block_entity_level(long blockEntityId) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        Level level;
        try {
            // This does not allocate a new Level
            level = ExampleMod.grug.getGrugBlockEntity(blockEntityId).getLevel();
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

    private static long get_block_state(long blockPos, long level) {
        return 0;
    }

    private static long get_default_block_state(long block) {
        return 0;
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
            return ExampleMod.grug.getGrugBlockEntity(blockEntityId).worldPositionId;
        } catch (AssertEntityTypeException assertEntityTypeException) {
            Grug.sendGameFunctionErrorToEveryone("get_world_position_of_block_entity", assertEntityTypeException.getMessage());
            Grug.gameFunctionErrorHappened = true;
            return 0;
        }
    }

    private static long hash_set() {
        return 0;
    }

    private static void hash_set_add(long hashSet, long id) {
    }

    private static void hash_set_clear(long hashSet) {
    }

    private static void hash_set_copy(long hashSetFrom, long hashSetTo) {
    }

    private static boolean hash_set_has(long hashSet, long id) {
        return false;
    }

    private static void hash_set_remove(long hashSet, long id) {
    }

    private static boolean is_air(long blockState) {
        return false;
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

    private static boolean iterating(long iterator) {
        return false;
    }

    private static long iteration(long iterator) {
        return 0;
    }

    private static long iterator(long iterable) {
        return 0;
    }

    private static void place_block(long pos, long blockState) {
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

    private static void remove_block(long pos) {
    }

    private static long resource_location(String resourceLocationString) {
        if (Grug.gameFunctionErrorHappened) {
            return 0;
        }

        ResourceLocation resourceLocation;
        try {
            resourceLocation = new ResourceLocation(resourceLocationString);
        } catch (ResourceLocationException resourceLocationException) {
            Grug.sendGameFunctionErrorToEveryone("get_resource_location", "invalid resource_location_string argument");
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

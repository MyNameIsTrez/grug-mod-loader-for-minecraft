package com.example.examplemod;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.registries.ForgeRegistries;

@GameTestHolder(ExampleMod.MODID)
public class GameTests {
    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void block(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long resourceLocation = GameFunctions.resource_location("white_concrete");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long block = GameFunctions.block(resourceLocation);
        helper.assertTrue(block != -1, "Invalid block " + block);

        helper.assertTrue(Grug.fnEntities.contains(block), "fnEntities did not contain block " + block);

        helper.succeed();
    }

    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void block_pos(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long blockPos = GameFunctions.block_pos(0, 0, 0);
        helper.assertTrue(blockPos != -1, "Invalid blockPos " + blockPos);

        helper.assertTrue(Grug.fnEntities.contains(blockPos), "fnEntities did not contain blockPos " + blockPos);

        helper.succeed();
    }

    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void block_flag_update_all(GameTestHelper helper) {
        int flag = GameFunctions.block_flag_update_all();
        helper.assertTrue(flag == Block.UPDATE_ALL, "Did not return Block.UPDATE_ALL, but " + flag);

        helper.succeed();
    }

    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void box_i32(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long boxId = GameFunctions.box_i32(0);

        helper.assertTrue(Grug.fnEntities.contains(boxId), "fnEntities did not contain boxId " + boxId);

        helper.succeed();
    }

    @GameTest(template = ExampleMod.MODID+":diamond_block")
    public static void destroy_block(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        ResourceLocation resourceLocation = new ResourceLocation("diamond_block");

        Block block = ForgeRegistries.BLOCKS.getValue(resourceLocation);

        BlockPos relativeStructureBlockPos = new BlockPos(0, 1, 0);

        helper.assertBlockPresent(block, relativeStructureBlockPos);

        BlockPos absoluteDiamondBlockPos = helper.absolutePos(relativeStructureBlockPos);

        long blockPosId = GameFunctions.block_pos(absoluteDiamondBlockPos.getX(), absoluteDiamondBlockPos.getY(), absoluteDiamondBlockPos.getZ());

        ServerLevel level = helper.getLevel();

        long levelId = Grug.addEntity(EntityType.Level, level);

        GameFunctions.remove_block(blockPosId, levelId);

        helper.assertBlockNotPresent(block, relativeStructureBlockPos);

        helper.succeed();
    }

    /*
    This code is tested:
    ```grug
    hash_set: id = hash_set()

    on_a() {
        hash_set_add(hash_set, box_i32(1))
    }

    on_b() {
        hash_set_add(hash_set, box_i32(2))

        assert_hash_set_has_global_entities(hash_set, box_i32(1))
        assert_hash_set_has_global_entities(hash_set, box_i32(2))
    }
    ```
    */
    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void hash_set_add_to_global_set(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = Grug.globalEntities;

        long hashSetId = GameFunctions.hash_set();

        Grug.fnEntities = new HashSet<>();

        long boxId1 = GameFunctions.box_i32(1);
        GameFunctions.hash_set_add(hashSetId, boxId1);

        Grug.fnEntities = new HashSet<>();

        long boxId2 = GameFunctions.box_i32(2);
        GameFunctions.hash_set_add(hashSetId, boxId2);

        String hashSetString = GameFunctions.get_hash_set_string(hashSetId);
        helper.assertTrue(hashSetString.equals("[17179869185, 17179869187]"), "Got unexpected hashSetString value '" + hashSetString + "'");

        helper.assertTrue(Grug.globalEntities.size() == 3, "Grug.globalEntities.size() was expected to be 3, but was " + Grug.globalEntities.size());

        helper.assertTrue(Grug.globalEntities.contains(hashSetId), "Grug.globalEntities did not contain hashSetId " + hashSetId);

        HashMap<Object, Long> objects = Grug.getHashSetObjects(hashSetId);

        Object boxId1Object = ExampleMod.grug.getObject(boxId1);
        helper.assertTrue(boxId1Object != null, "boxId1Object was not supposed to be null");
        Long realBoxId1 = objects.get(boxId1Object);
        helper.assertTrue(realBoxId1 != null, "realBoxId1 was not supposed to be null");
        helper.assertTrue(Grug.globalEntities.contains(realBoxId1), "Grug.globalEntities did not contain realBoxId1 " + realBoxId1);

        Object boxId2Object = ExampleMod.grug.getObject(boxId2);
        helper.assertTrue(boxId2Object != null, "boxId2Object was not supposed to be null");
        Long realBoxId2 = objects.get(boxId2Object);
        helper.assertTrue(realBoxId2 != null, "realBoxId2 was not supposed to be null");
        helper.assertTrue(Grug.globalEntities.contains(realBoxId2), "Grug.globalEntities did not contain realBoxId2 " + realBoxId2);

        helper.succeed();
    }
}

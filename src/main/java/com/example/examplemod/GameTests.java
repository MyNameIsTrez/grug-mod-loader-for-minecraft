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
    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        resource_location: id = resource_location("white_concrete")

        block: id = block(resource_location)

        assert_fn_entities_contains(block)
    }
    ```
    */
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

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        box_id: id = box_i32(1)

        block: id = block_nullable(box_id)
        assert(block == null_id)
    }
    ```
    */
    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void block_error_expected_resource_location(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long boxId = GameFunctions.box_i32(1);
        helper.assertTrue(boxId != -1, "Invalid boxId " + boxId);

        long block = GameFunctions.block(boxId);
        helper.assertTrue(block == -1, "Expected an invalid block, but got " + block);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        resource_location: id = resource_location("diamond")

        block: id = block_nullable(resource_location)
        assert(block == null_id)
    }
    ```
    */
    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void block_error_invalid_resource_location(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long resourceLocation = GameFunctions.resource_location("diamond");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long block = GameFunctions.block(resourceLocation);
        helper.assertTrue(block == -1, "Expected an invalid block, but got " + block);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        block_pos: id = block_pos(0, 0, 0)

        assert_fn_entities_contains(block_pos)
    }
    ```
    */
    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void block_pos(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long blockPos = GameFunctions.block_pos(0, 0, 0);
        helper.assertTrue(blockPos != -1, "Invalid blockPos " + blockPos);

        helper.assertTrue(Grug.fnEntities.contains(blockPos), "fnEntities did not contain blockPos " + blockPos);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        # I'm not sure how assert_enum() would be implemented in Java
        assert_enum(block_flag_update_all(), "Block.UPDATE_ALL")
    }
    ```
    */
    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void block_flag_update_all(GameTestHelper helper) {
        int flag = GameFunctions.block_flag_update_all();
        helper.assertTrue(flag == Block.UPDATE_ALL, "Did not return Block.UPDATE_ALL, but " + flag);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        box_id: id = box_i32(1)

        assert_fn_entities_contains(box_id)
    }
    ```
    */
    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void box_i32(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long boxId = GameFunctions.box_i32(0);
        helper.assertTrue(boxId != -1, "Invalid boxId " + boxId);

        helper.assertTrue(Grug.fnEntities.contains(boxId), "fnEntities did not contain boxId " + boxId);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        resource_location: id = resource_location("diamond_block")

        block: id = block(resource_location)

        relative_structure_block_pos: id = block_pos(0, 1, 0)

        assert_block_present(block, relative_structure_block_pos)

        absolute_diamond_block_pos: id = absolute_pos(relative_structure_block_pos)

        x: i32 = get_block_pos_x(absolute_diamond_block_pos)
        y: i32 = get_block_pos_y(absolute_diamond_block_pos)
        z: i32 = get_block_pos_z(absolute_diamond_block_pos)

        block_pos: id = block_pos(x, y, z)

        level: id = get_game_test_info_server_level()

        remove_block(block_pos, level)

        assert_block_not_present(block, relative_structure_block_pos)
    }
    ```
    */
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
        helper.assertTrue(blockPosId != -1, "Invalid blockPosId " + blockPosId);

        ServerLevel level = helper.getLevel();

        long levelId = Grug.addEntity(EntityType.Level, level);

        GameFunctions.remove_block(blockPosId, levelId);

        helper.assertBlockNotPresent(block, relativeStructureBlockPos);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        hash_set: id = hash_set()

        hash_set_add(hash_set, box_i32(1))

        assert(hash_set_has(hash_set, box_i32(1)))
    }
    ```
    */
    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void hash_set(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = new HashSet<>();

        long hashSetId = GameFunctions.hash_set();
        helper.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        long boxId = GameFunctions.box_i32(1);
        helper.assertTrue(boxId != -1, "Invalid boxId " + boxId);
        GameFunctions.hash_set_add(hashSetId, boxId);

        long boxIdSecond = GameFunctions.box_i32(1);
        helper.assertTrue(boxIdSecond != -1, "Invalid boxIdSecond " + boxIdSecond);

        helper.assertTrue(GameFunctions.hash_set_has(hashSetId, boxIdSecond), "hashSetId did not contain boxIdSecond " + boxIdSecond);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        hash_set: id = hash_set()

        hash_set_add(hash_set, box_i32(1))

        assert(hash_set_has(hash_set, box_i32(1)))
    }
    ```
    */
    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void hash_set_add(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = new HashSet<>();

        long hashSetId = GameFunctions.hash_set();
        helper.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        long boxId = GameFunctions.box_i32(1);
        helper.assertTrue(boxId != -1, "Invalid boxId " + boxId);
        GameFunctions.hash_set_add(hashSetId, boxId);

        long boxIdSecond = GameFunctions.box_i32(1);
        helper.assertTrue(boxIdSecond != -1, "Invalid boxIdSecond " + boxIdSecond);

        helper.assertTrue(GameFunctions.hash_set_has(hashSetId, boxIdSecond), "hashSetId did not contain boxIdSecond " + boxIdSecond);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    hash_set: id = hash_set()

    on_a() {
        hash_set_add(hash_set, box_i32(1))
    }

    on_b() {
        hash_set_add(hash_set, box_i32(2))

        assert_hash_set_contains_global_entity(hash_set, box_i32(1))
        assert_hash_set_contains_global_entity(hash_set, box_i32(2))
    }
    ```
    */
    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void hash_set_add_to_global_set(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = Grug.globalEntities;

        long hashSetId = GameFunctions.hash_set();
        helper.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        Grug.fnEntities = new HashSet<>();

        long boxId1 = GameFunctions.box_i32(1);
        helper.assertTrue(boxId1 != -1, "Invalid boxId1 " + boxId1);
        GameFunctions.hash_set_add(hashSetId, boxId1);

        Grug.fnEntities = new HashSet<>();

        long boxId2 = GameFunctions.box_i32(2);
        helper.assertTrue(boxId2 != -1, "Invalid boxId2 " + boxId2);
        GameFunctions.hash_set_add(hashSetId, boxId2);

        helper.assertTrue(GameFunctions.hash_set_has(hashSetId, boxId1), "hashSetId did not contain boxId1 " + boxId1);
        helper.assertTrue(GameFunctions.hash_set_has(hashSetId, boxId2), "hashSetId did not contain boxId2 " + boxId2);

        String hashSetString = GameFunctions.get_hash_set_string(hashSetId);
        helper.assertTrue(!hashSetString.isEmpty(), "Invalid empty hashSetString");

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

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        hash_set: id = hash_set()

        hash_set_add(hash_set, box_i32(1))

        assert(hash_set_has(hash_set, box_i32(1)))
    }
    ```
    */
    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void hash_set_has(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = new HashSet<>();

        long hashSetId = GameFunctions.hash_set();
        helper.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        long boxId = GameFunctions.box_i32(1);
        helper.assertTrue(boxId != -1, "Invalid boxId " + boxId);
        GameFunctions.hash_set_add(hashSetId, boxId);

        long boxIdSecond = GameFunctions.box_i32(1);
        helper.assertTrue(boxIdSecond != -1, "Invalid boxIdSecond " + boxIdSecond);
        helper.assertTrue(GameFunctions.hash_set_has(hashSetId, boxIdSecond), "hashSetId did not contain boxIdSecond " + boxIdSecond);

        helper.succeed();
    }
}

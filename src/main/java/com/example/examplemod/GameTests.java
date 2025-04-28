package com.example.examplemod;

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
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation");

        long block = GameFunctions.block(resourceLocation);
        helper.assertTrue(block != -1, "Invalid block");

        helper.assertTrue(Grug.fnEntities.contains(block), "fnEntities did not contain block");

        helper.succeed();
    }

    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void block_pos(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long blockPos = GameFunctions.block_pos(0, 0, 0);
        helper.assertTrue(blockPos != -1, "Invalid blockPos");

        helper.assertTrue(Grug.fnEntities.contains(blockPos), "fnEntities did not contain blockPos");

        helper.succeed();
    }

    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void block_flag_update_all(GameTestHelper helper) {
        int flag = GameFunctions.block_flag_update_all();
        helper.assertTrue(flag == Block.UPDATE_ALL, "Did not return Block.UPDATE_ALL");

        helper.succeed();
    }

    @GameTest(template = ExampleMod.MODID+":placeholder")
    public static void box_i32(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long boxId = GameFunctions.box_i32(0);

        helper.assertTrue(Grug.fnEntities.contains(boxId), "fnEntities did not contain boxId");

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
}

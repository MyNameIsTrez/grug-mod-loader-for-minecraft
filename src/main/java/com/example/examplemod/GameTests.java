package com.example.examplemod;

import java.util.HashSet;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(ExampleMod.MODID)
public class GameTests {
    @GameTest(template = ExampleMod.MODID+":foo")
    public static void block(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = new HashSet<>();

        long resourceLocation = GameFunctions.resource_location("white_concrete");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation");

        long block = GameFunctions.block(resourceLocation);
        helper.assertTrue(block != -1, "Invalid block");

        helper.assertTrue(Grug.fnEntities.contains(block), "fnEntities did not contain block");

        helper.succeed();
    }
}

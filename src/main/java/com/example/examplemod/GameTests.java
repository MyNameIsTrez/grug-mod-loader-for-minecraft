package com.example.examplemod;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(ExampleMod.MODID)
public class GameTests {
    @GameTest(template = ExampleMod.MODID+":foo")
    public static void foo(GameTestHelper helper) {
        Grug.resetVariables();
        helper.succeed();
    }
}

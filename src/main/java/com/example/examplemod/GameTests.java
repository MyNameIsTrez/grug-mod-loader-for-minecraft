package com.example.examplemod;

import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(ExampleMod.MODID)
public class GameTests {
    @GameTest
    public static void foo(GameTestHelper helper) {
        // helper.setStructure("examplemod:foo");
        helper.assertTrue(true, "hmm ok");
    }
}

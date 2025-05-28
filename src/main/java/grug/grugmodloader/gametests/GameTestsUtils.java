package grug.grugmodloader.gametests;

import java.util.HashSet;

import grug.grugmodloader.Grug;

public class GameTestsUtils {
    public static void reset() {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = new HashSet<>();
    }
}

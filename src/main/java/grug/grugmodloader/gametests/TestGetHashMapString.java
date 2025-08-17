package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetHashMapString extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_hash_map_string_empty_map(GameTestHelper h) {
        reset(h);

        String string = get_hash_map_string(hash_map());

        h.assertTrue(string.equals("{}"), "hash_map string was not \"{}\", but \"" + string + "\"");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_hash_map_string(GameTestHelper h) {
        reset(h);

        long hash_map = hash_map();

        hash_map_put(hash_map, box_i32(42), box_i32(1));
        hash_map_put(hash_map, box_i32(69), box_i32(2));

        String string = get_hash_map_string(hash_map);

        String expected1 = "{GrugObject{type=BoxedI32, object=42}=GrugObject{type=BoxedI32, object=1}, GrugObject{type=BoxedI32, object=69}=GrugObject{type=BoxedI32, object=2}}";
        String expected2 = "{GrugObject{type=BoxedI32, object=69}=GrugObject{type=BoxedI32, object=2}, GrugObject{type=BoxedI32, object=42}=GrugObject{type=BoxedI32, object=1}}";

        h.assertTrue(string.equals(expected1) || string.equals(expected2), "Expected hash_map string to be \"" + expected1 + "\" or \"" + expected2 + "\", but got \"" + string + "\"");

        h.succeed();
    }
}

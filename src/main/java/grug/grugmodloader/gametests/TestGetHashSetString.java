package grug.grugmodloader.gametests;

import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestGetHashSetString extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_hash_set_string_empty_set(GameTestHelper h) {
        reset(h);

        String string = get_hash_set_string(hash_set());

        h.assertTrue(string.equals("[]"), "hash_set string was not \"[]\", but \"" + string + "\"");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_hash_set_string(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));
        hash_set_add(hash_set, box_i32(2));

        String string = get_hash_set_string(hash_set);

        h.assertTrue(string.equals("[{type=BoxedI32, object=2}, {type=BoxedI32, object=1}]"), "hash_set string was not \"[{type=BoxedI32, object=2}, {type=BoxedI32, object=1}]\", but \"" + string + "\"");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void get_hash_set_string_expected_hash_set(GameTestHelper h) {
        reset(h);

        String string = get_hash_set_string(box_i32(1));

        h.assertTrue(string.equals(""), "hash_set string was not an empty string, but \"" + string + "\"");

        assert_game_function_error("get_hash_set_string(): Expected hash_set, but got boxed_i32");

        h.succeed();
    }
}

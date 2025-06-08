package grug.grugmodloader.gametests;

import java.util.HashSet;

import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import grug.grugmodloader.GrugObject;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashSetAdd extends GameTestsUtils {
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));

        h.assertTrue(hash_set_has(hash_set, box_i32(1)), "hash_set did not contain box");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_duplicate(GameTestHelper h) {
        reset(h);

        long hash_set = hash_set();

        hash_set_add(hash_set, box_i32(1));
        hash_set_add(hash_set, box_i32(1));

        h.assertTrue(hash_set_has(hash_set, box_i32(1)), "hash_set did not contain box");

        int size = get_hash_set_size(hash_set);
        h.assertTrue(size == 1, "size was expected to be 1, but was " + size);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_expected_hash_set(GameTestHelper h) {
        reset(h);

        long box = box_i32(1);

        hash_set_add(box, box);

        assert_game_function_error("hash_set_add(): Expected hash_set, but got boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_to_global_set(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = new HashSet<>();

        long hash_set = hash_set();

        Grug.fnEntities = new HashSet<>();

        long box1 = box_i32(1);
        hash_set_add(hash_set, box1);

        long box2 = box_i32(2);
        hash_set_add(hash_set, box2);

        h.assertTrue(hash_set_has(hash_set, box1), "hash_set did not contain box1 " + box1);
        h.assertTrue(hash_set_has(hash_set, box2), "hash_set did not contain box2 " + box2);

        String hash_set_string = get_hash_set_string(hash_set);
        h.assertFalse(hash_set_string.isEmpty(), "Invalid empty hash_set_string");

        String expected1 = "[{type=BoxedI32, object=1}, {type=BoxedI32, object=2}]";
        String expected2 = "[{type=BoxedI32, object=2}, {type=BoxedI32, object=1}]";

        h.assertTrue(hash_set_string.equals(expected1) || hash_set_string.equals(expected2), "hash_set_string was neither \"" + expected1 + "\" nor \"" + expected2 + "\", but \"" + hash_set_string + "\"");

        h.assertTrue(Grug.fnEntities.size() == 2, "Grug.fnEntities.size() was expected to be 2, but was " + Grug.fnEntities.size());

        GrugObject hash_set_object = get_object(hash_set);
        h.assertFalse(Grug.fnEntities.contains(hash_set_object), "Grug.fnEntities contained hash_set_object " + hash_set_object);

        GrugObject box1_object = get_object(box1);
        h.assertTrue(Grug.fnEntities.contains(box1_object), "Grug.fnEntities did not contain box1_object " + box1_object);

        GrugObject box2_object = get_object(box2);
        h.assertTrue(Grug.fnEntities.contains(box2_object), "Grug.fnEntities did not contain box2_object " + box2_object);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_global_hash_set_containing_global_box_to_global_hash_set(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = new HashSet<>();

        long global_hash_set = hash_set();
        long global_hash_set_inner = hash_set();
        long global_box = box_i32(1);

        Grug.fnEntities = new HashSet<>();

        hash_set_add(global_hash_set_inner, global_box);

        hash_set_add(global_hash_set, global_hash_set_inner);

        // This simulates returning from the current on_ fn
        Grug.fnEntities = new HashSet<>();

        long global_hash_set_inner_copy = iteration(iterator(global_hash_set));

        h.assertTrue(hash_set_has(global_hash_set_inner_copy, box_i32(1)), "global_hash_set_inner_copy did not contain boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_local_hash_set_containing_global_box_to_global_hash_set(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = new HashSet<>();

        long global_hash_set = hash_set();
        long global_box = box_i32(1);

        Grug.fnEntities = new HashSet<>();

        long local_hash_set = hash_set();

        hash_set_add(local_hash_set, global_box);

        hash_set_add(global_hash_set, local_hash_set);

        // This simulates returning from the current on_ fn
        Grug.fnEntities = new HashSet<>();

        long local_hash_set_copy = iteration(iterator(global_hash_set));

        h.assertTrue(hash_set_has(local_hash_set_copy, box_i32(1)), "local_hash_set_copy did not contain boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_local_hash_set_containing_local_box_to_global_hash_set(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = new HashSet<>();

        long global_hash_set = hash_set();

        Grug.fnEntities = new HashSet<>();

        long local_hash_set = hash_set();

        hash_set_add(local_hash_set, box_i32(1));

        hash_set_add(global_hash_set, local_hash_set);

        // This simulates returning from the current on_ fn, and entering a new on_ fn
        Grug.fnEntities = new HashSet<>();

        long local_hash_set_copy = iteration(iterator(global_hash_set));

        h.assertTrue(hash_set_has(local_hash_set_copy, box_i32(1)), "local_hash_set_copy did not contain boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_local_hash_set_to_itself(GameTestHelper h) {
        reset(h);

        long local_hash_set = hash_set();

        hash_set_add(local_hash_set, local_hash_set);

        // This is just testing that this doesn't cause a hang
        iteration(iterator(local_hash_set));

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_global_hash_set_to_itself(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = new HashSet<>();

        long global_hash_set = hash_set();

        Grug.fnEntities = new HashSet<>();

        hash_set_add(global_hash_set, global_hash_set);

        // This is just testing that this doesn't cause a hang
        iteration(iterator(global_hash_set));

        h.succeed();
    }
}

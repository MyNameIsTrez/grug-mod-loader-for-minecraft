package grug.grugmodloader.gametests;

import java.util.HashMap;
import java.util.HashSet;

import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
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

        Grug.fnEntities = Grug.globalEntities;

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

        h.assertTrue(hash_set_string.equals("[17179869185, 17179869187]"), "Got unexpected hash_set_string value '" + hash_set_string + "'");

        h.assertTrue(Grug.globalEntities.size() == 3, "Grug.globalEntities.size() was expected to be 3, but was " + Grug.globalEntities.size());

        h.assertTrue(Grug.globalEntities.contains(hash_set), "Grug.globalEntities did not contain hash_set " + hash_set);

        HashMap<Object, Long> objects = get_hash_set_objects(hash_set);

        Object box1_object = get_object(box1);

        Long real_box1 = objects.get(box1_object);
        h.assertTrue(real_box1 != null, "real_box1 was not supposed to be null");

        h.assertTrue(Grug.globalEntities.contains(real_box1), "Grug.globalEntities did not contain real_box1 " + real_box1);

        Object box2_object = get_object(box2);

        Long real_box2 = objects.get(box2_object);
        h.assertTrue(real_box2 != null, "real_box2 was not supposed to be null");

        h.assertTrue(Grug.globalEntities.contains(real_box2), "Grug.globalEntities did not contain real_box2 " + real_box2);

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_global_hash_set_containing_global_box_to_global_hash_set(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = Grug.globalEntities;

        long global_hash_set = hash_set();
        long global_hash_set_inner = hash_set();
        long global_box = box_i32(1);

        Grug.fnEntities = new HashSet<>();

        hash_set_add(global_hash_set_inner, global_box);

        hash_set_add(global_hash_set, global_hash_set_inner);

        // This simulates returning from the current on_ fn
        Grug.removeEntities(Grug.fnEntities);
        Grug.fnEntities = new HashSet<>();

        long global_hash_set_inner_copy = iteration(iterator(global_hash_set));

        h.assertTrue(hash_set_has(global_hash_set_inner_copy, box_i32(1)), "global_hash_set_inner_copy did not contain boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_local_hash_set_containing_global_box_to_global_hash_set(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = Grug.globalEntities;

        long global_hash_set = hash_set();
        long global_box = box_i32(1);

        Grug.fnEntities = new HashSet<>();

        long local_hash_set = hash_set();

        hash_set_add(local_hash_set, global_box);

        hash_set_add(global_hash_set, local_hash_set);

        // This simulates returning from the current on_ fn
        Grug.removeEntities(Grug.fnEntities);
        Grug.fnEntities = new HashSet<>();

        long local_hash_set_copy = iteration(iterator(global_hash_set));

        h.assertTrue(hash_set_has(local_hash_set_copy, box_i32(1)), "local_hash_set_copy did not contain boxed_i32");

        h.succeed();
    }

    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_local_hash_set_containing_local_box_to_global_hash_set(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = Grug.globalEntities;

        long global_hash_set = hash_set();

        Grug.fnEntities = new HashSet<>();

        long local_hash_set = hash_set();

        // TODO:
        // The reason this tests fails right now, is because hash_set_add()
        // uses Grug.globalEntities.contains(hashSetId) to decide whether the value should be added to globalEntities or fnEntities.
        // This doesn't account for the fact that once local_hash_set is global_hash_set, that the values inside local_hash_set can also be considered global.
        // I'm not sure if there's a way to resolve this, without recursively descending the values inside local_hash_set, which will require detecting cycles to prevent infinite loops.
        hash_set_add(local_hash_set, box_i32(1));

        hash_set_add(global_hash_set, local_hash_set);

        // This simulates returning from the current on_ fn, and entering a new on_ fn
        Grug.removeEntities(Grug.fnEntities);
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

        Grug.fnEntities = Grug.globalEntities;

        long global_hash_set = hash_set();

        Grug.fnEntities = new HashSet<>();

        hash_set_add(global_hash_set, global_hash_set);

        // This is just testing that this doesn't cause a hang
        iteration(iterator(global_hash_set));

        h.succeed();
    }
}

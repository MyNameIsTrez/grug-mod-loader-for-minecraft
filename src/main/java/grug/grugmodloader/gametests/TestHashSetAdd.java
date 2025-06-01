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
}

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

        long hashSetId = hash_set();

        Grug.fnEntities = new HashSet<>();

        long box1 = box_i32(1);
        hash_set_add(hashSetId, box1);

        long box2 = box_i32(2);
        hash_set_add(hashSetId, box2);

        h.assertTrue(hash_set_has(hashSetId, box1), "hashSetId did not contain box1 " + box1);
        h.assertTrue(hash_set_has(hashSetId, box2), "hashSetId did not contain box2 " + box2);

        String hashSetString = get_hash_set_string(hashSetId);
        h.assertFalse(hashSetString.isEmpty(), "Invalid empty hashSetString");

        h.assertTrue(hashSetString.equals("[17179869185, 17179869187]"), "Got unexpected hashSetString value '" + hashSetString + "'");

        h.assertTrue(Grug.globalEntities.size() == 3, "Grug.globalEntities.size() was expected to be 3, but was " + Grug.globalEntities.size());

        h.assertTrue(Grug.globalEntities.contains(hashSetId), "Grug.globalEntities did not contain hashSetId " + hashSetId);

        HashMap<Object, Long> objects = get_hash_set_objects(hashSetId);

        Object box1Object = get_object(box1);

        Long realBox1 = objects.get(box1Object);
        h.assertTrue(realBox1 != null, "realBox1 was not supposed to be null");

        h.assertTrue(Grug.globalEntities.contains(realBox1), "Grug.globalEntities did not contain realBox1 " + realBox1);

        Object box2Object = get_object(box2);

        Long realBox2 = objects.get(box2Object);
        h.assertTrue(realBox2 != null, "realBox2 was not supposed to be null");

        h.assertTrue(Grug.globalEntities.contains(realBox2), "Grug.globalEntities did not contain realBox2 " + realBox2);

        h.succeed();
    }
}

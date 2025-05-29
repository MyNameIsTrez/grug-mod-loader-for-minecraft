package grug.grugmodloader.gametests;

import java.util.HashMap;
import java.util.HashSet;

import grug.grugmodloader.AssertEntityTypeException;
import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestHashSetAdd extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        hash_set: id = hash_set()

        hash_set_add(hash_set, box_i32(1))

        assert(hash_set_has(hash_set, box_i32(1)))
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add(GameTestHelper h) {
        reset(h);

        long hashSetId = GameFunctions.hash_set();
        h.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);
        GameFunctions.hash_set_add(hashSetId, box);

        long boxSecond = GameFunctions.box_i32(1);
        h.assertTrue(boxSecond != -1, "Invalid boxSecond " + boxSecond);

        h.assertTrue(GameFunctions.hash_set_has(hashSetId, boxSecond), "hashSetId did not contain boxSecond " + boxSecond);

        h.succeed();
    }

    /*
    ```grug
    on_a() {
        hash_set: id = hash_set()

        hash_set_add(hash_set, box_i32(1))
        hash_set_add(hash_set, box_i32(1))

        assert(hash_set_has(hash_set, box_i32(1)))
        assert(hash_set_size(hash_set) == 1)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_duplicate(GameTestHelper h) {
        reset(h);

        long hashSetId = GameFunctions.hash_set();
        h.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);
        GameFunctions.hash_set_add(hashSetId, box);

        long boxSecond = GameFunctions.box_i32(1);
        h.assertTrue(boxSecond != -1, "Invalid boxSecond " + boxSecond);
        GameFunctions.hash_set_add(hashSetId, boxSecond);

        long boxThird = GameFunctions.box_i32(1);
        h.assertTrue(boxThird != -1, "Invalid boxThird " + boxThird);

        h.assertTrue(GameFunctions.hash_set_has(hashSetId, boxThird), "hashSetId did not contain boxThird " + boxThird);

        h.assertTrue(GameFunctions.get_hash_set_size(hashSetId) == 1, "GameFunctions.get_hash_set_size(hashSetId) was expected to be 1, but was " + GameFunctions.get_hash_set_size(hashSetId));

        h.succeed();
    }

    /*
    ```grug
    on_a() {
        box: id = box_i32(1)

        hash_set_add(box, box)

        assert_game_function_error("hash_set_add(): Expected hash_set, but got boxed_i32")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_expected_hash_set(GameTestHelper h) {
        reset(h);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);
        GameFunctions.hash_set_add(box, box);

        h.assertTrue(Grug.gameFunctionError.equals("hash_set_add(): Expected hash_set, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        h.succeed();
    }

    /*
    ```grug
    hash_set: id = hash_set()

    on_a() {
        box1: id = box_i32(1)
        hash_set_add(hash_set, box1)

        box2: id = box_i32(2)
        hash_set_add(hash_set, box2)

        assert(hash_set_has(hash_set, box1))
        assert(hash_set_has(hash_set, box2))

        assert(hash_set_string(hash_set) == "[17179869185, 17179869187]")

        assert_global_entities_size(3)

        assert_global_entities_contains(hash_set)

        assert_hash_set_contains_global_entity(hash_set, box_i32(1))
        assert_hash_set_contains_global_entity(hash_set, box_i32(2))
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add_to_global_set(GameTestHelper h) {
        reset(h);

        Grug.fnEntities = Grug.globalEntities;

        long hashSetId = GameFunctions.hash_set();
        h.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        Grug.fnEntities = new HashSet<>();

        long box1 = GameFunctions.box_i32(1);
        h.assertTrue(box1 != -1, "Invalid box1 " + box1);
        GameFunctions.hash_set_add(hashSetId, box1);

        long box2 = GameFunctions.box_i32(2);
        h.assertTrue(box2 != -1, "Invalid box2 " + box2);
        GameFunctions.hash_set_add(hashSetId, box2);

        h.assertTrue(GameFunctions.hash_set_has(hashSetId, box1), "hashSetId did not contain box1 " + box1);
        h.assertTrue(GameFunctions.hash_set_has(hashSetId, box2), "hashSetId did not contain box2 " + box2);

        String hashSetString = GameFunctions.get_hash_set_string(hashSetId);
        h.assertTrue(!hashSetString.isEmpty(), "Invalid empty hashSetString");

        h.assertTrue(hashSetString.equals("[17179869185, 17179869187]"), "Got unexpected hashSetString value '" + hashSetString + "'");

        h.assertTrue(Grug.globalEntities.size() == 3, "Grug.globalEntities.size() was expected to be 3, but was " + Grug.globalEntities.size());

        h.assertTrue(Grug.globalEntities.contains(hashSetId), "Grug.globalEntities did not contain hashSetId " + hashSetId);

        HashMap<Object, Long> objects;
        try {
            objects = Grug.getHashSetObjects(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            // Unreachable
            h.fail(assertEntityTypeException.getMessage());
            return;
        }

        Object box1Object = GrugModLoader.grug.getObject(box1);
        h.assertTrue(box1Object != null, "box1Object was not supposed to be null");
        Long realBox1 = objects.get(box1Object);
        h.assertTrue(realBox1 != null, "realBox1 was not supposed to be null");
        h.assertTrue(Grug.globalEntities.contains(realBox1), "Grug.globalEntities did not contain realBox1 " + realBox1);

        Object box2Object = GrugModLoader.grug.getObject(box2);
        h.assertTrue(box2Object != null, "box2Object was not supposed to be null");
        Long realBox2 = objects.get(box2Object);
        h.assertTrue(realBox2 != null, "realBox2 was not supposed to be null");
        h.assertTrue(Grug.globalEntities.contains(realBox2), "Grug.globalEntities did not contain realBox2 " + realBox2);

        h.succeed();
    }
}

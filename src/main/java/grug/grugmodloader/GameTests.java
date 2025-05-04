package grug.grugmodloader;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.core.BlockPos;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.gametest.GameTestHolder;
import net.minecraftforge.registries.ForgeRegistries;

// TODO: Add these tests:
// destroy_and_drop_block
// entry_key
// entry_set_value
// entry_value
// get_block_entity_level
// get_block_pos_above
// get_block_pos_above_n
// get_block_pos_center
// get_block_pos_x
// get_block_pos_y
// get_block_pos_z
// get_block_state
// get_default_block_state
// get_hash_map_size
// get_hash_map_string
// get_hash_set_size
// get_hash_set_string
// get_item_name
// get_item_entity_name
// get_item_stack_name
// get_level_name
// get_resource_location_string
// get_vec3_x    
// get_vec3_y    
// get_vec3_z
// get_world_position_of_block_entity
// hash_map
// hash_map_clear
// hash_map_clear_global
// hash_map_copy
// hash_map_copy_global
// hash_map_get
// hash_map_get_error_hash_map_does_not_contain_key
// hash_map_get_global
// hash_map_get_global_error_hash_map_does_not_contain_key
// hash_map_has_key
// hash_map_has_key_not_contained
// hash_map_put_existing_key
// hash_map_put_new_key
// hash_map_remove_key
// hash_map_remove_key_error_hash_map_does_not_contain_key
// hash_map_remove_key_global
// hash_map_remove_key_global_error_hash_map_does_not_contain_key
// hash_set_clear
// hash_set_clear_global
// hash_set_copy
// hash_set_copy_global
// hash_set_remove
// hash_set_remove_global
// hash_set_remove_error_hash_set_does_not_contain_value
// is_air_false
// is_air_true
// is_client_side_false
// is_client_side_true
// item
// item_error_invalid_resource_location
// item_entity
// item_stack
// iterating_false
// iterating_true
// iteration_hash_map
// iteration_hash_map_grug_entry_stored_in_global
// iteration_hash_set
// iterator_error_expected_iterable
// iterator_hash_map
// iterator_hash_set
// iterator_remove
// iterator_remove_error_called_more_than_once
// place_block
// print_bool
// print_i32
// print_id
// print_string
// remove_block
// remove_moving_block
// resource_location
// set_entity_delta_movement
// spawn_entity
// unbox_i32
// vec3_zero

@GameTestHolder(GrugModLoader.MODID)
public class GameTests {
    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        resource_location: id = resource_location("white_concrete")

        block: id = block(resource_location)

        assert_fn_entities_contains(block)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long resourceLocation = GameFunctions.resource_location("white_concrete");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long block = GameFunctions.block(resourceLocation);
        helper.assertTrue(block != -1, "Invalid block " + block);

        helper.assertTrue(Grug.fnEntities.contains(block), "fnEntities did not contain block " + block);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        resource_location: id = resource_location("diamond")

        block: id = block_nullable(resource_location)
        assert(block == null_id)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_error_invalid_resource_location(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long resourceLocation = GameFunctions.resource_location("diamond");
        helper.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long block = GameFunctions.block(resourceLocation);
        helper.assertTrue(block == -1, "Expected an invalid block, but got " + block);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        block_pos: id = block_pos(0, 0, 0)

        assert_fn_entities_contains(block_pos)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_pos(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long blockPos = GameFunctions.block_pos(0, 0, 0);
        helper.assertTrue(blockPos != -1, "Invalid blockPos " + blockPos);

        helper.assertTrue(Grug.fnEntities.contains(blockPos), "fnEntities did not contain blockPos " + blockPos);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        # I'm not sure how assert_enum() would be implemented in Java
        assert_enum(block_flag_update_all(), "Block.UPDATE_ALL")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void block_flag_update_all(GameTestHelper helper) {
        int flag = GameFunctions.block_flag_update_all();
        helper.assertTrue(flag == Block.UPDATE_ALL, "Did not return Block.UPDATE_ALL, but " + flag);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        box: id = box_i32(1)

        assert_fn_entities_contains(box)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void box_i32(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        long box = GameFunctions.box_i32(0);
        helper.assertTrue(box != -1, "Invalid box " + box);

        helper.assertTrue(Grug.fnEntities.contains(box), "fnEntities did not contain box " + box);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        block: id = block(resource_location("diamond_block"))

        relative_structure_block_pos: id = block_pos(0, 1, 0)

        assert_block_present(block, relative_structure_block_pos)

        absolute_diamond_block_pos: id = absolute_pos(relative_structure_block_pos)

        x: i32 = get_block_pos_x(absolute_diamond_block_pos)
        y: i32 = get_block_pos_y(absolute_diamond_block_pos)
        z: i32 = get_block_pos_z(absolute_diamond_block_pos)

        block_pos: id = block_pos(x, y, z)

        level: id = get_game_test_info_server_level()

        destroy_block(block_pos, level)

        assert_block_not_present(block, relative_structure_block_pos)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":diamond_block")
    public static void destroy_block(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.fnEntities = new HashSet<>();

        Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation("diamond_block"));

        BlockPos relativeStructureBlockPos = new BlockPos(0, 1, 0);

        helper.assertBlockPresent(block, relativeStructureBlockPos);

        BlockPos absoluteDiamondBlockPos = helper.absolutePos(relativeStructureBlockPos);

        int x = absoluteDiamondBlockPos.getX();
        int y = absoluteDiamondBlockPos.getY();
        int z = absoluteDiamondBlockPos.getZ();

        long blockPosId = GameFunctions.block_pos(x, y, z);
        helper.assertTrue(blockPosId != -1, "Invalid blockPosId " + blockPosId);

        ServerLevel level = helper.getLevel();

        long levelId = Grug.addEntity(EntityType.Level, level);

        GameFunctions.destroy_block(blockPosId, levelId);

        helper.assertBlockNotPresent(block, relativeStructureBlockPos);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        hash_set: id = hash_set()

        hash_set_add(hash_set, box_i32(1))

        assert(hash_set_has(hash_set, box_i32(1)))
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = new HashSet<>();

        long hashSetId = GameFunctions.hash_set();
        helper.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);
        GameFunctions.hash_set_add(hashSetId, box);

        long boxSecond = GameFunctions.box_i32(1);
        helper.assertTrue(boxSecond != -1, "Invalid boxSecond " + boxSecond);

        helper.assertTrue(GameFunctions.hash_set_has(hashSetId, boxSecond), "hashSetId did not contain boxSecond " + boxSecond);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        hash_set: id = hash_set()

        hash_set_add(hash_set, box_i32(1))

        assert(hash_set_has(hash_set, box_i32(1)))
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_add(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = new HashSet<>();

        long hashSetId = GameFunctions.hash_set();
        helper.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);
        GameFunctions.hash_set_add(hashSetId, box);

        long boxSecond = GameFunctions.box_i32(1);
        helper.assertTrue(boxSecond != -1, "Invalid boxSecond " + boxSecond);

        helper.assertTrue(GameFunctions.hash_set_has(hashSetId, boxSecond), "hashSetId did not contain boxSecond " + boxSecond);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
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
    public static void hash_set_add_duplicate(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = new HashSet<>();

        long hashSetId = GameFunctions.hash_set();
        helper.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);
        GameFunctions.hash_set_add(hashSetId, box);

        long boxSecond = GameFunctions.box_i32(1);
        helper.assertTrue(boxSecond != -1, "Invalid boxSecond " + boxSecond);
        GameFunctions.hash_set_add(hashSetId, boxSecond);

        long boxThird = GameFunctions.box_i32(1);
        helper.assertTrue(boxThird != -1, "Invalid boxThird " + boxThird);

        helper.assertTrue(GameFunctions.hash_set_has(hashSetId, boxThird), "hashSetId did not contain boxThird " + boxThird);

        helper.assertTrue(GameFunctions.get_hash_set_size(hashSetId) == 1, "GameFunctions.get_hash_set_size(hashSetId) was expected to be 1, but was " + GameFunctions.get_hash_set_size(hashSetId));

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
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
    public static void hash_set_add_to_global_set(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = Grug.globalEntities;

        long hashSetId = GameFunctions.hash_set();
        helper.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        Grug.fnEntities = new HashSet<>();

        long box1 = GameFunctions.box_i32(1);
        helper.assertTrue(box1 != -1, "Invalid box1 " + box1);
        GameFunctions.hash_set_add(hashSetId, box1);

        long box2 = GameFunctions.box_i32(2);
        helper.assertTrue(box2 != -1, "Invalid box2 " + box2);
        GameFunctions.hash_set_add(hashSetId, box2);

        helper.assertTrue(GameFunctions.hash_set_has(hashSetId, box1), "hashSetId did not contain box1 " + box1);
        helper.assertTrue(GameFunctions.hash_set_has(hashSetId, box2), "hashSetId did not contain box2 " + box2);

        String hashSetString = GameFunctions.get_hash_set_string(hashSetId);
        helper.assertTrue(!hashSetString.isEmpty(), "Invalid empty hashSetString");

        helper.assertTrue(hashSetString.equals("[17179869185, 17179869187]"), "Got unexpected hashSetString value '" + hashSetString + "'");

        helper.assertTrue(Grug.globalEntities.size() == 3, "Grug.globalEntities.size() was expected to be 3, but was " + Grug.globalEntities.size());

        helper.assertTrue(Grug.globalEntities.contains(hashSetId), "Grug.globalEntities did not contain hashSetId " + hashSetId);

        HashMap<Object, Long> objects;
        try {
            objects = Grug.getHashSetObjects(hashSetId);
        } catch (AssertEntityTypeException assertEntityTypeException) {
            // Unreachable
            helper.fail(assertEntityTypeException.getMessage());
            return;
        }

        Object box1Object = GrugModLoader.grug.getObject(box1);
        helper.assertTrue(box1Object != null, "box1Object was not supposed to be null");
        Long realBox1 = objects.get(box1Object);
        helper.assertTrue(realBox1 != null, "realBox1 was not supposed to be null");
        helper.assertTrue(Grug.globalEntities.contains(realBox1), "Grug.globalEntities did not contain realBox1 " + realBox1);

        Object box2Object = GrugModLoader.grug.getObject(box2);
        helper.assertTrue(box2Object != null, "box2Object was not supposed to be null");
        Long realBox2 = objects.get(box2Object);
        helper.assertTrue(realBox2 != null, "realBox2 was not supposed to be null");
        helper.assertTrue(Grug.globalEntities.contains(realBox2), "Grug.globalEntities did not contain realBox2 " + realBox2);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        hash_set: id = hash_set()

        hash_set_add(hash_set, box_i32(1))

        assert(hash_set_has(hash_set, box_i32(1)))
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_has(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = new HashSet<>();

        long hashSetId = GameFunctions.hash_set();
        helper.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);
        GameFunctions.hash_set_add(hashSetId, box);

        long boxSecond = GameFunctions.box_i32(1);
        helper.assertTrue(boxSecond != -1, "Invalid boxSecond " + boxSecond);
        helper.assertTrue(GameFunctions.hash_set_has(hashSetId, boxSecond), "hashSetId did not contain boxSecond " + boxSecond);

        helper.succeed();
    }

    /*
    This Java function should eventually be replaced with this rough grug equivalent:
    ```grug
    on_a() {
        hash_set: id = hash_set()

        hash_set_add(hash_set, box_i32(1))

        assert(!hash_set_has(hash_set, box_i32(2)))
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void hash_set_has_not(GameTestHelper helper) {
        Grug.resetVariables();

        Grug.globalEntities = new HashSet<>();
        Grug.fnEntities = new HashSet<>();

        long hashSetId = GameFunctions.hash_set();
        helper.assertTrue(hashSetId != -1, "Invalid hashSetId " + hashSetId);

        long box = GameFunctions.box_i32(1);
        helper.assertTrue(box != -1, "Invalid box " + box);
        GameFunctions.hash_set_add(hashSetId, box);

        long boxSecond = GameFunctions.box_i32(2);
        helper.assertTrue(boxSecond != -1, "Invalid boxSecond " + boxSecond);
        helper.assertFalse(GameFunctions.hash_set_has(hashSetId, boxSecond), "hashSetId did contain boxSecond " + boxSecond);

        helper.succeed();
    }
}

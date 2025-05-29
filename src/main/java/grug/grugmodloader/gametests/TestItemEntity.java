package grug.grugmodloader.gametests;

import grug.grugmodloader.EntityType;
import grug.grugmodloader.GameFunctions;
import grug.grugmodloader.Grug;
import grug.grugmodloader.GrugModLoader;
import net.minecraft.gametest.framework.GameTest;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraftforge.gametest.GameTestHolder;

@GameTestHolder(GrugModLoader.MODID)
public class TestItemEntity extends GameTestsUtils {
    /*
    ```grug
    on_a() {
        item_stack: id = item_stack(item(resource_location("diamond")))

        item_entity: id = item_entity(get_level(), 0, 0, 0, item_stack)

        assert_fn_entities_contains(item_entity)
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_entity(GameTestHelper h) {
        reset(h);

        long level = Grug.addEntity(EntityType.Level, h.getLevel());

        long resourceLocation = GameFunctions.resource_location("diamond");
        h.assertTrue(resourceLocation != -1, "Invalid resourceLocation " + resourceLocation);

        long item = GameFunctions.item(resourceLocation);
        h.assertTrue(item != -1, "Invalid item " + item);

        long itemStack = GameFunctions.item_stack(item);
        h.assertTrue(itemStack != -1, "Invalid itemStack " + itemStack);

        long itemEntity = GameFunctions.item_entity(level, 0, 0, 0, itemStack);
        h.assertTrue(itemEntity != -1, "Invalid itemEntity " + itemEntity);

        h.assertTrue(Grug.fnEntities.contains(itemEntity), "fnEntities did not contain " + itemEntity);

        h.succeed();
    }

    /*
    ```grug
    on_a() {
        box: id = box_i32(1)

        item_entity: id = item_entity(box, 0, 0, 0, box)
        assert_error_id(item_entity)

        assert_game_function_error("item_entity(): Expected level, but got boxed_i32")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_entity_expected_level(GameTestHelper h) {
        reset(h);

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);

        long itemEntity = GameFunctions.item_entity(box, 0, 0, 0, box);
        h.assertTrue(itemEntity == -1, "Expected an invalid itemEntity, but got " + itemEntity);

        h.assertTrue(Grug.gameFunctionError.equals("item_entity(): Expected level, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        h.succeed();
    }

    /*
    ```grug
    on_a() {
        item_entity: id = item_entity(get_level(), 0, 0, 0, box_i32(1))
        assert_error_id(item_entity)

        assert_game_function_error("item_entity(): Expected item_stack, but got boxed_i32")
    }
    ```
    */
    @GameTest(template = GrugModLoader.MODID+":placeholder")
    public static void item_entity_expected_item_stack(GameTestHelper h) {
        reset(h);

        long level = Grug.addEntity(EntityType.Level, h.getLevel());

        long box = GameFunctions.box_i32(1);
        h.assertTrue(box != -1, "Invalid box " + box);

        long itemEntity = GameFunctions.item_entity(level, 0, 0, 0, box);
        h.assertTrue(itemEntity == -1, "Expected an invalid itemEntity, but got " + itemEntity);

        h.assertTrue(Grug.gameFunctionError.equals("item_entity(): Expected item_stack, but got boxed_i32"), "gameFunctionError had the unexpected value '" + Grug.gameFunctionError + "'");

        h.succeed();
    }
}

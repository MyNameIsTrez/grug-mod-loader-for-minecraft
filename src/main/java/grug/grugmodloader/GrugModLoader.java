package grug.grugmodloader;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.event.Level;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(GrugModLoader.MODID)
public class GrugModLoader
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "grugmodloader";
    // Directly reference a slf4j logger
    public static final Logger logger = LogUtils.getLogger();

    public static Grug grug;

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);

    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "grugmodloader" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<FooBlock> FOO_BLOCK = BLOCKS.register("foo_block", () -> new FooBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.WARPED_STEM).strength(2.0f, 8f)));
    public static final RegistryObject<BlockEntityType<FooBlockEntity>> FOO_BLOCK_ENTITY = BLOCK_ENTITIES.register("foo_block", () -> BlockEntityType.Builder.of(FooBlockEntity::new, FOO_BLOCK.get()).build(null));

    public static final RegistryObject<GameOfLifeBlock> GAME_OF_LIFE_BLOCK = BLOCKS.register("game_of_life_block", () -> new GameOfLifeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEEHIVE).mapColor(MapColor.WARPED_STEM).strength(2.0f, 8f)));
    public static final RegistryObject<BlockEntityType<GameOfLifeBlockEntity>> GAME_OF_LIFE_BLOCK_ENTITY = BLOCK_ENTITIES.register("game_of_life_block", () -> BlockEntityType.Builder.of(GameOfLifeBlockEntity::new, GAME_OF_LIFE_BLOCK.get()).build(null));

    // Creates a new food item with the id "grugmodloader:example_id", nutrition 1 and saturation 2
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder()
            .alwaysEdible().nutrition(1).saturationModifier(2f).build())));

    // Creates a creative tab with the id "grugmodloader:grug_tab" for the example item, that is placed after the combat tab
    public static final RegistryObject<CreativeModeTab> GRUG_TAB = CREATIVE_MODE_TABS.register("grug_tab", () -> CreativeModeTab.builder()
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .title(Component.literal("grug"))
            .icon(() -> EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(EXAMPLE_ITEM.get()); // Add the example item to the tab. For your own tabs, this method is preferred over the event
            }).build());

    ArrayList<RegistryObject<Item>> block_items = new ArrayList<RegistryObject<Item>>();

    public GrugModLoader()
    {
        LogUtils.configureRootLoggingLevel(Level.INFO);

        logger.info("HELLO FROM CONSTRUCTOR");

        // This line is deliberately put here, as opposed to the `public static Grug grug;` line,
        // since Forge doesn't display proper grug mod error messages otherwise during game startup
        grug = new Grug();

        // RegistryObject<Block> foo_block = BLOCKS.register("foo_block", () -> new FooBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
        // RegistryObject<BlockEntityType<FooBlockEntity>> foo_block_entity = BLOCK_ENTITIES.register("foo_block_entity", () -> BlockEntityType.Builder.of(FooBlockEntity::new, foo_block).build(null));
        RegistryObject<Item> foo_block_item = ITEMS.register("foo_block", () -> new BlockItem(FOO_BLOCK.get(), new Item.Properties()));
        block_items.add(foo_block_item);
        RegistryObject<Item> game_of_life_block_item = ITEMS.register("game_of_life_block", () -> new BlockItem(GAME_OF_LIFE_BLOCK.get(), new Item.Properties()));
        block_items.add(game_of_life_block_item);

        // String[] blocks = {"foo_block", "bar_block"};
        // for (String name : blocks) {
        //     RegistryObject<Block> block = BLOCKS.register(name, () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
        //     RegistryObject<Item> block_item = ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        //     block_items.add(block_item);
        // }

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        // modEventBus.addListener(this::onTick);

        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        logger.info("HELLO FROM COMMON SETUP");

        if (Config.logDirtBlock)
            logger.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));

        logger.info(Config.magicNumberIntroduction + Config.magicNumber);

        Config.items.forEach((item) -> logger.info("ITEM >> {}", item.toString()));
    }

    // Add the example block item to the building blocks tab
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if (event.getTabKey() == GRUG_TAB.getKey()) {
            for (RegistryObject<Item> block_item : block_items) {
                event.accept(block_item);
            }
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        logger.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {
            // Some client setup code
            logger.info("HELLO FROM CLIENT SETUP");
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event) {
        if (event.phase != TickEvent.Phase.START) {
            return;
        }

        grug.onTick();
    }
}

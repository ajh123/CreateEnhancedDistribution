package me.ajh123.create_distribution.foundation;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import static me.ajh123.create_distribution.CreateDistribution.MODID;

public class ModRegistry {
    // Create a Deferred Register to hold Blocks which will all be registered under the "create_distribution" namespace
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    // Create a Deferred Register to hold Blocks Entities which will all be registered under the "create_distribution" namespace
    public static final DeferredRegister<BlockEntityType<?>> BLOCKS_ENTITIES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, MODID);
    // Create a Deferred Register to hold Items which will all be registered under the "create_distribution" namespace
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    // Create a Deferred Register to hold CreativeModeTabs which will all be registered under the "create_distribution" namespace
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    static {
        ModBlocks.init();
        ModItems.init();
    }

    // Creates a creative tab with the id "create_distribution:main" for the example item, that is placed after the combat tab
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MOD_CREATIVE_TAB = CREATIVE_MODE_TABS.register("main", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.create_distribution"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS).icon(() -> ModItems.ENERGY_METER_ITEM.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
        var entries = ITEMS.getEntries();
        for (var entry : entries) {
            output.accept(entry.get());
        }
    }).build());

    public static void init(IEventBus modEventBus) {
        // Register the Deferred Register to the mod event bus so blocks get registered
        BLOCKS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so block entities get registered
        BLOCKS_ENTITIES.register(modEventBus);
        // Register the Deferred Register to the mod event bus so items get registered
        ITEMS.register(modEventBus);
        // Register the Deferred Register to the mod event bus so tabs get registered
        CREATIVE_MODE_TABS.register(modEventBus);
    }
}

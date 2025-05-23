package me.ajh123.create_distribution.foundation;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModItems {
    // Creates a new BlockItem with the id "create_distribution:electrical_energy_meter", combining the namespace and path
    public static final DeferredItem<BlockItem> ENERGY_METER_ITEM = ModRegistry.ITEMS.registerSimpleBlockItem("electrical_energy_meter", ModBlocks.ENERGY_METER);

    // Creates a new BlockItem with the id "create_distribution:package_singer", combining the namespace and path
    public static final DeferredItem<BlockItem> PACKAGE_SIGNER_ITEM = ModRegistry.ITEMS.registerSimpleBlockItem("package_singer", ModBlocks.PACKAGE_SIGNER_BLOCK);


    // Creates a new food item with the id "create_distribution:example_id", nutrition 1 and saturation 2
    public static final DeferredItem<Item> EXAMPLE_ITEM = ModRegistry.ITEMS.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder().alwaysEdible().nutrition(1).saturationModifier(2f).build()));


    public static void init() {
        // static initialization
    }
}

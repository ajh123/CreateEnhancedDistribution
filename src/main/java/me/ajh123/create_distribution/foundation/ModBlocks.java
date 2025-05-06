package me.ajh123.create_distribution.foundation;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

public class ModBlocks {
    // Creates a new Block with the id "create_distribution:example_block", combining the namespace and path
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = ModRegistry.BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    public static void init() {
        // static initialization
    }
}

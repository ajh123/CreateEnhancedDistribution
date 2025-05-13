package me.ajh123.create_distribution.foundation;

import me.ajh123.create_distribution.content.meter.ElectricalEnergyMeterBlock;
import me.ajh123.create_distribution.content.meter.ElectricalEnergyMeterBlockEntity;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;

import java.util.function.Supplier;

public class ModBlocks {
    // Creates a new Block with the id "create_distribution:electrical_energy_meter", combining the namespace and path
    public static final DeferredBlock<ElectricalEnergyMeterBlock> ENERGY_METER = ModRegistry.BLOCKS.register(
            "electrical_energy_meter",
            registryName -> new ElectricalEnergyMeterBlock(BlockBehaviour.Properties.of()
                    .destroyTime(2.0f)
                    .explosionResistance(10.0f)
                    .sound(SoundType.METAL)
                    .mapColor(MapColor.STONE)
            )
    );

    public static final Supplier<BlockEntityType<ElectricalEnergyMeterBlockEntity>> ENERGY_METER_BLOCK_ENTITY =
        ModRegistry.BLOCKS_ENTITIES.register(
            "electrical_energy_meter",
            () -> BlockEntityType.Builder.of(
                // The supplier to use for constructing the block entity instances.
                ElectricalEnergyMeterBlockEntity::new,
                // A vararg of blocks that can have this block entity.
                // This assumes the existence of the referenced blocks as DeferredBlock<Block>s.
                ENERGY_METER.get()
            )
            // Build using null; vanilla does some datafixer shenanigans with the parameter that we don't need.
            .build(null)
    );

    public static void init() {
        // static initialization
    }
}

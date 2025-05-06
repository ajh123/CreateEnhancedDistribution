package me.ajh123.create_distribution.foundation.content.meter;

import me.ajh123.create_distribution.foundation.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ElectricalEnergyMeterBlockEntity extends BlockEntity {
    public ElectricalEnergyMeterBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlocks.ENERGY_METER_BLOCK_ENTITY.get(), pos, blockState);
    }
}

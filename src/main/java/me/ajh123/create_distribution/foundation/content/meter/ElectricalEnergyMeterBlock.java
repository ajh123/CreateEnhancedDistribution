package me.ajh123.create_distribution.foundation.content.meter;

import com.simibubi.create.foundation.block.IBE;
import me.ajh123.create_distribution.foundation.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ElectricalEnergyMeterBlock extends Block implements IBE<ElectricalEnergyMeterBlockEntity> {
    public ElectricalEnergyMeterBlock(Properties properties) {
        super(properties);
    }

    @Override
    public Class<ElectricalEnergyMeterBlockEntity> getBlockEntityClass() {
        return ElectricalEnergyMeterBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends ElectricalEnergyMeterBlockEntity> getBlockEntityType() {
        return ModBlocks.ENERGY_METER_BLOCK_ENTITY.get();
    }
}

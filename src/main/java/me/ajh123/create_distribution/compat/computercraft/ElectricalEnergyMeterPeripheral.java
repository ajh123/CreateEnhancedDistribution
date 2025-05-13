package me.ajh123.create_distribution.compat.computercraft;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import me.ajh123.create_distribution.content.meter.ElectricalEnergyMeterBlockEntity;
import org.jspecify.annotations.Nullable;

public class ElectricalEnergyMeterPeripheral implements IPeripheral {
    private final String type;
    private final ElectricalEnergyMeterBlockEntity energyMeterBlock;

    public ElectricalEnergyMeterPeripheral(String type, ElectricalEnergyMeterBlockEntity energyMeterBlock) {
        this.type = type;
        this.energyMeterBlock = energyMeterBlock;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return iPeripheral == this;
    }

    @Nullable
    @Override
    public Object getTarget() {
        return energyMeterBlock;
    }

    @LuaFunction(mainThread = true)
    public int getThroughputLastTick() {
        return energyMeterBlock.getThroughput();
    }

    @LuaFunction(mainThread = true)
    public long getTotalTransferred() {
        return energyMeterBlock.getTotalTransferred();
    }
}

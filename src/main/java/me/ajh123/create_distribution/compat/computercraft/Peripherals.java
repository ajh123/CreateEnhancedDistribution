package me.ajh123.create_distribution.compat.computercraft;


import dan200.computercraft.api.peripheral.PeripheralCapability;
import me.ajh123.create_distribution.foundation.ModBlocks;
import me.ajh123.create_distribution.foundation.content.meter.ElectricalEnergyMeterBlockEntity;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class Peripherals {
    public static void registerPeripheralCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                PeripheralCapability.get(),
                ModBlocks.ENERGY_METER_BLOCK_ENTITY.get(),
                (be, dir) -> createElectricalEnergyMeterPeripheral(be)
        );
    }

    public static ElectricalEnergyMeterPeripheral createElectricalEnergyMeterPeripheral(
            ElectricalEnergyMeterBlockEntity blockEntity
    ) {
        return new ElectricalEnergyMeterPeripheral(
                "electrical_energy_meter",
                blockEntity
        );
    }
}

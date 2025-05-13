package me.ajh123.create_distribution.compat.computercraft;

import com.simibubi.create.AllBlockEntityTypes;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import com.simibubi.create.content.logistics.stockTicker.StockTickerBlockEntity;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import me.ajh123.create_distribution.foundation.ModBlocks;
import me.ajh123.create_distribution.content.meter.ElectricalEnergyMeterBlockEntity;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class Peripherals {
    public static void registerPeripheralCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                PeripheralCapability.get(),
                ModBlocks.ENERGY_METER_BLOCK_ENTITY.get(),
                (be, dir) -> createElectricalEnergyMeterPeripheral(be)
        );

        event.registerBlockEntity(
                PeripheralCapability.get(),
                AllBlockEntityTypes.STOCK_TICKER.get(),
                (be, dir) -> createStockTickerPeripheral(be)
        );

        event.registerBlockEntity(
                PeripheralCapability.get(),
                AllBlockEntityTypes.PACKAGER.get(),
                (be, dir) -> createPackagerPeripheral(be)
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

    public static StockTickerPeripheral createStockTickerPeripheral(
            StockTickerBlockEntity blockEntity
    ) {
        return new StockTickerPeripheral(
                "Create_StockTicker",
                blockEntity
        );
    }

    public static PackagerPeripheral createPackagerPeripheral(
            PackagerBlockEntity blockEntity
    ) {
        return new PackagerPeripheral(
                "Create_Packager",
                blockEntity
        );
    }
}

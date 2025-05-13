package me.ajh123.create_distribution.foundation;

import me.ajh123.create_distribution.content.package_return_address.PackageReturnAddress;
import net.minecraft.core.component.DataComponentType;

import java.util.function.Supplier;

public class ModDataComponents {
    public static final Supplier<DataComponentType<PackageReturnAddress>> PACKAGE_RETURN_ADDRESS = ModRegistry.DATA_COMPONENTS.register(
            "package_return_address",
            () -> DataComponentType.<PackageReturnAddress>builder().persistent(PackageReturnAddress.CODEC).build()
    );

    public static void init() {
        // static initialization
    }
}

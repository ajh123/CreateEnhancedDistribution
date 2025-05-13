package me.ajh123.create_distribution.compat.computercraft;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import me.ajh123.create_distribution.content.package_return_address.PackageReturnAddress;
import me.ajh123.create_distribution.content.package_signer.PackageSignerBlockEntity;
import org.jspecify.annotations.Nullable;

public class PackageSignerPeripheral implements IPeripheral {
    private final String type;
    private final PackageSignerBlockEntity blockEntity;

    public PackageSignerPeripheral(String type, PackageSignerBlockEntity blockEntity) {
        this.type = type;
        this.blockEntity = blockEntity;
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
        return blockEntity;
    }

    @LuaFunction(mainThread = true)
    public PackageReturnAddress getReturnAddress() {
         return this.blockEntity.returnAddress;
    }

    @LuaFunction(mainThread = true)
    public void setReturnAddress(String name, String address) {
        this.blockEntity.returnAddress = new PackageReturnAddress(name, address);
    }
}

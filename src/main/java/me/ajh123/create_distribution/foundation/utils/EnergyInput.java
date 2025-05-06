package me.ajh123.create_distribution.foundation.utils;

import net.neoforged.neoforge.energy.IEnergyStorage;

public class EnergyInput implements IEnergyStorage {
    private final EnergyInputHandler handler;

    public EnergyInput(EnergyInputHandler handler) {
        this.handler = handler;
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        if (this.canReceive() && toReceive > 0) {
            if (!simulate) {
                handler.processEnergy(toReceive);
            }

            return toReceive;
        } else {
            return 0;
        }
    }

    @Override
    public int extractEnergy(int i, boolean b) {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    @Override
    public int getEnergyStored() {
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return 0;
    }

    @FunctionalInterface
    public interface EnergyInputHandler {
        void processEnergy(int amount);
    }
}

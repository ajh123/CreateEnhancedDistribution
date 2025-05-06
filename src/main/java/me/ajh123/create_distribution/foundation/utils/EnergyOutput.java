package me.ajh123.create_distribution.foundation.utils;

import net.neoforged.neoforge.energy.IEnergyStorage;

public class EnergyOutput implements IEnergyStorage {
    private final EnergyOutputHandler receiveEnergy;

    public EnergyOutput(EnergyOutputHandler receiveEnergy) {
        this.receiveEnergy = receiveEnergy;
    }

    @Override
    public int receiveEnergy(int toReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int toExtract, boolean simulate) {
        return receiveEnergy.extractEnergy(toExtract, simulate);
    }

    @Override
    public int getEnergyStored() {
        return 0;
    }

    @Override
    public int getMaxEnergyStored() {
        return 0;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }

    @FunctionalInterface
    public interface EnergyOutputHandler {
        int extractEnergy(int requestedAmount, boolean simulate);
    }
}

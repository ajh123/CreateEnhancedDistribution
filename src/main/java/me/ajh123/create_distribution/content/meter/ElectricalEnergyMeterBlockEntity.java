package me.ajh123.create_distribution.content.meter;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import me.ajh123.create_distribution.foundation.ModBlocks;
import me.ajh123.create_distribution.foundation.utils.EnergyInput;
import me.ajh123.create_distribution.foundation.utils.EnergyOutput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElectricalEnergyMeterBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {
    private final EnergyInput inputHandler;
    private final EnergyOutput outputHandler;
    private int maxEnergyStored = 1000;
    private int throughputLastTick;
    private int energyStored;
    private long totalTransferred;

    public ElectricalEnergyMeterBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlocks.ENERGY_METER_BLOCK_ENTITY.get(), pos, blockState);
        this.inputHandler = new EnergyInput(
            this::receiveEnergy
        );
        this.outputHandler = new EnergyOutput(
            this::extractEnergy
        );
    }

    private void receiveEnergy(int amount) {
        if (energyStored < maxEnergyStored) {
            int toStore = Math.min(amount, maxEnergyStored - energyStored);
            this.energyStored += toStore;
        }
    }

    private int extractEnergy(int amount, boolean simulate) {
        if (this.energyStored > 0) {
            int extracted = Math.min(amount, energyStored);
            if (!simulate) {
                this.energyStored -= extracted;
                this.throughputLastTick = extracted;
                this.totalTransferred += extracted;
            }
            this.sendData();
            return extracted;
        }
        this.sendData();
        return 0;
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> list) {

    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        throughputLastTick = tag.getInt("ThroughputLastTick");
        energyStored = tag.getInt("EnergyStored");
        totalTransferred = tag.getLong("TotalTransferred");
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        tag.putInt("ThroughputLastTick", throughputLastTick);
        tag.putInt("EnergyStored", energyStored);
        tag.putLong("TotalTransferred", totalTransferred);
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        tooltip.add(Component.literal("Throughput: " + throughputLastTick + " FE/t"));
        tooltip.add(Component.literal("Total Transferred: " + totalTransferred + " FE"));
        return true;
    }

    public @Nullable IEnergyStorage getEnergyStorage(@Nullable Direction direction) {
        if (direction == null) {
            return null;
        }

        if (direction == Direction.DOWN) {
            return inputHandler;
        } else if (direction == Direction.UP) {
            return outputHandler;
        } else {
            return null;
        }
    }

    public int getThroughput() {
        return this.throughputLastTick;
    }

    public long getTotalTransferred() {
        return totalTransferred;
    }
}

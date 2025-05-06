package me.ajh123.create_distribution.foundation.content.meter;

import com.simibubi.create.api.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import me.ajh123.create_distribution.foundation.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ElectricalEnergyMeterBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {
    private final EnergyStorage inputHandler;
    private final EnergyStorage outputHandler;
    private int throughputLastTick;
    private long totalTransferred;

    public ElectricalEnergyMeterBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlocks.ENERGY_METER_BLOCK_ENTITY.get(), pos, blockState);
        this.inputHandler = new EnergyStorage(
                10000,
                1000,
                1000
        );
        this.outputHandler = new EnergyStorage(
                10000,
                10000,
                1000
        );
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> list) {

    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);
        throughputLastTick = tag.getInt("ThroughputLastTick");
        totalTransferred = tag.getLong("TotalTransferred");
        Tag inputTag = tag.get("InputHandler");
        inputHandler.deserializeNBT(registries, inputTag == null ? IntTag.valueOf(0) : inputTag);
        Tag outputTag = tag.get("OutputHandler");
        outputHandler.deserializeNBT(registries, outputTag == null ? IntTag.valueOf(0) : outputTag);
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        tag.putInt("ThroughputLastTick", throughputLastTick);
        tag.putLong("TotalTransferred", totalTransferred);
        tag.put("InputHandler", inputHandler.serializeNBT(registries));
        tag.put("OutputHandler", outputHandler.serializeNBT(registries));
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

    @Override
    public void tick() {
        super.tick();

        if (level == null || level.isClientSide) return;

        IEnergyStorage input = this.inputHandler;
        IEnergyStorage output = this.outputHandler;

        int extracted = input.extractEnergy(1000, true);
        int accepted = output.receiveEnergy(extracted, true);

        int actualExtracted = input.extractEnergy(accepted, false);
        int actualAccepted = output.receiveEnergy(actualExtracted, false);

        this.throughputLastTick = actualAccepted;
        this.totalTransferred += actualAccepted;

        this.sendData();
    }
}

package me.ajh123.create_distribution.content.package_signer;

import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import me.ajh123.create_distribution.content.package_return_address.PackageReturnAddress;
import me.ajh123.create_distribution.foundation.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PackageSignerBlockEntity extends SmartBlockEntity {
    public ItemStack heldBox;
    public PackageReturnAddress returnAddress;
    public final PackageSignerItemHandler inventory;


    public PackageSignerBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlocks.PACKAGE_SIGNER_BLOCK_ENTITY.get(), pos, blockState);
        this.heldBox = ItemStack.EMPTY;
        this.returnAddress = new PackageReturnAddress("", "");
        this.inventory = new PackageSignerItemHandler(this);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> list) {

    }

    @Override
    protected void read(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(tag, registries, clientPacket);

        if (tag.contains("Return", Tag.TAG_COMPOUND)) {
            CompoundTag returnTag = tag.getCompound("Return");
            if (returnTag.contains("Address", Tag.TAG_STRING) && returnTag.contains("Name", Tag.TAG_STRING)) {
                this.returnAddress = new PackageReturnAddress(returnTag.getString("Name"), returnTag.getString("Address"));
            } else {
                this.returnAddress = new PackageReturnAddress("", "");
            }
        }

        if (tag.contains("HeldBox", Tag.TAG_COMPOUND)) {
            ItemStack.parseOptional(registries, tag.getCompound("HeldBox"));
        }
    }

    @Override
    protected void write(CompoundTag tag, HolderLookup.Provider registries, boolean clientPacket) {
        CompoundTag returnTag = new CompoundTag();
        returnTag.putString("Address", this.returnAddress.address());
        returnTag.putString("Name", this.returnAddress.name());
        tag.put("Return", returnTag);
        tag.put("HeldBox", this.heldBox.saveOptional(registries));
    }

    public @Nullable IItemHandler getItemHandler(@Nullable Direction direction) {
        return this.inventory;
    }
}

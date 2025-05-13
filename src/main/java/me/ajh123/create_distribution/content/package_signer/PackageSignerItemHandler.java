package me.ajh123.create_distribution.content.package_signer;

import com.simibubi.create.content.logistics.box.PackageItem;
import me.ajh123.create_distribution.foundation.ModDataComponents;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.IItemHandlerModifiable;
import org.jetbrains.annotations.NotNull;

public class PackageSignerItemHandler implements IItemHandlerModifiable {
    private final PackageSignerBlockEntity blockEntity;

    public PackageSignerItemHandler(PackageSignerBlockEntity blockEntity) {
        this.blockEntity = blockEntity;
    }

    @Override
    public void setStackInSlot(int slot, @NotNull ItemStack stack) {
        if (slot == 0) {
            this.blockEntity.heldBox = stack;
            this.blockEntity.notifyUpdate();
        }
    }

    @Override
    public int getSlots() {
        return 1;
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int slot) {
        return this.blockEntity.heldBox;
    }

    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (this.blockEntity.heldBox.isEmpty()) {
            if (PackageItem.isPackage(stack)) {
                if (!simulate) {
                    this.blockEntity.heldBox = stack.copy();
                    this.blockEntity.heldBox.setCount(1);
                    this.blockEntity.notifyUpdate();
                }
                return stack.copyWithCount(stack.getCount() - 1);
            } else {
                return stack;
            }
        } else {
            return stack;
        }
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        ItemStack box = this.blockEntity.heldBox;
        if (!simulate) {
            this.setStackInSlot(slot, ItemStack.EMPTY);
        }

        if (!box.has(ModDataComponents.PACKAGE_RETURN_ADDRESS)) {
            box.set(ModDataComponents.PACKAGE_RETURN_ADDRESS, this.blockEntity.returnAddress);
        }

        return box;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public boolean isItemValid(int slot, @NotNull ItemStack stack) {
        return PackageItem.isPackage(stack);
    }
}

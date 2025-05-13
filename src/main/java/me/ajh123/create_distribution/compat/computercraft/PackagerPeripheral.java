/* This file originated from https://github.com/Creators-of-Create/Create/pull/7883 - a pull request for the Create mod.
 *
 * As a result, the license for this file is the same as the Create mod, which is the MIT license, which is as follows:
 *
 * MIT License
 *
 * Copyright (c) 2019 simibubi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package me.ajh123.create_distribution.compat.computercraft;

import com.simibubi.create.content.logistics.BigItemStack;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.packager.PackagerBlockEntity;
import dan200.computercraft.api.detail.VanillaDetailRegistries;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class PackagerPeripheral implements IPeripheral {
    private final String type;
    private final PackagerBlockEntity blockEntity;

    public PackagerPeripheral(String type, PackagerBlockEntity blockEntity) {
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

    @Override
    public @Nullable Object getTarget() {
        return blockEntity;
    }

    @LuaFunction(mainThread = true)
    public final int getItemCount() {
        return blockEntity.getAvailableItems().getTotalCount();
    }

    @LuaFunction(mainThread = true)
    public final boolean makePackage() {
        if (!blockEntity.heldBox.isEmpty())
            return false;
        blockEntity.activate(); // activate() doesn't return a value so i'm walking around it
        if (blockEntity.heldBox.isEmpty())
            return false;
        return true;
    }

    @LuaFunction(mainThread = true)
    public final Map<Integer, Map<String, ?>> list() {
        Map<Integer, Map<String, ?>> result = new HashMap<>();
        int i = 0;
        for (BigItemStack entry : blockEntity.getAvailableItems().getStacks()) {
            i++;
            Map<String, Object> details = new HashMap<>(
                    VanillaDetailRegistries.ITEM_STACK.getBasicDetails(entry.stack));
            details.put("count", entry.count);
            result.put(i, details);
        }
        return result;
    }

    @LuaFunction(mainThread = true)
    public final void setAddress(Optional<String> argument) {
        if (argument.isPresent()) {
            blockEntity.signBasedAddress = argument.get();
        } else {
            blockEntity.signBasedAddress = "";
        }
    }

    @LuaFunction(mainThread = true)
    public final String getAddress() {
        return blockEntity.signBasedAddress;
    }

    @LuaFunction(mainThread = true)
    public final Map<Integer, Map<String, ?>> listDetailed() {
        Map<Integer, Map<String, ?>> result = new HashMap<>();
        int i = 0;
        for (BigItemStack entry : blockEntity.getAvailableItems().getStacks()) {
            i++;
            Map<String, Object> details = new HashMap<>(
                    VanillaDetailRegistries.ITEM_STACK.getDetails(entry.stack));
            details.put("count", entry.count);
            result.put(i, details);
        }
        return result;
    }

    @LuaFunction(mainThread = true)
    public final boolean setPackageAddress(Optional<String> argument) {
        if (!blockEntity.heldBox.isEmpty()) {
            if (argument.isPresent()) {
                PackageItem.addAddress(blockEntity.heldBox, argument.get());
            } else {
                PackageItem.addAddress(blockEntity.heldBox, "");
            }
            return true;
        }
        return false;
    }

    @LuaFunction(mainThread = true)
    public final Map<Integer, Map<String, ?>> getPackageItems() {
        ItemStack box = blockEntity.heldBox;
        if (box.isEmpty() && !PackageItem.isPackage(box))
            return null;
        ItemStackHandler results = PackageItem.getContents(box);
        Map<Integer, Map<String, ?>> result = new HashMap<>();
        for (int i = 0; i < results.getSlots(); i++) {
            ItemStack stack = results.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Map<String, Object> details = new HashMap<>(
                        VanillaDetailRegistries.ITEM_STACK.getDetails(stack));
                result.put(i + 1, details); // +1 because lua
            }
        }
        return result;
    }
}

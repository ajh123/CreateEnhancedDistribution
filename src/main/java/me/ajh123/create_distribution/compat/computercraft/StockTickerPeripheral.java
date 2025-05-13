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
import com.simibubi.create.content.logistics.packagerLink.LogisticallyLinkedBehaviour;
import com.simibubi.create.content.logistics.stockTicker.PackageOrder;
import com.simibubi.create.content.logistics.stockTicker.StockTickerBlockEntity;
import dan200.computercraft.api.detail.VanillaDetailRegistries;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockTickerPeripheral implements IPeripheral {
    private final String type;
    private final StockTickerBlockEntity blockEntity;

    public StockTickerPeripheral(String type, StockTickerBlockEntity blockEntity) {
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
        return blockEntity.getAccurateSummary().getTotalCount();
    }

    @LuaFunction(mainThread = true)
    public final Map<Integer, Map<String, ?>> list() {
        Map<Integer, Map<String, ?>> result = new HashMap<>();
        int i = 0;
        for (BigItemStack entry : blockEntity.getAccurateSummary().getStacks()) {
            i++;
            Map<String, Object> details = new HashMap<>(
                    VanillaDetailRegistries.ITEM_STACK.getBasicDetails(entry.stack));
            details.put("count", entry.count);
            result.put(i, details);
        }
        return result;
    }

    @LuaFunction(mainThread = true)
    public final Map<Integer, Map<String, ?>> listDetailed() {
        Map<Integer, Map<String, ?>> result = new HashMap<>();
        int i = 0;
        for (BigItemStack entry : blockEntity.getAccurateSummary().getStacks()) {
            i++;
            Map<String, Object> details = new HashMap<>(
                    VanillaDetailRegistries.ITEM_STACK.getDetails(entry.stack));
            details.put("count", entry.count);
            result.put(i, details);
        }
        return result;
    }

    /*
     * for every item in the netowrk, this will compare that item to the CC args
     * filter, a table that looks something like this:
     * {
     * name = "minecraft:jungle_log",
     * tags = {
     * ["minecraft:logs"] = true
     * },
     * count = 5
     * },
     * and the second optional String arg which is the address:
     * "home_address"
     * (default value "")
     *
     * It then adds items that match the name if provided, nbt if provided, have all
     * of the tags if provided, has all the enchants if provided and
     * stops looking after adding items equal to count or finishing
     * going through the summary.
     * filter of {} requests all items from the network trollface.jpeg
     */
    @LuaFunction(mainThread = true)
    public final int requestFiltered(IArguments arguments) throws LuaException {
        if (!(arguments.get(0) instanceof Map<?, ?>))
            return 0;
        Map<?, ?> filter = (Map<?, ?>) arguments.get(0);
        String address;
        // Computercraft has forced my hand to make this dollar store filter algo
        List<BigItemStack> validItems = new ArrayList<>();
        int totalItemCount = 0;
        for (BigItemStack entry : blockEntity.getAccurateSummary().getStacks()) {
            if (ComputerUtil.bigItemStackToLuaTableFilter(entry, filter) > 0) {
                // limit the number of items pulled from the system equals to the requested
                // count parameter
                if (filter.containsKey("count")) {
                    Object count = filter.get("count");
                    if (count instanceof Double) {
                        int maxCount = ((Double) count).intValue();
                        int remainingCount = maxCount - totalItemCount;

                        if (remainingCount > 0) {
                            int itemsToAdd = Math.min(remainingCount, entry.count);
                            entry.count = itemsToAdd;
                            totalItemCount += itemsToAdd;
                        } else
                            break;
                    }
                } else {
                    totalItemCount += entry.count;
                }
                validItems.add(entry);
            }
        }
        if (arguments.get(1) instanceof String)
            address = arguments.getString(1);
        else
            address = "";

        PackageOrder order = new PackageOrder(validItems);
        blockEntity.broadcastPackageRequest(LogisticallyLinkedBehaviour.RequestType.RESTOCK, order, null, address);

        /*
         * CatnipServices.NETWORK
         * .sendToServer(new PackageOrderRequestPacket(blockEntity.getBlockPos(), new
         * PackageOrder(itemsToOrder),
         * address, false, new PackageOrder(stacks);
         */
        return totalItemCount;
    }

}

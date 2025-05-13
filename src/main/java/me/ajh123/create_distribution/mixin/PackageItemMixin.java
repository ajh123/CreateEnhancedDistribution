package me.ajh123.create_distribution.mixin;

import com.simibubi.create.content.logistics.box.PackageItem;
import me.ajh123.create_distribution.foundation.ModDataComponents;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(PackageItem.class)
public class PackageItemMixin {
    @Inject(method = "appendHoverText", at = @At("HEAD"))
    public void appendHoverText(ItemStack stack, Item.TooltipContext tooltipContext, List<Component> tooltipComponents, TooltipFlag tooltipFlag, CallbackInfo ci) {
        if (stack.has(ModDataComponents.PACKAGE_RETURN_ADDRESS)) {
            tooltipComponents.add(Component.literal("← " + stack.get(ModDataComponents.PACKAGE_RETURN_ADDRESS).name()).withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.literal("← " + stack.get(ModDataComponents.PACKAGE_RETURN_ADDRESS).address()).withStyle(ChatFormatting.GOLD));
            tooltipComponents.add(Component.literal("----").withStyle(ChatFormatting.GOLD));
        }
    }
}

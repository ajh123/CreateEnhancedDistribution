package me.ajh123.create_distribution.foundation.content.meter;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.simibubi.create.foundation.blockEntity.renderer.SmartBlockEntityRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

public class ElectricalEnergyMeterRenderer extends SmartBlockEntityRenderer<ElectricalEnergyMeterBlockEntity> {
    public ElectricalEnergyMeterRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected void renderSafe(ElectricalEnergyMeterBlockEntity blockEntity, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(blockEntity, partialTicks, ms, buffer, light, overlay);

        Font font = Minecraft.getInstance().font;
        Direction f = blockEntity.getBlockState().getValue(ElectricalEnergyMeterBlock.FACING);

        ms.pushPose();

        // Step 1: Translate half‑block plus a hair outside the face
        double inset = 0.501;
        double tx = 0.5 + f.getStepX() * inset;
        double ty = 0.5 + f.getStepY() * inset;
        double tz = 0.5 + f.getStepZ() * inset;
        ms.translate(tx, ty, tz);

        // Step 2: Rotate so “front” faces us
        ms.mulPose(Axis.YP.rotationDegrees(-f.toYRot()));

        // Step 3: Scale down and flip Y
        float s = 1 / 32f;
        ms.scale(s, -s, s);

        // Step 4: Shift the baseline up by half of the text’s height
        //         (because you flipped Y, a negative translate moves it up)
        float halfTextHeight = font.lineHeight / 2f;
        ms.translate(0, -halfTextHeight, 0);

        // Step 5: Draw centred horizontally at y=0 (which is now text mid‑line)
        String energyStr = blockEntity.getThroughput() + " FE/t";
        FormattedCharSequence seq = Component.literal(energyStr).getVisualOrderText();
        float halfWidth = font.width(seq) / 2f;

        font.drawInBatch(
                seq,
                -halfWidth,              // centre on X
                0f,                      // baseline now at face‑centre
                0xFFFFFF,                // colour
                false,                   // no shadow
                ms.last().pose(),        // transformation
                buffer,
                Font.DisplayMode.SEE_THROUGH, // see-through
                0,                       // bg
                light
        );

        ms.popPose();
    }
}

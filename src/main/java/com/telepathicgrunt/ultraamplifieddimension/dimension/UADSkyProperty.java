package com.telepathicgrunt.ultraamplifieddimension.dimension;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.world.DimensionRenderInfo;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class UADSkyProperty extends DimensionRenderInfo {
    public UADSkyProperty() {
        super(UltraAmplifiedDimension.UADConfig.cloudHeight.get(), true, FogType.NORMAL, false, false);
    }

    @Override
    // thick fog or no
    public boolean func_230493_a_(int camX, int camY) {
        return UltraAmplifiedDimension.UADConfig.heavyFog.get();
    }

    @Nonnull
    @Override
    // sky/fog color
    public Vector3d func_230494_a_(Vector3d color, float sunHeight) {
        return adjustFogColorByHeight(color, sunHeight);
    }

    public Vector3d adjustFogColorByHeight(Vector3d color, float celestialAngle) {

        float f = MathHelper.cos(celestialAngle * ((float) Math.PI * 2F)) * 2.0F + 0.5F;
        f = MathHelper.clamp(f, 0.0F, 1.0F);
        double f1 = color.x;
        double f2 = color.y;
        double f3 = color.z;

        //returns a multiplier between 0 and 1 and will decrease the lower down the player gets from 256
        float multiplierOfBrightness = getHeightBasedModifier();

        f1 = f1 * (f * 0.94F + 0.06F) * multiplierOfBrightness;
        f2 = f2 * (f * 0.94F + 0.06F) * multiplierOfBrightness;
        f3 = f3 * (f * 0.91F + 0.09F) * multiplierOfBrightness;
        return new Vector3d(f1, f2, f3);
    }

    private float getHeightBasedModifier() {
        @SuppressWarnings("resource")
        ClientPlayerEntity player = Minecraft.getInstance().player;
        float multiplierOfBrightness = 1;
        if(player != null){
            int fullBrightnessFromTop = 56; // brightness of 1 starts at y = 200
            int noBrightnessFromBottom = 90; // brightness of 0 starts at y = 90
            multiplierOfBrightness = (float) ((player.getEyePosition(1).y - noBrightnessFromBottom) / Math.max(player.worldClient.getDimensionType().getLogicalHeight() - fullBrightnessFromTop - noBrightnessFromBottom, 1));
        }
        return Math.min(Math.max(multiplierOfBrightness, 0), 1);
    }

}

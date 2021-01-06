package com.telepathicgrunt.ultraamplifieddimension.dimension.biomeprovider.layer.transformers;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;
import net.minecraft.world.gen.layer.traits.IDimOffset1Transformer;

public interface CastleWithPositionTransformer extends IAreaTransformer1, IDimOffset1Transformer {
    int apply(INoiseRandom context, int north, int west, int south, int east, int center, int x, int z);

    default int apply(IExtendedNoiseRandom<?> context, IArea area, int x, int z) {
        return this.apply(context, area.getValue(this.getOffsetX(x + 1), this.getOffsetZ(z + 0)), area.getValue(this.getOffsetX(x + 2), this.getOffsetZ(z + 1)), area.getValue(this.getOffsetX(x + 1), this.getOffsetZ(z + 2)), area.getValue(this.getOffsetX(x + 0), this.getOffsetZ(z + 1)), area.getValue(this.getOffsetX(x + 1), this.getOffsetZ(z + 1)), x, z);
    }
}
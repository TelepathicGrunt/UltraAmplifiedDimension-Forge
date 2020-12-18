package com.telepathicgrunt.ultraamplifieddimension.mixin;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.dimension.UADDimension;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public class AbstractFireBlockMixin {

	/**
	 * Make it so that Nether Portals can be created and activated in Ultra Amplified Dimension
	 * @author TelepathicGrunt
	 */
	@Inject(method = "canLightPortal(Lnet/minecraft/world/World;)Z",
			at = @At(value = "RETURN"),
			cancellable = true)
	private static void allowUADNetherPortal(World world, CallbackInfoReturnable<Boolean> cir) {
		if(!cir.getReturnValue() && world.getDimensionKey().equals(UADDimension.UAD_WORLD_KEY) && UltraAmplifiedDimension.UADimensionConfig.allowNetherPortal.get()) {
			cir.setReturnValue(true);
		}
	}
}
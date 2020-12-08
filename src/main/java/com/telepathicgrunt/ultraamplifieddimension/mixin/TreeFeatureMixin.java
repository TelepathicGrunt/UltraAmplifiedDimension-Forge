package com.telepathicgrunt.ultraamplifieddimension.mixin;

import com.telepathicgrunt.ultraamplifieddimension.dimension.UADDimension;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.TreeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.Set;

@Mixin(TreeFeature.class)
public class TreeFeatureMixin {

	@Unique
	private boolean modifiedConfig = false;

	/**
	 * If this tree feature is generating in Ultra Amplified Dimension, set the position to be
	 * the incoming pos instead of heightmap due to forcePlacement being almost always true and is
	 * not exposed in codec's JSON. By doing it this way, now vanilla and modded tree can spawn
	 * under Ultra Amplified Dimension's ledges. If a person wants only surface trees in the
	 * dimension, they can make a new configured feature with a decorator that only places on surfaces.
	 *
	 * The other alternatives over this mixin are ether hackier, more fragile, an insane amount of work, or lacks mod compat.
	 * @author TelepathicGrunt
	 */
	@Inject(method = "Lnet/minecraft/world/gen/feature/TreeFeature;place(Lnet/minecraft/world/gen/IWorldGenerationReader;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Ljava/util/Set;Ljava/util/Set;Lnet/minecraft/util/math/MutableBoundingBox;Lnet/minecraft/world/gen/feature/BaseTreeFeatureConfig;)Z",
			at = @At(value = "HEAD"))
	private void setUndergroundTreesInUAD1(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, MutableBoundingBox boundingBoxIn, BaseTreeFeatureConfig configIn, CallbackInfoReturnable<Boolean> cir)
	{
		if(!configIn.forcePlacement &&
			generationReader instanceof WorldGenRegion &&
			((WorldGenRegion)generationReader).getWorld().getDimensionKey().equals(UADDimension.UAD_WORLD_KEY))
		{
			configIn.forcePlacement = true;
			modifiedConfig = true;
		}
	}

	/**
	 * If this tree feature is generating in Ultra Amplified Dimension, set the position to be
	 * the incoming pos instead of heightmap due to forcePlacement being almost always true and is
	 * not exposed in codec's JSON. By doing it this way, now vanilla and modded tree can spawn
	 * under Ultra Amplified Dimension's ledges. If a person wants only surface trees in the
	 * dimension, they can make a new configured feature with a decorator that only places on surfaces.
	 *
	 * The other alternatives over this mixin are ether hackier, more fragile, an insane amount of work, or lacks mod compat.
	 * @author TelepathicGrunt
	 */
	@Inject(method = "Lnet/minecraft/world/gen/feature/TreeFeature;place(Lnet/minecraft/world/gen/IWorldGenerationReader;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Ljava/util/Set;Ljava/util/Set;Lnet/minecraft/util/math/MutableBoundingBox;Lnet/minecraft/world/gen/feature/BaseTreeFeatureConfig;)Z",
			at = @At(value = "RETURN"))
	private void undoUndergroundTreesInUAD1(IWorldGenerationReader generationReader, Random rand, BlockPos positionIn, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, MutableBoundingBox boundingBoxIn, BaseTreeFeatureConfig configIn, CallbackInfoReturnable<Boolean> cir)
	{
		if(modifiedConfig){
			configIn.forcePlacement = false;
			modifiedConfig = false;
		}
	}
//
//	/**
//	 * If this tree feature is generating in Ultra Amplified Dimension, set the position to be
//	 * the incoming pos instead of heightmap due to forcePlacement being almost always true and is
//	 * not exposed in codec's JSON. By doing it this way, now vanilla and modded tree can spawn
//	 * under Ultra Amplified Dimension's ledges. If a person wants only surface trees in the
//	 * dimension, they can make a new configured feature with a decorator that only places on surfaces.
//	 *
//	 * The other alternatives over this mixin are ether hackier, more fragile, an insane amount of work, or lacks mod compat.
//	 * @author TelepathicGrunt
//	 */
//	@ModifyVariable(method = "Lnet/minecraft/world/gen/feature/TreeFeature;place(Lnet/minecraft/world/gen/IWorldGenerationReader;Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Ljava/util/Set;Ljava/util/Set;Lnet/minecraft/util/math/MutableBoundingBox;Lnet/minecraft/world/gen/feature/BaseTreeFeatureConfig;)Z",
//			at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/util/math/BlockPos;getY()I", ordinal = 0))
//	private int setUndergroundTreesInUAD1(int heightmapY, IWorldGenerationReader generationReader, Random rand, BlockPos positionIn)
//	{
//		if(generationReader instanceof WorldGenRegion && ((WorldGenRegion)generationReader).getWorld().getDimensionKey().equals(UADDimension.UAD_WORLD_KEY)){
//			return positionIn.getY();
//		}
//		else{
//			return heightmapY;
//		}
//	}
}
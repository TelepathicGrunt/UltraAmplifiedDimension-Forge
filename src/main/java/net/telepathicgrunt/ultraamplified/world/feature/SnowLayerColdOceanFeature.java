package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SnowLayerColdOceanFeature extends Feature<NoFeatureConfig> {
	public SnowLayerColdOceanFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory) {
		super(configFactory);
	}

	public static boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> p_212245_2_, Random p_212245_3_,
			BlockPos blockPos, NoFeatureConfig p_212245_5_, Biome biome) {
		
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		BlockPos.Mutable blockpos$Mutable1 = new BlockPos.Mutable();

		int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING, blockPos.getX(), blockPos.getZ());
		blockpos$Mutable.setPos(blockPos.getX(), y, blockPos.getZ());
		blockpos$Mutable1.setPos(blockpos$Mutable).move(Direction.DOWN, 1);

		if (blockpos$Mutable.getY() >= 0 && blockpos$Mutable.getY() < 256 && world.func_226658_a_(LightType.BLOCK, blockpos$Mutable) < 10) {

			BlockState iblockstate = world.getBlockState(blockpos$Mutable);
			if (iblockstate.isAir(world, blockpos$Mutable) && Blocks.SNOW.getDefaultState().isValidPosition(world, blockpos$Mutable)) {

				world.setBlockState(blockpos$Mutable, Blocks.SNOW.getDefaultState(), 2);
				BlockState iblockstate2 = world.getBlockState(blockpos$Mutable1);

				if (iblockstate2.has(SnowyDirtBlock.SNOWY)) {
					world.setBlockState(blockpos$Mutable1, iblockstate2.with(SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
				}
			}
		}

		return true;
	}

	// unused as snowlayerhandlerfeature will call the above place method
	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		return false;
	}
	
}
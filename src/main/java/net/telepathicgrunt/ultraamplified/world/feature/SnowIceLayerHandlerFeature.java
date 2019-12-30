package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeGenHelper;

public class SnowIceLayerHandlerFeature extends Feature<NoFeatureConfig> {

	public SnowIceLayerHandlerFeature(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51435_1_) {
		super(p_i51435_1_);
	}

	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

		for (int xOffset = 0; xOffset < 16; xOffset++) {
			for (int zOffset = 0; zOffset < 16; zOffset++) {
				blockpos$mutable.setPos(pos.add(xOffset, 0, zOffset));
				Biome biome = worldIn.func_226691_t_(blockpos$mutable);

				if (BiomeGenHelper.frozenBiomes.contains(biome)) 
				{
					IceAndSnowAtAllLayer.place(worldIn, generator, rand, blockpos$mutable, config, biome);
				}
				else if(BiomeGenHelper.coldOceanBiomes.contains(biome)) 
				{
					FeatureUA.SNOW_FOR_COLD_OCEAN.place(worldIn, generator, rand, blockpos$mutable, config);
				}
				else 
				{
					IceAndSnowFeatureUA.place(worldIn, generator, rand, blockpos$mutable, config, biome);
				}
			}
		}

		return true;
	}
}
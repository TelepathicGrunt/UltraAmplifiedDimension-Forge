package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.telepathicgrunt.ultraamplified.UltraAmplified;

import java.util.Random;
import java.util.function.Function;


public class SnowIceAllLayer extends Feature<NoFeatureConfig>
{
	public SnowIceAllLayer(Codec<NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}


	public static boolean generate(ISeedReader world, ChunkGenerator chunkSettings, Random random, BlockPos pos, NoFeatureConfig config, Biome biome)
	{
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		BlockPos.Mutable blockpos$Mutable1 = new BlockPos.Mutable();

		for (int y = 256; y > UltraAmplified.UATerrainConfig.seaLevel.get() - 1; --y)
		{

			blockpos$Mutable.setPos(pos.getX(), y, pos.getZ());
			blockpos$Mutable1.setPos(blockpos$Mutable).move(Direction.DOWN);

			if (world.getBlockState(blockpos$Mutable).getMaterial() == Material.AIR && world.getBlockState(blockpos$Mutable1).getMaterial() != Material.AIR)
			{

				if (!world.getBlockState(blockpos$Mutable1).getFluidState().isEmpty() && biome.doesWaterFreeze(world, blockpos$Mutable1, false))
				{
					world.setBlockState(blockpos$Mutable1, Blocks.ICE.getDefaultState(), 2);
				}

				if (biome.doesSnowGenerate(world, blockpos$Mutable))
				{
					world.setBlockState(blockpos$Mutable, Blocks.SNOW.getDefaultState(), 2);
					BlockState iblockstate = world.getBlockState(blockpos$Mutable1);

					if (iblockstate.has(SnowyDirtBlock.SNOWY))
					{
						world.setBlockState(blockpos$Mutable1, iblockstate.with(SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
					}
				}
			}
		}
		return true;
	}


	// unused as snowlayerhandlerfeature will call the above place method
	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config)
	{
		return false;
	}
}
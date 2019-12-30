package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

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
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class IceAndSnowAtAllLayer extends Feature<NoFeatureConfig> {
	public IceAndSnowAtAllLayer(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	public static boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos pos, NoFeatureConfig config, Biome biome) {
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		BlockPos.Mutable blockpos$Mutable1 = new BlockPos.Mutable();

		for (int y = 256; y > ConfigUA.seaLevel - 1; --y) 
		{

			blockpos$Mutable.setPos(pos.getX(), y, pos.getZ());
			blockpos$Mutable1.setPos(blockpos$Mutable).move(Direction.DOWN, 1);

			if (worldIn.getBlockState(blockpos$Mutable).getMaterial() == Material.AIR && 
					worldIn.getBlockState(blockpos$Mutable1).getMaterial() != Material.AIR) 
			{
				
				if (!worldIn.getBlockState(blockpos$Mutable1).getFluidState().isEmpty() && 
						biome.doesWaterFreeze(worldIn, blockpos$Mutable1, false)) 
				{
					worldIn.setBlockState(blockpos$Mutable1, Blocks.ICE.getDefaultState(), 2);
				}

				if (biome.doesSnowGenerate(worldIn, blockpos$Mutable)) 
				{
					worldIn.setBlockState(blockpos$Mutable, Blocks.SNOW.getDefaultState(), 2);
					BlockState iblockstate = worldIn.getBlockState(blockpos$Mutable1);
					
					if (iblockstate.has(SnowyDirtBlock.SNOWY)) 
					{
						worldIn.setBlockState(blockpos$Mutable1, iblockstate.with(SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
					}
				}
			}
		}
		return true;
	}

	// unused as snowlayerhandlerfeature will call the above place method
	@Override
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		return false;
	}
}
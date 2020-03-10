package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.Tags;


public class UndergroundPocket extends Feature<OreFeatureConfig>
{
	protected static final Set<Block> CARVABLE_NON_STONE_BLOCKS = 
			ImmutableSet.of(
				Blocks.DIRT, Blocks.COARSE_DIRT, 
				Blocks.PODZOL, Blocks.GRASS_BLOCK,
				Blocks.MYCELIUM, Blocks.WATER, 
				Blocks.LAVA, Blocks.OBSIDIAN,  
				Blocks.GRAVEL, Blocks.END_STONE,
				Blocks.SAND,  Blocks.SANDSTONE,
				Blocks.RED_SAND,  Blocks.RED_SANDSTONE,
				Blocks.ICE, Blocks.TERRACOTTA,
				Blocks.BLACK_TERRACOTTA, Blocks.BLUE_TERRACOTTA,
				Blocks.BROWN_TERRACOTTA, Blocks.CYAN_TERRACOTTA,
				Blocks.GREEN_TERRACOTTA, Blocks.GRAY_TERRACOTTA,
				Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA,
				Blocks.MAGENTA_TERRACOTTA, Blocks.LIME_TERRACOTTA,
				Blocks.PINK_TERRACOTTA, Blocks.ORANGE_TERRACOTTA,
				Blocks.RED_TERRACOTTA, Blocks.PURPLE_TERRACOTTA,
				Blocks.YELLOW_TERRACOTTA, Blocks.WHITE_TERRACOTTA,
				Blocks.BLUE_ICE, Blocks.CLAY,
				Blocks.PACKED_ICE, Blocks.NETHERRACK);
	
	public UndergroundPocket(Function<Dynamic<?>, ? extends OreFeatureConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random rand, BlockPos position, OreFeatureConfig config)
	{
		BlockPos.Mutable blockposMutable = new BlockPos.Mutable();
		Block blockToReplace;
		float angleOfRotation = (float) (Math.PI * rand.nextFloat());
		float sinOfAngle = MathHelper.sin(angleOfRotation);
		float cosOfAngle = MathHelper.cos(angleOfRotation);
		float size = config.size * 0.5f;
		
		for(int y = (int) -size/3; y <= size/3; y++)
		{
			float percentageOfRadius = 1f - (((float)y)/size) * (((float)y)/size)*3;
			float majorRadiusSq = (size * percentageOfRadius) * (size * percentageOfRadius);
			float minorRadiusSq = (size * 0.7f * percentageOfRadius) * (size * 0.7f * percentageOfRadius);
			
			for(int x = (int) -size; x < size; x++)
			{
				for(int z = (int) -size; z < size; z++)
				{
					float majorComp = x * cosOfAngle - z * sinOfAngle; 
					float minorComp = x * sinOfAngle + z * cosOfAngle; 
					
					float result = (majorComp * majorComp)/(majorRadiusSq*majorRadiusSq) + (minorComp*minorComp)/(minorRadiusSq*minorRadiusSq);
					if(result * 100f < 1f && !(x == 0 && z == 0 && y * y >= (size*size)))
					{
						blockposMutable.setPos(position.getX() + x + 8, position.getY() + y, position.getZ() + z + 8);
						blockToReplace = world.getBlockState(blockposMutable).getBlock();
						
						if(isStone(blockToReplace) || Tags.Blocks.ORES.contains(blockToReplace) || CARVABLE_NON_STONE_BLOCKS.contains(blockToReplace))
						{
							world.setBlockState(blockposMutable, config.state, 3);
						}
					}
				}
			}
		}
		
		return true;
	}

}

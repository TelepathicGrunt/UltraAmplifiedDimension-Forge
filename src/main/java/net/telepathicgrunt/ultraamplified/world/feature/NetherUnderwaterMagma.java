package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.telepathicgrunt.ultraamplified.world.biome.BiomeInit;


public class NetherUnderwaterMagma extends Feature<NoFeatureConfig>
{
	public NetherUnderwaterMagma(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private final static BlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
	private final static BlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();


	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos pos, NoFeatureConfig configBlock)
	{

		//set y to 0
		pos.down(pos.getY());
		BlockState currentblock;
		Biome netherBiome = null;
		boolean hasNetherBiome = false;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(pos);

		//checks to see if there is an nether biome in this chunk
		//breaks out of nested loop if nether is found
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				//only check along chunk edges for better performance
				if (!(z == 0 || z == 16 || x == 0 || x == 16))
				{
					continue;
				}

				netherBiome = world.getBiome(blockpos$Mutable.add(x, 0, z));
				if (netherBiome == BiomeInit.NETHERLAND)
				{
					hasNetherBiome = true;
					x = 16;
					break;
				}
			}
		}

		//does not do anything if there is no nether biome
		if (!hasNetherBiome)
		{
			return false;
		}

		//ocean nether was found and thus, is not null. Can safely now add the magma layer
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{

				netherBiome = world.getBiome(blockpos$Mutable.add(x, 0, z));
				if (netherBiome != BiomeInit.NETHERLAND)
				{
					continue;
				}

				currentblock = world.getBlockState(blockpos$Mutable.add(x, ConfigUA.seaLevel - 7, z));

				//if water, place magma block
				if (currentblock.getMaterial() == Material.WATER)
				{
					world.setBlockState(blockpos$Mutable.add(x, ConfigUA.seaLevel - 7, z), MAGMA, 3);
				}

				//check if lava below is bordering water and set it to obsidian if so
				for (int y = ConfigUA.seaLevel - 8; y > 20; y--)
				{

					for (Direction face : Direction.Plane.HORIZONTAL)
					{
						currentblock = world.getBlockState(blockpos$Mutable.add(x, y, z).offset(face));

						if (currentblock.getMaterial() == Material.WATER)
						{
							world.setBlockState(blockpos$Mutable.add(x, y, z), OBSIDIAN, 2);
							continue;
						}
					}
				}
			}
		}

		return true;
	}
}
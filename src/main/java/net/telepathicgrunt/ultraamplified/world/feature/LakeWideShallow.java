package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;


public class LakeWideShallow extends Feature<BlockStateFeatureConfig>
{

	public LakeWideShallow(Function<Dynamic<?>, ? extends BlockStateFeatureConfig> configFactory)
	{
		super(configFactory);
	}


	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos position, BlockStateFeatureConfig configBlock)
	{

		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position.down(2));
		boolean[] lakeMask = new boolean[2048];
		int maxInterations = random.nextInt(4) + 4;

		for (int currentMaskInteration = 0; currentMaskInteration < maxInterations; ++currentMaskInteration)
		{
			double rawRandX = random.nextDouble() * 6.0D + 3.0D;
			double rawRandZ = random.nextDouble() * 6.0D + 3.0D;
			double randX = random.nextDouble() * (16.0D - rawRandX - 2.0D) + 1.0D + rawRandX / 2.0D;
			double randZ = random.nextDouble() * (16.0D - rawRandZ - 2.0D) + 1.0D + rawRandZ / 2.0D;

			for (int x = 1; x < 15; ++x)
			{
				for (int z = 1; z < 15; ++z)
				{
					for (int y = 0; y < 5; ++y)
					{
						double xMagnetude = ((double) x - randX) / (rawRandX / 2.0D);
						double zMagnetude = ((double) z - randZ) / (rawRandZ / 2.0D);
						double squaredMagnetude = xMagnetude * xMagnetude + zMagnetude * zMagnetude;
						if (squaredMagnetude < 1.0D)
						{
							lakeMask[(x * 16 + z) * 8 + y] = true;
						}
					}
				}
			}
		}

		//creates the actual lakes
		boolean containedFlag = true;
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				int y = 5;

        		blockpos$Mutable.setPos(position).move(x, y, z);
				Material material = world.getBlockState(blockpos$Mutable).getMaterial();

				//finds first solid block of land starting from 5 blocks higher than initial input position
				while (!material.isSolid() && material != Material.WATER && y > 0)
				{
					y--;
					material = world.getBlockState(blockpos$Mutable.move(Direction.DOWN)).getMaterial();
				}

				
				//checks if the spot is solid all around (diagonally too) and has nothing solid above it
				containedFlag = checkIfValidSpot(world, blockpos$Mutable);
				
				
				//Is spot within the mask (sorta a roundish area) and is contained
				if (lakeMask[(x * 16 + z) * 8 + y] && containedFlag)
				{
					world.setBlockState(blockpos$Mutable, configBlock.state, 2);
				}
			}
		}
		return true;
	}
	
	/**
	 * checks if the spot is surrounded by solid blocks below and all around horizontally plus nothing solid above.
	 * @param world - world to check for materials in
	 * @param blockpos$Mutable - location to check if valid
	 * @return - if the spot is valid
	 */
	private boolean checkIfValidSpot(IWorld world, BlockPos.Mutable blockpos$Mutable)
	{
		Material material;
		
		//must be solid all around even diagonally
		for (int x2 = -1; x2 < 2; x2++)
		{
			for (int z2 = -1; z2 < 2; z2++)
			{
				material = world.getBlockState(blockpos$Mutable.add(x2, 0, z2)).getMaterial();

				if (!material.isSolid() && material != Material.WATER)
				{
					return false;
				}
			}
		}

		//must be solid below
		material = world.getBlockState(blockpos$Mutable.down()).getMaterial();
		if (!material.isSolid() && material != Material.WATER)
		{
			return false;
		}

		//cannot have solid or water above as that makes the lake
		//no longer shallow or on the surface
		material = world.getBlockState(blockpos$Mutable.up()).getMaterial();
		if (material.isSolid() || material == Material.WATER)
		{
			return false;
		}

		//Adjacent blocks must be solid    
		/*
		 * for (Direction face : Direction.values()) {
		 * 
		 * material = world.getBlockState(blockpos$Mutable.add(x, y, z).offset(face)).getMaterial();
		 * 
		 * if(face == Direction.UP) { if(material.isSolid() || material == Material.WATER) { notContainedFlag = true; } } else
		 * if(!material.isSolid() && material != Material.WATER ) { notContainedFlag = true; } }
		 */

		return true;
	}
}
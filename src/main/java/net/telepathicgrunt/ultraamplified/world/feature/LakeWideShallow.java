package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;


public class LakeWideShallow extends Feature<BlockStateFeatureConfig>
{

	protected static final Set<Material> unacceptableSolidMaterials = 
		ImmutableSet.of(
			Material.BAMBOO, 
			Material.BAMBOO_SAPLING,
			Material.LEAVES, 
			Material.WEB,
			Material.CACTUS,
			Material.ANVIL,
			Material.GOURD,
			Material.CAKE,
			Material.DRAGON_EGG,
			Material.BARRIER,
			Material.CAKE
		);
	
	
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
						if (squaredMagnetude < 1.2D)
						{
							lakeMask[(x * 16 + z) * 8 + y] = true;
						}
					}
				}
			}
		}

		//creates the actual lakes
		boolean containedFlag = true;
		Material material;
		BlockState blockState;
		for (int x = 0; x < 16; ++x)
		{
			for (int z = 0; z < 16; ++z)
			{
				int y = 5;

        		blockpos$Mutable.setPos(position).move(x, y, z);
        		blockState = world.getBlockState(blockpos$Mutable);
				material = blockState.getMaterial();

				//Finds first solid block of land starting from 5 blocks higher than initial input position
				//We use unacceptable solid set to help skip solid blocks like leaves.
				while ((!material.isSolid() || unacceptableSolidMaterials.contains(material)) && y > 0)
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
					//check below without moving down
					blockState = world.getBlockState(blockpos$Mutable.down());
					
					//sets the water
					if(isDirt(blockState.getBlock()) && random.nextInt(4) == 0) 
					{
						world.setBlockState(blockpos$Mutable, Blocks.SEAGRASS.getDefaultState(), 2); 
					}
					else 
					{
						world.setBlockState(blockpos$Mutable, configBlock.state, 2); 
					}
					
					
					//remove floating plants so they aren't hovering.
					//check above while moving up one.
					blockState = world.getBlockState(blockpos$Mutable.move(Direction.UP));
					material = blockState.getMaterial();
					
					if(material == Material.PLANTS && blockState.getBlock() != Blocks.LILY_PAD) 
					{
						world.setBlockState(blockpos$Mutable, Blocks.AIR.getDefaultState(), 3); 
					}
					if(material == Material.TALL_PLANTS && blockState.getBlock() != Blocks.VINE) 
					{
						world.setBlockState(blockpos$Mutable, Blocks.AIR.getDefaultState(), 3);
						world.setBlockState(blockpos$Mutable.up(), Blocks.AIR.getDefaultState(), 3);  
					}
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
		BlockState blockState;
		
		//Must be solid all around even diagonally.
		//Will also return false if an unacceptable solid material is found.
		for (int x2 = -1; x2 < 2; x2++)
		{
			for (int z2 = -1; z2 < 2; z2++)
			{
				blockState = world.getBlockState(blockpos$Mutable.add(x2, 0, z2));
				material = blockState.getMaterial();

				if ((!material.isSolid() || unacceptableSolidMaterials.contains(material)) && blockState.getFluidState().getFluidState() != Fluids.WATER)
				{
					return false;
				}
			}
		}

		//must be solid below
		//Will also return false if an unacceptable solid material is found.
		blockState = world.getBlockState(blockpos$Mutable.down());
		material = blockState.getMaterial();
		if ((!material.isSolid() || unacceptableSolidMaterials.contains(material)) && blockState.getFluidState().getFluidState() != Fluids.WATER)
		{
			return false;
		}

		//cannot have solid or water above as that makes the lake no longer shallow or on the surface.
		//Will not check unacceptable solid set to allow leaves to be over water.
		blockState = world.getBlockState(blockpos$Mutable.up());
		material = blockState.getMaterial();
		if (material.isSolid() || blockState.getFluidState().getFluidState() == Fluids.WATER)
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
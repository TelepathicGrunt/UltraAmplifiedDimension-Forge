package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.CountConfig;
import net.telepathicgrunt.ultraamplified.blocks.BlocksInit;


public class GlowPatch extends Feature<CountConfig>
{

	private static Map<BlockState, BlockState> GLOWBLOCKMAP;


	/**
	 * Have to make this map in UltraAmplified setup method since the blocks needs to be initialized first
	 */
	public static void setFillerMap()
	{
		if (GLOWBLOCKMAP == null)
		{
			GLOWBLOCKMAP = new HashMap<BlockState, BlockState>();

			GLOWBLOCKMAP.put(Blocks.DIRT.getDefaultState(), BlocksInit.GLOWDIRT.get().getDefaultState());
			GLOWBLOCKMAP.put(Blocks.COARSE_DIRT.getDefaultState(), BlocksInit.COARSE_GLOWDIRT.get().getDefaultState());
			GLOWBLOCKMAP.put(Blocks.GRASS_BLOCK.getDefaultState(), BlocksInit.GLOWGRASS_BLOCK.get().getDefaultState());
			GLOWBLOCKMAP.put(Blocks.MYCELIUM.getDefaultState(), BlocksInit.GLOWMYCELIUM.get().getDefaultState());
			GLOWBLOCKMAP.put(Blocks.STONE.getDefaultState(), BlocksInit.GLOWSTONE_ORE.get().getDefaultState());
			GLOWBLOCKMAP.put(Blocks.PODZOL.getDefaultState(), BlocksInit.GLOWPODZOL.get().getDefaultState());
			GLOWBLOCKMAP.put(Blocks.SAND.getDefaultState(), BlocksInit.GLOWSAND.get().getDefaultState());
			GLOWBLOCKMAP.put(Blocks.RED_SAND.getDefaultState(), BlocksInit.REDGLOWSAND.get().getDefaultState());
		}
	}


	public GlowPatch(Function<Dynamic<?>, ? extends CountConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random rand, BlockPos position, CountConfig countConfig)
	{
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);

		// tries as times specified to convert a randomly chosen nearby block
		for (int attempts = 0; attempts < countConfig.count; ++attempts)
		{
			// clustered around the center the most
			int gausX = (int) (Math.max(Math.min(rand.nextGaussian() * 3, 16), -16)); // range of -16 to 16
			int gausY = rand.nextInt(4) - rand.nextInt(4); // range of -4 to 4
			int gausZ = (int) (Math.max(Math.min(rand.nextGaussian() * 3, 16), -16)); // range of -16 to 16
			blockpos$Mutable.setPos(position).move(gausX, gausY, gausZ);
			BlockState chosenBlock = world.getBlockState(blockpos$Mutable);
			BlockState chosenAboveBlock = world.getBlockState(blockpos$Mutable.move(Direction.UP));

			if (chosenBlock.getMaterial() != Material.AIR)
			{
				// turns stone into glowstone ore even if no air above
				if (chosenBlock.getBlock() == Blocks.STONE)
				{
					world.setBlockState(blockpos$Mutable.move(Direction.DOWN), GLOWBLOCKMAP.get(chosenBlock), 2);
				}
				// turns valid surface blocks with air above into glowstone variants
				else if (GLOWBLOCKMAP.containsKey(chosenBlock) && chosenAboveBlock.getMaterial() == Material.AIR)
				{
					world.setBlockState(blockpos$Mutable.move(Direction.DOWN), GLOWBLOCKMAP.get(chosenBlock), 2);
				}
			}
		}

		//debugging
		//UltraAmplified.Logger.log(Level.DEBUG, "Glowpatch at "+pos.getX() +", "+pos.getY()+", "+pos.getZ());
		return true;
	}

}

package net.TelepathicGrunt.UltraAmplified.World.Feature;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Blocks.BlocksInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.LightType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.placement.CountConfig;

public class GlowPatch extends Feature<CountConfig> {

	private Map<BlockState, BlockState> GLOWBLOCKMAP;

	public GlowPatch(Function<Dynamic<?>, ? extends CountConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random rand, BlockPos pos, CountConfig countConfig) {
		
		//create the map here because it contains our modded blocks.
		//If we make the map in the constructor or as a class field, it'll cause
		//forge to get stuck whe registering this feature and no other registry events
		//will fire which will cause forge to not load this mod at all. Failing without errors...
		if(GLOWBLOCKMAP == null) {
			Map<BlockState, BlockState> result = new HashMap<BlockState, BlockState>();

			result.put(Blocks.DIRT.getDefaultState(), BlocksInit.GLOWDIRT.getDefaultState());
			result.put(Blocks.COARSE_DIRT.getDefaultState(), BlocksInit.COARSE_GLOWDIRT.getDefaultState());
			result.put(Blocks.GRASS_BLOCK.getDefaultState(), BlocksInit.GLOWGRASS_BLOCK.getDefaultState());
			result.put(Blocks.MYCELIUM.getDefaultState(), BlocksInit.GLOWMYCELIUM.getDefaultState());
			result.put(Blocks.STONE.getDefaultState(), BlocksInit.GLOWSTONE_ORE.getDefaultState());
			result.put(Blocks.PODZOL.getDefaultState(), BlocksInit.GLOWPODZOL.getDefaultState());
			result.put(Blocks.SAND.getDefaultState(), BlocksInit.GLOWSAND.getDefaultState());
			result.put(Blocks.RED_SAND.getDefaultState(), BlocksInit.REDGLOWSAND.getDefaultState());
			
			GLOWBLOCKMAP = result;
		}
		
		
        //UltraAmplified.Logger.log(Level.DEBUG, "Glowpatch at "+pos.getX() +", "+pos.getY()+", "+pos.getZ());
        
        
		
		boolean generatedSuccessfully = false;
		

		// tries as times specified to convert a randomly chosen nearby block
		for (int attempts = 0; attempts < countConfig.count; ++attempts) {

			// clustered around the center the most
			int gausX = (int) (Math.max(Math.min(rand.nextGaussian() * 3, 16), -16)); // range of -16 to 16
			int gausY =  rand.nextInt(4) - rand.nextInt(4); // range of -4 to 4
			int gausZ = (int) (Math.max(Math.min(rand.nextGaussian() * 3, 16), -16)); // range of -16 to 16
			BlockPos blockpos = pos.add(gausX, gausY, gausZ);
			BlockState chosenBlock = worldIn.getBlockState(blockpos);
			BlockState chosenAboveBlock = worldIn.getBlockState(blockpos.up());
			
			if (chosenBlock.getMaterial() != Material.AIR) {

				// turns stone into glowstone ore even if no air above
				if (chosenBlock == Blocks.STONE.getDefaultState()) {

					worldIn.setBlockState(blockpos, GLOWBLOCKMAP.get(chosenBlock), 2);
					generatedSuccessfully = true;
				}
				// turns valid surface blocks with air above into glowstone variants
				else if (GLOWBLOCKMAP.containsKey(chosenBlock) && chosenAboveBlock.getMaterial() == Material.AIR) {

					//if block is receiving too much light from the sky, does not generate glowstone variants here
					if(worldIn.getLightFor(LightType.SKY, blockpos) > 7){
						continue;
					}

					worldIn.setBlockState(blockpos, GLOWBLOCKMAP.get(chosenBlock), 2);
					generatedSuccessfully = true;
				}
			}
		}

		return generatedSuccessfully;
	}

}

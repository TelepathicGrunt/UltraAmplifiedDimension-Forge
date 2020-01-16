package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeInit;

public class ContainUndergroundLiquids extends Feature<NoFeatureConfig> {
	public ContainUndergroundLiquids(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory) {
		super(configFactory);
	}

	// Used to keep track of what block to use to fill in certain air/liquids
	protected BlockState replacementBlock = Blocks.STONE.getDefaultState();
	protected static final BlockState STONE = Blocks.STONE.getDefaultState();

	private static Map<Biome, BlockState> fillerBiomeMap;

	/**
	 * Have to make this map in UltraAmplified setup method since the biomes needs to be initialized first
	 */
	public static void setFillerMap() {
		if (fillerBiomeMap == null) {
			fillerBiomeMap = new HashMap<Biome, BlockState>();

			fillerBiomeMap.put(BiomeInit.NETHERLAND, Blocks.NETHERRACK.getDefaultState());
			fillerBiomeMap.put(BiomeInit.ICED_TERRAIN, Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(BiomeInit.ICE_SPIKES, Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(BiomeInit.DEEP_FROZEN_OCEAN, Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(BiomeInit.FROZEN_OCEAN, Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(BiomeInit.BARREN_END_FIELD, Blocks.END_STONE.getDefaultState());
			fillerBiomeMap.put(BiomeInit.END_FIELD, Blocks.END_STONE.getDefaultState());
		}
	}

	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos position, NoFeatureConfig configBlock) {
		
		boolean notContainedFlag;
		BlockState currentblock;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position.getX(), 0, position.getZ());  //set y to 0

		for (int x = 0; x < 16; ++x) {
			for (int z = 0; z < 16; ++z) {

				for (int y = 61; y >= 10; y--) {

					blockpos$Mutable.setPos(position.getX() + x, y, position.getZ() + z);
					currentblock = world.getBlockState(blockpos$Mutable);

					// move down until we hit an air block
					while (currentblock != Blocks.AIR.getDefaultState() && blockpos$Mutable.getY() > 11) {
						blockpos$Mutable.move(Direction.DOWN);
						currentblock = world.getBlockState(blockpos$Mutable);
					}
					
					//checks one last time at y = 11 to see if we should quit now
					if(blockpos$Mutable.getY() <= 11 && currentblock != Blocks.AIR.getDefaultState()) {
						continue;
					}
					

					// y value is now fully set for rest of code
					// checks to see if we are touching a liquid block
					notContainedFlag = false;

					for (Direction face : Direction.Plane.HORIZONTAL) {

						currentblock = world.getBlockState(blockpos$Mutable.offset(face));
						if (!currentblock.getFluidState().isEmpty()) {
							notContainedFlag = true;
						}
					}

					blockpos$Mutable.move(Direction.UP);
					currentblock = world.getBlockState(blockpos$Mutable);
					if (!currentblock.getFluidState().isEmpty()) {
						notContainedFlag = true;
					}

					blockpos$Mutable.move(Direction.DOWN, 2); //move from above block to below block 
					currentblock = world.getBlockState(blockpos$Mutable);
					if (blockpos$Mutable.getY() > 10 && !currentblock.getFluidState().isEmpty()) {
						notContainedFlag = true;
					}

					// this air block is touching liquid, time to make it solid
					if (notContainedFlag) {

						blockpos$Mutable.move(Direction.UP); //move back to center block
						//grabs what block to use based on what biome we are in
						replacementBlock = fillerBiomeMap.get(world.getBiome(blockpos$Mutable));
						if (replacementBlock == null) {
							replacementBlock = STONE;
						}

						world.setBlockState(blockpos$Mutable, replacementBlock, 2);
					}
				}
			}
		}
		return true;

	}
}
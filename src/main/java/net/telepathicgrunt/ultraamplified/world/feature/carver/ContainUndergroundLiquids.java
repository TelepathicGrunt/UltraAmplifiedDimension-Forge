package net.telepathicgrunt.ultraamplified.world.feature.carver;

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
	 * Have to make this map much later since the biomes needs to be initialized
	 * first and that's delayed a bit
	 */
	public void setFillerMap() {
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

	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random,
			BlockPos pos, NoFeatureConfig configBlock) {

		setFillerMap();

		// set y to 0
		pos.down(pos.getY());

		boolean notContainedFlag;
		BlockState currentblock;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(pos);

		for (int x = 0; x < 16; ++x) {
			for (int z = 0; z < 16; ++z) {

				for (int y = 61; y >= 10; y--) {

					blockpos$Mutable.setPos(pos.getX() + x, 0, pos.getZ() + z);
					currentblock = world.getBlockState(blockpos$Mutable.up(y));

					// move down until we hit an air block
					while (currentblock != Blocks.AIR.getDefaultState() && y > 11) {
						y--;
						currentblock = world.getBlockState(blockpos$Mutable.up(y));
					}
					
					//checks one last time at y = 11 to see if we should quit now
					if(y <= 11 && currentblock != Blocks.AIR.getDefaultState()) {
						continue;
					}

					// y value is now fully set for rest of code
					blockpos$Mutable.setPos(pos.getX() + x, y, pos.getZ() + z);

					// checks to see if we are touching a liquid block
					notContainedFlag = false;

					for (Direction face : Direction.Plane.HORIZONTAL) {

						currentblock = world.getBlockState(blockpos$Mutable.offset(face));
						if (!currentblock.getFluidState().isEmpty()) {
							notContainedFlag = true;
						}
					}

					currentblock = world.getBlockState(blockpos$Mutable.up());
					if (!currentblock.getFluidState().isEmpty()) {
						notContainedFlag = true;
					}

					currentblock = world.getBlockState(blockpos$Mutable.down());
					if (blockpos$Mutable.down().getY() > 10 && !currentblock.getFluidState().isEmpty()) {
						notContainedFlag = true;
					}

					// this air block is touching liquid, time to make it solid
					if (notContainedFlag) {
						
						//grabs what block to use based on what biome we are in
						replacementBlock = fillerBiomeMap.get(world.func_226691_t_(blockpos$Mutable));
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
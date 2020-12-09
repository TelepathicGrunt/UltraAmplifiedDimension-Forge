package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static com.telepathicgrunt.ultraamplifieddimension.utils.GeneralUtils.biomeRegistryKey;


public class ContainUndergroundLiquids extends Feature<NoFeatureConfig>
{
	public ContainUndergroundLiquids(Codec<NoFeatureConfig> configFactory) {
		super(configFactory);
	}

	// Used to keep track of what block to use to fill in certain air/liquids
	protected BlockState replacementBlock = Blocks.STONE.getDefaultState();
	protected static final BlockState STONE = Blocks.STONE.getDefaultState();

	private static Map<RegistryKey<Biome>, BlockState> fillerBiomeMap;
	static {
		fillerBiomeMap = new HashMap<>();

		fillerBiomeMap.put(biomeRegistryKey("nether_wasteland"), Blocks.NETHERRACK.getDefaultState());
		fillerBiomeMap.put(biomeRegistryKey("iced_terrain"), Blocks.ICE.getDefaultState());
		fillerBiomeMap.put(biomeRegistryKey("ice_spikes"), Blocks.ICE.getDefaultState());
		fillerBiomeMap.put(biomeRegistryKey("deep_frozen_ocean"), Blocks.ICE.getDefaultState());
		fillerBiomeMap.put(biomeRegistryKey("frozen_ocean"), Blocks.ICE.getDefaultState());
		fillerBiomeMap.put(biomeRegistryKey("barren_end_fields"), Blocks.END_STONE.getDefaultState());
		fillerBiomeMap.put(biomeRegistryKey("end_fields"), Blocks.END_STONE.getDefaultState());
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkSettings, Random random, BlockPos position, NoFeatureConfig configBlock) {
		BlockState currentblock;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		MutableRegistry<Biome> biome_registry = world.getWorld().func_241828_r().getRegistry(Registry.BIOME_KEY);

		for (int x = 0; x < 16; ++x) {
			for (int z = 0; z < 16; ++z) {
				blockpos$Mutable.setPos(position.getX() + x, 61, position.getZ() + z);
				while (blockpos$Mutable.getY() > 10) {
					currentblock = world.getBlockState(blockpos$Mutable);

					// move down until we hit a liquid filled block
					while (currentblock.getFluidState().isEmpty() && blockpos$Mutable.getY() > 10) {
						currentblock = world.getBlockState(blockpos$Mutable.move(Direction.DOWN));
					}

					//if too low, break and go to next xz coordinate
					if (blockpos$Mutable.getY() <= 10) {
						break;
					}

					// y value is now fully set for rest of code
					// checks to see if we are touching an air block
					for (Direction face : Direction.values()) {
						blockpos$Mutable.move(face);
						currentblock = world.getBlockState(blockpos$Mutable);
						if (currentblock.isAir()) {
							//grabs what block to use based on what biome we are in
							replacementBlock = fillerBiomeMap.get(biome_registry.getOptionalKey(world.getBiome(blockpos$Mutable)).orElse(null));
							if (replacementBlock == null) {
								replacementBlock = STONE;
							}

							world.setBlockState(blockpos$Mutable, replacementBlock, 2);
						}
						blockpos$Mutable.move(face.getOpposite());
					}

					blockpos$Mutable.move(Direction.DOWN);
				}
			}
		}
		return true;
	}
}
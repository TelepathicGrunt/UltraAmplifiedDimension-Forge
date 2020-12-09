package com.telepathicgrunt.ultraamplifieddimension.world.carver;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.mixin.BiomeContainerAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import static com.telepathicgrunt.ultraamplifieddimension.utils.GeneralUtils.biomeRegistryKey;


public class RavineCarver extends WorldCarver<ProbabilityConfig>
{
	private final float[] WALL_LEDGES = new float[1024];
	protected static final BlockState STONE = Blocks.STONE.getDefaultState();
	protected BlockState fillerBlock = Blocks.STONE.getDefaultState();
	protected static final BlockState LAVA = Blocks.LAVA.getDefaultState();
	protected static final BlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
	protected static final BlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();

	private static final Map<BlockState, BlockState> canReplaceMap;
	static
	{
		Map<BlockState, BlockState> result = new HashMap<BlockState, BlockState>();

		result.put(Blocks.NETHERRACK.getDefaultState(), Blocks.NETHERRACK.getDefaultState());
		result.put(Blocks.ICE.getDefaultState(), Blocks.ICE.getDefaultState());
		result.put(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.ICE.getDefaultState());
		result.put(Blocks.END_STONE.getDefaultState(), Blocks.END_STONE.getDefaultState());

		canReplaceMap = result;
	}

	private static Map<RegistryKey<Biome>, BlockState> fillerBiomeMap;
	static {
		if (fillerBiomeMap == null)
		{
			fillerBiomeMap = new HashMap<>();

			fillerBiomeMap.put(biomeRegistryKey("nether_wasteland"), Blocks.NETHERRACK.getDefaultState());
			fillerBiomeMap.put(biomeRegistryKey("iced_terrain"), Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(biomeRegistryKey("ice_spikes"), Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(biomeRegistryKey("deep_frozen_ocean"), Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(biomeRegistryKey("frozen_ocean"), Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(biomeRegistryKey("barren_end_fields"), Blocks.END_STONE.getDefaultState());
			fillerBiomeMap.put(biomeRegistryKey("end_fields"), Blocks.END_STONE.getDefaultState());
		}
	}

	private static Map<RegistryKey<Biome>, BlockState> lavaFloorBiomeMap;
	static {
		if (lavaFloorBiomeMap == null)
		{
			lavaFloorBiomeMap = new HashMap<>();

			lavaFloorBiomeMap.put(biomeRegistryKey("iced_terrain"), Blocks.OBSIDIAN.getDefaultState());
			lavaFloorBiomeMap.put(biomeRegistryKey("ice_spikes"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeRegistryKey("relic_snowy_taiga"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeRegistryKey("snowy_rocky_taiga"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeRegistryKey("snowy_taiga"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeRegistryKey("snowy_tundra"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeRegistryKey("frozen_desert"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeRegistryKey("deep_frozen_ocean"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeRegistryKey("frozen_ocean"), Blocks.MAGMA_BLOCK.getDefaultState());
		}
	}

	private SimpleRegistry<Biome> biomeRegistry;

	public RavineCarver(Codec<ProbabilityConfig> codec, int height) {
		super(codec, height);
	}

	@Override
	public boolean shouldCarve(Random random, int chunkX, int chunkZ, ProbabilityConfig config) {
		return random.nextFloat() <= config.probability;
	}

	@Override
	public boolean carveRegion(IChunk region, Function<BlockPos, Biome> biomeBlockPos, Random random, int seaLevel, int chunkX, int chunkZ, int originalX, int originalZ, BitSet mask, ProbabilityConfig config) {
		IObjectIntIterable<Biome> reg = region.getBiomes() != null ? ((BiomeContainerAccessor)region.getBiomes()).getBiomeRegistry() : null;
		if(reg instanceof SimpleRegistry && reg != biomeRegistry){
			biomeRegistry = (SimpleRegistry<Biome>)((BiomeContainerAccessor)region.getBiomes()).getBiomeRegistry();
		}
		else{
			biomeRegistry = null;
		}

		int i = (this.func_222704_c() * 2 - 1) * 16;
		double xpos = chunkX * 16 + random.nextInt(16);
		//TODO:  make codec that specifies height and range of height here
		double height = random.nextInt(random.nextInt(2) + 1) + 42; // 25 : (double) (random.nextInt(random.nextInt(2) + 1) + 42);
		double zpos = chunkZ * 16 + random.nextInt(16);
		float xzNoise2 = random.nextFloat() * ((float) Math.PI * 2F);
		float xzCosNoise = (random.nextFloat() - 0.5F) / 8.0F;
		float widthHeightBase = (random.nextFloat() * 2.0F + random.nextFloat()) * 2.0F;
		int maxIteration = i - random.nextInt(i / 4);
		// make a codec that handles the height multiplier
		//biome == UABiomes.NETHERLAND ? random.nextDouble() + 2.5D : random.nextDouble() / 3 + 1.9D
		this.func_202535_a(region, biomeBlockPos, random.nextLong(), originalX, originalZ, xpos, height, zpos, widthHeightBase, xzNoise2, xzCosNoise, maxIteration, random.nextDouble() / 3 + 1.9D, mask);
		return true;
	}


	private void func_202535_a(IChunk world, Function<BlockPos, Biome> biomeBlockPos, long randomSeed, int mainChunkX, int mainChunkZ, double randomBlockX, double randomBlockY, double randomBlockZ, float widthHeightBase, float xzNoise2, float xzCosNoise, int maxIteration, double heightMultiplier, BitSet mask) {
		Random random = new Random(randomSeed);

		float f = 1.0F;

		for (int i = 0; i < 256; ++i) {
			if (i == 0 || random.nextInt(3) == 0) {
				f = 1.0F + random.nextFloat() * random.nextFloat();
			}

			this.WALL_LEDGES[i] = f * f;
		}

		float f4 = 0.0F;
		float f1 = 0.0F;

		for (int j = 0; j < maxIteration; ++j) {
			double placementXZBound = 2D + MathHelper.sin(j * (float) Math.PI / maxIteration) * widthHeightBase;
			double placementYBound = placementXZBound * heightMultiplier;
			placementXZBound = placementXZBound * (random.nextFloat() * 0.15D + 0.65D); //thickness
			placementYBound = placementYBound * 0.8D;
			float f2 = MathHelper.cos(xzCosNoise); //multiply by 0.1f to make cylinders
			randomBlockX += MathHelper.cos(xzNoise2) * f2;
			randomBlockZ += MathHelper.sin(xzNoise2) * f2;
			xzCosNoise = xzCosNoise * 0.8F;
			xzCosNoise = xzCosNoise + f1 * 0.08F;
			xzNoise2 += f4 * 0.1F;
			f1 = f1 * 0.8F;
			f4 = f4 * 0.5F;
			f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 1.5F;
			f4 = f4 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 3.0F;
			if (random.nextInt(4) != 0) {
				if (!this.func_222702_a(mainChunkX, mainChunkZ, randomBlockX, randomBlockZ, j, maxIteration, widthHeightBase)) {
					return;
				}

				this.carveAtTarget(world, biomeBlockPos, random, mainChunkX, mainChunkZ, randomBlockX, randomBlockY, randomBlockZ, placementXZBound, placementYBound, mask);
			}
		}

	}

	protected void carveAtTarget(IChunk world, Function<BlockPos, Biome> biomeBlockPos, Random random, int mainChunkX, int mainChunkZ, double xRange, double yRange, double zRange, double placementXZBound, double placementYBound, BitSet mask) {
		double d0 = mainChunkX * 16 + 8;
		double d1 = mainChunkZ * 16 + 8;
		if (!(xRange < d0 - 16.0D - placementXZBound * 2.0D) && !(zRange < d1 - 16.0D - placementXZBound * 2.0D) && !(xRange > d0 + 16.0D + placementXZBound * 2.0D) && !(zRange > d1 + 16.0D + placementXZBound * 2.0D)) {
			int i = Math.max(MathHelper.floor(xRange - placementXZBound) - mainChunkX * 16 - 1, 0);
			int j = Math.min(MathHelper.floor(xRange + placementXZBound) - mainChunkX * 16 + 1, 16);
			int k = Math.max(MathHelper.floor(yRange - placementYBound) - 1, 9);
			int l = Math.min(MathHelper.floor(yRange + placementYBound) + 1, this.maxHeight);
			int i1 = Math.max(MathHelper.floor(zRange - placementXZBound) - mainChunkZ * 16 - 1, 0);
			int j1 = Math.min(MathHelper.floor(zRange + placementXZBound) - mainChunkZ * 16 + 1, 16);
			if (i <= j && k <= l && i1 <= j1) {
				BlockState secondaryFloorBlockstate;
				BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
				BlockPos.Mutable blockpos$Mutableup = new BlockPos.Mutable();
				BlockPos.Mutable blockpos$Mutabledown = new BlockPos.Mutable();
				RegistryKey<Biome> biome;

				for (int k1 = i; k1 < j; ++k1) {
					int x = k1 + mainChunkX * 16;
					double xSquaringModified = (x + 0.5D - xRange) / placementXZBound;

					for (int i2 = i1; i2 < j1; ++i2) {
						int z = i2 + mainChunkZ * 16;
						double zSquaringModified = (z + 0.5D - zRange) / placementXZBound;
						double xzSquaredModified = xSquaringModified * xSquaringModified + zSquaringModified * zSquaringModified;

						if (xzSquaredModified < 1.0D) {

							blockpos$Mutable.setPos(x, 60, z);
							biome = biomeRegistry != null ? biomeRegistry.getOptionalKey(biomeBlockPos.apply(blockpos$Mutable)).orElse(null) : null;
							fillerBlock = fillerBiomeMap.get(biome);
							if (fillerBlock == null) {
								fillerBlock = STONE;
							}
							secondaryFloorBlockstate = lavaFloorBiomeMap.get(biome);

							for (int y = l; y > k; --y) {
								double d4 = (y - 1 + 0.5D - yRange) / placementYBound;
								if (xzSquaredModified * this.WALL_LEDGES[y - 1] + d4 * d4 / 6.0D < 1.0D) {
									blockpos$Mutable.setPos(x, y, z);

									BlockState currentBlockstate = world.getBlockState(blockpos$Mutable);
									blockpos$Mutableup.setPos(blockpos$Mutable).move(Direction.UP);
									blockpos$Mutabledown.setPos(blockpos$Mutable).move(Direction.DOWN);
									BlockState aboveBlockstate = world.getBlockState(blockpos$Mutableup);

									if (y > 61 && !aboveBlockstate.getFluidState().isEmpty()) {
										//Creates the messy but cool plateau of stone on the ocean floor 
										//above this ravine to help players locate ravines when exploring
										//ocean biomes. Also helps to break up the blandness of ocean
										//floors.

										world.setBlockState(blockpos$Mutable, fillerBlock, false);
										world.setBlockState(blockpos$Mutableup, fillerBlock, false);
										world.setBlockState(blockpos$Mutabledown, fillerBlock, false);
									}
									else if (this.canCarveBlock(currentBlockstate, aboveBlockstate) || canReplaceMap.containsKey(currentBlockstate)) {
										if (y < 11) {
											currentBlockstate = LAVA;
											if (secondaryFloorBlockstate != null) {
												if (secondaryFloorBlockstate == OBSIDIAN) {
													currentBlockstate = MAGMA;
												}

												if (random.nextFloat() > 0.35F) {
													if (y == 10) {
														currentBlockstate = secondaryFloorBlockstate;
													}
													else if (y == 9 && random.nextFloat() < 0.35F)
													{
														currentBlockstate = secondaryFloorBlockstate;
													}
												}
											}

											world.setBlockState(blockpos$Mutable, currentBlockstate, false);
										}
										else {
											//carves the ravine
											world.setBlockState(blockpos$Mutable, CAVE_AIR, false);
										}

										mask.set(blockpos$Mutable.getX() | blockpos$Mutable.getZ() << 4 | blockpos$Mutable.getY() << 8);
									}
								}
							}
						}
					}
				}

			}
		}
	}


	/**
	 * MC doesn't seem to do anything with the returned value in the end. Strange. I wonder why.
	 */
	@Override
	protected boolean func_222708_a(double p_222708_1_, double p_222708_3_, double p_222708_5_, int p_222708_7_) {
		return true;
		//return (p_222708_1_ * p_222708_1_ + p_222708_5_ * p_222708_5_) * (double)this.field_202536_i[p_222708_7_ - 1] + p_222708_3_ * p_222708_3_ / 6.0D >= 1.0D;
	}
}

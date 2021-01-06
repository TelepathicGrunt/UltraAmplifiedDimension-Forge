package com.telepathicgrunt.ultraamplifieddimension.world.carver;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.mixin.BiomeContainerAccessor;
import com.telepathicgrunt.ultraamplifieddimension.utils.GeneralUtils;
import com.telepathicgrunt.ultraamplifieddimension.world.carver.configs.RavineConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.WorldCarver;

import java.util.BitSet;
import java.util.HashSet;
import java.util.Random;
import java.util.function.Function;


public class SuperLongRavineCarver extends WorldCarver<RavineConfig>
{
	private final float[] WALL_LEDGES = new float[1024];
	private SimpleRegistry<Biome> biomeRegistry;


	public SuperLongRavineCarver(Codec<RavineConfig> codec)
	{
		super(codec, 255);
		this.carvableBlocks = new HashSet<>(this.carvableBlocks);
		this.carvableBlocks.add(Blocks.NETHERRACK);
		this.carvableBlocks.add(Blocks.ICE);
		this.carvableBlocks.add(Blocks.SNOW_BLOCK);
		this.carvableBlocks.add(Blocks.END_STONE);
		this.carvableBlocks.add(Blocks.LAVA);
	}

	@Override
	public boolean shouldCarve(Random p_212246_2_, int chunkX, int chunkZ, RavineConfig config) {
		return p_212246_2_.nextFloat() <= (UltraAmplifiedDimension.UAFeaturesConfig.ravineSpawnrate.get()) / 850f;
	}


	@Override
	public boolean carveRegion(IChunk region, Function<BlockPos, Biome> biomeBlockPos, Random random, int seaLevel, int chunkX, int chunkZ, int originalX, int originalZ, BitSet mask, RavineConfig config) {
		IObjectIntIterable<Biome> reg = region.getBiomes() != null ? ((BiomeContainerAccessor)region.getBiomes()).getBiomeRegistry() : null;
		if(reg instanceof SimpleRegistry && reg != biomeRegistry){
			biomeRegistry = (SimpleRegistry<Biome>)((BiomeContainerAccessor)region.getBiomes()).getBiomeRegistry();
		}

		int i = (this.func_222704_c() * 3 - 1) * 16;
		double xpos = chunkX * 16 + random.nextInt(16);
		double height = config.heightPlacement.func_242259_a(random);
		double zpos = chunkZ * 16 + random.nextInt(16);
		float xzNoise2 = random.nextFloat() * ((float) Math.PI * 2F);
		float xzCosNoise = (random.nextFloat() - 0.5F) / 8.0F;
		float widthHeightBase = (random.nextFloat() * 1.3F + random.nextFloat()) * 1.3F;
		int maxIteration = i + random.nextInt(i / 4); //length of ravine. probably in chunks
		this.func_202535_a(region, biomeBlockPos, random.nextLong(), random, originalX, originalZ, xpos, height, zpos, widthHeightBase, xzNoise2, xzCosNoise, 0, maxIteration, config.tallness.func_242259_a(random) / 10D, mask, config);
		return true;
	}


	private void func_202535_a(IChunk world, Function<BlockPos, Biome> biomeBlockPos, long randomSeed, Random random, int mainChunkX, int mainChunkZ, double randomBlockX, double randomBlockY, double randomBlockZ, float widthHeightBase, float xzNoise2, float xzCosNoise, int startIteration, int maxIteration, double heightMultiplier, BitSet mask, RavineConfig config) {
		float f = 1.0F;

		for (int i = 0; i < config.cutoffHeight; ++i) {
			if (i == 0 || random.nextInt(3) == 0) {
				f = 1.0F + random.nextFloat() * random.nextFloat();
			}

			this.WALL_LEDGES[i] = f * f;
		}

		float f4 = 0.0F;
		float f1 = 0.0F;

		for (int j = startIteration; j < maxIteration; ++j) {
			double placementXZBound = 6D + MathHelper.sin(j * (float) Math.PI / maxIteration) * widthHeightBase;
			double placementYBound = placementXZBound * heightMultiplier;
			placementXZBound = placementXZBound * (random.nextFloat() * 0.10D + 0.4D); //thickness
			placementYBound = placementYBound * 0.8D;
			float f2 = MathHelper.cos(xzCosNoise); //multiply by 0.1f to make cylinders
			randomBlockX += MathHelper.cos(xzNoise2) * f2;
			randomBlockZ += MathHelper.sin(xzNoise2) * f2;
			xzCosNoise = xzCosNoise * 0.5F;
			xzCosNoise = xzCosNoise + f1 * 0.03F;
			xzNoise2 += f4 * 0.1F;
			f1 = f1 * 0.8F;
			f4 = f4 * 0.5F;
			f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 1.5F;
			f4 = f4 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 3.0F;
			if (random.nextInt(4) != 0) {
				if (!this.func_222702_a(mainChunkX, mainChunkZ, randomBlockX, randomBlockZ, j, maxIteration, widthHeightBase)) {
					return;
				}

				this.carveAtTarget(world, biomeBlockPos, random, mainChunkX, mainChunkZ, randomBlockX, randomBlockY, randomBlockZ, placementXZBound, placementYBound, mask, config);
			}
		}

	}


	protected void carveAtTarget(IChunk world, Function<BlockPos, Biome> biomeBlockPos, Random random, int mainChunkX, int mainChunkZ, double xRange, double yRange, double zRange, double placementXZBound, double placementYBound, BitSet mask, RavineConfig config) {
		double d0 = mainChunkX * 16 + 8;
		double d1 = mainChunkZ * 16 + 8;
		if (!(xRange < d0 - 16.0D - placementXZBound * 2.0D) && !(zRange < d1 - 16.0D - placementXZBound * 2.0D) && !(xRange > d0 + 16.0D + placementXZBound * 2.0D) && !(zRange > d1 + 16.0D + placementXZBound * 2.0D)) {
			int i = Math.max(MathHelper.floor(xRange - placementXZBound) - mainChunkX * 16 - 1, 0);
			int j = Math.min(MathHelper.floor(xRange + placementXZBound) - mainChunkX * 16 + 1, 16);
			int minY = Math.max(MathHelper.floor(yRange - placementYBound) - 1, 6);
			int maxY = Math.min(MathHelper.floor(yRange + placementYBound) + 1, config.cutoffHeight);
			int i1 = Math.max(MathHelper.floor(zRange - placementXZBound) - mainChunkZ * 16 - 1, 0);
			int j1 = Math.min(MathHelper.floor(zRange + placementXZBound) - mainChunkZ * 16 + 1, 16);
			if (i <= j && minY <= maxY && i1 <= j1) {
				BlockState fillerBlock;
				BlockState secondaryFloorBlockstate;
				BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
				BlockPos.Mutable blockpos$Mutableup = new BlockPos.Mutable();
				BlockPos.Mutable blockpos$Mutabledown = new BlockPos.Mutable();

				for (int xInChunk = i; xInChunk < j; ++xInChunk) {
					int x = xInChunk + mainChunkX * 16;
					double xSquaringModified = (x + 0.5D - xRange) / placementXZBound;

					for (int zInChunk = i1; zInChunk < j1; ++zInChunk) {
						int z = zInChunk + mainChunkZ * 16;
						double zSquaringModified = (z + 0.5D - zRange) / placementXZBound;
						double xzSquaredModified = xSquaringModified * xSquaringModified + zSquaringModified * zSquaringModified;

						if (xzSquaredModified < 1.0D) {

							blockpos$Mutable.setPos(x, 60, z);

							if(maxY >= 60 || minY < 11){
								Biome biome = biomeBlockPos.apply(blockpos$Mutable);
								ResourceLocation biomeID = biomeRegistry != null ? biomeRegistry.getKey(biome) : null;
								String biomeIDString = biomeID == null ? "" : biomeID.toString();

								fillerBlock = GeneralUtils.carverFillerBlock(biomeIDString, biome);
								secondaryFloorBlockstate = GeneralUtils.carverLavaReplacement(biomeIDString, biome);
							}
							else{
								// Set defaults as this will not be used as ravine is not high or low enough
								fillerBlock = Blocks.STONE.getDefaultState();
								secondaryFloorBlockstate = Blocks.LAVA.getDefaultState();
							}

							for (int y = maxY; y > minY; --y) {

								double d4 = (y - 1 + 0.5D - yRange) / placementYBound;

								if (xzSquaredModified * this.WALL_LEDGES[y - 1] + d4 * d4 / 6.0D < 1.0D) {

									blockpos$Mutable.setPos(x, y, z);

									BlockState currentBlockstate = world.getBlockState(blockpos$Mutable);
									blockpos$Mutableup.setPos(blockpos$Mutable).move(Direction.UP);
									blockpos$Mutabledown.setPos(blockpos$Mutable).move(Direction.DOWN);
									BlockState aboveBlockstate = world.getBlockState(blockpos$Mutableup);

									if (!mask.get(xInChunk | zInChunk << 4 | y << 8) &&
										(this.canCarveBlock(currentBlockstate, aboveBlockstate)))
									{

										if (y >= 60 && !aboveBlockstate.getFluidState().isEmpty()) {
											//Creates the messy but cool plateau of stone on the ocean floor
											//above this ravine to help players locate ravines when exploring
											//ocean biomes. Also helps to break up the blandness of ocean
											//floors.

											world.setBlockState(blockpos$Mutable, fillerBlock, false);
											world.setBlockState(blockpos$Mutableup, fillerBlock, false);
											world.setBlockState(blockpos$Mutabledown, fillerBlock, false);
										}
										else if (y < 11) {
											currentBlockstate = Blocks.LAVA.getDefaultState();
											if (secondaryFloorBlockstate != null) {
												if (secondaryFloorBlockstate.isIn(Blocks.OBSIDIAN)) {
													currentBlockstate = Blocks.MAGMA_BLOCK.getDefaultState();
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

										mask.set(xInChunk | zInChunk << 4 | y << 8);
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
	}
}

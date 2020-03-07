package net.telepathicgrunt.ultraamplified.world.feature.carver;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeInit;


public class SuperLongRavineCarver extends WorldCarver<ProbabilityConfig>
{
	public SuperLongRavineCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> p_i49921_1_, int p_i49921_2_)
	{
		super(p_i49921_1_, p_i49921_2_);
	}

	private final float[] field_202536_i = new float[1024];
	protected static final BlockState STONE = Blocks.STONE.getDefaultState();
	protected static final BlockState WATER = Blocks.WATER.getDefaultState();
	protected static final BlockState LAVA = Blocks.LAVA.getDefaultState();
	protected static final BlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
	protected static final BlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();
	protected BlockState fillerBlock = Blocks.STONE.getDefaultState();

	private static Map<BlockState, BlockState> canReplaceMap;
	static
	{
		Map<BlockState, BlockState> result = new HashMap<BlockState, BlockState>();

		result.put(Blocks.NETHERRACK.getDefaultState(), Blocks.NETHERRACK.getDefaultState());
		result.put(Blocks.ICE.getDefaultState(), Blocks.ICE.getDefaultState());
		result.put(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.ICE.getDefaultState());
		result.put(Blocks.END_STONE.getDefaultState(), Blocks.END_STONE.getDefaultState());

		canReplaceMap = result;
	}

	private static Map<Biome, BlockState> fillerBiomeMap;


	/**
	 * Have to make this map in UltraAmplified setup method since the biomes needs to be initialized first
	 */
	public static void setFillerMap()
	{
		if (fillerBiomeMap == null)
		{
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

	private static Map<Biome, BlockState> lavaFloorBiomeMap;


	/**
	 * Have to make this map in UltraAmplified setup method since the biomes needs to be initialized first
	 */
	public static void setLavaFloorMap()
	{
		if (lavaFloorBiomeMap == null)
		{
			lavaFloorBiomeMap = new HashMap<Biome, BlockState>();

			lavaFloorBiomeMap.put(BiomeInit.ICED_TERRAIN, Blocks.OBSIDIAN.getDefaultState());
			lavaFloorBiomeMap.put(BiomeInit.ICE_SPIKES, Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(BiomeInit.RELIC_SNOWY_TAIGA, Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(BiomeInit.SNOWY_ROCKY_TAIGA, Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(BiomeInit.SNOWY_TAIGA, Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(BiomeInit.SNOWY_TUNDRA, Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(BiomeInit.FROZEN_DESERT, Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(BiomeInit.DEEP_FROZEN_OCEAN, Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(BiomeInit.FROZEN_OCEAN, Blocks.MAGMA_BLOCK.getDefaultState());
		}
	}


	@Override
	public boolean shouldCarve(Random p_212246_2_, int chunkX, int chunkZ, ProbabilityConfig config)
	{
		return p_212246_2_.nextFloat() <= (ConfigUA.ravineSpawnrate) / 850f;
	}


	@Override
	public boolean carve(IChunk region, Function<BlockPos, Biome> biomeBlockPos, Random random, int seaLevel, int chunkX, int chunkZ, int originalX, int originalZ, BitSet mask, ProbabilityConfig config)
	{

		int i = (this.func_222704_c() * 3 - 1) * 16;
		double xpos = chunkX * 16 + random.nextInt(16);
		double height = random.nextInt(random.nextInt(3) + 3) + 15;
		double zpos = chunkZ * 16 + random.nextInt(16);
		float xzNoise2 = random.nextFloat() * ((float) Math.PI * 2F);
		float xzCosNoise = (random.nextFloat() - 0.5F) / 8.0F;
		float widthHeightBase = (random.nextFloat() * 1.3F + random.nextFloat()) * 1.3F;
		int maxIteration = i + random.nextInt(i / 4); //length of ravine. probably in chunks
		this.func_202535_a(region, biomeBlockPos, random.nextLong(), random, originalX, originalZ, xpos, height, zpos, widthHeightBase, xzNoise2, xzCosNoise, 0, maxIteration, random.nextDouble() / 2 + 0.9D, mask);
		return true;
	}


	private void func_202535_a(IChunk world, Function<BlockPos, Biome> biomeBlockPos, long randomSeed, Random random, int mainChunkX, int mainChunkZ, double randomBlockX, double randomBlockY, double randomBlockZ, float widthHeightBase, float xzNoise2, float xzCosNoise, int startIteration, int maxIteration, double heightMultiplier, BitSet mask)
	{
		float f = 1.0F;

		for (int i = 0; i < 256; ++i)
		{
			if (i == 0 || random.nextInt(3) == 0)
			{
				f = 1.0F + random.nextFloat() * random.nextFloat();
			}

			this.field_202536_i[i] = f * f;
		}

		float f4 = 0.0F;
		float f1 = 0.0F;

		for (int j = startIteration; j < maxIteration; ++j)
		{
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
			if (random.nextInt(4) != 0)
			{
				if (!this.func_222702_a(mainChunkX, mainChunkZ, randomBlockX, randomBlockZ, j, maxIteration, widthHeightBase))
				{
					return;
				}

				this.carveAtTarget(world, biomeBlockPos, random, mainChunkX, mainChunkZ, randomBlockX, randomBlockY, randomBlockZ, placementXZBound, placementYBound, mask);
			}
		}

	}


	protected boolean carveAtTarget(IChunk world, Function<BlockPos, Biome> biomeBlockPos, Random random, int mainChunkX, int mainChunkZ, double xRange, double yRange, double zRange, double placementXZBound, double placementYBound, BitSet mask)
	{
		double d0 = mainChunkX * 16 + 8;
		double d1 = mainChunkZ * 16 + 8;
		if (!(xRange < d0 - 16.0D - placementXZBound * 2.0D) && !(zRange < d1 - 16.0D - placementXZBound * 2.0D) && !(xRange > d0 + 16.0D + placementXZBound * 2.0D) && !(zRange > d1 + 16.0D + placementXZBound * 2.0D))
		{
			int i = Math.max(MathHelper.floor(xRange - placementXZBound) - mainChunkX * 16 - 1, 0);
			int j = Math.min(MathHelper.floor(xRange + placementXZBound) - mainChunkX * 16 + 1, 16);
			int k = Math.max(MathHelper.floor(yRange - placementYBound) - 1, 6);
			int l = Math.min(MathHelper.floor(yRange + placementYBound) + 1, this.maxHeight);
			int i1 = Math.max(MathHelper.floor(zRange - placementXZBound) - mainChunkZ * 16 - 1, 0);
			int j1 = Math.min(MathHelper.floor(zRange + placementXZBound) - mainChunkZ * 16 + 1, 16);
			if (i <= j && k <= l && i1 <= j1)
			{
				boolean flag = false;
				BlockState secondaryFloorBlockstate;
				BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
				BlockPos.Mutable blockpos$Mutableup = new BlockPos.Mutable();
				BlockPos.Mutable blockpos$Mutabledown = new BlockPos.Mutable();
				Biome biome;

				for (int k1 = i; k1 < j; ++k1)
				{
					int x = k1 + mainChunkX * 16;
					double xSquaringModified = (x + 0.5D - xRange) / placementXZBound;

					for (int i2 = i1; i2 < j1; ++i2)
					{
						int z = i2 + mainChunkZ * 16;
						double zSquaringModified = (z + 0.5D - zRange) / placementXZBound;
						double xzSquaredModified = xSquaringModified * xSquaringModified + zSquaringModified * zSquaringModified;

						if (xzSquaredModified < 1.0D)
						{

							blockpos$Mutable.setPos(x, 60, z);
							biome = biomeBlockPos.apply(blockpos$Mutable);
							fillerBlock = fillerBiomeMap.get(biome);
							if (fillerBlock == null)
							{
								fillerBlock = STONE;
							}
							secondaryFloorBlockstate = lavaFloorBiomeMap.get(biome);

							for (int y = l; y > k; --y)
							{

								double d4 = (y - 1 + 0.5D - yRange) / placementYBound;

								if (xzSquaredModified * this.field_202536_i[y - 1] + d4 * d4 / 6.0D < 1.0D)
								{

									blockpos$Mutable.setPos(x, y, z);

									BlockState currentBlockstate = world.getBlockState(blockpos$Mutable);
									blockpos$Mutableup.setPos(blockpos$Mutable).move(Direction.UP);
									blockpos$Mutabledown.setPos(blockpos$Mutable).move(Direction.DOWN);
									BlockState aboveBlockstate = world.getBlockState(blockpos$Mutableup);

									if (this.canCarveBlock(currentBlockstate, aboveBlockstate) || canReplaceMap.containsKey(currentBlockstate))
									{

										if (y < 11)
										{
											currentBlockstate = LAVA;
											if (secondaryFloorBlockstate != null)
											{
												if (secondaryFloorBlockstate == OBSIDIAN)
												{
													currentBlockstate = MAGMA;
												}

												if (random.nextFloat() > 0.35F)
												{
													if (y == 10)
													{
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
										else
										{
											//carves the ravine
											world.setBlockState(blockpos$Mutable, AIR, false);
										}

										flag = true;
									}
								}
							}
						}
					}
				}

				return flag;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}


	/**
	 * MC doesn't seem to do anything with the returned value in the end. Strange. I wonder why.
	 */
	@Override
	protected boolean func_222708_a(double p_222708_1_, double p_222708_3_, double p_222708_5_, int p_222708_7_)
	{
		return true;
		//return (p_222708_1_ * p_222708_1_ + p_222708_5_ * p_222708_5_) * (double)this.field_202536_i[p_222708_7_ - 1] + p_222708_3_ * p_222708_3_ / 6.0D >= 1.0D;
	}
}

package com.telepathicgrunt.ultraamplifieddimension.world.carver;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.mixin.BiomeContainerAccessor;
import com.telepathicgrunt.ultraamplifieddimension.utils.OpenSimplexNoise;
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
import net.minecraft.world.gen.feature.ProbabilityConfig;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import static com.telepathicgrunt.ultraamplifieddimension.utils.GeneralUtils.biomeIDString;


public class CaveCavityCarver extends WorldCarver<ProbabilityConfig>
{

	private final float[] ledgeWidthArrayYIndex = new float[1024];
	protected static long NOISE_SEED;
	protected static OpenSimplexNoise NOISE_GEN;

	// Blocks that we can carve out.
	private static final Map<BlockState, BlockState> CAN_REPLACE_MAP;
	static
	{
		Map<BlockState, BlockState> result = new HashMap<>();

		result.put(Blocks.NETHERRACK.getDefaultState(), Blocks.NETHERRACK.getDefaultState());
		result.put(Blocks.ICE.getDefaultState(), Blocks.ICE.getDefaultState());
		result.put(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.ICE.getDefaultState());
		result.put(Blocks.END_STONE.getDefaultState(), Blocks.END_STONE.getDefaultState());
		result.put(Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState());

		CAN_REPLACE_MAP = result;
	}

	private static final Map<String, BlockState> FILLER_BIOME_MAP;
	static {
		FILLER_BIOME_MAP = new HashMap<>();

		FILLER_BIOME_MAP.put(biomeIDString("nether_wasteland"), Blocks.NETHERRACK.getDefaultState());
		FILLER_BIOME_MAP.put(biomeIDString("iced_terrain"), Blocks.ICE.getDefaultState());
		FILLER_BIOME_MAP.put(biomeIDString("ice_spikes"), Blocks.ICE.getDefaultState());
		FILLER_BIOME_MAP.put(biomeIDString("deep_frozen_ocean"), Blocks.ICE.getDefaultState());
		FILLER_BIOME_MAP.put(biomeIDString("frozen_ocean"), Blocks.ICE.getDefaultState());
		FILLER_BIOME_MAP.put(biomeIDString("barren_end_fields"), Blocks.END_STONE.getDefaultState());
		FILLER_BIOME_MAP.put(biomeIDString("end_fields"), Blocks.END_STONE.getDefaultState());
	}

	private static final Map<String, BlockState> LAVA_FLOOR_BIOME_MAP;
	static {
		LAVA_FLOOR_BIOME_MAP = new HashMap<>();

		LAVA_FLOOR_BIOME_MAP.put(biomeIDString("iced_terrain"), Blocks.OBSIDIAN.getDefaultState());
		LAVA_FLOOR_BIOME_MAP.put(biomeIDString("ice_spikes"), Blocks.MAGMA_BLOCK.getDefaultState());
		LAVA_FLOOR_BIOME_MAP.put(biomeIDString("relic_snowy_taiga"), Blocks.MAGMA_BLOCK.getDefaultState());
		LAVA_FLOOR_BIOME_MAP.put(biomeIDString("snowy_rocky_taiga"), Blocks.MAGMA_BLOCK.getDefaultState());
		LAVA_FLOOR_BIOME_MAP.put(biomeIDString("snowy_taiga"), Blocks.MAGMA_BLOCK.getDefaultState());
		LAVA_FLOOR_BIOME_MAP.put(biomeIDString("snowy_tundra"), Blocks.MAGMA_BLOCK.getDefaultState());
		LAVA_FLOOR_BIOME_MAP.put(biomeIDString("frozen_desert"), Blocks.MAGMA_BLOCK.getDefaultState());
		LAVA_FLOOR_BIOME_MAP.put(biomeIDString("deep_frozen_ocean"), Blocks.MAGMA_BLOCK.getDefaultState());
		LAVA_FLOOR_BIOME_MAP.put(biomeIDString("frozen_ocean"), Blocks.MAGMA_BLOCK.getDefaultState());
	}

	private SimpleRegistry<Biome> biomeRegistry;

	/**
	 * Sets the internal seed for this carver after we get the world seed. (Based on Nether's surface builder code)
	 */
	public static void setSeed(long seed) {
		if (NOISE_SEED != seed || NOISE_GEN == null) {
			NOISE_GEN = new OpenSimplexNoise(seed);
			NOISE_SEED = seed;
		}
	}


	public CaveCavityCarver(Codec<ProbabilityConfig> codec, int maximumHeight) {
		super(codec, maximumHeight);
	}

	/**
	 * Checks whether the entire cave can spawn or not. (Not the individual parts)
	 */
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

		int maxIterations = (this.func_222704_c() * 2 - 1) * 16;
		double xpos = chunkX * 16 + random.nextInt(16);
		double height = random.nextInt(random.nextInt(2) + 1) + 34;
		double zpos = chunkZ * 16 + random.nextInt(16);
		float xzNoise2 = random.nextFloat() * ((float) Math.PI * 1F);
		float xzCosNoise = (random.nextFloat() - 0.5F) / 16.0F;
		float widthHeightBase = (random.nextFloat() + random.nextFloat()) / 16; // width And Height Modifier
		this.carveCavity(region, biomeBlockPos, random, seaLevel, originalX, originalZ, xpos, height, zpos, widthHeightBase, xzNoise2, xzCosNoise, 0, maxIterations, random.nextDouble() + 20D, mask);
		return true;
	}


	private void carveCavity(IChunk world, Function<BlockPos, Biome> biomeBlockPos, Random random, int seaLevel, int mainChunkX, int mainChunkZ, double randomBlockX, double randomBlockY, double randomBlockZ, float widthHeightBase, float xzNoise2, float xzCosNoise, int startIteration, int maxIteration, double heightMultiplier, BitSet mask) {
		float ledgeWidth = 1.0F;

		// CONTROLS THE LEDGES' WIDTH! FINALLY FOUND WHAT THIS JUNK DOES
		for (int currentHeight = 0; currentHeight <= this.maxHeight; ++currentHeight) {

			//attempt at creating dome ceilings
			if (currentHeight > 44 && currentHeight < 60) {
				ledgeWidth = 1.0F + random.nextFloat() * 0.3f;
				ledgeWidth = (float) (ledgeWidth + Math.max(0, Math.pow((currentHeight - 44) * 0.15F, 2)));
			}

			//normal ledges on walls
			else {
				if (currentHeight == 0 || random.nextInt(3) == 0)
				{
					ledgeWidth = 1.0F + random.nextFloat() * 0.5F;
				}
			}

			this.ledgeWidthArrayYIndex[currentHeight] = ledgeWidth;
		}

		double placementXZBound = 2D + MathHelper.sin(1 * (float) Math.PI / maxIteration) * widthHeightBase;
		double placementYBound = placementXZBound * heightMultiplier;
		placementXZBound = placementXZBound * 32D; // thickness of the "room" itself
		placementYBound = placementYBound * 2.2D;

		this.carveAtTarget(world, biomeBlockPos, random, mainChunkX, mainChunkZ, randomBlockX, randomBlockY, randomBlockZ, placementXZBound, placementYBound, mask);
	}


	protected void carveAtTarget(IChunk world, Function<BlockPos, Biome> biomeBlockPos, Random random, int mainChunkX, int mainChunkZ, double xRange, double yRange, double zRange, double placementXZBound, double placementYBound, BitSet mask) {

		double xPos = mainChunkX * 16 + 8;
		double zPos = mainChunkZ * 16 + 8;
		double multipliedXZBound = placementXZBound * 2.0D;

		if (!(xRange < xPos - 16.0D - multipliedXZBound) && !(zRange < zPos - 16.0D - multipliedXZBound) && !(xRange > xPos + 16.0D + multipliedXZBound) && !(zRange > zPos + 16.0D + multipliedXZBound)) {

			int xMin = Math.max(MathHelper.floor(xRange - placementXZBound) - mainChunkX * 16 - 1, 0);
			int xMax = Math.min(MathHelper.floor(xRange + placementXZBound) - mainChunkX * 16 + 1, 16);
			int yMin = Math.max(MathHelper.floor(yRange - placementYBound) - 1, 5);
			int yMax = Math.min(MathHelper.floor(yRange + placementYBound) + 1, this.maxHeight);
			int zMin = Math.max(MathHelper.floor(zRange - placementXZBound) - mainChunkZ * 16 - 1, 0);
			int zMax = Math.min(MathHelper.floor(zRange + placementXZBound) - mainChunkZ * 16 + 1, 16);
			if (xMin <= xMax && yMin <= yMax && zMin <= zMax) {
				BlockState secondaryFloorBlockstate;
				BlockState currentBlockstate;
				BlockState aboveBlockstate;
				BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
				BlockPos.Mutable blockpos$Mutableup = new BlockPos.Mutable();
				BlockPos.Mutable blockpos$Mutabledown = new BlockPos.Mutable();
				double stalagmiteDouble = 0;

				for (int xInChunk = xMin; xInChunk < xMax; ++xInChunk) {
					int x = xInChunk + mainChunkX * 16;
					double xSquaringModified = (x + 0.5D - xRange) / placementXZBound;

					for (int zInChunk = zMin; zInChunk < zMax; ++zInChunk) {
						int z = zInChunk + mainChunkZ * 16;
						double zSquaringModified = (z + 0.5D - zRange) / placementXZBound;
						double xzSquaredModified = xSquaringModified * xSquaringModified + zSquaringModified * zSquaringModified;

						if (xzSquaredModified < 1.0D) {
							if (yMax < yMin) {
								continue;
							}

							blockpos$Mutable.setPos(x, 60, z);
							ResourceLocation biomeID = biomeRegistry != null ? biomeRegistry.getKey(biomeBlockPos.apply(blockpos$Mutable)) : null;
							String biomeIDString = biomeID == null ? "" : biomeID.toString();

							BlockState replacementBlock = FILLER_BIOME_MAP.get(biomeIDString);
							if (replacementBlock == null) {
								replacementBlock = Blocks.STONE.getDefaultState();
							}
							secondaryFloorBlockstate = LAVA_FLOOR_BIOME_MAP.get(biomeIDString);

							for (int y = yMax; y > yMin; y--) {

								// Skip already carved spaces
								if( y < 60 && mask.get(xInChunk | zInChunk << 4 | y << 8)){
									continue;
								}

								// sets a trial and error value that widens base of pillar and makes paths
								// through lava that look good
								double yPillarModifier = y;

								if(y > 30) {
									//increase multiplier on end to widen top of pillar
									yPillarModifier = (Math.pow((yPillarModifier - 30D) * 0.033333D, 2) * 30D - (y * 0.016666D)) * 18D;
								}
								else {
									//increase multiplier on end to widen bottom of pillar
									yPillarModifier = Math.pow((Math.pow(yPillarModifier - 30D, 2) * 0.033333D), 2) * 2.8D;
								}
								
								if (yPillarModifier <= 0) {
									// prevents divide by 0 or by negative numbers (decreasing negative would make
									// more terrain be carved instead of not carve)
									yPillarModifier = 0.00001D;
								}
								else if (y < 10) {
									// creates a deep lava pool that starts 2 blocks deep automatically at edges.
									yPillarModifier -= 50D;
								}

								
								//limits calling pillar and stalagmite perlin generators to reduce gen time
								if (y < 60) {
									// Creates pillars that are widen at bottom.
									//
									// simplex field creates the main body for pillar by stepping slowly through x
									// and z and extremely slowly through y.
									// Then subtracted modified target height to flatten bottom of pillar to
									// make a path through lava.
									// Next, adds a random value to add some noise to the pillar.
									// And lastly, sets the greater than value to be very low so most of the cave gets carved
									// out.
									//
									//Increase step in X and Z to make pillars less frequent but thicker
									boolean flagPillars = NOISE_GEN.eval(
																x * 0.045D + (x % 16) * 0.002D, 
																z * 0.045D + (z % 16) * 0.002D, 
																y * 0.015D) - (yPillarModifier * 0.001D) + 
														 (random.nextDouble() * 0.01D) 
														 > -0.32D;

									if (!flagPillars) {
										//skip position if we are in pillar space
										continue;
									}

									//limits calling stalagmite perlin generators to reduce gen time
									if (y > 30) {
										// Creates large stalagmites that cannot reach floor of cavern.
										//
										// simplex field creates the main stalagmite shape and placement by stepping
										// though x and z pretty fast and through y very slowly.
										// Then adds 500/y so that as the y value gets lower, the more area gets carved
										// which sets the limit on how far down the stalagmites can go.
										// Next, add a random value to add some noise to the pillar.
										// And lastly, sets the greater than value to be high so more stalagmites can be made
										// while the 500/y has already carved out the rest of the cave.
										//
										//Increase step in X and Z to decrease number of stalagmites and make them slightly thicker
										stalagmiteDouble = NOISE_GEN.eval(x * 0.25D, z * 0.25D, 0) * 15.0D + (500D / (y));

										//adds more tiny stalagmites to ceiling
										if (y > 48) {
											stalagmiteDouble -= (y - 53D) / 3D;
										}

										//decrease constant to make stalagmites smaller and thinner
										boolean flagStalagmites = stalagmiteDouble > 5.3D;

										if (!flagStalagmites) {
											//skip position if we are in stalagmite space
											continue;
										}
									}
								}

								double ySquaringModified = (y - 1 + 0.5D - yRange) / placementYBound;

								// Where the pillar flag and stalagmite flag both flagged this block to be
								// carved, begin carving.
								// Thus the pillar and stalagmite is what is left after carving.
								if (xzSquaredModified * this.ledgeWidthArrayYIndex[y - 1] + ySquaringModified * ySquaringModified / 6.0D + random.nextFloat() * 0.015f < 1.0D) {

									blockpos$Mutable.setPos(x, y, z);
									currentBlockstate = world.getBlockState(blockpos$Mutable);
									blockpos$Mutableup.setPos(blockpos$Mutable).move(Direction.UP);
									blockpos$Mutabledown.setPos(blockpos$Mutable).move(Direction.DOWN);
									aboveBlockstate = world.getBlockState(blockpos$Mutableup);

									if (y >= 60) {
										//Creates the messy but cool plateau of stone on the ocean floor 
										//above this cave to help players locate caves when exploring
										//ocean biomes. Also helps to break up the blandness of ocean
										//floors.

										if (!currentBlockstate.getFluidState().isEmpty()) {
											world.setBlockState(blockpos$Mutable, replacementBlock, false);
										}
										else if (!aboveBlockstate.getFluidState().isEmpty()) {
											world.setBlockState(blockpos$Mutable, replacementBlock, false);
											world.setBlockState(blockpos$Mutableup, replacementBlock, false);
											world.setBlockState(blockpos$Mutabledown, replacementBlock, false);
										}
									}
									else if (this.canCarveBlock(currentBlockstate, aboveBlockstate) || CAN_REPLACE_MAP.containsKey(currentBlockstate))
									{

										if (y < 11) {
											currentBlockstate = Blocks.LAVA.getDefaultState();
											if (secondaryFloorBlockstate != null) {
												if (secondaryFloorBlockstate.isIn(Blocks.OBSIDIAN)) {
													currentBlockstate = Blocks.MAGMA_BLOCK.getDefaultState();
												}

												if (stalagmiteDouble > 13.5D) {
													if (y == 10) {
														currentBlockstate = secondaryFloorBlockstate;
													}
													else if (y == 9 && random.nextBoolean()) {
														currentBlockstate = secondaryFloorBlockstate;
													}
												}
											}

											world.setBlockState(blockpos$Mutable, currentBlockstate, false);
										}
										else {
											//carves the cave
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
	protected boolean func_222708_a(double p_222708_1_, double p_222708_3_, double p_222708_5_, int p_222708_7_)
	{
		return true;
	}
}

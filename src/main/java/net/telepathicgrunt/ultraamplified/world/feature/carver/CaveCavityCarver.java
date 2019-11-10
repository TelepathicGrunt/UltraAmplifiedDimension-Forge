package net.telepathicgrunt.ultraamplified.world.feature.carver;

import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.biome.BiomeInit;

public class CaveCavityCarver extends WorldCarver<ProbabilityConfig> {

	private final float[] ledgeWidthArrayYIndex = new float[1024];
	protected long seed;
	protected OctavesNoiseGenerator noiseGen;
	protected static final BlockState STONE = Blocks.STONE.getDefaultState();
	protected static final BlockState LAVA = Blocks.LAVA.getDefaultState();
	protected static final BlockState WATER = Blocks.WATER.getDefaultState();
	protected static final BlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
	protected static final BlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();

	// Blocks that we can carve out.
	private static final Map<BlockState, BlockState> canReplaceMap = createMap();
	private static Map<BlockState, BlockState> createMap() {
		Map<BlockState, BlockState> result = new HashMap<BlockState, BlockState>();
		result.put(Blocks.NETHERRACK.getDefaultState(), Blocks.NETHERRACK.getDefaultState());
		result.put(Blocks.ICE.getDefaultState(), Blocks.ICE.getDefaultState());
		result.put(Blocks.SNOW_BLOCK.getDefaultState(), Blocks.ICE.getDefaultState());
		result.put(Blocks.END_STONE.getDefaultState(), Blocks.END_STONE.getDefaultState());
		result.put(Blocks.LAVA.getDefaultState(), Blocks.LAVA.getDefaultState());
		return Collections.unmodifiableMap(result);
	}
	
	// Used to keep track of what block to use to fill in certain air/liquids
	protected BlockState replacementBlock = Blocks.STONE.getDefaultState();
	
	// Associates what block to use when in which biome when setting the replacementBlock.
    private static Map<Biome, BlockState> fillerBiomeMap;
	
    
	public CaveCavityCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> probabilityConfig, int maximumHeight) {
		super(probabilityConfig, maximumHeight);
		
	}

	
	/**
	 * Sets the internal seed for this carver after we get the world seed. 
	 * (Based on Nether's surface builder code)
	 */
	public void setSeed(long seed) {
		if (this.noiseGen == null) {
			this.noiseGen = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 4);
		}

		this.seed = seed;
	}
	
	/**
	 * Have to make this map much later instead of in constructor since 
	 * the biomes needs to be initialized first and that's delayed a bit
	 */
	public void setFillerMap() {
		if (fillerBiomeMap == null) {
			fillerBiomeMap = new HashMap<Biome, BlockState>();

			fillerBiomeMap.put(BiomeInit.NETHER, Blocks.NETHERRACK.getDefaultState()); 
			fillerBiomeMap.put(BiomeInit.ICE_MOUNTAIN, Blocks.ICE.getDefaultState()); 
			fillerBiomeMap.put(BiomeInit.ICE_SPIKES, Blocks.ICE.getDefaultState()); 
			fillerBiomeMap.put(BiomeInit.DEEP_FROZEN_OCEAN, Blocks.ICE.getDefaultState()); 
			fillerBiomeMap.put(BiomeInit.FROZEN_OCEAN, Blocks.ICE.getDefaultState()); 
	        fillerBiomeMap.put(BiomeInit.BARREN_END_FIELD, Blocks.END_STONE.getDefaultState()); 
	        fillerBiomeMap.put(BiomeInit.END, Blocks.END_STONE.getDefaultState()); 
		}
	}

	/**
	 * Checks whether the entire cave can spawn or not. (Not the individual parts)
	 */
	public boolean shouldCarve(Random randomIn, int chunkX, int chunkZ, ProbabilityConfig config) {
		return randomIn.nextFloat() <= (float) (ConfigUA.caveCavitySpawnrate) / 1000f;
	}

	
	public boolean carve(IChunk region, Random random, int seaLevel, int chunkX, int chunkZ, int originalX,
			int originalZ, BitSet mask, ProbabilityConfig config) {

		int i = (this.func_222704_c() * 2 - 1) * 16;
		double xpos = (double) (chunkX * 16 + random.nextInt(16));
		double height = (double) (random.nextInt(random.nextInt(2) + 1) + 34);
		double zpos = (double) (chunkZ * 16 + random.nextInt(16));
		float xzNoise2 = random.nextFloat() * ((float) Math.PI * 1F);
		float xzCosNoise = (random.nextFloat() - 0.5F) / 16.0F;
		float widthHeightBase = (random.nextFloat() + random.nextFloat()) / 16; // width And Height Modifier
		this.func_222729_a(region, random.nextLong(), seaLevel, originalX, originalZ, xpos, height, zpos,
				widthHeightBase, xzNoise2, xzCosNoise, 0, i, random.nextDouble() + 20D, mask);
		return true;
	}

	private void func_222729_a(IChunk worldIn, long randomSeed, int seaLevel, int mainChunkX, int mainChunkZ,
			double randomBlockX, double randomBlockY, double randomBlockZ, float widthHeightBase, float xzNoise2,
			float xzCosNoise, int startIteration, int maxIteration, double heightMultiplier, BitSet mask) {
		
		
		setSeed(randomSeed);
		setFillerMap();
		Random random = new Random(randomSeed);
		float ledgeWidth = 1.0F;

		// CONTROLS THE LEDGES' WIDTH! FINALLY FOUND WHAT THIS JUNK DOES
		for (int currentHeight = 0; currentHeight < 256; ++currentHeight) {
			if (currentHeight == 0 || random.nextInt(3) == 0) {
				ledgeWidth = 1.0F + random.nextFloat() * 0.5F; 
			}

			this.ledgeWidthArrayYIndex[currentHeight] = ledgeWidth;
		}

		
		float f4 = 0.0F;
		float f1 = 0.0F;

		//creates "rooms" which are giant sections of the cave. (cave is made up of many rooms)
		for (int currentRoom = startIteration; currentRoom < maxIteration; ++currentRoom) 
		{
			double placementXZBound = 2D
					+ (double) (MathHelper.sin((float) currentRoom * (float) Math.PI / (float) maxIteration)
							* widthHeightBase);
			double placementYBound = placementXZBound * heightMultiplier;
			placementXZBound = placementXZBound * 23D; // thickness of the "room" itself
			placementYBound = placementYBound * 2.2D;
			float f2 = MathHelper.cos(xzCosNoise);
			randomBlockX += (double) (MathHelper.cos(xzNoise2) * f2);
			randomBlockZ += (double) (MathHelper.sin(xzNoise2) * f2);
			xzCosNoise = xzCosNoise * 0.5F;
			xzCosNoise = xzCosNoise + f1 * 0.04F;
			xzNoise2 += f4 * 0.05F;
			f1 = f1 * 0.8F;
			f4 = f4 * 0.5F;
			f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 1.5F;
			f4 = f4 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 3.0F;
			
			//No idea what this is for yet
			if (!this.func_222702_a(mainChunkX, mainChunkZ, randomBlockX, randomBlockZ, currentRoom, maxIteration,
					widthHeightBase)) {
				return;
			}

			this.carveAtTarget(worldIn, random, randomSeed, mainChunkX, mainChunkZ, randomBlockX, randomBlockY,
					randomBlockZ, placementXZBound, placementYBound, mask);

		}

	}

	protected boolean carveAtTarget(IChunk worldIn, Random random, long seed, int mainChunkX, int mainChunkZ,
			double xRange, double yRange, double zRange, double placementXZBound, double placementYBound, BitSet mask) {
		
		
		double xPos = (double) (mainChunkX * 16 + 8);
		double zPos = (double) (mainChunkZ * 16 + 8);
		BlockState currentBlockstate;
		BlockState aboveBlockstate;
		double targetedHeight = 14;
		double multiplier = 2.0D;

		if (!(xRange < xPos - 16.0D - placementXZBound * multiplier) 
			&& !(zRange < zPos - 16.0D - placementXZBound * multiplier)
			&& !(xRange > xPos + 16.0D + placementXZBound * multiplier)
			&& !(zRange > zPos + 16.0D + placementXZBound * multiplier)) {
			
			int xMin = Math.max(MathHelper.floor(xRange - placementXZBound) - mainChunkX * 16 - 1, 0);
			int xMax = Math.min(MathHelper.floor(xRange + placementXZBound) - mainChunkX * 16 + 1, 16);
			int yMin = Math.max(MathHelper.floor(yRange - placementYBound) - 1, 5);
			int yMax = Math.min(MathHelper.floor(yRange + placementYBound) + 1, this.maxHeight);
			int zMin = Math.max(MathHelper.floor(zRange - placementXZBound) - mainChunkZ * 16 - 1, 0);
			int zMax = Math.min(MathHelper.floor(zRange + placementXZBound) - mainChunkZ * 16 + 1, 16);
			if (xMin <= xMax && yMin <= yMax && zMin <= zMax) {
				boolean flag = false;
				BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
				BlockPos.MutableBlockPos blockpos$mutableblockposup = new BlockPos.MutableBlockPos();
				BlockPos.MutableBlockPos blockpos$mutableblockposdown = new BlockPos.MutableBlockPos();

				for (int smallX = xMin; smallX < xMax; ++smallX) {
					int x = smallX + mainChunkX * 16;
					double xModified = ((double) x + 0.5D - xRange) / placementXZBound;

					for (int smallZ = zMin; smallZ < zMax; ++smallZ) {
						int z = smallZ + mainChunkZ * 16;
						double zModified = ((double) z + 0.5D - zRange) / placementXZBound;
						if (xModified * xModified + zModified * zModified < 1.0D) {
							int yMaxSum = (int) (yMax - ((1 + random.nextFloat()) * random.nextFloat() * 20));

							if (yMaxSum < yMin) {
								continue;
							}

							blockpos$mutableblockpos.setPos(x, 60, z);
							replacementBlock = fillerBiomeMap.get(worldIn.getBiome(blockpos$mutableblockpos));
							if (replacementBlock == null) {
								replacementBlock = STONE;
							}

							for (int y = yMaxSum; y > yMin; y--) {
								double d4 = ((double) (y - 1) + 0.5D - yRange) / placementYBound;

								// sets a trial and error value that widens base of pillar and makes paths
								// through lava that look good
								double yModified = y - 6.4;

								if (y < 10) {
									// creates a deep lava pool that starts 2 blocks deep automatically at edges.
									yModified++;
								} else if (yModified <= 0) {
									// prevents divide by 0 or by negative numbers (decreasing negative would make
									// more terrain be carved instead of not carve)
									yModified = 0.00001D;
								}

								// Creates pillars that are widen at bottom.
								//
								// Perlin field creates the main body for pillar by stepping slowly through x
								// and z and extremely slowly through y.
								// Then subtracted modified target height to flatten bottom of pillar to
								// make a path through lava.
								// Next, adds a random value to add some noise to the pillar.
								// And lastly, sets the greater than value to be very low so most of the cave gets carved
								// out.
								boolean flagPillars = this.noiseGen.func_205563_a((double) x * 0.2D, (double) z * 0.2D,
										y * 0.035D) - (targetedHeight / yModified) + random.nextDouble() * 0.1D > -3.5D;

								// Creates large stalagmites that cannot reach floor of cavern.
								//
								// Perlin field creates the main stalagmite shape and placement by stepping
								// though x and z pretty fast and through y very slowly.
								// Then adds 400/y so that as the y value gets lower, the more area gets carved
								// which sets the limit on how far down the stalagmites can go.
								// Next, add a random value to add some noise to the pillar.
								// And lastly, sets the greater than value to be high so more stalagmites can be made
								// while the 400/y has already carved out the rest of the cave.
								boolean flagStalagmites = this.noiseGen.func_205563_a((double) x * 0.63125D,
										(double) z * 0.63125D, y * 0.04D) + (360 / (y))
										+ random.nextDouble() * 0.1D > 2.8D;

								// Where the pillar flag and stalagmite flag both flagged this block to be
								// carved, begin carving.
								// Thus the pillar and stalagmite is what is left after carving.
								if ((flagPillars && flagStalagmites)
										&& (xModified * xModified + zModified * zModified) * (double) this.ledgeWidthArrayYIndex[y - 1]
												+ d4 * d4 / 6.0D < 1.0D) {
									
									blockpos$mutableblockpos.setPos(x, y, z);
									currentBlockstate = worldIn.getBlockState(blockpos$mutableblockpos);
									blockpos$mutableblockposup.setPos(blockpos$mutableblockpos).move(Direction.UP);
									blockpos$mutableblockposdown.setPos(blockpos$mutableblockpos).move(Direction.DOWN);
									aboveBlockstate = worldIn.getBlockState(blockpos$mutableblockposup);

									
									if(y > 61) {
										//Creates the messy but cool plateau of stone on the ocean floor 
										//above this cave to help players locate caves when exploring
										//ocean biomes. Also helps to break up the blandness of ocean
										//floors.
										
										if (!currentBlockstate.getFluidState().isEmpty()) {
											worldIn.setBlockState(blockpos$mutableblockpos, replacementBlock, false);
										} else if (!aboveBlockstate.getFluidState().isEmpty()) {
											worldIn.setBlockState(blockpos$mutableblockpos, replacementBlock, false);
											worldIn.setBlockState(blockpos$mutableblockposup, replacementBlock, false);
											worldIn.setBlockState(blockpos$mutableblockposdown, replacementBlock, false);
											flag = true;
										}
									} else if (this.canCarveBlock(currentBlockstate, aboveBlockstate)
											|| canReplaceMap.containsKey(currentBlockstate)) {
										
										if (y < 11) {
											worldIn.setBlockState(blockpos$mutableblockpos, LAVA, false);
										} else {
											
											//carves the cave
											worldIn.setBlockState(blockpos$mutableblockpos, CAVE_AIR.getBlockState(), false);
										}

										flag = true;
									}
									
								}
							}
						}
					}
				}

				return flag;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * MC doesn't seem to do anything with the returned value in the end. Strange.
	 * I wonder why.
	 */
	protected boolean func_222708_a(double p_222708_1_, double p_222708_3_, double p_222708_5_, int p_222708_7_) {
		return true;
		//return (p_222708_1_ * p_222708_1_ + p_222708_5_ * p_222708_5_) * (double) this.ledgeWidthArrayYIndex[p_222708_7_ - 1] + p_222708_3_ * p_222708_3_ / 6.0D >= 1.0D;
	}
}

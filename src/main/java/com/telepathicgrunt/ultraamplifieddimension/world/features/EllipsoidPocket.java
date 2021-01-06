package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.longs.Long2ReferenceOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.common.Tags;

import java.util.Map;
import java.util.Random;


public class EllipsoidPocket extends Feature<OreFeatureConfig>
{
	public EllipsoidPocket(Codec<OreFeatureConfig> configFactory) {
		super(configFactory);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, OreFeatureConfig config) {
		BlockPos.Mutable blockposMutable = new BlockPos.Mutable();
		BlockState blockToReplace;
		float angleOfRotation = (float) (Math.PI * rand.nextFloat());
		float sinOfAngle = MathHelper.sin(angleOfRotation);
		float cosOfAngle = MathHelper.cos(angleOfRotation);
		float size = config.size * 0.5f;
		boolean solidState = config.state.isSolid();
		IChunk cachedChunk;
		float stretchedFactor = 0.7f;
		if(config.size < 10) stretchedFactor = 1;
		int maxY = (int) (size / 3);
		int minY = -maxY - 	1;

		for(int y = minY; y <= maxY; y++) {
			float yModified = y;
			if(y < 0){
				yModified = y + rand.nextFloat() * 0.5f;
			}
			else if (y > 0){
				y = (int) ((y + 0.25f) + (rand.nextFloat() * 0.5f));
			}

			float percentageOfRadius = 1f - (yModified / size) * (yModified / size) * 3;
			float majorRadiusSq = (size * percentageOfRadius) * (size * percentageOfRadius);
			float minorRadiusSq = (size * stretchedFactor * percentageOfRadius) * (size * stretchedFactor * percentageOfRadius);
			
			for(int x = (int) -size; x < size; x++) {
				for(int z = (int) -size; z < size; z++) {
					float majorComp;
					float minorComp;

					if(config.size > 10){
						majorComp = (x + 0.275f) * cosOfAngle - (z + 0.275f) * sinOfAngle;
						minorComp = (x + 0.275f) * sinOfAngle + (z + 0.275f) * cosOfAngle;
					}
					else {
						majorComp = ((x + 0.25f) + (rand.nextFloat() * 0.5f)) * cosOfAngle - ((z + 0.25f) + (rand.nextFloat() * 0.5f)) * sinOfAngle;
						minorComp = ((x + 0.25f) + (rand.nextFloat() * 0.5f)) * sinOfAngle + ((z + 0.25f) + (rand.nextFloat() * 0.5f)) * cosOfAngle;
					}

					float result = ((majorComp * majorComp) / (majorRadiusSq * majorRadiusSq)) +
									((minorComp * minorComp) / (minorRadiusSq * minorRadiusSq));

					if(result * 100f < 1f && !(x == 0 && z == 0 && y * y >= (size * size))) {
						blockposMutable.setPos(position.getX() + x, position.getY() + y, position.getZ() + z);
						cachedChunk = getCachedChunk(world, blockposMutable);

						blockToReplace = cachedChunk.getBlockState(blockposMutable);
						if(config.target.test(blockToReplace, rand) || Tags.Blocks.ORES.contains(blockToReplace.getBlock())) {
							if(solidState){
								cachedChunk.setBlockState(blockposMutable, config.state, false);
							}

							// if our replacement state is not solid, do not expose any liquids then.
							else {
								boolean touchingLiquid = false;
								for(Direction direction : Direction.values()){
									if(direction != Direction.DOWN){
										blockposMutable.move(direction);
										cachedChunk = getCachedChunk(world, blockposMutable);

										if(!cachedChunk.getBlockState(blockposMutable).getFluidState().isEmpty()){
											touchingLiquid = true;
											blockposMutable.move(direction.getOpposite());
											break;
										}

										blockposMutable.move(direction.getOpposite());
									}
								}

								if(!touchingLiquid){
									cachedChunk = getCachedChunk(world, blockposMutable);
									cachedChunk.setBlockState(blockposMutable, config.state, false);
								}
							}
						}
					}
				}
			}
		}
		
		return true;
	}


	private static final Map<ISeedReader, Long2ReferenceOpenHashMap<IChunk>> CACHED_CHUNKS_ALL_WORLDS = new Reference2ObjectOpenHashMap<>();
	public IChunk getCachedChunk(ISeedReader world, BlockPos blockpos) {

		// get the world's cache or make one if map doesnt exist.
		Long2ReferenceOpenHashMap<IChunk> worldCachedChunks = CACHED_CHUNKS_ALL_WORLDS.get(world);
		if(worldCachedChunks == null){
			worldCachedChunks = new Long2ReferenceOpenHashMap<>();
			CACHED_CHUNKS_ALL_WORLDS.put(world, worldCachedChunks);
		}

		// shrink cache if it is too large to clear out old chunk refs no longer needed.
		if(worldCachedChunks.size() > 50){
			CACHED_CHUNKS_ALL_WORLDS.clear();
		}

		// gets the chunk saved or does the expensive .getChunk to get it if it isn't cached yet.
		long posLong = (long) blockpos.getX() & 4294967295L | ((long)blockpos.getZ() & 4294967295L) << 32;
		IChunk cachedChunk = worldCachedChunks.get(posLong);
		if(cachedChunk == null){
			cachedChunk = world.getChunk(blockpos);
			worldCachedChunks.put(posLong, cachedChunk);
		}

		return cachedChunk;
	}
}

package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
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
		IChunk cachedChunk = world.getChunk(position);
		float stretchedFactor = 0.7f;
		if(config.size < 10) stretchedFactor = 1;

		for(int y = (int) Math.ceil(-size / 3f); y <= Math.floor(size / 3f); y++) {
			float yModified = y;
			if(y < 0){
				yModified = y + rand.nextFloat() * 0.5f;
			}
			else if (y > 0){
				y = (int) ((y + 0.25f) + (rand.nextFloat() * 0.5f));
				//y = (int) (y + rand.nextFloat() - 0.15f);
				//y = (int) (y + rand.nextFloat() - 0.15f);
			}

			float percentageOfRadius = 1f - (yModified / size) * (yModified / size) * 3;
			float majorRadiusSq = (size * percentageOfRadius) * (size * percentageOfRadius);
			float minorRadiusSq = (size * stretchedFactor * percentageOfRadius) * (size * stretchedFactor * percentageOfRadius);
			
			for(int x = (int) -size; x < size; x++) {
				for(int z = (int) -size; z < size; z++) {
					float majorComp = ((x + 0.25f) + (rand.nextFloat() * 0.5f)) * cosOfAngle - ((z + 0.25f) + (rand.nextFloat() * 0.5f)) * sinOfAngle;
					float minorComp = ((x + 0.25f) + (rand.nextFloat() * 0.5f)) * sinOfAngle + ((z + 0.25f) + (rand.nextFloat() * 0.5f)) * cosOfAngle;
					
					float result = ((majorComp * majorComp) / (majorRadiusSq * majorRadiusSq)) +
									((minorComp * minorComp) / (minorRadiusSq * minorRadiusSq));

					if(result * 100f < 1f && !(x == 0 && z == 0 && y * y >= (size * size))) {
						blockposMutable.setPos(position.getX() + x, position.getY() + y, position.getZ() + z);

						if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
							cachedChunk = world.getChunk(blockposMutable);

						blockToReplace = cachedChunk.getBlockState(blockposMutable);
						if(config.target.test(blockToReplace, rand) || Tags.Blocks.ORES.contains(blockToReplace.getBlock())) {
							if(solidState){
								cachedChunk.setBlockState(blockposMutable, config.state, false);
							}

							// if our replacement state is not solid, do not expose any liquids then.
							else{
								boolean touchingLiquid = false;
								for(Direction direction : Direction.values()){
									if(direction != Direction.DOWN){
										blockposMutable.move(direction);
										if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
											cachedChunk = world.getChunk(blockposMutable);


										if(!cachedChunk.getBlockState(blockposMutable).getFluidState().isEmpty()){
											touchingLiquid = true;
											blockposMutable.move(direction.getOpposite());
											break;
										}

										blockposMutable.move(direction.getOpposite());
									}
								}

								if(!touchingLiquid){
									if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
										cachedChunk = world.getChunk(blockposMutable);

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

}

package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
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
		
		for(int y = (int) -size / 3; y <= size / 3; y++) {
			float percentageOfRadius = 1f - (((float)y) / size) * (((float)y) / size) * 3;
			float majorRadiusSq = (size * percentageOfRadius) * (size * percentageOfRadius);
			float minorRadiusSq = (size * 0.7f * percentageOfRadius) * (size * 0.7f * percentageOfRadius);
			
			for(int x = (int) -size; x < size; x++) {
				for(int z = (int) -size; z < size; z++) {
					float majorComp = x * cosOfAngle - z * sinOfAngle; 
					float minorComp = x * sinOfAngle + z * cosOfAngle; 
					
					float result = (majorComp * majorComp) / (majorRadiusSq * majorRadiusSq) + (minorComp * minorComp) / (minorRadiusSq * minorRadiusSq);
					if(result * 100f < 1f && !(x == 0 && z == 0 && y * y >= (size * size))) {
						blockposMutable.setPos(position.getX() + x + 8, position.getY() + y, position.getZ() + z + 8);
						blockToReplace = world.getBlockState(blockposMutable);
						if(config.target.test(world.getBlockState(blockposMutable), rand) || Tags.Blocks.ORES.contains(blockToReplace.getBlock())) {
							if(solidState){
								world.setBlockState(blockposMutable, config.state, 3);
							}

							// if our replacement state is not solid, do not expose any liquids then.
							else{
								boolean touchingLiquid = false;
								for(Direction direction : Direction.values()){
									if(direction != Direction.DOWN){
										blockposMutable.move(direction);

										if(!world.getBlockState(blockposMutable).getFluidState().isEmpty()){
											touchingLiquid = true;
											blockposMutable.move(direction.getOpposite());
											break;
										}

										blockposMutable.move(direction.getOpposite());
									}
								}

								if(!touchingLiquid){
									world.setBlockState(blockposMutable, config.state, 3);
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

package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;

public class LakePlacementUA extends BasePlacement<CountRangeAndTypeConfig> {
	public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, CountRangeAndTypeConfig lakeConfig, Feature<C> featureIn, C featureConfig) {

		int x = random.nextInt(16) + 8; // counteract the -8 i do in the actual generation of lakes so lakes generate
										// around chosen position instead of in corner
		int z = random.nextInt(16) + 8;
		
		
		switch (lakeConfig.type) {
			case LAVA: {
	
				if (!ConfigUA.lavaLakeGen) {
					return false;
				}
	
				if (random.nextInt(lakeConfig.chance / 10) == 0) {
					int y = random.nextInt(random.nextInt(chunkGenerator.getMaxHeight() - 8) + 8);
					if (y < worldIn.getSeaLevel() || random.nextInt(lakeConfig.chance / 8) == 0) {
						featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(x, y, z), featureConfig);
					}
				}
	
				return true;
	
			}
			case WATER: {
	
				if (!ConfigUA.waterLakeGen) {
					return false;
				}
	
				if (random.nextInt(lakeConfig.chance) == 0) {
					int y = random.nextInt(chunkGenerator.getMaxHeight());
					featureIn.func_212245_a(worldIn, chunkGenerator, random, pos.add(x, y, z), featureConfig);
				}
	
				return true;
			}
			case SLIME: {
				if (!ConfigUA.slimeLakeGen) {
					return false;
				}
	
				if (random.nextInt(lakeConfig.chance) == 0) {
					
					int y = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE, pos.getX() + x, pos.getZ() + z);
					if (y > worldIn.getSeaLevel() && y <= 170) {
						featureIn.func_212245_a(worldIn, chunkGenerator, random, new BlockPos(pos.getX() + x, y - 2, pos.getZ() + z), featureConfig);
					}
				}
	
				return true;
			}
		}

		return false;
	}
}
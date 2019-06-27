package net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.placement.BasePlacement;
import net.minecraft.world.gen.placement.LakeChanceConfig;

public class SlimeLakePlacementUA extends BasePlacement<LakeChanceConfig> {
   public <C extends IFeatureConfig> boolean generate(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkGenerator, Random random, BlockPos pos, LakeChanceConfig placementConfig, Feature<C> featureIn, C featureConfig) {
	      if (random.nextInt(placementConfig.rarity) == 0) {
	         int x = random.nextInt(16)+8; //counteract the -8 i do in the actual generation of lakes so lakes generate around chosen position instead of in corner
	         int z = random.nextInt(16)+8;
	         int y = worldIn.getHeight(Heightmap.Type.WORLD_SURFACE, pos.getX()+x, pos.getZ()+z);
	         if (y > worldIn.getSeaLevel() && y <= 170) {
	            featureIn.func_212245_a(worldIn, chunkGenerator, random, new BlockPos(pos.getX()+x, y-2, pos.getZ()+z), featureConfig);
	         }
	      }

	      return true;
	   }
	}
package net.telepathicgrunt.ultraamplified.world.generation;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.OverworldGenSettings;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.PatrolSpawner;
import net.minecraft.world.spawner.PhantomSpawner;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressStructureUA;
import net.telepathicgrunt.ultraamplified.world.spawner.UACatSpawner;


public class UAChunkGenerator extends UANoiseChunkGenerator<OverworldGenSettings>
{
	private static final float[] BIOME_WEIGHTING_KERNEL = Util.make(new float[25], (p_222575_0_) ->
	{
		for (int xRelative = -2; xRelative <= 2; ++xRelative)
		{
			for (int zRelative = -2; zRelative <= 2; ++zRelative)
			{
				float biomeWeighting = 10.0F / MathHelper.sqrt(xRelative * xRelative + zRelative * zRelative + 0.2F);
				p_222575_0_[xRelative + 2 + (zRelative + 2) * 5] = biomeWeighting;
			}
		}

	});
	private final OctavesNoiseGenerator depthNoise;
	private final PhantomSpawner phantomSpawner = new PhantomSpawner();
	private final PatrolSpawner patrolSpawner = new PatrolSpawner();
	private final UACatSpawner catSpawner = new UACatSpawner();

	protected static final Set<Block> acceptableStoneFortressBlocks = Stream.of(Blocks.CRACKED_STONE_BRICKS, Blocks.CHISELED_STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS, Blocks.STONE_BRICKS, Blocks.INFESTED_CHISELED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS, Blocks.INFESTED_MOSSY_STONE_BRICKS, Blocks.INFESTED_STONE_BRICKS).collect(Collectors.toCollection(HashSet::new));


	public UAChunkGenerator(IWorld world, BiomeProvider provider, OverworldGenSettings settingsIn)
	{
		super(world, provider, 4, 8, 256, settingsIn);
		this.randomSeed.skip(2620);
		this.depthNoise = new OctavesNoiseGenerator(this.randomSeed, 15, 0);
	}


	@Override
	public void spawnMobs(WorldGenRegion region)
	{
		int i = region.getMainChunkX();
		int j = region.getMainChunkZ();
		Biome biome = region.getBiome((new ChunkPos(i, j)).asBlockPos());
		SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
		sharedseedrandom.setDecorationSeed(region.getSeed(), i << 4, j << 4);
		WorldEntitySpawner.performWorldGenSpawning(region, biome, i, j, sharedseedrandom);
	}


	@Override
	protected void fillNoiseColumn(double[] areaArrayIn, int x, int z)
	{
		this.setupPerlinNoiseGenerators(areaArrayIn, x, z, UltraAmplified.UAConfig.secretSetting.get() ? 117104.946D : UltraAmplified.UAConfig.xzTerrainModifier.get(), UltraAmplified.UAConfig.secretSetting.get() ? 468419.786D : UltraAmplified.UAConfig.yTerrainModifier.get(), UltraAmplified.UAConfig.xzScaleModifier.get(), UltraAmplified.UAConfig.secretSetting.get() ? 73.1905915D : UltraAmplified.UAConfig.yScaleModifier.get(), 8.555149841308594D, 4.277574920654297D, 3, -10);
	}


	@Override
	protected double func_222545_a(double p_222545_1_, double p_222545_3_, int p_222545_5_)
	{
		double d1 = (p_222545_5_ - (8.5D + p_222545_1_ * 8.5D / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / p_222545_3_;
		if (d1 < 0.0D)
		{
			d1 *= 4.0D;
		}

		return d1;
	}


	@Override
	protected double[] getBiomeNoiseColumn(int noiseX, int noiseZ)
	{
		double[] adouble = new double[2];
		float f = 0.0F;
		float f1 = 0.0F;
		float f2 = 0.0F;
		int y = this.getSeaLevel();
		float f3 = this.biomeProvider.getNoiseBiome(noiseX, y, noiseZ).getDepth();

		for (int xOffset = -2; xOffset <= 2; ++xOffset)
		{
			for (int zOffset = -2; zOffset <= 2; ++zOffset)
			{
				Biome biome = this.biomeProvider.getNoiseBiome(noiseX + xOffset, y, noiseZ + zOffset);
				float depthWeight = 0; //biome.getDepth();
				float scaleWeight = 0; //biome.getScale();

				//Does not take into account the biome's base height and scale.
				//Making the terrain's height based on biomes took away some of the magic and coolness.
				//Thus, all biomes now have a uniformed base and scale applied to their terrain.
				//offset + scale
				depthWeight = 1.0F + (0.0F + 0.4F) * 2F;
				scaleWeight = 1.0F + (0.0F + 0.3F) * 12F;

				float f6 = BIOME_WEIGHTING_KERNEL[xOffset + 2 + (zOffset + 2) * 5] / (depthWeight + 2.0F);
				if (biome.getDepth() > f3)
				{
					f6 /= 2.0F;
				}

				f += scaleWeight * f6;
				f1 += depthWeight * f6;
				f2 += f6;
			}
		}

		f = f / f2;
		f1 = f1 / f2;
		f = f * 0.9F + 0.1F;
		f1 = (f1 * 4.0F - 1.0F) / 8.0F;
		adouble[0] = f1 + this.getNoiseDepthAt(noiseX, noiseZ);
		adouble[1] = f;
		return adouble;
	}


	private double getNoiseDepthAt(int x, int z)
	{
		double noise = this.depthNoise.getValue(x * 200, 10.0D, z * 200, 1.0D, 0.0D, true) * 65535.0D / 8000.0D;
		if (noise < 0.0D)
		{
			noise = -noise * 0.3D;
		}

		noise = noise * 3.0D - 2.0D;
		if (noise < 0.0D)
		{
			noise = noise / 28.0D;
		}
		else
		{
			if (noise > 1.0D)
			{
				noise = 1.0D;
			}

			noise = noise / 40.0D;
		}

		return noise;
	}


	@Override
	public List<Biome.SpawnListEntry> getPossibleCreatures(EntityClassification creatureType, BlockPos pos)
	{
		if (UAFeatures.WITCH_HUT.func_202383_b(this.world, pos))
		{
			//Witch Hut entity spawning
			if (creatureType == EntityClassification.MONSTER)
			{
				return UAFeatures.WITCH_HUT.getSpawnList();
			}
			if (creatureType == EntityClassification.CREATURE)
			{
				return UAFeatures.WITCH_HUT.getCreatureSpawnList();
			}
		}
		else if (creatureType == EntityClassification.MONSTER)
		{
			//Ordered checks from most common to least

			//The following fortress checks has no biome check to let players convert one
			//fortress into another type manually and get the other fortress's spawning entities.

			//Stone Fortress entity spawning
			if (acceptableStoneFortressBlocks.contains(this.world.getBlockState(pos.down()).getBlock()))
			{
				if (UAFeatures.FORTRESS.isPositionInsideStructure(this.world, pos))
				{
					return ((FortressStructureUA) UAFeatures.FORTRESS).getStoneFortressSpawnList();
				}
				if (UAFeatures.FORTRESS.isPositionInStructure(this.world, pos))
				{
					return ((FortressStructureUA) UAFeatures.FORTRESS).getStoneFortressSpawnList();
				}
			}
			//Nether Fortress entity spawning
			else if (this.world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICKS)
			{
				if (UAFeatures.FORTRESS.isPositionInsideStructure(this.world, pos))
				{
					return ((FortressStructureUA) UAFeatures.FORTRESS).getNetherFortressSpawnList();
				}
				if (UAFeatures.FORTRESS.isPositionInStructure(this.world, pos))
				{
					return ((FortressStructureUA) UAFeatures.FORTRESS).getNetherFortressSpawnList();
				}
			}

			//Pillager Outpost entity spawning
			if (UAFeatures.PILLAGER_OUTPOST.isPositionInStructure(this.world, pos))
			{
				return UAFeatures.PILLAGER_OUTPOST.getSpawnList();
			}

			//Ocean Monument entity spawning
			if (UAFeatures.OCEAN_MONUMENT.isPositionInStructure(this.world, pos))
			{
				return UAFeatures.OCEAN_MONUMENT.getSpawnList();
			}

			//End City entity spawning
			if (this.world.getBlockState(pos.down()).getBlock() == Blocks.PURPUR_BLOCK)
			{
				if (UAFeatures.END_CITY.isPositionInStructure(this.world, pos))
				{
					return UAFeatures.END_CITY.getSpawnList();
				}
				if (UAFeatures.END_CITY.isPositionInStructure(this.world, pos))
				{
					return UAFeatures.END_CITY.getSpawnList();
				}
			}
		}
		else if (creatureType == EntityClassification.CREATURE)
		{
			//End City entity spawning
			if (this.world.getBlockState(pos.down()).getBlock() == Blocks.PURPUR_BLOCK)
			{
				if (UAFeatures.END_CITY.isPositionInStructure(this.world, pos))
				{
					return UAFeatures.END_CITY.getSpawnList();
				}
				if (UAFeatures.END_CITY.isPositionInStructure(this.world, pos))
				{
					return UAFeatures.END_CITY.getSpawnList();
				}
			}
		}

		return super.getPossibleCreatures(creatureType, pos);
	}


	@Override
	public void spawnMobs(ServerWorld world, boolean spawnHostileMobs, boolean spawnPeacefulMobs)
	{
		this.phantomSpawner.tick(world, spawnHostileMobs, spawnPeacefulMobs);
		this.patrolSpawner.tick(world, spawnHostileMobs, spawnPeacefulMobs);
		this.catSpawner.tick(world, spawnHostileMobs, spawnPeacefulMobs);
	}


	@Override
	public int getGroundHeight()
	{
		return UltraAmplified.UAConfig.seaLevel.get() + 1;
	}


	@Override
	public int getSeaLevel()
	{
		return UltraAmplified.UAConfig.seaLevel.get();
	}

}
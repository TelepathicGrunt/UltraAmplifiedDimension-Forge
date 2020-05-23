package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;


public class OceanMonumentStructureUA extends Structure<NoFeatureConfig>
{
    /**
     * --------------------------------------------------------------------------
     * |									|
     * |	HELLO READERS! IF YOU'RE HERE, YOU'RE PROBABLY			|
     * |	LOOKING FOR A TUTORIAL ON HOW TO DO STRUCTURES			|
     * |									|
     * -------------------------------------------------------------------------
     * 
     * Don't worry, I actually have a structure tutorial
     * mod already setup for you to check out! It's full
     * of comments on what does what and how to make structures.
     * 
     * Here's the link! https://github.com/TelepathicGrunt/StructureTutorialMod
     * 
     * Good luck and have fun modding!
     */
   
    	private static final List<Biome.SpawnListEntry> MONUMENT_ENEMIES = 
    		Lists.newArrayList(new Biome.SpawnListEntry(EntityType.GUARDIAN, 1, 2, 4));

	public OceanMonumentStructureUA(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51427_1_)
	{
		super(p_i51427_1_);
	}


	@Override
	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ)
	{
		int maxDistance = UltraAmplified.UAConfig.oceanMonumentSpawnrate.get();
		int minDistance = 8;
		if (maxDistance < 9)
		{
			minDistance = maxDistance - 1;
		}
		int k = x + maxDistance * spacingOffsetsX;
		int l = z + maxDistance * spacingOffsetsZ;
		int i1 = k < 0 ? k - maxDistance + 1 : k;
		int j1 = l < 0 ? l - maxDistance + 1 : l;
		int k1 = i1 / maxDistance;
		int l1 = j1 / maxDistance;
		((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 10387313);
		k1 = k1 * maxDistance;
		l1 = l1 * maxDistance;
		k1 = k1 + (random.nextInt(maxDistance - minDistance) + random.nextInt(maxDistance - minDistance)) / 2;
		l1 = l1 + (random.nextInt(maxDistance - minDistance) + random.nextInt(maxDistance - minDistance)) / 2;
		return new ChunkPos(k1, l1);
	}


	@Override
	public boolean canBeGenerated(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome)
	{
		ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
		if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z)
		{
			for (Biome biome2 : chunkGen.getBiomeProvider().getBiomes(chunkPosX * 16 + 9, chunkGen.getSeaLevel(), chunkPosZ * 16 + 9, 32))
			{
				if (UltraAmplified.UAConfig.oceanMonumentSpawnrate.get() != 101 && 
					chunkGen.hasStructure(biome2, UAFeatures.OCEAN_MONUMENT))
				{
					return true;
				}
			}
		}

		return false;
	}


	@Override
	public Structure.IStartFactory getStartFactory()
	{
		return OceanMonumentStructureUA.Start::new;
	}


	@Override
	public String getStructureName()
	{
		return UltraAmplified.MODID + ":ocean_monument";
	}


	@Override
	public int getSize()
	{
		return 8;
	}


	@Override
	public List<Biome.SpawnListEntry> getSpawnList()
	{
		return MONUMENT_ENEMIES;
	}

	public static class Start extends StructureStart
	{
		private boolean wasCreated;


		public Start(Structure<?> p_i225814_1_, int p_i225814_2_, int p_i225814_3_, MutableBoundingBox p_i225814_4_, int p_i225814_5_, long p_i225814_6_)
		{
			super(p_i225814_1_, p_i225814_2_, p_i225814_3_, p_i225814_4_, p_i225814_5_, p_i225814_6_);
		}


		@Override
		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn)
		{
			this.generate(chunkX, chunkZ);
		}


		private void generate(int chunkX, int chunkZ)
		{
			int i = chunkX * 16 - 29;
			int j = chunkZ * 16 - 29;
			Direction enumfacing = Direction.Plane.HORIZONTAL.random(this.rand);
			this.components.add(new OceanMonumentPiecesUA.MonumentBuilding(this.rand, i, j, enumfacing));
			this.recalculateStructureSize();

			// UltraAmplified.LOGGER.log(Level.DEBUG, "Ocean Monument | "+(chunkX*16)+"
			// "+(chunkZ*16));

			this.wasCreated = true;
		}


		/**
		 * Keeps iterating Structure Pieces and spawning them until the checks tell it to stop
		 */
		public void generateStructure(IWorld world, ChunkGenerator<?> p_225565_2_, Random rand, MutableBoundingBox structurebb, ChunkPos pos)
		{
			if (!this.wasCreated)
			{
				this.components.clear();
				this.generate(this.getChunkPosX(), this.getChunkPosZ());
			}

			super.generateStructure(world, p_225565_2_, rand, structurebb, pos);
		}
	}
}

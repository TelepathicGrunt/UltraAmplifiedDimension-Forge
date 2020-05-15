package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;

import net.minecraft.entity.EntityType;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;


public class FortressStoneStructureUA extends Structure<NoFeatureConfig>
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

	private static final List<Biome.SpawnListEntry> STONE_FORTRESS_ENEMIES = Lists.newArrayList(
		new Biome.SpawnListEntry(EntityType.SILVERFISH, 2, 5, 10), 
		new Biome.SpawnListEntry(EntityType.SLIME, 4, 1, 2), 
		new Biome.SpawnListEntry(EntityType.WITHER_SKELETON, 8, 4, 6), 
		new Biome.SpawnListEntry(EntityType.SKELETON, 11, 6, 8), 
		new Biome.SpawnListEntry(EntityType.CAVE_SPIDER, 4, 2, 4));

	private static final List<Biome.SpawnListEntry> STONE_FORTRESS_ENEMIES_WITHOUT_SILVERFISH = Lists.newArrayList(
		new Biome.SpawnListEntry(EntityType.SLIME, 4, 1, 3), 
		new Biome.SpawnListEntry(EntityType.WITHER_SKELETON, 8, 4, 6), 
		new Biome.SpawnListEntry(EntityType.SKELETON, 10, 6, 8), 
		new Biome.SpawnListEntry(EntityType.CAVE_SPIDER, 5, 2, 5));


	public FortressStoneStructureUA(Function<Dynamic<?>, ? extends NoFeatureConfig> config)
	{
		super(config);
	}


	@Override
	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {
	    int maxDistance = UltraAmplified.UAConfig.stoneFortressSpawnrate.get();
	    int minDistance = 8;

	    if (maxDistance < 9) {
		minDistance = maxDistance - 1;
	    }

	    int k = x + maxDistance * spacingOffsetsX;
	    int l = z + maxDistance * spacingOffsetsZ;
	    int i1 = k < 0 ? k - maxDistance + 1 : k;
	    int j1 = l < 0 ? l - maxDistance + 1 : l;
	    int k1 = i1 / maxDistance;
	    int l1 = j1 / maxDistance;
	    ((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 14357655);
	    k1 = k1 * maxDistance;
	    l1 = l1 * maxDistance;
	    k1 = k1 + random.nextInt(maxDistance - minDistance);
	    l1 = l1 + random.nextInt(maxDistance - minDistance);
	    return new ChunkPos(k1, l1);
	}


	@Override
	public boolean func_225558_a_(BiomeManager biomeManager, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome)
	{
		if (UltraAmplified.UAConfig.stoneFortressSpawnrate.get() != 101) 
		{
			int i = chunkPosX >> 4;
			int j = chunkPosZ >> 4;
			rand.setSeed(i ^ j << 4 ^ chunkGen.getSeed());
			
			ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
			if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
			    return chunkGen.hasStructure(biome, UAFeatures.STONE_FORTRESS);
			}
		}

		return false;
	}


	@Override
	public Structure.IStartFactory getStartFactory()
	{
		return FortressStoneStructureUA.Start::new;
	}


	@Override
	public String getStructureName()
	{
		return UltraAmplified.MODID + ":fortress_stone";
	}


	@Override
	public int getSize()
	{
		return 8;
	}

	public List<Biome.SpawnListEntry> getStoneFortressSpawnList()
	{
		if (UltraAmplified.UAConfig.allowNaturalSilverfishFortress.get())
		{
			return STONE_FORTRESS_ENEMIES;
		}
		else
		{
			return STONE_FORTRESS_ENEMIES_WITHOUT_SILVERFISH;
		}

	}

	public static class Start extends StructureStart
	{
		public Start(Structure<?> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn)
		{
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}


		@Override
		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn)
		{
			FortressPiecesUA.Start fortresspieces$start = new FortressPiecesUA.Start(this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2, true);
			this.components.add(fortresspieces$start);

			fortresspieces$start.buildComponent(fortresspieces$start, this.components, this.rand);
			List<StructurePiece> list = fortresspieces$start.pendingChildren;

			while (!list.isEmpty())
			{
				int i = this.rand.nextInt(list.size());
				StructurePiece structurepiece = list.remove(i);
				structurepiece.buildComponent(fortresspieces$start, this.components, this.rand);
			}

			this.recalculateStructureSize();
			this.func_214626_a(this.rand, 15, 30);
			// UltraAmplified.LOGGER.log(Level.DEBUG, "Stone Fortress | "+(chunkX*16)+" "+(chunkZ*16));
		}
	}
}
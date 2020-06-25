package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;

import net.minecraft.entity.EntityType;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.MarginedStructureStart;
import net.minecraft.world.gen.feature.structure.PillagerOutpostPieces;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;


public class PillagerOutpostStructureUA extends Structure<NoFeatureConfig>
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
	private static final List<Biome.SpawnListEntry> PILLAGER_ENEMIES = 
		Lists.newArrayList(new Biome.SpawnListEntry(EntityType.PILLAGER, 1, 1, 1));

	public PillagerOutpostStructureUA(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51470_1_)
	{
		super(p_i51470_1_);
	}


	@Override
	public String getStructureName()
	{
		return UltraAmplified.MODID + ":pillager_outpost";
	}


	@Override
	public int getSize()
	{
		return 3;
	}


	@Override
	public List<Biome.SpawnListEntry> getSpawnList()
	{
		return PILLAGER_ENEMIES;
	}


	@Override
	public boolean canBeGenerated(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome)
	{
		((SharedSeedRandom) rand).setLargeFeatureSeed(chunkGen.getSeed(), chunkPosX, chunkPosZ);

		if (UltraAmplified.UAStructuresConfig.pillageOutpostRarity.get() != 101 && chunkGen.hasStructure(biome, UAFeatures.PILLAGER_OUTPOST))
		{

			if (rand.nextFloat() < 1 / ((UltraAmplified.UAStructuresConfig.pillageOutpostRarity.get() - 1) * 4.5D + 1))
			{
				for (int k = chunkPosX - 3; k <= chunkPosX + 3; ++k)
				{
					for (int l = chunkPosZ - 3; l <= chunkPosZ + 3; ++l)
					{
						if (UAFeatures.VILLAGE.canBeGenerated(p_225558_1_, chunkGen, rand, k, l, biome))
						{
							return false;
						}
					}
				}

				return true;
			}
		}

		return false;
	}


	@Override
	public Structure.IStartFactory getStartFactory()
	{
		return PillagerOutpostStructureUA.Start::new;
	}


	protected int getSeedModifier()
	{
		return 165745296;
	}

	public static class Start extends MarginedStructureStart
	{
		public Start(Structure<?> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn)
		{
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}


		@Override
		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn)
		{

			int offsetX = 7;
			int offsetZ = 7;
			int x = (chunkX << 4) + offsetX;
			int z = (chunkZ << 4) + offsetZ;
			int i1 = generator.func_222531_c(x, z, Heightmap.Type.WORLD_SURFACE_WG);
			int j1 = generator.func_222531_c(x, z + 1, Heightmap.Type.WORLD_SURFACE_WG);
			int k1 = generator.func_222531_c(x + 1, z, Heightmap.Type.WORLD_SURFACE_WG);
			int l1 = generator.func_222531_c(x + 1, z + 1, Heightmap.Type.WORLD_SURFACE_WG);
			int y = Math.max(Math.max(i1, j1), Math.max(k1, l1));

			if (y >= 70 && y <= 200)
			{
				BlockPos blockpos = new BlockPos(chunkX * 16, y, chunkZ * 16);
				PillagerOutpostPieces.func_215139_a(generator, templateManagerIn, blockpos, this.components, this.rand);
				this.recalculateStructureSize();
				// UltraAmplified.LOGGER.log(Level.DEBUG, "Pillager Outpost | "+(x)+" "+(y+1)+"
				// "+(z));
			}
		}
	}
}
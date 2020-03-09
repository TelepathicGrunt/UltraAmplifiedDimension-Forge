package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;

import net.minecraft.entity.EntityType;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;


public class FortressStructureUA extends Structure<FortressConfigUA>
{
	private static final List<Biome.SpawnListEntry> NETHER_FORTRESS_ENEMIES = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.BLAZE, 10, 2, 3), new Biome.SpawnListEntry(EntityType.ZOMBIE_PIGMAN, 5, 4, 4), new Biome.SpawnListEntry(EntityType.WITHER_SKELETON, 8, 5, 5), new Biome.SpawnListEntry(EntityType.SKELETON, 2, 5, 5), new Biome.SpawnListEntry(EntityType.MAGMA_CUBE, 3, 4, 4));

	private static final List<Biome.SpawnListEntry> STONE_FORTRESS_ENEMIES = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.SILVERFISH, 2, 5, 10), new Biome.SpawnListEntry(EntityType.SLIME, 4, 1, 2), new Biome.SpawnListEntry(EntityType.WITHER_SKELETON, 8, 4, 6), new Biome.SpawnListEntry(EntityType.SKELETON, 11, 6, 8), new Biome.SpawnListEntry(EntityType.CAVE_SPIDER, 4, 2, 4));

	private static final List<Biome.SpawnListEntry> STONE_FORTRESS_ENEMIES_WITHOUT_SILVERFISH = Lists.newArrayList(new Biome.SpawnListEntry(EntityType.SLIME, 4, 1, 3), new Biome.SpawnListEntry(EntityType.WITHER_SKELETON, 8, 4, 6), new Biome.SpawnListEntry(EntityType.SKELETON, 10, 6, 8), new Biome.SpawnListEntry(EntityType.CAVE_SPIDER, 5, 2, 5));


	public FortressStructureUA(Function<Dynamic<?>, ? extends FortressConfigUA> p_i51427_1_)
	{
		super(p_i51427_1_);
	}


	@Override
	public boolean shouldStartAt(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome)
	{
		if (ConfigUA.netherFortressAboveground || ConfigUA.netherFortressUnderground)
		{
			int i = chunkPosX >> 4;
			int j = chunkPosZ >> 4;
			rand.setSeed(i ^ j << 4 ^ chunkGen.getSeed());
			if (chunkPosX != (i << 4) + 4 + rand.nextInt(ConfigUA.netherFortressSpawnrate))
			{
				return false;
			}
			else if (chunkPosZ != (j << 4) + 4 + rand.nextInt(ConfigUA.netherFortressSpawnrate))
			{
				return false;
			}
			else
			{

				return chunkGen.hasStructure(biome, UAFeatures.FORTRESS_UA);
			}
		}

		return false;
	}


	@Override
	public Structure.IStartFactory getStartFactory()
	{
		return FortressStructureUA.Start::new;
	}


	@Override
	public String getStructureName()
	{
		return UltraAmplified.MODID + ":fortress";
	}


	@Override
	public int getSize()
	{
		return 8;
	}


	//Specific spawn list for each kind of fortress
	public List<Biome.SpawnListEntry> getNetherFortressSpawnList()
	{
		return NETHER_FORTRESS_ENEMIES;
	}


	public List<Biome.SpawnListEntry> getStoneFortressSpawnList()
	{
		if (ConfigUA.allowNaturalSilverfishFortress)
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
		private boolean genAboveSeaLevel;


		public Start(Structure<?> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn)
		{
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}


		@Override
		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn)
		{
			FortressConfigUA fortressconfig = generator.getStructureConfig(biomeIn, UAFeatures.FORTRESS_UA);
			this.genAboveSeaLevel = fortressconfig.surfaceAllow;
			boolean stoneVariant = false;

			// gens aboveground 50% of time if underground is allowed and 100% of time when
			// underground is not allowed
			// Otherwise, it'll always generate underground.
			// This will always be allowed aboveground or underground when reaching here as
			// we already checked if
			// this structure was disabled in hasStartAt
			if (genAboveSeaLevel)
			{
				stoneVariant = false;
			}
			else
			{
				stoneVariant = true;
			}

			FortressPiecesUA.Start fortresspieces$start = new FortressPiecesUA.Start(this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2, stoneVariant);
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

			if (fortressconfig.surfaceAllow)
			{
				if (rand.nextBoolean())
				{
					this.func_214626_a(this.rand, 85, 130);
				}
				else
				{
					this.func_214626_a(this.rand, 15, 30);
				}
				// UltraAmplified.LOGGER.log(Level.DEBUG, "Nether Fortress | "+(chunkX*16)+"
				// "+(chunkZ*16));
			}
			else
			{
				this.func_214626_a(this.rand, 15, 30);
				// UltraAmplified.LOGGER.log(Level.DEBUG, "Stone Fortress | "+(chunkX*16)+"
				// "+(chunkZ*16));
			}
		}
	}
}
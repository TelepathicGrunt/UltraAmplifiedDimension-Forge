package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;


public class MineshaftStructureUA extends Structure<MineshaftConfigUA>
{
	public MineshaftStructureUA(Function<Dynamic<?>, ? extends MineshaftConfigUA> p_i51427_1_)
	{
		super(p_i51427_1_);
	}


	@Override
	public boolean shouldStartAt(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome)
	{
		((SharedSeedRandom) rand).setLargeFeatureSeed(chunkGen.getSeed(), chunkPosX, chunkPosZ);

		if ((ConfigUA.mineshaftAbovegroundAllowed || ConfigUA.mineshaftUndergroundAllowed) && chunkGen.hasStructure(biome, UAFeatures.MINESHAFT_UA))
		{
			return rand.nextDouble() < (ConfigUA.mineshaftSpawnrate) / 10000D;
		}
		else
		{
			return false;
		}
	}


	@Override
	public Structure.IStartFactory getStartFactory()
	{
		return MineshaftStructureUA.Start::new;
	}


	@Override
	public String getStructureName()
	{
		return UltraAmplified.MODID + ":mineshaft";
	}


	@Override
	public int getSize()
	{
		return 8;
	}

	public static class Start extends StructureStart
	{
		private MineshaftStructureUA.Type type;


		public Start(Structure<?> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox, int referenceIn, long seedIn)
		{
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}


		@Override
		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn)
		{
			MineshaftConfigUA mineshaftconfig = generator.getStructureConfig(biomeIn, UAFeatures.MINESHAFT_UA);
			this.type = mineshaftconfig.type;
			MineshaftPiecesUA.Room structuremineshaftpiecesua$room = new MineshaftPiecesUA.Room(0, this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2, this.type);
			this.components.add(structuremineshaftpiecesua$room);

			structuremineshaftpiecesua$room.buildComponent(structuremineshaftpiecesua$room, this.components, this.rand);
			this.recalculateStructureSize();

			// makes the normal sized dirt room mineshafts spawn between 150 and 240
			// otherwise they will spawn at bottom of world
			if (structuremineshaftpiecesua$room.getBoundingBox().maxY < 100)
			{
				this.func_214626_a(this.rand, 150, 151 + this.rand.nextInt(70));
				// UltraAmplified.LOGGER.log(Level.DEBUG, "Aboveground Mineshaft |
				// "+this.type.toString()+" | "+(chunkX*16)+" "+(chunkZ*16));
			}
			else
			{
				this.func_214626_a(this.rand, 10, 11);
				// UltraAmplified.LOGGER.log(Level.DEBUG, "Underground Mineshaft |
				// "+this.type.toString()+" | "+(chunkX*16)+" "+(chunkZ*16));
			}

		}
	}

	public static enum Type
	{
		NORMAL, MESA, ICEY, COLDORBIRCH, JUNGLE, TAIGA, DESERT, STONE, SAVANNA, SWAMPORDARKFOREST, END, HELL, OCEAN;

		public static MineshaftStructureUA.Type byId(int id)
		{
			return id >= 0 && id < values().length ? values()[id] : NORMAL;
		}
	}
}

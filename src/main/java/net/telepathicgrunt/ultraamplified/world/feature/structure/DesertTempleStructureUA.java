package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class DesertTempleStructureUA extends Structure<NoFeatureConfig>
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
	public DesertTempleStructureUA(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51427_1_)
	{
		super(p_i51427_1_);
	}


	@Override
	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ)
	{
		int maxDistance = UltraAmplified.UAConfig.desertTempleSpawnrate.get();
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
		((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 14357617);
		k1 = k1 * maxDistance;
		l1 = l1 * maxDistance;
		k1 = k1 + random.nextInt(maxDistance - minDistance);
		l1 = l1 + random.nextInt(maxDistance - minDistance);
		return new ChunkPos(k1, l1);
	}


	@Override
	public String getStructureName()
	{
		return UltraAmplified.MODID + ":desert_temple";
	}


	@Override
	public int getSize()
	{
		return 3;
	}


	@Override
	public Structure.IStartFactory getStartFactory()
	{
		return DesertTempleStructureUA.Start::new;
	}


	@Override
	public boolean func_225558_a_(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome)
	{
		ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
		if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z)
		{
			if ((UltraAmplified.UAConfig.desertTempleSpawnrate.get() != 101) && chunkGen.hasStructure(biome, this))
			{
				return true;
			}
		}
		return false;
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
			Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
			int xOffset = 5;
			int zOffset = 5;
			if (rotation == Rotation.CLOCKWISE_90)
			{
				xOffset = -5;
			}
			else if (rotation == Rotation.CLOCKWISE_180)
			{
				xOffset = -5;
				zOffset = -5;
			}
			else if (rotation == Rotation.COUNTERCLOCKWISE_90)
			{
				zOffset = -5;
			}

			int xPos = (chunkX << 4) + 7;
			int zPos = (chunkZ << 4) + 7;
			int y1 = generator.func_222531_c(xPos, zPos, Heightmap.Type.WORLD_SURFACE_WG);
			int y2 = generator.func_222531_c(xPos, zPos + zOffset, Heightmap.Type.WORLD_SURFACE_WG);
			int y3 = generator.func_222531_c(xPos + xOffset, zPos, Heightmap.Type.WORLD_SURFACE_WG);
			int y4 = generator.func_222531_c(xPos + xOffset, zPos + zOffset, Heightmap.Type.WORLD_SURFACE_WG);
			int y = Math.min(Math.min(y1, y2), Math.min(y3, y4));
			y = Math.min(y, 244);

			if (y >= 70)
			{
				DesertTemplePiecesUA desertpyramidpiece = new DesertTemplePiecesUA(this.rand, chunkX * 16, y, chunkZ * 16);
				this.components.add(desertpyramidpiece);
				this.recalculateStructureSize();
				// UltraAmplified.LOGGER.log(Level.DEBUG, "Desert Temple | "+(chunkX*16)+" "+(chunkZ*16));
			}
		}
	}
}
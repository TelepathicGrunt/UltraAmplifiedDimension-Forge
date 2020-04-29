package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class MushroomTempleStructure extends Structure<NoFeatureConfig>
{

	public MushroomTempleStructure(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51427_1_)
	{
		super(p_i51427_1_);
	}


	@Override
	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ)
	{
		int maxDistance = UltraAmplified.UAConfig.mushroomTempleSpawnrate.get();
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
		((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, this.getSeedModifier());
		k1 = k1 * maxDistance;
		l1 = l1 * maxDistance;
		k1 = k1 + random.nextInt(maxDistance - minDistance);
		l1 = l1 + random.nextInt(maxDistance - minDistance);
		return new ChunkPos(k1, l1);
	}


	@Override
	public String getStructureName()
	{
		return UltraAmplified.MODID + ":mushroom_temple";
	}


	@Override
	public int getSize()
	{
		return 3;
	}


	@Override
	public Structure.IStartFactory getStartFactory()
	{
		return MushroomTempleStructure.Start::new;
	}


	protected int getSeedModifier()
	{
		return 14357876;
	}


	@Override
	public boolean func_225558_a_(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome)
	{
		ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
		if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z)
		{
			if (UltraAmplified.UAConfig.mushroomTempleSpawnrate.get() != 101 && chunkGen.hasStructure(biome, this))
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
			int x = chunkX * 16;
			int z = chunkZ * 16;
			BlockPos blockpos = new BlockPos(x, 90, z);
			Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
			MushroomTemplePiecesUA.start(templateManagerIn, blockpos, rotation, this.components, this.rand);
			this.recalculateStructureSize();

			// UltraAmplified.LOGGER.log(Level.DEBUG, "mushroom temple | "+(chunkX*16)+"
			// "+(chunkZ*16));
		}
	}

}
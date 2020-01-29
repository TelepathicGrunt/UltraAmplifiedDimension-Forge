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
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;


public class ShipwreckStructureUA extends Structure<ShipwreckConfig>
{

	public ShipwreckStructureUA(Function<Dynamic<?>, ? extends ShipwreckConfig> p_i51427_1_)
	{
		super(p_i51427_1_);
	}


	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ)
	{
		int maxDistance = ConfigUA.shipwreckSpawnrate;
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


	public String getStructureName()
	{
		return UltraAmplified.MODID + ":shipwreck";
	}


	public int getSize()
	{
		return 3;
	}


	public Structure.IStartFactory getStartFactory()
	{
		return ShipwreckStructureUA.Start::new;
	}


	protected int getSeedModifier()
	{
		return 165745295;
	}


	public boolean shouldStartAt(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome)
	{
		ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
		if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z)
		{
			if (ConfigUA.shipwreckSpawnrate != 101 && chunkGen.hasStructure(biome, this))
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


		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn)
		{

			Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
			int randHeight = rand.nextInt(130) + 70;

			BlockPos blockpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);

			// Our shipwreck can generate all kinds of variants regardless of what biome it
			// is in
			ShipwreckConfig newShipwreckConfig = new ShipwreckConfig(this.rand.nextBoolean() ? true : false);

			ShipwreckPiecesUA.beginGeneration(templateManagerIn, blockpos, rotation, this.components, this.rand, newShipwreckConfig, randHeight);
			this.recalculateStructureSize();

			// UltraAmplified.LOGGER.log(Level.DEBUG, "Shipwreck | "+blockpos.getX()+"
			// "+this.bounds.minY+" "+blockpos.getZ());

		}

	}
}
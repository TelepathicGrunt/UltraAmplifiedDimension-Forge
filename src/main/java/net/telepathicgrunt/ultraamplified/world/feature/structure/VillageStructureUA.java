package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.feature.structure.VillagePieces;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;


public class VillageStructureUA extends Structure<VillageConfigUA>
{
	public VillageStructureUA(Function<Dynamic<?>, ? extends VillageConfigUA> p_i51427_1_)
	{
		super(p_i51427_1_);
	}


	@Override
	public String getStructureName()
	{
		return UltraAmplified.MODID + ":village";
	}


	@Override
	public int getSize()
	{
		return 8;
	}


	@Override
	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ)
	{
		int maxDistance = ConfigUA.villageSpawnrate;
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
		((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 10387312);
		k1 = k1 * maxDistance;
		l1 = l1 * maxDistance;
		k1 = k1 + random.nextInt(maxDistance - minDistance);
		l1 = l1 + random.nextInt(maxDistance - minDistance);
		return new ChunkPos(k1, l1);
	}


	@Override
	public boolean shouldStartAt(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome)
	{
		ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
		if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z)
		{
			if (ConfigUA.villageSpawnrate != 101 && chunkGen.hasStructure(biome, UAFeatures.VILLAGE))
			{
				return true;
			}
		}
		return false;
	}


	@Override
	public Structure.IStartFactory getStartFactory()
	{
		return VillageStructureUA.Start::new;
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
			VillageConfigUA villageconfig = generator.getStructureConfig(biomeIn, UAFeatures.VILLAGE);

			// if we are requesting a village type that vanilla already can make,
			// we then generate a vanilla village.
			// This sets up the config that vanilla needs
			VillageConfig vanillaVillageConfig = new VillageConfig("village/plains/town_centers", 6);
			boolean genVanillaVillage = false;
			if (villageconfig.type == VillagePastStyledPiecesUA.Type.OAK)
			{
				genVanillaVillage = true;
			}
			else if (villageconfig.type == VillagePastStyledPiecesUA.Type.SANDSTONE)
			{
				vanillaVillageConfig = new VillageConfig("village/desert/town_centers", 6);
				genVanillaVillage = true;
			}
			else if (villageconfig.type == VillagePastStyledPiecesUA.Type.ACACIA)
			{
				vanillaVillageConfig = new VillageConfig("village/savanna/town_centers", 6);
				genVanillaVillage = true;
			}
			else if (villageconfig.type == VillagePastStyledPiecesUA.Type.SNOWYSPRUCE)
			{
				vanillaVillageConfig = new VillageConfig("village/snowy/town_centers", 6);
				genVanillaVillage = true;
			}
			else if (villageconfig.type == VillagePastStyledPiecesUA.Type.SPRUCE)
			{
				vanillaVillageConfig = new VillageConfig("village/taiga/town_centers", 6);
				genVanillaVillage = true;
			}

			if (genVanillaVillage)
			{
				// generates a vanilla village so if villages change in future updates, we pull
				// the new villages automatically
				BlockPos blockpos = new BlockPos(chunkX * 16, 0, chunkZ * 16);
				VillagePieces.func_214838_a(generator, templateManagerIn, blockpos, this.components, this.rand, vanillaVillageConfig);
				this.recalculateStructureSize();
			}
			else
			{
				// generates our own kind of village
				List<VillagePastStyledPiecesUA.PieceWeightUA> list = VillagePastStyledPiecesUA.getStructureVillageWeightedPieceList(this.rand, this.rand.nextInt(3));
				VillagePastStyledPiecesUA.Start villagepiecesua$start = new VillagePastStyledPiecesUA.Start(0, this.rand, (chunkX << 4) + 2, (chunkZ << 4) + 2, list, villageconfig, biomeIn);
				this.components.add(villagepiecesua$start);
				villagepiecesua$start.buildComponent(villagepiecesua$start, this.components, this.rand);
				List<StructurePiece> list1 = villagepiecesua$start.pendingRoads;
				List<StructurePiece> list2 = villagepiecesua$start.pendingHouses;

				while (!list1.isEmpty() || !list2.isEmpty())
				{
					if (list1.isEmpty())
					{
						int i = this.rand.nextInt(list2.size());
						StructurePiece structurepiece = list2.remove(i);
						structurepiece.buildComponent(villagepiecesua$start, this.components, this.rand);
					}
					else
					{
						int j = this.rand.nextInt(list1.size());
						StructurePiece structurepiece2 = list1.remove(j);
						structurepiece2.buildComponent(villagepiecesua$start, this.components, this.rand);
					}
				}
			}

			this.recalculateStructureSize();
			// UltraAmplified.LOGGER.log(Level.DEBUG,villageconfig.type+" Village |
			// "+chunkX*16+", "+chunkZ*16);
		}
	}
}

package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.WoodlandMansionPieces;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;


public class WoodlandMansionStructureUA extends Structure<NoFeatureConfig>
{

	public WoodlandMansionStructureUA(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51427_1_)
	{
		super(p_i51427_1_);
	}


	@Override
	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ)
	{

		int maxSpacing = UltraAmplified.UAConfig.mansionSpawnrate.get();
		int minSpacing = (int) (maxSpacing * 0.75);

		if (maxSpacing < 10)
		{
			minSpacing = maxSpacing - 1;
		}

		int k = x + maxSpacing * spacingOffsetsX;
		int l = z + maxSpacing * spacingOffsetsZ;
		int i1 = k < 0 ? k - maxSpacing + 1 : k;
		int j1 = l < 0 ? l - maxSpacing + 1 : l;
		int k1 = i1 / maxSpacing;
		int l1 = j1 / maxSpacing;
		((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 10387319);
		k1 = k1 * maxSpacing;
		l1 = l1 * maxSpacing;
		k1 = k1 + (random.nextInt(maxSpacing - minSpacing) + random.nextInt(maxSpacing - minSpacing)) / 2;
		l1 = l1 + (random.nextInt(maxSpacing - minSpacing) + random.nextInt(maxSpacing - minSpacing)) / 2;
		return new ChunkPos(k1, l1);
	}


	@Override
	public boolean func_225558_a_(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ, Biome biome)
	{
		ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
		if (UltraAmplified.UAConfig.mansionSpawnrate.get() != 101 && chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z)
		{
			for (Biome biome2 : chunkGen.getBiomeProvider().getBiomes(chunkPosX * 16 + 9, chunkGen.getSeaLevel(), chunkPosZ * 16 + 9, 32))
			{
				if (!chunkGen.hasStructure(biome2, UAFeatures.WOODLAND_MANSION))
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}


	@Override
	public Structure.IStartFactory getStartFactory()
	{
		return WoodlandMansionStructureUA.Start::new;
	}


	@Override
	public String getStructureName()
	{
		return UltraAmplified.MODID + ":woodland_mansion";
	}


	@Override
	public int getSize()
	{
		return 8;
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
			int i = 5;
			int j = 5;
			if (rotation == Rotation.CLOCKWISE_90)
			{
				i = -5;
			}
			else if (rotation == Rotation.CLOCKWISE_180)
			{
				i = -5;
				j = -5;
			}
			else if (rotation == Rotation.COUNTERCLOCKWISE_90)
			{
				j = -5;
			}

			int k = (chunkX << 4) + 7;
			int l = (chunkZ << 4) + 7;
			int i1 = generator.func_222531_c(k, l, Heightmap.Type.WORLD_SURFACE_WG);
			int j1 = generator.func_222531_c(k, l + j, Heightmap.Type.WORLD_SURFACE_WG);
			int k1 = generator.func_222531_c(k + i, l, Heightmap.Type.WORLD_SURFACE_WG);
			int l1 = generator.func_222531_c(k + i, l + j, Heightmap.Type.WORLD_SURFACE_WG);
			int y = Math.min(Math.min(i1, j1), Math.min(k1, l1));
			y = Math.min(y, 220);

			if (y >= 70)
			{
				BlockPos blockpos = new BlockPos(chunkX * 16 + 8, y + 1, chunkZ * 16 + 8);
				List<WoodlandMansionPieces.MansionTemplate> list = Lists.newLinkedList();
				WoodlandMansionPieces.generateMansion(templateManagerIn, blockpos, rotation, list, this.rand);
				this.components.addAll(list);
				this.recalculateStructureSize();
			}

			// UltraAmplified.LOGGER.log(Level.DEBUG, "Woodland Mansion | " + (chunkX * 16)
			// + " " + (chunkZ * 16));
		}


		/**
		 * Keeps iterating Structure Pieces and spawning them until the checks tell it to stop
		 */
		public void generateStructure(IWorld world, ChunkGenerator<?> p_225565_2_, Random rand, MutableBoundingBox structurebb, ChunkPos p_75068_4_)
		{
			super.generateStructure(world, p_225565_2_, rand, structurebb, p_75068_4_);
			int i = this.bounds.minY;

			for (int j = structurebb.minX; j <= structurebb.maxX; ++j)
			{
				for (int k = structurebb.minZ; k <= structurebb.maxZ; ++k)
				{
					BlockPos blockpos = new BlockPos(j, i, k);
					if (!world.isAirBlock(blockpos) && this.bounds.isVecInside(blockpos))
					{
						boolean flag = false;

						for (StructurePiece structurepiece : this.components)
						{
							if (structurepiece.getBoundingBox().isVecInside(blockpos))
							{
								flag = true;
								break;
							}
						}

						if (flag)
						{
							for (int l = i - 1; l > 1; --l)
							{
								BlockPos blockpos1 = new BlockPos(j, l, k);
								if (!world.isAirBlock(blockpos1) && !world.getBlockState(blockpos1).getMaterial().isLiquid())
								{
									break;
								}

								world.setBlockState(blockpos1, Blocks.COBBLESTONE.getDefaultState(), 2);
							}
						}
					}
				}
			}

		}
	}
}
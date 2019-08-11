package net.TelepathicGrunt.UltraAmplified.World.Feature.Structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;
import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.FeatureUA;
import net.minecraft.block.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.StructureStart;
import net.minecraft.world.gen.feature.structure.WoodlandMansionPieces;
import net.minecraft.world.gen.feature.template.TemplateManager;

public class WoodlandMansionUA extends Structure<NoFeatureConfig> {

	public WoodlandMansionUA(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51427_1_) {
		super(p_i51427_1_);
	}

	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z, int spacingOffsetsX, int spacingOffsetsZ) {

		int maxSpacing = ConfigUA.mansionSpawnrate;
		int minSpacing = (int) (maxSpacing * 0.75);

		if (maxSpacing < 10) {
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

	public boolean hasStartAt(ChunkGenerator<?> chunkGen, Random rand, int chunkPosX, int chunkPosZ) {
		ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
		if (ConfigUA.mansionSpawnrate != 101 && chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
			for (Biome biome : chunkGen.getBiomeProvider().getBiomesInSquare(chunkPosX * 16 + 9, chunkPosZ * 16 + 9, 32)) {
				if (!chunkGen.hasStructure(biome, FeatureUA.WOODLAND_MANSION_UA)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	protected boolean isEnabledIn(IWorld worldIn) {
		return worldIn.getWorldInfo().isMapFeaturesEnabled();
	}

	public Structure.IStartFactory getStartFactory() {
		return WoodlandMansionUA.Start::new;
	}

	public String getStructureName() {
		return "Woodland Mansion UA";
	}

	public int getSize() {
		return 8;
	}

	public static class Start extends StructureStart {
		public Start(Structure<?> p_i50437_1_, int p_i50437_2_, int p_i50437_3_, Biome p_i50437_4_, MutableBoundingBox p_i50437_5_, int p_i50437_6_, long p_i50437_7_) {
			super(p_i50437_1_, p_i50437_2_, p_i50437_3_, p_i50437_4_, p_i50437_5_, p_i50437_6_, p_i50437_7_);
		}

		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ, Biome biomeIn) {
			Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
			int i = 5;
			int j = 5;
			if (rotation == Rotation.CLOCKWISE_90) {
				i = -5;
			} else if (rotation == Rotation.CLOCKWISE_180) {
				i = -5;
				j = -5;
			} else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
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

			if (y >= 70) {
				BlockPos blockpos = new BlockPos(chunkX * 16 + 8, y + 1, chunkZ * 16 + 8);
				List<WoodlandMansionPieces.MansionTemplate> list = Lists.newLinkedList();
				WoodlandMansionPieces.generateMansion(templateManagerIn, blockpos, rotation, list, this.rand);
				this.components.addAll(list);
				this.recalculateStructureSize();
			}

			UltraAmplified.Logger.log(Level.DEBUG, "Woodland Mansion | " + (chunkX * 16) + " " + (chunkZ * 16));
		}

		/**
		 * Keeps iterating Structure Pieces and spawning them until the checks tell it
		 * to stop
		 */
		public void generateStructure(IWorld worldIn, Random rand, MutableBoundingBox structurebb, ChunkPos p_75068_4_) {
			super.generateStructure(worldIn, rand, structurebb, p_75068_4_);
			int i = this.bounds.minY;

			for (int j = structurebb.minX; j <= structurebb.maxX; ++j) {
				for (int k = structurebb.minZ; k <= structurebb.maxZ; ++k) {
					BlockPos blockpos = new BlockPos(j, i, k);
					if (!worldIn.isAirBlock(blockpos) && this.bounds.isVecInside(blockpos)) {
						boolean flag = false;

						for (StructurePiece structurepiece : this.components) {
							if (structurepiece.getBoundingBox().isVecInside(blockpos)) {
								flag = true;
								break;
							}
						}

						if (flag) {
							for (int l = i - 1; l > 1; --l) {
								BlockPos blockpos1 = new BlockPos(j, l, k);
								if (!worldIn.isAirBlock(blockpos1) && !worldIn.getBlockState(blockpos1).getMaterial().isLiquid()) {
									break;
								}

								worldIn.setBlockState(blockpos1, Blocks.COBBLESTONE.getDefaultState(), 2);
							}
						}
					}
				}
			}

		}
	}
}
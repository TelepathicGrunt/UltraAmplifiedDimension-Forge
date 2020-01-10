package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Random;
import java.util.function.Function;

import com.google.common.collect.Lists;
import com.mojang.datafixers.Dynamic;

import net.minecraft.entity.EntityType;
import net.minecraft.util.Rotation;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
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
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class EndCityStructureUA extends Structure<NoFeatureConfig> {
	public EndCityStructureUA(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51427_1_) {
		super(p_i51427_1_);
	}

	private static final List<Biome.SpawnListEntry> END_CITY_ENEMIES = Lists.newArrayList(
			new Biome.SpawnListEntry(EntityType.ENDERMITE, 14, 3, 5),
			new Biome.SpawnListEntry(EntityType.ENDERMAN, 7, 1, 2),
			new Biome.SpawnListEntry(EntityType.PHANTOM, 1, 1, 2));
	
	private final int citySpacing = 25;
	private final int minCitySeparation = 5;

	protected ChunkPos getStartPositionForPosition(ChunkGenerator<?> chunkGenerator, Random random, int x, int z,
			int spacingOffsetsX, int spacingOffsetsZ) {
		int i = citySpacing;
		int j = minCitySeparation;
		int k = x + i * spacingOffsetsX;
		int l = z + i * spacingOffsetsZ;
		int i1 = k < 0 ? k - i + 1 : k;
		int j1 = l < 0 ? l - i + 1 : l;
		int k1 = i1 / i;
		int l1 = j1 / i;
		((SharedSeedRandom) random).setLargeFeatureSeedWithSalt(chunkGenerator.getSeed(), k1, l1, 10387313);
		k1 = k1 * i;
		l1 = l1 * i;
		k1 = k1 + (random.nextInt(i - j) + random.nextInt(i - j)) / 2;
		l1 = l1 + (random.nextInt(i - j) + random.nextInt(i - j)) / 2;
		return new ChunkPos(k1, l1);
	}

	public boolean func_225558_a_(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX,
			int chunkPosZ, Biome biome) {
		ChunkPos chunkpos = this.getStartPositionForPosition(chunkGen, rand, chunkPosX, chunkPosZ, 0, 0);
		if (chunkPosX == chunkpos.x && chunkPosZ == chunkpos.z) {
			if (ConfigUA.endCitySpawnrate != 101 && chunkGen.hasStructure(biome, this)) {
				int i = getYPosForStructure(chunkPosX, chunkPosZ, chunkGen);
				return i >= 60;
			}
		}

		return false;
	}
	
	public Structure.IStartFactory getStartFactory() {
		return EndCityStructureUA.Start::new;
	}

	public String getStructureName() {
		return UltraAmplified.MODID + ":endcity";
	}

	public int getSize() {
		return 9;
	}

	public List<Biome.SpawnListEntry> getSpawnList() {
		return END_CITY_ENEMIES;
	}
	
	private static int getYPosForStructure(int chunkX, int chunkY, ChunkGenerator<?> generatorIn) {
		Random random = new Random((long) (chunkX + chunkY * 10387313));
		Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
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
		int l = (chunkY << 4) + 7;
		int i1 = generatorIn.func_222531_c(k, l, Heightmap.Type.WORLD_SURFACE_WG);
		int j1 = generatorIn.func_222531_c(k, l + j, Heightmap.Type.WORLD_SURFACE_WG);
		int k1 = generatorIn.func_222531_c(k + i, l, Heightmap.Type.WORLD_SURFACE_WG);
		int l1 = generatorIn.func_222531_c(k + i, l + j, Heightmap.Type.WORLD_SURFACE_WG);
		return Math.min(Math.min(Math.min(i1, j1), Math.min(k1, l1)), 106);
	}

	public static class Start extends StructureStart {
		public Start(Structure<?> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox,
				int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ,
				Biome biomeIn) {
			Rotation rotation = Rotation.values()[this.rand.nextInt(Rotation.values().length)];
			int height = EndCityStructureUA.getYPosForStructure(chunkX, chunkZ, generator);

			if (height >= 60) {
				BlockPos blockpos = new BlockPos(chunkX * 16 + 8, height, chunkZ * 16 + 8);
				EndCityPiecesUA.startHouseTower(templateManagerIn, blockpos, rotation, this.components, this.rand);
				this.recalculateStructureSize();
			}

			// UltraAmplified.LOGGER.log(Level.DEBUG, "End City | "+(chunkX*16)+"
			// "+(chunkZ*16));
		}

	}
}

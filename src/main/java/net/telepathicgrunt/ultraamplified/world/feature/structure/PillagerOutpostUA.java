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
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.FeatureUA;

public class PillagerOutpostUA extends Structure<NoFeatureConfig> {
	private static final List<Biome.SpawnListEntry> field_214558_a = Lists
			.newArrayList(new Biome.SpawnListEntry(EntityType.PILLAGER, 1, 1, 1));

	public PillagerOutpostUA(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51470_1_) {
		super(p_i51470_1_);
	}

	public String getStructureName() {
		return UltraAmplified.MODID + ":pillager_outpost";
	}

	public int getSize() {
		return 3;
	}

	public List<Biome.SpawnListEntry> getSpawnList() {
		return field_214558_a;
	}

	public boolean func_225558_a_(BiomeManager p_225558_1_, ChunkGenerator<?> chunkGen, Random rand, int chunkPosX,
			int chunkPosZ, Biome biome) {
		((SharedSeedRandom) rand).setLargeFeatureSeed(chunkGen.getSeed(), chunkPosX, chunkPosZ);

		if (ConfigUA.pillageOutpostRarity != 101 && chunkGen.hasStructure(biome, FeatureUA.PILLAGER_OUTPOST_UA)) {

			if (rand.nextFloat() < 1 / ((double) (ConfigUA.pillageOutpostRarity - 1) * 4.5D + 1)) {
				for (int k = chunkPosX - 3; k <= chunkPosX + 3; ++k) {
					for (int l = chunkPosZ - 3; l <= chunkPosZ + 3; ++l) {
						if (FeatureUA.VILLAGE_UA.func_225558_a_(p_225558_1_, chunkGen, rand, k, l, biome)) {
							return false;
						}
					}
				}

				return true;
			}
		}

		return false;
	}

	public Structure.IStartFactory getStartFactory() {
		return PillagerOutpostUA.Start::new;
	}

	protected int getSeedModifier() {
		return 165745296;
	}

	public static class Start extends MarginedStructureStart {
		public Start(Structure<?> structureIn, int chunkX, int chunkZ, MutableBoundingBox mutableBoundingBox,
				int referenceIn, long seedIn) {
			super(structureIn, chunkX, chunkZ, mutableBoundingBox, referenceIn, seedIn);
		}

		public void init(ChunkGenerator<?> generator, TemplateManager templateManagerIn, int chunkX, int chunkZ,
				Biome biomeIn) {

			int offsetX = 7;
			int offsetZ = 7;
			int x = (chunkX << 4) + offsetX;
			int z = (chunkZ << 4) + offsetZ;
			int i1 = generator.func_222531_c(x, z, Heightmap.Type.WORLD_SURFACE_WG);
			int j1 = generator.func_222531_c(x, z + 1, Heightmap.Type.WORLD_SURFACE_WG);
			int k1 = generator.func_222531_c(x + 1, z, Heightmap.Type.WORLD_SURFACE_WG);
			int l1 = generator.func_222531_c(x + 1, z + 1, Heightmap.Type.WORLD_SURFACE_WG);
			int y = Math.max(Math.max(i1, j1), Math.max(k1, l1));

			if (y >= 70 && y <= 200) {
				BlockPos blockpos = new BlockPos(chunkX * 16, y, chunkZ * 16);
				PillagerOutpostPieces.func_215139_a(generator, templateManagerIn, blockpos, this.components, this.rand);
				this.recalculateStructureSize();
				// UltraAmplified.LOGGER.log(Level.DEBUG, "Pillager Outpost | "+(x)+" "+(y+1)+"
				// "+(z));
			}
		}
	}
}
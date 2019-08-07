package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import com.google.common.collect.Lists;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.FeatureUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.ContainWaterConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.CountRangeAndTypeConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.PercentageAndFrequencyConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.NetherBridgeConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.VillagePiecesUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.VillageUAConfig;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.DepthAverageConfig;
import net.minecraft.world.gen.placement.DungeonRoomConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;

public final class DesertBiomeUA extends BiomeUA {

	public DesertBiomeUA() {
		super((new Builder()).surfaceBuilder(new ConfiguredSurfaceBuilder<>(SAND_SURFACE_BUILDER, SAND_SAND_SANDSTONE_SURFACE)).precipitation(Biome.RainType.NONE).category(Biome.Category.DESERT).depth(0.2F).scale(0.3F).temperature(2.0F).downfall(0.0F).waterColor(11260362).waterFogColor(9812925).parent((String) null));

		this.addStructure(FeatureUA.VILLAGE_UA, new VillageUAConfig(0, VillagePiecesUA.Type.SANDSTONE));
		this.addStructure(FeatureUA.MINESHAFT_UA, new MineshaftConfigUA((double) ConfigUA.mineshaftSpawnrate, MineshaftUA.Type.DESERT));
		this.addStructure(FeatureUA.STRONGHOLD_UA, IFeatureConfig.NO_FEATURE_CONFIG);
		this.addStructure(FeatureUA.FORTRESS_UA, new NetherBridgeConfigUA(false));
		this.addStructure(FeatureUA.DESERT_TEMPLE_UA, IFeatureConfig.NO_FEATURE_CONFIG);
	    this.addStructure(FeatureUA.PILLAGER_OUTPOST_UA, new PillagerOutpostConfig(1/((double)(ConfigUA.pillageOutpostRarity-1)*4.5D+1)));

		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(CAVE_CAVITY_CARVER, new ProbabilityConfig((float) (ConfigUA.caveCavitySpawnrate) / 1000)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(RAVINE_CARVER, new ProbabilityConfig((float) (ConfigUA.ravineSpawnrate) / 100)));
		this.addCarver(GenerationStage.Carving.AIR, Biome.createCarver(LONG_RAVINE_CARVER, new ProbabilityConfig((float) (ConfigUA.ravineSpawnrate) / 850)));
		this.addStructureFeaturesUA();
		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.CONTAIN_LIQUID, new ContainWaterConfig(Blocks.STONE.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.ANDESITE.getDefaultState()), Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));

		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(Feature.LAKE, new LakesConfig(Blocks.LAVA.getDefaultState()), LAKE_PLACEMENT, new CountRangeAndTypeConfig(80, CountRangeAndTypeConfig.Type.LAVA)));

		this.addFeature(GenerationStage.Decoration.LOCAL_MODIFICATIONS, Biome.createDecoratedFeature(FeatureUA.SLIME_AND_ICE_LAKE, new LakesConfig(Blocks.SLIME_BLOCK.getDefaultState()), LAKE_PLACEMENT, new CountRangeAndTypeConfig(7, CountRangeAndTypeConfig.Type.SLIME)));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, Biome.createDecoratedFeature(FeatureUA.FOSSILS_UA, IFeatureConfig.NO_FEATURE_CONFIG, RANDOM_SURFACE_BELOW_TOP_LAYER, new ChanceConfig(60)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Biome.createDecoratedFeature(Feature.DESERT_WELL, IFeatureConfig.NO_FEATURE_CONFIG, Placement.CHANCE_HEIGHTMAP, new ChanceConfig(100)));

		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Biome.createDecoratedFeature(FeatureUA.DESERT_DUNGEONS, IFeatureConfig.NO_FEATURE_CONFIG, DUNGEON_PLACEMENT, new DungeonRoomConfig(ConfigUA.dungeonSpawnrate)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIRT.getDefaultState(), 33), Placement.COUNT_RANGE, new CountRangeConfig(10, 0, 0, 175)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRAVEL.getDefaultState(), 33), Placement.COUNT_RANGE, new CountRangeConfig(8, 0, 0, 256)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GRANITE.getDefaultState(), 33), Placement.COUNT_RANGE, new CountRangeConfig(17, 0, 0, 100)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIORITE.getDefaultState(), 33), Placement.COUNT_RANGE, new CountRangeConfig(14, 0, 0, 150)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.ANDESITE.getDefaultState(), 33), Placement.COUNT_RANGE, new CountRangeConfig(20, 0, 0, 200)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.COAL_ORE.getDefaultState(), 17), Placement.COUNT_RANGE, new CountRangeConfig(ConfigUA.coalOreSpawnrate, 0, 0, 240)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.IRON_ORE.getDefaultState(), 9), Placement.COUNT_RANGE, new CountRangeConfig(ConfigUA.ironOreSpawnrate, 0, 0, 200)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 9), Placement.COUNT_RANGE, new CountRangeConfig(ConfigUA.goldOreSpawnrate, 0, 0, 50)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 8), Placement.COUNT_RANGE, new CountRangeConfig(ConfigUA.redstoneOreSpawnrate, 0, 0, 25)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.DIAMOND_ORE.getDefaultState(), 8), Placement.COUNT_RANGE, new CountRangeConfig(ConfigUA.diamondOreSpawnrate, 0, 0, 25)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 7), Placement.COUNT_DEPTH_AVERAGE, new DepthAverageConfig(ConfigUA.lapisOreSpawnrate, 20, 20)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.GOLD_ORE.getDefaultState(), 7), Placement.COUNT_RANGE, new CountRangeConfig(ConfigUA.goldOreSpawnrate, ConfigUA.seaLevel - 18, 0, 15)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.REDSTONE_ORE.getDefaultState(), 7), Placement.COUNT_RANGE, new CountRangeConfig(ConfigUA.redstoneOreSpawnrate / 2, ConfigUA.seaLevel - 15, 0, 10)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.ORE, new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, Blocks.LAPIS_ORE.getDefaultState(), 6), Placement.COUNT_DEPTH_AVERAGE, new DepthAverageConfig(ConfigUA.lapisOreSpawnrate / 2, ConfigUA.seaLevel - 10, 8)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, new SphereReplaceConfig(Blocks.SMOOTH_SANDSTONE.getDefaultState(), 7, 2, Lists.newArrayList(Blocks.STONE.getDefaultState())), CHANCE_ON_ALL_WATER_BOTTOMS_UA, new PercentageAndFrequencyConfig(0.9F, 4)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, new SphereReplaceConfig(Blocks.CLAY.getDefaultState(), 4, 1, Lists.newArrayList(Blocks.SANDSTONE.getDefaultState(), Blocks.CLAY.getDefaultState(), Blocks.STONE.getDefaultState())), CHANCE_ON_ALL_WATER_BOTTOMS_UA, new PercentageAndFrequencyConfig(0.5F, 2)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, Biome.createDecoratedFeature(Feature.DISK, new SphereReplaceConfig(Blocks.SAND.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.SANDSTONE.getDefaultState(), Blocks.STONE.getDefaultState())), CHANCE_ON_ALL_WATER_BOTTOMS_UA, new PercentageAndFrequencyConfig(1F, 2)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.DEFAULT_FLOWER, IFeatureConfig.NO_FEATURE_CONFIG, CHANCE_ON_ALL_SURFACES_UA, new PercentageAndFrequencyConfig(0.2F, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.DEAD_BUSH, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_EXTRA_UA, new AtSurfaceWithExtraConfig(5, 0.5f, 1)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(FeatureUA.BETTER_CACTUS, IFeatureConfig.NO_FEATURE_CONFIG, AT_SURFACE_WITH_EXTRA_UA, new AtSurfaceWithExtraConfig(5, 0.5f, 1)));
		DefaultBiomeFeatures.addMushrooms(this);
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.REED, IFeatureConfig.NO_FEATURE_CONFIG, RANDOM_POSITION_EVERY_5_HEIGHT, new FrequencyConfig(50)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.PUMPKIN, IFeatureConfig.NO_FEATURE_CONFIG, TWICE_SURFACE_WITH_CHANCE_UA, new ChanceConfig(32)));

		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SPRING_FEATURE, new LiquidsConfig(Fluids.WATER.getDefaultState()), Placement.COUNT_BIASED_RANGE, new CountRangeConfig(ConfigUA.waterfallSpawnrate / 34, 8, 8, 256)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SPRING_FEATURE, new LiquidsConfig(Fluids.LAVA.getDefaultState()), Placement.COUNT_VERY_BIASED_RANGE, new CountRangeConfig(ConfigUA.lavafallSpawnrate, 75, 16, 175)));
		this.addFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Biome.createDecoratedFeature(Feature.SPRING_FEATURE, new LiquidsConfig(Fluids.LAVA.getDefaultState()), Placement.COUNT_VERY_BIASED_RANGE, new CountRangeConfig(ConfigUA.lavafallSpawnrate, 8, 16, 70)));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Biome.createDecoratedFeature(Feature.FREEZE_TOP_LAYER, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
		this.addFeature(GenerationStage.Decoration.TOP_LAYER_MODIFICATION, Biome.createDecoratedFeature(Feature.FREEZE_TOP_LAYER, IFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, IPlacementConfig.NO_PLACEMENT_CONFIG));
		this.addSpawn(EntityClassification.CREATURE, new Biome.SpawnListEntry(EntityType.RABBIT, 4, 2, 3));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.SQUID, 10, 4, 4));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.PUFFERFISH, 15, 1, 3));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.TROPICAL_FISH, 25, 8, 8));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.DOLPHIN, 2, 1, 2));
		this.addSpawn(EntityClassification.WATER_CREATURE, new Biome.SpawnListEntry(EntityType.DROWNED, 5, 1, 5));
		this.addSpawn(EntityClassification.AMBIENT, new Biome.SpawnListEntry(EntityType.BAT, 10, 8, 8));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SPIDER, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SKELETON, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.CREEPER, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.SLIME, 100, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ENDERMAN, 10, 1, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.WITCH, 5, 1, 1));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE, 19, 4, 4));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.ZOMBIE_VILLAGER, 1, 1, 1));
		this.addSpawn(EntityClassification.MONSTER, new Biome.SpawnListEntry(EntityType.HUSK, 80, 4, 4));

	}
}
/*
 * import java.util.Iterator; import java.util.Random;
 * 
 * import net.TelepathicGrunt.UltraAmplified.Config.UAConfig; import
 * net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA; import
 * net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA; import
 * net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenBetterCactusUA;
 * import
 * net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenStonehenge;
 * import
 * net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenSunShrine;
 * import net.minecraft.block.BlockSandStone; import
 * net.minecraft.block.state.BlockState; import
 * net.minecraft.entity.monster.EntityHusk; import
 * net.minecraft.entity.monster.EntityZombie; import
 * net.minecraft.entity.monster.EntityZombieVillager; import
 * net.minecraft.entity.passive.EntityRabbit; import net.minecraft.block.Blocks;
 * import net.minecraft.util.math.BlockPos; import net.minecraft.world.World;
 * import net.minecraft.world.biome.Biome; import
 * net.minecraft.world.chunk.ChunkPrimer; import
 * net.minecraft.world.gen.feature.WorldGenDesertWells; import
 * net.minecraft.world.gen.feature.WorldGenFossils; import
 * net.minecraft.world.gen.feature.WorldGenerator;
 * 
 * public class BiomeDesertUA extends BiomeExtendedUA { protected static final
 * BlockState SANDSTONE_SMOOTH =
 * Blocks.SAND.getDefaultState()STONE.getDefaultState().withProperty(
 * BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH); protected static final
 * BlockState SANDSTONE_DEFAULT =
 * Blocks.SAND.getDefaultState()STONE.getDefaultState().withProperty(
 * BlockSandStone.TYPE, BlockSandStone.EnumType.DEFAULT); protected static final
 * BlockState SAND = Blocks.SAND.getDefaultState().getDefaultState();
 * 
 * private static final WorldGenerator tallCactus = new
 * WorldGenBetterCactusUA(8); private static final WorldGenerator shrine = new
 * WorldGenSunShrine(); private static final WorldGenerator stonehenge = new
 * WorldGenStonehenge(); private static final WorldGenerator fossil = new
 * WorldGenFossils();
 * 
 * public BiomeDesertUA(Biome.BiomeProperties properties) { super(properties);
 * 
 * this.decorator = new BiomeDecoratorUA();
 * 
 * //mutation form has slightly different properties if(this.isMutation()) {
 * this.topBlock = SANDSTONE_SMOOTH; this.fillerBlock = SANDSTONE_SMOOTH;
 * this.decorator.deadBushPerChunk = 30; this.decorator.cactiPerChunk = 0; }else
 * { this.topBlock = SAND; this.fillerBlock = SAND;
 * this.decorator.deadBushPerChunk = 2;
 * 
 * //This spawnrate may be a tad high... this.decorator.cactiPerChunk = 100; }
 * 
 * this.decorator.treesPerChunk = -999; this.decorator.reedsPerChunk = 50;
 * 
 * 
 * //sets mobs that spawn in this biome this.spawnableCreatureList.clear();
 * this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityRabbit.class,
 * 4, 2, 3)); Iterator<Biome.SpawnListEntry> iterator =
 * this.spawnableMonsterList.iterator();
 * 
 * while (iterator.hasNext()) { Biome.SpawnListEntry biome$spawnlistentry =
 * (Biome.SpawnListEntry)iterator.next();
 * 
 * if (biome$spawnlistentry.entityClass == EntityZombie.class ||
 * biome$spawnlistentry.entityClass == EntityZombieVillager.class) {
 * iterator.remove(); } }
 * 
 * this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityZombie.class,
 * 19, 4, 4)); this.spawnableMonsterList.add(new
 * Biome.SpawnListEntry(EntityZombieVillager.class, 1, 1, 1));
 * this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityHusk.class, 80,
 * 4, 4)); }
 * 
 * public void decorate(World worldIn, Random rand, BlockPos pos) { //needs to
 * be before stones so ores/dirt/etc does not replace stone's blocks
 * super.decorate(worldIn, rand, pos);
 * 
 * //generates fossils around every 60th chunk
 * if(UAConfig.StructuresOptions.biomeBasedStructuresOptions.
 * miniStructureGeneration &&
 * net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new
 * net.minecraft.util.math.ChunkPos(pos),
 * net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.
 * FOSSIL)) if (rand.nextInt(60) == 0) { fossil.generate(worldIn, rand, pos); }
 * 
 * 
 * if(this.isMutation()) { //generates taller cactus instead of shorter ones.
 * //The spawnrate may be a tad high...
 * if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new
 * net.minecraft.util.math.ChunkPos(pos),
 * net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.
 * CACTUS)) for (int j5 = 0; j5 < 100; ++j5) { int j19 = rand.nextInt(180) + 70;
 * tallCactus.generate(worldIn, rand, pos.add(16, j19, 16)); }
 * 
 * //generates desert wells with a higher spawnrate than the non-mutated desert
 * if(UAConfig.StructuresOptions.biomeBasedStructuresOptions.
 * miniStructureGeneration &&
 * net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new
 * net.minecraft.util.math.ChunkPos(pos),
 * net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.
 * DESERT_WELL)) if (rand.nextInt(10) == 0) { int i = rand.nextInt(16) + 8; int
 * j = rand.nextInt(16) + 8; BlockPos blockpos = worldIn.getHeight(pos.add(i, 0,
 * j)).up(); new WorldGenDesertWells().generate(worldIn, rand, blockpos); } }
 * 
 * //if height is 0.6, this is a hills variant biome and thus, sun shrine can
 * have a chance to generate if
 * (UAConfig.StructuresOptions.biomeBasedStructuresOptions.
 * miniStructureGeneration && this.getBaseHeight() == 0.6F) {
 * if(rand.nextInt(130) == 0) { int x = rand.nextInt(16) + 8; int z =
 * rand.nextInt(16) + 8; BlockPos position =
 * worldIn.getTopSolidOrLiquidBlock(pos.add(x, 0, z));
 * 
 * //attempt to generate sun shrine but the shrine code will check to make sure
 * the position is ok for it to spawn shrine.generate(worldIn, rand, position);
 * }
 * 
 * 
 * if(rand.nextInt(15) == 0) { BlockPos position =
 * worldIn.getTopSolidOrLiquidBlock(pos.add(16, 0, 16));
 * 
 * //attempt to generate sun shrine but the shrine code will check to make sure
 * the position is ok for it to spawn stonehenge.generate(worldIn, rand,
 * position); } } }
 * 
 * 
 * //If this is a mutated desert, generates smooth sandstone terrain with
 * patches of sand public void genTerrainBlocks(World worldIn, Random rand,
 * ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
 * if(this.isMutation()) { if (noiseVal > 1.80D) { this.topBlock = SAND;
 * this.fillerBlock = SANDSTONE_SMOOTH; } else if (noiseVal > -0.5D) {
 * this.topBlock = SANDSTONE_SMOOTH; this.fillerBlock = SANDSTONE_SMOOTH; } }
 * 
 * this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal); }
 * }
 */
package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeTreeFeatureConfig;
import net.minecraft.world.gen.feature.HugeTreesFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.CountConfig;
import net.minecraft.world.gen.treedecorator.BeehiveTreeDecorator;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.feature.config.BlockConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;
import net.telepathicgrunt.ultraamplified.world.feature.structure.DesertTempleStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.EndCityStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.IceSpikeTempleStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.IglooStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.JungleTempleStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MushroomTempleStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.OceanMonumentStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.OceanRuinsStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.PillagerOutpostStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.ShipwreckStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.StrongholdStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.StructureInitUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.WitchHutStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.WoodlandMansionStructureUA;


public class FeatureUA
{

	public static final HugeTreeFeatureConfig GIANT_PINE_CONFIG = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, Integer.valueOf(1))))).baseHeight(13).heightInterval(50).crownHeight(13).setSapling((net.minecraftforge.common.IPlantable) Blocks.SPRUCE_SAPLING).build();
	public static final HugeTreeFeatureConfig GIANT_SPRUCE_CONFIG = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, Integer.valueOf(1))))).baseHeight(13).heightInterval(50).crownHeight(5).setSapling((net.minecraftforge.common.IPlantable) Blocks.SPRUCE_SAPLING).build();
	public static final HugeTreeFeatureConfig GIANT_BIRCH_CONFIG = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, Integer.valueOf(1))))).baseHeight(13).heightInterval(50).crownHeight(18).setSapling((net.minecraftforge.common.IPlantable) Blocks.BIRCH_SAPLING).build();

	public static final TreeFeatureConfig OAK_TREE_WITH_MORE_BEEHIVES_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()), new BlobFoliagePlacer(2, 0))).baseHeight(4).heightRandA(2).foliageHeight(3).noVines().treeDecorators(ImmutableList.of(new BeehiveTreeDecorator(0.08F))).setSapling((net.minecraftforge.common.IPlantable) Blocks.OAK_SAPLING).build();
	public static final TreeFeatureConfig FANCY_TREE_WITH_MORE_BEEHIVES_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()), new BlobFoliagePlacer(0, 0))).treeDecorators(ImmutableList.of(new BeehiveTreeDecorator(0.08F))).setSapling((net.minecraftforge.common.IPlantable) Blocks.OAK_SAPLING).build();
	public static final TreeFeatureConfig OAK_TREE_WITH_BEEHIVES_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()), new BlobFoliagePlacer(2, 0))).baseHeight(4).heightRandA(2).foliageHeight(3).noVines().treeDecorators(ImmutableList.of(new BeehiveTreeDecorator(0.02F))).setSapling((net.minecraftforge.common.IPlantable) Blocks.OAK_SAPLING).build();
	public static final TreeFeatureConfig FANCY_TREE_WITH_BEEHIVES_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()), new BlobFoliagePlacer(0, 0))).treeDecorators(ImmutableList.of(new BeehiveTreeDecorator(0.02F))).setSapling((net.minecraftforge.common.IPlantable) Blocks.OAK_SAPLING).build();
	public static final TreeFeatureConfig BIRCH_TREE_WITH_BEEHIVES_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()), new BlobFoliagePlacer(2, 0))).baseHeight(5).heightRandA(2).foliageHeight(3).noVines().treeDecorators(ImmutableList.of(new BeehiveTreeDecorator(0.02F))).setSapling((net.minecraftforge.common.IPlantable) Blocks.BIRCH_SAPLING).build();


	public static <T extends BaseTreeFeatureConfig> T getConfiguredForUndergroundTreeConfig(T treeConfig)
	{
		treeConfig.func_227373_a_();
		return treeConfig;
	}


	public static <T extends BlockClusterFeatureConfig> T getConfiguredClusterConfig(T clusterConfig)
	{
		clusterConfig.project = false;
		return clusterConfig;
	}

	public static Feature<NoFeatureConfig> BETTER_CACTUS;
	public static Feature<NoFeatureConfig> GENERIC_DUNGEONS;
	public static Feature<NoFeatureConfig> MESA_DUNGEONS;
	public static Feature<NoFeatureConfig> DARK_FOREST_DUNGEONS;
	public static Feature<NoFeatureConfig> DESERT_DUNGEONS;
	public static Feature<NoFeatureConfig> END_DUNGEONS;
	public static Feature<NoFeatureConfig> NETHER_DUNGEONS;
	public static Feature<NoFeatureConfig> SNOW_DUNGEONS;
	public static Feature<NoFeatureConfig> SWAMP_DUNGEONS;
	public static Feature<NoFeatureConfig> MUSHROOM_DUNGEONS;
	public static Feature<NoFeatureConfig> JUNGLE_DUNGEONS;

	public static Feature<NoFeatureConfig> CROSS;
	public static Feature<NoFeatureConfig> HAY_BALE;
	public static Feature<NoFeatureConfig> TINY_HAY_BALE;
	public static Feature<NoFeatureConfig> STONEHENGE;
	public static Feature<NoFeatureConfig> SUN_SHRINE;
	public static Feature<NoFeatureConfig> HANGING_RUINS;
	public static Feature<NoFeatureConfig> FOSSILS_UA;
	public static Feature<BlockConfig> SINGLE_BLOCK;
	public static Feature<BlockStateFeatureConfig> ICEBERG_UA;
	public static Feature<NoFeatureConfig> MARKED_TREASURE_CHEST_UA;
	public static Feature<BlockBlobConfig> LARGE_STACKABLE_BOULDER;
	public static Feature<BlockBlobConfig> LARGE_BOULDER;
	public static Feature<BlockBlobConfig> MEDIUM_BOULDER;
	public static Feature<BlockBlobConfig> SMALL_BOULDER;

	public static Feature<BlockStateFeatureConfig> STONELESS_LAKE;
	public static Feature<BlockStateFeatureConfig> SHALLOW_LAKE;
	public static Feature<NoFeatureConfig> CONTAIN_LIQUID;
	public static Feature<NoFeatureConfig> CONTAIN_UNDERGROUND_LIQUID;
	public static Feature<NoFeatureConfig> ICE_PATCH_SANDY;
	public static Feature<NoFeatureConfig> GIANT_ICE_SPIKE;
	public static Feature<SphereReplaceConfig> DISK_DRY;
	public static Feature<NoFeatureConfig> BLUE_ICE_WATERFALL;
	public static Feature<NoFeatureConfig> SNOW_AND_ICE_LAYERER;
	public static Feature<ColumnBlocksConfig> COLUMN;
	public static Feature<ColumnBlocksConfig> RAMP;
	public static Feature<CountConfig> GLOWPATCH;

	public static Feature<NoFeatureConfig> LONG_VINES;
	public static Feature<NoFeatureConfig> SHORT_VINES;
	public static Feature<SeaGrassConfig> SEA_GRASS_UA;
	public static Feature<NoFeatureConfig> KELP_UA;
	public static Feature<CountConfig> SEA_PICKLE_UA;
	public static Feature<NoFeatureConfig> CORAL_CLAW_UA;
	public static Feature<NoFeatureConfig> CORAL_TREE_UA;
	public static Feature<NoFeatureConfig> CORAL_MUSHROOM_UA;
	public static Feature<NoFeatureConfig> BAMBOO_UA;
	public static Feature<BlockConfig> ROOTS;
	public static Feature<NoFeatureConfig> NETHER_UNDERWATER_MAGMA;
	public static Feature<NoFeatureConfig> RARE_LAVA;

	public static AbstractTreeFeature<TreeFeatureConfig> HORNED_SWAMP_TREE;
	public static HugeTreesFeature<HugeTreeFeatureConfig> GIANT_BIRCH_TREE;
	public static HugeTreesFeature<HugeTreeFeatureConfig> GIANT_PINE_TREE_1_UA;
	public static HugeTreesFeature<HugeTreeFeatureConfig> GIANT_PINE_TREE_2_UA;
	public static AbstractTreeFeature<HugeTreeFeatureConfig> GIANT_DARK_FOREST_TREE;
	public static AbstractTreeFeature<TreeFeatureConfig> PODZOL_TAIGA_TREE;
	public static AbstractTreeFeature<TreeFeatureConfig> END_TREE;
	public static Feature<BaseTreeFeatureConfig> JUNGLE_BUSH_UA;

	public static Structure<MineshaftConfigUA> MINESHAFT_UA;
	public static Structure<NoFeatureConfig> WOODLAND_MANSION_UA;
	public static Structure<NoFeatureConfig> JUNGLE_TEMPLE_UA;
	public static Structure<NoFeatureConfig> DESERT_TEMPLE_UA;
	public static Structure<NoFeatureConfig> IGLOO_UA;
	public static Structure<ShipwreckConfig> SHIPWRECK_UA;
	public static WitchHutStructureUA WITCH_HUT_UA;
	public static Structure<NoFeatureConfig> STRONGHOLD_UA;
	public static Structure<NoFeatureConfig> OCEAN_MONUMENT_UA;
	public static Structure<OceanRuinConfig> OCEAN_RUIN_UA;
	public static Structure<FortressConfigUA> FORTRESS_UA;
	public static Structure<NoFeatureConfig> END_CITY_UA;
	public static Structure<VillageConfigUA> VILLAGE_UA;
	public static Structure<NoFeatureConfig> PILLAGER_OUTPOST_UA;
	public static Structure<NoFeatureConfig> MUSHROOM_TEMPLE_UA;
	public static Structure<NoFeatureConfig> ICE_SPIKE_TEMPLE_UA;

	//for making the terrain bases for these structure
	public static final List<Structure<?>> ILLAGER_STRUCTURES = ImmutableList.of(PILLAGER_OUTPOST_UA, VILLAGE_UA);


	@SuppressWarnings("unchecked")
	public static void registerFeatures(RegistryEvent.Register<Feature<?>> event)
	{
		IForgeRegistry<Feature<?>> registry = event.getRegistry();

		UltraAmplified.LOGGER.debug("FEATURE REGISTER");

		BETTER_CACTUS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new BetterCactus(NoFeatureConfig::deserialize), "better_cactus_ua");
		GENERIC_DUNGEONS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new DungeonDefault(NoFeatureConfig::deserialize), "dungeon_default_ua");
		MESA_DUNGEONS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new DungeonBadlands(NoFeatureConfig::deserialize), "dungeons_badlands_ua");
		DARK_FOREST_DUNGEONS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new DungeonDarkForest(NoFeatureConfig::deserialize), "dungeons_dark_forest_ua");
		DESERT_DUNGEONS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new DungeonDesert(NoFeatureConfig::deserialize), "dungeons_desert_ua");
		END_DUNGEONS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new DungeonEnd(NoFeatureConfig::deserialize), "dungeons_end_ua");
		NETHER_DUNGEONS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new DungeonNether(NoFeatureConfig::deserialize), "dungeons_nether_ua");
		SNOW_DUNGEONS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new DungeonSnow(NoFeatureConfig::deserialize), "dungeons_snow_ua");
		SWAMP_DUNGEONS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new DungeonSwamp(NoFeatureConfig::deserialize), "dungeons_swamp_ua");
		MUSHROOM_DUNGEONS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new DungeonMushroom(NoFeatureConfig::deserialize), "dungeons_mushroom_ua");
		JUNGLE_DUNGEONS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new DungeonJungle(NoFeatureConfig::deserialize), "dungeons_jungle_ua");
		
		CROSS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new SwampCross(NoFeatureConfig::deserialize), "swamp_cross_ua");
		HAY_BALE = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new HayBalePile(NoFeatureConfig::deserialize), "hay_bale_pile_ua");
		TINY_HAY_BALE = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new HayBaleTinyPile(NoFeatureConfig::deserialize), "hay_bale_tiny_pile_ua");
		STONEHENGE = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new Stonehenge(NoFeatureConfig::deserialize), "stonehenge_ua");
		SUN_SHRINE = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new SunShrine(NoFeatureConfig::deserialize), "sun_shrine_ua");
		HANGING_RUINS = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new HangingRuins(NoFeatureConfig::deserialize), "hanging_ruins_ua");
		FOSSILS_UA = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new FossilUA(NoFeatureConfig::deserialize), "fossil_ua");
		SINGLE_BLOCK = (Feature<BlockConfig>) UltraAmplified.register(registry, new SingleBlock(BlockConfig::deserialize), "single_block_ua");
		ICEBERG_UA = (Feature<BlockStateFeatureConfig>) UltraAmplified.register(registry, new IcebergUA(BlockStateFeatureConfig::deserialize), "iceberg_ua");
		MARKED_TREASURE_CHEST_UA = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new MarkedTreasureChest(NoFeatureConfig::deserialize), "marked_treasure_chest_ua");
		LARGE_STACKABLE_BOULDER = (Feature<BlockBlobConfig>) UltraAmplified.register(registry, new BoulderGiantStackable(BlockBlobConfig::deserialize), "giant_stackable_boulder_ua");
		LARGE_BOULDER = (Feature<BlockBlobConfig>) UltraAmplified.register(registry, new BoulderGiant(BlockBlobConfig::deserialize), "boulder_giant_ua");
		MEDIUM_BOULDER = (Feature<BlockBlobConfig>) UltraAmplified.register(registry, new BoulderNormal(BlockBlobConfig::deserialize), "boulder_normal_ua");
		SMALL_BOULDER = (Feature<BlockBlobConfig>) UltraAmplified.register(registry, new BoulderTiny(BlockBlobConfig::deserialize), "boulder_tiny_ua");
		
		STONELESS_LAKE = (Feature<BlockStateFeatureConfig>) UltraAmplified.register(registry, new LakeStoneless(BlockStateFeatureConfig::deserialize), "stoneless_lakes_ua");
		SHALLOW_LAKE = (Feature<BlockStateFeatureConfig>) UltraAmplified.register(registry, new LakeWideShallow(BlockStateFeatureConfig::deserialize), "wide_shallow_lakes_ua");
		CONTAIN_LIQUID = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new ContainLiquidForOceans(NoFeatureConfig::deserialize), "contain_water_ua");
		ICE_PATCH_SANDY = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new ContainUndergroundLiquids(NoFeatureConfig::deserialize), "ice_patch_ua");
		GIANT_ICE_SPIKE = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new IcePatchUA(NoFeatureConfig::deserialize), "ice_spike_ua");
		DISK_DRY = (Feature<SphereReplaceConfig>) UltraAmplified.register(registry, new IceSpikeUA(NoFeatureConfig::deserialize), "disk_dry_ua");
		COLUMN = (Feature<ColumnBlocksConfig>) UltraAmplified.register(registry, new SphereReplaceDry(SphereReplaceConfig::deserialize), "column_ua");
		RAMP = (Feature<ColumnBlocksConfig>) UltraAmplified.register(registry, new BlueIceWaterfall(NoFeatureConfig::deserialize), "ramp_column_ua");
		GLOWPATCH = (Feature<CountConfig>) UltraAmplified.register(registry, new SnowIceLayerHandlerFeature(NoFeatureConfig::deserialize), "glowpatch_ua");
		BLUE_ICE_WATERFALL = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new ColumnVertical(ColumnBlocksConfig::deserialize), "blue_ice_waterfall_ua");
		SNOW_AND_ICE_LAYERER = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new ColumnRamp(ColumnBlocksConfig::deserialize), "snow_and_ice_layerer_ua");
		
		LONG_VINES = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new GlowPatch(CountConfig::deserialize), "vines_long_ua");
		SHORT_VINES = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new VinesLong(NoFeatureConfig::deserialize), "vines_short_ua");
		SEA_GRASS_UA = (Feature<SeaGrassConfig>) UltraAmplified.register(registry, new VinesShort(NoFeatureConfig::deserialize), "sea_grass_ua");
		KELP_UA = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new SeaGrassUA(SeaGrassConfig::deserialize), "kelp_ua");
		SEA_PICKLE_UA = (Feature<CountConfig>) UltraAmplified.register(registry, new KelpUA(NoFeatureConfig::deserialize), "sea_pickle_ua");
		CORAL_CLAW_UA = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new SeaPickleUA(CountConfig::deserialize), "coral_claw_ua");
		CORAL_TREE_UA = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new CoralClawUA(NoFeatureConfig::deserialize), "coral_tree_ua");
		CORAL_MUSHROOM_UA = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new CoralMushroomUA(NoFeatureConfig::deserialize), "coral_mushroom_ua");
		BAMBOO_UA = (Feature<NoFeatureConfig>) UltraAmplified.register(registry, new CoralTreeUA(NoFeatureConfig::deserialize), "bamboo_ua");
		ROOTS = (Feature<BlockConfig>) UltraAmplified.register(registry, new Bamboo(NoFeatureConfig::deserialize), "roots_ua");
		
		HORNED_SWAMP_TREE = (AbstractTreeFeature<TreeFeatureConfig>) UltraAmplified.register(registry, new Roots(BlockConfig::deserialize), "horned_swamp_tree_ua");
		GIANT_BIRCH_TREE = (HugeTreesFeature<HugeTreeFeatureConfig>) UltraAmplified.register(registry, new NetherUnderwaterMagma(NoFeatureConfig::deserialize), "giant_birch_tree_ua");
		GIANT_PINE_TREE_1_UA = (HugeTreesFeature<HugeTreeFeatureConfig>) UltraAmplified.register(registry, new NetherSurfaceLavaRare(NoFeatureConfig::deserialize), "giant_pine_1_tree_ua");
		GIANT_PINE_TREE_2_UA = (HugeTreesFeature<HugeTreeFeatureConfig>) UltraAmplified.register(registry, new TreeSwampHorned(TreeFeatureConfig::deserialize), "giant_pine_2_tree_ua");
		GIANT_DARK_FOREST_TREE = (AbstractTreeFeature<HugeTreeFeatureConfig>) UltraAmplified.register(registry, new TreeGiantBirch(HugeTreeFeatureConfig::deserializeDarkOak), "giant_dark_oak_tree_ua");
		PODZOL_TAIGA_TREE = (AbstractTreeFeature<TreeFeatureConfig>) UltraAmplified.register(registry, new TreeGiantPine(HugeTreeFeatureConfig::deserializeSpruce, false), "podzol_taiga_tree_ua");
		END_TREE = (AbstractTreeFeature<TreeFeatureConfig>) UltraAmplified.register(registry, new TreeGiantPine(HugeTreeFeatureConfig::deserializeSpruce, true), "end_tree_ua");
		JUNGLE_BUSH_UA = (Feature<BaseTreeFeatureConfig>) UltraAmplified.register(registry, new TreeJungleShrub(BaseTreeFeatureConfig::deserializeJungle, Blocks.JUNGLE_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, Integer.valueOf(1))), "jungle_bush_ua");
		
		MINESHAFT_UA = (Structure<MineshaftConfigUA>) UltraAmplified.register(registry, new MineshaftStructureUA(MineshaftConfigUA::deserialize), "mineshaft");
		WOODLAND_MANSION_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, new WoodlandMansionStructureUA(NoFeatureConfig::deserialize), "woodland_mansion");
		JUNGLE_TEMPLE_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, new JungleTempleStructureUA(NoFeatureConfig::deserialize), "jungle_temple");
		DESERT_TEMPLE_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, new DesertTempleStructureUA(NoFeatureConfig::deserialize), "desert_temple");
		IGLOO_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, new IglooStructureUA(NoFeatureConfig::deserialize), "igloo");
		SHIPWRECK_UA = (Structure<ShipwreckConfig>) UltraAmplified.register(registry, new ShipwreckStructureUA(ShipwreckConfig::deserialize), "shipwreck");
		WITCH_HUT_UA = (WitchHutStructureUA) UltraAmplified.register(registry, new WitchHutStructureUA(NoFeatureConfig::deserialize), "witch_hut");
		STRONGHOLD_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, new StrongholdStructureUA(NoFeatureConfig::deserialize), "stronghold");
		OCEAN_MONUMENT_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, new OceanMonumentStructureUA(NoFeatureConfig::deserialize), "ocean_monument");
		OCEAN_RUIN_UA = (Structure<OceanRuinConfig>) UltraAmplified.register(registry, new OceanRuinsStructureUA(OceanRuinConfig::deserialize), "ocean_ruins");
		FORTRESS_UA = (Structure<FortressConfigUA>) UltraAmplified.register(registry, new FortressStructureUA(FortressConfigUA::deserialize), "fortress");
		END_CITY_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, new EndCityStructureUA(NoFeatureConfig::deserialize), "endcity");
		VILLAGE_UA = (Structure<VillageConfigUA>) UltraAmplified.register(registry, new VillageStructureUA(VillageConfigUA::deserialize), "village");
		PILLAGER_OUTPOST_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, new PillagerOutpostStructureUA(NoFeatureConfig::deserialize), "pillager_outpost");
		MUSHROOM_TEMPLE_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, new MushroomTempleStructureUA(NoFeatureConfig::deserialize), "mushroom_temple");
		ICE_SPIKE_TEMPLE_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, new IceSpikeTempleStructureUA(NoFeatureConfig::deserialize), "ice_spike_temple");

		//registers the structure pieces.
		StructureInitUA.registerStructurePieces();
	}
}

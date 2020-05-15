package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.entity.EntityType;
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
import net.minecraft.world.gen.feature.OreFeatureConfig;
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
import net.telepathicgrunt.ultraamplified.utils.RegUtil;
import net.telepathicgrunt.ultraamplified.world.feature.config.BlockConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;
import net.telepathicgrunt.ultraamplified.world.feature.structure.DesertTempleStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.EndCityStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.IceSpikeTempleStructure;
import net.telepathicgrunt.ultraamplified.world.feature.structure.IglooStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.JungleTempleStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MushroomTempleStructure;
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

public class UAFeatures {

    public static final HugeTreeFeatureConfig GIANT_PINE_CONFIG = 
	    (new HugeTreeFeatureConfig.Builder(
		    new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
		    new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()
		    					.with(LeavesBlock.DISTANCE, Integer.valueOf(1)))))
			    			.baseHeight(13)
			    			.heightInterval(50)
			    			.crownHeight(13)
			    			.setSapling((net.minecraftforge.common.IPlantable) Blocks.SPRUCE_SAPLING).build();
    
    public static final HugeTreeFeatureConfig GIANT_SPRUCE_CONFIG = 
	    (new HugeTreeFeatureConfig.Builder(
		    new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
		    new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()
			    				.with(LeavesBlock.DISTANCE, Integer.valueOf(1)))))
	    					.baseHeight(13)
	    					.heightInterval(50)
	    					.crownHeight(5)
	    					.setSapling((net.minecraftforge.common.IPlantable) Blocks.SPRUCE_SAPLING).build();
    
    public static final HugeTreeFeatureConfig GIANT_BIRCH_CONFIG = 
	    (new HugeTreeFeatureConfig.Builder(
		    new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()),
		    new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()
			    				.with(LeavesBlock.DISTANCE, Integer.valueOf(1)))))
			    			.baseHeight(13)
			    			.heightInterval(50)
			    			.crownHeight(18)
			    			.setSapling((net.minecraftforge.common.IPlantable) Blocks.BIRCH_SAPLING).build();

    public static final TreeFeatureConfig OAK_TREE_WITH_MORE_BEEHIVES_CONFIG = 
	    (new TreeFeatureConfig.Builder(
		    new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
		    new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()
			    				.with(LeavesBlock.DISTANCE, Integer.valueOf(1))), 
		    					new BlobFoliagePlacer(2, 0)))
		    				.baseHeight(4)
		    				.heightRandA(2)
		    				.foliageHeight(3)
		    				.ignoreVines()
		    				.decorators(ImmutableList.of(new BeehiveTreeDecorator(0.08F)))
		    				.setSapling((net.minecraftforge.common.IPlantable) Blocks.OAK_SAPLING).build();
    
    public static final TreeFeatureConfig FANCY_TREE_WITH_MORE_BEEHIVES_CONFIG = 
	    (new TreeFeatureConfig.Builder(
		    new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
		    new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()
			    				.with(LeavesBlock.DISTANCE, Integer.valueOf(1))), 
		    					new BlobFoliagePlacer(0, 0)))
		    				.decorators(ImmutableList.of(new BeehiveTreeDecorator(0.08F)))
		    				.setSapling((net.minecraftforge.common.IPlantable) Blocks.OAK_SAPLING).build();
    
    public static final TreeFeatureConfig OAK_TREE_WITH_BEEHIVES_CONFIG = 
	    (new TreeFeatureConfig.Builder(
		    new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
		    new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()
			    				.with(LeavesBlock.DISTANCE, Integer.valueOf(1))), 
		    					new BlobFoliagePlacer(2, 0)))
		    				.baseHeight(4)
		    				.heightRandA(2)
		    				.foliageHeight(3)
		    				.ignoreVines()
		    				.decorators(ImmutableList.of(new BeehiveTreeDecorator(0.006F)))
		    				.setSapling((net.minecraftforge.common.IPlantable) Blocks.OAK_SAPLING).build();
    
    public static final TreeFeatureConfig FANCY_TREE_WITH_BEEHIVES_CONFIG = 
	    (new TreeFeatureConfig.Builder(
		    new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()),
		    new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()
			    				.with(LeavesBlock.DISTANCE, Integer.valueOf(1))), 
		    					new BlobFoliagePlacer(0, 0)))
		    				.decorators(ImmutableList.of(new BeehiveTreeDecorator(0.006F)))
		    				.setSapling((net.minecraftforge.common.IPlantable) Blocks.OAK_SAPLING).build();
    
    public static final TreeFeatureConfig BIRCH_TREE_WITH_BEEHIVES_CONFIG = 
	    (new TreeFeatureConfig.Builder(
		    new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()),
		    new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()
			    				.with(LeavesBlock.DISTANCE, Integer.valueOf(1))), 
		    					new BlobFoliagePlacer(2, 0)))
		    				.baseHeight(5)
		    				.heightRandA(2)
		    				.foliageHeight(3)
		    				.ignoreVines()
		    				.decorators(ImmutableList.of(new BeehiveTreeDecorator(0.006F)))
		    				.setSapling((net.minecraftforge.common.IPlantable) Blocks.BIRCH_SAPLING).build();

    public static <T extends BaseTreeFeatureConfig> T getConfiguredForUndergroundTreeConfig(T treeConfig) {
	treeConfig.forcePlacement();
	return treeConfig;
    }

    public static <T extends BlockClusterFeatureConfig> T getConfiguredClusterConfig(T clusterConfig) {
	clusterConfig.field_227298_k_ = false;
	return clusterConfig;
    }

    public static Feature<NoFeatureConfig> BETTER_CACTUS 	= new BetterCactus(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> GENERIC_DUNGEONS 	= new DungeonDefault(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> MESA_DUNGEONS 	= new DungeonBadlands(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> DARK_FOREST_DUNGEONS	= new DungeonDarkForest(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> DESERT_DUNGEONS 	= new DungeonDesert(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> END_DUNGEONS 	= new DungeonEnd(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> NETHER_DUNGEONS 	= new DungeonNether(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SNOW_DUNGEONS 	= new DungeonSnow(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SWAMP_DUNGEONS 	= new DungeonSwamp(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> MUSHROOM_DUNGEONS 	= new DungeonMushroom(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> JUNGLE_DUNGEONS 	= new DungeonJungle(NoFeatureConfig::deserialize);

    public static Feature<NoFeatureConfig> CROSS 		= new SwampCross(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> HAY_BALE 		= new HayBalePile(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> TINY_HAY_BALE 	= new HayBaleTinyPile(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> STONEHENGE 		= new Stonehenge(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SUN_SHRINE 		= new SunShrine(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> HANGING_RUINS 	= new HangingRuins(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> FOSSILS 		= new Fossil(NoFeatureConfig::deserialize);
    public static Feature<BlockConfig> SINGLE_BLOCK 		= new SingleBlock(BlockConfig::deserialize);
    public static Feature<BlockStateFeatureConfig> ICEBERG 	= new Iceberg(BlockStateFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> MARKED_TREASURE_CHEST = new MarkedTreasureChest(NoFeatureConfig::deserialize);

    public static Feature<BlockBlobConfig> LARGE_STACKABLE_BOULDER 	= new BoulderGiantStackable(BlockBlobConfig::deserialize);
    public static Feature<BlockBlobConfig> LARGE_BOULDER 		= new BoulderGiant(BlockBlobConfig::deserialize);
    public static Feature<BlockBlobConfig> MEDIUM_BOULDER 		= new BoulderNormal(BlockBlobConfig::deserialize);
    public static Feature<BlockBlobConfig> SMALL_BOULDER 		= new BoulderTiny(BlockBlobConfig::deserialize);

    public static Feature<BlockStateFeatureConfig> STONED_LAKE 		= new LakeStoned(BlockStateFeatureConfig::deserialize);
    public static Feature<BlockStateFeatureConfig> STONELESS_LAKE 	= new LakeStoneless(BlockStateFeatureConfig::deserialize);
    public static Feature<BlockStateFeatureConfig> SHALLOW_LAKE 	= new LakeWideShallow(BlockStateFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> CONTAIN_OCEAN_LIQUID 	= new ContainLiquidForOceans(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> CONTAIN_UNDERGROUND_LIQUID 	= new ContainUndergroundLiquids(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> ICE_PATCH_SANDY 		= new IcePatch(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> GIANT_ICE_SPIKE 		= new IceSpike(NoFeatureConfig::deserialize);
    public static Feature<SphereReplaceConfig> DISK_DRY 		= new SphereReplaceDry(SphereReplaceConfig::deserialize);
    public static Feature<NoFeatureConfig> BLUE_ICE_WATERFALL 		= new BlueIceWaterfall(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SNOW_AND_ICE_LAYERER 	= new SnowIceLayerHandlerFeature(NoFeatureConfig::deserialize);
    public static Feature<ColumnBlocksConfig> COLUMN 			= new ColumnVertical(ColumnBlocksConfig::deserialize);
    public static Feature<ColumnBlocksConfig> RAMP 			= new ColumnRamp(ColumnBlocksConfig::deserialize);
    public static Feature<CountConfig> GLOWPATCH 			= new GlowPatch(CountConfig::deserialize);
    public static Feature<OreFeatureConfig> UNDERGROUND_POCKET 		= new UndergroundPocket(OreFeatureConfig::deserialize);

    public static Feature<NoFeatureConfig> LONG_VINES 		= new VinesLong(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SHORT_VINES 		= new VinesShort(NoFeatureConfig::deserialize);
    public static Feature<SeaGrassConfig> SEA_GRASS 		= new SeaGrass(SeaGrassConfig::deserialize);
    public static Feature<NoFeatureConfig> KELP 		= new Kelp(NoFeatureConfig::deserialize);
    public static Feature<CountConfig> SEA_PICKLE 		= new SeaPickle(CountConfig::deserialize);
    public static Feature<NoFeatureConfig> CORAL_CLAW 		= new CoralClaw(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> CORAL_TREE 		= new CoralMushroom(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> CORAL_MUSHROOM 	= new CoralTree(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> BAMBOO 		= new Bamboo(NoFeatureConfig::deserialize);
    public static Feature<BlockConfig> ROOTS 			= new Roots(BlockConfig::deserialize);

    public static Feature<NoFeatureConfig> NETHER_UNDERWATER_MAGMA 	= new NetherUnderwaterMagma(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> RARE_LAVA 			= new NetherSurfaceLavaRare(NoFeatureConfig::deserialize);

    public static AbstractTreeFeature<TreeFeatureConfig> HORNED_SWAMP_TREE 		= new TreeSwampHorned(TreeFeatureConfig::func_227338_a_);
    public static HugeTreesFeature<HugeTreeFeatureConfig> GIANT_BIRCH_TREE 		= new TreeGiantBirch(HugeTreeFeatureConfig::deserializeDarkOak); // may need to fix deserialize
    public static HugeTreesFeature<HugeTreeFeatureConfig> GIANT_PINE_TREE_1 		= new TreeGiantPine(HugeTreeFeatureConfig::deserializeSpruce, false);
    public static HugeTreesFeature<HugeTreeFeatureConfig> GIANT_PINE_TREE_2 		= new TreeGiantPine(HugeTreeFeatureConfig::deserializeSpruce, true);
    public static AbstractTreeFeature<HugeTreeFeatureConfig> GIANT_DARK_FOREST_TREE 	= new TreeGiantDarkOak(HugeTreeFeatureConfig::deserializeDarkOak);
    public static AbstractTreeFeature<TreeFeatureConfig> PODZOL_TAIGA_TREE 		= new TreePodzolTaiga(TreeFeatureConfig::func_227338_a_);
    public static AbstractTreeFeature<TreeFeatureConfig> END_TREE 			= new TreeEnd(TreeFeatureConfig::func_227338_a_);
    public static Feature<BaseTreeFeatureConfig> JUNGLE_BUSH 				= new TreeJungleShrub(BaseTreeFeatureConfig::deserializeJungle, 
	    											Blocks.JUNGLE_LOG.getDefaultState(), 
	    											Blocks.OAK_LEAVES.getDefaultState()
	    												.with(LeavesBlock.DISTANCE, Integer.valueOf(1)));

    public static Structure<MineshaftConfigUA> MINESHAFT 	= new MineshaftStructureUA(MineshaftConfigUA::deserialize);
    public static Structure<NoFeatureConfig> WOODLAND_MANSION 	= new WoodlandMansionStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> JUNGLE_TEMPLE 	= new JungleTempleStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> DESERT_TEMPLE 	= new DesertTempleStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> IGLOO 		= new IglooStructureUA(NoFeatureConfig::deserialize);
    public static Structure<ShipwreckConfig> SHIPWRECK 		= new ShipwreckStructureUA(ShipwreckConfig::deserialize);
    public static WitchHutStructureUA WITCH_HUT 		= new WitchHutStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> STRONGHOLD 	= new StrongholdStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> OCEAN_MONUMENT 	= new OceanMonumentStructureUA(NoFeatureConfig::deserialize);
    public static Structure<OceanRuinConfig> OCEAN_RUIN 	= new OceanRuinsStructureUA(OceanRuinConfig::deserialize);
    public static Structure<FortressConfigUA> FORTRESS 		= new FortressStructureUA(FortressConfigUA::deserialize);
    public static Structure<NoFeatureConfig> END_CITY 		= new EndCityStructureUA(NoFeatureConfig::deserialize);
    public static Structure<VillageConfigUA> VILLAGE 		= new VillageStructureUA(VillageConfigUA::deserialize);
    public static Structure<NoFeatureConfig> PILLAGER_OUTPOST 	= new PillagerOutpostStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> MUSHROOM_TEMPLE 	= new MushroomTempleStructure(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> ICE_SPIKE_TEMPLE 	= new IceSpikeTempleStructure(NoFeatureConfig::deserialize);

    // for making the terrain bases for these structure
    public static final List<Structure<?>> ILLAGER_STRUCTURES = ImmutableList.of(PILLAGER_OUTPOST, VILLAGE);

    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
	IForgeRegistry<Feature<?>> registry = event.getRegistry();

	RegUtil.register(registry, BETTER_CACTUS, "better_cactus");
	RegUtil.register(registry, GENERIC_DUNGEONS, "dungeon_default");
	RegUtil.register(registry, MESA_DUNGEONS, "dungeons_badlands");
	RegUtil.register(registry, DARK_FOREST_DUNGEONS, "dungeons_dark_forest");
	RegUtil.register(registry, DESERT_DUNGEONS, "dungeons_desert");
	RegUtil.register(registry, END_DUNGEONS, "dungeons_end");
	RegUtil.register(registry, NETHER_DUNGEONS, "dungeons_nether");
	RegUtil.register(registry, SNOW_DUNGEONS, "dungeons_snow");
	RegUtil.register(registry, SWAMP_DUNGEONS, "dungeons_swamp");
	RegUtil.register(registry, MUSHROOM_DUNGEONS, "dungeons_mushroom");
	RegUtil.register(registry, JUNGLE_DUNGEONS, "dungeons_jungle");
	RegUtil.register(registry, CROSS, "swamp_cross");
	RegUtil.register(registry, HAY_BALE, "hay_bale_pile");
	RegUtil.register(registry, TINY_HAY_BALE, "hay_bale_tiny_pile");
	RegUtil.register(registry, STONEHENGE, "stonehenge");
	RegUtil.register(registry, SUN_SHRINE, "sun_shrine");
	RegUtil.register(registry, HANGING_RUINS, "hanging_ruins");
	RegUtil.register(registry, FOSSILS, "fossil");
	RegUtil.register(registry, SINGLE_BLOCK, "single_block");
	RegUtil.register(registry, ICEBERG, "iceberg");
	RegUtil.register(registry, MARKED_TREASURE_CHEST, "marked_treasure_chest");
	RegUtil.register(registry, LARGE_STACKABLE_BOULDER, "giant_stackable_boulder");
	RegUtil.register(registry, LARGE_BOULDER, "boulder_giant");
	RegUtil.register(registry, MEDIUM_BOULDER, "boulder_normal");
	RegUtil.register(registry, SMALL_BOULDER, "boulder_tiny");
	RegUtil.register(registry, STONED_LAKE, "stoned_lakes");
	RegUtil.register(registry, STONELESS_LAKE, "stoneless_lakes");
	RegUtil.register(registry, SHALLOW_LAKE, "wide_shallow_lakes");
	RegUtil.register(registry, CONTAIN_OCEAN_LIQUID, "contain_water");
	RegUtil.register(registry, CONTAIN_UNDERGROUND_LIQUID, "contain_underground_liquid");
	RegUtil.register(registry, ICE_PATCH_SANDY, "ice_patch");
	RegUtil.register(registry, GIANT_ICE_SPIKE, "ice_spike");
	RegUtil.register(registry, DISK_DRY, "disk_dry");
	RegUtil.register(registry, COLUMN, "column");
	RegUtil.register(registry, RAMP, "ramp_column");
	RegUtil.register(registry, GLOWPATCH, "glowpatch");
	RegUtil.register(registry, UNDERGROUND_POCKET, "underground_pocket");
	RegUtil.register(registry, BLUE_ICE_WATERFALL, "blue_ice_waterfall");
	RegUtil.register(registry, SNOW_AND_ICE_LAYERER, "snow_and_ice_layerer");
	RegUtil.register(registry, LONG_VINES, "vines_long");
	RegUtil.register(registry, SHORT_VINES, "vines_short");
	RegUtil.register(registry, SEA_GRASS, "sea_grass");
	RegUtil.register(registry, KELP, "kelp");
	RegUtil.register(registry, SEA_PICKLE, "sea_pickle");
	RegUtil.register(registry, CORAL_CLAW, "coral_claw");
	RegUtil.register(registry, CORAL_TREE, "coral_tree");
	RegUtil.register(registry, CORAL_MUSHROOM, "coral_mushroom");
	RegUtil.register(registry, BAMBOO, "bamboo");
	RegUtil.register(registry, ROOTS, "roots");
	RegUtil.register(registry, NETHER_UNDERWATER_MAGMA, "nether_underwater_magma");
	RegUtil.register(registry, RARE_LAVA, "rare_lava");
	RegUtil.register(registry, HORNED_SWAMP_TREE, "horned_swamp_tree");
	RegUtil.register(registry, GIANT_BIRCH_TREE, "giant_birch_tree");
	RegUtil.register(registry, GIANT_PINE_TREE_1, "giant_pine_1_tree");
	RegUtil.register(registry, GIANT_PINE_TREE_2, "giant_pine_2_tree");
	RegUtil.register(registry, GIANT_DARK_FOREST_TREE, "giant_dark_oak_tree");
	RegUtil.register(registry, PODZOL_TAIGA_TREE, "podzol_taiga_tree");
	RegUtil.register(registry, END_TREE, "end_tree");
	RegUtil.register(registry, JUNGLE_BUSH, "jungle_bush");
	RegUtil.register(registry, MINESHAFT, "mineshaft");
	RegUtil.register(registry, WOODLAND_MANSION, "woodland_mansion");
	RegUtil.register(registry, JUNGLE_TEMPLE, "jungle_temple");
	RegUtil.register(registry, DESERT_TEMPLE, "desert_temple");
	RegUtil.register(registry, IGLOO, "igloo");
	RegUtil.register(registry, SHIPWRECK, "shipwreck");
	RegUtil.register(registry, WITCH_HUT, "witch_hut");
	RegUtil.register(registry, STRONGHOLD, "stronghold");
	RegUtil.register(registry, OCEAN_MONUMENT, "ocean_monument");
	RegUtil.register(registry, OCEAN_RUIN, "ocean_ruins");
	RegUtil.register(registry, FORTRESS, "fortress");
	RegUtil.register(registry, END_CITY, "endcity");
	RegUtil.register(registry, VILLAGE, "village");
	RegUtil.register(registry, PILLAGER_OUTPOST, "pillager_outpost");
	RegUtil.register(registry, MUSHROOM_TEMPLE, "mushroom_temple");
	RegUtil.register(registry, ICE_SPIKE_TEMPLE, "ice_spike_temple");

	// registers the structure pieces.
	StructureInitUA.registerStructurePieces();
    }

    /**
     * Helper method that will return a random dungeon mob that other mods can
     * influence.
     */
    public static EntityType<?> pickRandomDungeonMob(Random random) {
	return net.minecraftforge.common.DungeonHooks.getRandomDungeonMob(random);
    }
}

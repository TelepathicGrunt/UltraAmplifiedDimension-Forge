package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.List;

import com.google.common.collect.ImmutableList;

import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.BigMushroomFeatureConfig;
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

    public static final HugeTreeFeatureConfig GIANT_PINE_CONFIG = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, Integer.valueOf(1))))).baseHeight(13).heightInterval(50).crownHeight(13).setSapling((net.minecraftforge.common.IPlantable)Blocks.SPRUCE_SAPLING).build();
    public static final HugeTreeFeatureConfig GIANT_SPRUCE_CONFIG = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, Integer.valueOf(1))))).baseHeight(13).heightInterval(50).crownHeight(5).setSapling((net.minecraftforge.common.IPlantable)Blocks.SPRUCE_SAPLING).build();
    public static final HugeTreeFeatureConfig GIANT_BIRCH_CONFIG = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, Integer.valueOf(1))))).baseHeight(13).heightInterval(50).crownHeight(18).setSapling((net.minecraftforge.common.IPlantable)Blocks.BIRCH_SAPLING).build();
    
    public static final TreeFeatureConfig OAK_TREE_WITH_MORE_BEEHIVES_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()), new BlobFoliagePlacer(2, 0))).baseHeight(4).heightRandA(2).foliageHeight(3).noVines().treeDecorators(ImmutableList.of(new BeehiveTreeDecoratorTempFix(0.05F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).build();
    public static final TreeFeatureConfig FANCY_TREE_WITH_MORE_BEEHIVES_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()), new BlobFoliagePlacer(0, 0))).treeDecorators(ImmutableList.of(new BeehiveTreeDecoratorTempFix(0.05F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).build();
    public static final TreeFeatureConfig OAK_TREE_WITH_BEEHIVES_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()), new BlobFoliagePlacer(2, 0))).baseHeight(4).heightRandA(2).foliageHeight(3).noVines().treeDecorators(ImmutableList.of(new BeehiveTreeDecoratorTempFix(0.01F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).build();
    public static final TreeFeatureConfig FANCY_TREE_WITH_BEEHIVES_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.OAK_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.OAK_LEAVES.getDefaultState()), new BlobFoliagePlacer(0, 0))).treeDecorators(ImmutableList.of(new BeehiveTreeDecoratorTempFix(0.01F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.OAK_SAPLING).build();
    public static final TreeFeatureConfig BIRCH_TREE_WITH_BEEHIVES_CONFIG = (new TreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()), new BlobFoliagePlacer(2, 0))).baseHeight(5).heightRandA(2).foliageHeight(3).noVines().treeDecorators(ImmutableList.of(new BeehiveTreeDecoratorTempFix(0.01F))).setSapling((net.minecraftforge.common.IPlantable)Blocks.BIRCH_SAPLING).build();
    
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
    
    public static Feature<NoFeatureConfig> BETTER_CACTUS = new BetterCactus(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> GENERIC_DUNGEONS = new DungeonDefault(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> MESA_DUNGEONS = new DungeonBadlands(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> DARK_FOREST_DUNGEONS = new DungeonDarkForest(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> DESERT_DUNGEONS = new DungeonDesert(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> END_DUNGEONS = new DungeonEnd(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> NETHER_DUNGEONS = new DungeonNether(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SNOW_DUNGEONS = new DungeonSnow(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SWAMP_DUNGEONS = new DungeonSwamp(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> MUSHROOM_DUNGEONS = new DungeonMushroom(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> JUNGLE_DUNGEONS = new DungeonJungle(NoFeatureConfig::deserialize);

    public static Feature<NoFeatureConfig> CROSS = new SwampCross(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> HAY_BALE = new HayBalePile(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> TINY_HAY_BALE = new HayBaleTinyPile(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> STONEHENGE = new Stonehenge(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SUN_SHRINE = new SunShrine(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> HANGING_RUINS = new HangingRuins(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> FOSSILS_UA = new FossilUA(NoFeatureConfig::deserialize);
    public static Feature<BlockConfig> SINGLE_BLOCK = new SingleBlock(BlockConfig::deserialize);
    public static Feature<BlockStateFeatureConfig> ICEBERG_UA = new IcebergUA(BlockStateFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> MARKED_TREASURE_CHEST_UA = new MarkedTreasureChest(NoFeatureConfig::deserialize);

    public static Feature<BlockBlobConfig> LARGE_STACKABLE_BOULDER = new BoulderGiantStackable(BlockBlobConfig::deserialize);
    public static Feature<BlockBlobConfig> LARGE_BOULDER = new BoulderGiant(BlockBlobConfig::deserialize);
    public static Feature<BlockBlobConfig> MEDIUM_BOULDER = new BoulderNormal(BlockBlobConfig::deserialize);
    public static Feature<BlockBlobConfig> SMALL_BOULDER = new BoulderTiny(BlockBlobConfig::deserialize);

    public static Feature<BlockStateFeatureConfig> STONELESS_LAKE = new LakeStoneless(BlockStateFeatureConfig::deserialize);
    public static Feature<BlockStateFeatureConfig> SHALLOW_LAKE = new LakeWideShallow(BlockStateFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> CONTAIN_LIQUID = new ContainLiquidForOceans(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> CONTAIN_UNDERGROUND_LIQUID = new ContainUndergroundLiquids(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> ICE_PATCH_SANDY = new IcePatchUA(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> GIANT_ICE_SPIKE = new IceSpikeUA(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> GREEN_CONCRETE_POWDER_PATCH = new GreenPowConcretePatch(NoFeatureConfig::deserialize);
    public static Feature<SphereReplaceConfig> DISK_DRY = new SphereReplaceDry(SphereReplaceConfig::deserialize);
    public static Feature<NoFeatureConfig> BLUE_ICE_WATERFALL = new BlueIceWaterfall(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SNOW_AND_ICE_LAYERER = new SnowIceLayerHandlerFeature(NoFeatureConfig::deserialize);
    public static Feature<ColumnBlocksConfig> COLUMN = new ColumnVertical(ColumnBlocksConfig::deserialize);
    public static Feature<ColumnBlocksConfig> RAMP = new ColumnRamp(ColumnBlocksConfig::deserialize);
    public static Feature<CountConfig> GLOWPATCH = new GlowPatch(CountConfig::deserialize);

    public static Feature<NoFeatureConfig> LONG_VINES = new VinesLong(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SHORT_VINES = new VinesShort(NoFeatureConfig::deserialize);
    public static Feature<SeaGrassConfig> SEA_GRASS_UA = new SeaGrassUA(SeaGrassConfig::deserialize);
    public static Feature<NoFeatureConfig> KELP_UA = new KelpUA(NoFeatureConfig::deserialize);
    public static Feature<CountConfig> SEA_PICKLE_UA = new SeaPickleUA(CountConfig::deserialize);
    public static Feature<NoFeatureConfig> CORAL_CLAW_UA = new CoralClawUA(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> CORAL_TREE_UA = new CoralMushroomUA(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> CORAL_MUSHROOM_UA = new CoralTreeUA(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> BAMBOO_UA = new Bamboo(NoFeatureConfig::deserialize);
    public static Feature<BlockConfig> ROOTS = new Roots(BlockConfig::deserialize);

    public static Feature<NoFeatureConfig> NETHER_UNDERWATER_MAGMA = new NetherUnderwaterMagma(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> RARE_LAVA = new NetherSurfaceLavaRare(NoFeatureConfig::deserialize);
    
    public static AbstractTreeFeature<TreeFeatureConfig> HORNED_SWAMP_TREE = new TreeSwampHorned(TreeFeatureConfig::deserialize);
    public static HugeTreesFeature<HugeTreeFeatureConfig> GIANT_BIRCH_TREE = new TreeGiantBirch(HugeTreeFeatureConfig::deserializeDarkOak); //may need to fix deserialize
    public static HugeTreesFeature<HugeTreeFeatureConfig> GIANT_PINE_TREE_1_UA = new TreeGiantPine(HugeTreeFeatureConfig::deserializeSpruce, false);
    public static HugeTreesFeature<HugeTreeFeatureConfig> GIANT_PINE_TREE_2_UA = new TreeGiantPine(HugeTreeFeatureConfig::deserializeSpruce, true);
    public static AbstractTreeFeature<HugeTreeFeatureConfig> GIANT_DARK_FOREST_TREE = new TreeGiantDarkOak(HugeTreeFeatureConfig::deserializeDarkOak);
    public static AbstractTreeFeature<TreeFeatureConfig> PODZOL_TAIGA_TREE = new TreePodzolTaiga(TreeFeatureConfig::deserialize);
    public static AbstractTreeFeature<TreeFeatureConfig> END_TREE = new TreeEnd(TreeFeatureConfig::deserialize);
    public static Feature<BaseTreeFeatureConfig> JUNGLE_BUSH_UA = new TreeJungleShrub(BaseTreeFeatureConfig::deserializeJungle, Blocks.JUNGLE_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, Integer.valueOf(1)));
    
    public static Structure<MineshaftConfigUA> MINESHAFT_UA = new MineshaftStructureUA(MineshaftConfigUA::deserialize);
    public static Structure<NoFeatureConfig> WOODLAND_MANSION_UA = new WoodlandMansionStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> JUNGLE_TEMPLE_UA = new JungleTempleStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> DESERT_TEMPLE_UA = new DesertTempleStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> IGLOO_UA = new IglooStructureUA(NoFeatureConfig::deserialize);
    public static Structure<ShipwreckConfig> SHIPWRECK_UA = new ShipwreckStructureUA(ShipwreckConfig::deserialize);
    public static WitchHutStructureUA WITCH_HUT_UA = new WitchHutStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> STRONGHOLD_UA = new StrongholdStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> OCEAN_MONUMENT_UA = new OceanMonumentStructureUA(NoFeatureConfig::deserialize);
    public static Structure<OceanRuinConfig> OCEAN_RUIN_UA = new OceanRuinsStructureUA(OceanRuinConfig::deserialize);
    public static Structure<FortressConfigUA> FORTRESS_UA = new FortressStructureUA(FortressConfigUA::deserialize);
    public static Structure<NoFeatureConfig> END_CITY_UA = new EndCityStructureUA(NoFeatureConfig::deserialize);
    public static Structure<VillageConfigUA> VILLAGE_UA = new VillageStructureUA(VillageConfigUA::deserialize);
    public static Structure<NoFeatureConfig> PILLAGER_OUTPOST_UA = new PillagerOutpostStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> MUSHROOM_TEMPLE_UA = new MushroomTempleStructureUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> ICE_SPIKE_TEMPLE_UA = new IceSpikeTempleStructureUA(NoFeatureConfig::deserialize);
    
    //bug fix until the mushroom class is no longer broken.
    public static final Feature<BigMushroomFeatureConfig> HUGE_RED_MUSHROOM_FIX = new BigRedMushroomTempFix(BigMushroomFeatureConfig::deserialize);
    public static final Feature<BigMushroomFeatureConfig> HUGE_BROWN_MUSHROOM_FIX = new BigBrownMushroomTempFix(BigMushroomFeatureConfig::deserialize);

    //for making the terrain bases for these structure
    public static final List<Structure<?>> ILLAGER_STRUCTURES = ImmutableList.of(PILLAGER_OUTPOST_UA, VILLAGE_UA);
    
    @SuppressWarnings("unchecked")
	public static void registerFeatures(RegistryEvent.Register<Feature<?>> event)
    {
    	IForgeRegistry<Feature<?>> registry = event.getRegistry();

        UltraAmplified.LOGGER.debug("FEATURE REGISTER");

        UltraAmplified.register(registry, BETTER_CACTUS, "better_cactus_ua");
        UltraAmplified.register(registry, GENERIC_DUNGEONS, "dungeon_default_ua");
        UltraAmplified.register(registry, MESA_DUNGEONS, "dungeons_badlands_ua");
        UltraAmplified.register(registry, DARK_FOREST_DUNGEONS, "dungeons_dark_forest_ua");
        UltraAmplified.register(registry, DESERT_DUNGEONS, "dungeons_desert_ua");
        UltraAmplified.register(registry, END_DUNGEONS, "dungeons_end_ua");
        UltraAmplified.register(registry, NETHER_DUNGEONS, "dungeons_nether_ua");
        UltraAmplified.register(registry, SNOW_DUNGEONS, "dungeons_snow_ua");
        UltraAmplified.register(registry, SWAMP_DUNGEONS, "dungeons_swamp_ua");
        UltraAmplified.register(registry, MUSHROOM_DUNGEONS, "dungeons_mushroom_ua");
        UltraAmplified.register(registry, JUNGLE_DUNGEONS, "dungeons_jungle_ua");
        UltraAmplified.register(registry, CROSS, "swamp_cross_ua");
        UltraAmplified.register(registry, HAY_BALE, "hay_bale_pile_ua");
        UltraAmplified.register(registry, TINY_HAY_BALE, "hay_bale_tiny_pile_ua");
        UltraAmplified.register(registry, STONEHENGE, "stonehenge_ua");
        UltraAmplified.register(registry, SUN_SHRINE, "sun_shrine_ua");
        UltraAmplified.register(registry, HANGING_RUINS, "hanging_ruins_ua");
        UltraAmplified.register(registry, FOSSILS_UA, "fossil_ua");
        UltraAmplified.register(registry, SINGLE_BLOCK, "single_block_ua");
        UltraAmplified.register(registry, ICEBERG_UA, "iceberg_ua");
        UltraAmplified.register(registry, MARKED_TREASURE_CHEST_UA, "marked_treasure_chest_ua");
        UltraAmplified.register(registry, LARGE_STACKABLE_BOULDER, "giant_stackable_boulder_ua");
        UltraAmplified.register(registry, LARGE_BOULDER, "boulder_giant_ua");
        UltraAmplified.register(registry, MEDIUM_BOULDER, "boulder_normal_ua");
        UltraAmplified.register(registry, SMALL_BOULDER, "boulder_tiny_ua");
        UltraAmplified.register(registry, STONELESS_LAKE, "stoneless_lakes_ua");
        UltraAmplified.register(registry, SHALLOW_LAKE, "wide_shallow_lakes_ua");
        UltraAmplified.register(registry, CONTAIN_LIQUID, "contain_water_ua");
        UltraAmplified.register(registry, ICE_PATCH_SANDY, "ice_patch_ua");
        UltraAmplified.register(registry, GIANT_ICE_SPIKE, "ice_spike_ua");
        UltraAmplified.register(registry, GREEN_CONCRETE_POWDER_PATCH, "green_powder_concrete_patch_ua");
        UltraAmplified.register(registry, DISK_DRY, "disk_dry_ua");
        UltraAmplified.register(registry, COLUMN, "column_ua");
        UltraAmplified.register(registry, RAMP, "ramp_column_ua");
        UltraAmplified.register(registry, GLOWPATCH, "glowpatch_ua");
        UltraAmplified.register(registry, BLUE_ICE_WATERFALL, "blue_ice_waterfall_ua");
        UltraAmplified.register(registry, SNOW_AND_ICE_LAYERER, "snow_and_ice_layerer_ua");
        UltraAmplified.register(registry, LONG_VINES, "vines_long_ua");
        UltraAmplified.register(registry, SHORT_VINES, "vines_short_ua");
        UltraAmplified.register(registry, SEA_GRASS_UA, "sea_grass_ua");
        UltraAmplified.register(registry, KELP_UA, "kelp_ua");
        UltraAmplified.register(registry, SEA_PICKLE_UA, "sea_pickle_ua");
        UltraAmplified.register(registry, CORAL_CLAW_UA, "coral_claw_ua");
        UltraAmplified.register(registry, CORAL_TREE_UA, "coral_tree_ua");
        UltraAmplified.register(registry, CORAL_MUSHROOM_UA, "coral_mushroom_ua");
        UltraAmplified.register(registry, BAMBOO_UA, "bamboo_ua");
        UltraAmplified.register(registry, ROOTS, "roots_ua");
        UltraAmplified.register(registry, HORNED_SWAMP_TREE, "horned_swamp_tree_ua");
        UltraAmplified.register(registry, GIANT_BIRCH_TREE, "giant_birch_tree_ua");
        UltraAmplified.register(registry, GIANT_PINE_TREE_1_UA, "giant_pine_1_tree_ua");
        UltraAmplified.register(registry, GIANT_PINE_TREE_2_UA, "giant_pine_2_tree_ua");
        UltraAmplified.register(registry, GIANT_DARK_FOREST_TREE, "giant_dark_oak_tree_ua");
        UltraAmplified.register(registry, PODZOL_TAIGA_TREE, "podzol_taiga_tree_ua");
        UltraAmplified.register(registry, END_TREE, "end_tree_ua");
        MINESHAFT_UA = (Structure<MineshaftConfigUA>) UltraAmplified.register(registry, MINESHAFT_UA, "mineshaft");
        WOODLAND_MANSION_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, WOODLAND_MANSION_UA, "woodland_mansion");
        JUNGLE_TEMPLE_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, JUNGLE_TEMPLE_UA, "jungle_temple");
        DESERT_TEMPLE_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, DESERT_TEMPLE_UA, "desert_temple");
        IGLOO_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, IGLOO_UA, "igloo");
        SHIPWRECK_UA = (Structure<ShipwreckConfig>) UltraAmplified.register(registry, SHIPWRECK_UA, "shipwreck");
        WITCH_HUT_UA = (WitchHutStructureUA) UltraAmplified.register(registry, WITCH_HUT_UA, "witch_hut");
        STRONGHOLD_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, STRONGHOLD_UA, "stronghold");
        OCEAN_MONUMENT_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, OCEAN_MONUMENT_UA, "ocean_monument");
        OCEAN_RUIN_UA = (Structure<OceanRuinConfig>) UltraAmplified.register(registry, OCEAN_RUIN_UA, "ocean_ruins");
        FORTRESS_UA = (Structure<FortressConfigUA>) UltraAmplified.register(registry, FORTRESS_UA, "fortress");
        END_CITY_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, END_CITY_UA, "endcity");
        VILLAGE_UA = (Structure<VillageConfigUA>) UltraAmplified.register(registry, VILLAGE_UA, "village");
        PILLAGER_OUTPOST_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, PILLAGER_OUTPOST_UA, "pillager_outpost");
        MUSHROOM_TEMPLE_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, MUSHROOM_TEMPLE_UA, "mushroom_temple");
        ICE_SPIKE_TEMPLE_UA = (Structure<NoFeatureConfig>) UltraAmplified.register(registry, ICE_SPIKE_TEMPLE_UA, "ice_spike_temple");


        //registers the structure pieces.
        StructureInitUA.registerStructurePieces();
    }
}

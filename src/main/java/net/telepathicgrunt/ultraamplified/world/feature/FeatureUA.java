package net.telepathicgrunt.ultraamplified.world.feature;

import net.minecraft.block.Blocks;
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
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.CountConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.feature.carver.ContainUndergroundLiquids;
import net.telepathicgrunt.ultraamplified.world.feature.config.BlockConfig;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;
import net.telepathicgrunt.ultraamplified.world.feature.structure.DesertTempleUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.EndCityUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.IceSpikeTempleUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.IglooUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.JungleTempleUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MushroomTempleUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.OceanMonumentUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.OceanRuinsUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.PillagerOutpostUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.ShipwreckUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.StrongholdUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.StructureInitUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.WitchHutUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.WoodlandMansionUA;

public class FeatureUA
{

    public static final HugeTreeFeatureConfig GIANT_PINE_CONFIG = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()))).func_225569_d_(13).func_227283_b_(50).func_227284_c_(13).setSapling((net.minecraftforge.common.IPlantable)Blocks.SPRUCE_SAPLING).func_225568_b_();
    public static final HugeTreeFeatureConfig GIANT_SPRUCE_CONFIG = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()))).func_225569_d_(13).func_227283_b_(50).func_227284_c_(5).setSapling((net.minecraftforge.common.IPlantable)Blocks.SPRUCE_SAPLING).func_225568_b_();
    public static final HugeTreeFeatureConfig GIANT_BIRCH_CONFIG = (new HugeTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()), new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()))).func_225569_d_(13).func_227283_b_(50).func_227284_c_(18).setSapling((net.minecraftforge.common.IPlantable)Blocks.BIRCH_SAPLING).func_225568_b_();
    
    public static <T extends BaseTreeFeatureConfig> T getConfiguredForUndergroundTreeConfig(T treeConfig)
    {
        treeConfig.func_227373_a_();
        return treeConfig;
    }

    public static <T extends BlockClusterFeatureConfig> T getConfiguredClusterConfig(T clusterConfig)
    {
    	clusterConfig.field_227298_k_ = false;
        return clusterConfig;
    }
    
    public static Feature<NoFeatureConfig> BETTER_CACTUS = new BetterCactus(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> GENERIC_DUNGEONS = new DungeonDefault(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> MESA_DUNGEONS = new DungeonsBadlands(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> DARK_FOREST_DUNGEONS = new DungeonsDarkForest(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> DESERT_DUNGEONS = new DungeonsDesert(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> END_DUNGEONS = new DungeonsEnd(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> NETHER_DUNGEONS = new DungeonsNether(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SNOW_DUNGEONS = new DungeonsSnow(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SWAMP_DUNGEONS = new DungeonsSwamp(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> MUSHROOM_DUNGEONS = new DungeonsMushroom(NoFeatureConfig::deserialize);

    public static Feature<NoFeatureConfig> CROSS = new SwampCross(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> HAY_BALE = new HayBalePile(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> TINY_HAY_BALE = new HayBaleTinyPile(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> STONEHENGE = new Stonehenge(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SUN_SHRINE = new SunShrine(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> HANGING_RUINS = new HangingRuins(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> FOSSILS_UA = new Fossil(NoFeatureConfig::deserialize);
    public static Feature<BlockConfig> SINGLE_BLOCK = new SingleBlock(BlockConfig::deserialize);
    public static Feature<BlockStateFeatureConfig> ICEBERG_UA = new Iceberg(BlockStateFeatureConfig::func_227271_a_);
    public static Feature<NoFeatureConfig> MARKED_TREASURE_CHEST_UA = new MarkedTreasureChest(NoFeatureConfig::deserialize);

    public static Feature<BlockBlobConfig> LARGE_STACKABLE_BOULDER = new GiantStackableBoulder(BlockBlobConfig::deserialize);
    public static Feature<BlockBlobConfig> LARGE_BOULDER = new BoulderGiant(BlockBlobConfig::deserialize);
    public static Feature<BlockBlobConfig> MEDIUM_BOULDER = new BoulderNormal(BlockBlobConfig::deserialize);
    public static Feature<BlockBlobConfig> SMALL_BOULDER = new BoulderTiny(BlockBlobConfig::deserialize);

    public static Feature<BlockStateFeatureConfig> SLIME_AND_ICE_LAKE = new SlimeAndIceLakes(BlockStateFeatureConfig::func_227271_a_);
    public static Feature<BlockStateFeatureConfig> SHALLOW_LAKE = new WideShallowLakes(BlockStateFeatureConfig::func_227271_a_);
    public static Feature<NoFeatureConfig> CONTAIN_LIQUID = new ContainLiquidForOceans(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> CONTAIN_UNDERGROUND_LIQUID = new ContainUndergroundLiquids(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> ICE_PATCH_SANDY = new IcePatch(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> GIANT_ICE_SPIKE = new IceSpike(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> GREEN_CONCRETE_POWDER_PATCH = new GreenPowConcretePatch(NoFeatureConfig::deserialize);
    public static Feature<SphereReplaceConfig> DISK_DRY = new SphereReplaceDry(SphereReplaceConfig::deserialize);
    public static Feature<NoFeatureConfig> BLUE_ICE_WATERFALL = new BlueIceWaterfall(NoFeatureConfig::deserialize);
    public static Feature<LiquidsConfig> CEILING_FLUID = new CeilingFluid(LiquidsConfig::deserialize);
    public static Feature<NoFeatureConfig> SNOW_AND_ICE_LAYERER = new SnowIceLayerHandlerFeature(NoFeatureConfig::deserialize);
    public static Feature<ColumnBlocksConfig> COLUMN = new Column(ColumnBlocksConfig::deserialize);
    public static Feature<ColumnBlocksConfig> RAMP = new RampColumn(ColumnBlocksConfig::deserialize);
    public static Feature<CountConfig> GLOWPATCH = new GlowPatch(CountConfig::deserialize);

    public static Feature<NoFeatureConfig> LONG_VINES = new VinesLong(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> SHORT_VINES = new VinesShort(NoFeatureConfig::deserialize);
    public static Feature<SeaGrassConfig> SEA_GRASS_UA = new SeaGrass(SeaGrassConfig::deserialize);
    public static Feature<NoFeatureConfig> KELP_UA = new Kelp(NoFeatureConfig::deserialize);
    public static Feature<CountConfig> SEA_PICKLE_UA = new SeaPickle(CountConfig::deserialize);
    public static Feature<NoFeatureConfig> CORAL_CLAW_UA = new CoralClaw(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> CORAL_TREE_UA = new CoralMushroom(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> CORAL_MUSHROOM_UA = new CoralTree(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> BAMBOO_UA = new Bamboo(NoFeatureConfig::deserialize);
    public static Feature<BlockConfig> ROOTS = new Roots(BlockConfig::deserialize);

    public static Feature<NoFeatureConfig> UNDERWATER_MAGMA = new UnderwaterMagmaForNether(NoFeatureConfig::deserialize);
    public static Feature<NoFeatureConfig> RARE_LAVA = new RareNetherSurfaceLava(NoFeatureConfig::deserialize);
    
    public static AbstractTreeFeature<TreeFeatureConfig> HORNED_SWAMP_TREE = new SwampTreeMutated(TreeFeatureConfig::func_227338_a_);
    public static HugeTreesFeature<HugeTreeFeatureConfig> MEGA_BIRCH_TREE = new BirchMTree(HugeTreeFeatureConfig::deserializeDarkOak); //may need to fix deserialize
    public static HugeTreesFeature<HugeTreeFeatureConfig> MEGA_PINE_TREE_1_UA = new MegaPineTree(HugeTreeFeatureConfig::deserializeSpruce, false);
    public static HugeTreesFeature<HugeTreeFeatureConfig> MEGA_PINE_TREE_2_UA = new MegaPineTree(HugeTreeFeatureConfig::deserializeSpruce, true);
    public static AbstractTreeFeature<HugeTreeFeatureConfig> DARK_FOREST_M_TREE = new DarkOakMTree(HugeTreeFeatureConfig::deserializeDarkOak);
    public static AbstractTreeFeature<TreeFeatureConfig> TAIGA_M_TREE = new TaigaTreeMutated(TreeFeatureConfig::func_227338_a_);
    public static AbstractTreeFeature<TreeFeatureConfig> END_TREE = new EndTree(TreeFeatureConfig::func_227338_a_);
    public static Feature<BaseTreeFeatureConfig> JUNGLE_BUSH_UA = new JungleShrub(BaseTreeFeatureConfig::deserializeJungle, Blocks.JUNGLE_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState());
    
    public static Structure<MineshaftConfigUA> MINESHAFT_UA = new MineshaftUA(MineshaftConfigUA::deserialize);
    public static Structure<NoFeatureConfig> WOODLAND_MANSION_UA = new WoodlandMansionUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> JUNGLE_TEMPLE_UA = new JungleTempleUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> DESERT_TEMPLE_UA = new DesertTempleUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> IGLOO_UA = new IglooUA(NoFeatureConfig::deserialize);
    public static Structure<ShipwreckConfig> SHIPWRECK_UA = new ShipwreckUA(ShipwreckConfig::deserialize);
    public static WitchHutUA WITCH_HUT_UA = new WitchHutUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> STRONGHOLD_UA = new StrongholdUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> OCEAN_MONUMENT_UA = new OceanMonumentUA(NoFeatureConfig::deserialize);
    public static Structure<OceanRuinConfig> OCEAN_RUIN_UA = new OceanRuinsUA(OceanRuinConfig::deserialize);
    public static Structure<FortressConfigUA> FORTRESS_UA = new FortressUA(FortressConfigUA::deserialize);
    public static Structure<NoFeatureConfig> END_CITY_UA = new EndCityUA(NoFeatureConfig::deserialize);
    public static Structure<VillageConfigUA> VILLAGE_UA = new VillageUA(VillageConfigUA::deserialize);
    public static Structure<NoFeatureConfig> PILLAGER_OUTPOST_UA = new PillagerOutpostUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> MUSHROOM_TEMPLE_UA = new MushroomTempleUA(NoFeatureConfig::deserialize);
    public static Structure<NoFeatureConfig> ICE_SPIKE_TEMPLE_UA = new IceSpikeTempleUA(NoFeatureConfig::deserialize);
    
    //bug fix until the mushroom class is no longer broken.
    public static final Feature<BigMushroomFeatureConfig> HUGE_RED_MUSHROOM_FIX = new BigRedMushroomTempFix(BigMushroomFeatureConfig::deserialize);
    public static final Feature<BigMushroomFeatureConfig> HUGE_BROWN_MUSHROOM_FIX = new BigBrownMushroomTempFix(BigMushroomFeatureConfig::deserialize);
    
    @SuppressWarnings("unchecked")
    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event)
    {
        if(registry == null)
            registry = event.getRegistry();

        UltraAmplified.LOGGER.debug("FEATURE REGISTER");

        registerFeature(BETTER_CACTUS, "better_cactus_ua");
        registerFeature(GENERIC_DUNGEONS, "dungeon_default_ua");
        registerFeature(MESA_DUNGEONS, "dungeons_badlands_ua");
        registerFeature(DARK_FOREST_DUNGEONS, "dungeons_dark_forest_ua");
        registerFeature(DESERT_DUNGEONS, "dungeons_desert_ua");
        registerFeature(END_DUNGEONS, "dungeons_end_ua");
        registerFeature(NETHER_DUNGEONS, "dungeons_nether_ua");
        registerFeature(SNOW_DUNGEONS, "dungeons_snow_ua");
        registerFeature(SWAMP_DUNGEONS, "dungeons_swamp_ua");
        registerFeature(MUSHROOM_DUNGEONS, "dungeons_mushroom_ua");
        registerFeature(CROSS, "swamp_cross_ua");
        registerFeature(HAY_BALE, "hay_bale_pile_ua");
        registerFeature(TINY_HAY_BALE, "hay_bale_tiny_pile_ua");
        registerFeature(STONEHENGE, "stonehenge_ua");
        registerFeature(SUN_SHRINE, "sun_shrine_ua");
        registerFeature(HANGING_RUINS, "hanging_ruins_ua");
        registerFeature(FOSSILS_UA, "fossil_ua");
        registerFeature(SINGLE_BLOCK, "single_block_ua");
        registerFeature(ICEBERG_UA, "iceberg_ua");
        registerFeature(MARKED_TREASURE_CHEST_UA, "marked_treasure_chest_ua");
        registerFeature(LARGE_STACKABLE_BOULDER, "giant_stackable_boulder_ua");
        registerFeature(LARGE_BOULDER, "boulder_giant_ua");
        registerFeature(MEDIUM_BOULDER, "boulder_normal_ua");
        registerFeature(SMALL_BOULDER, "boulder_tiny_ua");
        registerFeature(SLIME_AND_ICE_LAKE, "slime_lakes_ua");
        registerFeature(SHALLOW_LAKE, "wide_shallow_lakes_ua");
        registerFeature(CONTAIN_LIQUID, "contain_water_for_oceans_ua");
        registerFeature(ICE_PATCH_SANDY, "ice_patch_ua");
        registerFeature(GIANT_ICE_SPIKE, "ice_spike_ua");
        registerFeature(GREEN_CONCRETE_POWDER_PATCH, "green_pow_concrete_patch_ua");
        registerFeature(DISK_DRY, "disk_dry_ua");
        registerFeature(COLUMN, "column_ua");
        registerFeature(RAMP, "ramp_column_ua");
        registerFeature(GLOWPATCH, "glowpatch_ua");
        registerFeature(BLUE_ICE_WATERFALL, "blue_ice_waterfall_ua");
        registerFeature(CEILING_FLUID, "ceiling_fluid_ua");
        registerFeature(SNOW_AND_ICE_LAYERER, "snow_and_ice_layerer_ua");
        registerFeature(LONG_VINES, "vines_long_ua");
        registerFeature(SHORT_VINES, "vines_short_ua");
        registerFeature(SEA_GRASS_UA, "sea_grass_ua");
        registerFeature(KELP_UA, "kelp_ua");
        registerFeature(SEA_PICKLE_UA, "sea_pickle_ua");
        registerFeature(CORAL_CLAW_UA, "coral_claw_ua");
        registerFeature(CORAL_TREE_UA, "coral_tree_ua");
        registerFeature(CORAL_MUSHROOM_UA, "coral_mushroom_ua");
        registerFeature(BAMBOO_UA, "bamboo_ua");
        registerFeature(ROOTS, "roots_ua");
        registerFeature(HORNED_SWAMP_TREE, "swamp_tree_mutated_ua");
        registerFeature(MEGA_BIRCH_TREE, "birch_m_tree_ua");
        registerFeature(MEGA_PINE_TREE_1_UA, "mega_pine_1_tree_ua");
        registerFeature(MEGA_PINE_TREE_2_UA, "mega_pine_2_tree_ua");
        registerFeature(DARK_FOREST_M_TREE, "dark_oak_m_tree_ua");
        registerFeature(TAIGA_M_TREE, "taiga_tree_mutated_ua");
        registerFeature(END_TREE, "end_tree_ua");
        MINESHAFT_UA = (Structure<MineshaftConfigUA>) registerStructure(MINESHAFT_UA, "mineshaft");
        WOODLAND_MANSION_UA = (Structure<NoFeatureConfig>) registerStructure(WOODLAND_MANSION_UA, "woodland_mansion");
        JUNGLE_TEMPLE_UA = (Structure<NoFeatureConfig>) registerStructure(JUNGLE_TEMPLE_UA, "jungle_temple");
        DESERT_TEMPLE_UA = (Structure<NoFeatureConfig>) registerStructure(DESERT_TEMPLE_UA, "desert_temple");
        IGLOO_UA = (Structure<NoFeatureConfig>) registerStructure(IGLOO_UA, "igloo");
        SHIPWRECK_UA = (Structure<ShipwreckConfig>) registerStructure(SHIPWRECK_UA, "shipwreck");
        WITCH_HUT_UA = (WitchHutUA) registerStructure(WITCH_HUT_UA, "witch_hut");
        STRONGHOLD_UA = (Structure<NoFeatureConfig>) registerStructure(STRONGHOLD_UA, "stronghold");
        OCEAN_MONUMENT_UA = (Structure<NoFeatureConfig>) registerStructure(OCEAN_MONUMENT_UA, "ocean_monument");
        OCEAN_RUIN_UA = (Structure<OceanRuinConfig>) registerStructure(OCEAN_RUIN_UA, "ocean_ruins");
        FORTRESS_UA = (Structure<FortressConfigUA>) registerStructure(FORTRESS_UA, "fortress");
        END_CITY_UA = (Structure<NoFeatureConfig>) registerStructure(END_CITY_UA, "endcity");
        VILLAGE_UA = (Structure<VillageConfigUA>) registerStructure(VILLAGE_UA, "village");
        PILLAGER_OUTPOST_UA = (Structure<NoFeatureConfig>) registerStructure(PILLAGER_OUTPOST_UA, "pillager_outpost");
        MUSHROOM_TEMPLE_UA = (Structure<NoFeatureConfig>) registerStructure(MUSHROOM_TEMPLE_UA, "mushroom_temple");
        ICE_SPIKE_TEMPLE_UA = (Structure<NoFeatureConfig>) registerStructure(ICE_SPIKE_TEMPLE_UA, "ice_spike_temple");


      
        StructureInitUA.registerStructurePieces();
    }

    @SuppressWarnings("rawtypes")
    private static Feature registerFeature(Feature<?> feature, String name)
    {
        if(registry == null)
            throw new NullPointerException("Feature Registry not set");


        feature.setRegistryName(UltraAmplified.MODID, name);
        registry.register(feature);

        return feature;
    }

	private static Structure<?> registerStructure(Structure<?> structure, String name) {
    	if(registry == null)
            throw new NullPointerException("Feature Registry not set (Structure)");


    	structure.setRegistryName(UltraAmplified.MODID, name);
        registry.register(structure);

        return structure;
    }

    private static IForgeRegistry<Feature<?>> registry;
}

package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.BlockConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.placement.ContainWaterConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.DesertTempleUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.EndCityUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.IglooUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.JungleTempleUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.MineshaftUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.NetherBridgeConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.NetherBridgeUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.OceanMonumentUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.OceanRuinsUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.ShipwreckUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.StrongholdUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.VillageUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.VillageUAConfig;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.WitchHutUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.structure.WoodlandMansionUA;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeTreesFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.IcebergConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.feature.structure.DesertPyramidConfig;
import net.minecraft.world.gen.feature.structure.EndCityConfig;
import net.minecraft.world.gen.feature.structure.IglooConfig;
import net.minecraft.world.gen.feature.structure.JunglePyramidConfig;
import net.minecraft.world.gen.feature.structure.OceanMonumentConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.StrongholdConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.SwampHutConfig;
import net.minecraft.world.gen.feature.structure.WoodlandMansionConfig;
import net.minecraft.world.gen.placement.CountConfig;

public abstract class FeatureUA<C extends IFeatureConfig> extends Feature<C>{

	   public static final Feature<NoFeatureConfig> BETTER_CACTUS = new BetterCactus();
	   public static final Feature<NoFeatureConfig> GENERIC_DUNGEONS = new DungeonDefault();
	   public static final Feature<NoFeatureConfig> MESA_DUNGEONS = new DungeonsBadlands();
	   public static final Feature<NoFeatureConfig> DARK_FOREST_DUNGEONS = new DungeonsDarkForest();
	   public static final Feature<NoFeatureConfig> DESERT_DUNGEONS = new DungeonsDesert();
	   public static final Feature<NoFeatureConfig> END_DUNGEONS = new DungeonsEnd();
	   public static final Feature<NoFeatureConfig> NETHER_DUNGEONS = new DungeonsNether();
	   public static final Feature<NoFeatureConfig> SNOW_DUNGEONS = new DungeonsSnow();
	   public static final Feature<NoFeatureConfig> SWAMP_DUNGEONS = new DungeonsSwamp();
	   public static final Feature<NoFeatureConfig> MUSHROOM_DUNGEONS = new DungeonsMushroom();
	   
	   public static final Feature<NoFeatureConfig> CROSS = new SwampCross();
	   public static final Feature<NoFeatureConfig> HAY_BALE = new HayBalePile();
	   public static final Feature<NoFeatureConfig> TINY_HAY_BALE = new HayBaleTinyPile();
	   public static final Feature<NoFeatureConfig> STONEHENGE = new Stonehenge();
	   public static final Feature<NoFeatureConfig> SUN_SHRINE = new SunShrine();
	   public static final Feature<NoFeatureConfig> FOSSILS_UA = new FossilUA();
	   public static final Feature<BlockConfig> SINGLE_BLOCK = new SingleBlock();
	   public static final Feature<IcebergConfig> ICEBERG_UA = new IcebergUA();
	   public static final Feature<NoFeatureConfig> MARKED_TREASURE_CHEST_UA = new MarkedTreasureChest();

	   public static final Feature<BlockBlobConfig> LARGE_STACKABLE_BOULDER = new GiantStackableBoulder();
	   public static final Feature<BlockBlobConfig> LARGE_BOULDER = new BoulderGiant();
	   public static final Feature<BlockBlobConfig> MEDIUM_BOULDER = new BoulderNormal();
	   public static final Feature<BlockBlobConfig> SMALL_BOULDER = new BoulderTiny();

	   public static final Feature<LakesConfig> SLIME_LAKE = new SlimeLakes();
	   public static final Feature<LakesConfig> SHALLOW_LAKE = new WideShallowLakes();
	   public static final Feature<ContainWaterConfig> CONTAIN_WATER = new ContainWaterForOceans();
	   public static final Feature<NoFeatureConfig> ICE_PATCH_SANDY = new IcePatchUA();
	   public static final Feature<NoFeatureConfig> GIANT_ICE_SPIKE = new IceSpikeUA();
	   public static final Feature<NoFeatureConfig> GREEN_CONCRETE_POWDER_PATCH = new GreenPowConcretePatch();
	   public static final Feature<NoFeatureConfig> BLUE_ICE_WATERFALL = new BlueIceWaterfall();
	   public static final Feature<LiquidsConfig> CEILING_FLUID = new CeilingFluid();
	   public static final Feature<NoFeatureConfig> ICE_AND_SNOW_UNDER_LEDGES = new IceAndSnowAtAllLayer();
	   public static final Feature<NoFeatureConfig> SNOW_FIXED = new ColdOceanSnowFeatureUA();
	   
	   public static final Feature<NoFeatureConfig> LONG_VINES = new VinesLongUA();
	   public static final Feature<NoFeatureConfig> SHORT_VINES = new VinesShortUA();
	   public static final Feature<SeaGrassConfig> SEA_GRASS_UA = new SeaGrassUA();
	   public static final Feature<NoFeatureConfig> KELP_UA = new KelpUA();
	   public static final Feature<CountConfig> SEA_PICKLE_UA = new SeaPickleUA();
	   
	   public static final AbstractTreeFeature<NoFeatureConfig> HORNED_SWAMP_TREE = new SwampTreeMutated();
	   public static final HugeTreesFeature<NoFeatureConfig> MEGA_BIRCH_TREE = new BirchMTreeUA(false);
	   public static final HugeTreesFeature<NoFeatureConfig> MEGA_PINE_TREE_1_UA = new MegaPineTreeUA(false, false);
	   public static final HugeTreesFeature<NoFeatureConfig> MEGA_PINE_TREE_2_UA = new MegaPineTreeUA(false, true);
	   public static final AbstractTreeFeature<NoFeatureConfig> DARK_FOREST_M_TREE = new DarkOakMTreeUA(false);
	   public static final AbstractTreeFeature<NoFeatureConfig> TAIGA_M_TREE = new TaigaTreeMutatedUA(false);
	   
	   public static final Structure<MineshaftConfigUA> MINESHAFT_UA = new MineshaftUA();
	   public static final Structure<EndCityConfig> END_CITY_UA = new EndCityUA();
	   public static final Structure<NetherBridgeConfigUA> FORTRESS_UA = new NetherBridgeUA();
	   public static final Structure<WoodlandMansionConfig> WOODLAND_MANSION_UA = new WoodlandMansionUA();
	   public static final Structure<StrongholdConfig> STRONGHOLD_UA = new StrongholdUA();
	   public static final Structure<IglooConfig> IGLOO_UA = new IglooUA();
	   public static final Structure<DesertPyramidConfig> DESERT_TEMPLE_UA = new DesertTempleUA();
	   public static final Structure<JunglePyramidConfig> JUNGLE_TEMPLE_UA = new JungleTempleUA();
	   public static final Structure<SwampHutConfig> WITCH_HUT_UA = new WitchHutUA();
	   public static final Structure<VillageUAConfig> VILLAGE_UA = new VillageUA();
	   public static final Structure<ShipwreckConfig> SHIPWRECK_UA = new ShipwreckUA();
	   public static final Structure<OceanMonumentConfig> OCEAN_MONUMENT_UA = new OceanMonumentUA();
	   public static final Structure<OceanRuinConfig> OCEAN_RUIN_UA = new OceanRuinsUA();
}

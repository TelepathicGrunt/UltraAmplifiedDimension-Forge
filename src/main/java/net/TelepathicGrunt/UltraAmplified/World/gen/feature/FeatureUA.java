package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;

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
import net.minecraft.world.gen.feature.IcebergConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.placement.CountConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@Mod.EventBusSubscriber(modid = UltraAmplified.modid, bus = Mod.EventBusSubscriber.Bus.MOD)
@ObjectHolder(UltraAmplified.modid)
public class FeatureUA
{
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
	   public static Feature<NoFeatureConfig> FOSSILS_UA = new FossilUA(NoFeatureConfig::deserialize);
	   public static Feature<BlockConfig> SINGLE_BLOCK = new SingleBlock(BlockConfig::deserialize);
	   public static Feature<IcebergConfig> ICEBERG_UA = new IcebergUA(IcebergConfig::deserialize);
	   public static Feature<NoFeatureConfig> MARKED_TREASURE_CHEST_UA = new MarkedTreasureChest(NoFeatureConfig::deserialize);

	   public static Feature<BlockBlobConfig> LARGE_STACKABLE_BOULDER = new GiantStackableBoulder(BlockBlobConfig::deserialize);
	   public static Feature<BlockBlobConfig> LARGE_BOULDER = new BoulderGiant(BlockBlobConfig::deserialize);
	   public static Feature<BlockBlobConfig> MEDIUM_BOULDER = new BoulderNormal(BlockBlobConfig::deserialize);
	   public static Feature<BlockBlobConfig> SMALL_BOULDER = new BoulderTiny(BlockBlobConfig::deserialize);

	   public static Feature<LakesConfig> SLIME_LAKE = new SlimeLakes(LakesConfig::deserialize);
	   public static Feature<LakesConfig> SHALLOW_LAKE = new WideShallowLakes(LakesConfig::deserialize);
	   public static Feature<ContainWaterConfig> CONTAIN_WATER = new ContainWaterForOceans(ContainWaterConfig::deserialize);
	   public static Feature<NoFeatureConfig> ICE_PATCH_SANDY = new IcePatchUA(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> GIANT_ICE_SPIKE = new IceSpikeUA(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> GREEN_CONCRETE_POWDER_PATCH = new GreenPowConcretePatch(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> BLUE_ICE_WATERFALL = new BlueIceWaterfall(NoFeatureConfig::deserialize);
	   public static Feature<LiquidsConfig> CEILING_FLUID = new CeilingFluid(LiquidsConfig::deserialize);
	   public static Feature<NoFeatureConfig> ICE_AND_SNOW_UNDER_LEDGES = new IceAndSnowAtAllLayer(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> SNOW_FIXED = new ColdOceanSnowFeatureUA(NoFeatureConfig::deserialize);

	   public static Feature<NoFeatureConfig> LONG_VINES = new VinesLongUA(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> SHORT_VINES = new VinesShortUA(NoFeatureConfig::deserialize);
	   public static Feature<SeaGrassConfig> SEA_GRASS_UA = new SeaGrassUA(SeaGrassConfig::deserialize);
	   public static Feature<NoFeatureConfig> KELP_UA = new KelpUA(NoFeatureConfig::deserialize);
	   public static Feature<CountConfig> SEA_PICKLE_UA = new SeaPickleUA(CountConfig::deserialize);

	   public static AbstractTreeFeature<NoFeatureConfig> HORNED_SWAMP_TREE = new SwampTreeMutated(NoFeatureConfig::deserialize);
	   public static HugeTreesFeature<NoFeatureConfig> MEGA_BIRCH_TREE = new BirchMTreeUA(NoFeatureConfig::deserialize, false, false);
	   public static HugeTreesFeature<NoFeatureConfig> MEGA_PINE_TREE_1_UA = new MegaPineTreeUA(NoFeatureConfig::deserialize, false, false);
	   public static HugeTreesFeature<NoFeatureConfig> MEGA_PINE_TREE_2_UA = new MegaPineTreeUA(NoFeatureConfig::deserialize, false, true);
	   public static AbstractTreeFeature<NoFeatureConfig> DARK_FOREST_M_TREE = new DarkOakMTreeUA(NoFeatureConfig::deserialize, false);
	   public static AbstractTreeFeature<NoFeatureConfig> TAIGA_M_TREE = new TaigaTreeMutatedUA(NoFeatureConfig::deserialize, false);

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
	   public static Structure<NetherBridgeConfigUA> FORTRESS_UA = new NetherBridgeUA(NetherBridgeConfigUA::deserialize);
	   public static Structure<NoFeatureConfig> END_CITY_UA = new EndCityUA(NoFeatureConfig::deserialize);
	   public static Structure<VillageUAConfig> VILLAGE_UA = new VillageUA(VillageUAConfig::deserialize);
	   
	   
	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static void onRegisterFeature(RegistryEvent.Register<Feature<?>> event)
	{
		if(registry == null)
			registry = event.getRegistry();

		UltraAmplified.Logger.debug("FEATURE REGISTER");

		   BETTER_CACTUS = registerFeature(BETTER_CACTUS, "better_cactus");
		   GENERIC_DUNGEONS = registerFeature(GENERIC_DUNGEONS, "dungeon_default");
		   MESA_DUNGEONS = registerFeature(MESA_DUNGEONS, "dungeons_badlands");
		   DARK_FOREST_DUNGEONS = registerFeature(DARK_FOREST_DUNGEONS, "dungeons_dark_forest");
		   DESERT_DUNGEONS = registerFeature(DESERT_DUNGEONS, "dungeons_desert");
		   END_DUNGEONS = registerFeature(END_DUNGEONS, "dungeons_end");
		   NETHER_DUNGEONS = registerFeature(NETHER_DUNGEONS, "dungeons_nether");
		   SNOW_DUNGEONS = registerFeature(SNOW_DUNGEONS, "dungeons_snow");
		   SWAMP_DUNGEONS = registerFeature(SWAMP_DUNGEONS, "dungeons_swamp");
		   MUSHROOM_DUNGEONS = registerFeature(MUSHROOM_DUNGEONS, "dungeons_mushroom");
		   CROSS = registerFeature(CROSS, "swamp_cross");
		   HAY_BALE = registerFeature(HAY_BALE, "hay_bale_pile");
		   TINY_HAY_BALE = registerFeature(TINY_HAY_BALE, "hay_bale_tiny_pile");
		   STONEHENGE = registerFeature(STONEHENGE, "stonehenge");
		   SUN_SHRINE = registerFeature(SUN_SHRINE, "sun_shrine");
		   FOSSILS_UA = registerFeature(FOSSILS_UA, "fossil");
		   SINGLE_BLOCK = registerFeature(SINGLE_BLOCK, "single_block");
		   ICEBERG_UA = registerFeature(ICEBERG_UA, "iceberg");
		   MARKED_TREASURE_CHEST_UA = registerFeature(MARKED_TREASURE_CHEST_UA, "marked_treasure_chest");
		   LARGE_STACKABLE_BOULDER = registerFeature(LARGE_STACKABLE_BOULDER, "giant_stackable_boulder");
		   LARGE_BOULDER = registerFeature(LARGE_BOULDER, "boulder_giant");
		   MEDIUM_BOULDER = registerFeature(MEDIUM_BOULDER, "boulder_normal");
		   SMALL_BOULDER = registerFeature(SMALL_BOULDER, "boulder_tiny");
		   SLIME_LAKE = registerFeature(SLIME_LAKE, "slime_lakes");
		   SHALLOW_LAKE = registerFeature(SHALLOW_LAKE, "wide_shallow_lakes");
		   CONTAIN_WATER = registerFeature(CONTAIN_WATER, "contain_water_for_oceans");
		   ICE_PATCH_SANDY = registerFeature(ICE_PATCH_SANDY, "ice_patch");
		   GIANT_ICE_SPIKE = registerFeature(GIANT_ICE_SPIKE, "ice_spike");
		   GREEN_CONCRETE_POWDER_PATCH = registerFeature(GREEN_CONCRETE_POWDER_PATCH, "green_pow_concrete_patch");
		   BLUE_ICE_WATERFALL = registerFeature(BLUE_ICE_WATERFALL, "blue_ice_waterfall");
		   CEILING_FLUID = registerFeature(CEILING_FLUID, "ceiling_fluid");
		   ICE_AND_SNOW_UNDER_LEDGES = registerFeature(ICE_AND_SNOW_UNDER_LEDGES, "ice_and_snow_at_all_layer");
		   SNOW_FIXED = registerFeature(SNOW_FIXED, "cold_ocean_snow_feature");
		   LONG_VINES = registerFeature(LONG_VINES, "vines_long");
		   SHORT_VINES = registerFeature(SHORT_VINES, "vines_short");
		   SEA_GRASS_UA = registerFeature(SEA_GRASS_UA, "sea_grass");
		   KELP_UA = registerFeature(KELP_UA, "kelp");
		   SEA_PICKLE_UA = registerFeature(SEA_PICKLE_UA, "sea_pickle");
		   HORNED_SWAMP_TREE = (AbstractTreeFeature<NoFeatureConfig>) registerFeature(HORNED_SWAMP_TREE, "swamp_tree_mutated");
		   MEGA_BIRCH_TREE = (HugeTreesFeature<NoFeatureConfig>) registerFeature(MEGA_BIRCH_TREE, "birch_m_tree");
		   MEGA_PINE_TREE_1_UA = (HugeTreesFeature<NoFeatureConfig>) registerFeature(MEGA_PINE_TREE_1_UA, "mega_pine_1_tree");
		   MEGA_PINE_TREE_2_UA = (HugeTreesFeature<NoFeatureConfig>) registerFeature(MEGA_PINE_TREE_2_UA, "mega_pine_2_tree");
		   DARK_FOREST_M_TREE = (AbstractTreeFeature<NoFeatureConfig>) registerFeature(DARK_FOREST_M_TREE, "dark_oak_m_tree");
		   TAIGA_M_TREE = (AbstractTreeFeature<NoFeatureConfig>) registerFeature(TAIGA_M_TREE, "taiga_tree_mutated");
		   MINESHAFT_UA = (Structure<MineshaftConfigUA>) registerFeature(MINESHAFT_UA, "mineshaft_ua");
		   WOODLAND_MANSION_UA = (Structure<NoFeatureConfig>) registerFeature(WOODLAND_MANSION_UA, "woodland_mansion_ua");
		   JUNGLE_TEMPLE_UA = (Structure<NoFeatureConfig>) registerFeature(JUNGLE_TEMPLE_UA, "jungle_temple_ua");
		   DESERT_TEMPLE_UA = (Structure<NoFeatureConfig>) registerFeature(DESERT_TEMPLE_UA, "desert_pyramid_ua");
		   IGLOO_UA = (Structure<NoFeatureConfig>) registerFeature(IGLOO_UA, "igloo_ua");
		   SHIPWRECK_UA = (Structure<ShipwreckConfig>) registerFeature(SHIPWRECK_UA, "shipwreck_ua");
		   WITCH_HUT_UA = (WitchHutUA) registerFeature(WITCH_HUT_UA, "swamp_hut_ua");
		   STRONGHOLD_UA = (Structure<NoFeatureConfig>) registerFeature(STRONGHOLD_UA, "stronghold_ua");
		   OCEAN_MONUMENT_UA = (Structure<NoFeatureConfig>) registerFeature(OCEAN_MONUMENT_UA, "ocean_monument_ua");
		   OCEAN_RUIN_UA = (Structure<OceanRuinConfig>) registerFeature(OCEAN_RUIN_UA, "ocean_ruin_ua");
		   FORTRESS_UA = (Structure<NetherBridgeConfigUA>) registerFeature(FORTRESS_UA, "nether_bridge_ua");
		   END_CITY_UA = (Structure<NoFeatureConfig>) registerFeature(END_CITY_UA, "end_city_ua");
		   VILLAGE_UA = (Structure<VillageUAConfig>) registerFeature(VILLAGE_UA, "village_ua");
		
		
		
	}

	@SuppressWarnings("rawtypes")
	private static Feature registerFeature(Feature<?> feature, String name)
	{
		if(registry == null)
			throw new NullPointerException("Feature Registry not set");


		feature.setRegistryName(UltraAmplified.modid, name);
		registry.register(feature);

		return feature;
	}

	private static IForgeRegistry<Feature<?>> registry;
}

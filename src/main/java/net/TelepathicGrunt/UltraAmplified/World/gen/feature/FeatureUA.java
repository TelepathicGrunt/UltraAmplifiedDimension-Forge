package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.function.Function;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;
import com.mojang.datafixers.Dynamic;

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
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
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
public abstract class FeatureUA
{

		public static Feature<NoFeatureConfig> BETTER_CACTUS = new BetterCactus(NoFeatureConfig::deserialize);
		public static Feature<NoFeatureConfig> GENERIC_DUNGEONS = register("dungeon_default", new DungeonDefault(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> MESA_DUNGEONS = register("dungeons_badlands", new DungeonsBadlands(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> DARK_FOREST_DUNGEONS = register("dungeons_dark_forest", new DungeonsDarkForest(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> DESERT_DUNGEONS = register("dungeons_desert", new DungeonsDesert(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> END_DUNGEONS = register("dungeons_end", new DungeonsEnd(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> NETHER_DUNGEONS = register("dungeons_nether", new DungeonsNether(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> SNOW_DUNGEONS = register("dungeons_snow", new DungeonsSnow(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> SWAMP_DUNGEONS = register("dungeons_swamp", new DungeonsSwamp(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> MUSHROOM_DUNGEONS = register("dungeons_mushroom", new DungeonsMushroom(NoFeatureConfig::deserialize));
	
		public static Feature<NoFeatureConfig> CROSS = register("swamp_cross", new SwampCross(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> HAY_BALE = register("hay_bale_pile", new HayBalePile(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> TINY_HAY_BALE = register("hay_bale_tiny_pile", new HayBaleTinyPile(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> STONEHENGE = register("stonehenge", new Stonehenge(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> SUN_SHRINE = register("sun_shrine", new SunShrine(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> FOSSILS_UA = register("fossil", new FossilUA(NoFeatureConfig::deserialize));
		public static Feature<BlockConfig> SINGLE_BLOCK = register("single_block", new SingleBlock(BlockConfig::deserialize));
		public static Feature<IcebergConfig> ICEBERG_UA = register("iceberg", new IcebergUA(IcebergConfig::deserialize));
		public static Feature<NoFeatureConfig> MARKED_TREASURE_CHEST_UA = register("marked_treasure_chest", new MarkedTreasureChest(NoFeatureConfig::deserialize));
	
		public static Feature<BlockBlobConfig> LARGE_STACKABLE_BOULDER = register("giant_stackable_boulder", new GiantStackableBoulder(BlockBlobConfig::deserialize));
		public static Feature<BlockBlobConfig> LARGE_BOULDER = register("boulder_giant", new BoulderGiant(BlockBlobConfig::deserialize));
		public static Feature<BlockBlobConfig> MEDIUM_BOULDER = register("boulder_normal", new BoulderNormal(BlockBlobConfig::deserialize));
		public static Feature<BlockBlobConfig> SMALL_BOULDER = register("boulder_tiny", new BoulderTiny(BlockBlobConfig::deserialize));
	
		public static Feature<LakesConfig> SLIME_LAKE = register("slime_lakes", new SlimeLakes(LakesConfig::deserialize));
		public static Feature<LakesConfig> SHALLOW_LAKE = register("wide_shallow_lakes", new WideShallowLakes(LakesConfig::deserialize));
		public static Feature<ContainWaterConfig> CONTAIN_WATER = register("contain_water_for_oceans", new ContainWaterForOceans(ContainWaterConfig::deserialize));
		public static Feature<NoFeatureConfig> ICE_PATCH_SANDY = register("ice_patch", new IcePatchUA(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> GIANT_ICE_SPIKE = register("ice_spike", new IceSpikeUA(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> GREEN_CONCRETE_POWDER_PATCH = register("green_pow_concrete_patch", new GreenPowConcretePatch(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> BLUE_ICE_WATERFALL = register("blue_ice_waterfall", new BlueIceWaterfall(NoFeatureConfig::deserialize));
		public static Feature<LiquidsConfig> CEILING_FLUID = register("ceiling_fluid", new CeilingFluid(LiquidsConfig::deserialize));
		public static Feature<NoFeatureConfig> ICE_AND_SNOW_UNDER_LEDGES = register("ice_and_snow_at_all_layer", new IceAndSnowAtAllLayer(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> SNOW_FIXED = register("cold_ocean_snow_feature", new ColdOceanSnowFeatureUA(NoFeatureConfig::deserialize));
	
		public static Feature<NoFeatureConfig> LONG_VINES = register("vines_long", new VinesLongUA(NoFeatureConfig::deserialize));
		public static Feature<NoFeatureConfig> SHORT_VINES = register("vines_short", new VinesShortUA(NoFeatureConfig::deserialize));
		public static Feature<SeaGrassConfig> SEA_GRASS_UA = register("sea_grass", new SeaGrassUA(SeaGrassConfig::deserialize));
		public static Feature<NoFeatureConfig> KELP_UA = register("kelp", new KelpUA(NoFeatureConfig::deserialize));
		public static Feature<CountConfig> SEA_PICKLE_UA = register("sea_pickle", new SeaPickleUA(CountConfig::deserialize));
	
		public static AbstractTreeFeature<NoFeatureConfig> HORNED_SWAMP_TREE = register("swamp_tree_mutated", new SwampTreeMutated(NoFeatureConfig::deserialize));
		public static HugeTreesFeature<NoFeatureConfig> MEGA_BIRCH_TREE = register("birch_m_tree", new BirchMTreeUA(NoFeatureConfig::deserialize, false, false));
		public static HugeTreesFeature<NoFeatureConfig> MEGA_PINE_TREE_1_UA = register("mega_pine_1_tree", new MegaPineTreeUA(NoFeatureConfig::deserialize, false, false));
		public static HugeTreesFeature<NoFeatureConfig> MEGA_PINE_TREE_2_UA = register("mega_pine_2_tree", new MegaPineTreeUA(NoFeatureConfig::deserialize, false, true));
		public static AbstractTreeFeature<NoFeatureConfig> DARK_FOREST_M_TREE = register("dark_oak_m_tree", new DarkOakMTreeUA(NoFeatureConfig::deserialize, false));
		public static AbstractTreeFeature<NoFeatureConfig> TAIGA_M_TREE = register("taiga_tree_mutated", new TaigaTreeMutatedUA(NoFeatureConfig::deserialize, false));

	   
	   public static Structure<MineshaftConfigUA> MINESHAFT_UA = register("mineshaft_ua", new MineshaftUA(MineshaftConfigUA::deserialize));
	   public static Structure<NoFeatureConfig> WOODLAND_MANSION_UA = register("woodland_mansion_ua", new WoodlandMansionUA(NoFeatureConfig::deserialize));
	   public static Structure<NoFeatureConfig> JUNGLE_TEMPLE_UA = register("jungle_temple_ua", new JungleTempleUA(NoFeatureConfig::deserialize));
	   public static Structure<NoFeatureConfig> DESERT_TEMPLE_UA = register("desert_pyramid_ua", new DesertTempleUA(NoFeatureConfig::deserialize));
	   public static Structure<NoFeatureConfig> IGLOO_UA = register("igloo_ua", new IglooUA(NoFeatureConfig::deserialize));
	   public static Structure<ShipwreckConfig> SHIPWRECK_UA = register("shipwreck_ua", new ShipwreckUA(ShipwreckConfig::deserialize));
	   public static Structure<NoFeatureConfig> WITCH_HUT_UA = register("swamp_hut_ua", new WitchHutUA(NoFeatureConfig::deserialize));
	   public static Structure<NoFeatureConfig> STRONGHOLD_UA = register("stronghold_ua", new StrongholdUA(NoFeatureConfig::deserialize));
	   public static Structure<NoFeatureConfig> OCEAN_MONUMENT_UA = register("ocean_monument_ua", new OceanMonumentUA(NoFeatureConfig::deserialize));
	   public static Structure<OceanRuinConfig> OCEAN_RUIN_UA = register("ocean_ruin_ua", new OceanRuinsUA(OceanRuinConfig::deserialize));
	   public static Structure<NetherBridgeConfigUA> FORTRESS_UA = register("nether_bridge_ua", new NetherBridgeUA(NetherBridgeConfigUA::deserialize));
	   public static Structure<NoFeatureConfig> END_CITY_UA = register("end_city_ua", new EndCityUA(NoFeatureConfig::deserialize));
	   public static Structure<VillageUAConfig> VILLAGE_UA = register("village_ua", new VillageUA(VillageUAConfig::deserialize));


	@SuppressWarnings({ "unchecked", "deprecation" })
	private static <C extends IFeatureConfig, F extends Feature<C>> F register(String key, F value) {
	      return (F)(Registry.<Feature<?>>register(Registry.FEATURE, key, value));
	}

	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static void onRegisterFeature(RegistryEvent.Register<Feature<?>> event)
	{
		if(registry == null)
			registry = event.getRegistry();

		UltraAmplified.Logger.debug("FEATURE REGISTER");


		BETTER_CACTUS = registerFeature(BETTER_CACTUS, "better_cactus");
		
		
		
		
	}

	@SuppressWarnings("rawtypes")
	private static Feature registerFeature(Feature<?> feature, String name)
	{
		if(registry == null)
			throw new NullPointerException("Feature Registry not set");


		feature.setRegistryName(UltraAmplified.modid, name);
		registry.register(feature);

		return  feature;
	}

	private static IForgeRegistry<Feature<?>> registry;
}

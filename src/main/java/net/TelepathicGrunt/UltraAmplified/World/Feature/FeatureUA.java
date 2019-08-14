package net.TelepathicGrunt.UltraAmplified.World.Feature;

import java.util.Locale;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;

import net.TelepathicGrunt.UltraAmplified.World.Feature.Config.BlockConfig;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Config.ColumnBlocksConfig;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.DesertTempleUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.EndCityUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.IglooUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.JungleTempleUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.MineshaftConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.MineshaftUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.FortressConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.FortressUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.OceanMonumentUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.OceanRuinsUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.PillagerOutpostUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.ShipwreckUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.StrongholdUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.StructureInit;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.VillageUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.VillageUAConfig;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.WitchHutUA;
import net.TelepathicGrunt.UltraAmplified.World.Feature.Structure.WoodlandMansionUA;
import net.minecraft.block.Blocks;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.BlockBlobConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.HugeTreesFeature;
import net.minecraft.world.gen.feature.IcebergConfig;
import net.minecraft.world.gen.feature.LakesConfig;
import net.minecraft.world.gen.feature.LiquidsConfig;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.SeaGrassConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.PillagerOutpostConfig;
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

	   public static Feature<LakesConfig> SLIME_AND_ICE_LAKE = new SlimeAndIceLakes(LakesConfig::deserialize);
	   public static Feature<LakesConfig> SHALLOW_LAKE = new WideShallowLakes(LakesConfig::deserialize);
	   public static Feature<NoFeatureConfig> CONTAIN_LIQUID = new ContainLiquidForOceans(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> ICE_PATCH_SANDY = new IcePatchUA(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> GIANT_ICE_SPIKE = new IceSpikeUA(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> GREEN_CONCRETE_POWDER_PATCH = new GreenPowConcretePatch(NoFeatureConfig::deserialize);
	   public static Feature<SphereReplaceConfig> DISK_DRY = new SphereReplaceDryUA(SphereReplaceConfig::deserialize);
	   public static Feature<NoFeatureConfig> BLUE_ICE_WATERFALL = new BlueIceWaterfall(NoFeatureConfig::deserialize);
	   public static Feature<LiquidsConfig> CEILING_FLUID = new CeilingFluid(LiquidsConfig::deserialize);
	   public static Feature<NoFeatureConfig> ICE_AND_SNOW_UNDER_LEDGES = new IceAndSnowAtAllLayer(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> SNOW_FIXED = new ColdOceanSnowFeatureUA(NoFeatureConfig::deserialize);
	   public static Feature<ColumnBlocksConfig> COLUMN = new ColumnUA(ColumnBlocksConfig::deserialize);
	   public static Feature<ColumnBlocksConfig> RAMP = new RampColumnUA(ColumnBlocksConfig::deserialize);
	   public static Feature<CountConfig> GLOWPATCH = new GlowPatch(CountConfig::deserialize);

	   public static Feature<NoFeatureConfig> LONG_VINES = new VinesLongUA(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> SHORT_VINES = new VinesShortUA(NoFeatureConfig::deserialize);
	   public static Feature<SeaGrassConfig> SEA_GRASS_UA = new SeaGrassUA(SeaGrassConfig::deserialize);
	   public static Feature<NoFeatureConfig> KELP_UA = new KelpUA(NoFeatureConfig::deserialize);
	   public static Feature<CountConfig> SEA_PICKLE_UA = new SeaPickleUA(CountConfig::deserialize);
	   public static Feature<NoFeatureConfig> CORAL_CLAW_UA = new CoralClawUA(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> CORAL_TREE_UA = new CoralMushroomUA(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> CORAL_MUSHROOM_UA = new CoralTreeUA(NoFeatureConfig::deserialize);
	   public static Feature<NoFeatureConfig> JUNGLE_BUSH_UA = new JungleShrubUA(NoFeatureConfig::deserialize, Blocks.JUNGLE_LOG.getDefaultState(), Blocks.OAK_LEAVES.getDefaultState());
	   public static Feature<NoFeatureConfig> BAMBOO_UA = new BambooUA(NoFeatureConfig::deserialize);

	   
	   public static AbstractTreeFeature<NoFeatureConfig> HORNED_SWAMP_TREE = new SwampTreeMutated(NoFeatureConfig::deserialize);
	   public static HugeTreesFeature<NoFeatureConfig> MEGA_BIRCH_TREE = new BirchMTreeUA(NoFeatureConfig::deserialize, false, false);
	   public static HugeTreesFeature<NoFeatureConfig> MEGA_PINE_TREE_1_UA = new MegaPineTreeUA(NoFeatureConfig::deserialize, false, false);
	   public static HugeTreesFeature<NoFeatureConfig> MEGA_PINE_TREE_2_UA = new MegaPineTreeUA(NoFeatureConfig::deserialize, false, true);
	   public static AbstractTreeFeature<NoFeatureConfig> DARK_FOREST_M_TREE = new DarkOakMTreeUA(NoFeatureConfig::deserialize, false);
	   public static AbstractTreeFeature<NoFeatureConfig> TAIGA_M_TREE = new TaigaTreeMutatedUA(NoFeatureConfig::deserialize, false);
	   public static AbstractTreeFeature<NoFeatureConfig> END_TREE = new EndTreeUA(NoFeatureConfig::deserialize, false);

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
	   public static Structure<VillageUAConfig> VILLAGE_UA = new VillageUA(VillageUAConfig::deserialize);
	   public static Structure<PillagerOutpostConfig> PILLAGER_OUTPOST_UA = new PillagerOutpostUA(PillagerOutpostConfig::deserialize);
	   
	   
	@SuppressWarnings("unchecked")
	@SubscribeEvent
	public static void onRegisterFeature(RegistryEvent.Register<Feature<?>> event)
	{
		if(registry == null)
			registry = event.getRegistry();

		UltraAmplified.Logger.debug("FEATURE REGISTER");

		   BETTER_CACTUS = registerFeature(BETTER_CACTUS, "better_cactus_ua");
		   GENERIC_DUNGEONS = registerFeature(GENERIC_DUNGEONS, "dungeon_default_ua");
		   MESA_DUNGEONS = registerFeature(MESA_DUNGEONS, "dungeons_badlands_ua");
		   DARK_FOREST_DUNGEONS = registerFeature(DARK_FOREST_DUNGEONS, "dungeons_dark_forest_ua");
		   DESERT_DUNGEONS = registerFeature(DESERT_DUNGEONS, "dungeons_desert_ua");
		   END_DUNGEONS = registerFeature(END_DUNGEONS, "dungeons_end_ua");
		   NETHER_DUNGEONS = registerFeature(NETHER_DUNGEONS, "dungeons_nether_ua");
		   SNOW_DUNGEONS = registerFeature(SNOW_DUNGEONS, "dungeons_snow_ua");
		   SWAMP_DUNGEONS = registerFeature(SWAMP_DUNGEONS, "dungeons_swamp_ua");
		   MUSHROOM_DUNGEONS = registerFeature(MUSHROOM_DUNGEONS, "dungeons_mushroom_ua");
		   CROSS = registerFeature(CROSS, "swamp_cross_ua");
		   HAY_BALE = registerFeature(HAY_BALE, "hay_bale_pile_ua");
		   TINY_HAY_BALE = registerFeature(TINY_HAY_BALE, "hay_bale_tiny_pile_ua");
		   STONEHENGE = registerFeature(STONEHENGE, "stonehenge_ua");
		   SUN_SHRINE = registerFeature(SUN_SHRINE, "sun_shrine_ua");
		   FOSSILS_UA = registerFeature(FOSSILS_UA, "fossil_ua");
		   SINGLE_BLOCK = registerFeature(SINGLE_BLOCK, "single_block_ua");
		   ICEBERG_UA = registerFeature(ICEBERG_UA, "iceberg_ua");
		   MARKED_TREASURE_CHEST_UA = registerFeature(MARKED_TREASURE_CHEST_UA, "marked_treasure_chest_ua");
		   LARGE_STACKABLE_BOULDER = registerFeature(LARGE_STACKABLE_BOULDER, "giant_stackable_boulder_ua");
		   LARGE_BOULDER = registerFeature(LARGE_BOULDER, "boulder_giant_ua");
		   MEDIUM_BOULDER = registerFeature(MEDIUM_BOULDER, "boulder_normal_ua");
		   SMALL_BOULDER = registerFeature(SMALL_BOULDER, "boulder_tiny_ua");
		   SLIME_AND_ICE_LAKE = registerFeature(SLIME_AND_ICE_LAKE, "slime_lakes_ua");
		   SHALLOW_LAKE = registerFeature(SHALLOW_LAKE, "wide_shallow_lakes_ua");
		   CONTAIN_LIQUID = registerFeature(CONTAIN_LIQUID, "contain_water_for_oceans_ua");
		   ICE_PATCH_SANDY = registerFeature(ICE_PATCH_SANDY, "ice_patch_ua");
		   GIANT_ICE_SPIKE = registerFeature(GIANT_ICE_SPIKE, "ice_spike_ua");
		   GREEN_CONCRETE_POWDER_PATCH = registerFeature(GREEN_CONCRETE_POWDER_PATCH, "green_pow_concrete_patch_ua");
		   DISK_DRY = registerFeature(DISK_DRY, "disk_dry_ua");
		   COLUMN = registerFeature(COLUMN, "column_ua");
		   RAMP = registerFeature(RAMP, "ramp_column_ua");
		   GLOWPATCH = registerFeature(GLOWPATCH, "glowpatch_ua");
		   BLUE_ICE_WATERFALL = registerFeature(BLUE_ICE_WATERFALL, "blue_ice_waterfall_ua");
		   CEILING_FLUID = registerFeature(CEILING_FLUID, "ceiling_fluid_ua");
		   ICE_AND_SNOW_UNDER_LEDGES = registerFeature(ICE_AND_SNOW_UNDER_LEDGES, "ice_and_snow_at_all_layer_ua");
		   SNOW_FIXED = registerFeature(SNOW_FIXED, "cold_ocean_snow_feature_ua");
		   LONG_VINES = registerFeature(LONG_VINES, "vines_long_ua");
		   SHORT_VINES = registerFeature(SHORT_VINES, "vines_short_ua");
		   SEA_GRASS_UA = registerFeature(SEA_GRASS_UA, "sea_grass_ua");
		   KELP_UA = registerFeature(KELP_UA, "kelp_ua");
		   SEA_PICKLE_UA = registerFeature(SEA_PICKLE_UA, "sea_pickle_ua");
		   CORAL_CLAW_UA = registerFeature(CORAL_CLAW_UA, "coral_claw_ua");
		   CORAL_TREE_UA = registerFeature(CORAL_TREE_UA, "coral_tree_ua");
		   CORAL_MUSHROOM_UA = registerFeature(CORAL_MUSHROOM_UA, "coral_mushroom_ua");
		   BAMBOO_UA = registerFeature(BAMBOO_UA, "bamboo_ua");
		   HORNED_SWAMP_TREE = (AbstractTreeFeature<NoFeatureConfig>) registerFeature(HORNED_SWAMP_TREE, "swamp_tree_mutated_ua");
		   MEGA_BIRCH_TREE = (HugeTreesFeature<NoFeatureConfig>) registerFeature(MEGA_BIRCH_TREE, "birch_m_tree_ua");
		   MEGA_PINE_TREE_1_UA = (HugeTreesFeature<NoFeatureConfig>) registerFeature(MEGA_PINE_TREE_1_UA, "mega_pine_1_tree_ua");
		   MEGA_PINE_TREE_2_UA = (HugeTreesFeature<NoFeatureConfig>) registerFeature(MEGA_PINE_TREE_2_UA, "mega_pine_2_tree_ua");
		   DARK_FOREST_M_TREE = (AbstractTreeFeature<NoFeatureConfig>) registerFeature(DARK_FOREST_M_TREE, "dark_oak_m_tree_ua");
		   TAIGA_M_TREE = (AbstractTreeFeature<NoFeatureConfig>) registerFeature(TAIGA_M_TREE, "taiga_tree_mutated_ua");
		   END_TREE = (AbstractTreeFeature<NoFeatureConfig>) registerFeature(END_TREE, "end_tree_ua");
		   MINESHAFT_UA = (Structure<MineshaftConfigUA>) registerStructure(MINESHAFT_UA, "mineshaft_ua");
		   WOODLAND_MANSION_UA = (Structure<NoFeatureConfig>) registerStructure(WOODLAND_MANSION_UA, "woodland_mansion_ua");
		   JUNGLE_TEMPLE_UA = (Structure<NoFeatureConfig>) registerStructure(JUNGLE_TEMPLE_UA, "jungle_temple_ua");
		   DESERT_TEMPLE_UA = (Structure<NoFeatureConfig>) registerStructure(DESERT_TEMPLE_UA, "desert_temple_ua");
		   IGLOO_UA = (Structure<NoFeatureConfig>) registerStructure(IGLOO_UA, "igloo_ua");
		   SHIPWRECK_UA = (Structure<ShipwreckConfig>) registerStructure(SHIPWRECK_UA, "shipwreck_ua");
		   WITCH_HUT_UA = (WitchHutUA) registerStructure(WITCH_HUT_UA, "witch_hut_ua");
		   STRONGHOLD_UA = (Structure<NoFeatureConfig>) registerStructure(STRONGHOLD_UA, "stronghold_ua");
		   OCEAN_MONUMENT_UA = (Structure<NoFeatureConfig>) registerStructure(OCEAN_MONUMENT_UA, "ocean_monument_ua");
		   OCEAN_RUIN_UA = (Structure<OceanRuinConfig>) registerStructure(OCEAN_RUIN_UA, "ocean_ruin_ua");
		   FORTRESS_UA = (Structure<FortressConfigUA>) registerStructure(FORTRESS_UA, "fortress_ua");
		   END_CITY_UA = (Structure<NoFeatureConfig>) registerStructure(END_CITY_UA, "endcity_ua");
		   VILLAGE_UA = (Structure<VillageUAConfig>) registerStructure(VILLAGE_UA, "village_ua");
		   PILLAGER_OUTPOST_UA = (Structure<PillagerOutpostConfig>) registerStructure(PILLAGER_OUTPOST_UA, "pillager_outpost_ua");
		
		   
		   //add structures to the BiMap in Feature so Minecraft can generate our structures
		   Feature.STRUCTURES.put("Mineshaft_UA".toLowerCase(Locale.ROOT), MINESHAFT_UA);
		   Feature.STRUCTURES.put("Woodland_Mansion_UA".toLowerCase(Locale.ROOT), WOODLAND_MANSION_UA);
		   Feature.STRUCTURES.put("Jungle_Temple_UA".toLowerCase(Locale.ROOT), JUNGLE_TEMPLE_UA);
		   Feature.STRUCTURES.put("Desert_Temple_UA".toLowerCase(Locale.ROOT), DESERT_TEMPLE_UA);
		   Feature.STRUCTURES.put("Igloo_UA".toLowerCase(Locale.ROOT), IGLOO_UA);
		   Feature.STRUCTURES.put("Shipwreck_UA".toLowerCase(Locale.ROOT), SHIPWRECK_UA);
		   Feature.STRUCTURES.put("Witch_Hut_UA".toLowerCase(Locale.ROOT), WITCH_HUT_UA);
		   Feature.STRUCTURES.put("Stronghold_UA".toLowerCase(Locale.ROOT), STRONGHOLD_UA);
		   Feature.STRUCTURES.put("Ocean_Monument_UA".toLowerCase(Locale.ROOT), OCEAN_MONUMENT_UA);
		   Feature.STRUCTURES.put("Ocean_Ruin_UA".toLowerCase(Locale.ROOT), OCEAN_RUIN_UA);
		   Feature.STRUCTURES.put("Fortress_UA".toLowerCase(Locale.ROOT), FORTRESS_UA);
		   Feature.STRUCTURES.put("End_City_UA".toLowerCase(Locale.ROOT), END_CITY_UA);
		   Feature.STRUCTURES.put("Village_UA".toLowerCase(Locale.ROOT), VILLAGE_UA);
		   Feature.STRUCTURES.put("Pillager_Outpost_UA".toLowerCase(Locale.ROOT), PILLAGER_OUTPOST_UA);
		   
		   StructureInit.registerStructurePieces();
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
	

   private static Structure<?> registerStructure(Structure<?> p_215141_1_, String key) {
      return Registry.register(Registry.STRUCTURE_FEATURE, key.toLowerCase(Locale.ROOT), p_215141_1_);
   }

	private static IForgeRegistry<Feature<?>> registry;
}

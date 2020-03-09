package net.telepathicgrunt.ultraamplified.world.biome;

import java.util.Map.Entry;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.structure.BuriedTreasureConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinConfig;
import net.minecraft.world.gen.feature.structure.OceanRuinStructure;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.VillageConfig;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;
import net.telepathicgrunt.ultraamplified.world.feature.placement.UAPlacements;
import net.telepathicgrunt.ultraamplified.world.feature.structure.FortressConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.MineshaftStructureUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillageConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.structure.VillagePastStyledPiecesUA;


public class UABiome extends Biome
{
	
	protected UABiome(Biome.Builder biomeBuilder)
	{
		super(biomeBuilder);
	}


	protected void addStructureFeaturesUA()
	{
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, UAFeatures.MINESHAFT.configure(new MineshaftConfigUA(MineshaftStructureUA.Type.NORMAL)).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, UAFeatures.STRONGHOLD.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, Feature.VILLAGE.configure(new VillageConfig("village/plains/town_centers", 6)).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.VILLAGE.configure(new VillageConfigUA(0, VillagePastStyledPiecesUA.Type.OAK)).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_DECORATION, UAFeatures.FORTRESS.configure(new FortressConfigUA(false)).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.DESERT_TEMPLE.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.OCEAN_RUIN.configure(new OceanRuinConfig(OceanRuinStructure.Type.COLD, 0.3F, 0.9F)).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.OCEAN_MONUMENT.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.SHIPWRECK.configure(new ShipwreckConfig(false)).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.END_CITY.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.IGLOO.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.JUNGLE_TEMPLE.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.WITCH_HUT.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.WOODLAND_MANSION.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.UNDERGROUND_STRUCTURES, Feature.BURIED_TREASURE.configure(new BuriedTreasureConfig(0.01F)).createDecoratedFeature(UAPlacements.PASSTHROUGH_CHEST.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.PILLAGER_OUTPOST.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.MUSHROOM_TEMPLE.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
		this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, UAFeatures.ICE_SPIKE_TEMPLE.configure(IFeatureConfig.NO_FEATURE_CONFIG).createDecoratedFeature(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
	}


	public <C extends IFeatureConfig> void addStructure(Entry<Structure<?>, IFeatureConfig> structureEntry)
	{
		this.structures.put(structureEntry.getKey(), structureEntry.getValue());
	}
}

package net.telepathicgrunt.ultraamplified;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.feature.Feature;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.MissingMappings;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataFixer {

	//Converts from old namespace to new namespace
	@SubscribeEvent
    public void missingMappingItem(RegistryEvent.MissingMappings<Item> event) {
        for (MissingMappings.Mapping<Item> entry : event.getAllMappings()) {
            if (entry.key.getNamespace().equals("ultra_amplified_mod"))  {
                ResourceLocation newRL = new ResourceLocation(UltraAmplified.MODID + ":" + entry.key.getPath());
                entry.remap(ForgeRegistries.ITEMS.getValue(newRL));
            }
        }
    }

	@SubscribeEvent
    public void missingMappingBlock(RegistryEvent.MissingMappings<Block> event) {
        for (MissingMappings.Mapping<Block> entry : event.getAllMappings()) {
            if (entry.key.getNamespace().equals("ultra_amplified_mod"))  {
                ResourceLocation newRL = new ResourceLocation(UltraAmplified.MODID + ":" + entry.key.getPath());
                entry.remap(ForgeRegistries.BLOCKS.getValue(newRL));
            }
        }
    }

	@SubscribeEvent
    public void missingMappingFeature(RegistryEvent.MissingMappings<Feature<?>> event) {
        for (MissingMappings.Mapping<Feature<?>> entry : event.getAllMappings()) {
            if (entry.key.getNamespace().equals("ultra_amplified_mod"))  {
                ResourceLocation newRL = new ResourceLocation(UltraAmplified.MODID + ":" + entry.key.getPath());
                entry.remap(ForgeRegistries.FEATURES.getValue(newRL));
            }
        }
    }
	
	@SubscribeEvent
    public void missingMappingDimension(RegistryEvent.MissingMappings<ModDimension> event) {
        for (MissingMappings.Mapping<ModDimension> entry : event.getAllMappings()) {
            if (entry.key.getNamespace().equals("ultra_amplified_mod"))  {
                ResourceLocation newRL = new ResourceLocation(UltraAmplified.MODID + ":" + entry.key.getPath());
                entry.remap(ForgeRegistries.MOD_DIMENSIONS.getValue(newRL));
            }
        }
    }

	@SubscribeEvent
    public void missingMappingChunkgenerator(RegistryEvent.MissingMappings<ChunkGeneratorType<?,?>> event) {
        for (MissingMappings.Mapping<ChunkGeneratorType<?,?>> entry : event.getAllMappings()) {
            if (entry.key.getNamespace().equals("ultra_amplified_mod"))  {
                ResourceLocation newRL = new ResourceLocation(UltraAmplified.MODID + ":" + entry.key.getPath());
                entry.remap(ForgeRegistries.CHUNK_GENERATOR_TYPES.getValue(newRL));
            }
        }
    }

	@SubscribeEvent
    public void missingMappingBiomeProvider(RegistryEvent.MissingMappings<BiomeProviderType<?,?>> event) {
        for (MissingMappings.Mapping<BiomeProviderType<?,?>> entry : event.getAllMappings()) {
            if (entry.key.getNamespace().equals("ultra_amplified_mod"))  {
                ResourceLocation newRL = new ResourceLocation(UltraAmplified.MODID + ":" + entry.key.getPath());
                entry.remap(ForgeRegistries.BIOME_PROVIDER_TYPES.getValue(newRL));
            }
        }
    }
	
	
	//convert to new namespace and new biome names
	@SubscribeEvent
    public void missingMappingBiomes(RegistryEvent.MissingMappings<Biome> event) {
        for (MissingMappings.Mapping<Biome> entry : event.getAllMappings()) {
            if (entry.key.getNamespace().equals("ultra_amplified_mod"))  {
            	ResourceLocation newRL;
            	
                if(entry.key.getPath().equals("savanna_plateau")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "savanna_terrace");
                }
                else if(entry.key.getPath().equals("shattered_savanna_plateau")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "shattered_savanna_terrace");
                }
                else if(entry.key.getPath().equals("desert_hills")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "relic_desert");
                }
                else if(entry.key.getPath().equals("wooden_hills")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "relic_forest");
                }
                else if(entry.key.getPath().equals("taiga_hills")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "relic_taiga");
                }
                else if(entry.key.getPath().equals("bamboo_jungle_hills")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "relic_bamboo_jungle");
                }
                else if(entry.key.getPath().equals("jungle_hills")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "relic_jungle");
                }
                else if(entry.key.getPath().equals("stone_shore")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "stone_plains");
                }
                else if(entry.key.getPath().equals("snowy_beach")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "frozen_desert");
                }
                else if(entry.key.getPath().equals("birch_forest_hills")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "relic_birch_forest");
                }
                else if(entry.key.getPath().equals("snowy_taiga_hills")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "relic_snowy_taiga");
                }
                else if(entry.key.getPath().equals("wooded_badlands_plateau")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "wooded_badlands");
                }
                else if(entry.key.getPath().equals("bandlands_plateau")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "sandless_badlands");
                }
                else if(entry.key.getPath().equals("swampland_hills")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "spooky_swamp");
                }
                else if(entry.key.getPath().equals("modified_jungle")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "flower_jungle");
                }
                else if(entry.key.getPath().equals("modified_jungle_edge")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "flower_jungle_edge");
                }
                else if(entry.key.getPath().equals("tall_birch_forest_hills")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "relic_tall_birch_forest");
                }
                else if(entry.key.getPath().equals("dark_forest_hills")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "relic_dark_forest");
                }
                else if(entry.key.getPath().equals("giant_spruce_taiga_hills")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "relic_giant_spruce_taiga_pillars");
                }
                else if(entry.key.getPath().equals("giant_spruce_taiga")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "giant_spruce_taiga_pillars");
                }
                else if(entry.key.getPath().equals("modified_wooded_badlands_plateau")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "densed_wooded_badlands");
                }
                else if(entry.key.getPath().equals("eroded_badlands")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "spiky_badlands");
                }
                else if(entry.key.getPath().equals("modified_badlands_plateau")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "badlands_dissected_plateau");
                }
                else if(entry.key.getPath().equals("the_end")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "end_field");
                }
                else if(entry.key.getPath().equals("nether")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "netherland");
                }
                else if(entry.key.getPath().equals("taiga_mountains")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "rocky_taiga");
                }
                else if(entry.key.getPath().equals("snowy_taiga_mountains")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "snowy_rocky_taiga");
                }
                else if(entry.key.getPath().equals("ice_mountain")) {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + "iced_terrain");
                }
                else {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + entry.key.getPath());
                }
                
                entry.remap(ForgeRegistries.BIOMES.getValue(newRL));
            }
        }
    }
}

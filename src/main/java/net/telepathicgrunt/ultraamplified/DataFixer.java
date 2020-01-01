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
                else {
                    newRL = new ResourceLocation(UltraAmplified.MODID + ":" + entry.key.getPath());
                }
                
                entry.remap(ForgeRegistries.BIOMES.getValue(newRL));
            }
        }
    }
}

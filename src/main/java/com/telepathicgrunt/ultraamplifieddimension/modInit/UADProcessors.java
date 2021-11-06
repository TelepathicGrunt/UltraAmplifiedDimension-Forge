package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.world.processors.CeilingVinePostProcessor;
import com.telepathicgrunt.ultraamplifieddimension.world.processors.RemoveFloatingBlocksProcessor;
import com.telepathicgrunt.ultraamplifieddimension.world.processors.ReplaceAirOnlyProcessor;
import com.telepathicgrunt.ultraamplifieddimension.world.processors.ReplaceLiquidOnlyProcessor;
import com.telepathicgrunt.ultraamplifieddimension.world.processors.SpawnerRandomizingProcessor;
import com.telepathicgrunt.ultraamplifieddimension.world.processors.WallVinePostProcessor;
import com.telepathicgrunt.ultraamplifieddimension.world.processors.WaterloggingFixProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.template.IStructureProcessorType;

public class UADProcessors {

    public static IStructureProcessorType<WaterloggingFixProcessor> WATER_FIX_PROCESSOR = () -> WaterloggingFixProcessor.CODEC;
    public static IStructureProcessorType<ReplaceAirOnlyProcessor> REPLACE_AIR_ONLY_PROCESSOR = () -> ReplaceAirOnlyProcessor.CODEC;
    public static IStructureProcessorType<ReplaceLiquidOnlyProcessor> REPLACE_LIQUIDS_ONLY_PROCESSOR = () -> ReplaceLiquidOnlyProcessor.CODEC;
    public static IStructureProcessorType<RemoveFloatingBlocksProcessor> REMOVE_FLOATING_BLOCKS_PROCESSOR = () -> RemoveFloatingBlocksProcessor.CODEC;
    public static IStructureProcessorType<SpawnerRandomizingProcessor> SPAWNER_RANDOMIZING_PROCESSOR = () -> SpawnerRandomizingProcessor.CODEC;

    public static IStructureProcessorType<WallVinePostProcessor> WALL_VINE_POST_PROCESSOR = () -> WallVinePostProcessor.CODEC;
    public static IStructureProcessorType<CeilingVinePostProcessor> CEILING_VINE_POST_PROCESSOR = () -> CeilingVinePostProcessor.CODEC;

    public static void registerProcessors() {
        Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(UltraAmplifiedDimension.MODID, "water_fix_processor"), WATER_FIX_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(UltraAmplifiedDimension.MODID, "replace_air_only_processor"), REPLACE_AIR_ONLY_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(UltraAmplifiedDimension.MODID, "replace_liquids_only_processor"), REPLACE_LIQUIDS_ONLY_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(UltraAmplifiedDimension.MODID, "remove_floating_blocks_processor"), REMOVE_FLOATING_BLOCKS_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(UltraAmplifiedDimension.MODID, "spawner_randomizing_processor"), SPAWNER_RANDOMIZING_PROCESSOR);

        Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(UltraAmplifiedDimension.MODID, "wall_vine_post_processor"), WALL_VINE_POST_PROCESSOR);
        Registry.register(Registry.STRUCTURE_PROCESSOR, new ResourceLocation(UltraAmplifiedDimension.MODID, "ceiling_vine_post_processor"), CEILING_VINE_POST_PROCESSOR);
    }
}

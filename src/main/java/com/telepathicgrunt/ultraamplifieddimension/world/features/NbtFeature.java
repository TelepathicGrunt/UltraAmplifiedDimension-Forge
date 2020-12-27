package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.utils.GeneralUtils;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.NbtFeatureConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;


public class NbtFeature extends Feature<NbtFeatureConfig> {

    public NbtFeature(Codec<NbtFeatureConfig> configFactory) {
        super(configFactory);
    }

    private final BlockIgnoreStructureProcessor IGNORE_STRUCTURE_VOID = new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.STRUCTURE_VOID));
    private final PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).addProcessor(IGNORE_STRUCTURE_VOID).setIgnoreEntities(false);


    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, NbtFeatureConfig config) {

        // Person wants an empty feature for some reason.
        if (config.nbtResourcelocationsAndWeights.size() == 0) {
            return false;
        }

        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable().setPos(position);

        // Makes sure it generates with land around it instead of cutting into cliffs or hanging over an edge by checking if block at north, east, west, and south are acceptable terrain blocks that appear only at top of land.
        int radius = config.solidLandRadius;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (Math.abs(x * z) > radius && Math.abs(x * z) < radius * 2) {
                    blockpos$Mutable.setPos(position).move(-x, -1, -z);
                    if (!world.getBlockState(blockpos$Mutable).isSolid()) {
                        return false;
                    }
                    //world.setBlockState(blockpos$Mutable.up(), Blocks.REDSTONE_BLOCK.getDefaultState(), 2);
                }
            }
        }

        TemplateManager templatemanager = world.getWorld().getServer().getTemplateManager();
        ResourceLocation nbtRL = GeneralUtils.getRandomEntry(config.nbtResourcelocationsAndWeights);
        Template template = templatemanager.getTemplate(nbtRL);

        if (template == null) {
            UltraAmplifiedDimension.LOGGER.warn(config.nbtResourcelocationsAndWeights.toString() + " NTB does not exist!");
            return false;
        }

        BlockPos halfLengths = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
        placementsettings.setRotation(Rotation.randomRotation(rand)).setCenterOffset(halfLengths).setIgnoreEntities(false);
        if(config.processor != null){
            config.processor.get().func_242919_a().forEach(placementsettings::addProcessor);
        }
        template.func_237152_b_(world, blockpos$Mutable.setPos(position).move(-halfLengths.getX(), 0, -halfLengths.getZ()), placementsettings, rand);

        return true;
    }
}

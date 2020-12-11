package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.NbtFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.block.RailBlock;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.*;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;


public class NbtFeature extends Feature<NbtFeatureConfig> {

    public NbtFeature(Codec<NbtFeatureConfig> configFactory) {
        super(configFactory);
    }

    private final BlockIgnoreStructureProcessor IGNORE_STRUCTURE_VOID = new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.STRUCTURE_VOID));
    private final PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).addProcessor(IGNORE_STRUCTURE_VOID).setIgnoreEntities(false);


    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, NbtFeatureConfig config) {

        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable().setPos(position);

        //makes sure this shrine does not spawn too close to world height border or it will get cut off.
        //Also makes sure it generates with land around it instead of cutting into cliffs or hanging over an edge by checking if block at north, east, west, and south are acceptable terrain blocks that appear only at top of land.
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
        Template template = templatemanager.getTemplate(config.nbtResourcelocation);

        if (template == null) {
            UltraAmplifiedDimension.LOGGER.warn(config.nbtResourcelocation.toString() + " NTB does not exist!");
            return false;
        }

        BlockPos offset = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
        placementsettings.setRotation(Rotation.randomRotation(rand)).setCenterOffset(offset);
        template.func_237152_b_(world, blockpos$Mutable.move(offset.getX(), 0, offset.getZ()), placementsettings, rand);

        return true;
    }
}

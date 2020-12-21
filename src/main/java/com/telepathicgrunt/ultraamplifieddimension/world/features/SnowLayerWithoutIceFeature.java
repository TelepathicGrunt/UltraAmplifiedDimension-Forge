package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;


public class SnowLayerWithoutIceFeature extends Feature<NoFeatureConfig> {
    public SnowLayerWithoutIceFeature(Codec<NoFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, NoFeatureConfig config) {
        BlockPos.Mutable blockposMutable1 = new BlockPos.Mutable();
        BlockPos.Mutable blockposMutable2 = new BlockPos.Mutable();
        IChunk cachedChunk = world.getChunk(position);

        int y = cachedChunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, position.getX(), position.getZ()) + 1;
        blockposMutable1.setPos(position.getX(), y, position.getZ());
        blockposMutable2.setPos(blockposMutable1).move(Direction.DOWN);
        BlockState blockState1 = cachedChunk.getBlockState(blockposMutable1);

        if (blockState1.isAir() && Blocks.SNOW.getDefaultState().isValidPosition(world, blockposMutable1)) {

            cachedChunk.setBlockState(blockposMutable1, Blocks.SNOW.getDefaultState(), false);
            BlockState blockState2 = cachedChunk.getBlockState(blockposMutable2);

            if (blockState2.hasProperty(SnowyDirtBlock.SNOWY)) {
                cachedChunk.setBlockState(blockposMutable2, blockState2.with(SnowyDirtBlock.SNOWY, true), false);
            }
        }

        return true;
    }
}
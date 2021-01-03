package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;


public class TriggerBubbleColumns extends Feature<NoFeatureConfig> {
    public TriggerBubbleColumns(Codec<NoFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, NoFeatureConfig configBlock) {

        BlockPos.Mutable blockposMutable = new BlockPos.Mutable(position.getX(), 0, position.getZ());  //set y to 0
        IChunk chunk = world.getChunk(position.getX() >> 4, position.getZ() >> 4);

        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {

                if (world.getBiome(blockposMutable.setPos(position).move(x, chunkGenerator.getSeaLevel() - 7, z)).getCategory() != Biome.Category.NETHER) {
                    continue;
                }

                BlockState prevBlockState = Blocks.AIR.getDefaultState();
                for (int y = chunkGenerator.getSeaLevel() - 7; y <= chunkGenerator.getSeaLevel(); ++y) {
                    BlockState currentBlockState = chunk.getBlockState(blockposMutable);

                    if(currentBlockState.getFluidState().isTagged(FluidTags.WATER) &&
                        (prevBlockState.isIn(Blocks.MAGMA_BLOCK) || prevBlockState.isIn(Blocks.BUBBLE_COLUMN)))
                    {
                        chunk.setBlockState(blockposMutable, Blocks.BUBBLE_COLUMN.getDefaultState(), false);
                        prevBlockState = Blocks.BUBBLE_COLUMN.getDefaultState();
                    }
                    else{
                        prevBlockState = currentBlockState;
                    }

                    blockposMutable.move(Direction.UP);
                }
            }
        }

        return true;
    }
}
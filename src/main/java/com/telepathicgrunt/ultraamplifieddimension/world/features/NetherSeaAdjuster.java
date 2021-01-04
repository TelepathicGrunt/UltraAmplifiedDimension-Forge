package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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


public class NetherSeaAdjuster extends Feature<NoFeatureConfig> {
    public NetherSeaAdjuster(Codec<NoFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, NoFeatureConfig configBlock) {

        BlockPos.Mutable blockposMutable = new BlockPos.Mutable(position.getX(), 0, position.getZ());  //set y to 0
        BlockPos.Mutable blockposMutableTemp = new BlockPos.Mutable();
        IChunk cachedChunk = world.getChunk(position.getX() >> 4, position.getZ() >> 4);

        // We have to check quit a bit outward due to delta features placing lava WAY outside their chunk.
        for (int x = -6; x < 22; ++x) {
            for (int z = -6; z < 22; ++z) {

                // Run only in nether biomes
                if (world.getBiome(blockposMutable.setPos(position).move(x, chunkGenerator.getSeaLevel() - 7, z)).getCategory() != Biome.Category.NETHER) {
                    continue;
                }

                if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z){
                    cachedChunk = world.getChunk(blockposMutable);
                }

                BlockState prevBlockState = Blocks.AIR.getDefaultState();
                // Nether biomes gets only 7 block thick water above magma blocks.
                // But the water will not be bubble columns unless updated.
                // We will set the bubble columns manually.
                for (int y = chunkGenerator.getSeaLevel() - 7; y <= chunkGenerator.getSeaLevel(); ++y) {

                    BlockState currentBlockState = cachedChunk.getBlockState(blockposMutable);
                    if(currentBlockState.getFluidState().isTagged(FluidTags.WATER)){

                        // Make sure water isn't touching lava tagged blocks by turning water to obsidian.
                        for(Direction direction : Direction.values()){
                            blockposMutableTemp.setPos(blockposMutable).move(direction);
                            BlockState neighboringBlock;
                            if(blockposMutableTemp.getX() >> 4 != cachedChunk.getPos().x || blockposMutableTemp.getZ() >> 4 != cachedChunk.getPos().z){
                                neighboringBlock = world.getBlockState(blockposMutableTemp);
                            }
                            else{
                                neighboringBlock = cachedChunk.getBlockState(blockposMutableTemp);
                            }

                            if(neighboringBlock.getFluidState().isTagged(FluidTags.LAVA)){
                                cachedChunk.setBlockState(blockposMutableTemp, Blocks.OBSIDIAN.getDefaultState(), false);
                                prevBlockState = Blocks.OBSIDIAN.getDefaultState();
                                blockposMutable.move(Direction.UP);


                                break;
                            }
                        }

                        // Set bubble columns upward from magma blocks.
                        // If we set obsidian before, this gets skipped.
                        if(prevBlockState.isIn(Blocks.MAGMA_BLOCK) || prevBlockState.isIn(Blocks.BUBBLE_COLUMN)){
                            cachedChunk.setBlockState(blockposMutable, Blocks.BUBBLE_COLUMN.getDefaultState(), false);
                            prevBlockState = Blocks.BUBBLE_COLUMN.getDefaultState();
                            blockposMutable.move(Direction.UP);
                            continue;
                        }
                    }

                    prevBlockState = currentBlockState;
                    blockposMutable.move(Direction.UP);
                }
            }
        }

        return true;
    }
}
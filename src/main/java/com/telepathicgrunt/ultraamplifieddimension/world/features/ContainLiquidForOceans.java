package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;


public class ContainLiquidForOceans extends Feature<NoFeatureConfig> {
    public ContainLiquidForOceans(Codec<NoFeatureConfig> configFactory) {
        super(configFactory);
    }

    private final static BlockState ICE = Blocks.ICE.getDefaultState();
    private final static BlockState SNOW = Blocks.SNOW.getDefaultState();

    private final static BlockState[] DEAD_CORAL_ARRAY = {
            Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState(),
            Blocks.DEAD_BRAIN_CORAL_BLOCK.getDefaultState(),
            Blocks.DEAD_BUBBLE_CORAL_BLOCK.getDefaultState(),
            Blocks.DEAD_FIRE_CORAL_BLOCK.getDefaultState(),
            Blocks.DEAD_TUBE_CORAL_BLOCK.getDefaultState()
    };


    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, NoFeatureConfig configBlock) {
        //checks to see if there is an ocean biome in this chunk
        //breaks out of nested loop if ocean if found so oceanBiome holds the ocean
        Biome oceanBiome = getOceanInChunk(world, position);

        //does not do anything if there is no ocean biome
        if (oceanBiome == null) {
            return false;
        }

        int sealevel = world.getWorld().getSeaLevel();
        boolean containedFlag;
        BlockState currentblock;
        BlockState blockAbove;
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position.getX(), 0, position.getZ());  //set y to 0
        BlockPos.Mutable blockpos$MutableAbove = new BlockPos.Mutable().setPos(blockpos$Mutable);
        IChunk chunk = world.getChunk(position.getX() >> 4, position.getZ() >> 4);

        //ocean biome was found and thus, is not null. Can safely contain all water in this chunk
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                BlockState oceanTopBlock = oceanBiome.getGenerationSettings().getSurfaceBuilderConfig().getTop();
                BlockState oceanUnderBlock = oceanBiome.getGenerationSettings().getSurfaceBuilderConfig().getUnder();

                boolean useCoralTop = oceanTopBlock == DEAD_CORAL_ARRAY[0];
                boolean useCoralBottom = oceanTopBlock == DEAD_CORAL_ARRAY[0];
                blockpos$Mutable.setPos(position.getX() + x, 256, position.getZ() + z);

                for (; blockpos$Mutable.getY() >= sealevel; blockpos$Mutable.move(Direction.DOWN)) {

                    currentblock = chunk.getBlockState(blockpos$Mutable);

                    //move down until we hit a liquid block
                    while (currentblock.getFluidState().isEmpty() && blockpos$Mutable.getY() >= sealevel) {
                        blockpos$Mutable.move(Direction.DOWN);
                        currentblock = chunk.getBlockState(blockpos$Mutable);
                    }

                    //too low now, break out of the loop and move to next xz coordinate
                    if (blockpos$Mutable.getY() < sealevel) {
                        break;
                    }
                    //y value is now fully set for rest of code

                    /*
                     * // Keep this here in case we want to change behavior later // Must be solid all around even diagonally for(int x2 =
                     * -1; x2 < 2; x2++) { for(int z2 = -1; z2 < 2; z2++) {
                     *
                     * material = world.getBlockState(blockpos$Mutable.west(x2).north(z2)).getMaterial();
                     *
                     * if(!material.isSolid() && material != Material.WATER ) { notContainedFlag = true; } } }
                     */

                    //Adjacent blocks must be solid
                    containedFlag = true;
                    for (Direction face : Direction.Plane.HORIZONTAL) {
                        //Do world instead of chunk as this could check into the next chunk over.
                        currentblock = world.getBlockState(blockpos$Mutable.offset(face));

                        // If the block is snow or not solid without liquid, set contains to false.
                        // Yes, snow layers are considered solid and need a second check.
                        if ((!currentblock.isSolid() && currentblock.getFluidState().isEmpty() && currentblock != ICE) ||
                            currentblock == SNOW)
                        {
                            containedFlag = false;
                            break;
                        }
                    }

                    blockpos$MutableAbove.setPos(blockpos$Mutable).move(Direction.UP);

                    if (containedFlag) {
                        //water block is contained

                        blockAbove = chunk.getBlockState(blockpos$MutableAbove);

                        //if above is middle block, replace above block with third config block so middle block (sand/gravel) cannot fall.
                        if (blockAbove == oceanUnderBlock) {
                            if (useCoralBottom || !(oceanBiome.getGenerationSettings().getSurfaceBuilderConfig() instanceof SurfaceBuilderConfig)) {
                                chunk.setBlockState(blockpos$MutableAbove, DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], false);
                            }
                            else {
                                chunk.setBlockState(blockpos$MutableAbove, ((SurfaceBuilderConfig) oceanBiome.getGenerationSettings().getSurfaceBuilderConfig()).getUnderWaterMaterial(), false);
                            }
                        }
                    }
                    else {
                        //water is not contained
                        if (blockpos$Mutable.getY() < 256) {
                            blockAbove = chunk.getBlockState(blockpos$MutableAbove);

                            if (blockAbove.isSolid() || !blockAbove.getFluidState().isEmpty()) {

                                //if above is solid or water, place second config block
                                chunk.setBlockState(blockpos$Mutable, oceanUnderBlock, false);
                            }

                            //place first config block if no solid block above and below
                            else {
                                //if config top block is dead coral, randomly chooses any dead coral block to place
                                if (useCoralTop) {
                                    chunk.setBlockState(blockpos$Mutable, DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], false);
                                }
                                else {
                                    chunk.setBlockState(blockpos$Mutable, oceanTopBlock, false);
                                }
                            }
                        }

                        //place first config block if too high
                        //if config top block is dead coral, randomly chooses any dead coral block to place
                        else if (useCoralTop) {
                            chunk.setBlockState(blockpos$Mutable, DEAD_CORAL_ARRAY[random.nextInt(DEAD_CORAL_ARRAY.length)], false);
                        }
                        else {
                            chunk.setBlockState(blockpos$Mutable, oceanTopBlock, false);
                        }
                    }

                }
            }
        }
        return true;

    }


    /**
     * Gets the first ocean biome found within the edges of the chunk.
     */
    private Biome getOceanInChunk(IWorld world, BlockPos originalPosition) {
        Biome biomeAtLocation;
        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable();

        //checks to see if there is an ocean biome in this chunk
        //breaks out of nested loop if ocean if found so oceanBiome holds the ocean
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                //only check along chunk edges for better performance
                if ((x != 0 && x != 15) && (z != 0 && z != 15)) {
                    continue;
                }

                mutableBlockPos.setPos(originalPosition.getX() + x, 0, originalPosition.getZ() + z);
                biomeAtLocation = world.getBiome(mutableBlockPos);
                if (biomeAtLocation.getCategory() == Biome.Category.OCEAN) {
                    return biomeAtLocation;
                }
            }
        }

        return null;
    }
}
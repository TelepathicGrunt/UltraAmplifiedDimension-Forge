package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
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


public class NetherUnderwaterMagma extends Feature<NoFeatureConfig> {
    public NetherUnderwaterMagma(Codec<NoFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, NoFeatureConfig configBlock) {

        //set y to 0
        pos = pos.down(pos.getY());
        FluidState fluidState;
        boolean hasNetherBiome = false;
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();

        //checks to see if there is an nether biome in this chunk
        //breaks out of nested loop if nether is found
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                //only check along chunk edges for better performance
                if (!(z == 0 || z == 15 || x == 0 || x == 15)) {
                    continue;
                }

                if (world.getBiome(blockpos$Mutable.setPos(pos).move(x, 0, z)).getCategory() == Biome.Category.NETHER) {
                    hasNetherBiome = true;
                    x = 16;
                    break;
                }
            }
        }

        //does not do anything if there is no nether biome
        if (!hasNetherBiome) {
            return false;
        }

        // nether was found and thus, is not null. Can safely now add the magma layer
        IChunk chunk = world.getChunk(pos);
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {

                if (world.getBiome(blockpos$Mutable.setPos(pos).move(x, 0, z)).getCategory() != Biome.Category.NETHER) {
                    continue;
                }

                fluidState = chunk.getBlockState(blockpos$Mutable.setPos(pos).move(x, chunkGenerator.getSeaLevel() - 7, z)).getFluidState();

                //if water, place magma block
                if (!fluidState.isEmpty() && fluidState.isTagged(FluidTags.WATER)) {
                    chunk.setBlockState(blockpos$Mutable, Blocks.MAGMA_BLOCK.getDefaultState(), false);
                }

                //check if lava below is bordering water and set it to obsidian if so
                for (int y = chunkGenerator.getSeaLevel() - 8; y > 10; y--) {

                    for (Direction face : Direction.Plane.HORIZONTAL) {
                        fluidState = chunk.getBlockState(blockpos$Mutable.setPos(pos).move(x, y, z).offset(face)).getFluidState();

                        if (!fluidState.isEmpty() && fluidState.isTagged(FluidTags.WATER)) {
                            chunk.setBlockState(blockpos$Mutable, Blocks.OBSIDIAN.getDefaultState(), false);
                            break;
                        }
                    }
                }
            }
        }

        return true;
    }
}
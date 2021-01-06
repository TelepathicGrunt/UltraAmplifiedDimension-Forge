package com.telepathicgrunt.ultraamplifieddimension.world.surfacebuilder;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;


public class GrassyEndSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public GrassyEndSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }


    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        int xpos = x & 15;
        int zpos = z & 15;
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();

        // We use this so we can set the top half of the dimension to stone and then we can reuse the vanilla default surfacebuilder.
        // That way, bottom half of dimension has End Stone placed by the UAD chunk generator (for all end category biomes)
        // and then the top half of the dimension is Stone which is placed by the code below.
        BlockState altState = Blocks.STONE.getDefaultState();

        //makes stone below sea level into end stone
        for (int ypos = startHeight; ypos >= seaLevel - 2; ypos--) {
            blockpos$Mutable.setPos(xpos, ypos, zpos);
            BlockState iblockstate2 = chunkIn.getBlockState(blockpos$Mutable);

            iblockstate2.getBlock();
            if (iblockstate2.getMaterial() != Material.AIR) {
                if (iblockstate2 == defaultBlock) {
                    chunkIn.setBlockState(blockpos$Mutable, altState, false);
                }
            }
        }

        //creates grass surface only on top half of dimension.
        SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, altState, defaultFluid, seaLevel, seed, config);
    }
}
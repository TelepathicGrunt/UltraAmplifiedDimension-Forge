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


public class EndSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public EndSurfaceBuilder(Codec<SurfaceBuilderConfig> codec) {
        super(codec);
    }


    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        //creates grass surface normally
        SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, config);

        int xpos = x & 15;
        int zpos = z & 15;
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();

        //makes stone below sea level into end stone
        for (int ypos = seaLevel - 2; ypos >= 0; --ypos) {
            blockpos$Mutable.setPos(xpos, ypos, zpos);
            BlockState iblockstate2 = chunkIn.getBlockState(blockpos$Mutable);

            iblockstate2.getBlock();
            if (iblockstate2.getMaterial() != Material.AIR) {
                if (iblockstate2 == defaultBlock) {
                    chunkIn.setBlockState(blockpos$Mutable, Blocks.END_STONE.getDefaultState(), false);
                }
            }
        }
    }
}
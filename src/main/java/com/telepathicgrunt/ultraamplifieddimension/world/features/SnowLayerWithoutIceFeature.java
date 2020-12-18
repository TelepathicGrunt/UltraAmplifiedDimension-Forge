package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
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
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
        BlockPos.Mutable blockpos$Mutable1 = new BlockPos.Mutable();

        int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING, position.getX(), position.getZ());
        blockpos$Mutable.setPos(position.getX(), y, position.getZ());
        blockpos$Mutable1.setPos(blockpos$Mutable).move(Direction.DOWN);
        BlockState iblockstate = world.getBlockState(blockpos$Mutable);

        if (iblockstate.isAir(world, blockpos$Mutable) && Blocks.SNOW.getDefaultState().isValidPosition(world, blockpos$Mutable)) {

            world.setBlockState(blockpos$Mutable, Blocks.SNOW.getDefaultState(), 2);
            BlockState iblockstate2 = world.getBlockState(blockpos$Mutable1);

            if (iblockstate2.hasProperty(SnowyDirtBlock.SNOWY)) {
                world.setBlockState(blockpos$Mutable1, iblockstate2.with(SnowyDirtBlock.SNOWY, Boolean.TRUE), 2);
            }
        }

        return true;
    }
}
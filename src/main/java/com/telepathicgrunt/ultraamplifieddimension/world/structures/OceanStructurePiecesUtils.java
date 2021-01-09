package com.telepathicgrunt.ultraamplifieddimension.world.structures;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.template.Template;

import java.util.Random;

public class OceanStructurePiecesUtils {
    // called in structures/OceanRuinPiecesMixin and structures/ShipwreckPiecesMixin
    public static int getNewLedgeHeight(ISeedReader world, ChunkGenerator chunkGenerator, Random random, Template template, Rotation rotation, BlockPos templatePosition) {

        int highestHeight;
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        //get center of ruins
        int halfSizeX = template.getSize().getX() / 2;
        int halfSizeZ = template.getSize().getZ() / 2;

        mutable.setPos(Template.getTransformedPos(new BlockPos(template.getSize().getX() / 2 - 1, 0, template.getSize().getZ() / 2 - 1), Mirror.NONE, rotation, new BlockPos(0, 0, 0)).add(templatePosition));
        highestHeight = world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, mutable.getX(), mutable.getZ());
        int bottomOfSea = chunkGenerator.getSeaLevel() - 10;
        BlockState currentState;
        BlockState pastState = Blocks.STONE.getDefaultState();

        // Bias towards the ocean floor.
        int startHeight = Math.min(random.nextInt(random.nextInt(Math.max(highestHeight - bottomOfSea, 1)) + 1) + bottomOfSea + 5, 245);

        // Iterate downward until it hits underwater land that can hold the structure
        for(mutable.move(Direction.UP, startHeight);
            mutable.getY() > Math.max(bottomOfSea - 20, 5);
            mutable.move(Direction.DOWN))
        {
            currentState = world.getBlockState(mutable);
            if((currentState.isSolid() && !currentState.isIn(BlockTags.ICE)) && pastState.getFluidState().isTagged(FluidTags.WATER)){
                if(noAirAround(world, mutable.down(), (int) (halfSizeX * 0.8f), (int) (halfSizeZ * 0.8f))){
                    return mutable.getY();
                }
            }
            pastState = currentState;
        }

        // Set structure at bottom of sea if no valid place was found.
        return bottomOfSea;
    }

    public static boolean noAirAround(ISeedReader world, BlockPos blockpos, int xRange, int zRange) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for (int x = -xRange; x <= xRange; x += xRange) {
            for (int z = -zRange; z <= zRange; z += zRange) {
                BlockState state = world.getBlockState(mutable.setPos(blockpos).move(x, 0, z));
                if (state.isAir()) {
                    return false; // No air allowed
                }
            }
        }
        return true;
    }
}

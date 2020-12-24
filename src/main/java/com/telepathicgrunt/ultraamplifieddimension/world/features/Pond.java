package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.blocks.BigCactusBodyBlock;
import com.telepathicgrunt.ultraamplifieddimension.blocks.BigCactusCornerBlock;
import com.telepathicgrunt.ultraamplifieddimension.blocks.BigCactusMainBlock;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import com.telepathicgrunt.ultraamplifieddimension.utils.OpenSimplexNoise;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.HeightConfig;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.PondConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;


public class Pond extends Feature<PondConfig> {

    protected OpenSimplexNoise noiseGen;
    protected long seed;

    public void setSeed(long seed) {
        if (this.seed != seed || this.noiseGen == null) {
            this.noiseGen = new OpenSimplexNoise(seed);
            this.seed = seed;
        }
    }

    public Pond(Codec<PondConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, PondConfig pondConfig) {
        // Set to chunk center as our pond will not cross chunk boundaries.
        BlockPos.Mutable blockpos = new BlockPos.Mutable().setPos((position.getX() >> 4) << 4, position.getY(), (position.getZ() >> 4) << 4);
        blockpos.move(8, 0, 8);
        BlockPos centerPos = blockpos.toImmutable();
        IChunk cachedChunk = world.getChunk(blockpos);

        // Validation checks to make sure lake is in a safe spot to generate
        for(int x = -7; x < 7; x++){
            for(int z = -7; z < 7; z++){
                for(int y = -3; y < 4; y++){
                    double normX = x / 7d;
                    double normY = y / 4d;
                    double normZ = z / 7d;
                    // Check only in a squished sphere space
                    if((normX * normX) + (normY * normY) + (normZ * normZ) <= 0.60d){
                        blockpos.setPos(centerPos).move(x, y, z);
                        BlockState blockState = cachedChunk.getBlockState(blockpos);

                        // No liquids in lake space above
                        if(y >= 0 && !blockState.getFluidState().isEmpty()){
                            return false;
                        }
                        // No air/liquid space in lake space below (allow it's own inside state tho)
                        else if(!blockState.isSolid() && blockState != pondConfig.insideState){
                            return false;
                        }
                    }
                }
            }
        }

        // Setup noise
        setSeed(world.getSeed());
        BlockState aboveState = null;
        for(int x = -8; x < 8; x++){
            for(int z = -8; z < 8; z++){
                for(int y = 4; y >= -4; y--){
                    blockpos.setPos(centerPos).move(x, y, z);
                    double noiseVal = noiseGen.eval(blockpos.getX() * 0.21d, blockpos.getY() * 0.06d, blockpos.getZ() * 0.21d);
                    double normX = x / 8d;
                    double normY = y / 4d;
                    double normZ = z / 8d;
                    double lakeVal = (normX * normX) + (normY * normY) + (normZ * normZ) - ((noiseVal + 1) * 0.9d);

                    if(lakeVal < -0.065d){
                        BlockState block = cachedChunk.getBlockState(blockpos);

                        if(y == 4){

                            if(pondConfig.placeOutsideStateOften && block.isSolid() && rand.nextFloat() < 0.70f){
                                aboveState = cachedChunk.getBlockState(blockpos.move(Direction.UP));
                                blockpos.move(Direction.DOWN);

                                if(aboveState.isAir()){
                                    cachedChunk.setBlockState(blockpos, pondConfig.topState, false);
                                }
                                else{
                                    cachedChunk.setBlockState(blockpos, pondConfig.outsideState, false);
                                }
                            }

                            // Store y == 4 as that block in world must be stored as aboveState
                            aboveState = cachedChunk.getBlockState(blockpos);
                        }

                        if(block.isSolid()){
                            // Edge of chunk and bottom of lake is always solid blocks.
                            // Threshold used for the encasing in outside blockstate.
                            if(x == -8 || z== -8 || x == 7 || z == 7 || lakeVal > -0.48d || y == -4){
                                if(pondConfig.placeOutsideStateOften){
                                    if(aboveState.isAir()){
                                        cachedChunk.setBlockState(blockpos, pondConfig.topState, false);
                                    }
                                    else{
                                        cachedChunk.setBlockState(blockpos, pondConfig.outsideState, false);
                                    }
                                }
                            }
                            else if (y <= 0){
                                cachedChunk.setBlockState(blockpos, pondConfig.insideState, false);

                                for(Direction direction : Direction.values()){
                                    // Will never go into other chunk due to the edge of chunk check above.
                                    // This will contain the liquid as best as possible.
                                    if(direction != Direction.UP){
                                        BlockState blockState = cachedChunk.getBlockState(blockpos.move(direction));
                                        if(!blockState.isSolid() && blockState != pondConfig.insideState){
                                            cachedChunk.setBlockState(blockpos, pondConfig.outsideState, false);
                                        }
                                        blockpos.move(direction.getOpposite());
                                    }
                                    // Prevent stuff like lava ponds getting water placed above it.
                                    else if(!pondConfig.insideState.getFluidState().isEmpty()){
                                        BlockState blockState = cachedChunk.getBlockState(blockpos.move(direction));
                                        if(!blockState.getFluidState().isEmpty() && blockState != pondConfig.insideState){
                                            cachedChunk.setBlockState(blockpos, pondConfig.outsideState, false);
                                        }
                                        blockpos.move(direction.getOpposite());
                                    }
                                }
                            }
                            else {
                                if(!aboveState.getFluidState().isEmpty()){
                                    cachedChunk.setBlockState(blockpos, pondConfig.outsideState, false);
                                }
                                else{
                                    cachedChunk.setBlockState(blockpos, Blocks.CAVE_AIR.getDefaultState(), false);
                                }
                            }

                            // Remove floating plants.
                            while(blockpos.getY() <= chunkGenerator.getMaxBuildHeight() &&
                                    !aboveState.isValidPosition(world, blockpos))
                            {
                                cachedChunk.setBlockState(blockpos, Blocks.AIR.getDefaultState(), false);
                                blockpos.move(Direction.UP);
                            }
                        }
                    }
                    else{
                        // If fail to place lake blocks, store our current block for when we move down 1
                        aboveState = cachedChunk.getBlockState(blockpos);
                    }
                }
                // Reset above state when going to new column
                aboveState = null;
            }
        }

        return true;
    }
}

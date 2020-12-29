package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.utils.GeneralUtils;
import com.telepathicgrunt.ultraamplifieddimension.utils.OpenSimplexNoise;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.BoulderFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;


public class Boulders extends Feature<BoulderFeatureConfig> {

    protected long seed;
    protected static OpenSimplexNoise noiseGen;

    public void setSeed(long seed) {
        if (this.seed != seed || noiseGen == null) {
            noiseGen = new OpenSimplexNoise(seed);
            this.seed = seed;
        }
    }

    public Boulders(Codec<BoulderFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, BoulderFeatureConfig config) {

        BlockPos.Mutable blockposMutable = new BlockPos.Mutable().setPos(position);
        IChunk cachedChunk = world.getChunk(blockposMutable);

        // No boulders on trees or too high up
        if(blockposMutable.getY() > ((chunkGenerator.getMaxBuildHeight() - config.maxRadius) - 2))
        {
            return false;
        }

        setSeed(world.getSeed());
        int maxRadius;
        int minRadius;
        int startRadius;
        int prevHeight = 0;

        for(int stackCount = 0; stackCount < config.boulderStackCount; stackCount++){
            maxRadius = config.maxRadius;
            minRadius = config.minRadius;
            int radiusModifier = (stackCount / (int)Math.ceil(config.boulderStackCount / config.maxRadius));
            maxRadius = Math.max(maxRadius - radiusModifier, 1);
            minRadius = Math.max(minRadius - radiusModifier, 1);
            startRadius = Math.max(random.nextInt(maxRadius - minRadius + 1) + minRadius, 1);

            //we are at a valid spot to generate a boulder now. Begin generation.
            for (int currentCount = 0; currentCount < 3; ++currentCount) {

                // randomizes the x, y, or z by +1/-1/0 independently to make boulders more natural looking
                int x = Math.max(Math.min(startRadius + (random.nextInt(3) - 1), maxRadius), minRadius);
                int y = Math.max(Math.min(startRadius + (random.nextInt(3) - 1), maxRadius), minRadius);
                int z = Math.max(Math.min(startRadius + (random.nextInt(3) - 1), maxRadius), minRadius);

                float calculatedDistance = (x + y + z) * 0.333F + 0.5F;

                // Create the blob of boulder
                for (BlockPos blockpos : BlockPos.getAllInBoxMutable(blockposMutable.add(-x, -y, -z), blockposMutable.add(x, y, z))) {
                    if (blockpos.distanceSq(blockposMutable) <= calculatedDistance * calculatedDistance) {

                        double noiseValue = 1;
                        if(startRadius > 2){
                            noiseValue = noiseGen.eval(blockpos.getX() * 0.035D, blockpos.getY() * 0.0075D, blockpos.getZ() * 0.035D);
                        }
                        if(blockpos.distanceSq(blockposMutable) > calculatedDistance * calculatedDistance * 0.65f &&
                                noiseValue > -0.3D && noiseValue < 0.3D){
                            continue; // Rough the surface of the boulders a bit
                        }

                        if(blockpos.getX() >> 4 != cachedChunk.getPos().x || blockpos.getZ() >> 4 != cachedChunk.getPos().z)
                            cachedChunk = world.getChunk(blockpos);

                        //adds the blocks for generation in this boulder
                        BlockState boulderBlock = GeneralUtils.getRandomEntry(config.blockAndWeights);
                        cachedChunk.setBlockState(blockpos, boulderBlock, false);
                    }
                }

                // Randomizes pos of next blob to help keep boulders from looking samey
                if(config.boulderStackCount > 1){
                    blockposMutable.move(
                            random.nextInt((int) (startRadius * 1.1f)) - (int) (startRadius * 0.55f),
                            random.nextInt((int) (startRadius * 1.1f)) - (int) (startRadius * 0.55f),
                            random.nextInt((int) (startRadius * 1.1f)) - (int) (startRadius * 0.55f));
                }
                else{
                    blockposMutable.move(
                            random.nextInt(startRadius * 2) - startRadius,
                            0,
                            random.nextInt(startRadius * 2) - startRadius);

                    blockposMutable.move(Direction.UP,
                            world.getHeight(Heightmap.Type.OCEAN_FLOOR_WG, blockposMutable.getX(), blockposMutable.getZ())
                                    - random.nextInt(2)
                                    - blockposMutable.getY());
                }
            }

            prevHeight += minRadius;

            // set next boulders on top of previous to do stacking
            blockposMutable.setPos(position).move(
                    random.nextInt((int) (startRadius * 1.1f)) - (int) (startRadius * 0.55f),
                    prevHeight,
                    random.nextInt((int) (startRadius * 1.1f)) - (int) (startRadius * 0.55f));

            if(blockposMutable.getX() >> 4 != cachedChunk.getPos().x || blockposMutable.getZ() >> 4 != cachedChunk.getPos().z)
                cachedChunk = world.getChunk(blockposMutable);

            BlockState currentState = cachedChunk.getBlockState(blockposMutable);
            while(!currentState.isAir()){
                blockposMutable.move(Direction.UP);
                currentState = cachedChunk.getBlockState(blockposMutable);
            }

            if(blockposMutable.getY() > ((chunkGenerator.getMaxBuildHeight() - config.maxRadius) - 2))
            {
                return false;
            }
        }

        //finished generating the boulder
        return true;
    }
}

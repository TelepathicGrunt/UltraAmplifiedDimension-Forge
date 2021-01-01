package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.Set;


public class LakeWideShallow extends Feature<BlockStateFeatureConfig> {

    protected static final Set<Material> unacceptableSolidMaterials = ImmutableSet.of(Material.BAMBOO, Material.BAMBOO_SAPLING, Material.LEAVES, Material.WEB, Material.CACTUS, Material.ANVIL, Material.GOURD, Material.CAKE, Material.DRAGON_EGG, Material.BARRIER, Material.CAKE);

    public LakeWideShallow(Codec<BlockStateFeatureConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, BlockStateFeatureConfig configBlock) {

        BlockPos.Mutable blockposMutable = new BlockPos.Mutable().setPos(position);

        //creates the actual lakes
        boolean containedFlag;
        Material material;
        BlockState blockState;
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int y = 5;

                blockposMutable.setPos(position).move(x, y, z);

                blockState = world.getBlockState(blockposMutable);
                material = blockState.getMaterial();

                //Finds first solid block of land starting from 5 blocks higher than initial input position
                //We use unacceptable solid set to help skip solid blocks like leaves.
                while ((!material.isSolid() || unacceptableSolidMaterials.contains(material) || BlockTags.PLANKS.contains(blockState.getBlock())) && y > 0) {
                    y--;
                    material = world.getBlockState(blockposMutable.move(Direction.DOWN)).getMaterial();
                }

                //checks if the spot is solid all around (diagonally too) and has nothing solid above it
                containedFlag = checkIfValidSpot(world, blockposMutable);


                //Is spot contained and not exposed on ledges
                if (containedFlag) {

                    double normX = (x - 8) / 8d;
                    double normZ = (z - 8) / 8d;
                    double lakeVal = (normX * normX) + (normZ * normZ);

                    if(lakeVal < 0.8d){
                        //check below without moving down
                        blockState = world.getBlockState(blockposMutable.down());

                        //sets the water
                        if ((isDirt(blockState.getBlock()) && random.nextInt(5) == 0)) {
                            world.setBlockState(blockposMutable, Blocks.SEAGRASS.getDefaultState(), 3);
                        }
                        else {
                            world.setBlockState(blockposMutable, configBlock.state, 3);
                        }

                        //remove floating plants so they aren't hovering.
                        //check above while moving up one.
                        blockState = world.getBlockState(blockposMutable.move(Direction.UP));

                        while(blockposMutable.getY() < chunkGenerator.getMaxBuildHeight() && !blockState.isValidPosition(world, blockposMutable)){
                            world.setBlockState(blockposMutable, Blocks.AIR.getDefaultState(), 3);
                            blockState = world.getBlockState(blockposMutable.move(Direction.UP));
                        }
                    }
                }
            }
        }
        return true;
    }


    /**
     * checks if the spot is surrounded by solid blocks below and all around horizontally plus nothing solid above.
     * Must take world as this will check into neighboring chunks
     */
    private boolean checkIfValidSpot(ISeedReader world, BlockPos blockposMutable) {
        Material material;
        BlockState blockState;
        BlockPos.Mutable mutable = new BlockPos.Mutable().setPos(blockposMutable);

        //Must be solid all around even diagonally.
        //Will also return false if an unacceptable solid material is found.
        for (int x2 = -1; x2 <= 1; x2++) {
            for (int z2 = -1; z2 <= 1; z2++) {
                if(x2 == 0 && z2 == 0) continue;

                mutable.setPos(blockposMutable).move(x2, 0, z2);

                blockState = world.getBlockState(mutable);
                material = blockState.getMaterial();

                if ((!material.isSolid() || unacceptableSolidMaterials.contains(material) || BlockTags.PLANKS.contains(blockState.getBlock())) &&
                        blockState.getFluidState().isEmpty() && !blockState.getFluidState().isTagged(FluidTags.WATER))
                {
                    return false;
                }
            }
        }

        //must be solid below
        //Will also return false if an unacceptable solid material is found.
        blockState = world.getBlockState(mutable.setPos(blockposMutable).move(Direction.DOWN));
        material = blockState.getMaterial();
        if ((!material.isSolid() || unacceptableSolidMaterials.contains(material) || BlockTags.PLANKS.contains(blockState.getBlock())) &&
                blockState.getFluidState().isEmpty() && !blockState.getFluidState().isTagged(FluidTags.WATER))
        {
            return false;
        }

        //cannot have solid, rails, or water above as that makes the lake no longer shallow or on the surface.
        //Will not check unacceptable solid set to allow leaves to be over water.
        blockState = world.getBlockState(mutable.move(Direction.UP, 2));
        material = blockState.getMaterial();
        return !material.isSolid() && blockState.getFluidState().isEmpty() && blockState.getBlock() != Blocks.RAIL;
    }
}
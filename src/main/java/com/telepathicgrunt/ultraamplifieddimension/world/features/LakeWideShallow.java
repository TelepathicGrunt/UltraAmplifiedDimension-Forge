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
import net.minecraft.world.IWorld;
import net.minecraft.world.chunk.IChunk;
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

        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable().setPos(position);
        IChunk chunk = world.getChunk(blockpos$Mutable);
        boolean[] lakeMask = new boolean[2048];
        int maxInterations = random.nextInt(4) + 4;

        for (int currentMaskInteration = 0; currentMaskInteration < maxInterations; ++currentMaskInteration) {
            double rawRandX = random.nextDouble() * 6.0D + 3.0D;
            double rawRandZ = random.nextDouble() * 6.0D + 3.0D;
            double randX = random.nextDouble() * (16.0D - rawRandX - 2.0D) + 1.0D + rawRandX / 2.0D;
            double randZ = random.nextDouble() * (16.0D - rawRandZ - 2.0D) + 1.0D + rawRandZ / 2.0D;

            for (int x = 1; x < 15; ++x) {
                for (int z = 1; z < 15; ++z) {
                    for (int y = 0; y < 5; ++y) {
                        double xMagnetude = (x - randX) / (rawRandX / 2.0D);
                        double zMagnetude = (z - randZ) / (rawRandZ / 2.0D);
                        double squaredMagnetude = xMagnetude * xMagnetude + zMagnetude * zMagnetude;
                        if (squaredMagnetude < 1.2D) {
                            lakeMask[(x * 16 + z) * 8 + y] = true;
                        }
                    }
                }
            }
        }

        //creates the actual lakes
        boolean containedFlag;
        Material material;
        BlockState blockState;
        for (int x = 0; x < 16; ++x) {
            for (int z = 0; z < 16; ++z) {
                int y = 5;

                blockpos$Mutable.setPos(position).move(x, y, z);
                blockState = chunk.getBlockState(blockpos$Mutable);
                material = blockState.getMaterial();

                //Finds first solid block of land starting from 5 blocks higher than initial input position
                //We use unacceptable solid set to help skip solid blocks like leaves.
                while ((!material.isSolid() || unacceptableSolidMaterials.contains(material) || BlockTags.PLANKS.contains(blockState.getBlock())) && y > 0) {
                    y--;
                    material = chunk.getBlockState(blockpos$Mutable.move(Direction.DOWN)).getMaterial();
                }

                //checks if the spot is solid all around (diagonally too) and has nothing solid above it
                containedFlag = checkIfValidSpot(world, blockpos$Mutable);

                //Is spot within the mask (sorta a roundish area) and is contained
                if (lakeMask[(x * 16 + z) * 8 + y] && containedFlag) {
                    //check below without moving down
                    blockState = chunk.getBlockState(blockpos$Mutable.down());

                    //sets the water
                    if (isDirt(blockState.getBlock()) && random.nextInt(5) == 0) {
                        chunk.setBlockState(blockpos$Mutable, Blocks.SEAGRASS.getDefaultState(), false);
                    }
                    else {
                        chunk.setBlockState(blockpos$Mutable, configBlock.state, false);
                    }

                    //remove floating plants so they aren't hovering.
                    //check above while moving up one.
                    blockState = chunk.getBlockState(blockpos$Mutable.move(Direction.UP));
                    while(blockpos$Mutable.getY() < chunkGenerator.getMaxBuildHeight() && !blockState.isValidPosition(world, blockpos$Mutable)){
                        chunk.setBlockState(blockpos$Mutable, Blocks.AIR.getDefaultState(), false);
                        blockpos$Mutable.move(Direction.UP);
                    }
                }
            }
        }
        return true;
    }


    /**
     * checks if the spot is surrounded by solid blocks below and all around horizontally plus nothing solid above.
     * Must take world as this will check into neighboring chunks
     *
     * @param world            - world to check for materials in
     * @param blockpos$Mutable - location to check if valid
     * @return - if the spot is valid
     */
    private boolean checkIfValidSpot(IWorld world, BlockPos.Mutable blockpos$Mutable) {
        Material material;
        BlockState blockState;

        //Must be solid all around even diagonally.
        //Will also return false if an unacceptable solid material is found.
        for (int x2 = -1; x2 < 2; x2++) {
            for (int z2 = -1; z2 < 2; z2++) {
                blockState = world.getBlockState(blockpos$Mutable.add(x2, 0, z2));
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
        blockState = world.getBlockState(blockpos$Mutable.down());
        material = blockState.getMaterial();
        if ((!material.isSolid() || unacceptableSolidMaterials.contains(material) || BlockTags.PLANKS.contains(blockState.getBlock())) &&
                blockState.getFluidState().isEmpty() && !blockState.getFluidState().isTagged(FluidTags.WATER))
        {
            return false;
        }

        //cannot have solid, rails, or water above as that makes the lake no longer shallow or on the surface.
        //Will not check unacceptable solid set to allow leaves to be over water.
        blockState = world.getBlockState(blockpos$Mutable.up());
        material = blockState.getMaterial();
        return !material.isSolid() && blockState.getFluidState().isEmpty() && blockState.getBlock() != Blocks.RAIL;
    }
}
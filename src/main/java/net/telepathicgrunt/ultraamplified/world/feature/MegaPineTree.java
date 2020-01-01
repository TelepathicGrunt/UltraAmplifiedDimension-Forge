package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.HugeTreeFeatureConfig;
import net.minecraft.world.gen.feature.HugeTreesFeature;

public class MegaPineTree extends HugeTreesFeature<HugeTreeFeatureConfig> {
	
    private final boolean useBaseHeight;

    //only change is significant increase in possible height and thicker/bigger leaves so trees fit the terrain more.
    public MegaPineTree(Function<Dynamic<?>, ? extends HugeTreeFeatureConfig> p_i225808_1_, boolean useBaseHeightIn) {
        super(p_i225808_1_);
        useBaseHeight = useBaseHeightIn;
     }

    public boolean func_225557_a_(IWorldGenerationReader worldIn, Random rand, BlockPos position, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, MutableBoundingBox boundingBox, HugeTreeFeatureConfig p_225557_7_) 
    {
        int height = this.func_227256_a_(rand, p_225557_7_);
        IWorld world = (IWorld) worldIn;

        if (!this.hasRoom(worldIn, position, height+8, p_225557_7_)) 
        {
            return false;
        }
        else
        {
            this.createCrown(worldIn, position.getX(), position.getZ(), position.getY() + height, 0, rand, boundingBox, p_225557_5_, p_225557_7_);

            for (int currentHeight = 0; currentHeight < height; ++currentHeight)
            {
                BlockState iblockstate = world.getBlockState(position.up(currentHeight));

                if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                {
                    this.func_227216_a_(worldIn, rand, position.up(currentHeight), p_225557_4_, boundingBox, p_225557_7_);
                }

                if (currentHeight < height - 1)
                {
                    iblockstate = world.getBlockState(position.add(1, currentHeight, 0));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.func_227216_a_(worldIn, rand, position.add(1, currentHeight, 0), p_225557_4_, boundingBox, p_225557_7_);
                    }

                    iblockstate = world.getBlockState(position.add(1, currentHeight, 1));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.func_227216_a_(worldIn, rand, position.add(1, currentHeight, 1), p_225557_4_, boundingBox, p_225557_7_);
                    }

                    iblockstate = world.getBlockState(position.add(0, currentHeight, 1));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.func_227216_a_(worldIn, rand, position.add(0, currentHeight, 1), p_225557_4_, boundingBox, p_225557_7_);
                    }
                }
            }

            return true;
        }
    }

    private void createCrown(IWorldGenerationReader worldIn, int x, int z, int y, int extraRadiusSize, Random rand, MutableBoundingBox p_214596_7_, Set<BlockPos> p_214596_8_, HugeTreeFeatureConfig p_225557_7_)
    {
        int height = rand.nextInt(5) + (this.useBaseHeight ?  p_225557_7_.field_227276_b_ : 3);
        int prevRadius = 0;

        for (int currentHeight = y - height; currentHeight <= y+20; ++currentHeight)
        {
            int heightDiff = y - currentHeight;
            int radius = extraRadiusSize + MathHelper.floor((float)heightDiff / (float)height * 3.5F);
            this.func_227255_a_(worldIn, rand, new BlockPos(x, currentHeight, z), radius + (int)((heightDiff > 0 && radius == prevRadius && (currentHeight & 1) == 0 ? 1 : 0)*2), p_214596_8_, p_214596_7_, p_225557_7_);
            prevRadius = radius;
        }
    }
}

package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.HugeTreesFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BirchMTreeUA extends HugeTreesFeature<NoFeatureConfig> {
    private static final BlockState TRUNK = Blocks.BIRCH_LOG.getDefaultState();
    private static final BlockState LEAF = Blocks.BIRCH_LEAVES.getDefaultState();
    private static final int randExtraHeight = 50;
    
    public BirchMTreeUA(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn, boolean doBlockNotifyOnPlace, boolean useBaseHeightIn) {
        super(configFactoryIn, doBlockNotifyOnPlace, 13, randExtraHeight, TRUNK, LEAF);
        setSapling((net.minecraftforge.common.IPlantable)Blocks.BIRCH_SAPLING);
     }

    public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox p_208519_5_) 
    {
        int height = this.getHeight(rand);
        IWorld world = (IWorld) worldIn;
        
        //checks to see if there is room to generate tree
        if (!this.func_203427_a(worldIn, position, height+8)) {
            return false;
         } 
        else {
    		//adds the leaves on crown
            this.createCrown(worldIn, position.getX(), position.getZ(), position.getY() + height, 0, rand, p_208519_5_, changedBlocks);

            //adds the 2 by 2 wood trunk
            for (int currentHeight = 0; currentHeight < height; ++currentHeight)
            {
                BlockState iblockstate = world.getBlockState(position.up(currentHeight));

                if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                {
                    this.setLogState(changedBlocks, worldIn, position.up(currentHeight), TRUNK, p_208519_5_);
                }

                if (currentHeight < height - 1)
                {
                    iblockstate = world.getBlockState(position.add(1, currentHeight, 0));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setLogState(changedBlocks, worldIn, position.add(1, currentHeight, 0), TRUNK, p_208519_5_);
                    }

                    iblockstate = world.getBlockState(position.add(1, currentHeight, 1));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setLogState(changedBlocks, worldIn, position.add(1, currentHeight, 1), TRUNK, p_208519_5_);
                    }

                    iblockstate = world.getBlockState(position.add(0, currentHeight, 1));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setLogState(changedBlocks, worldIn, position.add(0, currentHeight, 1), TRUNK, p_208519_5_);
                    }
                }
            }
        	
        	
	        return true;
	     }
    }

    //this is set so that the crown is leaves in a cone shape 
    private void createCrown(IWorldGenerationReader worldIn, int x, int z, int y, int extraRadiusSize, Random rand, MutableBoundingBox p_214596_7_, Set<BlockPos> p_214596_8_)
    {
        int i = this.baseHeight - (rand.nextInt(5) + 6);
        int j = 0;

        for (int currentHeight = y - i; currentHeight <= y+10; ++currentHeight)
        {
            int l = y - currentHeight;
            int radius = extraRadiusSize + MathHelper.floor((float)l / (float)i * 2F);
            this.func_222839_a(worldIn, new BlockPos(x, currentHeight, z), radius + (int)((l > 0 && radius == j && (currentHeight & 1) == 0 ? 0.7 : 1)*4), p_214596_7_, p_214596_8_);
            j = radius;
        }
    }
}

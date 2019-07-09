package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.HugeTreesFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BirchMTreeUA extends HugeTreesFeature<NoFeatureConfig> {
    private static final IBlockState TRUNK = Blocks.BIRCH_LOG.getDefaultState();
    private static final IBlockState LEAF = Blocks.BIRCH_LEAVES.getDefaultState();
    private static final int randExtraHeight = 50;
    
    public BirchMTreeUA(boolean notify)
    {
        super(notify, 13, randExtraHeight, TRUNK, LEAF);
    }


    public boolean place(Set<BlockPos> changedBlocks, IWorld worldIn, Random rand, BlockPos position) 
    {
        int height = this.getHeight(rand);
        
        //checks to see if there is room to generate tree
        if (!this.func_203427_a(worldIn, position, height+8)) {
            return false;
         } 
        else {
        	if (worldIn.getBlockState(position.down()).canSustainPlant(worldIn, position.down(), net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.BIRCH_SAPLING) && position.getY() < worldIn.getWorld().getHeight() - height - 1) {
	        	
        		//adds the leaves on crown
	            this.createCrown(worldIn, position.getX(), position.getZ(), position.getY() + height, 0, rand);
	
	            //adds the 2 by 2 wood trunk
	            for (int currentHeight = 0; currentHeight < height; ++currentHeight)
	            {
	                IBlockState iblockstate = worldIn.getBlockState(position.up(currentHeight));
	
	                if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
	                {
	                    this.func_208520_a(changedBlocks, worldIn, position.up(currentHeight), TRUNK);
	                }
	
	                if (currentHeight < height - 1)
	                {
	                    iblockstate = worldIn.getBlockState(position.add(1, currentHeight, 0));
	
	                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
	                    {
	                        this.func_208520_a(changedBlocks, worldIn, position.add(1, currentHeight, 0), this.woodMetadata);
	                    }
	
	                    iblockstate = worldIn.getBlockState(position.add(1, currentHeight, 1));
	
	                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
	                    {
	                        this.func_208520_a(changedBlocks, worldIn, position.add(1, currentHeight, 1), this.woodMetadata);
	                    }
	
	                    iblockstate = worldIn.getBlockState(position.add(0, currentHeight, 1));
	
	                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
	                    {
	                        this.func_208520_a(changedBlocks, worldIn, position.add(0, currentHeight, 1), this.woodMetadata);
	                    }
	                }
	            }
        	}
        	
	        return true;
	     }
    }

    //this is set so that the crown is leaves in a cone shape 
    private void createCrown(IWorld worldIn, int x, int z, int y, int extraRadiusSize, Random rand)
    {
        int i = this.baseHeight - (rand.nextInt(5) + 6);
        int j = 0;

        for (int currentHeight = y - i; currentHeight <= y+10; ++currentHeight)
        {
            int l = y - currentHeight;
            int radius = extraRadiusSize + MathHelper.floor((float)l / (float)i * 2F);
            this.growLeavesLayerStrict(worldIn, new BlockPos(x, currentHeight, z), radius + (int)((l > 0 && radius == j && (currentHeight & 1) == 0 ? 0.7 : 1)*4));
            j = radius;
        }
    }
}

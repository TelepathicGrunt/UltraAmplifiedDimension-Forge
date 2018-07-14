package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import jline.internal.Log;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockNewLeaf;
import net.minecraft.block.BlockNewLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;

public class WorldGenDarkOakMTree extends WorldGenHugeTrees{
	private static final IBlockState TRUNK = Blocks.LOG2.getDefaultState().withProperty(BlockNewLog.VARIANT, BlockPlanks.EnumType.DARK_OAK);
    private static final IBlockState LEAF = Blocks.LEAVES2.getDefaultState().withProperty(BlockNewLeaf.VARIANT, BlockPlanks.EnumType.DARK_OAK).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));

    public WorldGenDarkOakMTree(boolean notify)
    {
        super(notify, 11, 3, TRUNK, LEAF);
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int i = this.getHeight(rand);

        if (!this.ensureGrowable(worldIn, rand, position, i))
        {
            return false;
        }
        else
        {
            this.createCrown(worldIn, position.getX(), position.getZ(), position.getY() + i, 0, rand);
            this.createWoodCrown(worldIn, position.getX(), position.getZ(), position.getY() + i, 1, rand);
            
            BlockPos temp = position;
            int randomChance = 70;
            
            for (int j = 0; j < i; ++j)
            {
                IBlockState iblockstate = worldIn.getBlockState(position.up(j));

                if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                {
                    this.setBlockAndNotifyAdequately(worldIn, position.up(j), this.woodMetadata);
                }

                if (j < i - 1)
                {
                	temp = position.add(1, j, 0);
                    iblockstate = worldIn.getBlockState(temp);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setBlockAndNotifyAdequately(worldIn, temp, this.woodMetadata);
                    }

                    temp = position.add(1, j, 1);
                    iblockstate = worldIn.getBlockState(temp);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setBlockAndNotifyAdequately(worldIn, temp, this.woodMetadata);
                    }
                    
                    temp = position.add(0, j, 1);
                    iblockstate = worldIn.getBlockState(temp);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setBlockAndNotifyAdequately(worldIn, temp, this.woodMetadata);
                    }

                    temp = position.add(-1, j, 0);
                    iblockstate = worldIn.getBlockState(temp);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, temp.getX(), temp.getZ(), temp.getY(), 0, rand);
                    	}
                    	else {
                    		 this.setBlockAndNotifyAdequately(worldIn, temp, this.woodMetadata);
                    	}
                       
                    }

                    temp = position.add(-1, j, 1);
                    iblockstate = worldIn.getBlockState(temp);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, temp.getX(), temp.getZ(), temp.getY(), 0, rand);
                    	}
                    	else {
                    		this.setBlockAndNotifyAdequately(worldIn, temp, this.woodMetadata);
                    	}
                    }

                    temp = position.add(0, j, -1);
                    iblockstate = worldIn.getBlockState(temp);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, temp.getX(), temp.getZ(), temp.getY(), 0, rand);
                    	}
                    	else {
                        	this.setBlockAndNotifyAdequately(worldIn, temp, this.woodMetadata);
                    	}
                    }

                    temp = position.add(1, j, -1);
                    iblockstate = worldIn.getBlockState(temp);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, temp.getX(), temp.getZ(), temp.getY(), 0, rand);
                    	}
                    	else {
                    		this.setBlockAndNotifyAdequately(worldIn, temp, this.woodMetadata);
                    	}
                    }

                    temp = position.add(0, j, 2);
                    iblockstate = worldIn.getBlockState(position.add(0, j, 2));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, temp.getX(), temp.getZ(), temp.getY(), 0, rand);
                    	}
                    	else {
                    		this.setBlockAndNotifyAdequately(worldIn, temp, this.woodMetadata);
                    	}
                    }

                    temp = position.add(1, j, 2);
                    iblockstate = worldIn.getBlockState(temp);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, temp.getX(), temp.getZ(), temp.getY(), 0, rand);
                    	}
                    	else {
                    		this.setBlockAndNotifyAdequately(worldIn, temp, this.woodMetadata);
                    	}
                    }

                    temp = position.add(2, j, 0);
                    iblockstate = worldIn.getBlockState(temp);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, temp.getX(), temp.getZ(), temp.getY(), 0, rand);
                    	}
                    	else {
                    		this.setBlockAndNotifyAdequately(worldIn, temp, this.woodMetadata);
                    	}
                    }

                    temp = position.add(2, j, 1);
                    iblockstate = worldIn.getBlockState(temp);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, temp.getX(), temp.getZ(), temp.getY(), 0, rand);
                    	}
                    	else {
                    		this.setBlockAndNotifyAdequately(worldIn, temp, this.woodMetadata);
                    	}
                    }
                }
            }

            return true;
        }
    }

    private void createCrown(World worldIn, int x, int z, int y, int p_150541_5_, Random rand)
    {
    	  int i = 4;
          int j = 0;

          for (int k = y - i; k <= y+3; ++k)
          {
              int l = y - k;
              int i1 = p_150541_5_ + MathHelper.floor((float)l / (float)i*1.5F);
              this.growLeavesLayerStrict(worldIn, new BlockPos(x, k, z), i1 + (int)((l > 0 && (k & 1) == 0 ? 0.9 : 1)*5.5));
              j = i1;
          }
          
          this.growLeavesLayerStrict(worldIn, new BlockPos(x, y+4, z), 1);
    }
    
    private void createWoodCrown(World worldIn, int x, int z, int y, int p_150541_5_, Random rand)
    {
    	  int i = 2;
          int j = 0;

          for (int k = y - (i + 4); k <= y-1; ++k)
          {
              int l = y - k;
              int i1;
              
              if(l < 3) {
            	  i1 = 4; 
              }
              else if(l < 5) {
            	  i1 = 3; 
              }
              else {
            	  i1 = 2;
              }

              this.growWoodLayerStrict(worldIn, new BlockPos(x, k, z), i1);
              j = i1;
          }
    }

    private void createMiniCrown(World worldIn, int x, int z, int y, int p_150541_5_, Random rand)
    {
    	  int i = rand.nextInt(2) + 1;
          int j = 0;

          for (int k = y - i; k <= y+1; ++k)
          {
              int l = y - k;
              int i1 = p_150541_5_ + MathHelper.floor((float)l / (float)i*1.5F);
              this.growLeavesLayerStrict(worldIn, new BlockPos(x, k, z), i1 + (int)((l > 0 && (k & 1) == 0 ? 0.9 : 1)*2));
              j = i1;
          }
    }
    
    
    /**
     * grow wood in a circle with the outsides being within the circle
     */
    protected void growWoodLayerStrict(World worldIn, BlockPos layerCenter, int width)
    {
        int i = width * width;

        for (int j = -width; j <= width + 1; ++j)
        {
            for (int k = -width; k <= width + 1; ++k)
            {
                int l = j - 1;
                int i1 = k - 1;

                if (j * j + k * k <= i || l * l + i1 * i1 <= i || j * j + i1 * i1 <= i || l * l + k * k <= i)
                {
                    BlockPos blockpos = layerCenter.add(j, 0, k);
                    IBlockState state = worldIn.getBlockState(blockpos);

                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos, this.woodMetadata);
                    }
                }
            }
        }
    }
    
}

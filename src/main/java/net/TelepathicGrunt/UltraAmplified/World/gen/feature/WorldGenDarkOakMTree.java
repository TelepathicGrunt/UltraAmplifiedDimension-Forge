package net.telepathicgrunt.ultraamplified.World.gen.feature;

import java.util.Random;

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
        int height = this.getHeight(rand);

        if (!this.ensureGrowable(worldIn, rand, position, height))
        {
            return false;
        }
        else
        {
        	//creates the dome like crown of leaves first, 
        	//then puts the ring of wood in the leaves that also makes a smooth transition between leaves and trunk,
        	//then lastly, generates the trunk
            this.createCrown(worldIn, position.getX(), position.getZ(), position.getY() + height, 0, rand);
            this.createWoodCrown(worldIn, position.getX(), position.getZ(), position.getY() + height, rand);
            
            BlockPos tempPos = position;
            int randomChance = 70;
            
            for (int currentHeight = 0; currentHeight < height; ++currentHeight)
            {
                IBlockState iblockstate = worldIn.getBlockState(position.up(currentHeight));

                //lots of if statements as we want to generate the trunk as a thick plus sign like this:
                //    [_][_]
                // [_][_][_][_]
                // [_][_][_][_]
                //    [_][_]
                
                //In addition, every wood block placed has a tiny chance of spawning a tiny patch of leaves or "mini crown".
                if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                {
                    this.setBlockAndNotifyAdequately(worldIn, position.up(currentHeight), this.woodMetadata);
                }

                if (currentHeight < height - 1)
                {
                	tempPos = position.add(1, currentHeight, 0);
                    iblockstate = worldIn.getBlockState(tempPos);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setBlockAndNotifyAdequately(worldIn, tempPos, this.woodMetadata);
                    }

                    tempPos = position.add(1, currentHeight, 1);
                    iblockstate = worldIn.getBlockState(tempPos);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setBlockAndNotifyAdequately(worldIn, tempPos, this.woodMetadata);
                    }
                    
                    tempPos = position.add(0, currentHeight, 1);
                    iblockstate = worldIn.getBlockState(tempPos);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setBlockAndNotifyAdequately(worldIn, tempPos, this.woodMetadata);
                    }

                    tempPos = position.add(-1, currentHeight, 0);
                    iblockstate = worldIn.getBlockState(tempPos);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, tempPos.getX(), tempPos.getZ(), tempPos.getY(), 0, rand);
                    	}
                    	else {
                    		 this.setBlockAndNotifyAdequately(worldIn, tempPos, this.woodMetadata);
                    	}
                       
                    }

                    tempPos = position.add(-1, currentHeight, 1);
                    iblockstate = worldIn.getBlockState(tempPos);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, tempPos.getX(), tempPos.getZ(), tempPos.getY(), 0, rand);
                    	}
                    	else {
                    		this.setBlockAndNotifyAdequately(worldIn, tempPos, this.woodMetadata);
                    	}
                    }

                    tempPos = position.add(0, currentHeight, -1);
                    iblockstate = worldIn.getBlockState(tempPos);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, tempPos.getX(), tempPos.getZ(), tempPos.getY(), 0, rand);
                    	}
                    	else {
                        	this.setBlockAndNotifyAdequately(worldIn, tempPos, this.woodMetadata);
                    	}
                    }

                    tempPos = position.add(1, currentHeight, -1);
                    iblockstate = worldIn.getBlockState(tempPos);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, tempPos.getX(), tempPos.getZ(), tempPos.getY(), 0, rand);
                    	}
                    	else {
                    		this.setBlockAndNotifyAdequately(worldIn, tempPos, this.woodMetadata);
                    	}
                    }

                    tempPos = position.add(0, currentHeight, 2);
                    iblockstate = worldIn.getBlockState(position.add(0, currentHeight, 2));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, tempPos.getX(), tempPos.getZ(), tempPos.getY(), 0, rand);
                    	}
                    	else {
                    		this.setBlockAndNotifyAdequately(worldIn, tempPos, this.woodMetadata);
                    	}
                    }

                    tempPos = position.add(1, currentHeight, 2);
                    iblockstate = worldIn.getBlockState(tempPos);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, tempPos.getX(), tempPos.getZ(), tempPos.getY(), 0, rand);
                    	}
                    	else {
                    		this.setBlockAndNotifyAdequately(worldIn, tempPos, this.woodMetadata);
                    	}
                    }

                    tempPos = position.add(2, currentHeight, 0);
                    iblockstate = worldIn.getBlockState(tempPos);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, tempPos.getX(), tempPos.getZ(), tempPos.getY(), 0, rand);
                    	}
                    	else {
                    		this.setBlockAndNotifyAdequately(worldIn, tempPos, this.woodMetadata);
                    	}
                    }

                    tempPos = position.add(2, currentHeight, 1);
                    iblockstate = worldIn.getBlockState(tempPos);

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	if(rand.nextInt(randomChance) == 0) {
                    		createMiniCrown(worldIn, tempPos.getX(), tempPos.getZ(), tempPos.getY(), 0, rand);
                    	}
                    	else {
                    		this.setBlockAndNotifyAdequately(worldIn, tempPos, this.woodMetadata);
                    	}
                    }
                }
            }

            return true;
        }
    }

    private void createCrown(World worldIn, int x, int z, int y, int extraRadius, Random rand)
    {
    	  int i = 4;

          for (int k = y - i; k <= y+3; ++k)
          {
              int l = y - k;
              int radius = extraRadius + MathHelper.floor((float)l / (float)i*1.5F);
              this.growLeavesLayerStrict(worldIn, new BlockPos(x, k, z), radius + (int)((l > 0 && (k & 1) == 0 ? 0.9 : 1)*5.5));
          }
          
          this.growLeavesLayerStrict(worldIn, new BlockPos(x, y+4, z), 1);
    }
    
    
    //generates the wood as an upside-down cone that curves out at end.
    private void createWoodCrown(World worldIn, int x, int z, int y, Random rand)
    {
    	  int i = 2;

          for (int k = y - (i + 4); k <= y-1; ++k)
          {
              int l = y - k;
              int radius;
              
              if(l < 3) {
            	  radius = 4; 
              }
              else if(l < 5) {
            	  radius = 3; 
              }
              else {
            	  radius = 2;
              }

              this.growWoodLayerStrict(worldIn, new BlockPos(x, k, z), radius);
          }
    }

    private void createMiniCrown(World worldIn, int x, int z, int y, int extraRadius, Random rand)
    {
    	//generates a tiny patch of leaves
    	  int i = rand.nextInt(2) + 1;

          for (int k = y - i; k <= y+1; ++k)
          {
              int l = y - k;
              int radius = extraRadius + MathHelper.floor((float)l / (float)i*1.5F);
              this.growLeavesLayerStrict(worldIn, new BlockPos(x, k, z), radius + (int)((l > 0 && (k & 1) == 0 ? 0.9 : 1)*2));
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

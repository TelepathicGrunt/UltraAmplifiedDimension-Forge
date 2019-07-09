package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class DarkOakMTreeUA extends AbstractTreeFeature<NoFeatureConfig> {
   private static final IBlockState DARK_OAK_LOG = Blocks.DARK_OAK_LOG.getDefaultState();
   private static final IBlockState DARK_OAK_LEAVES = Blocks.DARK_OAK_LEAVES.getDefaultState();

   public DarkOakMTreeUA(boolean notify) {
      super(notify);
   }

   public boolean place(Set<BlockPos> changedBlocks, IWorld worldIn, Random rand, BlockPos position) {
    {
        int height = 11 + rand.nextInt(3);

        BlockPos blockpos = position.down();
        boolean isSoil = worldIn.getBlockState(blockpos).canSustainPlant(worldIn, blockpos, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.DARK_OAK_SAPLING);
        if (!isSoil) {
           return false;
        } else if (!this.placeTreeOfHeight(worldIn, position, height)) {
           return false;
        }
        else
        { 
	        for(int x = -1; x < 3; x++) {
	        	for(int z = -1; z < 3; z++) {
		        	if(x + z != -2 && x * z != -2 && x + z != 4) {
		    	        this.setDirtAt(worldIn, blockpos.east(x).south(z), blockpos);
		        	}
		        }
	        }
	        
        	//creates the dome like crown of leaves first, 
        	//then puts the ring of wood in the leaves that also makes a smooth transition between leaves and trunk,
        	//then lastly, generates the trunk
            this.createCrown(changedBlocks, worldIn, position.getX(), position.getZ(), position.getY() + height, 0, rand);
            this.createWoodCrown(changedBlocks, worldIn, position.getX(), position.getZ(), position.getY() + height, rand);
            
                // we want to generate the trunk as a thick plus sign like this:
                //    [_][_]
                // [_][_][_][_]
                // [_][_][_][_]
                //    [_][_]
                
                //In addition, every wood block placed has a tiny chance of spawning a tiny patch of leaves or "mini crown".
                int ymax = height + position.getY();
                if(position.getY() > 3) {
                	position = position.down(2);
                }
                
                this.placeColumnOfWood(changedBlocks, worldIn, ymax, rand, position);
                this.placeColumnOfWood(changedBlocks, worldIn, ymax, rand, position.add(1, 0, 0));
                this.placeColumnOfWood(changedBlocks, worldIn, ymax, rand, position.add(1, 0, 1));
                this.placeColumnOfWood(changedBlocks, worldIn, ymax, rand, position.add(0, 0, 1));
                this.placeColumnOfWood(changedBlocks, worldIn, ymax, rand, position.add(-1, 0, 0));
                this.placeColumnOfWood(changedBlocks, worldIn, ymax, rand, position.add(0, 0, -1));
                this.placeColumnOfWood(changedBlocks, worldIn, ymax, rand, position.add(-1, 0, 1));
                this.placeColumnOfWood(changedBlocks, worldIn, ymax, rand, position.add(1, 0, -1));
                this.placeColumnOfWood(changedBlocks, worldIn, ymax, rand, position.add(0, 0, 2));
                this.placeColumnOfWood(changedBlocks, worldIn, ymax, rand, position.add(1, 0, 2));
                this.placeColumnOfWood(changedBlocks, worldIn, ymax, rand, position.add(2, 0, 0));
                this.placeColumnOfWood(changedBlocks, worldIn, ymax, rand, position.add(2, 0, 1));

            }

            return true;
        }
    }

    private void createCrown(Set<BlockPos> changedBlocks, IWorld worldIn, int x, int z, int y, int extraRadius, Random rand)
    {
    	  int i = 4;

          for (int k = y - i; k <= y+3; ++k)
          {
              int l = y - k;
              int radius = extraRadius + MathHelper.floor((float)l / (float)i*1.5F);
              this.growLeavesLayerStrict(changedBlocks, worldIn, new BlockPos(x, k, z), radius + (int)((l > 0 && (k & 1) == 0 ? 0.9 : 1)*5.5));
          }
          
          this.growLeavesLayerStrict(changedBlocks, worldIn, new BlockPos(x, y+4, z), 1);
    }
    
    
    //generates the wood as an upside-down cone that curves out at end.
    private void createWoodCrown(Set<BlockPos> changedBlocks, IWorld worldIn, int x, int z, int y, Random rand)
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

              this.growWoodLayerStrict(changedBlocks, worldIn, new BlockPos(x, k, z), radius);
          }
    }

    private void createMiniCrown(Set<BlockPos> changedBlocks, IWorld worldIn, int x, int z, int y, int extraRadius, Random rand)
    {
    	//generates a tiny patch of leaves
    	  int i = rand.nextInt(2) + 1;

          for (int k = y - i; k <= y+1; ++k)
          {
              int l = y - k;
              int radius = extraRadius + MathHelper.floor((float)l / (float)i*1.5F);
              this.growLeavesLayerStrict(changedBlocks, worldIn, new BlockPos(x, k, z), radius + (int)((l > 0 && (k & 1) == 0 ? 0.9 : 1)*2));
          }
    }
    

    /**
     * grow leaves in a circle with the outsides being within the circle
     */
    protected void growLeavesLayerStrict(Set<BlockPos> changedBlocks, IWorld worldIn, BlockPos layerCenter, int width)
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

                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getMaterial() == Material.LEAVES)
                    {
                    	this.func_208520_a(changedBlocks, worldIn, blockpos, DARK_OAK_LEAVES);
                    }
                }
            }
        }
    }
    
    
    /**
     * grow wood in a circle with the outsides being within the circle
     */
    protected void growWoodLayerStrict(Set<BlockPos> changedBlocks, IWorld worldIn, BlockPos layerCenter, int width)
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

                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getMaterial() == Material.LEAVES)
                    {
                        this.func_208520_a(changedBlocks, worldIn, blockpos, DARK_OAK_LOG);
                    }
                }
            }
        }
    }
    
    private void placeColumnOfWood(Set<BlockPos> changedBlocks, IWorld worldIn, int yMax, Random rand, BlockPos tempPos) {
    	while(tempPos.getY() < yMax)
        {
    		tempPos = tempPos.up();
            IBlockState iblockstate = worldIn.getBlockState(tempPos);
	
	        if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
	        {
	        	if(rand.nextInt(70) == 0) {
	        		createMiniCrown(changedBlocks, worldIn, tempPos.getX(), tempPos.getZ(), tempPos.getY(), 0, rand);
	        	}
	        	else {
	        		this.func_208520_a(changedBlocks, worldIn, tempPos, DARK_OAK_LOG);
	        	}
	        }
        }
    }


    private boolean placeTreeOfHeight(IBlockReader worldIn, BlockPos pos, int height) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        for(int l = 0; l <= height + 1; ++l) {
           int i1 = 1;
           if (l == 0) {
              i1 = 0;
           }

           if (l >= height - 1) {
              i1 = 2;
           }

           for(int j1 = -i1; j1 <= i1; ++j1) {
              for(int k1 = -i1; k1 <= i1; ++k1) {
                 if (!this.canGrowInto(worldIn, blockpos$mutableblockpos.setPos(i + j1, j + l, k + k1))) {
                    return false;
                 }
              }
           }
        }

        return true;
     }
}

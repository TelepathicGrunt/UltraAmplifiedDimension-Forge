package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.HugeTreeFeatureConfig;
import net.minecraftforge.common.IPlantable;

public class TreeGiantDarkOak extends AbstractTreeFeature<HugeTreeFeatureConfig> {
   private static final BlockState DARK_OAK_LOG = Blocks.DARK_OAK_LOG.getDefaultState();
   private static final BlockState DARK_OAK_LEAVES = Blocks.DARK_OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, Integer.valueOf(1));

   public TreeGiantDarkOak(Function<Dynamic<?>, ? extends HugeTreeFeatureConfig> p_i225808_1_) {
       super(p_i225808_1_);
    }

   public boolean func_225557_a_(IWorldGenerationReader worldIn, Random rand, BlockPos position, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, MutableBoundingBox boundingBox, HugeTreeFeatureConfig p_225557_7_) 
    {
        int height = 11 + rand.nextInt(3);
        IWorld world = (IWorld) worldIn;

        //checks to see if there is room to generate tree
        if (!this.isSpaceAt(worldIn, position, height + 4)) {
            return false;
        } 
        
        BlockPos blockpos = position.down();
        boolean isSoil = world.getBlockState(blockpos).canSustainPlant(world, blockpos, net.minecraft.util.Direction.UP, (IPlantable)Blocks.DARK_OAK_SAPLING);
        if (!isSoil) {
           return false;
        } else if (!this.placeTreeOfHeight(world, position, height)) {
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
            this.createCrown(world, position.getX(), position.getZ(), position.getY() + height, 0, rand, p_225557_5_, boundingBox, p_225557_7_);
            this.createWoodCrown(world, position.getX(), position.getZ(), position.getY() + height, rand, p_225557_5_, boundingBox, p_225557_7_);
            
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
                
                this.placeColumnOfWood(world, ymax, rand, position, p_225557_5_, boundingBox, p_225557_7_);
                this.placeColumnOfWood(world, ymax, rand, position.add(1, 0, 0), p_225557_5_, boundingBox, p_225557_7_);
                this.placeColumnOfWood(world, ymax, rand, position.add(1, 0, 1), p_225557_5_, boundingBox, p_225557_7_);
                this.placeColumnOfWood(world, ymax, rand, position.add(0, 0, 1), p_225557_5_, boundingBox, p_225557_7_);
                this.placeColumnOfWood(world, ymax, rand, position.add(-1, 0, 0), p_225557_5_, boundingBox, p_225557_7_);
                this.placeColumnOfWood(world, ymax, rand, position.add(0, 0, -1), p_225557_5_, boundingBox, p_225557_7_);
                this.placeColumnOfWood(world, ymax, rand, position.add(-1, 0, 1), p_225557_5_, boundingBox, p_225557_7_);
                this.placeColumnOfWood(world, ymax, rand, position.add(1, 0, -1), p_225557_5_, boundingBox, p_225557_7_);
                this.placeColumnOfWood(world, ymax, rand, position.add(0, 0, 2), p_225557_5_, boundingBox, p_225557_7_);
                this.placeColumnOfWood(world, ymax, rand, position.add(1, 0, 2), p_225557_5_, boundingBox, p_225557_7_);
                this.placeColumnOfWood(world, ymax, rand, position.add(2, 0, 0), p_225557_5_, boundingBox, p_225557_7_);
                this.placeColumnOfWood(world, ymax, rand, position.add(2, 0, 1), p_225557_5_, boundingBox, p_225557_7_);

            }

            return true;
        
    }

    private void createCrown(IWorld worldIn, int x, int z, int y, int extraRadius, Random rand, Set<BlockPos> p_214596_8_, MutableBoundingBox p_208519_5_, HugeTreeFeatureConfig p_227252_7_)
    {
    	  int i = 4;

          for (int k = y - i; k <= y+3; ++k)
          {
              int l = y - k;
              int radius = extraRadius + MathHelper.floor((float)l / (float)i*1.5F);
              this.growLeavesLayerStrict(worldIn, new BlockPos(x, k, z), radius + (int)((l > 0 && (k & 1) == 0 ? 0.9 : 1)*5.5), p_214596_8_, p_208519_5_, p_227252_7_);
          }
          
          this.growLeavesLayerStrict(worldIn, new BlockPos(x, y+4, z), 1, p_214596_8_, p_208519_5_, p_227252_7_);
    }
    
    
    //generates the wood as an upside-down cone that curves out at end.
    private void createWoodCrown(IWorld worldIn, int x, int z, int y, Random rand, Set<BlockPos> p_214596_8_, MutableBoundingBox p_208519_5_, HugeTreeFeatureConfig p_227252_7_)
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

              this.growWoodLayerStrict(worldIn, new BlockPos(x, k, z), radius, p_214596_8_, p_208519_5_, p_227252_7_);
          }
    }

    private void createMiniCrown(IWorld worldIn, int x, int z, int y, int extraRadius, Random rand, Set<BlockPos> p_214596_8_, MutableBoundingBox p_208519_5_, HugeTreeFeatureConfig p_227252_7_)
    {
    	//generates a tiny patch of leaves
    	  int i = rand.nextInt(2) + 1;

          for (int k = y - i; k <= y+1; ++k)
          {
              int l = y - k;
              int radius = extraRadius + MathHelper.floor((float)l / (float)i*1.5F);
              this.growLeavesLayerStrict(worldIn, new BlockPos(x, k, z), radius + (int)((l > 0 && (k & 1) == 0 ? 0.9 : 1)*2), p_214596_8_, p_208519_5_, p_227252_7_);
          }
    }
    

    /**
     * grow leaves in a circle with the outsides being within the circle
     */
    protected void growLeavesLayerStrict(IWorld worldIn, BlockPos layerCenter, int width, Set<BlockPos> p_214596_8_, MutableBoundingBox p_208519_5_, HugeTreeFeatureConfig p_227252_7_)
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
                    BlockState state = worldIn.getBlockState(blockpos);

                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getMaterial() == Material.LEAVES)
                    {
                    	this.setBlockState(worldIn, blockpos, DARK_OAK_LEAVES);
                    }
                }
            }
        }
    }
    
    
    /**
     * grow wood in a circle with the outsides being within the circle
     */
    protected void growWoodLayerStrict(IWorld worldIn, BlockPos layerCenter, int width, Set<BlockPos> p_214596_8_, MutableBoundingBox p_208519_5_, HugeTreeFeatureConfig p_227252_7_)
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
                    BlockState state = worldIn.getBlockState(blockpos);

                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getMaterial() == Material.LEAVES)
                    {
                    	this.setBlockState(worldIn, blockpos, DARK_OAK_LOG);
                    }
                }
            }
        }
    }
    
    private void placeColumnOfWood(IWorld worldIn, int yMax, Random rand, BlockPos tempPos, Set<BlockPos> p_214596_8_, MutableBoundingBox p_208519_5_, HugeTreeFeatureConfig p_227252_7_) {
    	while(tempPos.getY() < yMax)
        {
    		tempPos = tempPos.up();
            BlockState iblockstate = worldIn.getBlockState(tempPos);
	
	        if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
	        {
	        	if(rand.nextInt(70) == 0) {
	        		createMiniCrown(worldIn, tempPos.getX(), tempPos.getZ(), tempPos.getY(), 0, rand, p_214596_8_, p_208519_5_, p_227252_7_);
	        	}
	        	else {
                	this.setBlockState(worldIn, tempPos, DARK_OAK_LOG);
	        	}
	        }
        }
    }


    private boolean placeTreeOfHeight(IWorldGenerationBaseReader worldIn, BlockPos pos, int height) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();

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
                 if (!func_214587_a(worldIn, blockpos$Mutable.setPos(i + j1, j + l, k + k1))) {
                    return false;
                 }
              }
           }
        }

        return true;
     }
    
    
    private boolean isSpaceAt(IWorldGenerationBaseReader worldIn, BlockPos leavesPos, int height) {
        boolean flag = true;
        if (leavesPos.getY() >= 1 && leavesPos.getY() + height + 1 <= worldIn.getMaxHeight()) {
           for(int i = 0; i <= 1 + height; ++i) {
              int j = 2;
              if (i == 0) {
                 j = 1;
              } else if (i >= 1 + height - 2) {
                 j = 2;
              }

              for(int k = -j; k <= j && flag; ++k) {
                 for(int l = -j; l <= j && flag; ++l) {
                    if (leavesPos.getY() + i < 0 || leavesPos.getY() + i >= worldIn.getMaxHeight() || !func_214587_a(worldIn, leavesPos.add(k, i, l))) {
                       flag = false;
                    }
                 }
              }
           }

           return flag;
        } else {
           return false;
        }
     }
}

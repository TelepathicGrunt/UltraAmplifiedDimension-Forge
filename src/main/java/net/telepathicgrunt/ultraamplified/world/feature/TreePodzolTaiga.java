package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

public class TreePodzolTaiga extends AbstractTreeFeature<TreeFeatureConfig> 
{
    private static final BlockState TRUNK = Blocks.SPRUCE_LOG.getDefaultState();
    private static final BlockState LEAF = Blocks.SPRUCE_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, Integer.valueOf(1));
    private static final BlockState PODZOL = Blocks.PODZOL.getDefaultState();
    
    public TreePodzolTaiga(Function<Dynamic<?>, ? extends TreeFeatureConfig> p_i225808_1_) {
        super(p_i225808_1_);
     }

    //taller taiga trees with slightly thicker leaves and podzol soil below it.
    public boolean func_225557_a_(IWorldGenerationReader worldReader, Random rand, BlockPos position, Set<BlockPos> p_225557_4_, Set<BlockPos> p_225557_5_, MutableBoundingBox boundingBox, TreeFeatureConfig p_225557_7_) 
    {
        IWorld world = (IWorld) worldReader;
        int height = rand.nextInt(6) + 8;
        int bottomOfLeaves = (2 + rand.nextInt(3));
        int leavesRange = height - bottomOfLeaves;
        int l = 3 + rand.nextInt(2);
        boolean flag = true;

        if (position.getY() >= 1 && position.getY() + height + 1 <= 256)
        {
            for (int y = position.getY(); y <= position.getY() + 1 + height && flag; ++y)
            {
                int bounds;

                if (y - position.getY() < bottomOfLeaves)
                {
                    bounds = 0;
                }
                else
                {
                    bounds = l;
                }

                BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();

                for (int x = position.getX() - bounds; x <= position.getX() + bounds && flag; ++x)
                {
                    for (int z = position.getZ() - bounds; z <= position.getZ() + bounds && flag; ++z)
                    {
                        if (y >= 0 && y < 256)
                        {
                        	blockpos$Mutable.setPos(x, y, z);
                            if (!isAirOrLeaves(world, blockpos$Mutable))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else if (isSoil(world, position.down(), p_225557_7_.getSapling()) && position.getY() < world.getMaxHeight() - height - 1) 
            {
            	//places podzol +'s. Can generate up to 5.
            	if(rand.nextBoolean())
            		 this.placePodzolCircle(world, position.west().north());
            	if(rand.nextBoolean())
            		this.placePodzolCircle(world, position.east().north());
            	if(rand.nextBoolean())
            		this.placePodzolCircle(world, position.west().south());
            	if(rand.nextBoolean())
            		this.placePodzolCircle(world, position.east().south());
            	
        		this.placePodzolCircle(world, position);
        		
        		
        		this.setDirtAt(world, position.down(), position);
                

                
                int randomNum = rand.nextInt(3);

                for (int currentHeight = bottomOfLeaves; currentHeight < height - randomNum; ++currentHeight)
                {
                    BlockPos upN = position.up(currentHeight);

                    if (isAirOrLeaves(world, upN))
                    {
                    	this.setBlockState(world, upN, TRUNK);
                    }
                }
                
                placeTrunkVines(world, p_225557_4_, rand, position, bottomOfLeaves, boundingBox);
                
                int bounds = rand.nextInt(2);
                int j3 = 1;
                int k3 = 0;

                for (int currentHeight = 0; currentHeight <= leavesRange; ++currentHeight)
                {
                    int y = position.getY() + height - currentHeight;

                    for (int x = position.getX() - bounds; x <= position.getX() + bounds; ++x)
                    {
                        int j2 = x - position.getX();

                        for (int z = position.getZ() - bounds; z <= position.getZ() + bounds; ++z)
                        {
                            int l2 = z - position.getZ();

                            if (Math.abs(j2) != bounds || Math.abs(l2) != bounds || bounds <= 0)
                            {
                                BlockPos blockpos = new BlockPos(x, y, z);

                                if (isAirOrLeaves(world, blockpos))
                                {
                                    this.setBlockState(world, blockpos, LEAF);
                                }
                            }
                        }
                    }

                    if (bounds >= j3)
                    {
                        bounds = k3;
                        k3 = 1;
                        ++j3;

                        if (j3 > l)
                        {
                            j3 = l;
                        }
                    }
                    else
                    {
                        ++bounds;
                    }
                }
                
                return true;
            }
            else
            {
                return false;
            }
            
        }
        else
        {
            return false;
        }
    }
    
    private void placePodzolCircle(IWorld world, BlockPos center)
    {
        for (int x = -1; x <= 1; ++x)
        {
            for (int z = -1; z <= 1; ++z)
            {
                if (Math.abs(x) != 1 || Math.abs(z) != 1)
                {
                    this.placePodzolAt(world, center.add(x, 0, z));
                }
            }
        }
    }
    
    private void placePodzolAt(IWorld world, BlockPos pos)
    {
        for (int level = 2; level >= -3; --level)
        {
            BlockPos blockpos = pos.up(level);
            BlockState iblockstate = world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT)
            {
                this.setBlockState(world, blockpos, PODZOL);
                break;
            }

            if (iblockstate.getMaterial() != Material.AIR && level < 0)
            {
                break;
            }
        }
    }
    
    private void placeTrunkVines(IWorld world, Set<BlockPos> changedBlocks, Random rand, BlockPos position, int bottomLeavesHeight, MutableBoundingBox p_208519_5_) {
    	 
    	for(int height = 0; height < bottomLeavesHeight; height++) {
            BlockState iblockstate1 = world.getBlockState(position.up(height));
            
            if (iblockstate1.canBeReplacedByLeaves(world, position.up(height))) {
            	this.setBlockState(world, position.up(height), TRUNK);
              
              int chance = 1 + height;
               
              if (rand.nextInt(chance) == 0 && world.isAirBlock(position.add(-1, height, 0))) {
            	  this.setBlockState(world, position.add(-1, height, 0), Blocks.VINE.getDefaultState().with(VineBlock.EAST, Boolean.valueOf(true)));
              }

              if (rand.nextInt(chance) == 0 && world.isAirBlock(position.add(1, height, 0))) {
            	  this.setBlockState(world, position.add(1, height, 0), Blocks.VINE.getDefaultState().with(VineBlock.WEST, Boolean.valueOf(true)));
              }

              if (rand.nextInt(chance) == 0 && world.isAirBlock(position.add(0, height, -1))) {
            	  this.setBlockState(world, position.add(0, height, -1), Blocks.VINE.getDefaultState().with(VineBlock.SOUTH, Boolean.valueOf(true)));
              }

              if (rand.nextInt(chance) == 0 && world.isAirBlock(position.add(0, height, 1))) {
            	  this.setBlockState(world, position.add(0, height, 1), Blocks.VINE.getDefaultState().with(VineBlock.NORTH, Boolean.valueOf(true)));
              }
            }
         }

    } 
    
}

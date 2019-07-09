package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class TaigaTreeMutatedUA extends AbstractTreeFeature<NoFeatureConfig> 
{
    private static final IBlockState TRUNK = Blocks.SPRUCE_LOG.getDefaultState();
    private static final IBlockState LEAF = Blocks.SPRUCE_LEAVES.getDefaultState();
    private static final IBlockState PODZOL = Blocks.PODZOL.getDefaultState();
    
    public TaigaTreeMutatedUA(boolean notify)
    {
        super(notify);
    }

    //taller taiga trees with slightly thicker leaves and podzol soil below it.
    public boolean place(Set<BlockPos> changedBlocks, IWorld worldIn, Random rand, BlockPos position) 
    {
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

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int x = position.getX() - bounds; x <= position.getX() + bounds && flag; ++x)
                {
                    for (int z = position.getZ() - bounds; z <= position.getZ() + bounds && flag; ++z)
                    {
                        if (y >= 0 && y < 256)
                        {
                        	IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos.setPos(x, y, z));
                            if (!iblockstate.isAir(worldIn, blockpos$mutableblockpos) && !iblockstate.isIn(BlockTags.LEAVES))
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
            else
            {
            	BlockPos down = position.down();
                IBlockState state = worldIn.getBlockState(down);

                if (state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SPRUCE_SAPLING) && position.getY() < worldIn.getWorld().getHeight() - height - 1)
                {
                	
                	//places podzol +'s. Can generate up to 5.
	            	if(rand.nextBoolean())
	            		 this.placePodzolCircle(worldIn, position.west().north());
	            	if(rand.nextBoolean())
	            		this.placePodzolCircle(worldIn, position.east().north());
	            	if(rand.nextBoolean())
	            		this.placePodzolCircle(worldIn, position.west().south());
	            	if(rand.nextBoolean())
	            		this.placePodzolCircle(worldIn, position.east().south());
	
	        		this.placePodzolCircle(worldIn, position);
	        		
                	
                    state.getBlock().onPlantGrow(state, worldIn, down, position);
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

                                    if (worldIn.getBlockState(blockpos).canBeReplacedByLeaves(worldIn, blockpos))
                                    {
                                        this.setBlockState(worldIn, blockpos, LEAF);
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

                    placeTrunkVines(worldIn, changedBlocks, rand, position, bottomOfLeaves);
                    
                    int randomNum = rand.nextInt(3);

                    for (int currentHeight = bottomOfLeaves; currentHeight < height - randomNum; ++currentHeight)
                    {
                        BlockPos upN = position.up(currentHeight);
                        state = worldIn.getBlockState(upN);

                        if (state.getBlock().isAir(state, worldIn, upN) || state.isIn(BlockTags.LEAVES))
                        {
                        	this.func_208520_a(changedBlocks, worldIn, upN, TRUNK);
                        }
                    }
                    

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
    
    private void placePodzolCircle(IWorld worldIn, BlockPos center)
    {
        for (int x = -1; x <= 1; ++x)
        {
            for (int z = -1; z <= 1; ++z)
            {
                if (Math.abs(x) != 1 || Math.abs(z) != 1)
                {
                    this.placePodzolAt(worldIn, center.add(x, 0, z));
                }
            }
        }
    }
    
    private void placePodzolAt(IWorld worldIn, BlockPos pos)
    {
        for (int level = 2; level >= -3; --level)
        {
            BlockPos blockpos = pos.up(level);
            IBlockState iblockstate = worldIn.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT)
            {
                this.setBlockState(worldIn, blockpos, PODZOL);
                break;
            }

            if (iblockstate.getMaterial() != Material.AIR && level < 0)
            {
                break;
            }
        }
    }
    
    private void placeTrunkVines(IWorld worldIn, Set<BlockPos> changedBlocks, Random rand, BlockPos position, int bottomLeavesHeight) {
    	 
    	for(int height = 0; height < bottomLeavesHeight; height++) {
            IBlockState iblockstate1 = worldIn.getBlockState(position.up(height));
            Material material1 = iblockstate1.getMaterial();
            if (iblockstate1.canBeReplacedByLeaves(worldIn, position.up(height)) || material1 == Material.VINE) {
               this.func_208520_a(changedBlocks, worldIn, position.up(height), TRUNK);
              
              int chance = 1 + height;
               
              if (rand.nextInt(chance) == 0 && worldIn.isAirBlock(position.add(-1, height, 0))) {
            	  this.setBlockState(worldIn, position.add(-1, height, 0), Blocks.VINE.getDefaultState().with(BlockVine.EAST, Boolean.valueOf(true)));
              }

              if (rand.nextInt(chance) == 0 && worldIn.isAirBlock(position.add(1, height, 0))) {
            	  this.setBlockState(worldIn, position.add(1, height, 0), Blocks.VINE.getDefaultState().with(BlockVine.WEST, Boolean.valueOf(true)));
              }

              if (rand.nextInt(chance) == 0 && worldIn.isAirBlock(position.add(0, height, -1))) {
            	  this.setBlockState(worldIn, position.add(0, height, -1), Blocks.VINE.getDefaultState().with(BlockVine.SOUTH, Boolean.valueOf(true)));
              }

              if (rand.nextInt(chance) == 0 && worldIn.isAirBlock(position.add(0, height, 1))) {
            	  this.setBlockState(worldIn, position.add(0, height, 1), Blocks.VINE.getDefaultState().with(BlockVine.NORTH, Boolean.valueOf(true)));
              }
            }
         }

    } 
    
}

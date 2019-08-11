package net.TelepathicGrunt.UltraAmplified.World.Feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SwampTreeMutated extends AbstractTreeFeature<NoFeatureConfig> 
{
	 	private static final BlockState TRUNK = Blocks.OAK_LOG.getDefaultState();
	   private static final BlockState LEAF = Blocks.OAK_LEAVES.getDefaultState();

	    public SwampTreeMutated(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51425_1_) {
	        super(p_i51425_1_, false);
	    }

	    //generate the spooky horned swamp m trees
	    public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox p_208519_5_)
	    {
	        int height;
	        IWorld world = (IWorld) worldIn;
	        
	        for (height = rand.nextInt(4) + 6; world.getBlockState(position.down()).getMaterial() == Material.WATER; position = position.down())
	        {
	            ;
	        }

	        boolean flag = true;

	        if (position.getY() >= 1 && position.getY() + height + 1 <= 256)
	        {
	            for (int y = position.getY(); y <= position.getY() + 1 + height; ++y)
	            {
	                int k = 1;

	                if (y == position.getY())
	                {
	                    k = 0;
	                }

	                if (y >= position.getY() + 1 + height - 2)
	                {
	                    k = 3;
	                }

	                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

	                for (int x = position.getX() - k; x <= position.getX() + k && flag; ++x)
	                {
	                    for (int z = position.getZ() - k; z <= position.getZ() + k && flag; ++z)
	                    {
	                        if (y >= 0 && y < 256)
	                        {
	                            blockpos$mutableblockpos.setPos(x, y, z);
	                            if (!isAirOrLeaves(worldIn, blockpos$mutableblockpos))
	                            {
	                                if (isWater(worldIn, blockpos$mutableblockpos))
	                                {
	                                    flag = false;
	                                }
	                                else if (y > position.getY())
	                                {
	                                    flag = false;
	                                }
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
	            else if (isSoil(worldIn, position.down(), getSapling()) && position.getY() < worldIn.getMaxHeight() - height - 1) 
	            {
	                this.setDirtAt(worldIn, position.down(), position);
	                
                    for (int currentHeight = position.getY() - 4 + height; currentHeight <= position.getY() + height; ++currentHeight)
                    {
                        int heightDiff = currentHeight - (position.getY() + height);
                        int l2 = 2 - heightDiff / 2;

                        for (int x = position.getX() - l2 - 1; x <= position.getX() + l2; ++x)
                        {
                            int xPos = x - position.getX();

                            for (int z = position.getZ() - l2 - 1; z <= position.getZ() + l2; ++z)
                            {
                                int zPos = z - position.getZ();
                                int isCornerIfThisIsTwo = 0;
                                
                                if(xPos == l2) {
                                	isCornerIfThisIsTwo++;
                                }
                                if(zPos == l2) {
                                	isCornerIfThisIsTwo++;
                                }	                                
                                if(xPos == -l2 - 1) {
                                	isCornerIfThisIsTwo++;
                                }
                                if(zPos == -l2 - 1) {
                                	isCornerIfThisIsTwo++;
                                }
                                
                                //generate leaves if is in corners or if 2/3rd rng is true
                                if (isCornerIfThisIsTwo == 2 || rand.nextInt(3) < 2 && heightDiff != 0)
                                {
                                    BlockPos blockpos = new BlockPos(x, currentHeight, z);

                                    if (isAirOrLeaves(worldIn, blockpos) || func_214576_j(worldIn, blockpos))
                                    {
                                        this.setBlockState(worldIn, blockpos, LEAF);
                                    }
                                }
                            }
                        }
                    }

                    //the following four for statements generates the trunk of the tree
                    genTrunk(world, position, height);
                    genTrunk(world, position.west(), height);
                    genTrunk(world, position.north(), height);
                    genTrunk(world, position.west().north(), height);
                    
                    
                    //vine generation
                    for (int currentHeight = position.getY() - 3 + height; currentHeight <= position.getY() + height; ++currentHeight)
                    {
                        int heightDiff = currentHeight - (position.getY() + height);
                        int i3 = 2 - heightDiff / 2;
                        BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();

                        for (int x = position.getX() - i3 - 1; x <= position.getX() + i3; ++x)
                        {
                            for (int z = position.getZ() - i3 - 1; z <= position.getZ() + i3; ++z)
                            {
                                blockpos$mutableblockpos1.setPos(x, currentHeight, z);

                                if (world.getBlockState(blockpos$mutableblockpos1).getMaterial() == Material.LEAVES)
                                {
                                    BlockPos blockpos3 = blockpos$mutableblockpos1.west();
                                    BlockPos blockpos4 = blockpos$mutableblockpos1.east();
                                    BlockPos blockpos1 = blockpos$mutableblockpos1.north();
                                    BlockPos blockpos2 = blockpos$mutableblockpos1.south();

                                    if (rand.nextInt(4) == 0 && world.isAirBlock(blockpos3))
                                    {
                                        this.addVine(world, blockpos3, VineBlock.EAST);
                                    }

                                    if (rand.nextInt(4) == 0 && world.isAirBlock(blockpos4))
                                    {
                                        this.addVine(world, blockpos4, VineBlock.WEST);
                                    }

                                    if (rand.nextInt(4) == 0 && world.isAirBlock(blockpos1))
                                    {
                                        this.addVine(world, blockpos1, VineBlock.SOUTH);
                                    }

                                    if (rand.nextInt(4) == 0 && world.isAirBlock(blockpos2))
                                    {
                                        this.addVine(world, blockpos2, VineBlock.NORTH);
                                    }
                                }
                            }
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

	    private void addVine(IWorld worldIn, BlockPos pos, BooleanProperty prop)
	    {
	        BlockState iblockstate = Blocks.VINE.getDefaultState().with(prop, Boolean.valueOf(true));
	        this.setBlockState(worldIn, pos, iblockstate);
	        int i = 4;

	        for (BlockPos blockpos = pos.down(); worldIn.isAirBlock(blockpos) && i > 0; --i)
	        {
	            this.setBlockState(worldIn, blockpos, iblockstate);
	            blockpos = blockpos.down();
	        }
	    }
	    
	    private void genTrunk(IWorld worldIn, BlockPos position, int height)  {
	    	for (int currentHeight = 0; currentHeight < height; ++currentHeight)
            {
                BlockPos upN = position.up(currentHeight);
                BlockState iblockstate1 = worldIn.getBlockState(upN);
                Block block2 = iblockstate1.getBlock();

                if (currentHeight != height-1 && 
                	(block2.isAir(iblockstate1, worldIn, upN) || 
                	iblockstate1.isIn(BlockTags.LEAVES) || 
                	block2 == Blocks.WATER || 
                	block2 == Blocks.LILY_PAD))
                {
                    this.setBlockState(worldIn, upN, TRUNK);
                }else {
                	this.setBlockState(worldIn, upN, LEAF);
                }
            }
	    }
	}
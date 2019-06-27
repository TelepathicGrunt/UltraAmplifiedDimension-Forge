package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.BooleanProperty;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class WorldGenSwampTreeMutatedUA extends AbstractTreeFeature<NoFeatureConfig> 
{
	 	private static final IBlockState TRUNK = Blocks.OAK_LOG.getDefaultState();
	   private static final IBlockState LEAF = Blocks.OAK_LEAVES.getDefaultState();

	    public WorldGenSwampTreeMutatedUA()
	    {
	        super(false);
	    }

	    //generate the spooky horned swamp m trees
	    public boolean place(Set<BlockPos> changedBlocks, IWorld worldIn, Random rand, BlockPos position) 
	    {
	        int height;

	        for (height = rand.nextInt(4) + 6; worldIn.getBlockState(position.down()).getMaterial() == Material.WATER; position = position.down())
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
	                            IBlockState iblockstate = worldIn.getBlockState(blockpos$mutableblockpos.setPos(x, y, z));
	                            Block block = iblockstate.getBlock();

	                            if (!iblockstate.isAir(worldIn, blockpos$mutableblockpos.setPos(x, y, z)) && 
	                            	!iblockstate.isIn(BlockTags.LEAVES))
	                            {
	                                if (block != Blocks.WATER)
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
	            else
	            {
	                BlockPos down = position.down();
	                IBlockState state = worldIn.getBlockState(down);
	                boolean isSoil = state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, ((net.minecraft.block.BlockSapling)Blocks.OAK_SAPLING));

	                if (isSoil && position.getY() < worldIn.getWorld().getHeight() - height - 1)
	                {
	                    state.getBlock().onPlantGrow(state, worldIn, position.down(),position);

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
	                                    state = worldIn.getBlockState(blockpos);

	                                    if (state.getBlock().canBeReplacedByLeaves(state, worldIn, blockpos))
	                                    {
	                                        this.setBlockState(worldIn, blockpos, LEAF);
	                                    }
	                                }
	                            }
	                        }
	                    }

	                    //the following four for statements generates the trunk of the tree
	                    genTrunk(worldIn, position, height);
	                    genTrunk(worldIn, position.west(), height);
	                    genTrunk(worldIn, position.north(), height);
	                    genTrunk(worldIn, position.west().north(), height);
	                    
	                    
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

	                                if (worldIn.getBlockState(blockpos$mutableblockpos1).getMaterial() == Material.LEAVES)
	                                {
	                                    BlockPos blockpos3 = blockpos$mutableblockpos1.west();
	                                    BlockPos blockpos4 = blockpos$mutableblockpos1.east();
	                                    BlockPos blockpos1 = blockpos$mutableblockpos1.north();
	                                    BlockPos blockpos2 = blockpos$mutableblockpos1.south();

	                                    if (rand.nextInt(4) == 0 && worldIn.isAirBlock(blockpos3))
	                                    {
	                                        this.addVine(worldIn, blockpos3, BlockVine.EAST);
	                                    }

	                                    if (rand.nextInt(4) == 0 && worldIn.isAirBlock(blockpos4))
	                                    {
	                                        this.addVine(worldIn, blockpos4, BlockVine.WEST);
	                                    }

	                                    if (rand.nextInt(4) == 0 && worldIn.isAirBlock(blockpos1))
	                                    {
	                                        this.addVine(worldIn, blockpos1, BlockVine.SOUTH);
	                                    }

	                                    if (rand.nextInt(4) == 0 && worldIn.isAirBlock(blockpos2))
	                                    {
	                                        this.addVine(worldIn, blockpos2, BlockVine.NORTH);
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
	        }
	        else
	        {
	            return false;
	        }
	    }

	    private void addVine(IWorld worldIn, BlockPos pos, BooleanProperty prop)
	    {
	        IBlockState iblockstate = Blocks.VINE.getDefaultState().with(prop, Boolean.valueOf(true));
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
                IBlockState iblockstate1 = worldIn.getBlockState(upN);
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
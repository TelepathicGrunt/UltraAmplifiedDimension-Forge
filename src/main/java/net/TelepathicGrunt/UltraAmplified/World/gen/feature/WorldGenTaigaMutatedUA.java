package net.telepathicgrunt.ultraamplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class WorldGenTaigaMutatedUA extends WorldGenAbstractTree
{
    private static final IBlockState TRUNK = Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE);
    private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
    private static final IBlockState PODZOL = Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
    
    public WorldGenTaigaMutatedUA(boolean notify)
    {
        super(notify);
    }

    //taller taiga trees with slightly thicker leaves and podzol soil below it.
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int height = rand.nextInt(6) + 8;
        int bottomOfLeaves = height - (6 + rand.nextInt(2));
        int leavesRange = height - bottomOfLeaves;
        int l = 3 + rand.nextInt(2);
        boolean flag = true;

        if (position.getY() >= 1 && position.getY() + height + 1 <= 256)
        {
            for (int y = position.getY(); y <= position.getY() + 1 + height && flag; ++y)
            {
                int j1;

                if (y - position.getY() < bottomOfLeaves)
                {
                    j1 = 0;
                }
                else
                {
                    j1 = l;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int x = position.getX() - j1; x <= position.getX() + j1 && flag; ++x)
                {
                    for (int z = position.getZ() - j1; z <= position.getZ() + j1 && flag; ++z)
                    {
                        if (y >= 0 && y < 256)
                        {
                            Material material = worldIn.getBlockState(blockpos$mutableblockpos.setPos(x, y, z)).getMaterial();

                            if (material != Material.AIR && material != Material.LEAVES)
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
            	
            	//places podzol +'s. Can generate up to 5.
            	if(rand.nextBoolean())
            		 this.placePodzolCircle(worldIn, position.west().north());
            	if(rand.nextBoolean())
            		this.placePodzolCircle(worldIn, position.east().north());
            	if(rand.nextBoolean())
            		this.placePodzolCircle(worldIn, position.west().south());
            	if(rand.nextBoolean())
            		this.placePodzolCircle(worldIn, position.east().south());

        		this.placePodzolCircle(worldIn, position.down());
        		
        		
            	BlockPos down = position.down();
                IBlockState state = worldIn.getBlockState(down);

                if (state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SAPLING) && position.getY() < worldIn.getHeight() - height - 1)
                {
                    state.getBlock().onPlantGrow(state, worldIn, down, position);
                    int i3 = rand.nextInt(2);
                    int j3 = 1;
                    int k3 = 0;

                    for (int currentHeight = 0; currentHeight <= leavesRange; ++currentHeight)
                    {
                        int y = position.getY() + height - currentHeight;

                        for (int x = position.getX() - i3; x <= position.getX() + i3; ++x)
                        {
                            int j2 = x - position.getX();

                            for (int z = position.getZ() - i3; z <= position.getZ() + i3; ++z)
                            {
                                int l2 = z - position.getZ();

                                if (Math.abs(j2) != i3 || Math.abs(l2) != i3 || i3 <= 0)
                                {
                                    BlockPos blockpos = new BlockPos(x, y, z);
                                    state = worldIn.getBlockState(blockpos);

                                    if (state.getBlock().canBeReplacedByLeaves(state, worldIn, blockpos))
                                    {
                                        this.setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
                                    }
                                }
                            }
                        }

                        if (i3 >= j3)
                        {
                            i3 = k3;
                            k3 = 1;
                            ++j3;

                            if (j3 > l)
                            {
                                j3 = l;
                            }
                        }
                        else
                        {
                            ++i3;
                        }
                    }

                    int randomNum = rand.nextInt(3);

                    for (int currentHeight = 0; currentHeight < height - randomNum; ++currentHeight)
                    {
                        BlockPos upN = position.up(currentHeight);
                        state = worldIn.getBlockState(upN);

                        if (state.getBlock().isAir(state, worldIn, upN) || state.getBlock().isLeaves(state, worldIn, upN))
                        {
                            this.setBlockAndNotifyAdequately(worldIn, position.up(currentHeight), TRUNK);
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
    
    private void placePodzolCircle(World worldIn, BlockPos center)
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
    
    private void placePodzolAt(World worldIn, BlockPos pos)
    {
        for (int level = 2; level >= -3; --level)
        {
            BlockPos blockpos = pos.up(level);
            IBlockState iblockstate = worldIn.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (block == Blocks.GRASS || block == Blocks.DIRT)
            {
                this.setBlockAndNotifyAdequately(worldIn, blockpos, PODZOL);
                break;
            }

            if (iblockstate.getMaterial() != Material.AIR && level < 0)
            {
                break;
            }
        }
    }
}

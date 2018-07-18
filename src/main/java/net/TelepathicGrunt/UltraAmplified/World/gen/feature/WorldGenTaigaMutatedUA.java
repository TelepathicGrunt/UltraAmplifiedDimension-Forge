package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

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
    
    public WorldGenTaigaMutatedUA(boolean p_i2025_1_)
    {
        super(p_i2025_1_);
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int i = rand.nextInt(6) + 8;
        int j = i - (6 + rand.nextInt(2));
        int k = i - j;
        int l = 3 + rand.nextInt(2);
        boolean flag = true;

        if (position.getY() >= 1 && position.getY() + i + 1 <= 256)
        {
            for (int i1 = position.getY(); i1 <= position.getY() + 1 + i && flag; ++i1)
            {
                int j1;

                if (i1 - position.getY() < j)
                {
                    j1 = 0;
                }
                else
                {
                    j1 = l;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int k1 = position.getX() - j1; k1 <= position.getX() + j1 && flag; ++k1)
                {
                    for (int l1 = position.getZ() - j1; l1 <= position.getZ() + j1 && flag; ++l1)
                    {
                        if (i1 >= 0 && i1 < 256)
                        {
                            Material material = worldIn.getBlockState(blockpos$mutableblockpos.setPos(k1, i1, l1)).getMaterial();

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

                if (state.getBlock().canSustainPlant(state, worldIn, down, net.minecraft.util.EnumFacing.UP, (net.minecraft.block.BlockSapling)Blocks.SAPLING) && position.getY() < worldIn.getHeight() - i - 1)
                {
                    state.getBlock().onPlantGrow(state, worldIn, down, position);
                    int i3 = rand.nextInt(2);
                    int j3 = 1;
                    int k3 = 0;

                    for (int l3 = 0; l3 <= k; ++l3)
                    {
                        int j4 = position.getY() + i - l3;

                        for (int i2 = position.getX() - i3; i2 <= position.getX() + i3; ++i2)
                        {
                            int j2 = i2 - position.getX();

                            for (int k2 = position.getZ() - i3; k2 <= position.getZ() + i3; ++k2)
                            {
                                int l2 = k2 - position.getZ();

                                if (Math.abs(j2) != i3 || Math.abs(l2) != i3 || i3 <= 0)
                                {
                                    BlockPos blockpos = new BlockPos(i2, j4, k2);
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

                    int i4 = rand.nextInt(3);

                    for (int k4 = 0; k4 < i - i4; ++k4)
                    {
                        BlockPos upN = position.up(k4);
                        state = worldIn.getBlockState(upN);

                        if (state.getBlock().isAir(state, worldIn, upN) || state.getBlock().isLeaves(state, worldIn, upN))
                        {
                            this.setBlockAndNotifyAdequately(worldIn, position.up(k4), TRUNK);
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
        for (int i = -1; i <= 1; ++i)
        {
            for (int j = -1; j <= 1; ++j)
            {
                if (Math.abs(i) != 1 || Math.abs(j) != 1)
                {
                    this.placePodzolAt(worldIn, center.add(i, 0, j));
                }
            }
        }
    }
    
    private void placePodzolAt(World worldIn, BlockPos pos)
    {
        for (int i = 2; i >= -3; --i)
        {
            BlockPos blockpos = pos.up(i);
            IBlockState iblockstate = worldIn.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (block == Blocks.GRASS || block == Blocks.DIRT)
            {
                this.setBlockAndNotifyAdequately(worldIn, blockpos, PODZOL);
                break;
            }

            if (iblockstate.getMaterial() != Material.AIR && i < 0)
            {
                break;
            }
        }
    }
}

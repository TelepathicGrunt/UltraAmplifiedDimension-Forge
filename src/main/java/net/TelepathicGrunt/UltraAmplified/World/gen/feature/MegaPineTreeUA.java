package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.HugeTreesFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class MegaPineTreeUA extends HugeTreesFeature<NoFeatureConfig> {
	
    private static final BlockState TRUNK = Blocks.SPRUCE_LOG.getDefaultState();
    private static final BlockState LEAF = Blocks.SPRUCE_LEAVES.getDefaultState();
    private static final BlockState PODZOL = Blocks.PODZOL.getDefaultState();
    private final boolean useBaseHeight;

    //only change is significant increase in possible height and thicker/bigger leaves so trees fit the terrain more.
    public MegaPineTreeUA(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn, boolean doBlockNotifyOnPlace, boolean useBaseHeightIn) {
        super(configFactoryIn, doBlockNotifyOnPlace, 13, 50, TRUNK, LEAF);
        useBaseHeight = useBaseHeightIn;
        setSapling((net.minecraftforge.common.IPlantable)Blocks.SPRUCE_SAPLING);
     }

    public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox p_208519_5_)  
    {
        int height = this.getHeight(rand);
        IWorld world = (IWorld) worldIn;

        if (!this.func_203427_a(worldIn, position, height))
        {
            return false;
        }
        else
        {
            this.createCrown(worldIn, position.getX(), position.getZ(), position.getY() + height, 0, rand, p_208519_5_, changedBlocks);

            for (int currentHeight = 0; currentHeight < height; ++currentHeight)
            {
                BlockState iblockstate = world.getBlockState(position.up(currentHeight));

                if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                {
                	this.setLogState(changedBlocks, worldIn, position.up(currentHeight), TRUNK, p_208519_5_);
                }

                if (currentHeight < height - 1)
                {
                    iblockstate = world.getBlockState(position.add(1, currentHeight, 0));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	this.setLogState(changedBlocks, worldIn, position.add(1, currentHeight, 0), TRUNK, p_208519_5_);
                    }

                    iblockstate = world.getBlockState(position.add(1, currentHeight, 1));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                    	this.setLogState(changedBlocks, worldIn, position.add(1, currentHeight, 1), TRUNK, p_208519_5_);
                    }

                    iblockstate = world.getBlockState(position.add(0, currentHeight, 1));

                    if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
                    {
                        this.setLogState(changedBlocks, worldIn, position.add(0, currentHeight, 1), TRUNK, p_208519_5_);
                    }
                }
            }

            return true;
        }
    }

    private void createCrown(IWorldGenerationReader worldIn, int x, int z, int y, int extraRadiusSize, Random rand, MutableBoundingBox p_214596_7_, Set<BlockPos> p_214596_8_)
    {
        int height = rand.nextInt(5) + (this.useBaseHeight ? this.baseHeight : 3);
        int prevRadius = 0;

        for (int currentHeight = y - height; currentHeight <= y+20; ++currentHeight)
        {
            int heightDiff = y - currentHeight;
            int radius = extraRadiusSize + MathHelper.floor((float)heightDiff / (float)height * 3.5F);
            this.func_222839_a(worldIn, new BlockPos(x, currentHeight, z), radius + (int)((heightDiff > 0 && radius == prevRadius && (currentHeight & 1) == 0 ? 1 : 0)*2), p_214596_7_, p_214596_8_);
            prevRadius = radius;
        }
    }

    public void generateSaplings(IWorld worldIn, Random random, BlockPos pos)
    {
        this.placePodzolCircle(worldIn, pos.west().north());
        this.placePodzolCircle(worldIn, pos.east(2).north());
        this.placePodzolCircle(worldIn, pos.west().south(2));
        this.placePodzolCircle(worldIn, pos.east(2).south(2));

        for (int currentCount = 0; currentCount < 5; ++currentCount)
        {
            int randNum = random.nextInt(64);
            int x = randNum % 8;
            int z = randNum / 8;

            if (x == 0 || x == 7 || z == 0 || z == 7)
            {
                this.placePodzolCircle(worldIn, pos.add(-3 + x, 0, -3 + z));
            }
        }
    }

    private void placePodzolCircle(IWorld worldIn, BlockPos center)
    {
        for (int i = -2; i <= 2; ++i)
        {
            for (int j = -2; j <= 2; ++j)
            {
                if (Math.abs(i) != 2 || Math.abs(j) != 2)
                {
                    this.placePodzolAt(worldIn, center.add(i, 0, j));
                }
            }
        }
    }

    private void placePodzolAt(IWorld worldIn, BlockPos pos)
    {
        for (int i = 2; i >= -3; --i)
        {
            BlockPos blockpos = pos.up(i);
            BlockState iblockstate = worldIn.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (block == Blocks.GRASS || Block.isDirt(block))
            {
                this.setBlockState(worldIn, blockpos, PODZOL);
                break;
            }

            if (iblockstate.getMaterial() != Material.AIR && i < 0)
            {
                break;
            }
        }
    }
    
}

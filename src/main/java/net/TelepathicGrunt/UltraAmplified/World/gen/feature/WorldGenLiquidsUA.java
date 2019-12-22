package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenLiquidsUA extends WorldGenerator
{
    private final Block block;

    public WorldGenLiquidsUA(Block blockIn)
    {
        this.block = blockIn;
    }

    //replace stone or ice with the given liquid but only if one side of it has an air block
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
    	 if (worldIn.getBlockState(position.up()).getBlock() != Blocks.STONE && worldIn.getBlockState(position.up()).getBlock() != Blocks.ICE)
         {
             return false;
         }
         else if (worldIn.getBlockState(position.down()).getBlock() != Blocks.STONE && worldIn.getBlockState(position.down()).getBlock() != Blocks.ICE)
         {
             return false;
         }
         else
         {
             IBlockState iblockstate = worldIn.getBlockState(position);

             if (!iblockstate.getBlock().isAir(iblockstate, worldIn, position) && (iblockstate.getBlock() != Blocks.STONE && worldIn.getBlockState(position.up()).getBlock() != Blocks.ICE))
             {
                 return false;
             }
             else
             {
                 int i = 0;

                 if (worldIn.getBlockState(position.west()).getBlock() == Blocks.STONE || worldIn.getBlockState(position.west()).getBlock() == Blocks.ICE)
                 {
                     ++i;
                 }

                 if (worldIn.getBlockState(position.east()).getBlock() == Blocks.STONE || worldIn.getBlockState(position.east()).getBlock() == Blocks.ICE)
                 {
                     ++i;
                 }

                 if (worldIn.getBlockState(position.north()).getBlock() == Blocks.STONE || worldIn.getBlockState(position.north()).getBlock() == Blocks.ICE)
                 {
                     ++i;
                 }

                 if (worldIn.getBlockState(position.south()).getBlock() == Blocks.STONE || worldIn.getBlockState(position.south()).getBlock() == Blocks.ICE)
                 {
                     ++i;
                 }

                 int j = 0;

                 if (worldIn.isAirBlock(position.west()))
                 {
                     ++j;
                 }

                 if (worldIn.isAirBlock(position.east()))
                 {
                     ++j;
                 }

                 if (worldIn.isAirBlock(position.north()))
                 {
                     ++j;
                 }

                 if (worldIn.isAirBlock(position.south()))
                 {
                     ++j;
                 }

                 if (i == 3 && j == 1)
                 {
                     IBlockState iblockstate1 = this.block.getDefaultState();
                     worldIn.setBlockState(position, iblockstate1, 2);
                     worldIn.immediateBlockTick(position, iblockstate1, rand);
                 }

                 return true;
             }
         }
     }
 }
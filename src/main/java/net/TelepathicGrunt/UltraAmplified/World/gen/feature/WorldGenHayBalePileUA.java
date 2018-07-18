package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHay;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenHayBalePileUA extends WorldGenerator{
	

    private final IBlockState hayBaleNS = Blocks.HAY_BLOCK.getDefaultState().withProperty(BlockHay.AXIS, EnumFacing.Axis.Z);
    private final IBlockState hayBaleEW = Blocks.HAY_BLOCK.getDefaultState().withProperty(BlockHay.AXIS, EnumFacing.Axis.X);
    private final IBlockState railNSFlat = Blocks.RAIL.getDefaultState().withProperty(BlockRail.SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);
    private final IBlockState railNSAscending = Blocks.RAIL.getDefaultState().withProperty(BlockRail.SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_NORTH);
    private final IBlockState railNSDecending = Blocks.RAIL.getDefaultState().withProperty(BlockRail.SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_SOUTH);
    private final IBlockState railEWFlat = Blocks.RAIL.getDefaultState().withProperty(BlockRail.SHAPE, BlockRailBase.EnumRailDirection.EAST_WEST);
    private final IBlockState railEWAscending = Blocks.RAIL.getDefaultState().withProperty(BlockRail.SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_EAST);
    private final IBlockState railEWDecending = Blocks.RAIL.getDefaultState().withProperty(BlockRail.SHAPE, BlockRailBase.EnumRailDirection.ASCENDING_WEST);
    private final boolean largePile;

    public WorldGenHayBalePileUA(boolean largePile)
    {
       super(false);
       this.largePile = largePile;
    }

	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		
		//small pile
		if(this.largePile == false) {
			
			//makes sure it is generating completely on land and not over water or air
			if(worldIn.isBlockNormalCube(position.down().north(), false) && worldIn.isBlockNormalCube(position.down().south(), false) && worldIn.isBlockNormalCube(position.down().east(), false) && worldIn.isBlockNormalCube(position.down().west(), false))
			{
				//north/south
				if(rand.nextBoolean()) {
					worldIn.setBlockState(position.north(), hayBaleNS, 2);
					worldIn.setBlockState(position, hayBaleNS, 2);
					worldIn.setBlockState(position.south(), hayBaleNS, 2);
					
					worldIn.setBlockState(position.up(), railEWFlat, 2);
					worldIn.setBlockState(position.west(), railEWAscending, 2);
					worldIn.setBlockState(position.east(), railEWDecending, 2);
				}
			
				//East/West
				else {
					worldIn.setBlockState(position.east(), hayBaleEW, 2);
					worldIn.setBlockState(position, hayBaleEW, 2);
					worldIn.setBlockState(position.west(), hayBaleEW, 2);
					
					worldIn.setBlockState(position.up(), railNSFlat, 2);
					worldIn.setBlockState(position.south(), railNSAscending, 2);
					worldIn.setBlockState(position.north(), railNSDecending, 2);
				}
			}
		}
		else {
			
			//north/south
			if(rand.nextBoolean()) {
				
				//makes sure it is generating completely on land and not over water or air
				if(worldIn.isBlockNormalCube(position.down().north(2), false) && worldIn.isBlockNormalCube(position.down().south(2), false) && worldIn.isBlockNormalCube(position.down().east(2).north(), false) && worldIn.isBlockNormalCube(position.down().west(2).north(), false) && worldIn.isBlockNormalCube(position.down().east(2).south(), false) && worldIn.isBlockNormalCube(position.down().west(2).south(), false))
				{
					worldIn.setBlockState(position.north(2), hayBaleNS, 2);
					worldIn.setBlockState(position.north(), hayBaleNS, 2);
					worldIn.setBlockState(position, hayBaleNS, 2);
					worldIn.setBlockState(position.south(), hayBaleNS, 2);
					worldIn.setBlockState(position.south(2), hayBaleNS, 2);
					
					worldIn.setBlockState(position.north(2).up(), hayBaleNS, 2);
					worldIn.setBlockState(position.north().up(), hayBaleNS, 2);
					worldIn.setBlockState(position.up(), hayBaleNS, 2);
					worldIn.setBlockState(position.south().up(), hayBaleNS, 2);
					worldIn.setBlockState(position.south(2).up(), hayBaleNS, 2);
					
					worldIn.setBlockState(position.north(2).west(), hayBaleNS, 2);
					worldIn.setBlockState(position.north().west(), hayBaleNS, 2);
					worldIn.setBlockState(position.west(), hayBaleNS, 2);
					worldIn.setBlockState(position.south().west(), hayBaleNS, 2);
					worldIn.setBlockState(position.south(2).west(), hayBaleNS, 2);
					
					worldIn.setBlockState(position.north(2).east(), hayBaleNS, 2);
					worldIn.setBlockState(position.north().east(), hayBaleNS, 2);
					worldIn.setBlockState(position.east(), hayBaleNS, 2);
					worldIn.setBlockState(position.south().east(), hayBaleNS, 2);
					worldIn.setBlockState(position.south(2).east(), hayBaleNS, 2);
					
					
					
					worldIn.setBlockState(position.north().up(2), railEWFlat, 2);
					worldIn.setBlockState(position.west(2).north(), railEWAscending, 2);
					worldIn.setBlockState(position.west().north().up(), railEWAscending, 2);
					worldIn.setBlockState(position.east(2).north(), railEWDecending, 2);
					worldIn.setBlockState(position.east().north().up(), railEWDecending, 2);
					
					worldIn.setBlockState(position.south().up(2), railEWFlat, 2);
					worldIn.setBlockState(position.west(2).south(), railEWAscending, 2);
					worldIn.setBlockState(position.west().south().up(), railEWAscending, 2);
					worldIn.setBlockState(position.east(2).south(), railEWDecending, 2);
					worldIn.setBlockState(position.east().south().up(), railEWDecending, 2);
				}
			}
			
			//East/West
			else {
				
				//makes sure it is generating completely on land and not over water or air
				if(worldIn.isBlockNormalCube(position.down().west(2), false) && worldIn.isBlockNormalCube(position.down().east(2), false) && worldIn.isBlockNormalCube(position.down().north(2).west(), false) && worldIn.isBlockNormalCube(position.down().north(2).east(), false) && worldIn.isBlockNormalCube(position.down().south(2).east(), false) && worldIn.isBlockNormalCube(position.down().south(2).west(), false))
				{
					worldIn.setBlockState(position.east(2), hayBaleEW, 2);
					worldIn.setBlockState(position.east(), hayBaleEW, 2);
					worldIn.setBlockState(position, hayBaleEW, 2);
					worldIn.setBlockState(position.west(), hayBaleEW, 2);
					worldIn.setBlockState(position.west(2), hayBaleEW, 2);
					
					worldIn.setBlockState(position.east(2).up(), hayBaleEW, 2);
					worldIn.setBlockState(position.east().up(), hayBaleEW, 2);
					worldIn.setBlockState(position.up(), hayBaleEW, 2);
					worldIn.setBlockState(position.west().up(), hayBaleEW, 2);
					worldIn.setBlockState(position.west(2).up(), hayBaleEW, 2);
					
					worldIn.setBlockState(position.east(2).north(), hayBaleEW, 2);
					worldIn.setBlockState(position.east().north(), hayBaleEW, 2);
					worldIn.setBlockState(position.north(), hayBaleEW, 2);
					worldIn.setBlockState(position.west().north(), hayBaleEW, 2);
					worldIn.setBlockState(position.west(2).north(), hayBaleEW, 2);
					
					worldIn.setBlockState(position.east(2).south(), hayBaleEW, 2);
					worldIn.setBlockState(position.east().south(), hayBaleEW, 2);
					worldIn.setBlockState(position.south(), hayBaleEW, 2);
					worldIn.setBlockState(position.west().south(), hayBaleEW, 2);
					worldIn.setBlockState(position.west(2).south(), hayBaleEW, 2);
					
					
					
					worldIn.setBlockState(position.east().up(2), railEWFlat, 2);
					worldIn.setBlockState(position.north(2).east(), railNSDecending, 2);
					worldIn.setBlockState(position.north().east().up(), railNSDecending, 2);
					worldIn.setBlockState(position.south(2).east(), railNSAscending, 2);
					worldIn.setBlockState(position.south().east().up(), railNSAscending, 2);
					
					worldIn.setBlockState(position.west().up(2), railEWFlat, 2);
					worldIn.setBlockState(position.north(2).west(), railNSDecending, 2);
					worldIn.setBlockState(position.north().west().up(), railNSDecending, 2);
					worldIn.setBlockState(position.south(2).west(), railNSAscending, 2);
					worldIn.setBlockState(position.south().west().up(), railNSAscending, 2);
				}
			}
			
		}
		
		
		return true;
	}

}

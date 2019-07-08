package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.block.BlockHay;
import net.minecraft.block.BlockRail;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class WorldGenHayBalePileUA extends Feature<NoFeatureConfig> {
	

    private final IBlockState hayBaleNS = Blocks.HAY_BLOCK.getDefaultState().with(BlockHay.AXIS, EnumFacing.Axis.Z);
    private final IBlockState hayBaleEW = Blocks.HAY_BLOCK.getDefaultState().with(BlockHay.AXIS, EnumFacing.Axis.X);
    private final IBlockState railNSFlat = Blocks.RAIL.getDefaultState().with(BlockRail.SHAPE, RailShape.NORTH_SOUTH);
    private final IBlockState railNSAscending = Blocks.RAIL.getDefaultState().with(BlockRail.SHAPE, RailShape.ASCENDING_NORTH);
    private final IBlockState railNSDecending = Blocks.RAIL.getDefaultState().with(BlockRail.SHAPE, RailShape.ASCENDING_SOUTH);
    private final IBlockState railEWFlat = Blocks.RAIL.getDefaultState().with(BlockRail.SHAPE, RailShape.EAST_WEST);
    private final IBlockState railEWAscending = Blocks.RAIL.getDefaultState().with(BlockRail.SHAPE, RailShape.ASCENDING_EAST);
    private final IBlockState railEWDecending = Blocks.RAIL.getDefaultState().with(BlockRail.SHAPE, RailShape.ASCENDING_WEST);

    public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_) 
    {
        
  		if(!ConfigUA.miniStructureGeneration) {
  			return false;
  		}
		
		//north/south
		if(rand.nextBoolean()) {
			
			//makes sure it is generating completely on land and not over water or air
			if( this.isBlockNormalCube(worldIn, position.down().north(2), false) && 
				this.isBlockNormalCube(worldIn, position.down().south(2), false) && 
				this.isBlockNormalCube(worldIn, position.down().east(2).north(), false) && 
				this.isBlockNormalCube(worldIn, position.down().west(2).north(), false) && 
				this.isBlockNormalCube(worldIn, position.down().east(2).south(), false) && 
				this.isBlockNormalCube(worldIn, position.down().west(2).south(), false))
			{
				for(int i = -2; i <= 2; i++) {
					worldIn.setBlockState(position.north(i), hayBaleNS, 2);
					worldIn.setBlockState(position.north(i).up(), hayBaleNS, 2);
					worldIn.setBlockState(position.north(i).west(), hayBaleNS, 2);
					worldIn.setBlockState(position.north(i).east(), hayBaleNS, 2);
				}
				
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
			if(this.isBlockNormalCube(worldIn, position.down().west(2), false) && 
			   this.isBlockNormalCube(worldIn, position.down().east(2), false) && 
			   this.isBlockNormalCube(worldIn, position.down().north(2).west(), false) && 
			   this.isBlockNormalCube(worldIn, position.down().north(2).east(), false) && 
			   this.isBlockNormalCube(worldIn, position.down().south(2).east(), false) && 
			   this.isBlockNormalCube(worldIn, position.down().south(2).west(), false))
			{
				
				for(int i = -2; i <= 2; i++) {
					worldIn.setBlockState(position.east(i), hayBaleEW, 2);
					worldIn.setBlockState(position.east(i).up(), hayBaleEW, 2);
					worldIn.setBlockState(position.east(i).north(), hayBaleEW, 2);
					worldIn.setBlockState(position.east(i).south(), hayBaleEW, 2);
				}
				
				
				worldIn.setBlockState(position.east().up(2), railNSFlat, 2);
				worldIn.setBlockState(position.north(2).east(), railNSDecending, 2);
				worldIn.setBlockState(position.north().east().up(), railNSDecending, 2);
				worldIn.setBlockState(position.south(2).east(), railNSAscending, 2);
				worldIn.setBlockState(position.south().east().up(), railNSAscending, 2);
				
				worldIn.setBlockState(position.west().up(2), railNSFlat, 2);
				worldIn.setBlockState(position.north(2).west(), railNSDecending, 2);
				worldIn.setBlockState(position.north().west().up(), railNSDecending, 2);
				worldIn.setBlockState(position.south(2).west(), railNSAscending, 2);
				worldIn.setBlockState(position.south().west().up(), railNSAscending, 2);
			}
		}
			
		return true;
	}

    /**
     * Checks if a block's material is opaque, and that it takes up a full cube
     */
    private boolean isBlockNormalCube(IWorld worldIn, BlockPos pos, boolean _default)
    {
        if (pos.getY() > 255)
        {
            return false;
        }
        else
        {
            IBlockState iblockstate1 = worldIn.getBlockState(pos);
            return iblockstate1.getBlock().isNormalCube(iblockstate1, worldIn, pos);
        }
    }
}

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

public class WorldGenHayBaleTinyPileUA extends Feature<NoFeatureConfig> {
	

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
  		
		//makes sure it is generating completely on land and not over water or air
		if( this.isBlockNormalCube(worldIn, position.down().north(), false) && 
			this.isBlockNormalCube(worldIn, position.down().south(), false) && 
			this.isBlockNormalCube(worldIn, position.down().east(), false) && 
			this.isBlockNormalCube(worldIn, position.down().west(), false))
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

package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.block.RailBlock;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class HayBaleTinyPile extends Feature<NoFeatureConfig> {
	

    public HayBaleTinyPile(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	private final BlockState hayBaleNS = Blocks.HAY_BLOCK.getDefaultState().with(HayBlock.AXIS, Direction.Axis.Z);
    private final BlockState hayBaleEW = Blocks.HAY_BLOCK.getDefaultState().with(HayBlock.AXIS, Direction.Axis.X);
    private final BlockState railNSFlat = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.NORTH_SOUTH);
    private final BlockState railNSAscending = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.ASCENDING_NORTH);
    private final BlockState railNSDecending = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.ASCENDING_SOUTH);
    private final BlockState railEWFlat = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.EAST_WEST);
    private final BlockState railEWAscending = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.ASCENDING_EAST);
    private final BlockState railEWDecending = Blocks.RAIL.getDefaultState().with(RailBlock.SHAPE, RailShape.ASCENDING_WEST);

    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_) 
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
            return worldIn.getBlockState(pos).isNormalCube(worldIn, pos);
        }
    }
}

package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.config.BlockConfig;

public class Roots extends Feature<BlockConfig> {
    public Roots(Function<Dynamic<?>, ? extends BlockConfig> configFactoryIn) {
		super(configFactoryIn);
	}


	protected long seed;
	protected OctavesNoiseGenerator noiseGen;

	public void setSeed(long seed) {
		if (this.noiseGen == null) {
			this.noiseGen = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 4);
		}

		this.seed = seed;
	}


    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, BlockConfig blockConfig)
    {
		setSeed(rand.nextLong());
		
		//wont generate root if config is turned off
		//won't generate root on leaves, water, etc.
		//Only solid blocks can have roots
		if(!ConfigUA.rootGen || !worldIn.getBlockState(position).isSolid()) {
			return false;
		}
		
		position = position.down();
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
		BlockState currentBlockState;
		int xOffset;
		int zOffset;
		int yOffset;

		//increase the number of roots the higher up in the world it is
		int numOfRoots = 1+(position.getY()-70)/50;
		for(int rootNum = 1; rootNum < numOfRoots+1; rootNum++){
			
			//set mutable block pos back to the starting block of the roots
			mutableBlockPos.setPos(position);
			

			//makes root length increase the higher up in the world it is
			int rootLength = 2+(position.getY()-70)/22;
		
			//attempts to grow root branch as long as the new position is an air block. Otherwise, terminate root
			for(int length = 0; length < rootLength; length++){

				//checks to see if air block is not higher than starting place
				currentBlockState = worldIn.getBlockState(mutableBlockPos);
				if(mutableBlockPos.getY() <= position.getY() && 
				    (currentBlockState.getMaterial() == Material.AIR ||
				     currentBlockState == blockConfig.block.getDefaultState() || 
				     currentBlockState == Blocks.VINE.getDefaultState())
				){

					
					//checks to see if there is solid land still above (1 blocks higher than position height)
					if(worldIn.getBlockState( new BlockPos(mutableBlockPos.getX(), 
															position.getY()+1, 
															mutableBlockPos.getZ()
														   )).isSolid()) {

						//set root block
						worldIn.setBlockState(mutableBlockPos, blockConfig.block.getDefaultState(), 2);
						
						//rare chance to also generate a vine
						if(rand.nextFloat() < 1F) {
							generateTinyVine(worldIn, rand, mutableBlockPos);
						}
						

					}else {
						break;
					}
					
				}
				//The position was either too high or was a solid block ( not a root) and so ends this branch
				else
				{
					break;
				}
				
				
				//move to next place to grow root to
				//range is clamped to -1 to 1 due to int rounding
				xOffset = (int)MathHelper.clamp(this.noiseGen.func_205563_a(mutableBlockPos.getX() * 2D+20000*rootNum, mutableBlockPos.getZ() * 2D+20000*rootNum, mutableBlockPos.getY()*1D+20000*rootNum), -1, 1);
				zOffset = (int)MathHelper.clamp(this.noiseGen.func_205563_a(mutableBlockPos.getX() * 2D+10000*rootNum, mutableBlockPos.getZ() * 2D+10000*rootNum, mutableBlockPos.getY()*1D+10000*rootNum), -1, 1);
				yOffset = (int)MathHelper.clamp(this.noiseGen.func_205563_a(mutableBlockPos.getX() * 2D-10000*rootNum, mutableBlockPos.getZ() * 2D-10000*rootNum, mutableBlockPos.getY()*1D-10000)*rootNum - 1, -1, 1);

				
				//debugging
				//System.out.println(xOffset +", "+zOffset+", "+yOffset);
				mutableBlockPos.move(xOffset, yOffset, zOffset);
			}
		}


		return true;
	}
    
    
    private void generateTinyVine(IWorld worldIn, Random rand, BlockPos position) {
    	//generates vines from given position down 4 blocks if path is clear and the given position is valid
		//Also won't generate vines below Y = 1.
		int length = 0;
		
		//begin vine generation
		for (; position.getY() > 1 && length < 4; position = position.down()) 
		{
			if (worldIn.isAirBlock(position)) 
			{
				for (Direction Direction : Direction.Plane.HORIZONTAL) 
				{
					BlockState iblockstate = Blocks.VINE.getDefaultState().with(VineBlock.getPropertyFor(Direction), Boolean.valueOf(true));
					if (iblockstate.isValidPosition(worldIn, position)) 
					{
						worldIn.setBlockState(position, iblockstate, 2);
						break;
					} 
					else if (worldIn.getBlockState(position.up()).getBlock() == Blocks.VINE) 
					{
						worldIn.setBlockState(position, worldIn.getBlockState(position.up()), 2);
						length++;
						break;
					}
				}
			}
		}
    }
}

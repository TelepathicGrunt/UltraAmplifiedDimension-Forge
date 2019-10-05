package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class BlueIceWaterfall extends Feature<NoFeatureConfig> {

public BlueIceWaterfall(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

private static final BlockState BLUE_ICE = Blocks.BLUE_ICE.getDefaultState();
private static final BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();
private static final BlockState SNOW = Blocks.SNOW.getDefaultState();
private static final BlockState AIR = Blocks.AIR.getDefaultState();

	protected static final Set<Block> acceptableBlocks = 
    		Stream.of(
	    		Blocks.ICE,
	    		Blocks.SNOW_BLOCK,
	    		Blocks.PACKED_ICE
    		).collect(Collectors.toCollection(HashSet::new));
	
public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig fluidConfig) {
	
		//creates a waterfall of blue ice that has a puddle at bottom
	
    	 if (!acceptableBlocks.contains(worldIn.getBlockState(position.up()).getBlock()))
         {
             return false;
         }
         else if (!acceptableBlocks.contains(worldIn.getBlockState(position.down()).getBlock()))
         {
             return false;
         }
         else
         {
        	 //checks if we are in the side of a wall with air exposed on one side
        	 
             int numberOfSolidSides = 0;
             Direction emptySpot = Direction.NORTH;

             for (Direction face : Direction.Plane.HORIZONTAL) {
   
             	if(acceptableBlocks.contains(worldIn.getBlockState(position.offset(face)).getBlock())) 
             	{
             		++numberOfSolidSides;
             	}
             	else {
             		emptySpot = face;
             	}
             }
             
             
             //position valid. begin making ice waterfall
             if (numberOfSolidSides == 3)
             {
            	 
            	 //spot in wall
                 worldIn.setBlockState(position, BLUE_ICE, 2);
                 
                 //set what direction the open side of the wall is
                 BlockPos curPos = position.offset(emptySpot);
   			     worldIn.setBlockState(curPos, BLUE_ICE, 2);
            	 
            	 
   			     int ledges = 0;
            	 //places blue ice downward until it hit solid block
            	 while(true) 
            	 {
            		 if(curPos.getY() <= 1) {
            			 return false;
            		 }
            		 
            		 if(worldIn.getBlockState(curPos.down()).getMaterial() == Material.AIR ||
            			worldIn.getBlockState(curPos.down()) == BLUE_ICE ||
            			worldIn.getBlockState(curPos.down()) == SNOW_BLOCK)
            		 {
            			  curPos = curPos.down();
            			  worldIn.setBlockState(curPos, BLUE_ICE, 2);
            			  continue;
            		 }
            		 
            		 
            		 if(ledges >= 2) {
            			 break;
            		 }
            		 
            		 
            		 boolean spotFound = false;
                     
            		 //goes around ledge
                     for (Direction face : Direction.Plane.HORIZONTAL) 
                     {
                    	 
                		 if((worldIn.getBlockState(curPos.offset(face)).getMaterial() == Material.AIR ||
                     		 worldIn.getBlockState(curPos.offset(face)) == BLUE_ICE) &&
                			(worldIn.getBlockState(curPos.down().offset(face)).getMaterial() == Material.AIR ||
                			 worldIn.getBlockState(curPos.down().offset(face)) == BLUE_ICE)) 
                		 {
    	           			  curPos = curPos.offset(emptySpot);
    	           			  worldIn.setBlockState(curPos, BLUE_ICE, 2);
    	           			  curPos = curPos.down();
    	           			  worldIn.setBlockState(curPos, BLUE_ICE, 2);
    	           			  ledges++;
    	           			  spotFound = true;
    	           			  
    	           			  if(curPos.getY() <= 1) 
    	           			  {
    	           				  return false;
    	           			  }
    	           			  else 
    	           			  {
    	           				  break;
    	           			  }
                		 }
                     }
                     
                     if(!spotFound) {
                    	 break;
                     }
            		 
            	 }
            	 
            	 
            	 //creates blue ice puddle at bottom
            	 int width = rand.nextInt(2)+2;
                 for (int y = curPos.getY() - 1; y < curPos.getY() + 1; ++y)
                 {
                	  for (int x = -width; x <= width; ++x)
	                 {
	                     for (int z = -width; z <= width; ++z)
	                     {
	                         if (x * x + z * z <= width * width)
	                         {
                            	 if(y > 1 && y < 255) {
                            		 
                            		 BlockPos blockpos = new BlockPos(x + curPos.getX(), y, z + curPos.getZ());
	                                 BlockState block = worldIn.getBlockState(blockpos);
	
	                                 if (block == Blocks.ICE.getDefaultState() || block == SNOW_BLOCK)
	                                 {
	                                     worldIn.setBlockState(blockpos, BLUE_ICE, 2);
	                                 }
                            	 }
                            	 else {
                            		 break;
                            	 }
	                                 
                             }
                         }
                 	}
                	  
            	  	width++;
             	}
                 
                 
                 //remove thin snow above the highest blue ice puddle
                for (int x = -width; x <= width; ++x)
                {
                     for (int z = -width; z <= width; ++z)
                     {
                    	 if (x * x + z * z <= width * width)
	                     {
                    		 BlockPos blockpos = new BlockPos(x + curPos.getX(), curPos.getY() + 1, z + curPos.getZ());
                             BlockState block = worldIn.getBlockState(blockpos);
                             
	                    	 if(block == SNOW) 
	                         {
	                        	 worldIn.setBlockState(blockpos, AIR, 2);
	                         }
	                     }
                     }
                }
                 
             }
             return true;
         }
     }

 }
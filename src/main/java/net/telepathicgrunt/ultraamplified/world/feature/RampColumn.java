package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.feature.config.ColumnBlocksConfig;

public class RampColumn extends Feature<ColumnBlocksConfig> 
{
    protected OctavesNoiseGenerator noiseGen;
    protected long seed;
    private final BlockState AIR = Blocks.AIR.getDefaultState();
    
    public void setSeed(long seed) {
       if (this.noiseGen == null) {
          this.noiseGen = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 4);
       }

       this.seed = seed;
    }
    
    public RampColumn(Function<Dynamic<?>, ? extends ColumnBlocksConfig> configFactoryIn) {
		super(configFactoryIn);
	}


    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, ColumnBlocksConfig blocksConfig) 
    {
    	//check if user turned pillars off.
    	if(!ConfigUA.pillarGen) {
    		 return false;
     	}
    	
    	setSeed(rand.nextLong());
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);
    	int minWidth = 4;
    	int currentHeight = 0;
    	int ceilingHeight = currentHeight;
    	int bottomFloorHeight = currentHeight;
    	int topFloorHeight = currentHeight;
    	int heightDiff = 0;
    	
        
        //finds ceiling
        while (!worldIn.getBlockState(blockpos$Mutable.up(currentHeight)).isSolid())
        {
        	//too high for ramp to generate
        	if(blockpos$Mutable.up(currentHeight).getY() > 254) {
        		return false;
        	}
        	currentHeight+=2;
        }
        ceilingHeight = blockpos$Mutable.up(currentHeight).getY();
        

        //finds floor above ceiling
        while (worldIn.getBlockState(blockpos$Mutable.up(currentHeight)).isSolid())
        {
        	//too high for ramp to generate
        	if(blockpos$Mutable.up(currentHeight).getY() > 254) {
        		return false;
        	}
        	currentHeight++;
        }
        topFloorHeight = blockpos$Mutable.up(currentHeight).getY();
        
        
        //too thick or thin for ramp to generate
        if(topFloorHeight - ceilingHeight > 7 || topFloorHeight - ceilingHeight < 2) {
        	return false;
        }
        
        
        //find floor
        currentHeight = 0;
        while (!worldIn.getBlockState(blockpos$Mutable.up(currentHeight)).isSolid())
        {
        	//too low/tall for column to generate
        	if(blockpos$Mutable.up(currentHeight).getY() < 70) {
        		return false;
        	}
        	currentHeight-=2;
        }
        bottomFloorHeight = blockpos$Mutable.up(currentHeight).getY();
        
        
        heightDiff = ceilingHeight - bottomFloorHeight;
        if(heightDiff > 27 || heightDiff < 8) {
        	//too tall or short for a column ramp to spawn
        	return false;
        }


        //how much to turn on a range of -1 to 1. -1 for north, 0 for south
        float randFloat = rand.nextFloat();
        float xTurningValue = (float) Math.sin(randFloat*Math.PI*2);
        float zTurningValue = (float) Math.cos(randFloat*Math.PI*2);
        
        
        
        
        int widthAtHeight = 0;
        //min thickness   where we are in height  /  controls thickening rate 
     	widthAtHeight = getWidthAtHeight(0, heightDiff+5, minWidth);
        
        //checks to see if there is enough land above and below to hold pillar
        for (int x = position.getX() - widthAtHeight; x <= position.getX() + widthAtHeight; x+=3)
        {
            for (int z = position.getZ() - widthAtHeight; z <= position.getZ() + widthAtHeight; z+=3)
            {
                int xDiff = x - position.getX();
                int zDiff = z - position.getZ();
            	if(xDiff * xDiff + zDiff * zDiff <= (widthAtHeight * widthAtHeight)) {
                    BlockState block1 = worldIn.getBlockState(blockpos$Mutable.setPos(x + getOffsetAtHeight(heightDiff + 1, heightDiff, xTurningValue), ceilingHeight + 2, z + getOffsetAtHeight(0, heightDiff, zTurningValue)));
                    BlockState block2 = worldIn.getBlockState(blockpos$Mutable.setPos(x - getOffsetAtHeight(-1, heightDiff, xTurningValue), bottomFloorHeight - 2, z - getOffsetAtHeight(0, heightDiff, zTurningValue)));
                    
                    //there is not enough land to contain bases of pillar
                    if(!block1.isSolid() || !block2.isSolid()) {
                    	return false;
                    }
            	}
            }
        }
        
        
        //position is valid for pillar gen.
        //UltraAmplified.Logger.log(Level.DEBUG, "Ramp at "+position.getX() +", "+position.getY()+", "+position.getZ() +"  | "+xTurningValue +", "+zTurningValue);
        
        
        //makes ramp
        for (int y = -2; y <= heightDiff+5; y++)
        {
        	               //min thickness   where we are in height  /  controls thickening rate 
        	widthAtHeight = getWidthAtHeight(y, heightDiff+5, minWidth);
        	

        	int xOffset = getOffsetAtHeight(y, heightDiff, xTurningValue);
        	int zOffset = getOffsetAtHeight(y, heightDiff, zTurningValue);
        	
	        //Begin column gen
	        for (int x = position.getX() - widthAtHeight - 1; x <= position.getX() + widthAtHeight + 1; ++x)
	        {
	            for (int z = position.getZ() - widthAtHeight - 1; z <= position.getZ() + widthAtHeight + 1; ++z)
	            {
	                int xDiff = x - position.getX();
	                int zDiff = z - position.getZ();
                    blockpos$Mutable.setPos(x + xOffset, y + bottomFloorHeight, z + zOffset);
                    
                    
                    
	                //creates pillar with inside block
                    int xzDiffSquaredStretched = (xDiff * xDiff) + (zDiff * zDiff);
	                if (y <= heightDiff && xzDiffSquaredStretched <= (widthAtHeight-1) * (widthAtHeight-1) - 0.5F)
	                {
                        BlockState block = worldIn.getBlockState(blockpos$Mutable);

                        if (!block.isSolid())
                        {
                            worldIn.setBlockState(blockpos$Mutable, blocksConfig.insideBlock, 2);
                        }
                    }
	                //We are at non-pillar space 
	                //adds top and middle block to pillar part exposed in the below half of pillar
	                else if(y > heightDiff || xzDiffSquaredStretched <= (widthAtHeight+3) * (widthAtHeight+3)){
	                	//top block followed by 4 middle blocks below that
	                	for(int downward = 0; downward < 6 && y - downward >= -3; downward++) 
	                	{
	                        BlockState block = worldIn.getBlockState(blockpos$Mutable.down(downward));
	                        BlockState blockBelow = worldIn.getBlockState(blockpos$Mutable.down(downward+1));
	                        if (block == blocksConfig.insideBlock)
	                        {
	                        	if(downward == 1 && !(blocksConfig.topBlock.getMaterial() == Material.SAND && blockBelow.getMaterial() == Material.AIR)) {
	                        		worldIn.setBlockState(blockpos$Mutable.down(downward), blocksConfig.topBlock, 2);
	                        	}else {
	                        		worldIn.setBlockState(blockpos$Mutable.down(downward), blocksConfig.middleBlock, 2);
	                        	}
	                            
	                        }
	                	}
                    }
	                

	                //clears out space above disk so there is a hole for entire ramp
	                int holeHeight = 5;
                    BlockState block = worldIn.getBlockState(blockpos$Mutable.up(holeHeight));
	                if(block.getMaterial() != Material.AIR && xzDiffSquaredStretched <= (widthAtHeight-1) * (widthAtHeight-1) - 0.5F) {
                		worldIn.setBlockState(blockpos$Mutable.up(holeHeight), AIR, 2);
                		
                		//adds top block to exposed middle block after air was set
                		BlockState blockBelowAir = worldIn.getBlockState(blockpos$Mutable.up(holeHeight-1));
                        BlockState blockBelowBelowAir = worldIn.getBlockState(blockpos$Mutable.up(holeHeight-2));
                        if (blockBelowAir.isSolid())
                        {
                        	if(blocksConfig.topBlock.getMaterial() == Material.SAND && blockBelowBelowAir.getMaterial() == Material.AIR) {
                        		worldIn.setBlockState(blockpos$Mutable.up(holeHeight-1), blocksConfig.middleBlock, 2);
                        	}else {
                        		worldIn.setBlockState(blockpos$Mutable.up(holeHeight-1), blocksConfig.topBlock, 2);
                        	}
                            
                        }
	                }
                }
            }
        }

        return true;
    }
    

    private int getWidthAtHeight(int y, int heightDiff, int thinnestWidth) 
    {
		float yFromCenter = y - heightDiff * 0.5F;
		yFromCenter = Math.abs(yFromCenter * 0.4F) + 3;
		
		
		return thinnestWidth + (int) ((yFromCenter * yFromCenter) / 8);
    }
    

    private int getOffsetAtHeight(int y, int heightDiff, float turningValue) 
    {
		float yFromCenter = y - heightDiff / 2F;
		return (int) (turningValue*yFromCenter);
    }
}

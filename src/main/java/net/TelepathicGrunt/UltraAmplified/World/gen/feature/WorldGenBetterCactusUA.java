package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBetterCactusUA extends WorldGenerator
{
	private final int height; 
	
	public WorldGenBetterCactusUA(int height) {
		super();
		this.height = height;
	}
	
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
    	//randomly set this cactus to a random spot. (thus passed in position must be the corner of the 4 loaded chunks)
        BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

        if (worldIn.isAirBlock(blockpos) && worldIn.getBlockState(blockpos.down()) == Blocks.SAND.getDefaultState())
        {
            int maxHeight = height + rand.nextInt(2);

            //creates main body of cactus
            for (int currentHeight = 0; currentHeight < maxHeight; ++currentHeight)
            {
               worldIn.setBlockState(blockpos.up(currentHeight), Blocks.CACTUS.getDefaultState(), 2);
            }
            
            
            //below code will generate cactus facing north/south 50% of the time and east/west the other times.
            //it will also generate just left arm 1/3rd of time, just right arm 1/3rd of time, and both arms the other 1/3rd of times.
            
            //face east/west direction
            if(rand.nextBoolean()) {
            	 
            	//used to determine what arms will spawn for this cactus
            	int arms = rand.nextInt(3);
            	
            	//left arm
	            if(arms == 0 || arms == 2) 
	            {
	            	//trying to make sure arm does not equal or exceed main body height.
	            	int leftArmHeight = rand.nextInt(maxHeight-5)+2;
	            	int leftArmTallness = leftArmHeight + rand.nextInt(maxHeight - leftArmHeight - 2) + 2;
	            	
	            	worldIn.setBlockState(blockpos.up(leftArmHeight).west(), Blocks.CACTUS.getDefaultState(), 2);
	            
		            for (int k = leftArmHeight; k < leftArmTallness; ++k)
		            {
		                worldIn.setBlockState(blockpos.up(k).west(2), Blocks.CACTUS.getDefaultState(), 2);
		            }
	            }
	            
	            //right arm 
	            if(arms == 1 || arms == 2) 
	            {
	            	//trying to make sure arm does not equal or exceed main body height.
	            	int rightArmHeight = rand.nextInt(maxHeight-5)+2;
	            	int rightArmTallness = rightArmHeight + rand.nextInt(maxHeight - rightArmHeight - 2) + 2;
	            	
	            	worldIn.setBlockState(blockpos.up(rightArmHeight).east(), Blocks.CACTUS.getDefaultState(), 2);
	            
		            for (int k = rightArmHeight; k < rightArmTallness ; ++k)
		            {
		                worldIn.setBlockState(blockpos.up(k).east(2), Blocks.CACTUS.getDefaultState(), 2);
		            }
	            }
            }
            
            //face North/South Direction
            else {
            	
            	//used to determine what arms will spawn for this cactus
            	int arms = rand.nextInt(3);
            	
            	//north arm 
	            if(arms == 0 || arms == 2) 
	            {
	            	//trying to make sure arm does not equal or exceed main body height.
	            	int northArmHeight = rand.nextInt(maxHeight-5)+2;
	            	int northArmTallness = northArmHeight + rand.nextInt(maxHeight - northArmHeight - 2) + 2;
	            	
	            	worldIn.setBlockState(blockpos.up(northArmHeight).north(), Blocks.CACTUS.getDefaultState(), 2);
	            
		            for (int k = northArmHeight; k < northArmTallness ; ++k)
		            {
		                worldIn.setBlockState(blockpos.up(k).north(2), Blocks.CACTUS.getDefaultState(), 2);
		            }
	            }
	            
	            //south arm 
	            if(arms == 1 || arms == 2) 
	            {
	            	//trying to make sure arm does not equal or exceed main body height.
	            	int southArmHeight = rand.nextInt(maxHeight-5)+2;
	            	int southArmTallness = southArmHeight + rand.nextInt(maxHeight - southArmHeight - 2) + 2;
	            	
	            	worldIn.setBlockState(blockpos.up(southArmHeight).south(), Blocks.CACTUS.getDefaultState(), 2);
	            
		            for (int k = southArmHeight; k < southArmTallness ; ++k)
		            {
		                worldIn.setBlockState(blockpos.up(k).south(2), Blocks.CACTUS.getDefaultState(), 2);
		            }
	            }
            }
        }
        
        //cactus finished generating
        return true;
    }
}

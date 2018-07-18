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
        BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

        if (worldIn.isAirBlock(blockpos) && worldIn.getBlockState(blockpos.down()) == Blocks.SAND.getDefaultState())
        {
            int j = height + rand.nextInt(2);

            for (int k = 0; k < j; ++k)
            {
               worldIn.setBlockState(blockpos.up(k), Blocks.CACTUS.getDefaultState(), 2);
            }
            
            //face east/west direction
            if(rand.nextBoolean()) {
            	//left arm 
            	int arms = rand.nextInt(3);
            	
	            if(arms == 0 || arms == 2) {
	            	int leftArmHeight = rand.nextInt(j-5)+2;
	            	int leftArmTallness = leftArmHeight + rand.nextInt(j - leftArmHeight - 2) + 2;
	            	
	            	worldIn.setBlockState(blockpos.up(leftArmHeight).west(), Blocks.CACTUS.getDefaultState(), 2);
	            
		            for (int k = leftArmHeight; k < leftArmTallness; ++k)
		            {
		                worldIn.setBlockState(blockpos.up(k).west(2), Blocks.CACTUS.getDefaultState(), 2);
		            }
	            }
	            
	            //right arm 
	            if(arms == 1 || arms == 2) {
	            	int rightArmHeight = rand.nextInt(j-5)+2;
	            	int rightArmTallness = rightArmHeight + rand.nextInt(j - rightArmHeight - 2) + 2;
	            	
	            	worldIn.setBlockState(blockpos.up(rightArmHeight).east(), Blocks.CACTUS.getDefaultState(), 2);
	            
		            for (int k = rightArmHeight; k < rightArmTallness ; ++k)
		            {
		                worldIn.setBlockState(blockpos.up(k).east(2), Blocks.CACTUS.getDefaultState(), 2);
		            }
	            }
            }
            
            //face North/South Direction
            else {
            	//north arm 
            	int arms = rand.nextInt(3);
            	
	            if(arms == 0 || arms == 2) {
	            	int northArmHeight = rand.nextInt(j-5)+2;
	            	int northArmTallness = northArmHeight + rand.nextInt(j - northArmHeight - 2) + 2;
	            	
	            	worldIn.setBlockState(blockpos.up(northArmHeight).north(), Blocks.CACTUS.getDefaultState(), 2);
	            
		            for (int k = northArmHeight; k < northArmTallness ; ++k)
		            {
		                worldIn.setBlockState(blockpos.up(k).north(2), Blocks.CACTUS.getDefaultState(), 2);
		            }
	            }
	            
	            //south arm 
	            if(arms == 1 || arms == 2) {
	            	int southArmHeight = rand.nextInt(j-5)+2;
	            	int southArmTallness = southArmHeight + rand.nextInt(j - southArmHeight - 2) + 2;
	            	
	            	worldIn.setBlockState(blockpos.up(southArmHeight).south(), Blocks.CACTUS.getDefaultState(), 2);
	            
		            for (int k = southArmHeight; k < southArmTallness ; ++k)
		            {
		                worldIn.setBlockState(blockpos.up(k).south(2), Blocks.CACTUS.getDefaultState(), 2);
		            }
	            }
            }
            
            
            
        }
        

        return true;
    }
}

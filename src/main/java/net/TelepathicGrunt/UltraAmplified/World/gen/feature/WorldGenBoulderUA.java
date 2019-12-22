package net.telepathicgrunt.ultraamplified.World.gen.feature;

import java.util.Random;

import net.telepathicgrunt.ultraamplified.Config.UAConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenBoulderUA extends WorldGenerator
{
    private final Block mossyCobblestone;
    private final Block cobblestone;
    private final IBlockState andesite;
    private final Block coalOre;
    private final Block ironOre;
    private final Block diamondOre;
    private final int startRadius;
    private final boolean stackable;

    public WorldGenBoulderUA(int startRadiusIn, boolean stackable)
    {
        super(false);
        this.mossyCobblestone = Blocks.MOSSY_COBBLESTONE;
        this.cobblestone = Blocks.COBBLESTONE;
        this.andesite = Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE);
        this.coalOre = Blocks.COAL_ORE;
        this.ironOre = Blocks.IRON_ORE;
        this.diamondOre = Blocks.DIAMOND_ORE;
        this.startRadius = startRadiusIn;
        this.stackable = stackable;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        while (true)
        {
            label0:
            { 
        		if (position.getY() <= 6 || position.getY() >= 250)
                {
                    return false;
                }
            
        		//this and position = position.down(); will keeps moving down position until it finds ground to generate on
                if (worldIn.isAirBlock(position.down()))
                {
                    break label0;
                }
                Block block = worldIn.getBlockState(position.down()).getBlock();

                
                if(this.stackable) 
                {
                	//allows the boulder to be set on top of another boulder
	                if (block != Blocks.STONE && block != Blocks.COBBLESTONE && block != Blocks.MOSSY_COBBLESTONE && block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.COAL_ORE && block != Blocks.IRON_ORE && block != Blocks.DIAMOND_ORE)
	                {
	                    break label0;
	                }
                }
                else 
                {
                	//boulder will ignore other boulders and will generate only on ground
                	if (block != Blocks.GRASS && block != Blocks.DIRT)
	                {
	                    break label0;
	                }
                }
                

                int radius = this.startRadius;

                for (int currentCount = 0; radius >= 0 && currentCount < 3; ++currentCount)
                {
                    int x = radius + rand.nextInt(2);
                    int y = radius + rand.nextInt(2);
                    int z = radius + rand.nextInt(2);
                    float calculatedDistance = (float)(x + y + z) * 0.333F + 0.5F;

                    for (BlockPos blockpos : BlockPos.getAllInBoxMutable(position.add(-x, -y, -z), position.add(x, y, z)))
                    {
                        if (blockpos.distanceSq(position) <= (double)(calculatedDistance * calculatedDistance))
                        {
                        	//adds the blocks for generation in this boulder
                        	//note, if user turns off an ore, that ore's chance is dumped into the below ore for generation
                        	int randomChance = rand.nextInt(1400);
                        	
                        	// 1/1400th chance for diamond ore
                        	if(UAConfig.oreAndFeatures.mainOresOptions.diamondOreSpawnrate != 0 && randomChance == 0) {
                        		worldIn.setBlockState(blockpos, this.diamondOre.getDefaultState(), 4);
                        	}
                        	
                        	// 39/1400th chance for iron ore
                        	else if(UAConfig.oreAndFeatures.mainOresOptions.ironOreSpawnrate != 0 && randomChance <= 40){
                        		worldIn.setBlockState(blockpos, this.ironOre.getDefaultState(), 4);
                        	}
                        	
                        	// 60/1400th chance for coal ore
                        	else if(UAConfig.oreAndFeatures.mainOresOptions.coalOreSpawnrate != 0 && randomChance <= 100){
                        		worldIn.setBlockState(blockpos, this.coalOre.getDefaultState(), 4);
                        	}
                        	
                        	// 300/1400th chance for andesite
                        	else if(randomChance <= 400){
                        		worldIn.setBlockState(blockpos, this.andesite, 4);
                        	}
                        	
                        	// 300/1400th chance for cobblestone
                        	else if(randomChance <= 700){
                        		worldIn.setBlockState(blockpos, this.cobblestone.getDefaultState(), 4);
                        	}
                        	
                        	// 700/1400th chance for mossyCobblestone
                        	else {
                        		worldIn.setBlockState(blockpos, this.mossyCobblestone.getDefaultState(), 4);
                        	}
                        }
                    }
                    position = position.add(-(radius + 1) + rand.nextInt(2 + radius * 2), 0 - rand.nextInt(2), -(radius + 1) + rand.nextInt(2 + radius * 2));
               
                    
                }
                //finished generating the boulder
                return true;
            }
        

			//this and worldIn.isAirBlock(position.down()) will keeps moving down position until it finds ground to generate on
            position = position.down();
        }
    }
}

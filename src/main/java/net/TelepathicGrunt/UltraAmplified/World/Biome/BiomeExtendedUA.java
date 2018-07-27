package net.TelepathicGrunt.UltraAmplified.World.Biome;

import java.util.Random;

import jline.internal.Log;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeHellDecoratorUA;
import net.minecraft.block.BlockSand;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BiomeExtendedUA extends Biome{

	public BiomeExtendedUA(BiomeProperties properties) {
		super(properties);
	}
	 
	/**
     * Given x, z coordinates, we count down all the y positions starting at 255 and working our way down. When we hit a
     * non-air block, we replace it with this.topBlock (default grass, descendants may set otherwise), and then a
     * relatively shallow layer of blocks of type this.fillerBlock (default dirt). A random set of blocks below y == 5
     * (but always including y == 0) is replaced with bedrock.
     *  
     * If we don't hit non-air until somewhat below sea level, we top with gravel and fill down with stone.
     *  
     * If this.fillerBlock is red sand, we replace some of that with red sandstone.
     */
    public void generateBiomeTerrainUA(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
    	//variables
        int seaLevel = worldIn.getSeaLevel();
        IBlockState iblockstate = this.topBlock;
        IBlockState iblockstate1 = this.fillerBlock;
        int j = -1;
        int noise = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
        int chunkX = x & 15;
        int chunkZ = z & 15;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

        
        for (int height = 255; height >= 0; --height)
        {
            if (height <= rand.nextInt(5))
            {
                chunkPrimerIn.setBlockState(chunkZ, height, chunkX, BEDROCK);
            }
            else
            {
                IBlockState iblockstate2 = chunkPrimerIn.getBlockState(chunkZ, height, chunkX);

                //reset when hitting air I believe
                if (iblockstate2.getMaterial() == Material.AIR)
                {
                    j = -1;
                }
                
                //checks all four blocks to see if it should replace the top block 
                //This is needed because of End, Nether, and Ice Mountain does not use Stone when generating the terrain at all
                //This might be one of the causes of the long loading times but not sure...
                else if (iblockstate2.getBlock() == Blocks.STONE || iblockstate2.getBlock() == Blocks.NETHERRACK || iblockstate2.getBlock() == Blocks.END_STONE || iblockstate2.getBlock() == Blocks.ICE)
                {
                    if (j == -1)
                    {
                        
                        if (height < seaLevel && (iblockstate == null || iblockstate.getMaterial() == Material.AIR))
                        {
                        	//creates sea
                            if (this.getTemperature(blockpos$mutableblockpos.setPos(x, height, z)) < 0.15F)
                            {
                                iblockstate = ICE;
                            }
                            else
                            {
                                iblockstate = WATER;
                            }
                        }

                        j = noise;

                        if (height >= seaLevel - 1)
                        {
                        	//above sea or normal dry terrain
                            chunkPrimerIn.setBlockState(chunkZ, height, chunkX, iblockstate);
                        }
                        else if (height < seaLevel - 7 - noise)
                        {
                        	//bottom of sea
                            iblockstate = AIR;
                            iblockstate1 = STONE;
                            chunkPrimerIn.setBlockState(chunkZ, height, chunkX, GRAVEL);
                        }
                        else
                        {
                        	//may be place in shallow water in sea
                            chunkPrimerIn.setBlockState(chunkZ, height, chunkX, iblockstate1);
                        }
                    }
                    else if (j > 0)
                    {
                        --j;
                        chunkPrimerIn.setBlockState(chunkZ, height, chunkX, iblockstate1);

                        //turns sand into sandstone
                        if (j == 0 && iblockstate1.getBlock() == Blocks.SAND && noise > 1)
                        {
                            j = rand.nextInt(4) + Math.max(0, height - 63);
                            iblockstate1 = iblockstate1.getValue(BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND ? RED_SANDSTONE : SANDSTONE;
                        }
                    }
                }
            }
        }
    }
}

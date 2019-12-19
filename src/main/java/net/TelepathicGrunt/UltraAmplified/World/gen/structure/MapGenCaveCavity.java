package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Random;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import net.TelepathicGrunt.UltraAmplified.Config.UAConfig;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorOverworldUA;
import net.minecraft.block.BlockColored;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

public class MapGenCaveCavity extends MapGenBase
{
    protected static final IBlockState BLK_LAVA = Blocks.LAVA.getDefaultState();
    protected static final IBlockState BLK_AIR = Blocks.AIR.getDefaultState();
    protected static final IBlockState BLK_WATER = Blocks.WATER.getDefaultState();
    protected static final IBlockState BLK_MAGMA = Blocks.MAGMA.getDefaultState();
    protected static final IBlockState BLK_OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();
    protected static final IBlockState BLK_SANDSTONE = Blocks.SANDSTONE.getDefaultState();
    protected static final IBlockState BLK_RED_SANDSTONE = Blocks.RED_SANDSTONE.getDefaultState();

    private final ChunkGeneratorOverworldUA settings;
    

    public MapGenCaveCavity(ChunkGeneratorOverworldUA settingsIn) {
    	settings = settingsIn;
    }
    
    protected void addRoom(long p_180703_1_, int p_180703_3_, int p_180703_4_, ChunkPrimer p_180703_5_, double p_180703_6_, double p_180703_8_, double p_180703_10_)
    {
        this.addTunnel(p_180703_1_, p_180703_3_, p_180703_4_, p_180703_5_, p_180703_6_, p_180703_8_, p_180703_10_, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
    }

    protected void addTunnel(long seed, int chunkX, int chunkZ, ChunkPrimer chunkPrimer, double x, double y, double z, float firstUnknownFloat, float secondUnknownFloat, float thirdUnknownFloat, int firstUnknownInt, int secondUnknownInt, double unknownDouble)
    {
        double d0 = (double)(chunkX * 16 + 8);
        double d1 = (double)(chunkZ * 16 + 8);
        float f = 0.0F;
        float f1 = 0.0F;
        Random random = new Random(seed);

        if (secondUnknownInt <= 0)
        {
            int i = this.range * 16 - 16;
            secondUnknownInt = i - random.nextInt(i / 4);
        }

        boolean flag2 = false;

        if (firstUnknownInt == -1)
        {
            firstUnknownInt = secondUnknownInt / 2;
            flag2 = true;
        }

        int j = random.nextInt(secondUnknownInt / 2) + secondUnknownInt / 4;

        for (boolean flag = random.nextInt(6) == 0; firstUnknownInt < secondUnknownInt; ++firstUnknownInt)
        {
        	//the 18.0D controls size of each blob
            double d2 = 18.0D + (double)(MathHelper.sin((float)firstUnknownInt * (float)Math.PI / (float)secondUnknownInt) * firstUnknownFloat);
            double d3 = d2 * unknownDouble;
            float f2 = MathHelper.cos(thirdUnknownFloat);
            float f3 = (random.nextInt(80)+MathHelper.sin(thirdUnknownFloat)-40)/7;
            x += (double)(MathHelper.cos(secondUnknownFloat) * f2);
            y += (double)f3;
            z += (double)(MathHelper.sin(secondUnknownFloat) * f2);

            if (flag)
            {
                thirdUnknownFloat = thirdUnknownFloat * 0.92F;
            }
            else
            {
                thirdUnknownFloat = thirdUnknownFloat * 0.7F;
            }

            thirdUnknownFloat = thirdUnknownFloat + f1 * 0.1F;
            secondUnknownFloat += f * 0.1F;
            f1 = f1 * 0.9F;
            f = f * 0.75F;
            f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

            
            if (!flag2 && firstUnknownInt == j && firstUnknownFloat > 1.0F && secondUnknownInt > 0)
            {
                this.addTunnel(random.nextLong(), chunkX, chunkZ, chunkPrimer, x, y, z, random.nextFloat() * 0.5F + 0.5F, secondUnknownFloat - ((float)Math.PI / 2F), thirdUnknownFloat / 3.0F, firstUnknownInt, secondUnknownInt, 1.0D);
                this.addTunnel(random.nextLong(), chunkX, chunkZ, chunkPrimer, x, y, z, random.nextFloat() * 0.5F + 0.5F, secondUnknownFloat + ((float)Math.PI / 2F), thirdUnknownFloat / 3.0F, firstUnknownInt, secondUnknownInt, 1.0D);
                return;
            }

            if (flag2 || random.nextInt(4) != 0)
            {
                double d4 = x - d0;
                double d5 = z - d1;
                double d6 = (double)(secondUnknownInt - firstUnknownInt);
                double d7 = (double)(firstUnknownFloat + 2.0F + 16.0F);

                if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7)
                {
                    return;
                }

                if (x >= d0 - 16.0D - d2 * 2.0D && z >= d1 - 16.0D - d2 * 2.0D && x <= d0 + 16.0D + d2 * 2.0D && z <= d1 + 16.0D + d2 * 2.0D)
                {
                    int k2 = MathHelper.floor(x - d2) - chunkX * 16 - 1;
                    int k = MathHelper.floor(x + d2) - chunkX * 16 + 1;
                    int l2 = MathHelper.floor(y - d3) - 1;
                    int l = MathHelper.floor(y + d3) + 1;
                    int i3 = MathHelper.floor(z - d2) - chunkZ * 16 - 1;
                    int i1 = MathHelper.floor(z + d2) - chunkZ * 16 + 1;

                    if (k2 < 0)
                    {
                        k2 = 0;
                    }

                    if (k > 16)
                    {
                        k = 16;
                    }

                    if (l2 < 1)
                    {
                        l2 = 1;
                    }

                    if (l > 248)
                    {
                        l = 248;
                    }

                    if (i3 < 0)
                    {
                        i3 = 0;
                    }

                    if (i1 > 16)
                    {
                        i1 = 16;
                    }

                    for (int j1 = k2; j1 < k; ++j1)
                    {
                        for (int k1 = i3; k1 < i1; ++k1)
                        {
                            for (int l1 = l + 1; l1 >= l2 - 1; --l1)
                            {
                                if (l1 >= 0 && l1 < 256)
                                {
                                    IBlockState iblockstate = chunkPrimer.getBlockState(j1, l1, k1);
                                    
                                    if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1)
                                    {
                                        l1 = l2;
                                    }
                                }
                            }
                        }
                    }

                    BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                    for (int xInChunk = k2; xInChunk < k; ++xInChunk)
                    {
                        double d10 = ((double)(xInChunk + chunkX * 16) + 0.5D - x) / d2;

                        for (int zInChunk = i3; zInChunk < i1; ++zInChunk)
                        {
                            double d8 = ((double)(zInChunk + chunkZ * 16) + 0.5D - z) / d2;
                            
                            blockpos$mutableblockpos.setPos(xInChunk + chunkX * 16, 0, zInChunk + chunkZ * 16);
                            boolean flag1 = false;

                           
                            if (d10 * d10 + d8 * d8 < 1.0D)
                            {
                                for (int j2 = l; j2 > l2; --j2)
                                {
                                    double d9 = ((double)(j2 - 1) + 0.5D - y) / d3;

                                    if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D)
                                    {
                                        IBlockState iblockstate1 = chunkPrimer.getBlockState(xInChunk, j2, zInChunk);
                                        IBlockState iblockstate2 = (IBlockState)MoreObjects.firstNonNull(chunkPrimer.getBlockState(xInChunk, j2 + 1, zInChunk), BLK_AIR);

                                        if (iblockstate1.getBlock() == Blocks.GRASS || iblockstate1.getBlock() == Blocks.MYCELIUM)
                                        {
                                            flag1 = true;
                                        }

                                        if (this.canReplaceBlock(iblockstate1, iblockstate2))
                                        {
                                        	
	                                         
                                        		if (j2 - 1 < 10)
	                                            {
	                                                chunkPrimer.setBlockState(xInChunk, j2, zInChunk, BLK_LAVA);
	                                            }
	                                            else
	                                            {
	                                            	chunkPrimer.setBlockState(xInChunk, j2, zInChunk, BLK_AIR);
	
	                                                if (flag1 && chunkPrimer.getBlockState(xInChunk, j2 - 1, zInChunk).getBlock() == Blocks.DIRT)
	                                                {
	                                                    chunkPrimer.setBlockState(xInChunk, j2 - 1, zInChunk, this.world.getBiome(blockpos$mutableblockpos).topBlock.getBlock().getDefaultState());
	                                                }
	                                            }
                                        	
                                           
                                        }
                                    }
                                }
                            }
                        }
                    

                        if (flag2)
                        {
                            break;
                        }
                    }
                }
            }
        }
    }

    protected boolean canReplaceBlock(IBlockState p_175793_1_, IBlockState p_175793_2_)
    {
        if (p_175793_1_.getBlock() == Blocks.STONE)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.NETHERRACK)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.END_STONE)
        {
            return true;
        }else if (p_175793_1_.getBlock() == Blocks.ICE)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.WATER)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.LAVA)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.DIRT)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.SNOW)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.HARDENED_CLAY)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.STAINED_HARDENED_CLAY)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.GRASS)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.SANDSTONE)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.RED_SANDSTONE)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.MYCELIUM)
        {
            return true;
        }
        else if (p_175793_1_.getBlock() == Blocks.SNOW_LAYER)
        {
            return true;
        }
        else
        {
            return (p_175793_1_.getBlock() == Blocks.SAND || p_175793_1_.getBlock() == Blocks.GRAVEL);
        }
    }

    /**
     * Recursively called by generate()
     */
    protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn)
    {
        //the nextInt controls how rare the cave cavities are
        //Increase the number to increase rarity
        //Remove this and all heck breaks loose!
        if (this.rand.nextInt(700 - settings.settings.caveCavityCount) != 0)
        {
            return;
        }
        
        //how many sphere cavity this will make
        int i = this.rand.nextInt(5) + 5;

        
        for (int j = 0; j < i; ++j)
        {
        	//randomly places sphere
            double x = (double)(chunkX * 16 + this.rand.nextInt(120));
            double y = (double)this.rand.nextInt(this.rand.nextInt(15)+5);
            double z = (double)(chunkZ * 16 + this.rand.nextInt(120));
           
			int k = 1;

            this.addRoom(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, x, y, z);
             
			k += this.rand.nextInt(4);
			
            for (int l = 0; l < k; ++l)
            {
                float f = this.rand.nextFloat() * ((float)Math.PI * 2F);
                float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
                float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();

                if (this.rand.nextInt(10) == 0)
                {
                    f2 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
                }

                this.addTunnel(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, x, y, z, f2, f, f1, 0, 0, 1.0D);
            } 
        }
    }
    
    protected boolean isOceanBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ)
    {
        net.minecraft.block.Block block = data.getBlockState(x, y, z).getBlock();
        return block== Blocks.FLOWING_WATER || block == Blocks.WATER;
    }

    //Exception biomes to make sure we generate like vanilla
    private boolean isExceptionBiome(net.minecraft.world.biome.Biome biome)
    {
        if (biome == net.minecraft.init.Biomes.BEACH) return true;
        if (biome == net.minecraft.init.Biomes.DESERT) return true;
        return false;
    }

    //Determine if the block at the specified location is the top block for the biome, we take into account
    //Vanilla bugs to make sure that we generate the map the same way vanilla does.
    private boolean isTopBlock(ChunkPrimer data, int x, int y, int z, int chunkX, int chunkZ)
    {
        net.minecraft.world.biome.Biome biome = world.getBiome(new BlockPos(x + chunkX * 16, 0, z + chunkZ * 16));
        IBlockState state = data.getBlockState(x, y, z);
        return (isExceptionBiome(biome) ? state.getBlock() == Blocks.GRASS : state.getBlock() == biome.topBlock);
    }
}
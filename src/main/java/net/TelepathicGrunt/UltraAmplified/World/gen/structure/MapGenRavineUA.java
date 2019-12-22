package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorOverworldUA;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.MapGenBase;

public class MapGenRavineUA extends MapGenBase
{
    protected static final IBlockState FLOWING_LAVA = Blocks.FLOWING_LAVA.getDefaultState();
    protected static final IBlockState MAGMA = Blocks.MAGMA.getDefaultState();
    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
    protected static final IBlockState WATER = Blocks.WATER.getDefaultState();
    protected static final IBlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();
    private final float[] rs = new float[1024];
    
    private final ChunkGeneratorOverworldUA settings;
    
    public MapGenRavineUA(ChunkGeneratorOverworldUA settingsIn) {
    	settings = settingsIn;
    }

    protected void addTunnel(long p_180707_1_, int p_180707_3_, int p_180707_4_, ChunkPrimer chunkPrimer, double x, double y, double z, float p_180707_12_, float p_180707_13_, float p_180707_14_, int p_180707_15_, int p_180707_16_, double mightAffectHeight)
    {
        Random random = new Random(p_180707_1_);
        double d0 = (double)(p_180707_3_ * 16 + 8);
        double d1 = (double)(p_180707_4_ * 16 + 8);
        float f = 0.0F;
        float f1 = 0.0F;

        if (p_180707_16_ <= 0)
        {
            int i = this.range * 16 - 16;
            p_180707_16_ = i - random.nextInt(i / 4);
        }

        boolean flag1 = false;

        if (p_180707_15_ == -1)
        {
            p_180707_15_ = p_180707_16_ / 2;
            flag1 = true;
        }

        float f2 = 1.0F;

        for (int j = 0; j < 256; ++j)
        {
            if (j == 0 || random.nextInt(3) == 0)
            {
                f2 = 1.0F + random.nextFloat() * random.nextFloat();
            }

            this.rs[j] = f2 * f2;
        }

        for (; p_180707_15_ < p_180707_16_; ++p_180707_15_)
        {
            double d9 = 1.5D + (double)(MathHelper.sin((float)p_180707_15_ * (float)Math.PI / (float)p_180707_16_) * p_180707_12_);
           
            //has something to do with tallness
            double d2 = d9 * mightAffectHeight;
           
            d9 = d9 * ((double)random.nextFloat() * 0.25D + 0.75D);
           
            //has something to do with tallness
            d2 = d2 * ((double)random.nextFloat() * 0.25D + 0.75D);
            
            float f3 = MathHelper.cos(p_180707_14_);
            float f4 = MathHelper.sin(p_180707_14_);
            x += (double)(MathHelper.cos(p_180707_13_) * f3);
            y += (double)f4;
            z += (double)(MathHelper.sin(p_180707_13_) * f3);
            p_180707_14_ = p_180707_14_ * 0.7F;
            p_180707_14_ = p_180707_14_ + f1 * 0.05F;
            p_180707_13_ += f * 0.05F;
            f1 = f1 * 0.8F;
            f = f * 0.5F;
            f1 = f1 + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            f = f + (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

            if (flag1 || random.nextInt(4) != 0)
            {
                double d3 = x - d0;
                double d4 = z - d1;
                double d5 = (double)(p_180707_16_ - p_180707_15_);
                double d6 = (double)(p_180707_12_ + 2.0F + 16.0F);

                if (d3 * d3 + d4 * d4 - d5 * d5 > d6 * d6)
                {
                    return;
                }

                if (x >= d0 - 16.0D - d9 * 2.0D && z >= d1 - 16.0D - d9 * 2.0D && x <= d0 + 16.0D + d9 * 2.0D && z <= d1 + 16.0D + d9 * 2.0D)
                {
                    int k2 = MathHelper.floor(x - d9) - p_180707_3_ * 16 - 1;
                    int k = MathHelper.floor(x + d9) - p_180707_3_ * 16 + 1;
                    int l2 = MathHelper.floor(y - d2) - 1;
                    int l = MathHelper.floor(y + d2) + 1;
                    int i3 = MathHelper.floor(z - d9) - p_180707_4_ * 16 - 1;
                    int i1 = MathHelper.floor(z + d9) - p_180707_4_ * 16 + 1;

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

                                    if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1)
                                    {
                                        l1 = l2;
                                    }
                                }
                            }
                        }
                    }

                    for (int xInChunk = k2; xInChunk < k; ++xInChunk)
                    {
                        double d10 = ((double)(xInChunk + p_180707_3_ * 16) + 0.5D - x) / d9;

                        for (int zInChunk = i3; zInChunk < i1; ++zInChunk)
                        {

                            
                            double d7 = ((double)(zInChunk + p_180707_4_ * 16) + 0.5D - z) / d9;

                            if (d10 * d10 + d7 * d7 < 1.0D)
                            {
                                for (int j2 = l; j2 > l2; --j2)
                                {
                                    double d8 = ((double)(j2 - 1) + 0.5D - y) / d2;

                                    if ((d10 * d10 + d7 * d7) * (double)this.rs[j2 - 1] + d8 * d8 / 6.0D < 1.0D)
                                    {
                                        IBlockState iblockstate1 = chunkPrimer.getBlockState(xInChunk, j2, zInChunk);

                                        if (iblockstate1.getBlock() == Blocks.STONE || iblockstate1.getBlock() == Blocks.DIRT || iblockstate1.getBlock() == Blocks.NETHERRACK || iblockstate1.getBlock() == Blocks.END_STONE || iblockstate1.getBlock() == Blocks.ICE || iblockstate1.getBlock() == Blocks.WATER || iblockstate1.getBlock() == Blocks.LAVA || iblockstate1.getBlock() == Blocks.SNOW )
                                        {
                                        	
                                        		if (j2 - 1 < 10)
                                                {
                                                    chunkPrimer.setBlockState(xInChunk, j2, zInChunk, FLOWING_LAVA);
                                                }
                                                else
                                                {
                                                	chunkPrimer.setBlockState(xInChunk, j2, zInChunk, AIR);
                                                }
                                            
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (flag1)
                    {
                        break;
                    }
                
                }
            }
        }
    }

    /**
     * Recursively called by generate()
     */
    protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn)
    {
        if (this.rand.nextInt(8 - settings.settings.ravineCount) != 0)
        {
        	return;
        }
        
        double x = (double)(chunkX * 16 + this.rand.nextInt(16));
        double y = (double)(this.rand.nextInt(this.rand.nextInt(3) + 2) + 31);
        double z = (double)(chunkZ * 16 + this.rand.nextInt(16));

        for (int j = 0; j < 1; ++j)
        {
            float f = this.rand.nextFloat() * ((float)Math.PI * 2F);
            float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
            float f2 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
            this.addTunnel(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, x, y, z, f2, f, f1, 0, 0, this.rand.nextDouble()*1+2.5D);
        }
    
    }
}

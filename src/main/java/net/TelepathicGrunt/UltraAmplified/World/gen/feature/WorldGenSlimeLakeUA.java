package net.telepathicgrunt.ultraamplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class WorldGenSlimeLakeUA {
	
	 private final Block slimeBlock = Blocks.SLIME_BLOCK;

    public WorldGenSlimeLakeUA()
    {
    }

    //generates a slime lake very similar to a Lake or Lava Lake but the edges are also made of Slime instead of stone which makes the slime lake slightly bigger
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        for (position = position.add(-8, 0, -8); position.getY() > 5 && worldIn.isAirBlock(position); position = position.down())
        {
            ;
        }

        if (position.getY() <= 4)
        {
            return false;
        }
        else
        {
            position = position.down(4);
            boolean[] aboolean = new boolean[2048];
            int i = rand.nextInt(4) + 4;

            for (int j = 0; j < i; ++j)
            {
                double d0 = rand.nextDouble() * 6.0D + 3.0D;
                double d1 = rand.nextDouble() * 4.0D + 2.0D;
                double d2 = rand.nextDouble() * 6.0D + 3.0D;
                double d3 = rand.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
                double d4 = rand.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
                double d5 = rand.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

                for (int x = 1; x < 15; ++x)
                {
                    for (int z = 1; z < 15; ++z)
                    {
                        for (int y = 1; y < 7; ++y)
                        {
                            double d6 = ((double)x - d3) / (d0 / 2.0D);
                            double d7 = ((double)y - d4) / (d1 / 2.0D);
                            double d8 = ((double)z - d5) / (d2 / 2.0D);
                            double d9 = d6 * d6 + d7 * d7 + d8 * d8;

                            if (d9 < 1.0D)
                            {
                                aboolean[(x * 16 + z) * 8 + y] = true;
                            }
                        }
                    }
                }
            }

            for (int x = 0; x < 16; ++x)
            {
                for (int z = 0; z < 16; ++z)
                {
                    for (int y = 0; y < 8; ++y)
                    {
                        boolean flag = !aboolean[(x * 16 + z) * 8 + y] && (x < 15 && aboolean[((x + 1) * 16 + z) * 8 + y] || x > 0 && aboolean[((x - 1) * 16 + z) * 8 + y] || z < 15 && aboolean[(x * 16 + z + 1) * 8 + y] || z > 0 && aboolean[(x * 16 + (z - 1)) * 8 + y] || y < 7 && aboolean[(x * 16 + z) * 8 + y + 1] || y > 0 && aboolean[(x * 16 + z) * 8 + (y - 1)]);

                        if (flag)
                        {
                            Material material = worldIn.getBlockState(position.add(x, y, z)).getMaterial();

                            if (y >= 4 && material.isLiquid())
                            {
                                return false;
                            }

                            if (y < 4 && !material.isSolid() && worldIn.getBlockState(position.add(x, y, z)).getBlock() != this.slimeBlock)
                            {
                                return false;
                            }
                        }
                    }
                }
            }

            for (int x = 0; x < 16; ++x)
            {
                for (int z = 0; z < 16; ++z)
                {
                    for (int y = 0; y < 8; ++y)
                    {
                        if (aboolean[(x * 16 + z) * 8 + y])
                        {
                            worldIn.setBlockState(position.add(x, y, z), y >= 4 ? Blocks.AIR.getDefaultState() : this.slimeBlock.getDefaultState(), 2);
                        }
                    }
                }
            }

            for (int x = 0; x < 16; ++x)
            {
                for (int z = 0; z < 16; ++z)
                {
                    for (int y = 4; y < 8; ++y)
                    {
                        if (aboolean[(x * 16 + z) * 8 + y])
                        {
                            BlockPos blockpos = position.add(x, y - 1, z);

                            if (worldIn.getBlockState(blockpos).getBlock() == Blocks.DIRT && worldIn.getLightFor(EnumSkyBlock.SKY, position.add(x, y, z)) > 0)
                            {
                                Biome biome = worldIn.getBiome(blockpos);

                                if (biome.topBlock.getBlock() == Blocks.MYCELIUM)
                                {
                                    worldIn.setBlockState(blockpos, Blocks.MYCELIUM.getDefaultState(), 2);
                                }
                                else
                                {
                                    worldIn.setBlockState(blockpos, Blocks.GRASS.getDefaultState(), 2);
                                }
                            }
                        }
                    }
                }
            }
            
            for (int x = 0; x < 16; ++x)
            {
                for (int z = 0; z < 16; ++z)
                {
                    for (int y = 0; y < 8; ++y)
                    {
                        boolean flag1 = !aboolean[(x * 16 + z) * 8 + y] && (x < 15 && aboolean[((x + 1) * 16 + z) * 8 + y] || x > 0 && aboolean[((x - 1) * 16 + z) * 8 + y] || z < 15 && aboolean[(x * 16 + z + 1) * 8 + y] || z > 0 && aboolean[(x * 16 + (z - 1)) * 8 + y] || y < 7 && aboolean[(x * 16 + z) * 8 + y + 1] || y > 0 && aboolean[(x * 16 + z) * 8 + (y - 1)]);

                        if (flag1 && (y < 4 || rand.nextInt(3) != 0) && (worldIn.getBlockState(position.add(x, y, z)).getMaterial().isSolid() || worldIn.getBlockState(position.add(x, y, z)).getMaterial().isLiquid()))
                        {
                            worldIn.setBlockState(position.add(x, y, z), Blocks.SLIME_BLOCK.getDefaultState(), 2);
                        }
                    }
                }
            }

            return true;
        }
    }
}

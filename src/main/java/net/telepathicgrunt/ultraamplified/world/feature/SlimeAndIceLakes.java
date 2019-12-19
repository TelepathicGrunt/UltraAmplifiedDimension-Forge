package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LakesConfig;

public class SlimeAndIceLakes extends Feature<LakesConfig> {
	   public SlimeAndIceLakes(Function<Dynamic<?>, ? extends LakesConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState(); 

	   public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos pos, LakesConfig configBlock) {
	     
         pos = pos.down(4);
         boolean[] aboolean = new boolean[2048];
         int i = random.nextInt(4) + 4;

         for(int j = 0; j < i; ++j) {
            double d0 = random.nextDouble() * 6.0D + 3.0D;
            double d1 = random.nextDouble() * 4.0D + 2.0D;
            double d2 = random.nextDouble() * 6.0D + 3.0D;
            double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
            double d4 = random.nextDouble() * (8.0D - d1 - 4.0D) + 2.0D + d1 / 2.0D;
            double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

            for(int l = 1; l < 15; ++l) {
               for(int i1 = 1; i1 < 15; ++i1) {
                  for(int j1 = 1; j1 < 7; ++j1) {
                     double d6 = ((double)l - d3) / (d0 / 2.0D);
                     double d7 = ((double)j1 - d4) / (d1 / 2.0D);
                     double d8 = ((double)i1 - d5) / (d2 / 2.0D);
                     double d9 = d6 * d6 + d7 * d7 + d8 * d8;
                     if (d9 < 1.0D) {
                        aboolean[(l * 16 + i1) * 8 + j1] = true;
                     }
                  }
               }
            }
         }

         for(int x = 0; x < 16; ++x) {
            for(int z = 0; z < 16; ++z) {
               for(int y = 0; y < 8; ++y) {
                  boolean flag = !aboolean[(x * 16 + z) * 8 + y] && (x < 15 && aboolean[((x + 1) * 16 + z) * 8 + y] || x > 0 && aboolean[((x - 1) * 16 + z) * 8 + y] || z < 15 && aboolean[(x * 16 + z + 1) * 8 + y] || z > 0 && aboolean[(x * 16 + (z - 1)) * 8 + y] || y < 7 && aboolean[(x * 16 + z) * 8 + y + 1] || y > 0 && aboolean[(x * 16 + z) * 8 + (y - 1)]);
                  if (flag) {
                     Material material = worldIn.getBlockState(pos.add(x-8, y, z-8)).getMaterial();
                     if (y >= 4 && material.isLiquid()) {
                        return false;
                     }

                     if (y < 4 && !material.isSolid() && worldIn.getBlockState(pos.add(x-8, y, z-8)) != configBlock.state) {
                        return false;
                     }
                  }
               }
            }
         }

         for(int x = 0; x < 16; ++x) {
            for(int z = 0; z < 16; ++z) {
               for(int y = 0; y < 8; ++y) {
                  if (aboolean[(x * 16 + z) * 8 + y]) {
                     worldIn.setBlockState(pos.add(x-8, y, z-8), y >= 4 ? CAVE_AIR : configBlock.state, 2);
                  }
               }
            }
         }
         
        for(int x = 0; x < 16; ++x) {
           for(int z = 0; z < 16; ++z) {
              for(int y = 0; y < 8; ++y) {
                 boolean flag1 = !aboolean[(x * 16 + z) * 8 + y] && (x < 15 && aboolean[((x + 1) * 16 + z) * 8 + y] || x > 0 && aboolean[((x - 1) * 16 + z) * 8 + y] || z < 15 && aboolean[(x * 16 + z + 1) * 8 + y] || z > 0 && aboolean[(x * 16 + (z - 1)) * 8 + y] || y < 7 && aboolean[(x * 16 + z) * 8 + y + 1] || y > 0 && aboolean[(x * 16 + z) * 8 + (y - 1)]);
                 
                 Material blockMaterial = worldIn.getBlockState(pos.add(x-8, y, z-8)).getMaterial();
                 if (flag1 && (y < 4 || random.nextInt(2) != 0) && blockMaterial.isSolid() && blockMaterial != Material.LEAVES) {
                    worldIn.setBlockState(pos.add(x-8, y, z-8), configBlock.state, 2);
                 }
              }
           }
        }
        
         return true;
	      
	   }
	}
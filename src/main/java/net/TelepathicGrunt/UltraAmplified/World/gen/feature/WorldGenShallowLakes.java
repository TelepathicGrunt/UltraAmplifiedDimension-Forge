package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.LakesConfig;

public class WorldGenShallowLakes extends Feature<LakesConfig> {
	   public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkSettings, Random random, BlockPos pos, LakesConfig configBlock) {
	     
		  pos = pos.down(2);
         boolean[] aboolean = new boolean[2048];
         int i = random.nextInt(4) + 4;

         for(int j = 0; j < i; ++j) {
            double d0 = random.nextDouble() * 6.0D + 3.0D;
            double d2 = random.nextDouble() * 6.0D + 3.0D;
            double d3 = random.nextDouble() * (16.0D - d0 - 2.0D) + 1.0D + d0 / 2.0D;
            double d5 = random.nextDouble() * (16.0D - d2 - 2.0D) + 1.0D + d2 / 2.0D;

            for(int x = 1; x < 15; ++x) {
               for(int z = 1; z < 15; ++z) {
                   for(int y = 0; y < 5; ++y) {
                     double d6 = ((double)x - d3) / (d0 / 2.0D);
                     double d8 = ((double)z - d5) / (d2 / 2.0D);
                     double d9 = d6 * d6 + d8 * d8;
                     if (d9 < 1.4D) 
                     {
                        aboolean[(x * 16 + z) * 8 + y] = true;
                     }
                   }
               }
            }
         }
         

         for(int x = 0; x < 16; ++x) {
            for(int z = 0; z < 16; ++z) {
            	int y = 5;
            	
            	Material material = worldIn.getBlockState(pos.add(x, y, z)).getMaterial();
            	
            	while(!material.isSolid() &&
            			material != Material.WATER && 
                		y > 0) {
            		y--;
            		material = worldIn.getBlockState(pos.add(x, y, z)).getMaterial();
            	}
            	
              boolean flag = !aboolean[(x * 16 + z) * 8 + y] && (x < 15 && aboolean[((x + 1) * 16 + z) * 8 + y] || x > 0 && aboolean[((x - 1) * 16 + z) * 8 + y] || z < 15 && aboolean[(x * 16 + z + 1) * 8 + y] || z > 0 && aboolean[(x * 16 + (z - 1)) * 8 + y] || y < 7 && aboolean[(x * 16 + z) * 8 + y + 1] || y > 0 && aboolean[(x * 16 + z) * 8 + (y - 1)]);
              if (flag) {
            	  
            	  material = worldIn.getBlockState(pos.add(x-8, y, z-8)).getMaterial();
            	  
                 if (!material.isSolid() && 
                	  material != Material.WATER && 
                	  worldIn.getBlockState(pos.add(x-8, y, z-8)).getBlock() != configBlock.field_202438_a) {
                    return false;
                 }
              }
            }
         }

         for(int x = 0; x < 16; ++x) {
            for(int z = 0; z < 16; ++z) {
            	int y = 5;

            	Material material = worldIn.getBlockState(pos.add(x, y, z)).getMaterial();
            	
            	while(!material.isSolid() &&
            		   material != Material.WATER && 
                	   y > 0) 
            	{
            		y--;
            		material = worldIn.getBlockState(pos.add(x, y, z)).getMaterial();
            	}
            	
	            boolean notContainedFlag = false;
	            
	            
	            //must be solid all around even diagonally
	            for(int x2 = -1; x2 < 2; x2++) {
	            	for(int z2 = -1; z2 < 2; z2++) {
	                	
	            		material = worldIn.getBlockState(pos.add(x, y, z).west(x2).north(z2)).getMaterial();
	            		
	            		if(!material.isSolid() &&
	                        material != Material.WATER ) 
	                   	{
	                   		notContainedFlag = true;
	                   	}
	            	}
	            }
	            
	            material = worldIn.getBlockState(pos.add(x, y, z).up()).getMaterial();
	            if(material.isSolid() ||
	               material == Material.WATER) 
	        	{
	        		notContainedFlag = true;
	        	}
	            
	            material = worldIn.getBlockState(pos.add(x, y, z).down()).getMaterial();
	            if(!material.isSolid() &&
	          		material != Material.WATER ) 
	           	{
	           		notContainedFlag = true;
	           	}
            
            
            
	        //Adjacent blocks must be solid    
	        /*
            for (EnumFacing face : EnumFacing.values()) {

            	material = worldIn.getBlockState(pos.add(x, y, z).offset(face)).getMaterial();
            	
            	if(face == EnumFacing.UP)
            	{
	            	if(material.isSolid() ||
	                   material == Material.WATER) 
	            	{
	            		notContainedFlag = true;
	            	}
            	}
            	else if(!material.isSolid() &&
               		 material != Material.WATER ) 
            	{
            		notContainedFlag = true;
            	}
            }*/

              if (aboolean[(x * 16 + z) * 8 + y] &&
            	 !notContainedFlag )
              {
                 worldIn.setBlockState(pos.add(x, y, z), configBlock.field_202438_a.getDefaultState(), 2);
              }
            }
         }
         
         return true;
	      
	   }
	}
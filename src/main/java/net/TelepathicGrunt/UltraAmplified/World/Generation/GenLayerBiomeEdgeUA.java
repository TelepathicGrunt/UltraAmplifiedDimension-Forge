package net.TelepathicGrunt.UltraAmplified.World.Generation;

import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum GenLayerBiomeEdgeUA implements ICastleTransformer {
	   INSTANCE;
	
	
	   public int apply(INoiseRandom context, int pos1, int pos2, int pos3, int pos4, int currentPos) {
	      int[] aint = new int[1];
	      if (!this.replaceBiomeEdge(aint, pos1, pos2, pos3, pos4, currentPos, BiomeGenHelper.WOODED_BADLANDS_PLATEAU, BiomeGenHelper.BADLANDS) 
	    	  && !this.replaceBiomeEdge(aint, pos1, pos2, pos3, pos4, currentPos, BiomeGenHelper.BADLANDS_PLATEAU, BiomeGenHelper.BADLANDS) 
	    	  && !this.replaceBiomeEdge(aint, pos1, pos2, pos3, pos4, currentPos, BiomeGenHelper.GIANT_TREE_TAIGA, BiomeGenHelper.TAIGA)) 
	      {
	         if (currentPos != BiomeGenHelper.DESERT || pos1 != BiomeGenHelper.SNOWY_TUNDRA && pos2 != BiomeGenHelper.SNOWY_TUNDRA && pos4 != BiomeGenHelper.SNOWY_TUNDRA && pos3 != BiomeGenHelper.SNOWY_TUNDRA) 
	         {
	            if (currentPos == BiomeGenHelper.SWAMP) 
	            {
	               if (pos1 == BiomeGenHelper.DESERT || pos2 == BiomeGenHelper.DESERT || pos4 == BiomeGenHelper.DESERT || pos3 == BiomeGenHelper.DESERT || 
	            	   pos1 == BiomeGenHelper.SNOWY_TAIGA || pos2 == BiomeGenHelper.SNOWY_TAIGA || pos4 == BiomeGenHelper.SNOWY_TAIGA || pos3 == BiomeGenHelper.SNOWY_TAIGA || 
	            	   pos1 == BiomeGenHelper.SNOWY_TUNDRA || pos2 == BiomeGenHelper.SNOWY_TUNDRA || pos4 == BiomeGenHelper.SNOWY_TUNDRA || pos3 == BiomeGenHelper.SNOWY_TUNDRA) 
	               {
	                  return BiomeGenHelper.PLAINS;
	               }
	            }
	            else if(currentPos == BiomeGenHelper.NETHER) 
	            {
	            	if (pos1 == BiomeGenHelper.SNOWY_TAIGA || pos2 == BiomeGenHelper.SNOWY_TAIGA || pos4 == BiomeGenHelper.SNOWY_TAIGA || pos3 == BiomeGenHelper.SNOWY_TAIGA || 
	 	            	pos1 == BiomeGenHelper.SNOWY_TUNDRA || pos2 == BiomeGenHelper.SNOWY_TUNDRA || pos4 == BiomeGenHelper.SNOWY_TUNDRA || pos3 == BiomeGenHelper.SNOWY_TUNDRA)
	               {
	                  return BiomeGenHelper.SAVANNA;
	               }
	            }
	            else if(currentPos == BiomeGenHelper.JUNGLE) {
	            	if (pos1 != BiomeGenHelper.JUNGLE || pos3 != BiomeGenHelper.JUNGLE || pos2 != BiomeGenHelper.JUNGLE || pos4 != BiomeGenHelper.JUNGLE) 
	               {
	                  return BiomeGenHelper.JUNGLE_EDGE;
	               }
	            }

	            return currentPos;
	            
	         } else 
	         {
	            return BiomeGenHelper.WOODED_MOUNTAINS;
	         }
	      } else 
	      {
	         return aint[0];
	      }
	   }

	   /**
	    * Creates a border around a biome.
	    */
	   private boolean replaceBiomeEdge(int[] p_151635_1_, int p_151635_2_, int p_151635_3_, int p_151635_4_, int p_151635_5_, int p_151635_6_, int p_151635_7_, int p_151635_8_) {
	      if (p_151635_6_ != p_151635_7_) {
	         return false;
	      } else {
	         if (LayerUtil.areBiomesSimilar(p_151635_2_, p_151635_7_) && LayerUtil.areBiomesSimilar(p_151635_3_, p_151635_7_) && LayerUtil.areBiomesSimilar(p_151635_5_, p_151635_7_) && LayerUtil.areBiomesSimilar(p_151635_4_, p_151635_7_)) {
	            p_151635_1_[0] = p_151635_6_;
	         } else {
	            p_151635_1_[0] = p_151635_8_;
	         }

	         return true;
	      }
	   }
	}
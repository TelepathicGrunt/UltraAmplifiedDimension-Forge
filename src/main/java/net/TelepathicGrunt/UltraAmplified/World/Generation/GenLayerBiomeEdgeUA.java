package net.TelepathicGrunt.UltraAmplified.World.Generation;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.gen.IContext;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum GenLayerBiomeEdgeUA implements ICastleTransformer {
	   INSTANCE;

	   private static final int DESERT = IRegistry.field_212624_m.getId(BiomeInit.DESERT);
	   private static final int MOUNTAINS = IRegistry.field_212624_m.getId(BiomeInit.MOUNTAINS);
	   private static final int WOODED_MOUNTAINS = IRegistry.field_212624_m.getId(BiomeInit.WOODED_MOUNTAINS);
	   private static final int SNOWY_TUNDRA = IRegistry.field_212624_m.getId(BiomeInit.SNOWY_TUNDRA);
	   private static final int JUNGLE = IRegistry.field_212624_m.getId(BiomeInit.JUNGLE);
	   private static final int JUNGLE_EDGE = IRegistry.field_212624_m.getId(BiomeInit.JUNGLE_EDGE);
	   private static final int BADLANDS = IRegistry.field_212624_m.getId(BiomeInit.BADLANDS);
	   private static final int BADLANDS_PLATEAU = IRegistry.field_212624_m.getId(BiomeInit.BADLANDS_PLATEAU);
	   private static final int WOODED_BADLANDS_PLATEAU = IRegistry.field_212624_m.getId(BiomeInit.WOODED_BADLANDS_PLATEAU);
	   private static final int PLAINS = IRegistry.field_212624_m.getId(BiomeInit.PLAINS);
	   private static final int GIANT_TREE_TAIGA = IRegistry.field_212624_m.getId(BiomeInit.GIANT_TREE_TAIGA);
	   private static final int SWAMP = IRegistry.field_212624_m.getId(BiomeInit.SWAMP);
	   private static final int TAIGA = IRegistry.field_212624_m.getId(BiomeInit.TAIGA);
	   private static final int SNOWY_TAIGA = IRegistry.field_212624_m.getId(BiomeInit.SNOWY_TAIGA);
	   private static final int SAVANNA = IRegistry.field_212624_m.getId(BiomeInit.SAVANNA);

	   public int apply(IContext context, int pos1, int pos2, int pos3, int pos4, int currentPos) {
	      int[] aint = new int[1];
	      if (!this.replaceBiomeEdge(aint, pos1, pos2, pos3, pos4, currentPos, WOODED_BADLANDS_PLATEAU, BADLANDS) 
	    	  && !this.replaceBiomeEdge(aint, pos1, pos2, pos3, pos4, currentPos, BADLANDS_PLATEAU, BADLANDS) 
	    	  && !this.replaceBiomeEdge(aint, pos1, pos2, pos3, pos4, currentPos, GIANT_TREE_TAIGA, TAIGA)) 
	      {
	         if (currentPos != DESERT || pos1 != SNOWY_TUNDRA && pos2 != SNOWY_TUNDRA && pos4 != SNOWY_TUNDRA && pos3 != SNOWY_TUNDRA) 
	         {
	            if (currentPos == SWAMP) 
	            {
	               if (pos1 == DESERT || pos2 == DESERT || pos4 == DESERT || pos3 == DESERT || 
	            	   pos1 == SNOWY_TAIGA || pos2 == SNOWY_TAIGA || pos4 == SNOWY_TAIGA || pos3 == SNOWY_TAIGA || 
	            	   pos1 == SNOWY_TUNDRA || pos2 == SNOWY_TUNDRA || pos4 == SNOWY_TUNDRA || pos3 == SNOWY_TUNDRA) 
	               {
	                  return PLAINS;
	               }

	               if (pos1 == JUNGLE || pos3 == JUNGLE || pos2 == JUNGLE || pos4 == JUNGLE)
	               {
	                  return JUNGLE_EDGE;
	               }
	            }
	            else if(currentPos == BADLANDS || currentPos == SAVANNA) 
	            {
	               if (pos1 == JUNGLE || pos3 == JUNGLE || pos2 == JUNGLE || pos4 == JUNGLE) 
	               {
	                  return JUNGLE_EDGE;
	               }
	            }

	            return currentPos;
	            
	         } else 
	         {
	            return WOODED_MOUNTAINS;
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
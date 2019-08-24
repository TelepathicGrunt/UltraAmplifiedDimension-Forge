package net.TelepathicGrunt.UltraAmplified.World.Generation.Layers;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.BiomeGenHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum BiomeEdgeLayerUA implements ICastleTransformer {
	   INSTANCE;
	
	
	   public int apply(INoiseRandom context, int north, int west, int south, int east, int currentBiomeID) {
	      int[] areaArray = new int[1];
	      if (!this.replaceBiomeEdge(areaArray, north, west, south, east, currentBiomeID, BiomeGenHelper.WOODED_BADLANDS_PLATEAU, BiomeGenHelper.BADLANDS)
	    	  && !this.replaceBiomeEdge(areaArray, north, west, south, east, currentBiomeID, BiomeGenHelper.BADLANDS_PLATEAU, BiomeGenHelper.BADLANDS)
	    	  && !this.replaceBiomeEdge(areaArray, north, west, south, east, currentBiomeID, BiomeGenHelper.GIANT_TREE_TAIGA, BiomeGenHelper.TAIGA))
	      {
        	if (ConfigUA.mountains && currentBiomeID == BiomeGenHelper.DESERT)
            {
               if (north == BiomeGenHelper.SNOWY_TUNDRA || west == BiomeGenHelper.SNOWY_TUNDRA || east == BiomeGenHelper.SNOWY_TUNDRA || south == BiomeGenHelper.SNOWY_TUNDRA) 
               {
                  return BiomeGenHelper.WOODED_MOUNTAINS;
               }
            }
            else if (ConfigUA.plains && currentBiomeID == BiomeGenHelper.SWAMP)
            {
               if (north == BiomeGenHelper.DESERT || west == BiomeGenHelper.DESERT || east == BiomeGenHelper.DESERT || south == BiomeGenHelper.DESERT || 
            	   north == BiomeGenHelper.SNOWY_TAIGA || west == BiomeGenHelper.SNOWY_TAIGA || east == BiomeGenHelper.SNOWY_TAIGA || south == BiomeGenHelper.SNOWY_TAIGA || 
            	   north == BiomeGenHelper.SNOWY_TUNDRA || west == BiomeGenHelper.SNOWY_TUNDRA || east == BiomeGenHelper.SNOWY_TUNDRA || south == BiomeGenHelper.SNOWY_TUNDRA) 
               {
                  return BiomeGenHelper.PLAINS;
               }
            }
            else if(ConfigUA.savanna && currentBiomeID == BiomeGenHelper.NETHER)
            {
               if ((north != BiomeGenHelper.NETHER && north != BiomeGenHelper.SAVANNA) || 
            	   (west != BiomeGenHelper.NETHER && west != BiomeGenHelper.SAVANNA) || 
        		   (east != BiomeGenHelper.NETHER && east != BiomeGenHelper.SAVANNA) || 
        		   (south != BiomeGenHelper.NETHER && south != BiomeGenHelper.SAVANNA))
               {
                  return BiomeGenHelper.SAVANNA;
               }
            }
            else if(currentBiomeID == BiomeGenHelper.END) 
            {
               if ((north != BiomeGenHelper.END && north != BiomeGenHelper.BARREN_END_FIELD) || 
            	   (west != BiomeGenHelper.END && west != BiomeGenHelper.BARREN_END_FIELD) || 
        		   (east != BiomeGenHelper.END && east != BiomeGenHelper.BARREN_END_FIELD) || 
        		   (south != BiomeGenHelper.END && south != BiomeGenHelper.BARREN_END_FIELD))
               {
                  return BiomeGenHelper.BARREN_END_FIELD;
               }
            }
            else if(currentBiomeID == BiomeGenHelper.JUNGLE) {
               if ((north != BiomeGenHelper.JUNGLE && north != BiomeGenHelper.BAMBOO_JUNGLE) || 
            	    (west != BiomeGenHelper.JUNGLE && west != BiomeGenHelper.BAMBOO_JUNGLE) || 
         		    (east != BiomeGenHelper.JUNGLE && east != BiomeGenHelper.BAMBOO_JUNGLE) || 
         		    (south != BiomeGenHelper.JUNGLE && south != BiomeGenHelper.BAMBOO_JUNGLE))
               {
                  return BiomeGenHelper.JUNGLE_EDGE;
               }
            }
			else if(ConfigUA.coldBeach && BiomeGenHelper.BiomeRegistry.getValue(currentBiomeID).getCategory() == Biome.Category.ICY) {
				if (	BiomeGenHelper.isOcean(north) ||
						BiomeGenHelper.isOcean(west) ||
						BiomeGenHelper.isOcean(east) ||
						BiomeGenHelper.isOcean(south))
				{
					return BiomeGenHelper.SNOWY_BEACH;
				}
			}
			else if(ConfigUA.stoneBeach && BiomeGenHelper.BiomeRegistry.getValue(currentBiomeID).getCategory() == Biome.Category.EXTREME_HILLS) {
				if (	BiomeGenHelper.isOcean(north) ||
						BiomeGenHelper.isOcean(west) ||
						BiomeGenHelper.isOcean(east) ||
						BiomeGenHelper.isOcean(south))
				{
					return BiomeGenHelper.STONE_BEACH;
				}
			}

            return currentBiomeID;
	      } 
	      else 
	      {
	         return areaArray[0];
	      }
	   }

	   /**
	    * Creates a border around a biome.
	    */
	   private boolean replaceBiomeEdge(int[] aint, int north, int west, int south, int east, int currentBiomeID, int primaryBiome, int secondaryBiome) {
	      if (currentBiomeID != primaryBiome) {
	         return false;
	      } else {
	         if (BiomeGenHelper.areUABiomesSimilar(north, primaryBiome) && BiomeGenHelper.areUABiomesSimilar(west, primaryBiome) && BiomeGenHelper.areUABiomesSimilar(east, primaryBiome) && BiomeGenHelper.areUABiomesSimilar(south, primaryBiome)) {
	            aint[0] = currentBiomeID;
	         } else {
	            aint[0] = secondaryBiome;
	         }

	         return true;
	      }
	   }
	}
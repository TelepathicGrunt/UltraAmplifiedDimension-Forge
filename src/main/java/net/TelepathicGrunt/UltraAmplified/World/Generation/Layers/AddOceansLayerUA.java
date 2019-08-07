package net.TelepathicGrunt.UltraAmplified.World.Generation.Layers;

import java.util.ArrayList;
import java.util.List;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.BiomeGenHelper;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.ImprovedNoiseGenerator;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class AddOceansLayerUA implements IAreaTransformer0 {
	
		private static List<Integer> oceanList;
		private static float listSize; 
	
		public AddOceansLayerUA(){
			oceanList = new ArrayList<Integer>();
			
			if(ConfigUA.warmOcean) 
				oceanList.add(BiomeGenHelper.WARM_OCEAN);
			if(ConfigUA.lukewarmOcean) 
				oceanList.add(BiomeGenHelper.LUKEWARM_OCEAN);
			if(ConfigUA.ocean) 
				oceanList.add(BiomeGenHelper.OCEAN);
			if(ConfigUA.coldOcean) 
				oceanList.add(BiomeGenHelper.COLD_OCEAN);
			if(ConfigUA.frozenOcean) 
				oceanList.add(BiomeGenHelper.FROZEN_OCEAN);
			
			listSize = oceanList.size();
			
		}

	   public int apply(INoiseRandom context, int x, int z) {
	      ImprovedNoiseGenerator noisegeneratorimproved = context.getNoiseGenerator();
	      double d0 = noisegeneratorimproved.func_215456_a((double)(x) / 8.0D, (double)(z) / 8.0D, 0.0D, 0.0D, 0.0D);
	      
	      
	      //dynamic code that will try to equally space oceans depending on how many is allowed
	      if(listSize == 0) {
	    	  //no oceans allowed
	    	  return 0;
	      }else {
	    	  //converts the noise range into the equivalent int range for our list
	    	  //
	    	  //If all oceans allowed...
	    	  //less than -0.399 becomes 0 or less.
	    	  //-0.399 to -0.133 becomes 1.
	    	  //-0.133 to 0.133 becomes 2
	    	  //0.133 to 0.399 becomes 3
	    	  //greater than 0.39 becomes 4 
	    	  int index = (int) ((d0 / 0.266D)+2.5D);
	    	  
	    	  //trims the extremes to be within 0 - 4
	    	  if(index < 0) {
	    		  index = 0;
	    	  }else if(index > 4) {
	    		  index = 4;
	    	  }
	    	  
	    	  //shrinks the range of ints to the size of the list so all remaining ocean still has close to equal distribution.
	    	  index = (int) (index * (listSize/5));
	    	  int biomeID = oceanList.get(index);
	    	  
	    	  
	    	  //makes deep oceans
    	  	  double d1 = noisegeneratorimproved.func_215456_a((double)(x + 54443) / 5.0D, (double)(z + 34445) / 5.0D, 0.0D, 0.0D, 0.0D);
    		     
    		  if(Math.abs(d1%0.1D) < 0.03D) {
    			  if (biomeID == BiomeGenHelper.WARM_OCEAN) {
    				  biomeID = BiomeGenHelper.DEEP_WARM_OCEAN;
   	            }

   	            if (biomeID == BiomeGenHelper.LUKEWARM_OCEAN) {
   	            	biomeID = BiomeGenHelper.DEEP_LUKEWARM_OCEAN;
   	            }

   	            if (biomeID == BiomeGenHelper.OCEAN) {
   	            	biomeID = BiomeGenHelper.DEEP_OCEAN;
   	            }

   	            if (biomeID == BiomeGenHelper.COLD_OCEAN) {
   	            	biomeID = BiomeGenHelper.DEEP_COLD_OCEAN;
   	            }

   	            if (biomeID == BiomeGenHelper.FROZEN_OCEAN) {
   	            	biomeID = BiomeGenHelper.DEEP_FROZEN_OCEAN;
   	            }
    		  }
	    		  
	    	  return biomeID;
	      }
	      
	      
	   }
	}
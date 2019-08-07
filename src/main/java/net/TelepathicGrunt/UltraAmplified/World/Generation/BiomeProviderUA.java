package net.TelepathicGrunt.UltraAmplified.World.Generation;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.LongFunction;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.TelepathicGrunt.UltraAmplified.World.Generation.Layers.AddMushroomBiomeLayerUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.Layers.AddOceansLayerUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.Layers.AddSunflowerPlainsLayerUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.Layers.BiomeDebugLayer;
import net.TelepathicGrunt.UltraAmplified.World.Generation.Layers.BiomeEdgeLayerUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.Layers.BiomeLayerUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.Layers.HillsAndAmplifiedLayerUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.Layers.MixOceanLayerUA;
import net.TelepathicGrunt.UltraAmplified.World.WorldTypes.WorldTypeUA;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.layer.AddIslandLayer;
import net.minecraft.world.gen.layer.AddSnowLayer;
import net.minecraft.world.gen.layer.EdgeLayer;
import net.minecraft.world.gen.layer.IslandLayer;
import net.minecraft.world.gen.layer.Layer;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.RemoveTooMuchOceanLayer;
import net.minecraft.world.gen.layer.SmoothLayer;
import net.minecraft.world.gen.layer.VoroniZoomLayer;
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;

public class BiomeProviderUA extends BiomeProvider{

private final Layer genBiomes;
/** A GenLayer containing a factory to generate biome arrays for {@llink #getBiomes(int, int, int, int, boolean)} */
private final Layer biomeFactoryLayer;
private final Biome[] biomes;

public BiomeProviderUA(long seed, WorldType worldType)
{
	super();
	
    biomes = BiomeInit.getBiomeArray();
	
	//checks to make sure this biomeProvider is only running with the Ultra Amplified worldtypes
    if (!(worldType instanceof WorldTypeUA))
    {
        throw new RuntimeException("Error: WorldType is not UltraAmplified... How did you get this error?!");
    }    
   
    
    //generates the world and biome layouts
    Layer[] agenlayer = buildOverworldProcedure(seed, worldType);
    this.genBiomes = agenlayer[0];
    this.biomeFactoryLayer = agenlayer[1];
}

public BiomeProviderUA(World world)
{
    this(world.getSeed(), world.getWorldInfo().getGenerator());
}

public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> repeat(long seed, IAreaTransformer1 parent, IAreaFactory<T> p_202829_3_, int count, LongFunction<C> contextFactory) {
    IAreaFactory<T> iareafactory = p_202829_3_;

    for(int i = 0; i < count; ++i) {
       iareafactory = parent.apply(contextFactory.apply(seed + (long)i), iareafactory);
    }

    return iareafactory;
 }

public static Layer[] buildOverworldProcedure(long seed, WorldType typeIn) {
      ImmutableList<IAreaFactory<LazyArea>> immutablelist = buildOverworldProcedure(typeIn, (p_215737_2_) -> {
          return new LazyAreaLayerContext(25, seed, p_215737_2_);
       });
      Layer genlayer = new Layer(immutablelist.get(0));
      Layer genlayer1 = new Layer(immutablelist.get(1));
      Layer genlayer2 = new Layer(immutablelist.get(2));
      return new Layer[]{genlayer, genlayer1, genlayer2};
   }

public static <T extends IArea, C extends IExtendedNoiseRandom<T>> ImmutableList<IAreaFactory<T>> buildOverworldProcedure(WorldType worldTypeIn, LongFunction<C> contextFactory) {
	   
     int biomeSize = ConfigUA.biomeSize;
	 
	 IAreaFactory<T> iareafactory = IslandLayer.INSTANCE.apply(contextFactory.apply(1L));
      iareafactory = ZoomLayer.FUZZY.apply(contextFactory.apply(2000L), iareafactory);
      iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(1L), iareafactory);
      iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2001L), iareafactory);
      iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
      iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(50L), iareafactory);
      iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(70L), iareafactory);
      iareafactory = RemoveTooMuchOceanLayer.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
      IAreaFactory<T> iareafactory1 = new AddOceansLayerUA().apply(contextFactory.apply(2L));
      iareafactory1 = LayerUtil.repeat(2001L, ZoomLayer.NORMAL, iareafactory1, (int)(biomeSize*1.4), contextFactory);
      iareafactory = AddSnowLayer.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
      iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(3L), iareafactory);
      iareafactory = EdgeLayer.CoolWarm.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
      iareafactory = EdgeLayer.HeatIce.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
      iareafactory = EdgeLayer.Special.INSTANCE.apply(contextFactory.apply(3L), iareafactory);
      iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2002L), iareafactory);
      iareafactory = ZoomLayer.NORMAL.apply(contextFactory.apply(2003L), iareafactory);
      iareafactory = AddIslandLayer.INSTANCE.apply(contextFactory.apply(4L), iareafactory);
      iareafactory = AddMushroomBiomeLayerUA.INSTANCE.apply(contextFactory.apply(5L), iareafactory);
      iareafactory = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, iareafactory, 0, contextFactory);
      

      IAreaFactory<T> lvt_7_1_ = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, iareafactory, 0, contextFactory);
     // lvt_7_1_ = GenLayerRiverInit.INSTANCE.apply(contextFactory.apply(100L), lvt_7_1_);
      
      //generates the main biome layout
      //IAreaFactory<T>lvt_8_1_ = (new BiomeDebugLayer(worldTypeIn, null).apply(contextFactory.apply(200L), iareafactory));
      IAreaFactory<T>lvt_8_1_ = (new BiomeLayerUA().apply(contextFactory.apply(200L), iareafactory));
      
      
      lvt_8_1_ = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, lvt_8_1_, 2, contextFactory);
      
      //creates biomes that border incompatible biomes
      lvt_8_1_ = BiomeEdgeLayerUA.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_);
      
      IAreaFactory<T> lvt_9_1_ = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, lvt_7_1_, 2, contextFactory);
      
      //generates the hills and Amplified variants/patches of biomes
      lvt_8_1_ = HillsAndAmplifiedLayerUA.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_, lvt_9_1_);
      lvt_8_1_ = AddSunflowerPlainsLayerUA.INSTANCE.apply(contextFactory.apply(1001L), lvt_8_1_);
	  

      for(int k = 0; k < biomeSize; ++k) {
         lvt_8_1_ = ZoomLayer.NORMAL.apply(contextFactory.apply((long)(1000 + k)), lvt_8_1_);
      }

      lvt_8_1_ = SmoothLayer.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_);
      lvt_8_1_ = MixOceanLayerUA.INSTANCE.apply(contextFactory.apply(100L), lvt_8_1_, iareafactory1);
      
      IAreaFactory<T> iareafactory5 = VoroniZoomLayer.INSTANCE.apply(contextFactory.apply(10L), lvt_8_1_);
      return ImmutableList.of(lvt_8_1_, iareafactory5, lvt_8_1_);
   }

	/**
	 * Gets the biome from the provided coordinates
	 */
	public Biome getBiome(int x, int y) {
	   return this.biomeFactoryLayer.func_215738_a(x, y);
	}
	
	public Biome func_222366_b(int p_222366_1_, int p_222366_2_) {
	   return this.genBiomes.func_215738_a(p_222366_1_, p_222366_2_);
	}
	
	public Biome[] getBiomes(int x, int z, int width, int length, boolean cacheFlag) {
	   return this.biomeFactoryLayer.generateBiomes(x, z, width, length);
	}
	
	public Set<Biome> getBiomesInSquare(int centerX, int centerZ, int sideLength) {
	   int i = centerX - sideLength >> 2;
	   int j = centerZ - sideLength >> 2;
	   int k = centerX + sideLength >> 2;
	   int l = centerZ + sideLength >> 2;
	   int i1 = k - i + 1;
	   int j1 = l - j + 1;
	   Set<Biome> set = Sets.newHashSet();
	   Collections.addAll(set, this.genBiomes.generateBiomes(i, j, i1, j1));
	   return set;
	}
	
	@Nullable
	public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
	   int i = x - range >> 2;
	   int j = z - range >> 2;
	   int k = x + range >> 2;
	   int l = z + range >> 2;
	   int i1 = k - i + 1;
	   int j1 = l - j + 1;
	   Biome[] abiome = this.genBiomes.generateBiomes(i, j, i1, j1);
	   BlockPos blockpos = null;
	   int k1 = 0;
	
	   for(int l1 = 0; l1 < i1 * j1; ++l1) {
	      int i2 = i + l1 % i1 << 2;
	      int j2 = j + l1 / i1 << 2;
	      if (biomes.contains(abiome[l1])) {
	         if (blockpos == null || random.nextInt(k1 + 1) == 0) {
	            blockpos = new BlockPos(i2, 0, j2);
	         }
	
	         ++k1;
	      }
	   }
	
	   return blockpos;
	}
	
	public boolean hasStructure(Structure<?> structureIn) {
	   return this.hasStructureCache.computeIfAbsent(structureIn, (p_205006_1_) -> {
	      for(Biome biome : this.biomes) {
	         if (biome.hasStructure(p_205006_1_)) {
	            return true;
	         }
	      }
	
	      return false;
	   });
	}
	
	public Set<BlockState> getSurfaceBlocks() {
	   if (this.topBlocksCache.isEmpty()) {
	      for(Biome biome : this.biomes) {
	         this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
	      }
	   }
	
	   return this.topBlocksCache;
	}
}

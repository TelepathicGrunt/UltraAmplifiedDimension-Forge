package net.TelepathicGrunt.UltraAmplified.World.Generation;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.LongFunction;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

import net.TelepathicGrunt.UltraAmplified.Config.Config;
import net.TelepathicGrunt.UltraAmplified.World.WorldTypes.WorldTypeUA;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeCache;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.IContextExtended;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.area.LazyArea;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerAddIsland;
import net.minecraft.world.gen.layer.GenLayerAddMushroomIsland;
import net.minecraft.world.gen.layer.GenLayerAddSnow;
import net.minecraft.world.gen.layer.GenLayerDeepOcean;
import net.minecraft.world.gen.layer.GenLayerEdge;
import net.minecraft.world.gen.layer.GenLayerIsland;
import net.minecraft.world.gen.layer.GenLayerMixOceans;
import net.minecraft.world.gen.layer.GenLayerRemoveTooMuchOcean;
import net.minecraft.world.gen.layer.GenLayerRiverInit;
import net.minecraft.world.gen.layer.GenLayerSmooth;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.gen.layer.LayerUtil;
import net.minecraft.world.gen.layer.OceanLayer;

public class BiomeProviderUA extends BiomeProvider{

private final BiomeCache cache = new BiomeCache(this);
private final GenLayer genBiomes;
/** A GenLayer containing a factory to generate biome arrays for {@llink #getBiomes(int, int, int, int, boolean)} */
private final GenLayer biomeFactoryLayer;
private final Biome[] biomes = new Biome[]{Biomes.OCEAN, Biomes.PLAINS, Biomes.DESERT, Biomes.MOUNTAINS, Biomes.FOREST, Biomes.TAIGA, Biomes.SWAMP, Biomes.RIVER, Biomes.FROZEN_OCEAN, Biomes.FROZEN_RIVER, Biomes.SNOWY_TUNDRA, Biomes.SNOWY_MOUNTAINS, Biomes.MUSHROOM_FIELDS, Biomes.MUSHROOM_FIELD_SHORE, Biomes.BEACH, Biomes.DESERT_HILLS, Biomes.WOODED_HILLS, Biomes.TAIGA_HILLS, Biomes.MOUNTAIN_EDGE, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.JUNGLE_EDGE, Biomes.DEEP_OCEAN, Biomes.STONE_SHORE, Biomes.SNOWY_BEACH, Biomes.BIRCH_FOREST, Biomes.BIRCH_FOREST_HILLS, Biomes.DARK_FOREST, Biomes.SNOWY_TAIGA, Biomes.SNOWY_TAIGA_HILLS, Biomes.GIANT_TREE_TAIGA, Biomes.GIANT_TREE_TAIGA_HILLS, Biomes.WOODED_MOUNTAINS, Biomes.SAVANNA, Biomes.SAVANNA_PLATEAU, Biomes.BADLANDS, Biomes.WOODED_BADLANDS_PLATEAU, Biomes.BADLANDS_PLATEAU, Biomes.WARM_OCEAN, Biomes.LUKEWARM_OCEAN, Biomes.COLD_OCEAN, Biomes.DEEP_WARM_OCEAN, Biomes.DEEP_LUKEWARM_OCEAN, Biomes.DEEP_COLD_OCEAN, Biomes.DEEP_FROZEN_OCEAN, Biomes.SUNFLOWER_PLAINS, Biomes.DESERT_LAKES, Biomes.GRAVELLY_MOUNTAINS, Biomes.FLOWER_FOREST, Biomes.TAIGA_MOUNTAINS, Biomes.SWAMP_HILLS, Biomes.ICE_SPIKES, Biomes.MODIFIED_JUNGLE, Biomes.MODIFIED_JUNGLE_EDGE, Biomes.TALL_BIRCH_FOREST, Biomes.TALL_BIRCH_HILLS, Biomes.DARK_FOREST_HILLS, Biomes.SNOWY_TAIGA_MOUNTAINS, Biomes.GIANT_SPRUCE_TAIGA, Biomes.GIANT_SPRUCE_TAIGA_HILLS, Biomes.MODIFIED_GRAVELLY_MOUNTAINS, Biomes.SHATTERED_SAVANNA, Biomes.SHATTERED_SAVANNA_PLATEAU, Biomes.ERODED_BADLANDS, Biomes.MODIFIED_WOODED_BADLANDS_PLATEAU, Biomes.MODIFIED_BADLANDS_PLATEAU};

public BiomeProviderUA(long seed, WorldType worldType)
{
	super();
	
    
	
	//checks to make sure this biomeProvider is only running with the Ultra Amplified worldtypes
    if (!(worldType instanceof WorldTypeUA))
    {
        throw new RuntimeException("Error: WorldType is not UltraAmplified... How did you get this error?!");
    }    
   
    
    //generates the world and biome layouts
    GenLayer[] agenlayer = buildOverworldProcedure(seed, worldType);
    this.genBiomes = agenlayer[0];
    this.biomeFactoryLayer = agenlayer[1];
    
    
    			//not implemented yet
    //agenlayer = getModdedBiomeGenerators(worldType, seed, agenlayer);
    
    
    
    //reflections to set two fields because they are private and we need to pass in our version of agenlayer to generate properly.
    /*
    try {
    	//genBiomes
        Field genBiomeField = ObfuscationReflectionHelper.getPrivateValue(BiomeProvider.class, this, "field_76944_d");
        genBiomeField.set(this, agenlayer[0]);
    }
    catch(Exception e) {
    	UltraAmplified.Logger.warn("BiomeProviderUA error with setting genBiome: "+e.getMessage());
    }
    
    try {
    	//biomeIndexLayer
    	Field genBiomeField = ObfuscationReflectionHelper.getPrivateValue(BiomeProvider.class, this, "field_76945_e");
        genBiomeField.set(this, agenlayer[1]);
    }
    catch(Exception e) {
    	UltraAmplified.Logger.warn("BiomeProviderUA error with setting biomeIndexLayer: "+e.getMessage());
    }
    */
}

public BiomeProviderUA(World world)
{
    this(world.getSeed(), world.getWorldInfo().getTerrainType());
}


public static GenLayer[] buildOverworldProcedure(long seed, WorldType typeIn) {
      int[] aint = new int[1];
      ImmutableList<IAreaFactory<LazyArea>> immutablelist = buildOverworldProcedure(typeIn, (p_202825_3_) -> {
         ++aint[0];
         return new LazyAreaLayerContext(1, aint[0], seed, p_202825_3_);
      });
      GenLayer genlayer = new GenLayer(immutablelist.get(0));
      GenLayer genlayer1 = new GenLayer(immutablelist.get(1));
      GenLayer genlayer2 = new GenLayer(immutablelist.get(2));
      return new GenLayer[]{genlayer, genlayer1, genlayer2};
   }

 public static <T extends IArea, C extends IContextExtended<T>> ImmutableList<IAreaFactory<T>> buildOverworldProcedure(WorldType worldTypeIn, LongFunction<C> contextFactory) {
      IAreaFactory<T> iareafactory = GenLayerIsland.INSTANCE.apply(contextFactory.apply(1L));
      iareafactory = GenLayerZoom.FUZZY.apply(contextFactory.apply(2000L), iareafactory);
      iareafactory = GenLayerAddIsland.INSTANCE.apply(contextFactory.apply(1L), iareafactory);
      iareafactory = GenLayerZoom.NORMAL.apply(contextFactory.apply(2001L), iareafactory);
      iareafactory = GenLayerAddIsland.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
      iareafactory = GenLayerAddIsland.INSTANCE.apply(contextFactory.apply(50L), iareafactory);
      iareafactory = GenLayerAddIsland.INSTANCE.apply(contextFactory.apply(70L), iareafactory);
      iareafactory = GenLayerRemoveTooMuchOcean.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
      IAreaFactory<T> iareafactory1 = OceanLayer.INSTANCE.apply(contextFactory.apply(2L));
      iareafactory1 = LayerUtil.repeat(2001L, GenLayerZoom.NORMAL, iareafactory1, 6, contextFactory);
      iareafactory = GenLayerAddSnow.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
      iareafactory = GenLayerAddIsland.INSTANCE.apply(contextFactory.apply(3L), iareafactory);
      iareafactory = GenLayerEdge.CoolWarm.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
      iareafactory = GenLayerEdge.HeatIce.INSTANCE.apply(contextFactory.apply(2L), iareafactory);
      iareafactory = GenLayerEdge.Special.INSTANCE.apply(contextFactory.apply(3L), iareafactory);
      iareafactory = GenLayerZoom.NORMAL.apply(contextFactory.apply(2002L), iareafactory);
      iareafactory = GenLayerZoom.NORMAL.apply(contextFactory.apply(2003L), iareafactory);
      iareafactory = GenLayerAddIsland.INSTANCE.apply(contextFactory.apply(4L), iareafactory);
      iareafactory = GenLayerAddMushroomIsland.INSTANCE.apply(contextFactory.apply(5L), iareafactory);
      iareafactory = GenLayerDeepOcean.INSTANCE.apply(contextFactory.apply(4L), iareafactory);
      iareafactory = LayerUtil.repeat(1000L, GenLayerZoom.NORMAL, iareafactory, 0, contextFactory);
      
      int i = Config.biomeSize;
      int j = i;

      IAreaFactory<T> lvt_7_1_ = LayerUtil.repeat(1000L, GenLayerZoom.NORMAL, iareafactory, 0, contextFactory);
      lvt_7_1_ = GenLayerRiverInit.INSTANCE.apply(contextFactory.apply(100L), lvt_7_1_);
      
      //generates the main biome layout
      IAreaFactory<T>lvt_8_1_ = (new GenLayerBiomeUA().apply(contextFactory.apply(200L), iareafactory));
      //IAreaFactory<T>lvt_8_1_ = (new GenLayerBiomeDebug(worldTypeIn, null).apply(contextFactory.apply(200L), iareafactory));
      
      
      lvt_8_1_ = LayerUtil.repeat(1000L, GenLayerZoom.NORMAL, lvt_8_1_, 2, contextFactory);
      
      //creates biomes that border incompatible biomes
      lvt_8_1_ = GenLayerBiomeEdgeUA.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_);
      
      IAreaFactory<T> lvt_9_1_ = LayerUtil.repeat(1000L, GenLayerZoom.NORMAL, lvt_7_1_, 2, contextFactory);
      
      //generates the hills and Amplified variants/patches of biomes
      lvt_8_1_ = GenLayerHillsAndAmplifiedUA.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_, lvt_9_1_);
      
      lvt_7_1_ = LayerUtil.repeat(1000L, GenLayerZoom.NORMAL, lvt_7_1_, 2, contextFactory);
      lvt_7_1_ = LayerUtil.repeat(1000L, GenLayerZoom.NORMAL, lvt_7_1_, j, contextFactory);
      //lvt_7_1_ = GenLayerRiver.INSTANCE.apply(contextFactory.apply(1L), lvt_7_1_);
      lvt_7_1_ = GenLayerSmooth.INSTANCE.apply(contextFactory.apply(1000L), lvt_7_1_);
      lvt_8_1_ = GenLayerSunflowerPlains.INSTANCE.apply(contextFactory.apply(1001L), lvt_8_1_);

      for(int k = 0; k < i; ++k) {
         lvt_8_1_ = GenLayerZoom.NORMAL.apply(contextFactory.apply((long)(1000 + k)), lvt_8_1_);
         if (k == 0) {
            lvt_8_1_ = GenLayerAddIsland.INSTANCE.apply(contextFactory.apply(3L), lvt_8_1_);
         }

         if (k == 1 || i == 1) {
            //lvt_8_1_ = GenLayerShore.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_);
         }
      }

      lvt_8_1_ = GenLayerSmooth.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_);
      //lvt_8_1_ = GenLayerRiverMix.INSTANCE.apply((IContextExtended)contextFactory.apply(100L), lvt_8_1_, lvt_7_1_);
      lvt_8_1_ = GenLayerMixOceans.INSTANCE.apply(contextFactory.apply(100L), lvt_8_1_, iareafactory1);
      IAreaFactory<T> iareafactory5 = GenLayerVoronoiZoom.INSTANCE.apply(contextFactory.apply(10L), lvt_8_1_);
      return ImmutableList.of(lvt_8_1_, iareafactory5, lvt_8_1_);
   }

    @Nullable
    public Biome getBiome(BlockPos pos, @Nullable Biome defaultBiome) {
       return this.cache.getBiome(pos.getX(), pos.getZ(), defaultBiome);
    }

    public Biome[] getBiomes(int startX, int startZ, int xSize, int zSize) {
       return this.genBiomes.generateBiomes(startX, startZ, xSize, zSize, Biomes.DEFAULT);
    }

    public Biome[] getBiomes(int x, int z, int width, int length, boolean cacheFlag) {
       return cacheFlag && width == 16 && length == 16 && (x & 15) == 0 && (z & 15) == 0 ? this.cache.getCachedBiomes(x, z) : this.biomeFactoryLayer.generateBiomes(x, z, width, length, Biomes.DEFAULT);
    }

    public Set<Biome> getBiomesInSquare(int centerX, int centerZ, int sideLength) {
       int i = centerX - sideLength >> 2;
       int j = centerZ - sideLength >> 2;
       int k = centerX + sideLength >> 2;
       int l = centerZ + sideLength >> 2;
       int i1 = k - i + 1;
       int j1 = l - j + 1;
       Set<Biome> set = Sets.newHashSet();
       Collections.addAll(set, this.genBiomes.generateBiomes(i, j, i1, j1, (Biome)null));
       
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
       Biome[] abiome = this.genBiomes.generateBiomes(i, j, i1, j1, (Biome)null);
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

    public Set<IBlockState> getSurfaceBlocks() {
       if (this.topBlocksCache.isEmpty()) {
          for(Biome biome : this.biomes) {
             this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
          }
       }

       return this.topBlocksCache;
    }

    public void tick() {
       this.cache.cleanupCache();
    }
}

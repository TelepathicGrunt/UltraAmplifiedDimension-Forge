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
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
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
private final Biome[] biomes = new Biome[]
	{
		BiomeInit.PLAINS, 
		BiomeInit.DESERT, 
		BiomeInit.MOUNTAINS, 
		BiomeInit.FOREST, 
		BiomeInit.TAIGA, 
		BiomeInit.SWAMP, 
		BiomeInit.NETHER, 
		BiomeInit.END, 
		BiomeInit.SNOWY_TUNDRA, 
		BiomeInit.ICE_MOUNTAIN, 
		BiomeInit.MUSHROOM_FIELDS, 
		BiomeInit.DESERT_HILLS, 
		BiomeInit.WOODED_HILLS, 
		BiomeInit.TAIGA_HILLS, 
		BiomeInit.JUNGLE, 
		BiomeInit.JUNGLE_HILLS, 
		BiomeInit.JUNGLE_EDGE, 
		BiomeInit.STONE_SHORE, 
		BiomeInit.SNOWY_BEACH, 
		BiomeInit.BIRCH_FOREST, 
		BiomeInit.BIRCH_FOREST_HILLS, 
		BiomeInit.DARK_FOREST, 
		BiomeInit.SNOWY_TAIGA, 
		BiomeInit.SNOWY_TAIGA_HILLS, 
		BiomeInit.GIANT_TREE_TAIGA, 
		BiomeInit.GIANT_TREE_TAIGA_HILLS, 
		BiomeInit.WOODED_MOUNTAINS, 
		BiomeInit.SAVANNA, 
		BiomeInit.SAVANNA_PLATEAU, 
		BiomeInit.BADLANDS, 
		BiomeInit.WOODED_BADLANDS_PLATEAU, 
		BiomeInit.BADLANDS_PLATEAU, 
		BiomeInit.SUNFLOWER_PLAINS, 
		BiomeInit.DESERT_LAKES, 
		BiomeInit.GRAVELLY_MOUNTAINS, 
		BiomeInit.FLOWER_FOREST, 
		BiomeInit.TAIGA_MOUNTAINS, 
		BiomeInit.SWAMP_HILLS, 
		BiomeInit.ICE_SPIKES, 
		BiomeInit.MODIFIED_JUNGLE, 
		BiomeInit.MODIFIED_JUNGLE_EDGE, 
		BiomeInit.TALL_BIRCH_FOREST, 
		BiomeInit.TALL_BIRCH_FOREST_HILLS, 
		BiomeInit.DARK_FOREST_HILLS, 
		BiomeInit.SNOWY_TAIGA_MOUNTAINS, 
		BiomeInit.GIANT_SPRUCE_TAIGA, 
		BiomeInit.GIANT_SPRUCE_TAIGA_HILLS,
		BiomeInit.MODIFIED_GRAVELLY_MOUNTAINS,
		BiomeInit.SHATTERED_SAVANNA, 
		BiomeInit.SHATTERED_SAVANNA_PLATEAU, 
		BiomeInit.ERODED_BADLANDS, 
		BiomeInit.MODIFIED_WOODED_BADLANDS_PLATEAU,
		BiomeInit.MODIFIED_BADLANDS_PLATEAU
	};

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
      IAreaFactory<T>lvt_8_1_ = (new GenLayerBiomeDebug(worldTypeIn, null).apply(contextFactory.apply(200L), iareafactory));
      /*IAreaFactory<T>lvt_8_1_ = (new GenLayerBiomeUA().apply(contextFactory.apply(200L), iareafactory));
      
      
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
	  */


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

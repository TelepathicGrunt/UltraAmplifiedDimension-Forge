package net.telepathicgrunt.ultraamplified.world.generation;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.LongFunction;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

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
import net.minecraft.world.gen.layer.ZoomLayer;
import net.minecraft.world.gen.layer.traits.IAreaTransformer1;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;
import net.telepathicgrunt.ultraamplified.world.biome.UABiomes;
import net.telepathicgrunt.ultraamplified.world.generation.layers.AddOceansLayerUA;
import net.telepathicgrunt.ultraamplified.world.generation.layers.AddSunflowerPlainsLayerUA;
import net.telepathicgrunt.ultraamplified.world.generation.layers.BiomeEdgeLayerUA;
import net.telepathicgrunt.ultraamplified.world.generation.layers.BiomeLayerPickerUA;
import net.telepathicgrunt.ultraamplified.world.generation.layers.HillsAndAmplifiedLayerUA;
import net.telepathicgrunt.ultraamplified.world.generation.layers.MixOceanLayerUA;


public class UABiomeProvider extends BiomeProvider
{

	private final Layer genBiomes;
	private final Set<Biome> biomes;


	public UABiomeProvider(long seed, WorldType worldType)
	{
		super(UABiomes.getBiomeArray());
		biomes = UABiomes.getBiomeArray();

		//generates the world and biome layouts
		Layer[] agenlayer = buildOverworldProcedure(seed, worldType);
		this.genBiomes = agenlayer[0];
	}


	public UABiomeProvider(World world)
	{
		this(world.getSeed(), world.getWorldInfo().getGenerator());
	}


	public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> repeat(long seed, IAreaTransformer1 parent, IAreaFactory<T> p_202829_3_, int count, LongFunction<C> contextFactory)
	{
		IAreaFactory<T> iareafactory = p_202829_3_;

		for (int i = 0; i < count; ++i)
		{
			iareafactory = parent.apply(contextFactory.apply(seed + i), iareafactory);
		}

		return iareafactory;
	}


	public static Layer[] buildOverworldProcedure(long seed, WorldType typeIn)
	{
		ImmutableList<IAreaFactory<LazyArea>> immutablelist = buildOverworldProcedure(typeIn, (p_215737_2_) ->
		{
			return new LazyAreaLayerContext(25, seed, p_215737_2_);
		});
		Layer genlayer = new Layer(immutablelist.get(0));
		Layer genlayer1 = new Layer(immutablelist.get(1));
		Layer genlayer2 = new Layer(immutablelist.get(2));
		return new Layer[] { genlayer, genlayer1, genlayer2 };
	}


	public static <T extends IArea, C extends IExtendedNoiseRandom<T>> ImmutableList<IAreaFactory<T>> buildOverworldProcedure(WorldType worldTypeIn, LongFunction<C> contextFactory)
	{

		int biomeSize = ConfigUA.biomeSize;

		IAreaFactory<T> areaFactory1 = IslandLayer.INSTANCE.apply(contextFactory.apply(1L));
		areaFactory1 = ZoomLayer.FUZZY.apply(contextFactory.apply(2000L), areaFactory1);
		areaFactory1 = AddIslandLayer.INSTANCE.apply(contextFactory.apply(1L), areaFactory1);
		areaFactory1 = ZoomLayer.NORMAL.apply(contextFactory.apply(2001L), areaFactory1);
		areaFactory1 = AddIslandLayer.INSTANCE.apply(contextFactory.apply(2L), areaFactory1);
		areaFactory1 = AddIslandLayer.INSTANCE.apply(contextFactory.apply(50L), areaFactory1);
		areaFactory1 = AddIslandLayer.INSTANCE.apply(contextFactory.apply(70L), areaFactory1);
		areaFactory1 = RemoveTooMuchOceanLayer.INSTANCE.apply(contextFactory.apply(2L), areaFactory1);
		IAreaFactory<T> areaFactory2 = new AddOceansLayerUA().apply(contextFactory.apply(2L));
		areaFactory2 = LayerUtil.repeat(2001L, ZoomLayer.NORMAL, areaFactory2, (int) (biomeSize * 1.4), contextFactory);
		areaFactory1 = AddSnowLayer.INSTANCE.apply(contextFactory.apply(2L), areaFactory1);
		areaFactory1 = AddIslandLayer.INSTANCE.apply(contextFactory.apply(3L), areaFactory1);
		areaFactory1 = EdgeLayer.CoolWarm.INSTANCE.apply(contextFactory.apply(2L), areaFactory1);
		areaFactory1 = EdgeLayer.HeatIce.INSTANCE.apply(contextFactory.apply(2L), areaFactory1);
		areaFactory1 = EdgeLayer.Special.INSTANCE.apply(contextFactory.apply(3L), areaFactory1);
		areaFactory1 = ZoomLayer.NORMAL.apply(contextFactory.apply(2002L), areaFactory1);
		areaFactory1 = ZoomLayer.NORMAL.apply(contextFactory.apply(2003L), areaFactory1);
		areaFactory1 = AddIslandLayer.INSTANCE.apply(contextFactory.apply(4L), areaFactory1);
		areaFactory1 = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, areaFactory1, 0, contextFactory);
		IAreaFactory<T> lvt_7_1_ = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, areaFactory1, 0, contextFactory);

		//generates the main biome layout
		// IAreaFactory<T>lvt_8_1_ = new BiomeDebugLayer(worldTypeIn, null).apply(contextFactory.apply(200L), areaFactory1);
		IAreaFactory<T> lvt_8_1_ = new BiomeLayerPickerUA().apply(contextFactory.apply(200L), areaFactory1);

		lvt_8_1_ = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, lvt_8_1_, 2, contextFactory);

		//creates biomes that border incompatible biomes
		lvt_8_1_ = BiomeEdgeLayerUA.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_);

		IAreaFactory<T> lvt_9_1_ = LayerUtil.repeat(1000L, ZoomLayer.NORMAL, lvt_7_1_, 2, contextFactory);

		//generates the hills and M variants/patches of biomes
		lvt_8_1_ = HillsAndAmplifiedLayerUA.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_, lvt_9_1_);
		lvt_8_1_ = AddSunflowerPlainsLayerUA.INSTANCE.apply(contextFactory.apply(1001L), lvt_8_1_);

		for (int k = 0; k < biomeSize; ++k)
		{
			lvt_8_1_ = ZoomLayer.NORMAL.apply(contextFactory.apply(1000 + k), lvt_8_1_);
		}

		lvt_8_1_ = SmoothLayer.INSTANCE.apply(contextFactory.apply(1000L), lvt_8_1_);
		lvt_8_1_ = MixOceanLayerUA.INSTANCE.apply(contextFactory.apply(100L), lvt_8_1_, areaFactory2);
		return ImmutableList.of(lvt_8_1_, lvt_8_1_, lvt_8_1_);
	}


	@Override
	public Set<Biome> getBiomesInArea(int centerX, int centerY, int centerZ, int sideLength)
	{
		int i = centerX - sideLength >> 2;
		int j = centerY - sideLength >> 2;
		int k = centerZ - sideLength >> 2;
		int l = centerX + sideLength >> 2;
		int i1 = centerY + sideLength >> 2;
		int j1 = centerZ + sideLength >> 2;
		int k1 = l - i + 1;
		int l1 = i1 - j + 1;
		int i2 = j1 - k + 1;
		Set<Biome> set = Sets.newHashSet();

		for (int j2 = 0; j2 < i2; ++j2)
		{
			for (int k2 = 0; k2 < k1; ++k2)
			{
				for (int l2 = 0; l2 < l1; ++l2)
				{
					int i3 = i + k2;
					int j3 = j + l2;
					int k3 = k + j2;
					set.add(this.getBiomeForNoiseGen(i3, j3, k3));
				}
			}
		}
		return set;
	}


	@Nullable
	public BlockPos locateBiome(int x, int z, int range, List<Biome> biomes, Random random)
	{
		int i = x - range >> 2;
		int j = z - range >> 2;
		int k = x + range >> 2;
		int l = z + range >> 2;
		int i1 = k - i + 1;
		int j1 = l - j + 1;
		BlockPos blockpos = null;
		int k1 = 0;

		for (int l1 = 0; l1 < i1 * j1; ++l1)
		{
			int i2 = i + l1 % i1 << 2;
			int j2 = j + l1 / i1 << 2;
			if (biomes.contains(this.getBiomeForNoiseGen(i2, k1, j2)))
			{
				if (blockpos == null || random.nextInt(k1 + 1) == 0)
				{
					blockpos = new BlockPos(i2, 0, j2);
				}

				++k1;
			}
		}

		return blockpos;
	}


	@Override
	public boolean hasStructure(Structure<?> structureIn)
	{
		return this.hasStructureCache.computeIfAbsent(structureIn, (p_205006_1_) ->
		{
			for (Biome biome : this.biomes)
			{
				if (biome.hasStructure(p_205006_1_))
				{
					return true;
				}
			}

			return false;
		});
	}


	@Override
	public Set<BlockState> getSurfaceBlocks()
	{
		if (this.topBlocksCache.isEmpty())
		{
			for (Biome biome : this.biomes)
			{
				this.topBlocksCache.add(biome.getSurfaceBuilderConfig().getTop());
			}
		}

		return this.topBlocksCache;
	}


	@Override
	public Biome getBiomeForNoiseGen(int p_225526_1_, int p_225526_2_, int p_225526_3_)
	{
		return this.genBiomes.func_215738_a(p_225526_1_, p_225526_3_);
	}
}

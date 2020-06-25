package net.telepathicgrunt.ultraamplified.world.feature.carver;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.biome.UABiomes;


public class UnderwaterCaveCarver extends CaveWorldCarver
{
	protected static final Set<BlockState> CARVABLE_BLOCKS = ImmutableSet.of(Blocks.STONE.getDefaultState(), Blocks.GRANITE.getDefaultState(), Blocks.DIORITE.getDefaultState(), Blocks.ANDESITE.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState(), Blocks.PODZOL.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), Blocks.TERRACOTTA.getDefaultState(), Blocks.WHITE_TERRACOTTA.getDefaultState(), Blocks.ORANGE_TERRACOTTA.getDefaultState(),
			Blocks.MAGENTA_TERRACOTTA.getDefaultState(), Blocks.LIGHT_BLUE_TERRACOTTA.getDefaultState(), Blocks.YELLOW_TERRACOTTA.getDefaultState(), Blocks.LIME_TERRACOTTA.getDefaultState(), Blocks.PINK_TERRACOTTA.getDefaultState(), Blocks.GRAY_TERRACOTTA.getDefaultState(), Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState(), Blocks.CYAN_TERRACOTTA.getDefaultState(), Blocks.PURPLE_TERRACOTTA.getDefaultState(), Blocks.BLUE_TERRACOTTA.getDefaultState(), Blocks.BROWN_TERRACOTTA.getDefaultState(),
			Blocks.GREEN_TERRACOTTA.getDefaultState(), Blocks.RED_TERRACOTTA.getDefaultState(), Blocks.BLACK_TERRACOTTA.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), Blocks.RED_SANDSTONE.getDefaultState(), Blocks.MYCELIUM.getDefaultState(), Blocks.SNOW.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.WATER.getDefaultState(), Blocks.LAVA.getDefaultState(), Blocks.OBSIDIAN.getDefaultState(), Blocks.AIR.getDefaultState(),
			Blocks.CAVE_AIR.getDefaultState(), Blocks.PACKED_ICE.getDefaultState());

	private static final BlockState STONE = Blocks.STONE.getDefaultState();
	private static final BlockState LAVA = Blocks.LAVA.getDefaultState();
	private static final BlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
	private static final BlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();

	private static Map<Biome, BlockState> fillerBiomeMap;


	/**
	 * Have to make this map in UltraAmplified setup method since the biomes needs to be initialized first
	 */
	public static void setFillerMap()
	{
		if (fillerBiomeMap == null)
		{
			fillerBiomeMap = new HashMap<Biome, BlockState>();

			fillerBiomeMap.put(UABiomes.NETHERLAND, Blocks.NETHERRACK.getDefaultState());
			fillerBiomeMap.put(UABiomes.ICED_TERRAIN, Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(UABiomes.ICE_SPIKES, Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(UABiomes.DEEP_FROZEN_OCEAN, Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(UABiomes.FROZEN_OCEAN, Blocks.ICE.getDefaultState());
			fillerBiomeMap.put(UABiomes.BARREN_END_FIELD, Blocks.END_STONE.getDefaultState());
			fillerBiomeMap.put(UABiomes.END_FIELD, Blocks.END_STONE.getDefaultState());
		}
	}


	public UnderwaterCaveCarver(Function<Dynamic<?>, ? extends ProbabilityConfig> probabilityConfig)
	{
		super(probabilityConfig, 73);
	}


	@Override
	public boolean shouldCarve(Random random, int chunkX, int chunkZ, ProbabilityConfig config)
	{
		return random.nextFloat() <= (UltraAmplified.UAFeaturesConfig.oceanCaveSpawnrate.get()) / 100f;
	}


	@Override
	protected boolean func_222700_a(IChunk chunkIn, int chunkX, int chunkZ, int minX, int maxX, int minY, int maxY, int minZ, int maxZ)
	{
		return false;
	}


	@Override
	protected boolean func_225556_a_(IChunk chunkIn, Function<BlockPos, Biome> biomeBlockPos, BitSet carvingMask, Random random, BlockPos.Mutable MutableIn, BlockPos.Mutable p_222703_5_, BlockPos.Mutable p_222703_6_, int minHeight, int chunkX, int chunkZ, int x, int z, int maskY, int y, int atomicBoolean, AtomicBoolean p_222703_15_)
	{
		return carvingBlock(this, biomeBlockPos, chunkIn, carvingMask, random, MutableIn, minHeight, chunkX, chunkZ, x, z, maskY, y, atomicBoolean);
	}


	protected static boolean carvingBlock(WorldCarver<?> worldCarver, Function<BlockPos, Biome> biomeBlockPos, IChunk chunkIn, BitSet carvingMask, Random random, BlockPos.Mutable mutableBlockPos, int minHeight, int chunkX, int chunkZ, int xStart, int zStart, int maskY, int y, int atomicBoolean)
	{
		if (y >= minHeight)
		{
			return false;
		}
		else
		{
			mutableBlockPos.setPos(xStart, y, zStart);

			BlockState fillerBlock = fillerBiomeMap.get(biomeBlockPos.apply(mutableBlockPos));
			if (fillerBlock == null)
			{
				fillerBlock = STONE;
			}

			BlockState blockstate = chunkIn.getBlockState(mutableBlockPos);
			if (!CARVABLE_BLOCKS.contains(blockstate))
			{
				return false;
			}
			else if (y == 10)
			{
				float f = random.nextFloat();
				if (f < 0.25D)
				{
					chunkIn.setBlockState(mutableBlockPos, MAGMA, false);
					chunkIn.getBlocksToBeTicked().scheduleTick(mutableBlockPos, MAGMA.getBlock(), 0);
				}
				else
				{
					chunkIn.setBlockState(mutableBlockPos, OBSIDIAN, false);
				}

				return true;
			}
			else if (y < 10)
			{
				chunkIn.setBlockState(mutableBlockPos, LAVA, false);

				return false;
			}
			else
			{
				mutableBlockPos.setPos(xStart, y, zStart);
				chunkIn.setBlockState(mutableBlockPos, WATER.getBlockState(), false);

				return true;
			}
		}
	}
}
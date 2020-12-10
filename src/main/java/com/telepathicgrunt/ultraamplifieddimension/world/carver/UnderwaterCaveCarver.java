package com.telepathicgrunt.ultraamplifieddimension.world.carver;

import com.google.common.collect.ImmutableSet;
import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.mixin.BiomeContainerAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.IObjectIntIterable;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.carver.CaveWorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.*;
import java.util.function.Function;

import static com.telepathicgrunt.ultraamplifieddimension.utils.GeneralUtils.biomeIDString;


public class UnderwaterCaveCarver extends CaveWorldCarver
{
	protected static final Set<BlockState> CARVABLE_BLOCKS = ImmutableSet.of(Blocks.STONE.getDefaultState(), Blocks.GRANITE.getDefaultState(), Blocks.DIORITE.getDefaultState(), Blocks.ANDESITE.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState(), Blocks.PODZOL.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), Blocks.TERRACOTTA.getDefaultState(), Blocks.WHITE_TERRACOTTA.getDefaultState(), Blocks.ORANGE_TERRACOTTA.getDefaultState(),
			Blocks.MAGENTA_TERRACOTTA.getDefaultState(), Blocks.LIGHT_BLUE_TERRACOTTA.getDefaultState(), Blocks.YELLOW_TERRACOTTA.getDefaultState(), Blocks.LIME_TERRACOTTA.getDefaultState(), Blocks.PINK_TERRACOTTA.getDefaultState(), Blocks.GRAY_TERRACOTTA.getDefaultState(), Blocks.LIGHT_GRAY_TERRACOTTA.getDefaultState(), Blocks.CYAN_TERRACOTTA.getDefaultState(), Blocks.PURPLE_TERRACOTTA.getDefaultState(), Blocks.BLUE_TERRACOTTA.getDefaultState(), Blocks.BROWN_TERRACOTTA.getDefaultState(),
			Blocks.GREEN_TERRACOTTA.getDefaultState(), Blocks.RED_TERRACOTTA.getDefaultState(), Blocks.BLACK_TERRACOTTA.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), Blocks.RED_SANDSTONE.getDefaultState(), Blocks.MYCELIUM.getDefaultState(), Blocks.SNOW.getDefaultState(), Blocks.SAND.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.WATER.getDefaultState(), Blocks.LAVA.getDefaultState(), Blocks.OBSIDIAN.getDefaultState(), Blocks.AIR.getDefaultState(),
			Blocks.CAVE_AIR.getDefaultState(), Blocks.PACKED_ICE.getDefaultState());

	private static final BlockState LAVA = Blocks.LAVA.getDefaultState();
	private static final BlockState MAGMA = Blocks.MAGMA_BLOCK.getDefaultState();
	private static final BlockState OBSIDIAN = Blocks.OBSIDIAN.getDefaultState();

	private static Map<String, BlockState> lavaFloorBiomeMap;
	static {
		if (lavaFloorBiomeMap == null)
		{
			lavaFloorBiomeMap = new HashMap<>();

			lavaFloorBiomeMap.put(biomeIDString("iced_terrain"), Blocks.OBSIDIAN.getDefaultState());
			lavaFloorBiomeMap.put(biomeIDString("ice_spikes"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeIDString("relic_snowy_taiga"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeIDString("snowy_rocky_taiga"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeIDString("snowy_taiga"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeIDString("snowy_tundra"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeIDString("frozen_desert"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeIDString("deep_frozen_ocean"), Blocks.MAGMA_BLOCK.getDefaultState());
			lavaFloorBiomeMap.put(biomeIDString("frozen_ocean"), Blocks.MAGMA_BLOCK.getDefaultState());
		}
	}

	private SimpleRegistry<Biome> biomeRegistry;


	public UnderwaterCaveCarver(Codec<ProbabilityConfig> codec)
	{
		super(codec, 73);
	}


	@Override
	public boolean shouldCarve(Random random, int chunkX, int chunkZ, ProbabilityConfig config)
	{
		return random.nextFloat() <= config.probability;
	}


	@Override
	protected boolean func_222700_a(IChunk chunkIn, int chunkX, int chunkZ, int minX, int maxX, int minY, int maxY, int minZ, int maxZ)
	{
		return false;
	}

	@Override
	protected boolean carveBlock(IChunk chunk, Function<BlockPos, Biome> biomePos, BitSet carvingMask, Random rand, BlockPos.Mutable p_230358_5_, BlockPos.Mutable p_230358_6_, BlockPos.Mutable p_230358_7_, int p_230358_8_, int p_230358_9_, int p_230358_10_, int posX, int posZ, int p_230358_13_, int posY, int p_230358_15_, MutableBoolean isSurface) {
		IObjectIntIterable<Biome> reg = chunk.getBiomes() != null ? ((BiomeContainerAccessor)chunk.getBiomes()).getBiomeRegistry() : null;
		if(reg instanceof SimpleRegistry && reg != biomeRegistry){
			biomeRegistry = (SimpleRegistry<Biome>)((BiomeContainerAccessor)chunk.getBiomes()).getBiomeRegistry();
		}

		return func_222728_a(biomeRegistry, biomePos, chunk, carvingMask, rand, p_230358_5_, p_230358_8_, p_230358_9_, p_230358_10_, posX, posZ, p_230358_13_, posY, p_230358_15_);
	}


	protected static boolean func_222728_a(SimpleRegistry<Biome> biomeRegistry, Function<BlockPos, Biome> biomePos, IChunk chunk, BitSet carvingMask, Random random, BlockPos.Mutable MutableIn, int minHeight, int chunkX, int chunkZ, int x, int z, int maskY, int y, int atomicBoolean) {
		return carvingBlock(biomeRegistry, biomePos, chunk, carvingMask, random, MutableIn, minHeight, chunkX, chunkZ, x, z, maskY, y, atomicBoolean);
	}


	protected static boolean carvingBlock(SimpleRegistry<Biome> biomeRegistry, Function<BlockPos, Biome> biomeBlockPos, IChunk chunkIn, BitSet carvingMask, Random random, BlockPos.Mutable mutableBlockPos, int minHeight, int chunkX, int chunkZ, int xStart, int zStart, int maskY, int y, int atomicBoolean) {
		if (y >= minHeight) {
			return false;
		}
		else {
			mutableBlockPos.setPos(xStart, y, zStart);
			ResourceLocation biomeID = biomeRegistry != null ? biomeRegistry.getKey(biomeBlockPos.apply(mutableBlockPos)) : null;
			String biomeIDString = biomeID == null ? "" : biomeID.toString();

			BlockState lavaBlock = lavaFloorBiomeMap.get(biomeIDString);
			if (lavaBlock == null) {
				lavaBlock = LAVA;
			}

			BlockState blockstate = chunkIn.getBlockState(mutableBlockPos);
			if (!CARVABLE_BLOCKS.contains(blockstate)) {
				return false;
			}
			else if (y == 10) {
				float f = random.nextFloat();
				if (f < 0.25D && lavaBlock != OBSIDIAN) {
					chunkIn.setBlockState(mutableBlockPos, MAGMA, false);
					chunkIn.getBlocksToBeTicked().scheduleTick(mutableBlockPos, MAGMA.getBlock(), 0);
				}
				else {
					chunkIn.setBlockState(mutableBlockPos, OBSIDIAN, false);
				}

				return true;
			}
			else if (y < 10) {
				chunkIn.setBlockState(mutableBlockPos, lavaBlock, false);
				return false;
			}
			else {
				mutableBlockPos.setPos(xStart, y, zStart);
				chunkIn.setBlockState(mutableBlockPos, WATER.getBlockState(), false);
				return true;
			}
		}
	}
}
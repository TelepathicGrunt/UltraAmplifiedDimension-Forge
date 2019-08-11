package net.TelepathicGrunt.UltraAmplified.World.Feature;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Blocks.BlocksInit;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class GlowPatch extends Feature<NoFeatureConfig> {

	private final Map<BlockState, BlockState> GLOWBLOCKMAP = createMap();

	private static Map<BlockState, BlockState> createMap() {
		Map<BlockState, BlockState> result = new HashMap<BlockState, BlockState>();
		result.put(Blocks.DIRT.getDefaultState(), BlocksInit.GLOWDIRT.getDefaultState());
		result.put(Blocks.COARSE_DIRT.getDefaultState(), BlocksInit.COARSE_GLOWDIRT.getDefaultState());
		result.put(Blocks.GRASS_BLOCK.getDefaultState(), BlocksInit.GLOWGRASS.getDefaultState());
		result.put(Blocks.MYCELIUM.getDefaultState(), BlocksInit.GLOWMYCELIUM.getDefaultState());
		result.put(Blocks.STONE.getDefaultState(), BlocksInit.GLOWSTONE_ORE.getDefaultState());
		result.put(Blocks.PODZOL.getDefaultState(), BlocksInit.GLOWPODZOL.getDefaultState());
		result.put(Blocks.SAND.getDefaultState(), BlocksInit.GLOWSAND.getDefaultState());
		result.put(Blocks.RED_SAND.getDefaultState(), BlocksInit.REDGLOWSAND.getDefaultState());

		return Collections.unmodifiableMap(result);
	}

	public GlowPatch(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random rand, BlockPos pos, NoFeatureConfig blockConfig) {

		
		
		return true;
	}

}

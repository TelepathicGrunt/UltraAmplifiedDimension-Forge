package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.utils.GeneralUtils;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;


public class ContainUndergroundLiquids extends Feature<NoFeatureConfig>
{
	public ContainUndergroundLiquids(Codec<NoFeatureConfig> configFactory) {
		super(configFactory);
	}
	private MutableRegistry<Biome> BIOME_REGISTRY = null;

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, NoFeatureConfig configBlock) {
		if(BIOME_REGISTRY == null){
			BIOME_REGISTRY = world.getWorld().func_241828_r().getRegistry(Registry.BIOME_KEY);
		}

		BlockState replacementBlock;
		BlockState currentblock;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();
		IChunk chunk = world.getChunk(position.getX() >> 4, position.getZ() >> 4);

		for (int x = 0; x < 16; ++x) {
			for (int z = 0; z < 16; ++z) {
				blockpos$Mutable.setPos(position.getX() + x, 61, position.getZ() + z);
				while (blockpos$Mutable.getY() > 10) {
					currentblock = chunk.getBlockState(blockpos$Mutable);

					// move down until we hit a liquid filled block
					while (currentblock.getFluidState().isEmpty() && blockpos$Mutable.getY() > 10) {
						currentblock = chunk.getBlockState(blockpos$Mutable.move(Direction.DOWN));
					}

					//if too low, break and go to next xz coordinate
					if (blockpos$Mutable.getY() <= 10) {
						break;
					}

					// y value is now fully set for rest of code
					// checks to see if we are touching an air block
					for (Direction face : Direction.values()) {
						blockpos$Mutable.move(face);
						//Do world instead of chunk as this could check into the next chunk over.
						currentblock = world.getBlockState(blockpos$Mutable);
						if (currentblock.isAir()) {
							//grabs what block to use based on what biome we are in
							Biome biome = world.getBiome(blockpos$Mutable);
							ResourceLocation rl = BIOME_REGISTRY.getKey(biome);

							replacementBlock = GeneralUtils.carverFillerBlock(rl == null ? "" : rl.toString(), biome);
							world.setBlockState(blockpos$Mutable, replacementBlock, 2);
						}
						blockpos$Mutable.move(face.getOpposite());
					}

					blockpos$Mutable.move(Direction.DOWN);
				}
			}
		}
		return true;
	}
}
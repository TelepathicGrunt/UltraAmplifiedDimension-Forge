package net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;


public class EndSurfaceBuilderUA extends SurfaceBuilder<SurfaceBuilderConfig>
{
	public EndSurfaceBuilderUA(Function<Dynamic<?>, ? extends SurfaceBuilderConfig> p_i51310_1_)
	{
		super(p_i51310_1_);
	}

	private static final BlockState STONE = Blocks.STONE.getDefaultState();
	private static final BlockState ENDSTONE = Blocks.END_STONE.getDefaultState();


	@Override
	public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config)
	{
		//creates grass surface normally
		SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, config);
		int xpos = x & 15;
		int zpos = z & 15;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();

		//makes stone below sea level into end stone
		for (int ypos = seaLevel - 2; ypos >= 0; --ypos)
		{
			blockpos$Mutable.setPos(xpos, ypos, zpos);
			BlockState iblockstate2 = chunkIn.getBlockState(blockpos$Mutable);

			if (iblockstate2.getBlock() != null && iblockstate2.getMaterial() != Material.AIR)
			{
				if (iblockstate2 == STONE)
				{

					chunkIn.setBlockState(blockpos$Mutable, ENDSTONE, false);
				}
			}
		}
	}
}
package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class IceSpike extends Feature<NoFeatureConfig>
{

	public IceSpike(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	protected static final Set<BlockState> ALLOWED_BLOCKS = ImmutableSet.of(Blocks.AIR.getDefaultState(), Blocks.CAVE_AIR.getDefaultState(), Blocks.ICE.getDefaultState(), Blocks.PACKED_ICE.getDefaultState(), Blocks.SNOW.getDefaultState(), Blocks.SNOW_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState(), Blocks.WATER.getDefaultState());

	private static final BlockState WATER = Blocks.WATER.getDefaultState();
	private static final BlockState ICE = Blocks.ICE.getDefaultState();
	private static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();


	//ice spike code was changed to only generate taller ice spikes and to have spikes go all the way to Y = 5 if path is clear.
	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_)
	{

		if (world.getBlockState(position).getBlock() != Blocks.SNOW_BLOCK)
		{
			return false;
		}
		else
		{
			BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable(position);
			mutableBlockPos.move(Direction.UP, rand.nextInt(4));
			int headHeightOffset = rand.nextInt(4) + 7;
			int tailWidthOffset = headHeightOffset / 4 + rand.nextInt(2);
			int finalHeight = 0;

			if (tailWidthOffset > 1 && rand.nextInt(40) == 0)
			{
				//if ice spike has the potential to generate over 245, then set the position to 245
				if (mutableBlockPos.getY() + 130 > 245)
				{
					mutableBlockPos.move(Direction.UP, (245 - mutableBlockPos.getY()));
				}
				else
				{
					mutableBlockPos.move(Direction.UP, 30 + rand.nextInt(100));
				}

			}

			finalHeight = mutableBlockPos.getY();
			for (int y = 0; y < headHeightOffset; ++y)
			{
				float maxWidth = (1.0F - (float) y / (float) headHeightOffset) * tailWidthOffset;
				int range = MathHelper.ceil(maxWidth);

				for (int x = -range; x <= range; ++x)
				{
					float xWidth = MathHelper.abs(x) - 0.25F;

					for (int z = -range; z <= range; ++z)
					{
						float zWidth = MathHelper.abs(z) - 0.25F;

						if ((x == 0 && z == 0 || xWidth * xWidth + zWidth * zWidth <= maxWidth * maxWidth) && (x != -range && x != range && z != -range && z != range || rand.nextFloat() <= 0.75F))
						{
							BlockPos topPos = mutableBlockPos.add(x, y, z);
							BlockPos bottomPos = mutableBlockPos.add(x, -y, z);
							BlockState iblockstate = world.getBlockState(topPos);
							if (ALLOWED_BLOCKS.contains(iblockstate) && topPos.getY() > UltraAmplified.UATerrainConfig.seaLevel.get() - 2)
							{
								this.setBlockState(world, topPos, PACKED_ICE);
							}
							else if (iblockstate == WATER)
							{
								this.setBlockState(world, topPos, ICE);
							}

							if (y != 0 && range > 1)
							{
								iblockstate = world.getBlockState(bottomPos);

								if (ALLOWED_BLOCKS.contains(iblockstate) && bottomPos.getY() > UltraAmplified.UATerrainConfig.seaLevel.get() - 2)
								{
									this.setBlockState(world, bottomPos, PACKED_ICE);
								}
								else if (iblockstate == WATER)
								{
									this.setBlockState(world, bottomPos, ICE);
								}
							}
						}
					}
				}
			}

			int maxWidth = tailWidthOffset - 1;

			if (maxWidth < 0)
			{
				maxWidth = 0;
			}
			else if (maxWidth > 1)
			{
				maxWidth = 1;
			}

			for (int x = -maxWidth; x <= maxWidth; ++x)
			{
				for (int z = -maxWidth; z <= maxWidth; ++z)
				{
					mutableBlockPos.setPos(position.getX() + x, finalHeight - 1, position.getZ() + z);
					int randomSkippingHeightValue = 50;

					if (Math.abs(x) == 1 && Math.abs(z) == 1)
					{
						randomSkippingHeightValue = rand.nextInt(5);
					}

					//how far down the ice spike can generate
					while (mutableBlockPos.getY() > 5)
					{
						BlockState iblockstate1 = world.getBlockState(mutableBlockPos);

						if (!ALLOWED_BLOCKS.contains(iblockstate1))
						{
							break;
						}

						if (iblockstate1 == WATER || iblockstate1 == ICE)
						{
							this.setBlockState(world, mutableBlockPos, Blocks.ICE.getDefaultState());
						}
						else
						{
							this.setBlockState(world, mutableBlockPos, Blocks.PACKED_ICE.getDefaultState());
						}
						mutableBlockPos.move(Direction.DOWN);
						--randomSkippingHeightValue;

						// It's like a stamina bar. Every time we place a block, this goes down and when it hits 0,
						// it moves us downward a random height of 1 - 5. This is what makes the missing blocks on 
						// the corners of the Ice Spike pillar.
						if (randomSkippingHeightValue <= 0)
						{
							mutableBlockPos.move(Direction.DOWN, rand.nextInt(5) + 1);
							randomSkippingHeightValue = rand.nextInt(5);
						}
					}
				}
			}

			return true;
		}
	}
}

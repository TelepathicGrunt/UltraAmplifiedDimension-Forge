package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.GiantSpikeConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;


public class GiantSpike extends Feature<GiantSpikeConfig> {

    public GiantSpike(Codec<GiantSpikeConfig> configFactory) {
        super(configFactory);
    }

    //ice spike code was changed to only generate taller ice spikes and to have spikes go all the way to Y = 5 if path is clear.
    @Override
    public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos position, GiantSpikeConfig config) {

    	// Prevent feature stacking
    	BlockState startBlockState = world.getBlockState(position);
        if (!config.target.test(startBlockState, rand) &&
			startBlockState != config.aboveSeaState &&
			startBlockState != config.belowSeaState)
        {
            return false;
        }

		BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable().setPos(position);
		mutableBlockPos.move(Direction.UP, rand.nextInt(4));
		int headHeightOffset = rand.nextInt(4) + 7;
		int tailWidthOffset = headHeightOffset / 4 + rand.nextInt(2);
		int finalHeight;

		if (rand.nextFloat() < config.giantSpikeChance) {
			int extraHeight = rand.nextInt(config.giantSpikeMaxHeight - config.giantSpikeMinHeight) + config.giantSpikeMinHeight;

			//if ice spike has the potential to generate too close to top of world, then shrink it so it fits in world
			if (mutableBlockPos.getY() + extraHeight > generator.getMaxBuildHeight() - 10) {
				mutableBlockPos.move(Direction.UP, generator.getMaxBuildHeight() - 10 - mutableBlockPos.getY());
			}
			else {
				mutableBlockPos.move(Direction.UP, extraHeight);
			}

		}

		finalHeight = mutableBlockPos.getY();
		for (int y = 0; y < headHeightOffset; ++y) {
			float maxWidth = (1.0F - (float) y / (float) headHeightOffset) * tailWidthOffset;
			int range = MathHelper.ceil(maxWidth);

			for (int x = -range; x <= range; ++x) {
				float xWidth = MathHelper.abs(x) - 0.25F;

				for (int z = -range; z <= range; ++z) {
					float zWidth = MathHelper.abs(z) - 0.25F;

					if ((x == 0 && z == 0 || (xWidth * xWidth) + (zWidth * zWidth) <= maxWidth * maxWidth) &&
						((x != -range && x != range && z != -range && z != range) || rand.nextFloat() <= 0.75F))
					{
						BlockPos topPos = mutableBlockPos.add(x, y, z);
						BlockPos bottomPos = mutableBlockPos.add(x, -y, z);
						BlockState currentBlockState = world.getBlockState(topPos);
						if (config.target.test(currentBlockState, rand) && topPos.getY() >= generator.getSeaLevel() - 1) {
							this.setBlockState(world, topPos, config.aboveSeaState);
						}
						else if (!currentBlockState.getFluidState().isEmpty()) {
							this.setBlockState(world, topPos, config.belowSeaState);
						}

						if (y != 0 && range > 1) {
							currentBlockState = world.getBlockState(bottomPos);

							if (config.target.test(currentBlockState, rand) && bottomPos.getY() >= generator.getSeaLevel() - 1) {
								this.setBlockState(world, bottomPos, config.aboveSeaState);
							}
							else if (!currentBlockState.getFluidState().isEmpty()) {
								this.setBlockState(world, bottomPos, config.belowSeaState);
							}
						}
					}
				}
			}
		}

		int maxWidth = 1;
		for (int x = -maxWidth; x <= maxWidth; ++x) {
			for (int z = -maxWidth; z <= maxWidth; ++z) {
				mutableBlockPos.setPos(position.getX() + x, finalHeight - 1, position.getZ() + z);
				int modeThreshold = Integer.MAX_VALUE;
				boolean placingMode = true;

				if (Math.abs(x) == maxWidth && Math.abs(z) == maxWidth) {
					modeThreshold = rand.nextInt(5);
				}


				//how far down the ice spike can generate
				while (mutableBlockPos.getY() > 5) {
					BlockState currentBlockState = world.getBlockState(mutableBlockPos);
					if (mutableBlockPos.getY() != finalHeight -1 && !config.target.test(currentBlockState, rand)) {
						break;
					}

					if(placingMode){
						if (mutableBlockPos.getY() < generator.getSeaLevel() - 1) {
							this.setBlockState(world, mutableBlockPos, config.belowSeaState);
						}
						else {
							this.setBlockState(world, mutableBlockPos, config.aboveSeaState);
						}
					}
					else if(mutableBlockPos.getY() == generator.getSeaLevel() - 1){
						this.setBlockState(world, mutableBlockPos, config.aboveSeaState);
					}

					mutableBlockPos.move(Direction.DOWN);

					// Using rng spam, this is what makes the missing blocks on the corners of the Ice Spike pillar.
					// The blocks will be placed in a row or missing in a row.
					if (modeThreshold <= 0) {
						++modeThreshold;

						if(placingMode){
							placingMode = false;
							modeThreshold = -(rand.nextInt(6) - 1);
						}
					}
					else {
						--modeThreshold;

						if(!placingMode){
							placingMode = true;
							modeThreshold = rand.nextInt(5);
						}
					}
				}
			}
		}

		return true;
    }
}

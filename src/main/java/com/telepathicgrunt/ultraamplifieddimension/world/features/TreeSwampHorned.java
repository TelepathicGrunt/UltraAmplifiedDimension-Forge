package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.VineBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.math.shapes.BitSetVoxelShapePart;
import net.minecraft.util.math.shapes.VoxelShapePart;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldWriter;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.IWorldGenerationBaseReader;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.template.Template;

import java.util.*;


public class TreeSwampHorned extends Feature<BaseTreeFeatureConfig> {

	public TreeSwampHorned(Codec<BaseTreeFeatureConfig> config) {
		super(config);
	}

	@Override
	public boolean generate(ISeedReader serverWorldAccess, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, BaseTreeFeatureConfig config) {
		Set<BlockPos> set = Sets.newHashSet();
		Set<BlockPos> set2 = Sets.newHashSet();
		Set<BlockPos> set3 = Sets.newHashSet();
		MutableBoundingBox blockBox = MutableBoundingBox.getNewBoundingBox();
		boolean bl = this.generate(serverWorldAccess, chunkGenerator, random, blockPos, set, set2, config);
		if (blockBox.minX <= blockBox.maxX && bl && !set.isEmpty()) {
			if (!config.decorators.isEmpty()) {
				List<BlockPos> list = Lists.newArrayList(set);
				List<BlockPos> list2 = Lists.newArrayList(set2);
				list.sort(Comparator.comparingInt(Vector3i::getY));
				list2.sort(Comparator.comparingInt(Vector3i::getY));
				config.decorators.forEach((decorator) -> decorator.func_225576_a_(serverWorldAccess, random, list, list2, set3, blockBox));
			}

			VoxelShapePart voxelSet = this.placeLogsAndLeaves(serverWorldAccess, blockBox, set, set3);
			Template.func_222857_a(serverWorldAccess, 3, voxelSet, blockBox.minX, blockBox.minY, blockBox.minZ);
			return true;
		} else {
			return false;
		}
	}


	// generate the spooky horned swamp m trees
	private boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, Set<BlockPos> logPositions, Set<BlockPos> leavesPositions, BaseTreeFeatureConfig config) {
		int height = config.trunkPlacer.func_236917_a_(random);
		BlockState currentPos = world.getBlockState(position);
		BlockState belowPos = world.getBlockState(position.down());

		// checks to see if there is room to generate tree
		if (!this.isSpaceAt(world, chunkGenerator, position, height)) {
			return false;
		}

		// sets tree in water if there is water below
		if (world.getBlockState(position.down()).getFluidState().isTagged(FluidTags.WATER)) {
			position = position.down();
		}

		boolean flag = true;

		if (position.getY() >= 1 && position.getY() + height + 1 <= chunkGenerator.getMaxBuildHeight()) {
			for (int y = position.getY(); y <= position.getY() + 1 + height; ++y) {
				int radius = 1;

				if (y == position.getY()) {
					radius = 0;
				}

				if (y >= position.getY() + 1 + height - 2) {
					radius = 3;
				}

				BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();

				for (int x = position.getX() - radius; x <= position.getX() + radius && flag; ++x) {
					for (int z = position.getZ() - radius; z <= position.getZ() + radius && flag; ++z) {
						if (y >= 0 && y < chunkGenerator.getMaxBuildHeight()) {
							blockpos$Mutable.setPos(x, y, z);
							if (!isAirOrLeaves(world, blockpos$Mutable)) {
								if (y > position.getY() && !isWater(world, blockpos$Mutable)) {
									flag = false;
								}
							}
						} else {
							flag = false;
						}
					}
				}
			}

			if (!flag) {
				return false;
			}
			else if (isDirtOrGrass(world, position.down()) && position.getY() < chunkGenerator.getMaxBuildHeight() - height - 1) {

				for (int currentHeight = position.getY() - 4 + height; currentHeight <= position.getY() + height; ++currentHeight) {
					int heightDiff = currentHeight - (position.getY() + height);
					int leavesWidth = 2 - heightDiff / 2;

					for (int x = position.getX() - leavesWidth - 1; x <= position.getX() + leavesWidth; ++x) {
						int xPos = x - position.getX();

						for (int z = position.getZ() - leavesWidth - 1; z <= position.getZ() + leavesWidth; ++z) {
							int zPos = z - position.getZ();
							int isCornerIfThisIsTwo = 0;

							if (xPos == leavesWidth) {
								isCornerIfThisIsTwo++;
							}
							if (zPos == leavesWidth) {
								isCornerIfThisIsTwo++;
							}
							if (xPos == -leavesWidth - 1) {
								isCornerIfThisIsTwo++;
							}
							if (zPos == -leavesWidth - 1) {
								isCornerIfThisIsTwo++;
							}

							// generate leaves if is in corners or if 2/3rd rng is true
							if (isCornerIfThisIsTwo == 2 || random.nextInt(3) < 2 && heightDiff != 0) {
								BlockPos blockpos = new BlockPos(x, currentHeight, z);

								if (isAirOrLeaves(world, blockpos) || isReplaceablePlant(world, blockpos)) {
									this.setBlockState(world, blockpos, config.leavesProvider.getBlockState(random, blockpos)); // .with(LeavesBlock.DISTANCE, 1)
									leavesPositions.add(blockpos);
								}
							}
						}
					}
				}

				// the following four for statements generates the trunk of the tree
				genTrunk(world, position, height, random, logPositions, leavesPositions, config);
				genTrunk(world, position.west(), height, random, logPositions, leavesPositions, config);
				genTrunk(world, position.north(), height, random, logPositions, leavesPositions, config);
				genTrunk(world, position.west().north(), height, random, logPositions, leavesPositions, config);

				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}


	private void genTrunk(ISeedReader world, BlockPos position, int height, Random rand, Set<BlockPos> logPositions, Set<BlockPos> leavesPositions, BaseTreeFeatureConfig config) {
		this.setBlockState(world, position.down(), Blocks.DIRT.getDefaultState());
		BlockPos.Mutable mutable = new BlockPos.Mutable().setPos(position);

		for (int currentHeight = 0; currentHeight < height; currentHeight++) {
			BlockState iblockstate1 = world.getBlockState(mutable);

			if(!iblockstate1.isSolid()){
				if (currentHeight != height - 1) {
					this.setBlockState(world, mutable, config.trunkProvider.getBlockState(rand, mutable));
					logPositions.add(mutable);
				} else if(currentHeight == height - 1) {
					this.setBlockState(world, mutable, config.leavesProvider.getBlockState(rand, mutable)); //.with(LeavesBlock.DISTANCE, 1)
					leavesPositions.add(mutable);
				}
			}

			mutable.move(Direction.UP);
		}
	}


	private boolean isSpaceAt(ISeedReader world, ChunkGenerator chunkGenerator, BlockPos leavesPos, int height) {
		boolean spaceFound = true;
		if (leavesPos.getY() >= 1 && leavesPos.getY() + height + 1 <= chunkGenerator.getMaxBuildHeight()) {
			for (int y = 0; y <= 1 + height; ++y) {
				int radius = 2;
				if (y == 0) {
					radius = 1;
				}

				for (int x = -radius; x <= radius && spaceFound; ++x) {
					for (int z = -radius; z <= radius && spaceFound; ++z) {
						if (leavesPos.getY() + y < 0 || leavesPos.getY() + y >= chunkGenerator.getMaxBuildHeight() || !canTreeReplace(world, leavesPos.add(x, y, z))) {
							spaceFound = false;
						}
					}
				}
			}

			return spaceFound;
		} else {
			return false;
		}
	}


	public static boolean canTreeReplace(IWorldGenerationBaseReader world, BlockPos pos) {
		return canReplace(world, pos) || world.hasBlockState(pos, (state) -> state.isIn(BlockTags.LOGS));
	}

	private static boolean isWater(IWorldGenerationBaseReader world, BlockPos pos) {
		return world.hasBlockState(pos, (state) -> state.isIn(Blocks.WATER));
	}


	public static boolean isAirOrLeaves(IWorldGenerationBaseReader world, BlockPos pos) {
		return world.hasBlockState(pos, (state) -> state.isAir() || state.isIn(BlockTags.LEAVES));
	}


	private static boolean isDirtOrGrass(IWorldGenerationBaseReader world, BlockPos pos) {
		return world.hasBlockState(pos, (state) -> isDirt(state.getBlock()) || state.isIn(Blocks.FARMLAND));
	}


	private static boolean isReplaceablePlant(IWorldGenerationBaseReader world, BlockPos pos) {
		return world.hasBlockState(pos, (state) -> {
			Material material = state.getMaterial();
			return material == Material.TALL_PLANTS;
		});
	}


	public static void setBlockStateWithoutUpdatingNeighbors(IWorldWriter world, BlockPos pos, BlockState state) {
		world.setBlockState(pos, state, 19);
	}


	public static boolean canReplace(IWorldGenerationBaseReader IWorldGenerationBaseReader, BlockPos pos) {
		return isAirOrLeaves(IWorldGenerationBaseReader, pos) || isReplaceablePlant(IWorldGenerationBaseReader, pos) || isWater(IWorldGenerationBaseReader, pos);
	}


	private VoxelShapePart placeLogsAndLeaves(IWorld world, MutableBoundingBox box, Set<BlockPos> logs, Set<BlockPos> leaves) {
		List<Set<BlockPos>> list = Lists.newArrayList();
		VoxelShapePart voxelSet = new BitSetVoxelShapePart(box.getXSize(), box.getYSize(), box.getZSize());

		for (int j = 0; j < 6; ++j) {
			list.add(Sets.newHashSet());
		}

		BlockPos.Mutable mutable = new BlockPos.Mutable();
		Iterator<BlockPos> var9 = Lists.newArrayList(leaves).iterator();

		BlockPos blockPos2;
		while (var9.hasNext()) {
			blockPos2 = var9.next();
			if (box.isVecInside(blockPos2)) {
				voxelSet.setFilled(blockPos2.getX() - box.minX, blockPos2.getY() - box.minY, blockPos2.getZ() - box.minZ, true, true);
			}
		}

		var9 = Lists.newArrayList(logs).iterator();

		while (var9.hasNext()) {
			blockPos2 = var9.next();
			if (box.isVecInside(blockPos2)) {
				voxelSet.setFilled(blockPos2.getX() - box.minX, blockPos2.getY() - box.minY, blockPos2.getZ() - box.minZ, true, true);
			}

			Direction[] var11 = Direction.values();

			for (Direction direction : var11) {
				mutable.setAndMove(blockPos2, direction);
				if (!logs.contains(mutable)) {
					BlockState blockState = world.getBlockState(mutable);
					if (blockState.hasProperty(BlockStateProperties.DISTANCE_1_7)) {
						list.get(0).add(mutable.toImmutable());
						setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState.with(BlockStateProperties.DISTANCE_1_7, 1));
						if (box.isVecInside(mutable)) {
							voxelSet.setFilled(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
						}
					}
				}
			}
		}

		for (int k = 1; k < 6; ++k) {
			Set<BlockPos> set = list.get(k - 1);
			Set<BlockPos> set2 = list.get(k);

			for (BlockPos blockPos3 : set) {
				if (box.isVecInside(blockPos3)) {
					voxelSet.setFilled(blockPos3.getX() - box.minX, blockPos3.getY() - box.minY, blockPos3.getZ() - box.minZ, true, true);
				}

				Direction[] var27 = Direction.values();

				for (Direction direction2 : var27) {
					mutable.setAndMove(blockPos3, direction2);
					if (!set.contains(mutable) && !set2.contains(mutable)) {
						BlockState blockState2 = world.getBlockState(mutable);
						if (blockState2.hasProperty(BlockStateProperties.DISTANCE_1_7)) {
							int l = blockState2.get(BlockStateProperties.DISTANCE_1_7);
							if (l > k + 1) {
								BlockState blockState3 = blockState2.with(BlockStateProperties.DISTANCE_1_7, k + 1);
								setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState3);
								if (box.isVecInside(mutable)) {
									voxelSet.setFilled(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
								}

								set2.add(mutable.toImmutable());
							}
						}
					}
				}
			}
		}

		return voxelSet;
	}
}
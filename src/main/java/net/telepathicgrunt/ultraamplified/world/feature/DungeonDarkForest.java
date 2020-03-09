package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.MobSpawnerTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.storage.loot.LootTables;


public class DungeonDarkForest extends Feature<NoFeatureConfig>
{
	public DungeonDarkForest(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private static final Logger LOGGER = LogManager.getLogger();
	private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
	private static final BlockState LEAVES = Blocks.DARK_OAK_LEAVES.getDefaultState().with(LeavesBlock.DISTANCE, Integer.valueOf(1));
	private static final BlockState LOGS = Blocks.DARK_OAK_LOG.getDefaultState();
	private static final BlockState PLANKS = Blocks.DARK_OAK_PLANKS.getDefaultState();


	//only the mob spawner chance and what blocks the wall cannot replace was changed. Everything else is just the normal dungeon code.

	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_)
	{
		int randXRange = rand.nextInt(2) + 2;
		int xMin = -randXRange - 1;
		int xMax = randXRange + 1;
		int randZRange = rand.nextInt(2) + 2;
		int zMin = -randZRange - 1;
		int zMax = randZRange + 1;
		int validOpenings = 0;
		int ceilingOpenings = 0;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);

		for (int x = xMin; x <= xMax; ++x)
		{
			for (int y = -1; y <= 4; ++y)
			{
				for (int z = zMin; z <= zMax; ++z)
				{
					blockpos$Mutable.setPos(position).move(x, y, z);
					Material material = world.getBlockState(blockpos$Mutable).getMaterial();
					boolean flag = material.isSolid();

					if (y == -1 && !flag)
					{
						return false;
					}

					if (y == 4 && !flag)
					{
						ceilingOpenings++;
					}

					if ((x == xMin || x == xMax || z == zMin || z == zMax) && y == 0 && world.isAirBlock(blockpos$Mutable) && world.isAirBlock(blockpos$Mutable.up()))
					{
						++validOpenings;
					}
				}
			}
		}

		if (validOpenings >= 1 && validOpenings <= 14 && ceilingOpenings < 14)
		{
			for (int x = xMin; x <= xMax; ++x)
			{
				for (int y = 3; y >= -1; --y)
				{
					for (int z = zMin; z <= zMax; ++z)
					{
						blockpos$Mutable.setPos(position).move(x, y, z);

						if (x != xMin && y != -1 && z != zMin && x != xMax && y != 4 && z != zMax)
						{
							if (world.getBlockState(blockpos$Mutable).getBlock() != Blocks.CHEST && world.getBlockState(blockpos$Mutable).getBlock() != Blocks.SPAWNER)
							{
								world.setBlockState(blockpos$Mutable, CAVE_AIR, 2);
							}
						}
						else if (blockpos$Mutable.getY() >= 0 && !world.getBlockState(blockpos$Mutable.down()).getMaterial().isSolid())
						{
							world.setBlockState(blockpos$Mutable, CAVE_AIR, 2);
						}

						//made sure the dungeon wall cannot replace other dungeon's mob spawner now.
						else if (world.getBlockState(blockpos$Mutable).getMaterial().isSolid() && world.getBlockState(blockpos$Mutable).getBlock() != Blocks.CHEST && world.getBlockState(blockpos$Mutable).getBlock() != Blocks.SPAWNER)
						{
							if (y == -1 && rand.nextInt(5) != 0)
							{
								world.setBlockState(blockpos$Mutable, PLANKS, 2);
							}
							else
							{
								if (rand.nextInt(3) == 0)
								{
									world.setBlockState(blockpos$Mutable, LOGS, 2);
								}
								else
								{
									world.setBlockState(blockpos$Mutable, LEAVES, 2);
								}
							}
						}
					}
				}
			}

			for (int l3 = 0; l3 < 2; ++l3)
			{
				for (int j4 = 0; j4 < 3; ++j4)
				{
					int x = position.getX() + rand.nextInt(randXRange * 2 + 1) - randXRange;
					int y = position.getY();
					int z = position.getZ() + rand.nextInt(randZRange * 2 + 1) - randZRange;
					blockpos$Mutable.setPos(x, y, z);

					if (world.isAirBlock(blockpos$Mutable))
					{
						int j3 = 0;

						for (Direction Direction : Direction.Plane.HORIZONTAL)
						{
							if (world.getBlockState(blockpos$Mutable.offset(Direction)).getMaterial().isSolid())
							{
								++j3;
							}
						}

						if (j3 == 1)
						{
							world.setBlockState(blockpos$Mutable, StructurePiece.func_197528_a(world, blockpos$Mutable, Blocks.CHEST.getDefaultState()), 2);
							LockableLootTileEntity.setLootTable(world, rand, blockpos$Mutable, LootTables.CHESTS_SIMPLE_DUNGEON);

							break;
						}
					}
				}
			}

			world.setBlockState(position, Blocks.SPAWNER.getDefaultState(), 2);
			TileEntity tileentity = world.getTileEntity(position);

			if (tileentity instanceof MobSpawnerTileEntity)
			{
				((MobSpawnerTileEntity) tileentity).getSpawnerBaseLogic().setEntityType(this.pickMobSpawner(world, rand, position));
			}
			else
			{
				LOGGER.error("Failed to fetch mob spawner entity at ({}, {}, {})", new Object[] { Integer.valueOf(position.getX()), Integer.valueOf(position.getY()), Integer.valueOf(position.getZ()) });
			}

			return true;
		}
		else
		{
			return false;
		}
	}


	/**
	 * Randomly decides which spawner to use in a dungeon
	 */
	private EntityType<?> pickMobSpawner(IWorld world, Random rand, BlockPos position)
	{
		int roll = rand.nextInt(100);

		if (roll < 48)
		{
			//48% chance
			return UAFeatures.pickRandomDungeonMob(rand);
		}
		else if (roll < 73)
		{
			//25% chance
			return EntityType.VEX;

		}
		else if (roll < 98)
		{
			//25% chance
			return UAFeatures.pickRandomDungeonMob(rand);
		}
		else if (roll == 98)
		{
			//1% chance
			return EntityType.CREEPER;
		}
		else
		{
			//1% chance
			return EntityType.ILLUSIONER;
		}
	}
}

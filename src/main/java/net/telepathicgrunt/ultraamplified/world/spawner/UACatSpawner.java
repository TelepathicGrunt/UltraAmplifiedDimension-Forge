package net.telepathicgrunt.ultraamplified.world.spawner;

import java.util.Random;

import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.CatSpawner;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.telepathicgrunt.ultraamplified.world.feature.UAFeatures;


public class UACatSpawner extends CatSpawner
{
	private int field_221125_a;


	@Override
	public int tick(ServerWorld world, boolean spawnHostileMobs, boolean spawnPeacefulMobs)
	{
		if (spawnPeacefulMobs && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING))
		{
			--this.field_221125_a;
			if (this.field_221125_a > 0)
			{
				return 0;
			}
			else
			{
				this.field_221125_a = 1200;
				PlayerEntity playerentity = world.getRandomPlayer();
				if (playerentity == null)
				{
					return 0;
				}
				else
				{
					Random random = world.rand;
					int i = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
					int j = (8 + random.nextInt(24)) * (random.nextBoolean() ? -1 : 1);
					BlockPos blockpos = (new BlockPos(playerentity)).add(i, 0, j);
					if (!world.isAreaLoaded(blockpos, 10))
					{
						return 0;
					}
					else
					{
						if (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, world, blockpos, EntityType.CAT))
						{
							//                     if (world.func_217471_a(blockpos, 2)) {
							//                        return this.func_221121_a(world, blockpos);
							//                     }

							if (UAFeatures.WITCH_HUT.isPositionInsideStructure(world, blockpos))
							{
								return this.func_221123_a(world, blockpos);
							}
						}

						return 0;
					}
				}
			}
		}
		else
		{
			return 0;
		}
	}
}
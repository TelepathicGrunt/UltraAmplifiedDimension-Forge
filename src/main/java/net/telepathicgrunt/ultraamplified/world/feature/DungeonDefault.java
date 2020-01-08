package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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

public class DungeonDefault extends Feature<NoFeatureConfig> 
{
    public DungeonDefault(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	private static final Logger LOGGER = LogManager.getLogger();
    private static final BlockState CaveAir = Blocks.CAVE_AIR.getDefaultState();
    
    //only the mob spawner chance and what blocks the wall cannot replace was changed. Everything else is just the normal dungeon code.
    
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_)
    {
        int randXRange = rand.nextInt(2) + 2;
        int minX = -randXRange - 1;
        int maxX = randXRange + 1;
        int randZRange = rand.nextInt(2) + 2;
        int minZ = -randZRange - 1;
        int maxZ = randZRange + 1;
        int validOpenings = 0;
        int ceilingOpenings = 0;

        for (int x = minX; x <= maxX; ++x)
        {
            for (int y = -1; y <= 4; ++y)
            {
                for (int z = minZ; z <= maxZ; ++z)
                {
                    BlockPos blockpos = position.add(x, y, z);
                    Material material = world.getBlockState(blockpos).getMaterial();
                    boolean flag = material.isSolid();

                    if (y == -1 && !flag)
                    {
                    	return false;
                    }

                    if (y == 4 && !flag)
                    {
                    	ceilingOpenings++;
                    }

                    if ((x == minX || x == maxX || z == minZ || z == maxZ) && y == 0 && world.isAirBlock(blockpos) && world.isAirBlock(blockpos.up()))
                    {
                        validOpenings++;
                    }
                }
            }
        }

        if (validOpenings >= 1 && validOpenings <= 14 && ceilingOpenings < 14)
        {
            for (int x = minX; x <= maxX; ++x)
            {
                for (int y = 3; y >= -1; --y)
                {
                    for (int z = minZ; z <= maxZ; ++z)
                    {
                        BlockPos blockpos1 = position.add(x, y, z);

                        if (x != minX && y != -1 && z != minZ && x != maxX && y != 4 && z != maxZ)
                        {
                            if (world.getBlockState(blockpos1).getBlock() != Blocks.CHEST && world.getBlockState(blockpos1).getBlock() != Blocks.SPAWNER)
                            {
                                world.setBlockState(blockpos1, CaveAir, 2);
                            }
                        }
                        else if (blockpos1.getY() >= 0 && !world.getBlockState(blockpos1.down()).getMaterial().isSolid())
                        {
                            world.setBlockState(blockpos1, CaveAir, 2);
                        }
                        
                        //made sure the dungeon wall cannot replace other dungeon's mob spawner now.
                        else if (world.getBlockState(blockpos1).getMaterial().isSolid() && world.getBlockState(blockpos1).getBlock() != Blocks.CHEST && world.getBlockState(blockpos1).getBlock() != Blocks.SPAWNER)
                        {
                            if (y == -1 && rand.nextInt(4) != 0)
                            {
                                world.setBlockState(blockpos1, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 2);
                            }
                            else
                            {
                                world.setBlockState(blockpos1, Blocks.COBBLESTONE.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }

            for (int l3 = 0; l3 < 2; ++l3)
            {
                for (int j4 = 0; j4 < 3; ++j4)
                {
                    int l4 = position.getX() + rand.nextInt(randXRange * 2 + 1) - randXRange;
                    int i5 = position.getY();
                    int j5 = position.getZ() + rand.nextInt(randZRange * 2 + 1) - randZRange;
                    BlockPos blockpos2 = new BlockPos(l4, i5, j5);

                    if (world.isAirBlock(blockpos2))
                    {
                        int j3 = 0;

                        for (Direction Direction : Direction.Plane.HORIZONTAL)
                        {
                            if (world.getBlockState(blockpos2.offset(Direction)).getMaterial().isSolid())
                            {
                                ++j3;
                            }
                        }

                        if (j3 == 1)
                        {
                        	world.setBlockState(blockpos2, StructurePiece.func_197528_a(world, blockpos2, Blocks.CHEST.getDefaultState()), 2); 
                        	LockableLootTileEntity.setLootTable(world, rand, blockpos2, LootTables.CHESTS_SIMPLE_DUNGEON);

                            break;
                        }
                    }
                }
            }

            world.setBlockState(position, Blocks.SPAWNER.getDefaultState(), 2);
            TileEntity tileentity = world.getTileEntity(position);

            if (tileentity instanceof MobSpawnerTileEntity)
            {
             ((MobSpawnerTileEntity)tileentity).getSpawnerBaseLogic().setEntityType(this.pickMobSpawner(world, rand, position));
            }
            else
            {
                LOGGER.error("Failed to fetch mob spawner entity at ({}, {}, {})", new Object[] {Integer.valueOf(position.getX()), Integer.valueOf(position.getY()), Integer.valueOf(position.getZ())});
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
    	
    	if(roll < 48) {
    		//48% chance
    		return pickRandomDungeonMob(rand);
    	}
    	else if(roll < 73) {
    		//25% chance
    		return pickRandomDungeonMob(rand);
    	}
    	else if(roll < 98) {
    		//25% chance
    		return pickRandomDungeonMob(rand);
    	}
    	else if(roll == 98) {
    		//1% chance
    		return EntityType.CREEPER;
        }
    	else {
    		//1% chance
    		return EntityType.PIG;
    	}
    }
    
    private EntityType<?> pickRandomDungeonMob(Random p_201043_1_) {
        return net.minecraftforge.common.DungeonHooks.getRandomDungeonMob(p_201043_1_);
     }
}

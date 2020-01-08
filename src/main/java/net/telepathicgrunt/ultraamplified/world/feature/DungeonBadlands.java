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

public class DungeonBadlands extends Feature<NoFeatureConfig> 
{
    public DungeonBadlands(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	private static final Logger LOGGER = LogManager.getLogger();
    private static final BlockState CAVE_AIR = Blocks.CAVE_AIR.getDefaultState();
    private static final BlockState RED_TERRACOTTA = Blocks.RED_TERRACOTTA.getDefaultState();
    private static final BlockState ORANGE_TERRACOTTA = Blocks.ORANGE_TERRACOTTA.getDefaultState();
    private static final BlockState CUT_RED_SANDSTONE = Blocks.CUT_RED_SANDSTONE.getDefaultState();
    
    //only the mob spawner chance and what blocks the wall cannot replace was changed. Everything else is just the normal dungeon code.
    
    public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_)
    {
        int j = rand.nextInt(2) + 2;
        int xMin = -j - 1;
        int xMax = j + 1;
        int k1 = rand.nextInt(2) + 2;
        int zMin = -k1 - 1;
        int zMax = k1 + 1;
        int j2 = 0;
        int ceilingOpenings = 0;

        for (int k2 = xMin; k2 <= xMax; ++k2)
        {
            for (int l2 = -1; l2 <= 4; ++l2)
            {
                for (int i3 = zMin; i3 <= zMax; ++i3)
                {
                    BlockPos blockpos = position.add(k2, l2, i3);
                    Material material = world.getBlockState(blockpos).getMaterial();
                    boolean flag = material.isSolid();

                    if (l2 == -1 && !flag)
                    {
                        return false;
                    }

                    if (l2 == 4 && !flag)
                    {
                    	ceilingOpenings++;
                    }

                    if ((k2 == xMin || k2 == xMax || i3 == zMin || i3 == zMax) && l2 == 0 && world.isAirBlock(blockpos) && world.isAirBlock(blockpos.up()))
                    {
                        ++j2;
                    }
                }
            }
        }

        if (j2 >= 1 && j2 <= 14 && ceilingOpenings < 14)
        {
            for (int xOffset = xMin; xOffset <= xMax; ++xOffset)
            {
                for (int y = 4; y >= -1; --y)
                {
                    for (int zOffset = zMin; zOffset <= zMax; ++zOffset)
                    {
                        BlockPos blockpos1 = position.add(xOffset, y, zOffset);

                        if (xOffset != xMin && zOffset != zMin && xOffset != xMax&& zOffset != zMax && y != -1 && y != 5 )
                        {
                        	if(y == 4) {
                        		//ceiling
                            	if (rand.nextInt(4) == 0)
                            	{
                                	world.setBlockState(blockpos1, CUT_RED_SANDSTONE, 2);
                            	}else 
                            	{
                                	world.setBlockState(blockpos1, ORANGE_TERRACOTTA, 2);
                            	}
                        	}
                        	else if (world.getBlockState(blockpos1).getBlock() != Blocks.CHEST && world.getBlockState(blockpos1).getBlock() != Blocks.SPAWNER)
                            {
                                world.setBlockState(blockpos1, CAVE_AIR, 2);
                            }
                        }
                        else if (blockpos1.getY() >= 0 && !world.getBlockState(blockpos1.down()).getMaterial().isSolid())
                        {
                            world.setBlockState(blockpos1, CAVE_AIR, 2);
                        }
                        
                        //made sure the dungeon wall cannot replace other dungeon's mob spawner now.
                        else if (world.getBlockState(blockpos1).getMaterial().isSolid() && world.getBlockState(blockpos1).getBlock() != Blocks.CHEST && world.getBlockState(blockpos1).getBlock() != Blocks.SPAWNER)
                        {
                            if (y == -1 && rand.nextInt(4) != 0)
                            {
                                world.setBlockState(blockpos1, RED_TERRACOTTA, 2);
                            }
                            else if (rand.nextInt(2) == 0)
                            {
                                world.setBlockState(blockpos1, ORANGE_TERRACOTTA, 2);
                            }
                            else
                            {
                                world.setBlockState(blockpos1, CUT_RED_SANDSTONE, 2);
                            }
                        }
                    }
                }
            }

            for (int l3 = 0; l3 < 2; ++l3)
            {
                for (int j4 = 0; j4 < 3; ++j4)
                {
                    int l4 = position.getX() + rand.nextInt(j * 2 + 1) - j;
                    int i5 = position.getY();
                    int j5 = position.getZ() + rand.nextInt(k1 * 2 + 1) - k1;
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
    		return EntityType.HUSK;
    	}
    	else if(roll < 73) {
    		//25% chance
        	return EntityType.CAVE_SPIDER;
    	}
    	else if(roll < 98) {
    		//25% chance
    		EntityType<?> et= pickRandomDungeonMob(rand);
    		if(et != EntityType.ZOMBIE) 
    		{
        		return et;
    		}
    		else {
        		return EntityType.HUSK;
    		}
    	}
    	else if(roll == 98) {
    		//1% chance
        	return EntityType.CREEPER;
        }
    	else {
    		//1% chance
      		return EntityType.ILLUSIONER;
    	}
    }
    
    private EntityType<?> pickRandomDungeonMob(Random p_201043_1_) {
        return net.minecraftforge.common.DungeonHooks.getRandomDungeonMob(p_201043_1_);
     }
}

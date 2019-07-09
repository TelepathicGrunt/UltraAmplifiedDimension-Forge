package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.storage.loot.LootTableList;

public class DungeonDefault extends Feature<NoFeatureConfig> 
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final IBlockState CaveAir = Blocks.CAVE_AIR.getDefaultState();
    
    //only the mob spawner chance and what blocks the wall cannot replace was changed. Everything else is just the normal dungeon code.
    
    public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_)
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
                    Material material = worldIn.getBlockState(blockpos).getMaterial();
                    boolean flag = material.isSolid();

                    if (y == -1 && !flag)
                    {
                    	return false;
                    }

                    if (y == 4 && !flag)
                    {
                    	ceilingOpenings++;
                    }

                    if ((x == minX || x == maxX || z == minZ || z == maxZ) && y == 0 && worldIn.isAirBlock(blockpos) && worldIn.isAirBlock(blockpos.up()))
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
                            if (worldIn.getBlockState(blockpos1).getBlock() != Blocks.CHEST && worldIn.getBlockState(blockpos1).getBlock() != Blocks.SPAWNER)
                            {
                                worldIn.setBlockState(blockpos1, CaveAir, 2);
                            }
                        }
                        else if (blockpos1.getY() >= 0 && !worldIn.getBlockState(blockpos1.down()).getMaterial().isSolid())
                        {
                            worldIn.setBlockState(blockpos1, CaveAir, 2);
                        }
                        
                        //made sure the dungeon wall cannot replace other dungeon's mob spawner now.
                        else if (worldIn.getBlockState(blockpos1).getMaterial().isSolid() && worldIn.getBlockState(blockpos1).getBlock() != Blocks.CHEST && worldIn.getBlockState(blockpos1).getBlock() != Blocks.SPAWNER)
                        {
                            if (y == -1 && rand.nextInt(4) != 0)
                            {
                                worldIn.setBlockState(blockpos1, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 2);
                            }
                            else
                            {
                                worldIn.setBlockState(blockpos1, Blocks.COBBLESTONE.getDefaultState(), 2);
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

                    if (worldIn.isAirBlock(blockpos2))
                    {
                        int j3 = 0;

                        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
                        {
                            if (worldIn.getBlockState(blockpos2.offset(enumfacing)).getMaterial().isSolid())
                            {
                                ++j3;
                            }
                        }

                        if (j3 == 1)
                        {
                        	worldIn.setBlockState(blockpos2, StructurePiece.func_197528_a(worldIn, blockpos2, Blocks.CHEST.getDefaultState()), 2); 
                        	TileEntityLockableLoot.setLootTable(worldIn, rand, blockpos2, LootTableList.CHESTS_SIMPLE_DUNGEON);

                            break;
                        }
                    }
                }
            }

            worldIn.setBlockState(position, Blocks.SPAWNER.getDefaultState(), 2);
            TileEntity tileentity = worldIn.getTileEntity(position);

            if (tileentity instanceof TileEntityMobSpawner)
            {
             ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityType(this.pickMobSpawner(worldIn, rand, position));
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
    private EntityType<?> pickMobSpawner(IWorld worldIn, Random rand, BlockPos position)
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

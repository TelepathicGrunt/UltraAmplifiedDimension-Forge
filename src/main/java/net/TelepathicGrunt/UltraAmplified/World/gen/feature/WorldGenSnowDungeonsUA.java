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

public class WorldGenSnowDungeonsUA extends Feature<NoFeatureConfig> 
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final IBlockState CaveAir = Blocks.CAVE_AIR.getDefaultState();
    
    //only the mob spawner chance and what blocks the wall cannot replace was changed. Everything else is just the normal dungeon code.
    
    public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_)
    {
        int j = rand.nextInt(2) + 2;
        int k = -j - 1;
        int l = j + 1;
        int k1 = rand.nextInt(2) + 2;
        int l1 = -k1 - 1;
        int i2 = k1 + 1;
        int j2 = 0;

        for (int k2 = k; k2 <= l; ++k2)
        {
            for (int l2 = -1; l2 <= 4; ++l2)
            {
                for (int i3 = l1; i3 <= i2; ++i3)
                {
                    BlockPos blockpos = position.add(k2, l2, i3);
                    Material material = worldIn.getBlockState(blockpos).getMaterial();
                    boolean flag = material.isSolid();

                    if (l2 == -1 && !flag)
                    {
                        return false;
                    }

                    if (l2 == 4 && !flag)
                    {
                        return false;
                    }

                    if ((k2 == k || k2 == l || i3 == l1 || i3 == i2) && l2 == 0 && worldIn.isAirBlock(blockpos) && worldIn.isAirBlock(blockpos.up()))
                    {
                        ++j2;
                    }
                }
            }
        }

        if (j2 >= 1 && j2 <= 5)
        {
            for (int k3 = k; k3 <= l; ++k3)
            {
                for (int i4 = 3; i4 >= -1; --i4)
                {
                    for (int k4 = l1; k4 <= i2; ++k4)
                    {
                        BlockPos blockpos1 = position.add(k3, i4, k4);

                        if (k3 != k && i4 != -1 && k4 != l1 && k3 != l && i4 != 4 && k4 != i2)
                        {
                            if (worldIn.getBlockState(blockpos1).getBlock() != Blocks.CHEST && worldIn.getBlockState(blockpos1).getBlock() != Blocks.SPAWNER)
                            {
                            	 //icicles
                                if((i4 >= 2 && k4%3 == 0 && k3%2 == 0) || (i4 == 3 && k4%2 == 0 && k3%2 == 0)) {
                                    worldIn.setBlockState(blockpos1, Blocks.ICE.getDefaultState(), 2);
                                }
                                else {
                                	worldIn.setBlockState(blockpos1, CaveAir, 2);
                                }
                            }
                        }
                        else if (blockpos1.getY() >= 0 && !worldIn.getBlockState(blockpos1.down()).getMaterial().isSolid())
                        {
                            worldIn.setBlockState(blockpos1, CaveAir, 2);
                        }
                        
                        //made sure the dungeon wall cannot replace other dungeon's mob spawner now.
                        else if (worldIn.getBlockState(blockpos1).getMaterial().isSolid() && worldIn.getBlockState(blockpos1).getBlock() != Blocks.CHEST && worldIn.getBlockState(blockpos1).getBlock() != Blocks.SPAWNER)
                        {
                            if (i4 == -1 && rand.nextInt(6) == 0)
                            {
                                worldIn.setBlockState(blockpos1, Blocks.BLUE_ICE.getDefaultState(), 2);
                            }
                            else if (rand.nextInt(4) == 0)
                            {
                                worldIn.setBlockState(blockpos1, Blocks.SNOW_BLOCK.getDefaultState(), 2);
                            }
                            else
                            {
                                worldIn.setBlockState(blockpos1, Blocks.ICE.getDefaultState(), 2);
                            }
                            
                        }
                    }
                }
            }
            
            

            for (int l3 = rand.nextInt(3); l3 < 2; ++l3)
            {
                for (int j4 = 0; j4 < 3; ++j4)
                {
                    int l4 = position.getX() + rand.nextInt(j * 2 + 1) - j;
                    int i5 = position.getY();
                    int j5 = position.getZ() + rand.nextInt(k1 * 2 + 1) - k1;
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
    	
    	if(roll < 47) {
    		//47% chance
    		return pickRandomDungeonMob(rand);
    	}
    	else if(roll < 72) {
    		//25% chance
    		return EntityType.STRAY;
    	}
    	else if(roll < 97) {
    		//25% chance
    		return pickRandomDungeonMob(rand);
    	}
    	else if(roll < 98) {
    		//25% chance
    		return EntityType.POLAR_BEAR;
    	}
    	else if(roll == 98) {
    		//1% chance
    		return EntityType.CREEPER;
        }
    	else {
    		//1% chance
    		return EntityType.SNOW_GOLEM;
    	}
    }
    
    private EntityType<?> pickRandomDungeonMob(Random p_201043_1_) {
        return net.minecraftforge.common.DungeonHooks.getRandomDungeonMob(p_201043_1_);
     }
}

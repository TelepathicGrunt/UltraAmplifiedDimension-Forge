package net.telepathicgrunt.ultraamplified.World.gen.feature;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.telepathicgrunt.ultraamplified.World.Biome.BiomeInit;
import net.telepathicgrunt.ultraamplified.World.Biomes.BiomeDesertUA;
import net.telepathicgrunt.ultraamplified.World.Biomes.BiomeMesaUA;
import net.telepathicgrunt.ultraamplified.World.Biomes.BiomeSnowUA;
import net.telepathicgrunt.ultraamplified.World.Biomes.BiomeSwampUA;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.monster.EntityHusk;
import net.minecraft.entity.monster.EntityIllusionIllager;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.LootTableList;

public class WorldGenDungeonsUA extends WorldGenerator
{
    private static final Logger LOGGER = LogManager.getLogger();
    
    //only the mob spawner chance and what blocks the wall cannot replace was changed. Everything else is just the normal dungeon code.
    
    public boolean generate(World worldIn, Random rand, BlockPos position)
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
                            if (worldIn.getBlockState(blockpos1).getBlock() != Blocks.CHEST && worldIn.getBlockState(blockpos1).getBlock() != Blocks.MOB_SPAWNER)
                            {
                                worldIn.setBlockToAir(blockpos1);
                            }
                        }
                        else if (blockpos1.getY() >= 0 && !worldIn.getBlockState(blockpos1.down()).getMaterial().isSolid())
                        {
                            worldIn.setBlockToAir(blockpos1);
                        }
                        
                        //made sure the dungeon wall cannot replace other dungeon's mob spawner now.
                        else if (worldIn.getBlockState(blockpos1).getMaterial().isSolid() && worldIn.getBlockState(blockpos1).getBlock() != Blocks.CHEST && worldIn.getBlockState(blockpos1).getBlock() != Blocks.MOB_SPAWNER)
                        {
                            if (i4 == -1 && rand.nextInt(4) != 0)
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
                            worldIn.setBlockState(blockpos2, Blocks.CHEST.correctFacing(worldIn, blockpos2, Blocks.CHEST.getDefaultState()), 2);
                            TileEntity tileentity1 = worldIn.getTileEntity(blockpos2);

                            if (tileentity1 instanceof TileEntityChest)
                            {
                                ((TileEntityChest)tileentity1).setLootTable(LootTableList.CHESTS_SIMPLE_DUNGEON, rand.nextLong());
                            }

                            break;
                        }
                    }
                }
            }

            worldIn.setBlockState(position, Blocks.MOB_SPAWNER.getDefaultState(), 2);
            TileEntity tileentity = worldIn.getTileEntity(position);

            if (tileentity instanceof TileEntityMobSpawner)
            {
                ((TileEntityMobSpawner)tileentity).getSpawnerBaseLogic().setEntityId(this.pickMobSpawner(worldIn, rand, position));
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
    private ResourceLocation pickMobSpawner(World worldIn, Random rand, BlockPos position)
    {
    	//this is so we can generate certain mob spawners in certain biomes while letting other mods have a chance to add spawner mobs.
    	Biome biome = worldIn.getBiome(position);
    	
    	if(biome != BiomeInit.BiomeMushroomIsland) {
	    	int roll = rand.nextInt(100);
	    	
	    	if(roll < 48) {
	    		//48% chance
	    		
	    		if(biome == BiomeInit.BiomeNether) 
	    		{
	    			 return EntityList.getKey(EntityPigZombie.class);
	        	}
	    		else if(biome == BiomeInit.BiomeEnd) 
	    		{
	   			 return EntityList.getKey(EntityEndermite.class);
	    		}
	    		else if(biome instanceof BiomeDesertUA || biome instanceof BiomeMesaUA) 
	    		{
	      			 return EntityList.getKey(EntityHusk.class);
	       		}
	    		else 
	    		{
	        		 return net.minecraftforge.common.DungeonHooks.getRandomDungeonMob(rand);
	        	}
	    	}
	    	else if(roll < 73) {
	    		//25% chance
	    		
	    		if(biome == BiomeInit.BiomeNether) 
	    		{
	    			 return EntityList.getKey(EntityBlaze.class);
	        	}
	    		else if(biome == BiomeInit.BiomeEnd) 
	    		{
	   			 return EntityList.getKey(EntityEndermite.class);
	    		}
	    		else if(biome instanceof BiomeSnowUA) 
	    		{
	   			 return EntityList.getKey(EntityStray.class);
	    		}
	    		else if(biome instanceof BiomeSwampUA || biome == BiomeInit.BiomeRoofedForest || biome == BiomeInit.BiomeRoofedForestM) 
	    		{
	      			 return EntityList.getKey(EntityVex.class);
	       		}
	    		else 
	    		{
	        		 return net.minecraftforge.common.DungeonHooks.getRandomDungeonMob(rand);
	        	}
	    	}
	    	else if(roll < 98) {
	    		//25% chance
	    		
	    		if(biome == BiomeInit.BiomeNether) 
	    		{
	    			 return EntityList.getKey(EntityMagmaCube.class);
	        	}
	    		else if(biome == BiomeInit.BiomeEnd) 
	    		{
	   			 	 return EntityList.getKey(EntityEndermite.class);
	    		}
	    		else 
	    		{
	    			 return net.minecraftforge.common.DungeonHooks.getRandomDungeonMob(rand);
	        	}
	    	}
	    	else if(roll == 98) {
	    		//1% chance
	    		
	    		if(biome == BiomeInit.BiomeNether) 
	    		{
	    			 return EntityList.getKey(EntityWitherSkeleton.class);
	        	}
	    		else if(biome == BiomeInit.BiomeEnd) 
	    		{
	   			 	 return EntityList.getKey(EntityEnderman.class);
	    		}
	    		else if(biome instanceof BiomeSwampUA) 
	    		{
	      			 return EntityList.getKey(EntitySlime.class);
	       		}
	    		else 
	    		{
	        		 return EntityList.getKey(EntityCreeper.class);
	        	}
	        }
	    	else {
	    		//1% chance
	    		
	    		if(biome == BiomeInit.BiomeNether) 
	    		{
	    			 return EntityList.getKey(EntityWitherSkeleton.class);
	        	}
	    		else if(biome == BiomeInit.BiomeEnd) 
	    		{
	   			 	 return EntityList.getKey(EntityEnderman.class);
	    		}
	    		else if(biome instanceof BiomeSnowUA) 
	    		{
	      			 return EntityList.getKey(EntitySnowman.class);
	       		}
	    		else if(biome instanceof BiomeDesertUA || biome instanceof BiomeMesaUA) 
	    		{
	      			 return EntityList.getKey(EntityIllusionIllager.class);
	       		}
	    		else 
	    		{
	        		 return EntityList.getKey(EntityPig.class);
	        	}
	    	}
    	}
    	else {
    		//mushroom biome always get mooshroom spawners
    		 return EntityList.getKey(EntityMooshroom.class);
    	}
    }
}

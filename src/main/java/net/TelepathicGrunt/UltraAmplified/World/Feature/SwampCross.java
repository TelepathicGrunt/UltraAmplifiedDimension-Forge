package net.TelepathicGrunt.UltraAmplified.World.Feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.block.Blocks;
import net.minecraft.block.LogBlock;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.storage.loot.LootTables;

public class SwampCross extends Feature<NoFeatureConfig> {
	   public SwampCross(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> p_212245_2_, Random rand, BlockPos pos, NoFeatureConfig p_212245_5_) {
		      
		if(!ConfigUA.miniStructureGeneration) {
			return false;
		}
		   
		//creates vertical log blocks
		for (int i = -2; i < 6; i++) 
		{
			this.setBlockState(worldIn, pos.up(i), Blocks.SPRUCE_LOG.getDefaultState());
		}

		//adds horizontal log blocks towards top
		for (int i = -2; i < 3; i++) 
		{
			worldIn.setBlockState(pos.up(4).east(i), Blocks.SPRUCE_LOG.getDefaultState().with(LogBlock.AXIS, Direction.Axis.X), 16 | 2);
		}

		//adds skull underground if block is not water, lava, or air
		BlockPos positionTemp = pos.down(2).north();
		if (!worldIn.isAirBlock(positionTemp) && worldIn.getBlockState(positionTemp) != Blocks.WATER.getDefaultState() && worldIn.getBlockState(positionTemp) != Blocks.LAVA.getDefaultState()) 
		{
			if(rand.nextFloat() < 0.1F) {
				worldIn.setBlockState(positionTemp, Blocks.WITHER_SKELETON_WALL_SKULL.getDefaultState(), 2);
			}else {
				worldIn.setBlockState(positionTemp, Blocks.SKELETON_WALL_SKULL.getDefaultState(), 2);
			}
		}

		//adds hidden chest underground if block is not water, lava, air, and if next boolean is true
		positionTemp = pos.down(3);
		if (!worldIn.isAirBlock(positionTemp) && worldIn.getBlockState(positionTemp) != Blocks.WATER.getDefaultState() && worldIn.getBlockState(positionTemp) != Blocks.LAVA.getDefaultState() && rand.nextBoolean()) 
		{
			worldIn.setBlockState(positionTemp, Blocks.CHEST.getDefaultState(), 2);

			TileEntity tileentity = worldIn.getTileEntity(positionTemp);

			if (tileentity instanceof ChestTileEntity) 
			{
				((ChestTileEntity)tileentity).setLootTable(LootTables.CHESTS_SPAWN_BONUS_CHEST, rand.nextLong());
			}
		}

		return true;
	}
}
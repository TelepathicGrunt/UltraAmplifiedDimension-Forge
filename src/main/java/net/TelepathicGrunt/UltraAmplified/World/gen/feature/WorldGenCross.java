package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.storage.loot.LootTableList;

public class WorldGenCross extends Feature<NoFeatureConfig> {
	   public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> p_212245_2_, Random rand, BlockPos pos, NoFeatureConfig p_212245_5_) {
		      
		//creates vertical log blocks
		for (int i = -2; i < 6; i++) 
		{
			this.setBlockState(worldIn, pos.up(i), Blocks.SPRUCE_LOG.getDefaultState());
		}

		//adds horizontal log blocks towards top
		for (int i = -2; i < 3; i++) 
		{
			worldIn.setBlockState(pos.up(4).east(i), Blocks.SPRUCE_LOG.getDefaultState().with(BlockLog.AXIS, EnumFacing.Axis.X), 16 | 2);
		}

		//adds skull underground if block is not water, lava, or air
		BlockPos positionTemp = pos.down(2).north();
		if (!worldIn.isAirBlock(positionTemp) && worldIn.getBlockState(positionTemp) != Blocks.WATER.getDefaultState() && worldIn.getBlockState(positionTemp) != Blocks.LAVA.getDefaultState()) 
		{
			worldIn.setBlockState(positionTemp, Blocks.SKELETON_SKULL.getDefaultState(), 2);
		}

		//adds skull underground if block is not water, lava, air, and if next boolean is true
		positionTemp = pos.down(3);
		if (!worldIn.isAirBlock(positionTemp) && worldIn.getBlockState(positionTemp) != Blocks.WATER.getDefaultState() && worldIn.getBlockState(positionTemp) != Blocks.LAVA.getDefaultState() && rand.nextBoolean()) 
		{
			worldIn.setBlockState(positionTemp, Blocks.CHEST.getDefaultState(), 2);

			TileEntity tileentity = worldIn.getTileEntity(positionTemp);

			if (tileentity instanceof TileEntityChest) 
			{
				((TileEntityChest) tileentity).setLootTable(LootTableList.CHESTS_JUNGLE_TEMPLE, rand.nextLong());
			}
		}

		return true;
	}
}
package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.storage.loot.LootTableList;

public class WorldGenCross extends WorldGenerator {

	public WorldGenCross()
    {
       super(false);
    }
	
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos pos) {

		//creates vertical log blocks
		for (int i = -2; i < 6; i++) 
		{
			worldIn.setBlockState(pos.up(i), Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.Y), 16 | 2);
		}

		//adds horizontal log blocks towards top
		for (int i = -2; i < 3; i++) 
		{
			worldIn.setBlockState(pos.up(4).east(i), Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.SPRUCE).withProperty(BlockLog.LOG_AXIS, BlockLog.EnumAxis.X), 16 | 2);
		}

		//adds skull underground if block is not water, lava, or air
		BlockPos positionTemp = pos.down(2).north();
		if (!worldIn.isAirBlock(positionTemp) && worldIn.getBlockState(positionTemp) != Blocks.WATER.getDefaultState() && worldIn.getBlockState(positionTemp) != Blocks.LAVA.getDefaultState()) 
		{
			worldIn.setBlockState(positionTemp, Blocks.SKULL.getDefaultState(), 2);
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
package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.loot.LootTables;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;


public class SwampCross extends Feature<NoFeatureConfig>
{
	public SwampCross(Codec<NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, NoFeatureConfig p_212245_5_)
	{
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable().setPos(position).move(Direction.DOWN, 2);

		//creates vertical log blocks
		for (int i = 0; i < 8; i++) {
			this.setBlockState(world, blockpos$Mutable.move(Direction.UP), Blocks.SPRUCE_LOG.getDefaultState());
		}

		blockpos$Mutable.move(Direction.DOWN);
		//adds horizontal log blocks towards top
		for (int i = -2; i < 3; i++) {
			world.setBlockState(blockpos$Mutable.east(i), Blocks.SPRUCE_LOG.getDefaultState().with(RotatedPillarBlock.AXIS, Direction.Axis.X), 16 | 2);
		}

		//adds skull underground if block is not a fluid or air
		blockpos$Mutable.setPos(position).move(Direction.DOWN, 2).move(Direction.NORTH, 1);
		if (world.getBlockState(blockpos$Mutable).isSolid()) {
			if (rand.nextFloat() < 0.1F) {
				world.setBlockState(blockpos$Mutable, Blocks.WITHER_SKELETON_WALL_SKULL.getDefaultState(), 2);
			}
			else {
				world.setBlockState(blockpos$Mutable, Blocks.SKELETON_WALL_SKULL.getDefaultState(), 2);
			}
		}

		//adds hidden chest underground if block is not water, lava, air, and if next boolean is true
		blockpos$Mutable.setPos(position).move(Direction.DOWN, 3);
		if (world.getBlockState(blockpos$Mutable).isSolid() && rand.nextBoolean()) {
			world.setBlockState(blockpos$Mutable, Blocks.AIR.getDefaultState(), 2); // Reset any TE here somehow.
			world.setBlockState(blockpos$Mutable, Blocks.CHEST.getDefaultState(), 2);

			TileEntity tileentity = world.getTileEntity(blockpos$Mutable);
			if (tileentity instanceof ChestTileEntity) {
				((ChestTileEntity) tileentity).setLootTable(LootTables.CHESTS_SPAWN_BONUS_CHEST, rand.nextLong());
			}
		}

		return true;
	}
}
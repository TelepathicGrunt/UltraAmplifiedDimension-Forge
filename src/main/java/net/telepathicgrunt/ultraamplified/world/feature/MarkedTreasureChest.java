package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.storage.loot.LootTables;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;


public class MarkedTreasureChest extends Feature<NoFeatureConfig>
{

	public MarkedTreasureChest(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private static final BlockState RED_SAND = Blocks.RED_SAND.getDefaultState();


	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos position, NoFeatureConfig config)
	{

		if (!ConfigUA.chestGeneration)
		{
			return false;
		}
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);

		//surface block must be solid with water above
		if (!world.getBlockState(blockpos$Mutable).isSolid() || world.getBlockState(blockpos$Mutable.up()).getFluidState().isEmpty())
		{
			return false;
		}

		//chest position must be surrounded by solid blocks
		for (Direction face : Direction.values())
		{
			//skip above block as we already checked it
			if (face == Direction.UP)
			{
				continue;
			}

			if (!world.getBlockState(blockpos$Mutable.down().offset(face)).isSolid())
			{
				return false;
			}
		}

		//if we reached here, then the placement is good for generation.

		//creates the x marker
		int size = 5;
		for (int x = -size; x <= size; x++)
		{
			for (int z = -size; z <= size; z++)
			{
				int absx = Math.abs(x);
				int absz = Math.abs(z);

				//doesn't place red sand on corners
				if (absx == size && absz == size)
				{
					continue;
				}

				//creates a thick x shape
				if (random.nextFloat() < 0.85 && Math.abs(absx - absz) < 2)
				{
					world.setBlockState(blockpos$Mutable.setPos(position).move(x, 0, z), RED_SAND, 2);
				}
			}
		}

		blockpos$Mutable.setPos(position).move(Direction.DOWN);
		//places chest with a 50/50 split between treasure chest and end city loot
		world.setBlockState(blockpos$Mutable, StructurePiece.func_197528_a(world, blockpos$Mutable, Blocks.CHEST.getDefaultState()), 2);
		if (random.nextFloat() < 0.5f)
		{
			LockableLootTileEntity.setLootTable(world, random, blockpos$Mutable, LootTables.CHESTS_BURIED_TREASURE);
		}
		else
		{
			LockableLootTileEntity.setLootTable(world, random, blockpos$Mutable, LootTables.CHESTS_END_CITY_TREASURE);
		}
		//UltraAmplified.Logger.log(Level.DEBUG, "Marked Treasure Chest "+" | "+blockpos.getX()+" "+blockpos.getZ());

		return true;
	}
}
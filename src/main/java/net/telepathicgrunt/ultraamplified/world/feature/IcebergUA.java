package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.IcebergFeature;


public class IcebergUA extends IcebergFeature
{

	public IcebergUA(Function<Dynamic<?>, ? extends BlockStateFeatureConfig> configFactory)
	{
		super(configFactory);
	}

	private final static BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
	private final static BlockState AIR = Blocks.AIR.getDefaultState();
	private final static BlockState WATER = Blocks.WATER.getDefaultState();
	private final static BlockState STONE = Blocks.STONE.getDefaultState();
	private final static BlockState SNOW_BLOCK = Blocks.SNOW_BLOCK.getDefaultState();


	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos position, BlockStateFeatureConfig icebergConfig)
	{

		boolean flag = random.nextDouble() > 0.7D;
		BlockState iblockstate = icebergConfig.state;
		double d0 = random.nextDouble() * 2.0D * Math.PI;
		int i = 11 - random.nextInt(5);
		int j = 3 + random.nextInt(3);
		boolean flag1 = random.nextDouble() > 0.7D;
		int upperHeight = flag1 ? random.nextInt(6) + 6 : random.nextInt(15) + 3;
		if (!flag1 && random.nextDouble() > 0.9D)
		{
			upperHeight += random.nextInt(19) + 7;
		}

		int downHeight = Math.min(upperHeight + random.nextInt(11), 18);
		int maxheight = Math.min(upperHeight + random.nextInt(7) - random.nextInt(5), 11);
		int radius = flag1 ? i : 11;

		for (int x = -radius; x < radius; ++x)
		{
			for (int z = -radius; z < radius; ++z)
			{
				for (int y = 0; y < upperHeight; ++y)
				{
					int k2 = flag1 ? this.func_205178_b(y, upperHeight, maxheight) : this.func_205183_a(random, y, upperHeight, maxheight);
					if (flag1 || x < k2)
					{
						this.func_205181_a(world, random, position, upperHeight, x, y, z, k2, radius, flag1, j, d0, flag, iblockstate);
					}
				}
			}
		}

		this.func_205186_a(world, position, maxheight, upperHeight, flag1, i);

		for (int x = -radius; x < radius; ++x)
		{
			for (int z = -radius; z < radius; ++z)
			{
				for (int y = -1; y > -downHeight; --y)
				{
					int l3 = flag1 ? MathHelper.ceil((float) radius * (1.0F - (float) Math.pow((double) y, 2.0D) / ((float) downHeight * 8.0F))) : radius;
					int l2 = this.func_205187_b(random, -y, downHeight, maxheight);
					if (x < l2)
					{
						this.func_205181_a(world, random, position, downHeight, x, y, z, l2, l3, flag1, j, d0, flag, iblockstate);
					}
				}
			}
		}

		boolean flag2 = flag1 ? random.nextDouble() > 0.1D : random.nextDouble() > 0.7D;
		if (flag2)
		{
			this.func_205184_a(random, world, maxheight, upperHeight, position, flag1, i, d0, j);
		}

		return true;
	}


	private void func_205184_a(Random random, IWorld world, int maxheight, int height, BlockPos position, boolean flag, int int1, double double1, int int2)
	{
		int x = random.nextBoolean() ? -1 : 1;
		int z = random.nextBoolean() ? -1 : 1;
		int randomHeightBasedMultiplier = random.nextInt(Math.max(maxheight / 2 - 2, 1));
		if (random.nextBoolean())
		{
			randomHeightBasedMultiplier = maxheight / 2 + 1 - random.nextInt(Math.max(maxheight - maxheight / 2 - 1, 1));
		}

		int l = random.nextInt(Math.max(maxheight / 2 - 2, 1));
		if (random.nextBoolean())
		{
			l = maxheight / 2 + 1 - random.nextInt(Math.max(maxheight - maxheight / 2 - 1, 1));
		}

		if (flag)
		{
			randomHeightBasedMultiplier = l = random.nextInt(Math.max(int1 - 5, 1));
		}

		BlockPos blockpos = new BlockPos(x * randomHeightBasedMultiplier, 0, z * l);
		double double2 = flag ? double1 + (Math.PI / 2D) : random.nextDouble() * 2.0D * Math.PI;

		for (int i1 = 0; i1 < height - 3; ++i1)
		{
			int j1 = this.func_205183_a(random, i1, height, maxheight);
			this.func_205174_a(j1, i1, position, world, false, double2, blockpos, int1, int2);
		}

		for (int belowCenterY = -1; belowCenterY > -height + random.nextInt(5); --belowCenterY)
		{
			int randomY = this.func_205187_b(random, -belowCenterY, height, maxheight);
			this.func_205174_a(randomY, belowCenterY, position, world, true, double2, blockpos, int1, int2);
		}

	}


	private void func_205174_a(int randomY, int belowCenterY, BlockPos position, IWorld world, boolean placeWater, double double1, BlockPos pos2, int int1, int int2)
	{
		int radius = randomY + 1 + int1 / 3;
		int int3 = Math.min(randomY - 3, 3) + int2 / 2 - 1;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);
		Block block;

		for (int x = -radius; x < radius; ++x)
		{
			for (int z = -radius; z < radius; ++z)
			{
				double d0 = this.func_205180_a(x, z, pos2, radius, int3, double1);
				if (d0 < 0.0D)
				{
					blockpos$Mutable.setPos(position).move(x, belowCenterY, z);
					block = world.getBlockState(blockpos$Mutable).getBlock();
					if (this.isIce(block) || block == Blocks.SNOW_BLOCK)
					{
						if (placeWater)
						{
							world.setBlockState(blockpos$Mutable, appropriateBlockForNeighbors(world, position), 3);
						}
						else
						{
							this.setBlockState(world, blockpos$Mutable, appropriateBlockForNeighbors(world, position));
							this.removeSnowLayer(world, blockpos$Mutable);
						}
					}
				}
			}
		}

	}


	private void removeSnowLayer(IWorld world, BlockPos.Mutable mutableBlockPos)
	{
		mutableBlockPos.move(Direction.UP);
		if (world.getBlockState(mutableBlockPos).getBlock() == Blocks.SNOW)
		{
			this.setBlockState(world, mutableBlockPos, appropriateBlockForNeighbors(world, mutableBlockPos.down()));
		}
	}


	private void func_205181_a(IWorld world, Random random, BlockPos position, int maxHeight, int xPos, int yPos, int zPos, int int1, int int2, boolean flag1, int int3, double double1, boolean flag2, BlockState defaultState)
	{
		double d0 = flag1 ? this.func_205180_a(xPos, zPos, BlockPos.ZERO, int2, this.func_205176_a(yPos, maxHeight, int3), double1) : this.func_205177_a(xPos, zPos, BlockPos.ZERO, int1, random);
		if (d0 < 0.0D)
		{
			BlockPos blockpos1 = position.add(xPos, yPos, zPos);
			double d1 = flag1 ? -0.5D : (double) (-6 - random.nextInt(3));
			if (d0 > d1 && random.nextDouble() > 0.9D)
			{
				return;
			}

			this.func_205175_a(blockpos1, world, random, maxHeight - yPos, maxHeight, flag1, flag2, defaultState);
		}

	}


	private void func_205175_a(BlockPos position, IWorld world, Random random, int minHeight, int maxHeight, boolean flag1, boolean flag2, BlockState defaultState)
	{
		BlockState iblockstate = world.getBlockState(position);
		Block block = iblockstate.getBlock();

		if (iblockstate.getMaterial() == Material.AIR || block == Blocks.SNOW_BLOCK || block == Blocks.ICE || block == Blocks.WATER || block == Blocks.LAVA)
		{
			boolean flag = !flag1 || random.nextDouble() > 0.05D;
			int i = flag1 ? 3 : 2;
			if (flag2 && block != Blocks.WATER || block != Blocks.LAVA && (double) minHeight <= (double) random.nextInt(Math.max(1, maxHeight / i)) + (double) maxHeight * 0.6D && flag)
			{
				this.setBlockState(world, position, SNOW_BLOCK);
			}
			else
			{
				this.setBlockState(world, position, defaultState);
			}
		}

	}


	private BlockState appropriateBlockForNeighbors(IWorld world, BlockPos position)
	{

		boolean bordersWater = false;
		boolean bordersAir = false;
		BlockState iblockstate;
		BlockState[] blockArray = new BlockState[5];
		int i = 0;
		//detects what blocks are adjacent
		for (Direction face : Direction.values())
		{

			if (face == Direction.UP)
			{
				continue;
			}

			iblockstate = world.getBlockState(position.offset(face));
			blockArray[i] = iblockstate;
			i++;
			if (!isIce(iblockstate.getBlock()))
			{
				if (iblockstate.getMaterial() == Material.AIR)
				{
					bordersAir = true;
				}
				else if (!iblockstate.getFluidState().isEmpty())
				{
					bordersWater = true;
				}
			}
		}

		if (bordersWater && bordersAir)
		{
			return STONE;
		}
		else if (bordersWater)
		{
			return WATER;
		}
		else if (bordersAir)
		{
			return AIR;
		}
		else
		{
			return PACKED_ICE;
		}
	}


	private void func_205186_a(IWorld world, BlockPos position, int smallRadiusIn, int height, boolean flag, int largeRadiusIn)
	{
		int radius = flag ? largeRadiusIn : smallRadiusIn / 2;
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);

		for (int x = -radius; x <= radius; ++x)
		{
			for (int z = -radius; z <= radius; ++z)
			{
				for (int y = 0; y <= height; ++y)
				{
					blockpos$Mutable.setPos(position).move(x, y, z);
					Block block = world.getBlockState(blockpos$Mutable).getBlock();
					if (this.isIce(block) || block == Blocks.SNOW)
					{
						if (isAirBellow(world, blockpos$Mutable))
						{
							this.setBlockState(world, blockpos$Mutable, appropriateBlockForNeighbors(world, blockpos$Mutable));
							this.setBlockState(world, blockpos$Mutable.up(), appropriateBlockForNeighbors(world, blockpos$Mutable));
						}
						else if (this.isIce(block))
						{
							Block[] ablock = new Block[] { world.getBlockState(blockpos$Mutable.west()).getBlock(), world.getBlockState(blockpos$Mutable.east()).getBlock(), world.getBlockState(blockpos$Mutable.north()).getBlock(), world.getBlockState(blockpos$Mutable.south()).getBlock() };
							int notIceCounter = 0;

							for (Block block1 : ablock)
							{
								if (!this.isIce(block1))
								{
									++notIceCounter;
								}
							}

							if (notIceCounter >= 3)
							{
								this.setBlockState(world, blockpos$Mutable, appropriateBlockForNeighbors(world, blockpos$Mutable));
							}
						}
					}
				}
			}
		}
	}
}
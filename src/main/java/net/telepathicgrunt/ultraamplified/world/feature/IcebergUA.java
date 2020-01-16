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
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.BlockStateFeatureConfig;
import net.minecraft.world.gen.feature.Feature;


public class IcebergUA extends Feature<BlockStateFeatureConfig>
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


	private void func_205184_a(Random random, IWorld world, int maxheight, int height, BlockPos pos, boolean flag, int p_205184_7_, double p_205184_8_, int p_205184_10_)
	{
		int i = random.nextBoolean() ? -1 : 1;
		int j = random.nextBoolean() ? -1 : 1;
		int k = random.nextInt(Math.max(maxheight / 2 - 2, 1));
		if (random.nextBoolean())
		{
			k = maxheight / 2 + 1 - random.nextInt(Math.max(maxheight - maxheight / 2 - 1, 1));
		}

		int l = random.nextInt(Math.max(maxheight / 2 - 2, 1));
		if (random.nextBoolean())
		{
			l = maxheight / 2 + 1 - random.nextInt(Math.max(maxheight - maxheight / 2 - 1, 1));
		}

		if (flag)
		{
			k = l = random.nextInt(Math.max(p_205184_7_ - 5, 1));
		}

		BlockPos blockpos = (new BlockPos(0, 0, 0)).add(i * k, 0, j * l);
		double d0 = flag ? p_205184_8_ + (Math.PI / 2D) : random.nextDouble() * 2.0D * Math.PI;

		for (int i1 = 0; i1 < height - 3; ++i1)
		{
			int j1 = this.func_205183_a(random, i1, height, maxheight);
			this.func_205174_a(j1, i1, pos, world, false, d0, blockpos, p_205184_7_, p_205184_10_);
		}

		for (int belowCenterY = -1; belowCenterY > -height + random.nextInt(5); --belowCenterY)
		{
			int l1 = this.func_205187_b(random, -belowCenterY, height, maxheight);
			this.func_205174_a(l1, belowCenterY, pos, world, true, d0, blockpos, p_205184_7_, p_205184_10_);
		}

	}


	private void func_205174_a(int p_205174_1_, int p_205174_2_, BlockPos pos, IWorld world, boolean placeWater, double p_205174_6_, BlockPos pos2, int p_205174_9_, int p_205174_10_)
	{
		int i = p_205174_1_ + 1 + p_205174_9_ / 3;
		int j = Math.min(p_205174_1_ - 3, 3) + p_205174_10_ / 2 - 1;

		for (int k = -i; k < i; ++k)
		{
			for (int l = -i; l < i; ++l)
			{
				double d0 = this.func_205180_a(k, l, pos2, i, j, p_205174_6_);
				if (d0 < 0.0D)
				{
					BlockPos blockpos = pos.add(k, p_205174_2_, l);
					Block block = world.getBlockState(blockpos).getBlock();
					if (this.isIce(block) || block == Blocks.SNOW_BLOCK)
					{
						if (placeWater)
						{
							world.setBlockState(blockpos, appropriateBlockForNeighbors(world, pos), 3);
						}
						else
						{
							this.setBlockState(world, blockpos, appropriateBlockForNeighbors(world, pos));
							this.removeSnowLayer(world, blockpos);
						}
					}
				}
			}
		}

	}


	private void removeSnowLayer(IWorld world, BlockPos pos)
	{
		if (world.getBlockState(pos.up()).getBlock() == Blocks.SNOW)
		{
			this.setBlockState(world, pos.up(), appropriateBlockForNeighbors(world, pos));
		}

	}


	private void func_205181_a(IWorld p_205181_1_, Random p_205181_2_, BlockPos p_205181_3_, int p_205181_4_, int xPos, int yPos, int zPos, int p_205181_8_, int p_205181_9_, boolean p_205181_10_, int p_205181_11_, double p_205181_12_, boolean p_205181_14_, BlockState p_205181_15_)
	{
		BlockPos blockpos = new BlockPos(0, 0, 0);
		double d0 = p_205181_10_ ? this.func_205180_a(xPos, zPos, blockpos, p_205181_9_, this.func_205176_a(yPos, p_205181_4_, p_205181_11_), p_205181_12_) : this.func_205177_a(xPos, zPos, blockpos, p_205181_8_, p_205181_2_);
		if (d0 < 0.0D)
		{
			BlockPos blockpos1 = p_205181_3_.add(xPos, yPos, zPos);
			double d1 = p_205181_10_ ? -0.5D : (double) (-6 - p_205181_2_.nextInt(3));
			if (d0 > d1 && p_205181_2_.nextDouble() > 0.9D)
			{
				return;
			}

			this.func_205175_a(blockpos1, p_205181_1_, p_205181_2_, p_205181_4_ - yPos, p_205181_4_, p_205181_10_, p_205181_14_, p_205181_15_);
		}

	}


	private void func_205175_a(BlockPos p_205175_1_, IWorld p_205175_2_, Random p_205175_3_, int p_205175_4_, int p_205175_5_, boolean p_205175_6_, boolean p_205175_7_, BlockState p_205175_8_)
	{
		BlockState iblockstate = p_205175_2_.getBlockState(p_205175_1_);
		Block block = iblockstate.getBlock();
		if (iblockstate.getMaterial() == Material.AIR || block == Blocks.SNOW_BLOCK || block == Blocks.ICE || block == Blocks.WATER || block == Blocks.LAVA)
		{
			boolean flag = !p_205175_6_ || p_205175_3_.nextDouble() > 0.05D;
			int i = p_205175_6_ ? 3 : 2;
			if (p_205175_7_ && block != Blocks.WATER || block != Blocks.LAVA && (double) p_205175_4_ <= (double) p_205175_3_.nextInt(Math.max(1, p_205175_5_ / i)) + (double) p_205175_5_ * 0.6D && flag)
			{
				this.setBlockState(p_205175_2_, p_205175_1_, SNOW_BLOCK);
			}
			else
			{
				this.setBlockState(p_205175_2_, p_205175_1_, p_205175_8_);
			}
		}

	}


	private int func_205176_a(int belowCenterY, int height, int maxheight)
	{
		int i = maxheight;
		if (belowCenterY > 0 && height - belowCenterY <= 3)
		{
			i = maxheight - (4 - (height - belowCenterY));
		}

		return i;
	}


	private double func_205177_a(int p_205177_1_, int p_205177_2_, BlockPos p_205177_3_, int p_205177_4_, Random p_205177_5_)
	{
		float f = 10.0F * MathHelper.clamp(p_205177_5_.nextFloat(), 0.2F, 0.8F) / (float) p_205177_4_;
		return (double) f + Math.pow((double) (p_205177_1_ - p_205177_3_.getX()), 2.0D) + Math.pow((double) (p_205177_2_ - p_205177_3_.getZ()), 2.0D) - Math.pow((double) p_205177_4_, 2.0D);
	}


	private double func_205180_a(int p_205180_1_, int p_205180_2_, BlockPos p_205180_3_, int p_205180_4_, int p_205180_5_, double p_205180_6_)
	{
		return Math.pow(((double) (p_205180_1_ - p_205180_3_.getX()) * Math.cos(p_205180_6_) - (double) (p_205180_2_ - p_205180_3_.getZ()) * Math.sin(p_205180_6_)) / (double) p_205180_4_, 2.0D) + Math.pow(((double) (p_205180_1_ - p_205180_3_.getX()) * Math.sin(p_205180_6_) + (double) (p_205180_2_ - p_205180_3_.getZ()) * Math.cos(p_205180_6_)) / (double) p_205180_5_, 2.0D) - 1.0D;
	}


	private int func_205183_a(Random p_205183_1_, int p_205183_2_, int p_205183_3_, int p_205183_4_)
	{
		float f = 3.5F - p_205183_1_.nextFloat();
		float f1 = (1.0F - (float) Math.pow((double) p_205183_2_, 2.0D) / ((float) p_205183_3_ * f)) * (float) p_205183_4_;
		if (p_205183_3_ > 15 + p_205183_1_.nextInt(5))
		{
			int i = p_205183_2_ < 3 + p_205183_1_.nextInt(6) ? p_205183_2_ / 2 : p_205183_2_;
			f1 = (1.0F - (float) i / ((float) p_205183_3_ * f * 0.4F)) * (float) p_205183_4_;
		}

		return MathHelper.ceil(f1 / 2.0F);
	}


	private int func_205178_b(int y, int upperHeight, int p_205178_3_)
	{
		float f1 = (1.0F - (float) Math.pow((double) y, 2.0D) / ((float) upperHeight * 1.0F)) * (float) p_205178_3_;
		return MathHelper.ceil(f1 / 2.0F);
	}


	private int func_205187_b(Random random, int p_205187_2_, int p_205187_3_, int p_205187_4_)
	{
		float f = 1.0F + random.nextFloat() / 2.0F;
		float f1 = (1.0F - (float) p_205187_2_ / ((float) p_205187_3_ * f)) * (float) p_205187_4_;
		return MathHelper.ceil(f1 / 2.0F);
	}


	private boolean isIce(Block block)
	{
		return block == Blocks.PACKED_ICE || block == Blocks.SNOW_BLOCK || block == Blocks.BLUE_ICE;
	}


	private boolean isAirBelow(IBlockReader reader, BlockPos position)
	{
		return reader.getBlockState(position.down()).getMaterial() == Material.AIR;
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
			for (int y = -radius; y <= radius; ++y)
			{
				for (int z = 0; z <= height; ++z)
				{
            		blockpos$Mutable.setPos(position).move(x, y, z);
					Block block = world.getBlockState(blockpos$Mutable).getBlock();
					if (this.isIce(block) || block == Blocks.SNOW)
					{
						if (this.isAirBelow(world, blockpos$Mutable))
						{
							this.setBlockState(world, blockpos$Mutable, appropriateBlockForNeighbors(world, blockpos$Mutable));
							this.setBlockState(world, blockpos$Mutable.up(), appropriateBlockForNeighbors(world, blockpos$Mutable));
						}
						else if (this.isIce(block))
						{
							Block[] ablock = new Block[] { 
								world.getBlockState(blockpos$Mutable.west()).getBlock(), 
								world.getBlockState(blockpos$Mutable.east()).getBlock(), 
								world.getBlockState(blockpos$Mutable.north()).getBlock(), 
								world.getBlockState(blockpos$Mutable.south()).getBlock() 
							};
							int i1 = 0;

							for (Block block1 : ablock)
							{
								if (!this.isIce(block1))
								{
									++i1;
								}
							}

							if (i1 >= 3)
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
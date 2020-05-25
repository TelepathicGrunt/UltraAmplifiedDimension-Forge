package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.HugeTreeFeatureConfig;
import net.minecraft.world.gen.feature.HugeTreesFeature;


public class TreeGiantPine extends HugeTreesFeature<HugeTreeFeatureConfig>
{

	private final boolean useBaseHeight;


	//only change is significant increase in possible height and thicker/bigger leaves so trees fit the terrain more.
	public TreeGiantPine(Function<Dynamic<?>, ? extends HugeTreeFeatureConfig> p_i225808_1_, boolean useBaseHeightIn)
	{
		super(p_i225808_1_);
		useBaseHeight = useBaseHeightIn;
	}


	@Override
	public boolean place(IWorldGenerationReader worldReader, Random rand, BlockPos position, Set<BlockPos> leavesSet, Set<BlockPos> trunkSet, MutableBoundingBox boundingBox, HugeTreeFeatureConfig config)
	{
		int height = this.func_227256_a_(rand, config);
		IWorld world = (IWorld) worldReader;

		if (!this.hasRoom(world, position, height + 8, config))
		{
			return false;
		}
		else
		{
			this.createCrown(world, position.getX(), position.getZ(), position.getY() + height, 0, rand, boundingBox, trunkSet, config);

			for (int currentHeight = 0; currentHeight < height; ++currentHeight)
			{
				BlockState iblockstate = world.getBlockState(position.up(currentHeight));

				if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
				{
					this.func_227216_a_(world, rand, position.up(currentHeight), leavesSet, boundingBox, config);
				}

				if (currentHeight < height - 1)
				{
					iblockstate = world.getBlockState(position.add(1, currentHeight, 0));

					if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
					{
						this.func_227216_a_(world, rand, position.add(1, currentHeight, 0), leavesSet, boundingBox, config);
					}

					iblockstate = world.getBlockState(position.add(1, currentHeight, 1));

					if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
					{
						this.func_227216_a_(world, rand, position.add(1, currentHeight, 1), leavesSet, boundingBox, config);
					}

					iblockstate = world.getBlockState(position.add(0, currentHeight, 1));

					if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
					{
						this.func_227216_a_(world, rand, position.add(0, currentHeight, 1), leavesSet, boundingBox, config);
					}
				}
			}

			return true;
		}
	}


	private void createCrown(IWorldGenerationReader world, int x, int z, int y, int extraRadiusSize, Random rand, MutableBoundingBox p_214596_7_, Set<BlockPos> p_214596_8_, HugeTreeFeatureConfig p_225557_7_)
	{
		int height = rand.nextInt(5) + (this.useBaseHeight ? p_225557_7_.baseHeight : 3);
		int prevRadius = 0;

		for (int currentHeight = y - height; currentHeight <= y + 20; ++currentHeight)
		{
			int heightDiff = y - currentHeight;
			int radius = extraRadiusSize + MathHelper.floor((float) heightDiff / (float) height * 3.5F);
			this.func_227255_a_(world, rand, new BlockPos(x, currentHeight, z), radius + (heightDiff > 0 && radius == prevRadius && (currentHeight & 1) == 0 ? 1 : 0) * 2, p_214596_8_, p_214596_7_, p_225557_7_);
			prevRadius = radius;
		}
	}
}

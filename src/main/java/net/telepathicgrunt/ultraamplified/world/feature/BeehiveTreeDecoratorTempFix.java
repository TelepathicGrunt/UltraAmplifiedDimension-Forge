package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.tileentity.BeehiveTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;


public class BeehiveTreeDecoratorTempFix extends TreeDecorator
{
	private final float chance;


	public BeehiveTreeDecoratorTempFix(float chance)
	{
		super(TreeDecoratorType.field_227428_d_);
		this.chance = chance;
	}


	public <T> BeehiveTreeDecoratorTempFix(Dynamic<T> property)
	{
		this(property.get("probability").asFloat(0.0F));
	}


	public void generate(IWorld world, Random random, List<BlockPos> leavesPositionList, List<BlockPos> trunkPositionList, Set<BlockPos> beehivePositionList, MutableBoundingBox mutableBoundingBox)
	{
		if (!(random.nextFloat() >= this.chance))
		{
			Direction direction = BeehiveBlock.GENERATE_DIRECTIONS[random.nextInt(BeehiveBlock.GENERATE_DIRECTIONS.length)];
			int chosenBeehiveHeight = !trunkPositionList.isEmpty() ? Math.max(trunkPositionList.get(0).getY() - 1, leavesPositionList.get(0).getY()) : Math.min(leavesPositionList.get(0).getY() + 1 + random.nextInt(3), leavesPositionList.get(leavesPositionList.size() - 1).getY());
			
			List<BlockPos> listOfChosenPositions = leavesPositionList.stream().filter((positionAtHeight) ->
			{
				return positionAtHeight.getY() == chosenBeehiveHeight;
			}).collect(Collectors.toList());
			
				
			BlockPos blockpos;
			//Fix very rare bug where list could be empty and cause a crash when doing next int
			if(!listOfChosenPositions.isEmpty()) 
			{
				blockpos = listOfChosenPositions.get(random.nextInt(listOfChosenPositions.size()));
			}
			else 
			{
				return; //would've crashed because position is actually invalid
			}
			
			
			BlockPos blockpos1 = blockpos.offset(direction);
			if (AbstractTreeFeature.isAir(world, blockpos1) && AbstractTreeFeature.isAir(world, blockpos1.offset(Direction.SOUTH)))
			{
				BlockState blockstate = Blocks.field_226905_ma_.getDefaultState().with(BeehiveBlock.FACING, Direction.SOUTH);
				this.func_227423_a_(world, blockpos1, blockstate, beehivePositionList, mutableBoundingBox);
				TileEntity tileentity = world.getTileEntity(blockpos1);
				if (tileentity instanceof BeehiveTileEntity)
				{
					BeehiveTileEntity beehivetileentity = (BeehiveTileEntity) tileentity;
					int j = 2 + random.nextInt(2);

					for (int k = 0; k < j; ++k)
					{
						BeeEntity beeentity = new BeeEntity(EntityType.field_226289_e_, world.getWorld());
						beehivetileentity.tryEnterHive(beeentity, false, random.nextInt(599));
					}
				}

			}
		}
	}


	public <T> T serialize(DynamicOps<T> p_218175_1_)
	{
		return (new Dynamic<>(p_218175_1_, p_218175_1_.createMap(ImmutableMap.of(p_218175_1_.createString("type"), p_218175_1_.createString(Registry.field_229390_w_.getKey(this.field_227422_a_).toString()), p_218175_1_.createString("probability"), p_218175_1_.createFloat(this.chance))))).getValue();
	}
}
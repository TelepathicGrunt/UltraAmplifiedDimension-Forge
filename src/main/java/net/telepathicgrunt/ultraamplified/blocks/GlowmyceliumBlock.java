package net.telepathicgrunt.ultraamplified.blocks;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MyceliumBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class GlowmyceliumBlock extends MyceliumBlock
{

	public GlowmyceliumBlock()
	{
		super(Properties.create(Material.ORGANIC, MaterialColor.PURPLE).tickRandomly().hardnessAndResistance(0.4F).sound(SoundType.PLANT).lightValue(15));
		setRegistryName("glowmycelium");
	}


	/*
	 * every tick, it'll attempt to spread normal mycelium instead of itself. If covered, will turn into glowdirt.
	 */
	@Override
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random)
	{
		if (!worldIn.isRemote)
		{
			if (!worldIn.isAreaLoaded(pos, 3))
				return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			if (!func_220257_b(state, worldIn, pos))
			{
				//block is covered and so will turn into glowdirt
				worldIn.setBlockState(pos, BlocksInit.GLOWDIRT.getDefaultState());
			}
			else if (worldIn.getLight(pos.up()) >= 4)
			{
				if (worldIn.getLight(pos.up()) >= 9)
				{
					//attempt to spread mycelium onto neighboring dirt (glowdirt handles its own conversion)
					BlockState replacementBlock = Blocks.MYCELIUM.getDefaultState();

					for (int i = 0; i < 4; ++i)
					{
						BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
						if (worldIn.getBlockState(blockpos).getBlock() == Blocks.DIRT && func_220256_c(replacementBlock, worldIn, blockpos))
						{
							worldIn.setBlockState(blockpos,
									replacementBlock.with(SNOWY, Boolean.valueOf(worldIn.getBlockState(blockpos.up()).getBlock() == Blocks.SNOW)));
						}
					}
				}

			}
		}
	}
}

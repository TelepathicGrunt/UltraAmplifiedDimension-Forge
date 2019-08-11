package net.TelepathicGrunt.UltraAmplified.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.lighting.LightEngine;

public class GlowdirtBlock extends Block {

	public GlowdirtBlock() {
		super(Properties.create(Material.EARTH, MaterialColor.DIRT).tickRandomly().hardnessAndResistance(0.4F).sound(SoundType.GROUND).lightValue(15));
		setRegistryName("glowdirt");
	}

	public static boolean validLightAndSpacing(BlockState p_220257_0_, IWorldReader p_220257_1_, BlockPos p_220257_2_) {
		BlockPos blockpos = p_220257_2_.up();
		BlockState blockstate = p_220257_1_.getBlockState(blockpos);
		if (blockstate.getBlock() == Blocks.SNOW && blockstate.get(SnowBlock.LAYERS) == 1) {
			return true;
		} else {
			int i = LightEngine.func_215613_a(p_220257_1_, p_220257_0_, p_220257_2_, blockstate, blockpos, Direction.UP, blockstate.getOpacity(p_220257_1_, blockpos));
			return i < p_220257_1_.getMaxLightLevel();
		}
	}

	public static boolean validNeighboringBlockSpace(BlockState p_220256_0_, IWorldReader p_220256_1_, BlockPos p_220256_2_) {
		BlockPos blockpos = p_220256_2_.up();
		return validLightAndSpacing(p_220256_0_, p_220256_1_, p_220256_2_) && !p_220256_1_.getFluidState(blockpos).isTagged(FluidTags.WATER);
	}

	// checks to see if there is a nearby grass block, glowgrass block, mycelium, or
	// glow mycelium and will transform into glowgrass block or mycelium block
	public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
		if (!worldIn.isRemote) {
			if (!worldIn.isAreaLoaded(pos, 3))
				return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
			
			if (worldIn.getLight(pos.up()) >= 4) {
				if (worldIn.getLight(pos.up()) >= 9) {
					BlockState replacementBlock = BlocksInit.GLOWGRASS.getDefaultState();

					for (int i = 0; i < 4; ++i) {
						BlockPos blockpos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
						Block neighborBlock = worldIn.getBlockState(blockpos).getBlock();
						
						if ((neighborBlock == Blocks.GRASS_BLOCK || neighborBlock == BlocksInit.GLOWGRASS) && validNeighboringBlockSpace(replacementBlock, worldIn, blockpos)) {
							replacementBlock = BlocksInit.GLOWGRASS.getDefaultState();
							worldIn.setBlockState(pos, replacementBlock.with(SnowyDirtBlock.SNOWY, Boolean.valueOf(worldIn.getBlockState(blockpos.up()).getBlock() == Blocks.SNOW)));
						}
						else if((neighborBlock == Blocks.MYCELIUM || neighborBlock == BlocksInit.GLOWMYCELIUM) && validNeighboringBlockSpace(replacementBlock, worldIn, blockpos)) {
							replacementBlock = BlocksInit.GLOWMYCELIUM.getDefaultState();
							worldIn.setBlockState(pos, replacementBlock.with(SnowyDirtBlock.SNOWY, Boolean.valueOf(worldIn.getBlockState(blockpos.up()).getBlock() == Blocks.SNOW)));
						}
					}
				}
			}
		}
	}
}

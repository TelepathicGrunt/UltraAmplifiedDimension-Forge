package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;


public class NetherSurfaceLavaRare extends Feature<NoFeatureConfig>
{
	private static final BlockState LAVA = Blocks.LAVA.getDefaultState();
	private static final FlowingFluid LAVA_FLUID = Fluids.LAVA;
	private static final BlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
	private static final BlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
	private static final BlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
	
	public final Set<Block> acceptableSurroundingBlocks;
	
	public NetherSurfaceLavaRare(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51430_1_)
	{
		super(p_i51430_1_);
		
		acceptableSurroundingBlocks = ImmutableSet.of(
											Blocks.NETHERRACK, 
											Blocks.GRAVEL, 
											Blocks.SOUL_SAND, 
											Blocks.MAGMA_BLOCK, 
											Blocks.NETHER_QUARTZ_ORE, 
											Blocks.NETHER_BRICKS);
	}


	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config)
	{

		BlockState blockstate = worldIn.getBlockState(pos);
		boolean generateLava = false;
        int solidSurrounding = 0;

        if (acceptableSurroundingBlocks.contains(worldIn.getBlockState(pos.west()).getBlock())) {
           ++solidSurrounding;
        }

        if (acceptableSurroundingBlocks.contains(worldIn.getBlockState(pos.east()).getBlock())) {
           ++solidSurrounding;
        }

        if (acceptableSurroundingBlocks.contains(worldIn.getBlockState(pos.north()).getBlock())) {
           ++solidSurrounding;
        }

        if (acceptableSurroundingBlocks.contains(worldIn.getBlockState(pos.south()).getBlock())) {
           ++solidSurrounding;
        }
        
        //not enough solid blocks surrounding area to generate lava
        if(solidSurrounding < 3) {
        	return false;
        }
		
		//full chance to generate in gravel
		if(blockstate == GRAVEL) {
	        if (acceptableSurroundingBlocks.contains(worldIn.getBlockState(pos.down()).getBlock())) {
	        	//can only generate in gravel if below is also a solid block to prevent
	        	//lava spawning in 1 thick gravel which causes the gravel to fall,
	        	//leaving a pilalr of lava floating in mid-air which looks bad.
				generateLava = true;
	        }
		}
		//1/3rd chance to generate in soulsand
		else if(blockstate == SOUL_SAND) {
			if(rand.nextFloat() < 0.33) {
				generateLava = true;
			}
		}
		//1/30th chance to generate in netherrack
		else if(blockstate == NETHERRACK) {
			if(rand.nextFloat() < 0.033) {
				generateLava = true;
			}
		}
		
		//generates surface lava that can flow
		if(generateLava) {
			worldIn.setBlockState(pos, LAVA, 2);
			worldIn.getPendingFluidTicks().scheduleTick(pos, LAVA_FLUID, 0);
		}
		return true;
	}
}
package net.TelepathicGrunt.UltraAmplified.World.Feature.StructureUA;

import com.mojang.datafixers.Dynamic;
import net.TelepathicGrunt.UltraAmplified.Config.ConfigUA;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HayBlock;
import net.minecraft.block.RailBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.RailShape;
import net.minecraft.util.Direction;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.OctavesNoiseGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.time.ZoneOffset;
import java.util.Random;
import java.util.function.Function;

public class Roots extends Feature<NoFeatureConfig> {
    public Roots(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}


	private final BlockState ROOT_BLOCK = Blocks.STRIPPED_OAK_LOG.getDefaultState();

	private final float[] field_202536_i = new float[1024];
	protected long seed;
	protected OctavesNoiseGenerator noiseGen;

	public void setSeed(long seed) {
		if (this.noiseGen == null) {
			this.noiseGen = new OctavesNoiseGenerator(new SharedSeedRandom(seed), 4);
		}

		this.seed = seed;
	}


    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_)
    {
		setSeed(rand.nextLong());
		position = position.down();
		BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();

		int xOffset;
		int zOffset;
		int yOffset;

		for(int rootNum = 1; rootNum < 5; rootNum++){
			mutableBlockPos.setPos(position);

			//attempts to grow root as long as the new position is an air block. Otherwise, terminate root
			for(int length = 0; length < 8; length++){
				worldIn.setBlockState(mutableBlockPos, ROOT_BLOCK, 2);

				//range is clamped to -1 to 1 due to int rounding
				xOffset = (int)MathHelper.clamp(this.noiseGen.func_205563_a(mutableBlockPos.getX() * 2D+20000*rootNum, mutableBlockPos.getZ() * 2D+20000*rootNum, mutableBlockPos.getY()*1D+20000*rootNum), -1, 1);
				zOffset = (int)MathHelper.clamp(this.noiseGen.func_205563_a(mutableBlockPos.getX() * 2D+10000*rootNum, mutableBlockPos.getZ() * 2D+10000*rootNum, mutableBlockPos.getY()*1D+10000*rootNum), -1, 1);
				yOffset = (int)MathHelper.clamp(this.noiseGen.func_205563_a(mutableBlockPos.getX() * 2D-10000*rootNum, mutableBlockPos.getZ() * 2D-10000*rootNum, mutableBlockPos.getY()*1D-10000)*rootNum, -1, 1);

				System.out.println(xOffset +", "+zOffset+", "+yOffset);
				mutableBlockPos.add(xOffset, yOffset, zOffset);
				if(worldIn.getBlockState(mutableBlockPos).getMaterial() != Material.AIR && worldIn.getBlockState(mutableBlockPos) != ROOT_BLOCK){
					break;
				}
			}
		}


		return true;
	}
}

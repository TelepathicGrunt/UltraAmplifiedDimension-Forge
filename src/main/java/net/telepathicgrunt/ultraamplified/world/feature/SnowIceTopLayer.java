package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class SnowIceTopLayer extends Feature<NoFeatureConfig> {
   public SnowIceTopLayer(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i51435_1_) {
      super(p_i51435_1_);
   }

   public static boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config, Biome biome) {
      BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();
      BlockPos.Mutable blockpos$mutable1 = new BlockPos.Mutable();

            int y = world.getHeight(Heightmap.Type.MOTION_BLOCKING, pos.getX(), pos.getZ());
            blockpos$mutable.setPos(pos.getX(), y, pos.getZ());
            blockpos$mutable1.setPos(blockpos$mutable).move(Direction.DOWN, 1);
            
            if (biome.doesWaterFreeze(world, blockpos$mutable1, false)) {
               world.setBlockState(blockpos$mutable1, Blocks.ICE.getDefaultState(), 2);
            }

            if (biome.doesSnowGenerate(world, blockpos$mutable)) {
               world.setBlockState(blockpos$mutable, Blocks.SNOW.getDefaultState(), 2);
               BlockState blockstate = world.getBlockState(blockpos$mutable1);
               if (blockstate.has(SnowyDirtBlock.SNOWY)) {
                  world.setBlockState(blockpos$mutable1, blockstate.with(SnowyDirtBlock.SNOWY, Boolean.valueOf(true)), 2);
               }
            }
      return true;
   }
   

	// unused as snowlayerhandlerfeature will call the above place method
	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos,
			NoFeatureConfig config) {
		return false;
	}
}
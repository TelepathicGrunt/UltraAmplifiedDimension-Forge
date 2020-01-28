package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BambooBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.properties.BambooLeaves;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class Bamboo extends Feature<NoFeatureConfig> {
   private static final BlockState BAMBOO_DEFAULT = Blocks.BAMBOO.getDefaultState().with(BambooBlock.PROPERTY_AGE, Integer.valueOf(1)).with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.NONE).with(BambooBlock.PROPERTY_STAGE, Integer.valueOf(0));
   private static final BlockState BAMBOO_LEAVES_LARGE_TOP = BAMBOO_DEFAULT.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.LARGE).with(BambooBlock.PROPERTY_STAGE, Integer.valueOf(1));
   private static final BlockState BAMBOO_LEAVES_LARGE = BAMBOO_DEFAULT.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.LARGE);
   private static final BlockState BAMBOO_LEAVES_SMALL = BAMBOO_DEFAULT.with(BambooBlock.PROPERTY_BAMBOO_LEAVES, BambooLeaves.SMALL);

   public Bamboo(Function<Dynamic<?>, ? extends NoFeatureConfig> p_i49919_1_) {
      super(p_i49919_1_);
   }

   public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
      int i = 0;
      BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(pos);
      BlockPos.Mutable blockpos$Mutable1 = new BlockPos.Mutable(pos);
      if (world.isAirBlock(blockpos$Mutable)) {
         if (Blocks.BAMBOO.getDefaultState().isValidPosition(world, blockpos$Mutable)) {
           int maxHeight = rand.nextInt(12) + 5;
           int podzolRange = rand.nextInt(2) + 1;

           for(int x = pos.getX() - podzolRange; x <= pos.getX() + podzolRange; ++x) {
              for(int z = pos.getZ() - podzolRange; z <= pos.getZ() + podzolRange; ++z) {
                  for(int y = pos.getY() - 2; y <= pos.getY() + 2; ++y) {
	                 int xDiff = x - pos.getX();
	                 int zDiff = z - pos.getZ();
	                 if (xDiff * xDiff + zDiff * zDiff <= podzolRange * podzolRange) {
	                    blockpos$Mutable1.setPos(x, y, z);
	                    if (rand.nextFloat() < 0.4F && world.getBlockState(blockpos$Mutable1).getBlock() == Blocks.GRASS_BLOCK) {
	                       world.setBlockState(blockpos$Mutable1, Blocks.PODZOL.getDefaultState(), 2);
	                    }
	                 }	
                  }
              }
           }

            for(int height = 0; height < maxHeight && height <= 255 && world.isAirBlock(blockpos$Mutable); ++height) {
               world.setBlockState(blockpos$Mutable, BAMBOO_DEFAULT, 2);
               blockpos$Mutable.move(Direction.UP, 1);
            }

            if (blockpos$Mutable.getY() - pos.getY() >= 3 && blockpos$Mutable.getY() <= 255 ) {
               world.setBlockState(blockpos$Mutable, BAMBOO_LEAVES_LARGE_TOP, 2);
               world.setBlockState(blockpos$Mutable.move(Direction.DOWN, 1), BAMBOO_LEAVES_LARGE, 2);
               world.setBlockState(blockpos$Mutable.move(Direction.DOWN, 1), BAMBOO_LEAVES_SMALL, 2);
            }
         }

         ++i;
      }

      return i > 0;
   }
}
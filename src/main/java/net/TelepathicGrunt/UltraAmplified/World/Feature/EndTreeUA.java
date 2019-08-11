package net.TelepathicGrunt.UltraAmplified.World.Feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

public class EndTreeUA extends AbstractTreeFeature<NoFeatureConfig> 
{
   private static final BlockState LOG = Blocks.BIRCH_LOG.getDefaultState();
   private static final BlockState LEAF = Blocks.DARK_OAK_LEAVES.getDefaultState();
   private static final BlockState PURPLE_TERRACOTTA = Blocks.BLUE_TERRACOTTA.getDefaultState();

   public EndTreeUA(Function<Dynamic<?>, ? extends NoFeatureConfig> configIn, boolean doBlockNotifyIn) {
      super(configIn, doBlockNotifyIn);
      this.setSapling((net.minecraftforge.common.IPlantable)Blocks.BIRCH_SAPLING);
   }

   public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox p_208519_5_) {
      int i = rand.nextInt(3) + 5;

      boolean flag = true;
      if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getMaxHeight()) {
         for(int j = position.getY(); j <= position.getY() + 1 + i; ++j) {
            int k = 1;
            if (j == position.getY()) {
               k = 0;
            }

            if (j >= position.getY() + 1 + i - 2) {
               k = 2;
            }

            BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

            for(int l = position.getX() - k; l <= position.getX() + k && flag; ++l) {
               for(int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1) {
                  if (j >= 0 && j < worldIn.getMaxHeight()) {
                     if (!func_214587_a(worldIn, blockpos$mutableblockpos.setPos(l, j, i1))) {
                        flag = false;
                     }
                  } else {
                     flag = false;
                  }
               }
            }
         }

         if (!flag) {
            return false;
         } else if ((isSoil(worldIn, position.down(), getSapling())) && position.getY() < worldIn.getMaxHeight() - i - 1) {
        	 placeTerracottaCircle((IWorld)worldIn, position.down());

            for(int l1 = position.getY() - 3 + i; l1 <= position.getY() + i; ++l1) {
               int j2 = l1 - (position.getY() + i);
               int k2 = 1 - j2 / 2;

               for(int l2 = position.getX() - k2; l2 <= position.getX() + k2; ++l2) {
                  int i3 = l2 - position.getX();

                  for(int j1 = position.getZ() - k2; j1 <= position.getZ() + k2; ++j1) {
                     int k1 = j1 - position.getZ();
                     if (Math.abs(i3) != k2 || Math.abs(k1) != k2 || rand.nextInt(2) != 0 && j2 != 0) {
                        BlockPos blockpos = new BlockPos(l2, l1, j1);
                        if (isAirOrLeaves(worldIn, blockpos)) {
                           this.setLogState(changedBlocks, worldIn, blockpos, LEAF, p_208519_5_);
                        }
                     }
                  }
               }
            }

            for(int i2 = 0; i2 < i; ++i2) {
               if (isAirOrLeaves(worldIn, position.up(i2))) {
                  this.setLogState(changedBlocks, worldIn, position.up(i2), LOG, p_208519_5_);
               }
            }

            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }
    
    private void placeTerracottaCircle(IWorld worldIn, BlockPos center)
    {
        for (int x = -2; x <= 2; ++x)
        {
            for (int z = -2; z <= 2; ++z)
            {
                if (Math.abs(x) + Math.abs(z) < 3)
                {
                    this.placeTerracottaAt(worldIn, center.add(x, 0, z));
                }
            }
        }
    }
    
    private void placeTerracottaAt(IWorld worldIn, BlockPos pos)
    {
        for (int level = 2; level >= -3; --level)
        {
            BlockPos blockpos = pos.up(level);
            BlockState iblockstate = worldIn.getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if (block == Blocks.GRASS_BLOCK || block == Blocks.DIRT)
            {
                this.setBlockState(worldIn, blockpos, PURPLE_TERRACOTTA);
                break;
            }

            if (iblockstate.getMaterial() != Material.AIR && level < 0)
            {
                break;
            }
        }
    }
}

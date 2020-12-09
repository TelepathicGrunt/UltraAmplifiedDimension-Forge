package com.telepathicgrunt.ultraamplifieddimension.world.decorators;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.WorldDecoratingHelper;
import net.minecraft.world.gen.placement.Placement;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;


public class LedgeSurfacePlacer extends Placement<LedgeSurfacePlacerConfig> {
    public LedgeSurfacePlacer(Codec<LedgeSurfacePlacerConfig> codec) {
        super(codec);
    }

    @Nonnull
    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper context, Random rand, LedgeSurfacePlacerConfig config, BlockPos pos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        List<BlockPos> list = new ArrayList<>();

        // Count specifies number of columns passes we will do
        for(int count = 0; count < config.columnCount; ++count) {

            // Randomizes first pos and set it to heightmap
            int x = rand.nextInt(16) + pos.getX();
            int z = rand.nextInt(16) + pos.getZ();
            int heightMapY = context.func_242893_a(Heightmap.Type.MOTION_BLOCKING, x, z);
            mutable.setPos(x, heightMapY, z);

            // Set the block above for heightmap pos
            BlockState prevBlockState = context.func_242894_a(mutable.up());

            // Move downward towards sealevel and get every surface along the way
            while (mutable.getY() >= context.func_242895_b()) {

                BlockState currentBlockPos = context.func_242894_a(mutable);

                // This is true if above is spacious while current block is solid.
                // We are at ledge if this is the case.
                // Also allows underside placements as well
                if (((!config.undersideOnly && !notSolidSpace(currentBlockPos) && notSolidSpace(prevBlockState)) ||
                    (config.undersideOnly && notSolidSpace(currentBlockPos) && !notSolidSpace(prevBlockState))) &&
                    !currentBlockPos.isIn(Blocks.BEDROCK))
                {
                    // If we are skipping top ledge, then current y must not be terrain heightmap.
                    // The -1 is because we check top down and find surfaces if our block is a solid
                    // block with space above but the heightmap method always returns that above space.
                    // thus we need to subtract one to be able to tell if our pos is topmost terrain.
                    //
                    // Underside placing skips the top ledge checks
                    if(config.undersideOnly || !config.skipTopLedge || mutable.getY() != context.func_242893_a(Heightmap.Type.MOTION_BLOCKING, mutable.getX(), mutable.getZ()) - 1){
                        list.add(mutable.toImmutable());
                    }
                }

                // Set prevblock to this block and randomize the pos while move down one.
                prevBlockState = currentBlockPos;
                mutable.setPos(rand.nextInt(16) + pos.getX(),
                            mutable.getY() - 1,
                            rand.nextInt(16) + pos.getZ());
            }
        }

        return list.stream();
    }

    private static boolean notSolidSpace(BlockState state) {
        return state.isAir() || state.isIn(Blocks.WATER) || state.isIn(Blocks.LAVA);
    }
}

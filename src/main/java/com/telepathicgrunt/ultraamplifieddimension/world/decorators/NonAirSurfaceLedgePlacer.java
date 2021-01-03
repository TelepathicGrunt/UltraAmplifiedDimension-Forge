package com.telepathicgrunt.ultraamplifieddimension.world.decorators;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.tags.BlockTags;
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


public class NonAirSurfaceLedgePlacer extends Placement<NonAirSurfaceLedgePlacerConfig> {

    public NonAirSurfaceLedgePlacer(Codec<NonAirSurfaceLedgePlacerConfig> codec) {
        super(codec);
    }

    @Nonnull
    @Override
    public Stream<BlockPos> getPositions(WorldDecoratingHelper context, Random rand, NonAirSurfaceLedgePlacerConfig config, BlockPos pos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        List<BlockPos> list = new ArrayList<>();

        // Count specifies number of columns passes we will do
        for(int count = 0; count < config.columnCount; ++count) {

            // Randomizes first pos and set it to heightmap
            int x = rand.nextInt(16) + pos.getX();
            int z = rand.nextInt(16) + pos.getZ();
            int heightMapY = context.func_242893_a(Heightmap.Type.OCEAN_FLOOR_WG, x, z);
            mutable.setPos(x, heightMapY, z);

            // Set the block above for heightmap pos
            BlockState prevBlockState = context.func_242894_a(mutable.up());
            int bottomYLimit = context.func_242895_b();

            // Move downward towards sealevel and get every surface along the way
            while (mutable.getY() >= bottomYLimit) {

                BlockState currentBlockState = context.func_242894_a(mutable);

                // This is true if above is spacious while current block is solid.
                // We are at ledge if this is the case.
                // Also allows underside placements as well
                if (!currentBlockState.isIn(BlockTags.LEAVES) &&
                    !currentBlockState.isIn(BlockTags.LOGS) &&
                    !currentBlockState.isIn(Blocks.BEDROCK) &&
                    currentBlockState.getMaterial() != Material.CACTUS &&
                    !currentBlockState.isAir() &&
                    prevBlockState.isAir())
                {
                    if(rand.nextFloat() < config.validSpotChance){
                        list.add(mutable.toImmutable());
                    }

                    // pick a new x/z pos
                    mutable.setPos(
                            rand.nextInt(16) + pos.getX(),
                            mutable.getY(),
                            rand.nextInt(16) + pos.getZ());
                }

                // Set prevblock to this block and move down one.
                prevBlockState = currentBlockState;
                mutable.move(Direction.DOWN);
            }
        }

        return list.stream();
    }
}

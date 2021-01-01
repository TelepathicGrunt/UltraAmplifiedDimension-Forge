package com.telepathicgrunt.ultraamplifieddimension.world.features.treedecorators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADTreeDecoratorTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;

import java.util.List;
import java.util.Random;
import java.util.Set;

public class DiskGroundDecorator extends TreeDecorator {

    public static final Codec<DiskGroundDecorator> CODEC = RecordCodecBuilder.create((diskGroundDecorator) -> diskGroundDecorator.group(
            BlockStateProvider.CODEC.fieldOf("provider").forGetter((config) -> config.blockStateProvider),
            Codec.intRange(0, 36).fieldOf("radius").forGetter((config) -> config.radius)
    ).apply(diskGroundDecorator, DiskGroundDecorator::new));

    private final BlockStateProvider blockStateProvider;
    private final int radius;

    public DiskGroundDecorator(BlockStateProvider blockStateProvider, int radius) {
        this.blockStateProvider = blockStateProvider;
        this.radius = radius;
    }

    @Override
    protected TreeDecoratorType<?> func_230380_a_() {
        return UADTreeDecoratorTypes.DISK_GROUND_DECORATOR.get();
    }

    @Override
    public void func_225576_a_(ISeedReader world, Random random, List<BlockPos> trunkBlockPos, List<BlockPos> leavesBlockPos, Set<BlockPos> blockPosSet, MutableBoundingBox boundingBox) {
        int minY = trunkBlockPos.get(0).getY();

        // run blob code only for bottom trunks
        trunkBlockPos.stream().filter((pos) -> pos.getY() == minY).forEach((pos) -> this.genBlob(world, random, pos));
    }

    private void genBlob(IWorldGenerationReader world, Random random, BlockPos centerBlockPos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        for(int x = -this.radius; x <= this.radius; ++x) {
            for(int z = -this.radius; z <= this.radius; ++z) {
                if ((x * x) + (z * z) <= (this.radius * this.radius)) {
                    this.setBlobBlock(world, random, mutable.setPos(centerBlockPos).move(x, 0, z));
                }
            }
        }

    }

    private void setBlobBlock(IWorldGenerationReader world, Random random, BlockPos startBlockPos) {
        BlockPos.Mutable mutable = new BlockPos.Mutable().setPos(startBlockPos).move(Direction.UP, 2);
        for(int y = 2; y >= -3; --y) {
            if (Feature.isDirtAt(world, mutable)) {
                world.setBlockState(mutable, this.blockStateProvider.getBlockState(random, startBlockPos), 19);
                break;
            }

            if (!Feature.isAirAt(world, mutable) && y < 0) {
                break;
            }

            mutable.move(Direction.DOWN);
        }
    }
}

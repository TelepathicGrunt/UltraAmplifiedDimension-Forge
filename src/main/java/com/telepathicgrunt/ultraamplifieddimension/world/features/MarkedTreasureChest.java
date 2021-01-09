package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.LootTableConfig;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.structure.StructurePiece;

import java.util.Random;


public class MarkedTreasureChest extends Feature<LootTableConfig> {

    public MarkedTreasureChest(Codec<LootTableConfig> configFactory) {
        super(configFactory);
    }

    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random random, BlockPos position, LootTableConfig config) {
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable().setPos(position);

        //surface block must be solid with water above
        if (!world.getBlockState(blockpos$Mutable).isSolid() || world.getBlockState(blockpos$Mutable.up()).getFluidState().isEmpty()) {
            return false;
        }

        //chest position must be surrounded by solid blocks
        for (Direction face : Direction.values()) {
            //skip above block as we already checked it
            if (face == Direction.UP) {
                continue;
            }

            if (!world.getBlockState(blockpos$Mutable.down().offset(face)).isSolid()) {
                return false;
            }
        }

        //if we reached here, then the placement is good for generation.

        //creates the x marker
        int size = 5;
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                int absx = Math.abs(x);
                int absz = Math.abs(z);

                //doesn't place red sand on corners
                if (absx == size && absz == size) {
                    continue;
                }

                //creates a thick x shape
                if (random.nextFloat() < 0.85 && Math.abs(absx - absz) < 2) {
                    world.setBlockState(blockpos$Mutable.setPos(position).move(x, 0, z), Blocks.RED_SAND.getDefaultState(), 2);
                }
            }
        }

        blockpos$Mutable.setPos(position).move(Direction.DOWN);
        //places chest with a 50/50 split between treasure chest and end city loot
        world.setBlockState(blockpos$Mutable, StructurePiece.correctFacing(world, blockpos$Mutable, Blocks.CHEST.getDefaultState()), 2);
        LockableLootTileEntity.setLootTable(world, random, blockpos$Mutable, config.lootTable);
        //UltraAmplified.Logger.log(Level.DEBUG, "Marked Treasure Chest "+" | "+blockpos.getX()+" "+blockpos.getZ());

        return true;
    }
}
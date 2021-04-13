package com.telepathicgrunt.ultraamplifieddimension.utils;

import com.mojang.datafixers.util.Pair;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.biome.Biome;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GeneralUtils {

    public static String biomeIDString(String biomeName){
        return UltraAmplifiedDimension.MODID + ":" + biomeName;
    }

    // Weighted Random from: https://stackoverflow.com/a/6737362
    public static <T> T getRandomEntry(List<Pair<T, Integer>> rlList, Random random){
        double totalWeight = 0.0;

        // Compute the total weight of all items together.
        for (Pair<T, Integer> pair : rlList) {
            totalWeight += pair.getSecond();
        }

        // Now choose a random item.
        int index = 0;
        for (double randomWeightPicked = random.nextFloat() * totalWeight; index < rlList.size() - 1; ++index) {
            randomWeightPicked -= rlList.get(index).getSecond();
            if (randomWeightPicked <= 0.0) break;
        }

        return rlList.get(index).getFirst();
    }

///////////////////////////////////////////////////////////////////
    // For UAD carvers

    private static final Map<String, BlockState> FILLER_BIOME_MAP;
    static {
        FILLER_BIOME_MAP = new HashMap<>();

        FILLER_BIOME_MAP.put(biomeIDString("iced_terrain"), Blocks.ICE.getDefaultState());
        FILLER_BIOME_MAP.put(biomeIDString("ice_spikes"), Blocks.ICE.getDefaultState());
        FILLER_BIOME_MAP.put(biomeIDString("deep_frozen_ocean"), Blocks.ICE.getDefaultState());
        FILLER_BIOME_MAP.put(biomeIDString("frozen_ocean"), Blocks.ICE.getDefaultState());
    }

    private static final Map<String, BlockState> LAVA_FLOOR_BIOME_MAP;
    static {
        LAVA_FLOOR_BIOME_MAP = new HashMap<>();

        LAVA_FLOOR_BIOME_MAP.put(biomeIDString("iced_terrain"), Blocks.OBSIDIAN.getDefaultState());
        LAVA_FLOOR_BIOME_MAP.put(biomeIDString("ice_spikes"), Blocks.MAGMA_BLOCK.getDefaultState());
        LAVA_FLOOR_BIOME_MAP.put(biomeIDString("relic_snowy_taiga"), Blocks.MAGMA_BLOCK.getDefaultState());
        LAVA_FLOOR_BIOME_MAP.put(biomeIDString("snowy_rocky_taiga"), Blocks.MAGMA_BLOCK.getDefaultState());
        LAVA_FLOOR_BIOME_MAP.put(biomeIDString("snowy_taiga"), Blocks.MAGMA_BLOCK.getDefaultState());
        LAVA_FLOOR_BIOME_MAP.put(biomeIDString("snowy_tundra"), Blocks.MAGMA_BLOCK.getDefaultState());
        LAVA_FLOOR_BIOME_MAP.put(biomeIDString("frozen_desert"), Blocks.MAGMA_BLOCK.getDefaultState());
        LAVA_FLOOR_BIOME_MAP.put(biomeIDString("deep_frozen_ocean"), Blocks.MAGMA_BLOCK.getDefaultState());
        LAVA_FLOOR_BIOME_MAP.put(biomeIDString("frozen_ocean"), Blocks.MAGMA_BLOCK.getDefaultState());
    }

    public static BlockState carverLavaReplacement(String biomeIDString, Biome biome) {

        BlockState replacementBlock = LAVA_FLOOR_BIOME_MAP.get(biomeIDString);

        // Cache the biome
        if(replacementBlock == null){
            if(biome.getCategory() == Biome.Category.ICY){
                if(biome.getTemperature() < -0.5){
                    LAVA_FLOOR_BIOME_MAP.put(biomeIDString, Blocks.OBSIDIAN.getDefaultState());
                }
                else{
                    LAVA_FLOOR_BIOME_MAP.put(biomeIDString, Blocks.MAGMA_BLOCK.getDefaultState());
                }
            }
            else{
                LAVA_FLOOR_BIOME_MAP.put(biomeIDString, Blocks.LAVA.getDefaultState());
            }

            replacementBlock = LAVA_FLOOR_BIOME_MAP.get(biomeIDString);
        }

        return replacementBlock;
    }

    public static BlockState carverFillerBlock(String biomeIDString, Biome biome){
        BlockState replacementBlock = FILLER_BIOME_MAP.get(biomeIDString);

        // cache the biome
        if (replacementBlock == null) {
            if(biome.getCategory() == Biome.Category.THEEND){
                FILLER_BIOME_MAP.put(biomeIDString, Blocks.END_STONE.getDefaultState());
            }
            else if(biome.getCategory() == Biome.Category.NETHER){
                FILLER_BIOME_MAP.put(biomeIDString, Blocks.NETHERRACK.getDefaultState());
            }
            else if(biome.getCategory() == Biome.Category.ICY && biome.getTemperature() < -0.5){
                FILLER_BIOME_MAP.put(biomeIDString, Blocks.ICE.getDefaultState());
            }
            else{
                FILLER_BIOME_MAP.put(biomeIDString, Blocks.STONE.getDefaultState());
            }

            replacementBlock = FILLER_BIOME_MAP.get(biomeIDString);
        }

        return replacementBlock;
    }

    //////////////////////////////
    private static final Map<BlockState, Boolean> IS_FULLCUBE_MAP = new HashMap<>();

    public static boolean isFullCube(ISeedReader world, BlockPos pos, BlockState state){
        if(!IS_FULLCUBE_MAP.containsKey(state)){
            boolean isFullCube = Block.isOpaque(state.getShape(world, pos));
            IS_FULLCUBE_MAP.put(state, isFullCube);
        }
        return IS_FULLCUBE_MAP.get(state);
    }
    //////////////////////////////

    // Helper method to make chests always face away from walls
    public static BlockState orientateChest(ISeedReader blockView, BlockPos blockPos, BlockState blockState) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Direction bestDirection = blockState.get(HorizontalBlock.HORIZONTAL_FACING);

        for(Direction facing : Direction.Plane.HORIZONTAL) {
            mutable.setPos(blockPos).move(facing);

            // Checks if wall is in this side
            if (isFullCube(blockView, mutable, blockView.getBlockState(mutable))) {
                bestDirection = facing;

                // Exit early if facing open space opposite of wall
                mutable.move(facing.getOpposite(), 2);
                if(!blockView.getBlockState(mutable).getMaterial().isSolid()){
                    break;
                }
            }
        }

        // Make chest face away from wall
        return blockState.with(HorizontalBlock.HORIZONTAL_FACING, bestDirection.getOpposite());
    }
}

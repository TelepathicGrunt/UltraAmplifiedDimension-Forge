package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import com.telepathicgrunt.ultraamplifieddimension.world.features.configs.CountConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.Feature;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class GlowPatch extends Feature<CountConfig> {

    private static Map<BlockState, BlockState> GLOWBLOCK_MAP;


    /**
     * Have to make this map in UltraAmplified setup method since the blocks needs to be initialized first
     */
    public static void setFillerMap() {
        if (GLOWBLOCK_MAP == null) {
            GLOWBLOCK_MAP = new HashMap<>();

            GLOWBLOCK_MAP.put(Blocks.DIRT.getDefaultState(), UADBlocks.GLOWDIRT.get().getDefaultState());
            GLOWBLOCK_MAP.put(Blocks.COARSE_DIRT.getDefaultState(), UADBlocks.COARSE_GLOWDIRT.get().getDefaultState());
            GLOWBLOCK_MAP.put(Blocks.GRASS_BLOCK.getDefaultState(), UADBlocks.GLOWGRASS_BLOCK.get().getDefaultState());
            GLOWBLOCK_MAP.put(Blocks.MYCELIUM.getDefaultState(), UADBlocks.GLOWMYCELIUM.get().getDefaultState());
            GLOWBLOCK_MAP.put(Blocks.STONE.getDefaultState(), UADBlocks.GLOWSTONE_ORE.get().getDefaultState());
            GLOWBLOCK_MAP.put(Blocks.PODZOL.getDefaultState(), UADBlocks.GLOWPODZOL.get().getDefaultState());
            GLOWBLOCK_MAP.put(Blocks.SAND.getDefaultState(), UADBlocks.GLOWSAND.get().getDefaultState());
            GLOWBLOCK_MAP.put(Blocks.RED_SAND.getDefaultState(), UADBlocks.RED_GLOWSAND.get().getDefaultState());
        }
    }


    public GlowPatch(Codec<CountConfig> configFactory) {
        super(configFactory);
        setFillerMap();
    }


    @Override
    public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, CountConfig countConfig) {
        BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable();

        // tries as times specified to convert a randomly chosen nearby block
        for (int attempts = 0; attempts < countConfig.count; ++attempts) {
            // clustered around the center the most
            int gausX = (int) (Math.max(Math.min(rand.nextGaussian() * 3, 15), -15)); // range of -15 to 15
            int gausY = rand.nextInt(4) - rand.nextInt(4); // range of -4 to 4
            int gausZ = (int) (Math.max(Math.min(rand.nextGaussian() * 3, 15), -15)); // range of -15 to 15
            blockpos$Mutable.setPos(position).move(gausX, gausY + 1, gausZ);

            // Glowstuff cannot be placed in sunlight
            // Need cache for heightmap checking
            //if(world.getLightFor(LightType.SKY, blockpos$Mutable) > 10) continue;
            //if (chunkGenerator.getHeight(blockpos$Mutable.getX(), blockpos$Mutable.getX(), Heightmap.Type.OCEAN_FLOOR) - 1 != blockpos$Mutable.getY()) {

            BlockState chosenAboveBlock = world.getBlockState(blockpos$Mutable);
            BlockState chosenBlock = world.getBlockState(blockpos$Mutable.move(Direction.DOWN));

            // turns stone into glowstone ore even if no air above
            if (chosenBlock.getBlock() == Blocks.STONE) {
                world.setBlockState(blockpos$Mutable, GLOWBLOCK_MAP.get(chosenBlock), 2);
            }
            // turns valid surface blocks with air above into glowstone variants
            else if (GLOWBLOCK_MAP.containsKey(chosenBlock) && chosenAboveBlock.getMaterial() == Material.AIR) {
                world.setBlockState(blockpos$Mutable, GLOWBLOCK_MAP.get(chosenBlock), 2);
            }
        }

        //debugging
        //UltraAmplified.Logger.log(Level.DEBUG, "Glowpatch at "+pos.getX() +", "+pos.getY()+", "+pos.getZ());
        return true;
    }

}

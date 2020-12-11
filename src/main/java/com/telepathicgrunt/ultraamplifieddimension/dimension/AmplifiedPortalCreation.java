package com.telepathicgrunt.ultraamplifieddimension.dimension;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import com.telepathicgrunt.ultraamplifieddimension.world.features.AmplifiedPortalFrame;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.Entity;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

/**
 * Handles creating the Amplified Portal block and holds the code to make the portal frame too.
 */
public class AmplifiedPortalCreation {
    private static final ResourceLocation PORTAL_ITEMS_TAG = new ResourceLocation(UltraAmplifiedDimension.MODID, "portal_activation_items");

    public static void PortalCreationRightClick(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        Entity entity = event.getEntity();

        if (!world.isRemote() && !entity.isCrouching()) {
            if (event.getItemStack().getItem().getTags().contains(PORTAL_ITEMS_TAG)) {
                trySpawnPortal(world, event.getPos());
            }
        }
    }


    // ------------------------------------------------------------------------------------//
    // Portal creation and validation check

    private static final Block POLISHED_DIORITE = Blocks.POLISHED_DIORITE;
    private static final ResourceLocation PORTAL_CORNER_TAG = new ResourceLocation(UltraAmplifiedDimension.MODID, "portal_corner_blocks");
    private static final ResourceLocation PORTAL_NON_CORNER_TAG = new ResourceLocation(UltraAmplifiedDimension.MODID, "portal_non_corner_blocks");


    public static boolean checkForGeneratedPortal(IWorld worldUA) {
        BlockPos pos = new BlockPos(8, worldUA.func_234938_ad_(), 8);
        worldUA.getChunk(pos);

        while (pos.getY() >= 0) {
            if (worldUA.getBlockState(pos) == UADBlocks.AMPLIFIED_PORTAL.get().getDefaultState()) {
                return true;
            }
            pos = pos.down();
        }

        return false;
    }


    public static void generatePortal(ServerWorld worldUA) {
        AmplifiedPortalFrame amplifiedPortalFrame = new AmplifiedPortalFrame();
        BlockPos pos = new BlockPos(8, worldUA.func_234938_ad_(), 8);
        worldUA.getChunk(pos);

        pos = worldUA.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
        if (pos.getY() > 252) {
            pos = pos.down(3);
        }
        else if (pos.getY() < 6) {
            pos = new BlockPos(pos.getX(), 6, pos.getZ());
        }

        amplifiedPortalFrame.generate(worldUA, pos);
    }


    public static boolean isValid(IWorld world, BlockPos pos) {
        // bottom of portal frame
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                // Floor corners
                if (Math.abs(x * z) == 1) {
                    if (!world.getBlockState(pos.add(x, -1, z)).getBlock().getTags().contains(PORTAL_CORNER_TAG)) {
                        return false;
                    }
                }

                // Plus shape on floor
                else {
                    BlockState currentFloor = world.getBlockState(pos.add(x, -1, z));
                    if (!(currentFloor.getBlock().getTags().contains(PORTAL_NON_CORNER_TAG) &&
                        (!currentFloor.hasProperty(SlabBlock.TYPE) || currentFloor.get(SlabBlock.TYPE) == SlabType.BOTTOM)))
                    {
                        return false;
                    }
                }
            }
        }

        // the center itself
        if (world.getBlockState(pos.add(0, 0, 0)).getBlock() != POLISHED_DIORITE) {
            return false;
        }

        // top of portal frame
        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                // Top corners
                if (Math.abs(x * z) == 1) {
                    if (!world.getBlockState(pos.add(x, 1, z)).getBlock().getTags().contains(PORTAL_CORNER_TAG)) {
                        return false;
                    }
                }
                // Plus shape on ceiling
                else {
                    BlockState currentCeiling = world.getBlockState(pos.add(x, 1, z));
                    if (!(currentCeiling.getBlock().getTags().contains(PORTAL_NON_CORNER_TAG) &&
                        (!currentCeiling.hasProperty(SlabBlock.TYPE) || currentCeiling.get(SlabBlock.TYPE) == SlabType.TOP)))
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static void trySpawnPortal(IWorld world, BlockPos pos) {
        boolean canMakePortal = isValid(world, pos);
        if (canMakePortal) {
            //place portal at pos in the portal frame.
            world.setBlockState(pos, UADBlocks.AMPLIFIED_PORTAL.get().getDefaultState(), 18);
        }
    }
}

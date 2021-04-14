package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class UADTags {
    // All tag wrappers need to be made at mod init.
    public static void tagInit(){}

    public static final ITag.INamedTag<Item> PORTAL_ACTIVATION_ITEMS = ItemTags.makeWrapperTag(UltraAmplifiedDimension.MODID + ":portal_activation_items");

    public static final ITag.INamedTag<Block> PORTAL_CORNER_BLOCKS = BlockTags.makeWrapperTag(UltraAmplifiedDimension.MODID + ":portal_corner_blocks");
    public static final ITag.INamedTag<Block> PORTAL_CENTER_BLOCKS = BlockTags.makeWrapperTag(UltraAmplifiedDimension.MODID + ":portal_center_blocks");
    public static final ITag.INamedTag<Block> PORTAL_NON_CORNER_BLOCKS = BlockTags.makeWrapperTag(UltraAmplifiedDimension.MODID+":portal_non_corner_blocks");
    public static final ITag.INamedTag<Block> TERRACOTTA_BLOCKS = BlockTags.makeWrapperTag(UltraAmplifiedDimension.MODID+":terracotta");
}

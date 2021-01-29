package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.blocks.*;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;


public class UADBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UltraAmplifiedDimension.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UltraAmplifiedDimension.MODID);

    public static final RegistryObject<Block> AMPLIFIED_PORTAL = createBlock("amplified_portal", AmplifiedPortalBlock::new);
    public static final RegistryObject<Block> GLOWSTONE_ORE = createBlock("glowstone_ore", GlowstoneOreBlock::new);
    public static final RegistryObject<Block> COARSE_GLOWDIRT = createBlock("coarse_glowdirt", CoarseGlowdirtBlock::new);
    public static final RegistryObject<Block> GLOWDIRT = createBlock("glowdirt", GlowdirtBlock::new);
    public static final RegistryObject<Block> GLOWGRASS_BLOCK = createBlock("glowgrass_block", GlowgrassBlock::new);
    public static final RegistryObject<Block> GLOWMYCELIUM = createBlock("glowmycelium", GlowmyceliumBlock::new);
    public static final RegistryObject<Block> GLOWPODZOL = createBlock("glowpodzol", GlowpodzolBlock::new);
    public static final RegistryObject<Block> GLOWSAND = createBlock("glowsand", GlowsandBlock::new);
    public static final RegistryObject<Block> RED_GLOWSAND = createBlock("red_glowsand", RedGlowsandBlock::new);
    public static final RegistryObject<Block> BIG_CACTUS_BODY_BLOCK = createBlock("big_cactus_body_block", BigCactusBodyBlock::new);
    public static final RegistryObject<Block> BIG_CACTUS_CORNER_BLOCK = createBlock("big_cactus_corner_block", BigCactusCornerBlock::new);
    public static final RegistryObject<Block> BIG_CACTUS_MAIN_BLOCK = createBlock("big_cactus_main_block", BigCactusMainBlock::new);

    //creative tab to hold our block items
    public static final ItemGroup ULTRA_AMPLIFIED_TAB = new ItemGroup(ItemGroup.GROUPS.length, UltraAmplifiedDimension.MODID + ".main_tab") {
        @Override
        // CLIENT-SIDED
        public ItemStack createIcon() {
            return new ItemStack(AMPLIFIED_PORTAL.get());
        }
    };
    
    public static <B extends Block> RegistryObject<B> createBlock(String name, Supplier<B> block) {
        RegistryObject<B> blockHolder = BLOCKS.register(name, block);
        ITEMS.register(name, () -> new BlockItem(blockHolder.get(), new Item.Properties().group(ULTRA_AMPLIFIED_TAB)));
        return blockHolder;
    }
}
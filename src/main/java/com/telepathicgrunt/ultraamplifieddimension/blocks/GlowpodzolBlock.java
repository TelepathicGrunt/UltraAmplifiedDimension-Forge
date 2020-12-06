package com.telepathicgrunt.ultraamplifieddimension.blocks;

import net.minecraft.block.SnowyDirtBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;


public class GlowpodzolBlock extends SnowyDirtBlock {

    public GlowpodzolBlock() {
        super(Properties.create(Material.EARTH, MaterialColor.OBSIDIAN).hardnessAndResistance(0.4F).sound(SoundType.GROUND).setLightLevel((blockState) -> 15));
    }
}

package com.telepathicgrunt.ultraamplifieddimension.blocks;

import net.minecraft.block.SandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;


public class RedGlowsandBlock extends SandBlock {

    public RedGlowsandBlock() {
        super(11098145, Properties.create(Material.SAND, MaterialColor.ADOBE).hardnessAndResistance(0.4F).sound(SoundType.SAND).setLightLevel((blockState) -> 15));
    }
}

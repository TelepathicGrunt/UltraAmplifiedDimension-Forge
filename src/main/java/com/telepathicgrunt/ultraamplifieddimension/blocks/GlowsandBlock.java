package com.telepathicgrunt.ultraamplifieddimension.blocks;

import net.minecraft.block.SandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;


public class GlowsandBlock extends SandBlock {

    public GlowsandBlock() {
        super(14406560, Properties.create(Material.SAND, MaterialColor.SAND).hardnessAndResistance(0.4F).sound(SoundType.SAND).setLightLevel((blockState) -> 15));
    }

}

package com.telepathicgrunt.ultraamplifieddimension.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraftforge.common.ToolType;


public class GlowstoneOreBlock extends Block {

    public GlowstoneOreBlock() {
        super(Properties.create(Material.ROCK, MaterialColor.STONE).sound(SoundType.STONE).setLightLevel((blockState) -> 15).harvestTool(ToolType.PICKAXE).setRequiresTool().hardnessAndResistance(1.3F, 5.8F));
    }

}

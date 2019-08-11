package net.TelepathicGrunt.UltraAmplified.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SandBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class RedGlowsandBlock extends SandBlock {

		public RedGlowsandBlock() {
			super(11098145, Block.Properties.create(Material.SAND, MaterialColor.ADOBE).hardnessAndResistance(0.4F).sound(SoundType.SAND).lightValue(15));
			setRegistryName("red_glowsand");
		}

	

}

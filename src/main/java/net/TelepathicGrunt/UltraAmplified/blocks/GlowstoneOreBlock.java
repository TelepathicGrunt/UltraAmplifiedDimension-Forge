package net.telepathicgrunt.ultraamplified.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class GlowstoneOreBlock extends Block {

		public GlowstoneOreBlock() {
			super(Properties.create(Material.ROCK, MaterialColor.STONE).sound(SoundType.STONE).lightValue(15).hardnessAndResistance(1.3F, 5.8F));
			setRegistryName("glowstone_ore");
		}

	

}

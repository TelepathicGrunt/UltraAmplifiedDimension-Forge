package net.telepathicgrunt.ultraamplified.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

public class CoarseGlowdirtBlock extends Block {

		public CoarseGlowdirtBlock() {
			super(Properties.create(Material.EARTH, MaterialColor.DIRT).hardnessAndResistance(0.4F).sound(SoundType.GROUND).lightValue(15));
			setRegistryName("coarse_glowdirt");
		}

	

}

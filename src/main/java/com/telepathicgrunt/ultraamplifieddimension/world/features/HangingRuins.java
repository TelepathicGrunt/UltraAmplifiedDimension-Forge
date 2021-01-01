package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.mojang.serialization.Codec;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import net.minecraft.block.BlockState;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;


public class HangingRuins extends Feature<NoFeatureConfig>
{
	private static final ResourceLocation HANGING_RUINS_RL = new ResourceLocation(UltraAmplifiedDimension.MODID + ":hanging_ruins");

	public HangingRuins(Codec<NoFeatureConfig> codec) {
		super(codec);
	}

	@Override
	public boolean generate(ISeedReader world, ChunkGenerator chunkGenerator, Random rand, BlockPos position, NoFeatureConfig config) {
		//makes sure this ruins does not spawn too close to world height border.
		if (position.getY() < chunkGenerator.getSeaLevel() + 5) {
			return false;
		}

		BlockPos.Mutable mutableMain = new BlockPos.Mutable().setPos(position);
		BlockPos.Mutable mutableTemp = new BlockPos.Mutable();

		//move through roots to the actual bottom of ledges
		BlockState currentBlock = world.getBlockState(mutableMain);
		while((BlockTags.LOGS.contains(currentBlock.getBlock()) || !currentBlock.isSolid()) && 
			mutableMain.getY() < chunkGenerator.getMaxBuildHeight())
		{
		    mutableMain.move(Direction.UP);
		    currentBlock = world.getBlockState(mutableMain);
		}
		
		//makes sure there is enough solid blocks on ledge to hold this feature.
		for (int x = -5; x <= 5; x++) {
			for (int z = -5; z <= 5; z++) {
				if (Math.abs(x * z) > 9 && Math.abs(x * z) < 20) {
					mutableTemp.setPos(mutableMain).move(x, 1, z);
					if (!world.getBlockState(mutableTemp).isSolid()) {
						return false;
					}
				}
			}
		}

		//makes sure top won't be exposed to air
		if (shouldMoveDownOne(world, mutableMain)) {
			mutableMain.move(Direction.DOWN);
		}

		//UltraAmplified.LOGGER.debug("Hanging Ruins | " + position.getX() + " " + position.getY() + " "+position.getZ());
		TemplateManager templatemanager = world.getWorld().getServer().getTemplateManager();
		Template template = templatemanager.getTemplate(HANGING_RUINS_RL);

		if (template == null) {
			UltraAmplifiedDimension.LOGGER.warn("hanging ruins NTB does not exist!");
			return false;
		}

		// enough space for ruins.
		if(mutableMain.getY() == chunkGenerator.getMaxBuildHeight() ||
				!world.getBlockState(mutableMain.down(template.getSize().getY())).isAir() ||
				!world.getBlockState(mutableMain.down(template.getSize().getY() + 5)).isAir())
		{
			return false;
		}

		BlockPos halfLengths = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
		PlacementSettings placementsettings = new PlacementSettings().setCenterOffset(halfLengths).setRotation(Rotation.randomRotation(rand)).setIgnoreEntities(false);
		template.func_237152_b_(world, mutableMain.move(-halfLengths.getX(), -8, -halfLengths.getZ()), placementsettings, rand);
		return true;
	}


	private boolean shouldMoveDownOne(IWorld world, BlockPos.Mutable mutableMain) {
		BlockPos.Mutable mutableTemp = new BlockPos.Mutable();
		//if we are on a 1 block thick ledge at any point, move down one so ruins ceiling isn't exposed 
		for (int x = -5; x <= 5; x++) {
			for (int z = -5; z <= 5; z++) {
				mutableTemp.setPos(mutableMain).move(x, 2, z);
				if (Math.abs(x * z) < 20 && !world.getBlockState(mutableTemp).isSolid()) {
					//world.setBlockState(mutableTemp, Blocks.REDSTONE_BLOCK.getDefaultState(), 2);
					return true;
				}
			}
		}
		return false;
	}
}

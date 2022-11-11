package com.telepathicgrunt.ultraamplifieddimension.world.features;

import com.google.common.collect.ImmutableList;
import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.modInit.UADBlocks;
import com.telepathicgrunt.ultraamplifieddimension.utils.GeneralUtils;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;

import java.util.Random;


public class AmplifiedPortalFrame extends Feature<NoFeatureConfig>
{
	private static final ResourceLocation PORTAL_RL = new ResourceLocation(UltraAmplifiedDimension.MODID, "auto_generated_portal");
	private final BlockIgnoreStructureProcessor IGNORE_STRUCTURE_VOID = new BlockIgnoreStructureProcessor(ImmutableList.of(Blocks.STRUCTURE_VOID));
	private final PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).addProcessor(IGNORE_STRUCTURE_VOID).setIgnoreEntities(false);

	public AmplifiedPortalFrame() {
		super(NoFeatureConfig.CODEC);
	}

	//need to be made due to extending feature
	@Override
	public boolean generate(ISeedReader world, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
		return false;
	}

	//is called in AmplifiedPortalBehavior which doesn't have a chunk generator passed in
	public boolean generate(ISeedReader world, BlockPos pos) {

		TemplateManager templatemanager = world.getWorld().getServer().getTemplateManager();
		Template template = templatemanager.getTemplate(PORTAL_RL);

		if (template == null) {
			UltraAmplifiedDimension.LOGGER.warn(PORTAL_RL + " NTB does not exist!");
			return false;
		}

		BlockPos halfLengths = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
		placementsettings.setRotation(Rotation.randomRotation(world.getRandom())).setCenterOffset(halfLengths).setIgnoreEntities(false);
		BlockPos.Mutable mutable = new BlockPos.Mutable().setPos(pos).move(-halfLengths.getX(), 0, -halfLengths.getZ());
		template.func_237152_b_(world, mutable, placementsettings, world.getRandom());

		return true;
	}
}

package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTables;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class IglooPiecesUA {
	private static final ResourceLocation IGLOO_TOP = new ResourceLocation("igloo/top");
	private static final ResourceLocation IGLOO_MIDDLE = new ResourceLocation("igloo/middle");
	private static final ResourceLocation IGLOO_BOTTOM = new ResourceLocation("igloo/bottom");
	private static final Map<ResourceLocation, BlockPos> OFFSET1 = ImmutableMap.of(IGLOO_TOP, new BlockPos(3, 5, 5),
			IGLOO_MIDDLE, new BlockPos(1, 3, 1), IGLOO_BOTTOM, new BlockPos(3, 6, 7));
	private static final Map<ResourceLocation, BlockPos> OFFSET2 = ImmutableMap.of(IGLOO_TOP, new BlockPos(0, 0, 0),
			IGLOO_MIDDLE, new BlockPos(2, -3, 4), IGLOO_BOTTOM, new BlockPos(0, -3, -2));

	public static void start(TemplateManager p_207617_0_, BlockPos p_207617_1_, Rotation p_207617_2_,
			List<StructurePiece> p_207617_3_, Random p_207617_4_) {

		int i = p_207617_4_.nextInt(8) + 4;
		p_207617_3_.add(new IglooPiecesUA.Piece(p_207617_0_, IGLOO_BOTTOM, p_207617_1_, p_207617_2_, i * 3));

		for (int j = 0; j < i - 1; ++j) {
			p_207617_3_.add(new IglooPiecesUA.Piece(p_207617_0_, IGLOO_MIDDLE, p_207617_1_, p_207617_2_, j * 3));
		}

		p_207617_3_.add(new IglooPiecesUA.Piece(p_207617_0_, IGLOO_TOP, p_207617_1_, p_207617_2_, 0));
	}

	public static class Piece extends TemplateStructurePiece {
		private ResourceLocation resourceLocation;
		private Rotation rotation;

		public Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos pos,
				Rotation rotation, int downDepth) {
			super(StructureInitUA.IGLUUA, 0);
			this.resourceLocation = resourceLocation;
			BlockPos blockpos = IglooPiecesUA.OFFSET2.get(resourceLocation);
			this.templatePosition = pos.add(blockpos.getX(), blockpos.getY() - downDepth, blockpos.getZ());
			this.rotation = rotation;
			this.setupPiece(templateManager);
		}

		public Piece(TemplateManager p_i50566_1_, CompoundNBT p_i50566_2_) {
			super(StructureInitUA.IGLUUA, p_i50566_2_);
			this.resourceLocation = new ResourceLocation(p_i50566_2_.getString("Template"));
			this.rotation = Rotation.valueOf(p_i50566_2_.getString("Rot"));
			this.setupPiece(p_i50566_1_);
		}

		private void setupPiece(TemplateManager templateManager) {
			Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation)
					.setMirror(Mirror.NONE).setCenterOffset(IglooPiecesUA.OFFSET1.get(this.resourceLocation));
			this.setup(template, this.templatePosition, placementsettings);
		}

		/**
		 * (abstract) Helper method to write subclass data to NBT
		 */
		protected void readAdditional(CompoundNBT tagCompound) {
			super.readAdditional(tagCompound);
			tagCompound.putString("Template", this.resourceLocation.toString());
			tagCompound.putString("Rot", this.rotation.name());
		}

		protected void handleDataMarker(String function, BlockPos pos, IWorld world, Random rand,
				MutableBoundingBox sbb) {
			if ("chest".equals(function)) {
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				TileEntity tileentity = world.getTileEntity(pos.down());
				if (tileentity instanceof ChestTileEntity) {
					if (ConfigUA.chestGeneration) {
						((ChestTileEntity) tileentity).setLootTable(LootTables.CHESTS_IGLOO_CHEST, rand.nextLong());
					} else {
						world.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 2);
					}
				}

			}
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob
		 * Spawners, it closes Mineshafts at the end, it adds Fences...
		 */
		public boolean func_225577_a_(IWorld world, ChunkGenerator<?> p_225577_2_, Random random,
				MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos) {
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation)
					.setMirror(Mirror.NONE).setCenterOffset(IglooPiecesUA.OFFSET1.get(this.resourceLocation));
			BlockPos blockpos = IglooPiecesUA.OFFSET2.get(this.resourceLocation);
			BlockPos blockpos1 = this.templatePosition.add(Template.transformedBlockPos(placementsettings,
					new BlockPos(3 - blockpos.getX(), 0, 0 - blockpos.getZ())));

			int i = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());

			if (i > 244) {
				i = 244;
			}

			BlockPos blockpos2 = this.templatePosition;
			this.templatePosition = this.templatePosition.add(0, i - 90 - 1, 0);
			boolean flag = super.func_225577_a_(world, p_225577_2_, random, structureBoundingBoxIn, chunkPos);

			this.templatePosition = blockpos2;
			return flag;
		}
	}
}
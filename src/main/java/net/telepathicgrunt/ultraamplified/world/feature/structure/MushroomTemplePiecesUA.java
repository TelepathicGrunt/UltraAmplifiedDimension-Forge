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
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class MushroomTemplePiecesUA {
	private static final ResourceLocation MUSHROOM_TEMPLE = new ResourceLocation(
			UltraAmplified.MODID + ":mushroom_temple");
	private static final Map<ResourceLocation, BlockPos> OFFSET1 = ImmutableMap.of(MUSHROOM_TEMPLE,
			new BlockPos(0, 4, 0));
	private static final Map<ResourceLocation, BlockPos> OFFSET2 = ImmutableMap.of(MUSHROOM_TEMPLE,
			new BlockPos(-8, -1, -8));
	private static final ResourceLocation CHESTS_MUSHROOM_TEMPLE_UA = new ResourceLocation(
			UltraAmplified.MODID + ":chests/mushroom_temple_ua");

	public static void start(TemplateManager templateManagerIn, BlockPos pos, Rotation rotationIn,
			List<StructurePiece> structurePieceList, Random random) {

		structurePieceList
				.add(new MushroomTemplePiecesUA.Piece(templateManagerIn, MUSHROOM_TEMPLE, pos, rotationIn, 0));
	}

	public static class Piece extends TemplateStructurePiece {
		private ResourceLocation resourceLocation;
		private Rotation rotation;

		public Piece(TemplateManager templateManager, ResourceLocation resourceLocation, BlockPos pos,
				Rotation rotation, int downDepth) {
			super(StructureInitUA.MTUA, 0);
			this.resourceLocation = resourceLocation;
			BlockPos blockpos = MushroomTemplePiecesUA.OFFSET2.get(resourceLocation);
			this.templatePosition = pos.add(blockpos.getX(), blockpos.getY() - downDepth, blockpos.getZ());
			this.rotation = rotation;
			this.setupPiece(templateManager);
		}

		public Piece(TemplateManager templateManagerIn, CompoundNBT tagCompound) {
			super(StructureInitUA.MTUA, tagCompound);
			this.resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
			this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
			this.setupPiece(templateManagerIn);
		}

		private void setupPiece(TemplateManager templateManager) {
			Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation)
					.setMirror(Mirror.NONE).setCenterOffset(MushroomTemplePiecesUA.OFFSET1.get(this.resourceLocation));
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
						((ChestTileEntity) tileentity).setLootTable(CHESTS_MUSHROOM_TEMPLE_UA, rand.nextLong());
					} else {
						world.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 2);
					}
				}
			} else if ("lottery".equals(function)) {

				int lotteryNum = rand.nextInt(100);

				// 1/100th chance of wither rose and Dried Kelp Block being place
				if (lotteryNum == 0) {
					world.setBlockState(pos, Blocks.POTTED_WITHER_ROSE.getDefaultState(), 2);
					world.setBlockState(pos.down(2), Blocks.DRIED_KELP_BLOCK.getDefaultState(), 2);
				}
				// 14/100th chance of Redstone Block being place
				else if (lotteryNum < 14) {
					world.setBlockState(pos, Blocks.POTTED_RED_MUSHROOM.getDefaultState(), 2);
					world.setBlockState(pos.down(2), Blocks.REDSTONE_BLOCK.getDefaultState(), 2);
				}
				// 12/100th chance of Lapis Block being place
				else if (lotteryNum < 26) {
					world.setBlockState(pos, Blocks.POTTED_BLUE_ORCHID.getDefaultState(), 2);
					world.setBlockState(pos.down(2), Blocks.LAPIS_BLOCK.getDefaultState(), 2);
				}
				// 12/100th chance of Bone Block being place
				else if (lotteryNum < 38) {
					world.setBlockState(pos, Blocks.POTTED_LILY_OF_THE_VALLEY.getDefaultState(), 2);
					world.setBlockState(pos.down(2), Blocks.BONE_BLOCK.getDefaultState(), 2);
				}
				// 12/100th chance of Hay Bale Block being place
				else if (lotteryNum < 50) {
					world.setBlockState(pos, Blocks.POTTED_DANDELION.getDefaultState(), 2);
					world.setBlockState(pos.down(2), Blocks.HAY_BLOCK.getDefaultState(), 2);
				}
				// 1/2th chance of nothing being place
				else {
					world.setBlockState(pos, Blocks.POTTED_BROWN_MUSHROOM.getDefaultState(), 2);
				}

			}
		}

		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob
		 * Spawners, it closes Mineshafts at the end, it adds Fences...
		 */
		public boolean generate(IWorld world, ChunkGenerator<?> p_225577_2_, Random random,
				MutableBoundingBox structureBoundingBoxIn, ChunkPos chunkPos) {
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation)
					.setMirror(Mirror.NONE).setCenterOffset(MushroomTemplePiecesUA.OFFSET1.get(this.resourceLocation));
			BlockPos blockpos = MushroomTemplePiecesUA.OFFSET2.get(this.resourceLocation);
			BlockPos blockpos1 = this.templatePosition.add(Template.transformedBlockPos(placementsettings,
					new BlockPos(3 - blockpos.getX(), 0, 0 - blockpos.getZ())));

			int i = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, blockpos1.getX(), blockpos1.getZ());

			if (i > 246) {
				i = 246;
			}

			BlockPos blockpos2 = this.templatePosition;
			this.templatePosition = this.templatePosition.add(0, i - 90 - 2, 0);
			boolean flag = super.generate(world, p_225577_2_, random, structureBoundingBoxIn, chunkPos);

			this.templatePosition = blockpos2;
			return flag;
		}
	}
}
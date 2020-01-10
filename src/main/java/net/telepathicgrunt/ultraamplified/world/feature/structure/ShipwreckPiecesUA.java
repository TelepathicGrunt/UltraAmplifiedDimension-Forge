package net.telepathicgrunt.ultraamplified.world.feature.structure;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.structure.ShipwreckConfig;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTables;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;


public class ShipwreckPiecesUA
{
	private static final BlockPos STRUCTURE_OFFSET = new BlockPos(4, 0, 15);
	private static final ResourceLocation[] BEACHED_SHIPS = new ResourceLocation[] { new ResourceLocation("shipwreck/with_mast"), new ResourceLocation("shipwreck/sideways_full"),
			new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"), new ResourceLocation("shipwreck/rightsideup_full"),
			new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"), new ResourceLocation("shipwreck/with_mast_degraded"),
			new ResourceLocation("shipwreck/rightsideup_full_degraded"), new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"),
			new ResourceLocation("shipwreck/rightsideup_backhalf_degraded") };
	private static final ResourceLocation[] NOT_BEACHED_SHIPS = new ResourceLocation[] { new ResourceLocation("shipwreck/with_mast"),
			new ResourceLocation("shipwreck/upsidedown_full"), new ResourceLocation("shipwreck/upsidedown_fronthalf"), new ResourceLocation("shipwreck/upsidedown_backhalf"),
			new ResourceLocation("shipwreck/sideways_full"), new ResourceLocation("shipwreck/sideways_fronthalf"), new ResourceLocation("shipwreck/sideways_backhalf"),
			new ResourceLocation("shipwreck/rightsideup_full"), new ResourceLocation("shipwreck/rightsideup_fronthalf"), new ResourceLocation("shipwreck/rightsideup_backhalf"),
			new ResourceLocation("shipwreck/with_mast_degraded"), new ResourceLocation("shipwreck/upsidedown_full_degraded"),
			new ResourceLocation("shipwreck/upsidedown_fronthalf_degraded"), new ResourceLocation("shipwreck/upsidedown_backhalf_degraded"),
			new ResourceLocation("shipwreck/sideways_full_degraded"), new ResourceLocation("shipwreck/sideways_fronthalf_degraded"),
			new ResourceLocation("shipwreck/sideways_backhalf_degraded"), new ResourceLocation("shipwreck/rightsideup_full_degraded"),
			new ResourceLocation("shipwreck/rightsideup_fronthalf_degraded"), new ResourceLocation("shipwreck/rightsideup_backhalf_degraded") };
	
	public static void beginGeneration(TemplateManager templateManager, BlockPos pos, Rotation rotation, List<StructurePiece> piecesList, Random random, ShipwreckConfig config, int height)
	{
		ResourceLocation resourcelocation = config.isBeached ? BEACHED_SHIPS[random.nextInt(BEACHED_SHIPS.length)] : NOT_BEACHED_SHIPS[random.nextInt(NOT_BEACHED_SHIPS.length)];
		piecesList.add(new ShipwreckPiecesUA.Piece(templateManager, resourcelocation, pos, rotation, config.isBeached, height));
	}

	public static class Piece extends TemplateStructurePiece
	{
		private Rotation rotation;
		private ResourceLocation resourceLocation;
		private boolean isBeached;
		private int startHeight;


		public Piece(TemplateManager templateManager, ResourceLocation resourceLoc, BlockPos pos, Rotation rot, boolean isBeached, int startHeight)
		{
			super(StructureInitUA.SHIPWRECKUA, 0);
			this.templatePosition = pos;
			this.rotation = rot;
			this.resourceLocation = resourceLoc;
			this.isBeached = isBeached;
			this.func_204754_a(templateManager);
			this.startHeight = startHeight;
		}


		public Piece(TemplateManager templateManager, CompoundNBT tagCompound)
		{
			super(StructureInitUA.SHIPWRECKUA, tagCompound);
			resourceLocation = new ResourceLocation(tagCompound.getString("Template"));
			isBeached = tagCompound.getBoolean("isBeached");
			rotation = Rotation.valueOf(tagCompound.getString("Rot"));
			func_204754_a(templateManager);
			startHeight = tagCompound.getInt("startHeight");
		}


		/**
		 * (abstract) Helper method to read subclass data from NBT
		 */
		protected void readAdditional(CompoundNBT tagCompound)
		{
			super.readAdditional(tagCompound);
			tagCompound.putString("Template", this.resourceLocation.toString());
			tagCompound.putBoolean("isBeached", this.isBeached);
			tagCompound.putString("Rot", this.rotation.name());
			tagCompound.putInt("startHeight", startHeight);
		}


		private void func_204754_a(TemplateManager templateManager)
		{
			Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(ShipwreckPiecesUA.STRUCTURE_OFFSET)
					.addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
			this.setup(template, this.templatePosition, placementsettings);
		}


		protected void handleDataMarker(String function, BlockPos pos, IWorld world, Random random, MutableBoundingBox structureBoundingBox)
		{
			if (ConfigUA.chestGeneration)
			{
				if ("map_chest".equals(function))
				{
					LockableLootTileEntity.setLootTable(world, random, pos.down(), LootTables.CHESTS_SHIPWRECK_MAP);
				}
				else if ("treasure_chest".equals(function))
				{
					LockableLootTileEntity.setLootTable(world, random, pos.down(), LootTables.CHESTS_SHIPWRECK_TREASURE);
				}
				else if ("supply_chest".equals(function))
				{
					LockableLootTileEntity.setLootTable(world, random, pos.down(), LootTables.CHESTS_SHIPWRECK_SUPPLY);
				}
			}
			else
			{

				// removes chest
				if (world.getBlockState(pos.down()).getFluidState().isEmpty())
				{
					world.setBlockState(pos.down(), Blocks.AIR.getDefaultState(), 2);
				}
				else
				{
					world.setBlockState(pos.down(), Blocks.WATER.getDefaultState(), 2);
				}
			}

		}


		/**
		 * second Part of Structure generating, this for example places Spiderwebs, Mob Spawners, it closes Mineshafts at the
		 * end, it adds Fences...
		 */
		public boolean func_225577_a_(IWorld world, ChunkGenerator<?> chunkGen, Random random, MutableBoundingBox structureBoundingBox, ChunkPos chunkPos)
		{
			BlockPos blockpos;
			BlockPos blockposSize = this.template.getSize();
			int k = blockposSize.getX() * blockposSize.getZ();
			
			//This is how vanilla moves the block pos to always be checking land at exact same position to keep shipwreck together at same height
			if (k == 0)
			{
				blockpos = new BlockPos(templatePosition.getX(), startHeight, templatePosition.getZ());
			}
			else
			{
				blockpos = templatePosition.add(blockposSize.getX() - 1, 0, blockposSize.getZ() - 1);
				blockpos = new BlockPos(blockpos.getX(), startHeight, blockpos.getZ());
			}
			
			
			// finds surface on water
			while (blockpos.getY() > 65 && world.getBlockState(blockpos).getFluidState().isEmpty())
			{
				blockpos = blockpos.down();
			}

			// finds bottom of water body
			while (blockpos.getY() > 65 && !world.getBlockState(blockpos).getFluidState().isEmpty())
			{
				blockpos = blockpos.down();
			}
			
			
			//debugging
			//world.setBlockState(blockpos, Blocks.REDSTONE_BLOCK.getDefaultState(), 2);
			//UltraAmplified.LOGGER.info("heightpos: " + blockpos.getX() + ", " + blockpos.getY() + ", " + blockpos.getZ());
			templatePosition = new BlockPos(templatePosition.getX(), blockpos.getY() - 2, templatePosition.getZ());

			return super.func_225577_a_(world, chunkGen, random, structureBoundingBox, chunkPos);
		}
	}
}
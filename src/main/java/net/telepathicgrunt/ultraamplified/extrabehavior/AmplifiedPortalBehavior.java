package net.telepathicgrunt.ultraamplified.extrabehavior;

import java.util.Random;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.blocks.UABlocks;
import net.telepathicgrunt.ultraamplified.world.dimension.UADimensionRegistration;
import net.telepathicgrunt.ultraamplified.world.feature.AmplifiedPortalFrame;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AmplifiedPortalBehavior
{

	@Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
	private static class ForgeEvents
	{
		static ForgeRegistry<Item> itemRegistry = ((ForgeRegistry<Item>) ForgeRegistries.ITEMS);

		@SubscribeEvent
		public static void BlockRightClickEvent(PlayerInteractEvent.RightClickBlock event)
		{
			World world = event.getWorld();
			Entity entity = event.getEntity();

			// checks to see if player uses right click with flint and steel or other specified item.
			// If so, tries to create portal if possible. only works in non-ultra amplified world types
			if (world.getWorldType() != UltraAmplified.UltraAmplifiedWorldType && !entity.isCrouching())
			{
				ResourceLocation itemrl = new ResourceLocation(UltraAmplified.UAConfig.portalActivationItem.get());
				if (itemRegistry.containsKey(itemrl))
				{
					Item activationItem = itemRegistry.getValue(itemrl);
					if(event.getItemStack().getItem() == activationItem) 
					{
						trySpawnPortal(world, event.getPos(), entity);
					}
				}
				else
				{
					if (entity instanceof ServerPlayerEntity)
					{
						ITextComponent message = new StringTextComponent("§eUltra Amplified Dimension: §fI could not find the item with the resourcelocation that was put in the portal activation item config! Here is what was in the config: §c" + UltraAmplified.UAConfig.portalActivationItem.get());
						entity.sendMessage(message);
					}
				}
			}
		}


		// Fires when player enters UA dimension
		@SubscribeEvent(priority = EventPriority.HIGHEST)
		public static void worldLoad(PlayerEvent.PlayerChangedDimensionEvent event)
		{
			//check for if portal was made in UA and if not, make it.
			if (event.getPlayer().world.getDimension().getType() == UADimensionRegistration.ultraamplified())
			{
				IWorld worldUA = event.getPlayer().world;
				if (!checkForGeneratedPortal(worldUA))
				{
					generatePortal(worldUA);
				}
			}
		}
	}

	// ------------------------------------------------------------------------------------//
	// Portal creation and validation check

	private static final BlockState POLISHED_GRANITE = Blocks.POLISHED_GRANITE.getDefaultState();
	private static final BlockState POLISHED_DIORITE = Blocks.POLISHED_DIORITE.getDefaultState();
	private static final BlockState POLISHED_ANDESITE_SLAB_TOP = Blocks.POLISHED_ANDESITE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP);
	private static final BlockState POLISHED_ANDESITE_SLAB_BOTTOM = Blocks.POLISHED_ANDESITE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM);


	private static boolean checkForGeneratedPortal(IWorld worldUA)
	{
		BlockPos pos = new BlockPos(8, 255, 8);
		worldUA.getChunk(pos);

		while (pos.getY() >= 0)
		{
			if (worldUA.getBlockState(pos) == UABlocks.AMPLIFIEDPORTAL.get().getDefaultState())
			{
				return true;
			}
			pos = pos.down();
		}

		return false;
	}


	private static void generatePortal(IWorld worldUA)
	{
		AmplifiedPortalFrame amplifiedportalfeature = new AmplifiedPortalFrame();
		BlockPos pos = new BlockPos(8, 255, 8);
		worldUA.getChunk(pos);

		pos = worldUA.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, pos);
		if (pos.getY() > 252)
		{
			pos = pos.down(3);
		}
		else if (pos.getY() < 6)
		{
			pos = new BlockPos(pos.getX(), 6, pos.getZ());
		}

		amplifiedportalfeature.place(worldUA, new Random(), pos, IFeatureConfig.NO_FEATURE_CONFIG);
	}


	public static boolean isValid(IWorld world, BlockPos pos, Entity entity)
	{
		ForgeRegistry<Block> registry = ((ForgeRegistry<Block>) ForgeRegistries.BLOCKS);

		// Grabs resourcelocation from config and tries to find that block to use for corners
		// Doesn't need to be Blockstate as Polished Granite has only 1 state anyway
		Block blockCorner;
		if (!UltraAmplified.UAConfig.portalCornerBlocks.get().equals(""))
		{
			if (registry.containsKey(new ResourceLocation(UltraAmplified.UAConfig.portalCornerBlocks.get())))
			{
				blockCorner = registry.getValue(new ResourceLocation(UltraAmplified.UAConfig.portalCornerBlocks.get()));
			}
			else
			{
				if (entity instanceof ServerPlayerEntity)
				{
					ITextComponent message = new StringTextComponent("§eUltra Amplified Dimension: §fI could not find the block with the resourcelocation that was put in the portal frame corner config! Here is what was in the config: §c" + UltraAmplified.UAConfig.portalCornerBlocks.get());
					entity.sendMessage(message);
				}

				return false;
			}
		}
		else
		{
			blockCorner = POLISHED_GRANITE.getBlock();
		}

		//grabs resourcelocation from config and tries to find that block to use for ceiling
		BlockState blockCeiling;
		boolean customCeiling = false;
		if (!UltraAmplified.UAConfig.portalCeilingBlocks.get().equals(""))
		{
			if (registry.containsKey(new ResourceLocation(UltraAmplified.UAConfig.portalCeilingBlocks.get())))
			{
				blockCeiling = registry.getValue(new ResourceLocation(UltraAmplified.UAConfig.portalCeilingBlocks.get())).getDefaultState();
				customCeiling = true;
			}
			else
			{
				if (entity instanceof ServerPlayerEntity)
				{
					ITextComponent message = new StringTextComponent("§eUltra Amplified Dimension: §fI could not find the block with the resourcelocation that was put in the portal frame ceiling config! Here is what was in the config: §c" + UltraAmplified.UAConfig.portalCeilingBlocks.get());
					entity.sendMessage(message);
				}

				return false;
			}
		}
		else
		{
			blockCeiling = POLISHED_ANDESITE_SLAB_TOP;
		}

		//grabs resourcelocation from config and tries to find that block to use for floor
		BlockState blockFloor;
		boolean customFloor = false;
		if (!UltraAmplified.UAConfig.portalFloorBlocks.get().equals(""))
		{
			if (registry.containsKey(new ResourceLocation(UltraAmplified.UAConfig.portalFloorBlocks.get())))
			{
				blockFloor = registry.getValue(new ResourceLocation(UltraAmplified.UAConfig.portalFloorBlocks.get())).getDefaultState();
				customFloor = true;
			}
			else
			{
				if (entity instanceof ServerPlayerEntity)
				{
					ITextComponent message = new StringTextComponent("§eUltra Amplified Dimension: §fI could not find the block with the resourcelocation that was" + " put in the portal frame floor config! Here is what was in the config: §c" + UltraAmplified.UAConfig.portalFloorBlocks.get());
					entity.sendMessage(message);
				}

				return false;
			}
		}
		else
		{
			blockFloor = POLISHED_ANDESITE_SLAB_BOTTOM;
		}

		// bottom of portal frame
		for (int x = -1; x <= 1; x++)
		{
			for (int z = -1; z <= 1; z++)
			{
				// Floor corners
				if (Math.abs(x * z) == 1)
				{
					if (world.getBlockState(pos.add(x, -1, z)).getBlock() != blockCorner)
					{
						return false;
					}
				}

				// Plus shape on floor
				else
				{
					BlockState currentFloor = world.getBlockState(pos.add(x, -1, z));
					// Convert to default block if use changed the floor block in config so we can ignore properties of the block
					if (customFloor)
					{
						currentFloor = currentFloor.getBlock().getDefaultState();
					}

					if (currentFloor != blockFloor)
					{
						return false;
					}
				}
			}
		}

		// the center itself
		if (world.getBlockState(pos.add(0, 0, 0)) != POLISHED_DIORITE)
		{
			return false;
		}

		// top of portal frame
		for (int x = -1; x <= 1; x++)
		{
			for (int z = -1; z <= 1; z++)
			{
				// Top corners
				if (Math.abs(x * z) == 1)
				{
					if (world.getBlockState(pos.add(x, 1, z)).getBlock() != blockCorner)
					{
						return false;
					}
				}
				// Plus shape on ceiling
				else
				{
					BlockState currentCeiling = world.getBlockState(pos.add(x, 1, z));
					// Convert to default block if use changed the ceiling block in config so we can ignore properties of the block
					if (customCeiling)
					{
						currentCeiling = currentCeiling.getBlock().getDefaultState();
					}

					if (currentCeiling != blockCeiling)
					{
						return false;
					}
				}
			}
		}

		return true;
	}


	public static void placePortalBlocks(IWorld world, BlockPos pos)
	{
		// the portal itself
		world.setBlockState(pos, UABlocks.AMPLIFIEDPORTAL.get().getDefaultState(), 18);
	}


	public static boolean trySpawnPortal(IWorld world, BlockPos pos, Entity entity)
	{

		// cannot create amplified portal in Ultra Amplified Worldtype
		if (world.getWorld().getWorldType() == UltraAmplified.UltraAmplifiedWorldType)
		{
			return false;
		}

		boolean canMakePortal = isPortal(world, pos, entity);
		if (canMakePortal)
		{
			placePortalBlocks(world, pos);
			return true;
		}
		else
		{
			return false;
		}
	}


	@Nullable
	public static boolean isPortal(IWorld world, BlockPos pos, Entity entity)
	{
		if (isValid(world, pos, entity))
		{
			return true;
		}

		return false;
	}

}

package net.telepathicgrunt.ultraamplified.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SlabBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.EndPortalTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class AmplifiedPortalBlock extends Block {
	protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D);

	public AmplifiedPortalBlock() {
		super(Block.Properties.create(Material.PORTAL, MaterialColor.BLACK)
				.lightValue(15)
				.hardnessAndResistance(-1.0F, 3600000.0F)
				.noDrops()
			  );
		
		setRegistryName("amplified_portal");
	}

	public TileEntity createNewTileEntity(IBlockReader worldIn) {
		return new EndPortalTileEntity();
	}

	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return SHAPE;
	}

	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.TRANSLUCENT;
	}

	
	
	//Does not work in survival due to a glitch that I have no goddamn clue why or how it occurs
//	//when entity touches this block, they will teleport to ultra amplified dimension no matter what other dimension they are in.
//	//Touching block in ultra amplified dimension will teleport player to overworld.
//	@Override
//	public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) 
//	{
//
//		//cannot teleport players in Ultra Amplified Worldtype 
//		if(worldIn.getWorld().getWorldType() == UltraAmplified.UltraAmplified) {
//			return;
//		}
//			
//		
//		
//		if (!worldIn.isRemote && !entityIn.isPassenger() && !entityIn.isBeingRidden() && entityIn.isNonBoss()
//				&& VoxelShapes.compare(VoxelShapes.create(entityIn.getBoundingBox().offset(
//								(double) (-pos.getX()),
//								(double) (-pos.getY()), 
//								(double) (-pos.getZ()))),
//								state.getShape(worldIn, pos), 
//								IBooleanFunction.AND)) 
//		{
//			if (entityIn.isAlive() && entityIn instanceof PlayerEntity) {
//				DimensionType destination = worldIn.dimension.getType() != UltraAmplifiedDimension.ultraamplified()
//						? UltraAmplifiedDimension.ultraamplified()
//						: DimensionType.OVERWORLD;
//						
//				MinecraftServer minecraftserver = entityIn.getServer();
//				ServerWorld serverworld = minecraftserver.getWorld(destination);
//				
//				//gets top block in other world
//				BlockPos blockpos = new BlockPos(10, 60, 8);
//				Block block;
//				
//				//load chunk beforehand
//				ChunkPos chunkpos = new ChunkPos(blockpos);
//				minecraftserver.getWorld(destination).getChunkProvider().func_217228_a(TicketType.POST_TELEPORT, chunkpos, 1, entityIn.getEntityId());
//				
//		         if (((ServerPlayerEntity)entityIn).isSleeping()) {
//		            ((ServerPlayerEntity)entityIn).wakeUpPlayer(true, true, false);
//		         }
//		         
//				//checks a 2 by 2 by 5 area to replace any portal with air to prevent infinite loops or being stuck by badly place portals
//				blockpos = serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 255, 8));
//				for(int x = -1; x < 1; x++) {
//					for(int z = -1; z < 1; z++) {
//						for(int y = -2; y < 3; y++) {
//							block = serverworld.getBlockState(blockpos.add(x, y, z)).getBlock();
//							
//							if(block == BlocksInit.AMPLIFIEDPORTAL) {
//								serverworld.setBlockState(blockpos.add(x, y, z), Blocks.AIR.getDefaultState());
//							}
//						}
//					}	
//				}
//				
//				
//				blockpos = serverworld.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(10, 255, 8));
//
//				
//				((ServerPlayerEntity)entityIn).teleport(
//						minecraftserver.getWorld(destination), 
//						blockpos.getX(), 
//						blockpos.getY(), 
//						blockpos.getZ(), 
//						entityIn.rotationYaw, 
//						entityIn.rotationPitch);
//				
//			}
//		}
//	}

	public boolean trySpawnPortal(IWorld worldIn, BlockPos pos) {
		
		//cannot create amplified portal in Ultra Amplified Worldtype 
		if(worldIn.getWorld().getWorldType() == UltraAmplified.UltraAmplified) {
			return false;
		}
			
			
		boolean canMakePortal = this.isPortal(worldIn, pos);
		if (canMakePortal) {
			placePortalBlocks(worldIn, pos);
			return true;
		} else {
			return false;
		}
	}

	@Nullable
	public boolean isPortal(IWorld worldIn, BlockPos pos) {
		if (isValid(worldIn, pos)) {
			return true;
		}

		return false;
	}

	/**
	 * faster particle movement than normal EndPortal block
	 */
	@OnlyIn(Dist.CLIENT)
	public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		double d0 = (double) ((float) pos.getX() + (rand.nextFloat() * 3 - 1));
		double d1 = (double) ((float) pos.getY() + (rand.nextFloat() * 3 - 1));
		double d2 = (double) ((float) pos.getZ() + (rand.nextFloat() * 3 - 1));
		worldIn.addParticle(ParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D);
	}

	// has no item form
	public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
		return ItemStack.EMPTY;
	}

	
	

	// ------------------------------------------------------------------------------------//
	// Portal creation and validation check

	private static final BlockState POLISHED_GRANITE = Blocks.POLISHED_GRANITE.getDefaultState();
	private static final BlockState POLISHED_DIORITE = Blocks.POLISHED_DIORITE.getDefaultState();
	private static final BlockState POLISHED_ANDESITE_SLAB_TOP = Blocks.POLISHED_ANDESITE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.TOP);
	private static final BlockState POLISHED_ANDESITE_SLAB_BOTTOM = Blocks.POLISHED_ANDESITE_SLAB.getDefaultState().with(SlabBlock.TYPE, SlabType.BOTTOM);

	public boolean isValid(IWorld worldIn, BlockPos pos) {

		// bottom of portal frame
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (Math.abs(x * z) == 1) {
					if (worldIn.getBlockState(pos.add(x, -1, z)) != POLISHED_GRANITE) {
						return false;
					}
				} else {
					if (worldIn.getBlockState(pos.add(x, -1, z)) != POLISHED_ANDESITE_SLAB_BOTTOM) {
						return false;
					}
				}
			}
		}

		// the center itself
		if (worldIn.getBlockState(pos.add(0, 0, 0)) != POLISHED_DIORITE) {
			return false;
		}

		// top of portal frame
		for (int x = -1; x <= 1; x++) {
			for (int z = -1; z <= 1; z++) {
				if (Math.abs(x * z) == 1) {
					if (worldIn.getBlockState(pos.add(x, 1, z)) != POLISHED_GRANITE) {
						return false;
					}
				} else {
					if (worldIn.getBlockState(pos.add(x, 1, z)) != POLISHED_ANDESITE_SLAB_TOP) {
						return false;
					}
				}
			}
		}

		return true;
	}

	public void placePortalBlocks(IWorld worldIn, BlockPos pos) {
		// the portal itself
		worldIn.setBlockState(pos.add(0, 0, 0), BlocksInit.AMPLIFIEDPORTAL.getDefaultState(), 18);
	}

}

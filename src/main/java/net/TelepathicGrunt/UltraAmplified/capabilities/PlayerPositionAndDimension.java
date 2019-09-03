package net.telepathicgrunt.ultraamplified.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.registries.ClearableRegistry;

public class PlayerPositionAndDimension implements IPlayerPosAndDim{

	//handles the actual data that this capability will have and use
	private static final ClearableRegistry<DimensionType> REGISTRY = new ClearableRegistry<>(new ResourceLocation("dimension_type"), DimensionType.class);

	public DimensionType prevDimension = null;
	public BlockPos prevBlockPos = null;
	
	@Override
	public void setDim(DimensionType incomingDim) {
		prevDimension = incomingDim;
	}

	@Override
	public void setPos(BlockPos incomingPos) {
		prevBlockPos = incomingPos;
	}

	@Override
	public DimensionType getDim() {
		return prevDimension;
	}

	@Override
	public BlockPos getPos() {
		return prevBlockPos;
	}
		
}
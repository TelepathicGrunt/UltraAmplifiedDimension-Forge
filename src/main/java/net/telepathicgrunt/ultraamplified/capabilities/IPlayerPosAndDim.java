package net.telepathicgrunt.ultraamplified.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public interface IPlayerPosAndDim {

	//what methods the capability will have and what the capability is
	
	void setDim(DimensionType incomingDim);
	DimensionType getDim();
	
	void setPos(BlockPos incomingPos);
	BlockPos getPos();

	void setAltTele(boolean shouldDoAltTeleporting);
	Boolean getAltTele();
	
	CompoundNBT saveNBTData();
	void loadNBTData(CompoundNBT nbtTag);
}

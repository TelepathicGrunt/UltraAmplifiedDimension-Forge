package net.telepathicgrunt.ultraamplified.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public interface IPlayerPosAndDim {

	//what methods the capability will have and what the capability is
	
	void setNonUADim(DimensionType incomingDim);
	DimensionType getNonUADim();

	
	
	void setNonUAPos(BlockPos incomingPos);
	BlockPos getNonUAPos();

	void setNonUAPitch(float incomingPitch);
	float getNonUAPitch();

	void setNonUAYaw(float incomingYaw);
	float getNonUAYaw();
	
	
	
	void setUAPos(BlockPos incomingPos);
	BlockPos getUAPos();
	
	void setUAPitch(float incomingPitch);
	float getUAPitch();

	void setUAYaw(float incomingYaw);
	float getUAYaw();
	
	CompoundNBT saveNBTData();
	void loadNBTData(CompoundNBT nbtTag);
}

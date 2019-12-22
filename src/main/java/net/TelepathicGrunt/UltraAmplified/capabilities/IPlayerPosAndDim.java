package net.telepathicgrunt.ultraamplified.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;

public interface IPlayerPosAndDim {

	//what methods the capability will have and what the capability is
	
	void setDim(DimensionType incomingDim);
	void setPos(BlockPos incomingPos);

	DimensionType getDim();
	BlockPos getPos();

	NBTTagCompound saveNBTData();
	void loadNBTData(NBTTagCompound nbtTag);
}

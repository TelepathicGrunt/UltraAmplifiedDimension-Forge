package com.telepathicgrunt.ultraamplifieddimension.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;


public interface IPlayerPosAndDim
{

	//what methods the capability will have and what the capability is

	void setNonUADim(RegistryKey<World> incomingDim);
	RegistryKey<World> getNonUADim();
	
	void setNonUAPos(Vector3d incomingPos);
	Vector3d getNonUAPos();
	
	void setNonUAPitch(float incomingPitch);
	float getNonUAPitch();

	void setNonUAYaw(float incomingYaw);
	float getNonUAYaw();

	void setUAPos(Vector3d incomingPos);
	Vector3d getUAPos();

	void setUAPitch(float incomingPitch);
	float getUAPitch();

	void setUAYaw(float incomingYaw);
	float getUAYaw();

	CompoundNBT saveNBTData();
	void loadNBTData(CompoundNBT nbtTag);
}

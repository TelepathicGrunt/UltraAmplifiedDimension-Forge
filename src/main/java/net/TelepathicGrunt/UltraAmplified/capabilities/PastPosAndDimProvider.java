package net.telepathicgrunt.ultraamplified.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PastPosAndDimProvider implements ICapabilityProvider, ICapabilitySerializable<NBTTagCompound> {

	//the capability itself
	@CapabilityInject(IPlayerPosAndDim.class)
	public static Capability<IPlayerPosAndDim> PAST_POS_AND_DIM = null;
	
	//The instance of the capability? I think?
	private PlayerPositionAndDimension instance = (PlayerPositionAndDimension) PAST_POS_AND_DIM.getDefaultInstance();


	//returns the capability attached to player
	@Override
	public <T> T getCapability(Capability<T> cap, EnumFacing side) {
		if (cap == PAST_POS_AND_DIM) {
			if(instance == null) {
				instance = new PlayerPositionAndDimension();
			}
			
			return PAST_POS_AND_DIM.cast(instance);
		} 
		else {
			return null;
		}
	}

	@Override
	public boolean hasCapability(Capability<?> cap, EnumFacing facing) {
		if (cap == PAST_POS_AND_DIM) {
			return true;
		} else {
			return false;
		}
	}


	@Override
	public NBTTagCompound serializeNBT() {
		return instance.saveNBTData();
	}


	@Override
	public void deserializeNBT(NBTTagCompound nbt) {
		instance.loadNBTData(nbt);
	}


}

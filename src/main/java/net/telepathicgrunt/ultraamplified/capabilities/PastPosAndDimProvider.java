package net.telepathicgrunt.ultraamplified.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;


public class PastPosAndDimProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundNBT>
{

	//the capability itself
	@CapabilityInject(IPlayerPosAndDim.class)
	public static Capability<IPlayerPosAndDim> PAST_POS_AND_DIM = null;

	//The instance of the capability? I think?
	private PlayerPositionAndDimension instance = (PlayerPositionAndDimension) PAST_POS_AND_DIM.getDefaultInstance();


	//returns the capability attached to player
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side)
	{
		if (cap == PAST_POS_AND_DIM)
		{
			if (instance == null)
			{
				instance = new PlayerPositionAndDimension();
			}

			return LazyOptional.of(() -> instance).cast();
		}
		else
		{
			return LazyOptional.empty();
		}
	}


	@Override
	public CompoundNBT serializeNBT()
	{
		return instance.saveNBTData();
	}


	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		instance.loadNBTData(nbt);
	}
}

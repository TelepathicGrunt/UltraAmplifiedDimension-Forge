package net.telepathicgrunt.ultraamplified.capabilities;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

public class CapabilityPlayerPosAndDim {
	//the capability itself
	@CapabilityInject(IPlayerPosAndDim.class)
	public static Capability<IPlayerPosAndDim> PAST_POS_AND_DIM = null;

	//registers the capability and defines how it will read/write data from nbt
	public static void register() {
		CapabilityManager.INSTANCE.register(IPlayerPosAndDim.class, new Capability.IStorage<IPlayerPosAndDim>() 
		{
			@Nullable
			public NBTBase writeNBT(Capability<IPlayerPosAndDim> capability, IPlayerPosAndDim instance, EnumFacing side) 
			{
				return instance.saveNBTData();
			}

			public void readNBT(Capability<IPlayerPosAndDim> capability, IPlayerPosAndDim instance, EnumFacing side, NBTBase nbt) 
			{
				instance.loadNBTData((NBTTagCompound) nbt);
			}
		}, () -> new PlayerPositionAndDimension());
	}
}

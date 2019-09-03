package net.telepathicgrunt.ultraamplified.capabilities;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ClearableRegistry;

public class PastPosAndDimProvider implements ICapabilityProvider {

	//needed to get and convert the dimension's resource location into a string format
	private static final ClearableRegistry<DimensionType> REGISTRY = new ClearableRegistry<>(new ResourceLocation("dimension_type"), DimensionType.class);

	//the capability itself
	@CapabilityInject(IPlayerPosAndDim.class)
	public static Capability<IPlayerPosAndDim> PAST_POS_AND_DIM = null;

	//The instance of the capability? I think?
	private PlayerPositionAndDimension instance;
	
	//registers the capability and defines how it will read/write data from nbt
	public static void register() {
		CapabilityManager.INSTANCE.register(IPlayerPosAndDim.class, new Capability.IStorage<IPlayerPosAndDim>() 
		{
			@Nullable
			public INBT writeNBT(Capability<IPlayerPosAndDim> capability, IPlayerPosAndDim instance, Direction side) 
			{
				CompoundNBT nbt = new CompoundNBT();

				nbt.putInt("PrevX", instance.getPos().getX());
				nbt.putInt("PrevY", instance.getPos().getY());
				nbt.putInt("PrevZ", instance.getPos().getZ());
				nbt.putString("PreviousDimensionNamespace", instance.getDim().getRegistryName().getNamespace());
				nbt.putString("PreviousDimensionPath", instance.getDim().getRegistryName().getPath());

				return nbt;
			}

			public void readNBT(Capability<IPlayerPosAndDim> capability, IPlayerPosAndDim instance, Direction side, INBT nbt) 
			{
				CompoundNBT cnbt = (CompoundNBT) nbt;
				instance.setPos(new BlockPos(cnbt.getInt("PrevX"), cnbt.getInt("PrevY"), cnbt.getInt("PrevZ")));
				instance.setDim(REGISTRY.getOrDefault(
						new ResourceLocation(cnbt.getString("PreviousDimensionNamespace"), cnbt.getString("PreviousDimensionPath"))
						));

			}
		}, () -> new PlayerPositionAndDimension());
	}

	//returns the capability attached to player
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == PAST_POS_AND_DIM) {
			if(instance == null) {
				instance = new PlayerPositionAndDimension();
			}
			
			return LazyOptional.of(() -> instance).cast();
		} else {
			return LazyOptional.empty();
		}
	}
}

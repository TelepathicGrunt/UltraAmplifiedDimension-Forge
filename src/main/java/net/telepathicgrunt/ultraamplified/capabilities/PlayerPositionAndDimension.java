package net.telepathicgrunt.ultraamplified.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;

public class PlayerPositionAndDimension implements IPlayerPosAndDim{


	public DimensionType nonUADimension = null;
	public BlockPos nonUABlockPos = new BlockPos(0,0,0);
	public BlockPos UABlockPos = new BlockPos(0,0,0);
	public boolean doAltTeleporting = false;
	
	@Override
	public void setNonUADim(DimensionType incomingDim) {
		nonUADimension = incomingDim;
	}
	@Override
	public DimensionType getNonUADim() {
		return nonUADimension;
	}

	@Override
	public void setNonUAPos(BlockPos incomingPos) {
		nonUABlockPos = incomingPos;
	}
	@Override
	public BlockPos getNonUAPos() {
		return nonUABlockPos;
	}
	
	@Override
	public void setUAPos(BlockPos incomingPos) {
		UABlockPos = incomingPos;
	}
	@Override
	public BlockPos getUAPos() {
		return UABlockPos;
	}

	@Override
	public void setAltTele(boolean shouldDoAltTeleporting)
	{
		doAltTeleporting = shouldDoAltTeleporting;
	}
	@Override
	public Boolean getAltTele()
	{
		return doAltTeleporting;
	}

	@Override
	public CompoundNBT saveNBTData() {
		CompoundNBT nbt = new CompoundNBT();

		nbt.putInt("PrevX", this.getNonUAPos().getX());
		nbt.putInt("PrevY", this.getNonUAPos().getY());
		nbt.putInt("PrevZ", this.getNonUAPos().getZ());
		
		if(this.getNonUADim() != null) {
			nbt.putString("PreviousDimensionNamespace", this.getNonUADim().getRegistryName().getNamespace());
			nbt.putString("PreviousDimensionPath", this.getNonUADim().getRegistryName().getPath());
		}
		
		nbt.putBoolean("AltTeleporting", this.getAltTele());

		return nbt;
	}

	@Override
	public void loadNBTData(CompoundNBT nbtTag) {
		CompoundNBT cnbt = (CompoundNBT) nbtTag;
		BlockPos storedBlockPos = new BlockPos(cnbt.getInt("PrevX"), cnbt.getInt("PrevY"), cnbt.getInt("PrevZ"));
		
		//grabs past dimension resource location and tries to get that dimension from the registry
		DimensionType storedDimension = DimensionType.byName(new ResourceLocation(cnbt.getString("PreviousDimensionNamespace"), 
																			      cnbt.getString("PreviousDimensionPath")));

		boolean doAltTeleporting = cnbt.getBoolean("AltTeleporting");
		
		this.setNonUADim(storedDimension);
		this.setNonUAPos(storedBlockPos);
		this.setAltTele(doAltTeleporting);
	}
		
}
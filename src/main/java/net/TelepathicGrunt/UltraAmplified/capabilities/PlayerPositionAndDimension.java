package net.telepathicgrunt.ultraamplified.capabilities;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DimensionType;

public class PlayerPositionAndDimension implements IPlayerPosAndDim{


	public DimensionType prevDimension = null;
	public BlockPos prevBlockPos = new BlockPos(0,0,0);
	
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

	@Override
	public NBTTagCompound saveNBTData() {
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setInteger("PrevX", this.getPos().getX());
		nbt.setInteger("PrevY", this.getPos().getY());
		nbt.setInteger("PrevZ", this.getPos().getZ());
		
		if(this.getDim() != null) {
			nbt.setString("PreviousDimensionNamespace", this.getDim().getName());
			nbt.setString("PreviousDimensionPath", this.getDim().getSuffix());
		}

		return nbt;
	}

	@Override
	public void loadNBTData(NBTTagCompound nbtTag) {
		NBTTagCompound cnbt = (NBTTagCompound) nbtTag;
		BlockPos storedBlockPos = new BlockPos(cnbt.getInteger("PrevX"), cnbt.getInteger("PrevY"), cnbt.getInteger("PrevZ"));
		this.setPos(storedBlockPos);
		
		String prevDimName = cnbt.getString("PreviousDimensionNamespace");
		if(!prevDimName.equals("")) {
			//grabs past dimension resource location and tries to get that dimension from the registry
			DimensionType storedDimension = DimensionType.byName(prevDimName);
			this.setDim(storedDimension);
			
		}else {
			this.setDim(null);
		}
	}
		
}
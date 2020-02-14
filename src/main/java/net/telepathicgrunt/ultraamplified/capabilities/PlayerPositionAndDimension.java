package net.telepathicgrunt.ultraamplified.capabilities;

import org.apache.logging.log4j.Level;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.dimension.DimensionType;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.dimension.UltraAmplifiedDimension;


public class PlayerPositionAndDimension implements IPlayerPosAndDim
{

	public DimensionType nonUADimension = DimensionType.OVERWORLD;
	public BlockPos nonUABlockPos = null;
	public BlockPos UABlockPos = null;


	@Override
	public void setNonUADim(DimensionType incomingDim)
	{
		//error, this should not be UA. Set to Overworld instead and print to log.
		if (incomingDim == UltraAmplifiedDimension.ultraamplified())
		{
			nonUADimension = DimensionType.OVERWORLD;
			UltraAmplified.LOGGER.log(Level.ERROR, "Tried to set the NonUADimension variable to UA dimension. Please contact mod owner to report this!");
		}
		else
		{
			nonUADimension = incomingDim;
		}
	}


	@Override
	public DimensionType getNonUADim()
	{
		return nonUADimension;
	}


	@Override
	public void setNonUAPos(BlockPos incomingPos)
	{
		nonUABlockPos = incomingPos;
	}


	@Override
	public BlockPos getNonUAPos()
	{
		return nonUABlockPos;
	}


	@Override
	public void setUAPos(BlockPos incomingPos)
	{
		UABlockPos = incomingPos;
	}


	@Override
	public BlockPos getUAPos()
	{
		return UABlockPos;
	}


	@Override
	public CompoundNBT saveNBTData()
	{
		CompoundNBT data = new CompoundNBT();

		if(this.getNonUAPos() != null)
		{
			data.putInt("NonUA_X", this.getNonUAPos().getX());
			data.putInt("NonUA_Y", this.getNonUAPos().getY());
			data.putInt("NonUA_Z", this.getNonUAPos().getZ());
		}
		
		if(this.getUAPos() != null)
		{
			data.putInt("UA_X", this.getUAPos().getX());
			data.putInt("UA_Y", this.getUAPos().getY());
			data.putInt("UA_Z", this.getUAPos().getZ());
		}

		if (this.getNonUADim() != null)
		{
			data.putString("NonUADimensionNamespace", this.getNonUADim().getRegistryName().getNamespace());
			data.putString("NonUADimensionPath", this.getNonUADim().getRegistryName().getPath());
		}

		return data;
	}


	/**
	 * Loads the data from the player nbt along with lots of checks to make sure 
	 * we don't try and call something that doesn't exist.
	 */
	@Override
	public void loadNBTData(CompoundNBT nbtTag)
	{
		CompoundNBT data = (CompoundNBT) nbtTag;
		BlockPos storedBlockPosNonUA = null;
		BlockPos storedBlockPosUA = null;
		DimensionType storedDimension = null;

		if(data.contains("NonUA_X"))
		{
			storedBlockPosNonUA = new BlockPos(data.getInt("NonUA_X"), data.getInt("NonUA_Y"), data.getInt("NonUA_Z"));
		}
		
		if(data.contains("UA_X"))
		{
			storedBlockPosUA = new BlockPos(data.getInt("UA_X"), data.getInt("UA_Y"), data.getInt("UA_Z"));
		}
		
		if(data.contains("NonUADimensionNamespace"))
		{
			storedDimension = DimensionType.byName(new ResourceLocation(
													data.getString("NonUADimensionNamespace"), 
													data.getString("NonUADimensionPath")));
		}

		this.setNonUADim(storedDimension);
		this.setNonUAPos(storedBlockPosNonUA);
		this.setNonUAPos(storedBlockPosUA);
	}


	/**
	 * Will return the nbt if it is the most recent version and if not,
	 * convert the nbt from the old version to the new version as best as we can 
	 * and return the new version of the nbt.
	 */
	public static CompoundNBT fixData(CompoundNBT data)
	{
		if (isOldData(data))
		{
			return getConvertedData(data);
		}
		else
		{
			return data;
		}
	}
	
	
	/**
	 * Checks if the nbt has old data format and returns true if it does
	 */
	private static boolean isOldData(CompoundNBT data)
	{
		if(data.contains("PrevX"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	/**
	 * Convert the old data as best as we can to the new dimension.
	 */
	private static CompoundNBT getConvertedData(CompoundNBT oldData) 
	{
		// Get what the old data has stored in it
		BlockPos storedOldBlockPos = new BlockPos(oldData.getInt("PrevX"), oldData.getInt("PrevY"), oldData.getInt("PrevZ"));
		DimensionType storedOldDimension = DimensionType.byName(new ResourceLocation(
				oldData.getString("PreviousDimensionNamespace"), 
				oldData.getString("PreviousDimensionPath")));

		// Create new nbt to use our new format
		CompoundNBT newData = new CompoundNBT();
		
		// Old data had position of player in UA dimension. Save it.
		// Player is in non-UA dimension currently so set the entrance correctly.
		if(storedOldDimension == UltraAmplifiedDimension.ultraamplified())
		{
			newData.putInt("UA_X", storedOldBlockPos.getX());
			newData.putInt("UA_Y", storedOldBlockPos.getY());
			newData.putInt("UA_Z", storedOldBlockPos.getZ());
			
			// Set the non-UA dimension to Overworld as we do not have access to where the person came from originally.
			// But this is ok as the person is currently not in UA dimension (unless they got in without the Amplified Portal...)
			// Because they aren't in UA dimension, the next time they click the Amplified Portal, it'll set this to correct dimension.
			newData.putString("NonUADimensionNamespace", DimensionType.OVERWORLD.getRegistryName().getNamespace());
			newData.putString("NonUADimensionPath", DimensionType.OVERWORLD.getRegistryName().getPath());
		}
		// Old data had non-UA dimension and position. Save it.
		// Player is in the UA dimension currently so set the exit correctly.
		else
		{
			newData.putInt("NonUA_X", storedOldBlockPos.getX());
			newData.putInt("NonUA_Y", storedOldBlockPos.getY());
			newData.putInt("NonUA_Z", storedOldBlockPos.getZ());
			
			newData.putString("NonUADimensionNamespace", storedOldDimension.getRegistryName().getNamespace());
			newData.putString("NonUADimensionPath", storedOldDimension.getRegistryName().getPath());
		}

		return newData;
	}
}
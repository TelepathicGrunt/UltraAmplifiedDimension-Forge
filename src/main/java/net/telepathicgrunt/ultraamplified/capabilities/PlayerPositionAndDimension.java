package net.telepathicgrunt.ultraamplified.capabilities;

import org.apache.logging.log4j.Level;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.dimension.DimensionType;
import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.world.dimension.UltraAmplifiedDimension;


public class PlayerPositionAndDimension implements IPlayerPosAndDim
{

	public DimensionType nonUADimension = DimensionType.OVERWORLD;
	public Vec3d nonUABlockPos = null;
	public float nonUAPitch = 0;
	public float nonUAYaw = 0;
	public Vec3d UABlockPos = null;
	public float UAPitch = 3.75F;
	public float UAYaw = -45F;


	//////////////////////////////////////////
	//Non-UA stuff
	
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
	public void setNonUAPitch(float incomingPitch)
	{
		nonUAPitch = incomingPitch;
	}
	@Override
	public float getNonUAPitch()
	{
		return nonUAPitch;
	}
	
	@Override
	public void setNonUAYaw(float incomingYaw)
	{
		nonUAYaw = incomingYaw;
	}
	@Override
	public float getNonUAYaw()
	{
		return nonUAYaw;
	}

	////////////////////////////////////////////////////////////
	//UA stuff

	@Override
	public void setNonUAPos(Vec3d incomingPos)
	{
		nonUABlockPos = incomingPos;
	}
	@Override
	public Vec3d getNonUAPos()
	{
		return nonUABlockPos;
	}


	@Override
	public void setUAPos(Vec3d incomingPos)
	{
		UABlockPos = incomingPos;
	}
	@Override
	public Vec3d getUAPos()
	{
		return UABlockPos;
	}

	
	@Override
	public void setUAPitch(float incomingPitch)
	{
		UAPitch = incomingPitch;
	}
	@Override
	public float getUAPitch()
	{
		return UAPitch;
	}
	
	@Override
	public void setUAYaw(float incomingYaw)
	{
		UAYaw = incomingYaw;
	}
	@Override
	public float getUAYaw()
	{
		return UAYaw;
	}
	
	///////////////////////////////////////////////////////
	//Save and load stuff

	@Override
	public CompoundNBT saveNBTData()
	{
		CompoundNBT data = new CompoundNBT();

		if(this.getNonUAPos() != null)
		{
			data.putDouble("NonUA_X", this.getNonUAPos().getX());
			data.putDouble("NonUA_Y", this.getNonUAPos().getY());
			data.putDouble("NonUA_Z", this.getNonUAPos().getZ());
		}
		data.putFloat("NonUAPitch", nonUAPitch);
		data.putFloat("NonUAYaw", nonUAYaw);
		
		if(this.getUAPos() != null)
		{
			data.putDouble("UA_X", this.getUAPos().getX());
			data.putDouble("UA_Y", this.getUAPos().getY());
			data.putDouble("UA_Z", this.getUAPos().getZ());
		}
		data.putFloat("UAPitch", UAPitch);
		data.putFloat("UAYaw", UAYaw);

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
		data = fixData(data);
		
		//temp variables to hold what is read from nbt
		Vec3d storedBlockPosNonUA = null;
		float storedNonUAPitch = 3.75F;
		float storedNonUAYaw = 0F;
		Vec3d storedBlockPosUA = null;
		float storedUAPitch = 3.75F;
		float storedUAYaw = 0F;
		DimensionType storedDimension = null;
		

		//Non-UA stuff
		storedDimension = DimensionType.byName(new ResourceLocation(
												data.getString("NonUADimensionNamespace"), 
												data.getString("NonUADimensionPath")));
		//Need check for null so we can let rest for code know the player has not exit the dimension yet for the first time.
		if(data.contains("NonUA_X") && data.contains("NonUA_Y") && data.contains("NonUA_Z"))
		{
			storedBlockPosNonUA = new Vec3d(data.getFloat("NonUA_X"), data.getFloat("NonUA_Y"), data.getFloat("NonUA_Z"));
		}
		storedNonUAPitch = data.getFloat("NonUAPitch");
		storedNonUAYaw = data.getFloat("NonUAYaw");
		

		//UA stuff
		//Need check for null so we can let rest for code know the player has not exit the dimension yet for the first time.
		if(data.contains("UA_X") && data.contains("UA_Y") && data.contains("UA_Z"))
		{
			storedBlockPosUA = new Vec3d(data.getFloat("UA_X"), data.getFloat("UA_Y"), data.getFloat("UA_Z"));
		}
		storedUAPitch = data.getFloat("UAPitch");
		storedUAYaw = data.getFloat("UAYaw");

		
		
		this.setNonUADim(storedDimension);
		this.setNonUAPitch(storedNonUAPitch);
		this.setNonUAYaw(storedNonUAYaw);
		this.setNonUAPos(storedBlockPosNonUA);
		
		this.setUAPitch(storedUAPitch);
		this.setUAYaw(storedUAYaw);
		this.setUAPos(storedBlockPosUA);
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
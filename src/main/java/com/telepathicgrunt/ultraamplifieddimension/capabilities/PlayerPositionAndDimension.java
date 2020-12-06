package com.telepathicgrunt.ultraamplifieddimension.capabilities;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.dimension.UADDimension;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;


public class PlayerPositionAndDimension implements IPlayerPosAndDim
{

	public RegistryKey<World> nonUADimension = World.OVERWORLD;
	public Vector3d nonUABlockPos = null;
	public float nonUAPitch = 0;
	public float nonUAYaw = 0;
	public Vector3d UABlockPos = null;
	public float UAPitch = 3.75F;
	public float UAYaw = 90F;


	//////////////////////////////////////////
	//Non-UA stuff

	@Override
	public void setNonUADim(RegistryKey<World> incomingDim)
	{
		//error, this should not be UA. Set to Overworld instead and print to log.
		if (incomingDim == UADDimension.UAD_WORLD_KEY)
		{
			nonUADimension = World.OVERWORLD;
			UltraAmplifiedDimension.LOGGER.log(Level.ERROR, "Tried to set the NonUADimension variable to UA dimension. Please contact mod owner to report this!");
		}
		else
		{
			nonUADimension = incomingDim;
		}
	}


	@Override
	public RegistryKey<World> getNonUADim()
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

	@Override
	public void setNonUAPos(Vector3d incomingPos)
	{
		nonUABlockPos = incomingPos;
	}


	@Override
	public Vector3d getNonUAPos()
	{
		return nonUABlockPos;
	}

	
	////////////////////////////////////////////////////////////
	//UA stuff

	@Override
	public void setUAPos(Vector3d incomingPos)
	{
		UABlockPos = incomingPos;
	}


	@Override
	public Vector3d getUAPos()
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

		if (this.getNonUAPos() != null)
		{
			data.putDouble("NonUA_X", this.getNonUAPos().x);
			data.putDouble("NonUA_Y", this.getNonUAPos().y);
			data.putDouble("NonUA_Z", this.getNonUAPos().z);
		}
		data.putFloat("NonUAPitch", nonUAPitch);
		data.putFloat("NonUAYaw", nonUAYaw);

		if (this.getUAPos() != null)
		{
			data.putDouble("UA_X", this.getUAPos().x);
			data.putDouble("UA_Y", this.getUAPos().y);
			data.putDouble("UA_Z", this.getUAPos().z);
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
	 * Loads the data from the player nbt along with lots of checks to make sure we don't try and call something that
	 * doesn't exist.
	 */
	@Override
	public void loadNBTData(CompoundNBT nbtTag)
	{
		//temp variables to hold what is read from nbt
		Vector3d storedPositionNonUA = null;
		float storedNonUAPitch;
		float storedNonUAYaw;
		Vector3d storePositionUA = null;
		float storedUAPitch;
		float storedUAYaw;
		RegistryKey<World> storedDimension;

		//Non-UA stuff
		storedDimension = RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(nbtTag.getString("NonUADimensionPath")));
		//Need check for null so we can let rest for code know the player has not exit the dimension yet for the first time.
		if (nbtTag.contains("NonUA_X") && nbtTag.contains("NonUA_Y") && nbtTag.contains("NonUA_Z"))
		{
			storedPositionNonUA = new Vector3d(nbtTag.getFloat("NonUA_X"), nbtTag.getFloat("NonUA_Y"), nbtTag.getFloat("NonUA_Z"));
		}
		storedNonUAPitch = nbtTag.getFloat("NonUAPitch");
		storedNonUAYaw = nbtTag.getFloat("NonUAYaw");

		//UA stuff
		//Need check for null so we can let rest for code know the player has not exit the dimension yet for the first time.
		if (nbtTag.contains("UA_X") && nbtTag.contains("UA_Y") && nbtTag.contains("UA_Z"))
		{
			storePositionUA = new Vector3d(nbtTag.getFloat("UA_X"), nbtTag.getFloat("UA_Y"), nbtTag.getFloat("UA_Z"));
		}
		storedUAPitch = nbtTag.getFloat("UAPitch");
		storedUAYaw = nbtTag.getFloat("UAYaw");

		this.setNonUADim(storedDimension);
		this.setNonUAPitch(storedNonUAPitch);
		this.setNonUAYaw(storedNonUAYaw);
		this.setNonUAPos(storedPositionNonUA);

		this.setUAPitch(storedUAPitch);
		this.setUAYaw(storedUAYaw);
		this.setUAPos(storePositionUA);
	}
}
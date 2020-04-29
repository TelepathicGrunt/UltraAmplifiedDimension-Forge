package net.telepathicgrunt.ultraamplified.world.dimension;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.DimensionSavedDataManager;
import net.minecraft.world.storage.WorldSavedData;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class UAWorldSavedData extends WorldSavedData
{
	private static final String DATANAME = UltraAmplified.MODID + "timeoffset";
	private static final UAWorldSavedData CLIENT_DUMMY = new UAWorldSavedData();
	private long timeUA;
	
	public UAWorldSavedData()
	{
		super(DATANAME);
	}

	public UAWorldSavedData(String s)
	{
		super(s);
	}


	public static UAWorldSavedData get(World world)
	{
		if (!(world instanceof ServerWorld))
		{
			return CLIENT_DUMMY;
		}
		
		ServerWorld wbWorld = ((ServerWorld)world).getServer().getWorld(UADimensionRegistration.ultraamplified());
		DimensionSavedDataManager storage = wbWorld.getSavedData();
		return storage.getOrCreate(UAWorldSavedData::new, DATANAME);
	}
	
	

	@Override
	public void read(CompoundNBT data)
	{
		timeUA = data.getInt("timeUA");
	}

	@Override
	public CompoundNBT write(CompoundNBT data)
	{
		data.putLong("timeUA", timeUA);

		return data;
	}

	public void setTimeUA(long time) 
	{
		this.timeUA = time;
	}
	
	public long getTimeUA() 
	{
		return this.timeUA;
	}
}
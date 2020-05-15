package net.telepathicgrunt.ultraamplified.world.dimension;

import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;

public class UAOverworldDimension extends OverworldDimension {
    World trueOverworld = null;

    public UAOverworldDimension(World world, DimensionType typeIn) {
	super(world, typeIn);

    }

    @Override
    public boolean isDaytime() {
	if (trueOverworld == null && !world.isRemote)
	    trueOverworld = world.getServer().getWorld(DimensionType.OVERWORLD);

	return trueOverworld.getSkylightSubtracted() < 4;
    }

    @Override
    public long getWorldTime() {
	if (trueOverworld == null && !world.isRemote)
	    trueOverworld = world.getServer().getWorld(DimensionType.OVERWORLD);

	return trueOverworld.getWorldInfo().getDayTime();
    }

    @Override
    public void setWorldTime(long timeIn) {
	if (trueOverworld == null && !world.isRemote)
	    trueOverworld = world.getServer().getWorld(DimensionType.OVERWORLD);

	trueOverworld.getWorldInfo().setDayTime(timeIn);
    }
}

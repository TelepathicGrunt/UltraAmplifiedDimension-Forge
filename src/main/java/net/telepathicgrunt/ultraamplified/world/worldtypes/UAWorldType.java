package net.telepathicgrunt.ultraamplified.world.worldtypes;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.telepathicgrunt.ultraamplified.world.feature.carver.CaveCavityCarver;
import net.telepathicgrunt.ultraamplified.world.generation.UABiomeProvider;
import net.telepathicgrunt.ultraamplified.world.generation.UAChunkGenerator;


public class UAWorldType extends WorldType
{
	//displays our mod as a world type

	public UAWorldType()
	{
		/*
		 * Name of world type. Also had to add this to en_us.json file to display name and info properly:
		 * 
		 * en_us.json entries "generator.ultra_amplified":"Ultra Amplified"
		 * "generator.ultra_amplified.info":"May take a minute or two to create a fresh world."
		 */
		super("ultra_amplified");
		this.enableInfoNotice();
	}


	@Override
	public ChunkGenerator<?> createChunkGenerator(World world)
	{
		CaveCavityCarver.setSeed(world.getSeed());
		
		if (world.dimension.getType() == DimensionType.OVERWORLD)
		{
			//tells Minecraft to use this mod's ChunkGeneratorOverworld when running this world type in Overworld.
			return new UAChunkGenerator(world, new UABiomeProvider(world), ChunkGeneratorType.SURFACE.createSettings());
		}

		// Run default chunkgenerator for each dimension
		return super.createChunkGenerator(world);
	}


	@Override
	public boolean hasCustomOptions()
	{
		//Not customizable since we use a config file instead to customize.
		return false;
	}
}

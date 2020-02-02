package net.telepathicgrunt.ultraamplified.world.worldtypes;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeProviderUA;
import net.telepathicgrunt.ultraamplified.world.generation.UAChunkGenerator;


public class WorldTypeUA extends WorldType
{
	//displays our mod as a world type

	public WorldTypeUA()
	{
		/*
		 * Name of world type. Also had to add this to en_us.json file to display name and info properly:
		 * 	 
         *  en_us.json entries
		 * "generator.UltraAmplified":"Ultra Amplified"
		 * "generator.UltraAmplified.info":"May take a minute or two to create a fresh world."
		 */
		super("UltraAmplified");
		this.enableInfoNotice();
	}


	@Override
	public ChunkGenerator<?> createChunkGenerator(World world)
	{
		//tells Minecraft to use this mod's ChunkGeneratorOverworld when running this world type in Overworld.
		return new UAChunkGenerator(world, new BiomeProviderUA(world), ChunkGeneratorType.SURFACE.createSettings());
	}


	@Override
	public boolean hasCustomOptions()
	{
		//Not customizable since we use a config file instead to customize.
		return false;
	}
}

package net.telepathicgrunt.ultraamplified.World.WorldTypes;

import java.lang.reflect.Method;

import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.telepathicgrunt.ultraamplified.World.Generation.BiomeProviderUA;
import net.telepathicgrunt.ultraamplified.World.Generation.ChunkGeneratorOverworldUA;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class WorldTypeUA extends WorldType {
	//displays our mod as a world type

	
    public WorldTypeUA()
    {
    	/* Name of world type. 
    	 * Also had to add this to .lang file to display name and info properly:
    	 * 
    	 * generator.UltraAmplified=Ultra Amplified
		 * generator.UltraAmplified.info=May take a minute or two to create a fresh world.
    	 */
        super("UltraAmplified");

        //used reflection to get method that displays world type info and run that method.
        try {	
        	Method enableInfoNoticeMethod = ReflectionHelper.findMethod(WorldType.class, "enableInfoNotice", "func_151358_j");
        	enableInfoNoticeMethod.setAccessible(true);
        	enableInfoNoticeMethod.invoke(this);
        }
        catch(Exception e){
        	UltraAmplified.logger.warn("WorldTypeUA error with enableInfoNotice reflection: "+e.getMessage());
        }
    }
    
    @Override
    public BiomeProvider getBiomeProvider(World world)
    {
    	//tells Minecraft to use this mod's BiomeProvider when running this world type.
        return new BiomeProviderUA(world);
    } 
    
    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions)
    {
    	//tells Minecraft to use this mod's ChunkGeneratorOverworld when running this world type in Overworld.
        return new ChunkGeneratorOverworldUA(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled());
    }
    
    @Override
    public boolean isCustomizable()
    {
    	//Not customizable since we use a config file instead to customize.
        return false;
    }
}


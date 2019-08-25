package net.telepathicgrunt.ultraamplified.world.worldtypes;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;

import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.telepathicgrunt.ultraamplified.world.generation.BiomeProviderUA;
import net.telepathicgrunt.ultraamplified.world.generation.ChunkGeneratorOverworldUA;

public class WorldTypeUA extends WorldType {
	//displays our mod as a world type

	
    public WorldTypeUA()
    {
    	/* Name of world type. 
    	 * Also had to add this to en_us.json file to display name and info properly:
    	 * 
    	 * "generator.UltraAmplified":"Ultra Amplified"
		 * "generator.UltraAmplified.info":"May take a minute or two to create a fresh world."
    	 */
        super("UltraAmplified");

        
        //used reflection to get method that displays world type info and run that method.
        try {	
        	Method enableInfoNoticeMethod = ObfuscationReflectionHelper.findMethod(WorldType.class, "func_151358_j"); // update the srg name
        	enableInfoNoticeMethod.setAccessible(true);
        	enableInfoNoticeMethod.invoke(this);
        }
        catch(Exception e){
        	UltraAmplified.Logger.log(Level.ERROR ,"WorldTypeUA error with enableInfoNotice reflection: "+e.getMessage());
        }
    }
    
    @Override
    public ChunkGenerator<?> createChunkGenerator(World world)
    {
    	//tells Minecraft to use this mod's ChunkGeneratorOverworld when running this world type in Overworld.
        return new ChunkGeneratorOverworldUA(world, new BiomeProviderUA(world), ChunkGeneratorType.SURFACE.createSettings());
    }
    
    
    @Override
    public boolean hasCustomOptions()
    {
    	//Not customizable since we use a config file instead to customize.
        return false;
    }
}


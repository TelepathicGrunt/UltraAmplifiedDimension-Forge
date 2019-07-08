package net.TelepathicGrunt.UltraAmplified.World.WorldTypes;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;

import com.TelepathicGrunt.UltraAmplified.UltraAmplified;

import net.TelepathicGrunt.UltraAmplified.World.Generation.BiomeProviderUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorOverworldUA;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

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
    public IChunkGenerator<?> createChunkGenerator(World world)
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


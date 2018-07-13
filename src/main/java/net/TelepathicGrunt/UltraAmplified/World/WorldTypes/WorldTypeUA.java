package net.TelepathicGrunt.UltraAmplified.World.WorldTypes;

import java.lang.reflect.Method;

import jline.internal.Log;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.TelepathicGrunt.UltraAmplified.World.Generation.BiomeProviderUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorOverworldUA;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class WorldTypeUA extends WorldType {
	

	
    public WorldTypeUA()
    {
        super("UltraAmplified");

        try {	
        	Method enableInfoNoticeMethod = ReflectionHelper.findMethod(WorldType.class, "enableInfoNotice", "func_151358_j");
        	enableInfoNoticeMethod.setAccessible(true);
        	enableInfoNoticeMethod.invoke(this);
        }
        catch(Exception e){
        	Log.warn("WorldTypeUA error with enableInfoNotice reflection: "+e.getMessage());
        }
    }
    
    @Override
    public BiomeProvider getBiomeProvider(World world)
    {
        return new BiomeProviderUA(world);
    } 
    
    @Override
    public IChunkGenerator getChunkGenerator(World world, String generatorOptions)
    {
        return new ChunkGeneratorOverworldUA(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), generatorOptions);
    }
    
    @Override
    public boolean isCustomizable()
    {
        return false;
    }
}


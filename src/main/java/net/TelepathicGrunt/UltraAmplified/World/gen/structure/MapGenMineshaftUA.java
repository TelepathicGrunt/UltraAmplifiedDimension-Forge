package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeColdBeachUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeDesertUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeEndUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeHellUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeHillsUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeJungleUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeMesaUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeSavannaUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeSnowUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeStoneBeachUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeSwampUA;
import net.TelepathicGrunt.UltraAmplified.World.Biomes.BiomeTaigaUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorOverworldUA;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;

public class MapGenMineshaftUA extends MapGenStructure
{
    private double chance = 0.0025D;

    public MapGenMineshaftUA()
    {
    }
    
    public MapGenMineshaftUA(ChunkGeneratorOverworldUA settings)
    {
    	chance = settings.settings.mineshaftSpawnrate/10000;
    }

    public String getStructureName()
    {
        return "Mineshaft";
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        return this.rand.nextDouble() < this.chance && this.rand.nextInt(80) < Math.max(Math.abs(chunkX), Math.abs(chunkZ));
    }

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        int j = pos.getX() >> 4;
        int k = pos.getZ() >> 4;

        for (int l = 0; l <= 1000; ++l)
        {
            for (int i1 = -l; i1 <= l; ++i1)
            {
                boolean flag = i1 == -l || i1 == l;

                for (int j1 = -l; j1 <= l; ++j1)
                {
                    boolean flag1 = j1 == -l || j1 == l;

                    if (flag || flag1)
                    {
                        int k1 = j + i1;
                        int l1 = k + j1;
                        this.rand.setSeed((long)(k1 ^ l1) ^ worldIn.getSeed());
                        this.rand.nextInt();

                        if (this.canSpawnStructureAtCoords(k1, l1) && (!findUnexplored || !worldIn.isChunkGeneratedAt(k1, l1)))
                        {
                            return new BlockPos((k1 << 4) + 8, 64, (l1 << 4) + 8);
                        }
                    }
                }
            }
        }

        return null;
    }

    protected StructureMineshaftStartUA getStructureStart(int chunkX, int chunkZ)
    {

        Biome biome = this.world.getBiome(new BlockPos((chunkX << 4) + 8, 80, (chunkZ << 4) + 8));
        
        MapGenMineshaftUA.Type mapgenmineshaft$type;

        if(biome instanceof BiomeMesaUA  || biome instanceof BiomeHellUA) 
        {
        	//System.out.println("Mineshaft | Mesa | "+chunkX*16+" "+chunkZ*16);
        	mapgenmineshaft$type = MapGenMineshaftUA.Type.MESA;
        }
        else if(biome instanceof BiomeSnowUA || biome instanceof BiomeColdBeachUA) 
        {
        	if(biome.isMutation()) {
            	//System.out.println("Mineshaft | Icey | "+chunkX*16+" "+chunkZ*16);
        		mapgenmineshaft$type = MapGenMineshaftUA.Type.ICEY;
        	}
        	else {
            	//System.out.println("Mineshaft | Cold or Birch | "+chunkX*16+" "+chunkZ*16);
        		mapgenmineshaft$type = MapGenMineshaftUA.Type.COLDORBIRCH;
        	}
        }
        else if(biome instanceof BiomeJungleUA) 
        {
        	//System.out.println("Mineshaft | Jungle | "+chunkX*16+" "+chunkZ*16);
        	mapgenmineshaft$type = MapGenMineshaftUA.Type.JUNGLE;
        }
        else if(biome instanceof BiomeTaigaUA || biome instanceof BiomeHillsUA) 
        {
        	//System.out.println("Mineshaft | Taiga | "+chunkX*16+" "+chunkZ*16);
        	mapgenmineshaft$type = MapGenMineshaftUA.Type.TAIGA;
        }
        else if(biome instanceof BiomeDesertUA) 
        {
        	//System.out.println("Mineshaft | Desert | "+chunkX*16+" "+chunkZ*16);
        	mapgenmineshaft$type = MapGenMineshaftUA.Type.DESERT;
        }
        else if(biome instanceof BiomeEndUA) 
        {
        	//System.out.println("Mineshaft | End | "+chunkX*16+" "+chunkZ*16);
        	mapgenmineshaft$type = MapGenMineshaftUA.Type.END;
        }
        else if(biome instanceof BiomeHellUA) 
        {
        	//System.out.println("Mineshaft | Nether | "+chunkX*16+" "+chunkZ*16);
        	mapgenmineshaft$type = MapGenMineshaftUA.Type.HELL;
        }
        else if(biome instanceof BiomeSavannaUA) 
        {
        	//System.out.println("Mineshaft | Savanna | "+chunkX*16+" "+chunkZ*16);
        	mapgenmineshaft$type = MapGenMineshaftUA.Type.SAVANNA;
        }
        else if(biome instanceof BiomeStoneBeachUA) 
        {
        	//System.out.println("Mineshaft | Stone | "+chunkX*16+" "+chunkZ*16);
        	mapgenmineshaft$type = MapGenMineshaftUA.Type.STONE;
        }
        else if(biome instanceof BiomeSwampUA) 
        {
        	//System.out.println("Mineshaft | Swamp | "+chunkX*16+" "+chunkZ*16);
        	mapgenmineshaft$type = MapGenMineshaftUA.Type.SWAMP;
        }
        else if(biome.getBiomeName() == "Birch Forest" || biome.getBiomeName() == "Birch Forest Hills" || biome.getBiomeName() == "Birch Forest M" || biome.getBiomeName() == "Birch Forest Hills M") 
        {
        	//System.out.println("Mineshaft | Cold or Birch | "+chunkX*16+" "+chunkZ*16);
        	mapgenmineshaft$type = MapGenMineshaftUA.Type.COLDORBIRCH;
        }
        else 
        {
        	//System.out.println("Mineshaft | Normal | "+chunkX*16+" "+chunkZ*16);
        	mapgenmineshaft$type = MapGenMineshaftUA.Type.NORMAL;
        }
        
        
        
        return new StructureMineshaftStartUA(this.world, this.rand, chunkX, chunkZ, mapgenmineshaft$type);
    }

    public static enum Type
    {
        NORMAL,
        MESA,
        ICEY,
        COLDORBIRCH,
        JUNGLE,
        TAIGA,
        DESERT,
        STONE,
        SAVANNA,
        SWAMP,
        END,
        HELL;

        public static MapGenMineshaftUA.Type byId(int id)
        {
            return id >= 0 && id < values().length ? values()[id] : NORMAL;
        }
    }
}

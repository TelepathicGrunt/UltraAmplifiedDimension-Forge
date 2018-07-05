package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Biomes;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenScatteredFeatureUA extends MapGenStructure
{
    private static final List<Biome> BIOMELIST = Arrays.<Biome>asList(new Biome[] {BiomeInit.BiomeNether, BiomeInit.BiomeEnd, BiomeInit.BiomeDesert, BiomeInit.BiomeDesertHills, BiomeInit.BiomeJungle, BiomeInit.BiomeJungleEdge, BiomeInit.BiomeJungleEdgeM, BiomeInit.BiomeJungleHills, BiomeInit.BiomeJungleM, BiomeInit.BiomeSwampland, BiomeInit.BiomeSwamplandM, BiomeInit.BiomeIceFlats, BiomeInit.BiomeIceSpike, BiomeInit.BiomeColdTaiga});

    private final List<Biome.SpawnListEntry> monsters;

    /** the maximum distance between scattered features */
    private int maxDistanceBetweenScatteredFeatures;

    /** the minimum distance between scattered features */
    private final int minDistanceBetweenScatteredFeatures;

    public MapGenScatteredFeatureUA()
    {
        this.monsters = Lists.<Biome.SpawnListEntry>newArrayList();
        this.maxDistanceBetweenScatteredFeatures = 13;
        this.minDistanceBetweenScatteredFeatures = 13;
        this.monsters.add(new Biome.SpawnListEntry(EntityWitch.class, 1, 1, 1));
    }

    public MapGenScatteredFeatureUA(Map<String, String> p_i2061_1_)
    {
        this();

        for (Entry<String, String> entry : p_i2061_1_.entrySet())
        {
            if ((entry.getKey()).equals("distance"))
            {
                this.maxDistanceBetweenScatteredFeatures = MathHelper.getInt(entry.getValue(), this.maxDistanceBetweenScatteredFeatures, 9);
            }
        }
    }

    public String getStructureName()
    {
        return "Temple";
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
    	
	        int i = chunkX;
	        int j = chunkZ;
	
	        if (chunkX < 0)
	        {
	            chunkX -= this.maxDistanceBetweenScatteredFeatures - 1;
	        }

	        if (chunkZ < 0)
	        {
	            chunkZ -= this.maxDistanceBetweenScatteredFeatures - 1;
	        }

	        int k = chunkX / this.maxDistanceBetweenScatteredFeatures;
	        int l = chunkZ / this.maxDistanceBetweenScatteredFeatures;
	        Random random = this.world.setRandomSeed(k, l, 14357617);
	        k = k * this.maxDistanceBetweenScatteredFeatures;
	        l = l * this.maxDistanceBetweenScatteredFeatures;
	        k = k + random.nextInt(this.maxDistanceBetweenScatteredFeatures - 8);
	        l = l + random.nextInt(this.maxDistanceBetweenScatteredFeatures - 8);

	        
	        if (k == i && l == j)
	        {
	        	
	        	boolean flag = this.world.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, BIOMELIST);
	
	            if (flag)
	            {
	                return true;
	            }
	        	
	        }
	    	
	        return false;
    	
    }

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        this.world = worldIn;
        return findNearestStructurePosBySpacing(worldIn, this, pos, this.maxDistanceBetweenScatteredFeatures, 8, 14357617, false, 100, findUnexplored);
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new MapGenScatteredFeatureUA.Start(this.world, this.rand, chunkX, chunkZ);
    }

    public boolean isSwampHut(BlockPos p_175798_1_)
    {
        StructureStart structurestart = this.getStructureAt(p_175798_1_);

        if (structurestart != null && structurestart instanceof MapGenScatteredFeatureUA.Start && !structurestart.getComponents().isEmpty())
        {
            StructureComponent structurecomponent = (StructureComponent)structurestart.getComponents().get(0);
            return structurecomponent instanceof ComponentScatteredFeaturePiecesUA.SwampHut;
        }
        else
        {
            return false;
        }
    }

    /**
     * returns possible spawns for scattered features
     */
    public List<Biome.SpawnListEntry> getMonsters()
    {
        return this.monsters;
    }


    public static class Start extends StructureStart
    {
        public Start()
        {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ)
        {
            this(worldIn, random, chunkX, chunkZ, worldIn.getBiome(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8)));
        }
        
        public Start(World worldIn, Random random, int chunkX, int chunkZ, Biome biomeIn)
        {
            super(chunkX, chunkZ);

            if(random.nextInt(4) < 3)
            {
	            if (biomeIn != BiomeInit.BiomeJungle && biomeIn != BiomeInit.BiomeJungleEdge && biomeIn != BiomeInit.BiomeJungleEdgeM  && biomeIn != BiomeInit.BiomeJungleHills && biomeIn != BiomeInit.BiomeJungleM)
	            {
	                if (biomeIn == BiomeInit.BiomeSwampland || biomeIn == BiomeInit.BiomeSwamplandM)
	                {
	                	System.out.println("Witch Hut | "+chunkX*16+" "+chunkZ*16);
	                    ComponentScatteredFeaturePiecesUA.SwampHut componentscatteredfeaturepieces$swamphut = new ComponentScatteredFeaturePiecesUA.SwampHut(random, chunkX * 16, chunkZ * 16);
	                    this.components.add(componentscatteredfeaturepieces$swamphut);
	                }
	                else if (biomeIn != BiomeInit.BiomeDesert && biomeIn != BiomeInit.BiomeDesertHills)
	                {
	                    if (biomeIn != BiomeInit.BiomeIceFlats && biomeIn != BiomeInit.BiomeColdTaiga && biomeIn != BiomeInit.BiomeIceSpike)
	                    {
	                    	if(biomeIn == BiomeInit.BiomeEnd) 
	                    	{
	                        	System.out.println("End City | "+chunkX*16+" "+chunkZ*16);
	                    		this.createEndCity(worldIn, random, chunkX, chunkZ);
	                    	}
	                    }
	                    else 
	                    {
	                    	System.out.println("Igloo | "+chunkX*16+" "+chunkZ*16);
	                        ComponentScatteredFeaturePiecesUA.Igloo componentscatteredfeaturepieces$igloo = new ComponentScatteredFeaturePiecesUA.Igloo(random, chunkX * 16, chunkZ * 16);
	                        this.components.add(componentscatteredfeaturepieces$igloo);
	                    }
	                }
	                else
	                {
	                	System.out.println("Desert Temple | "+chunkX*16+" "+chunkZ*16);
	                    ComponentScatteredFeaturePiecesUA.DesertPyramid componentscatteredfeaturepieces$desertpyramid = new ComponentScatteredFeaturePiecesUA.DesertPyramid(random, chunkX * 16, chunkZ * 16);
	                    this.components.add(componentscatteredfeaturepieces$desertpyramid);
	                }
	            }
	            else
	            {
	            	System.out.println("Jungle Temple | "+chunkX*16+" "+chunkZ*16);
	                ComponentScatteredFeaturePiecesUA.JunglePyramid componentscatteredfeaturepieces$junglepyramid = new ComponentScatteredFeaturePiecesUA.JunglePyramid(random, chunkX * 16, chunkZ * 16);
	                this.components.add(componentscatteredfeaturepieces$junglepyramid);
	            }
	
	            this.updateBoundingBox();
	            
	
	            if(biomeIn == BiomeInit.BiomeNether) 
	            {
	            	System.out.println("Nether Fortress | aboveground | "+chunkX*16+" "+chunkZ*16);
	        		StructureNetherBridgePiecesUA.Start structurenetherbridgepieces$start = new StructureNetherBridgePiecesUA.Start(random, (chunkX << 4) + 2, (chunkZ << 4) + 2);
	                this.components.add(structurenetherbridgepieces$start);
	                structurenetherbridgepieces$start.buildComponent(structurenetherbridgepieces$start, this.components, random);
	                List<StructureComponent> list = structurenetherbridgepieces$start.pendingChildren;
	
	                while (!list.isEmpty())
	                {
	                    int i = random.nextInt(list.size());
	                    StructureComponent structurecomponent = (StructureComponent)list.remove(i);
	                    structurecomponent.buildComponent(structurenetherbridgepieces$start, this.components, random);
	                }
	                
	                this.updateBoundingBox();
	                this.setRandomHeight(worldIn, random, 65, 165);
	        	}
            }
            else{
            	System.out.println("Nether Fortress | underground | "+chunkX*16+" "+chunkZ*16);
            	
            	StructureNetherBridgePiecesUA.Start structurenetherbridgepieces$start = new StructureNetherBridgePiecesUA.Start(random, (chunkX << 4) + 2, (chunkZ << 4) + 2);
                this.components.add(structurenetherbridgepieces$start);
                structurenetherbridgepieces$start.buildComponent(structurenetherbridgepieces$start, this.components, random);
                List<StructureComponent> list = structurenetherbridgepieces$start.pendingChildren;

                while (!list.isEmpty())
                {
                    int i = random.nextInt(list.size());
                    StructureComponent structurecomponent = (StructureComponent)list.remove(i);
                    structurecomponent.buildComponent(structurenetherbridgepieces$start, this.components, random);
                }
                
                this.updateBoundingBox();
                this.setRandomHeight(worldIn, random, 20, 35);
            }
        }
       
        private void createEndCity(World worldIn, Random rnd, int chunkX, int chunkZ)
        {
            Random random = new Random((long)(chunkX + chunkZ * 10387313));
            Rotation rotation = Rotation.values()[random.nextInt(Rotation.values().length)];
             BlockPos blockpos = new BlockPos(chunkX * 16 + 8, 145, chunkZ * 16 + 8);
            
            
            StructureEndCityPiecesUA.startHouseTower(worldIn.getSaveHandler().getStructureTemplateManager(), blockpos, rotation, this.components, rnd);
            this.updateBoundingBox();
        }
    }
}

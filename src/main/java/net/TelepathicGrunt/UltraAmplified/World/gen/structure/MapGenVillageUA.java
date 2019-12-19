package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorOverworldUA;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenVillageUA extends MapGenStructure
{
    public static final List<Biome> VILLAGE_SPAWN_BIOMES = Arrays.<Biome>asList(new Biome[] {BiomeInit.BiomePlains, BiomeInit.BiomeDesert, BiomeInit.BiomeSavanna, BiomeInit.BiomeTaiga, BiomeInit.BiomeIceFlats, BiomeInit.BiomeColdTaiga, BiomeInit.BiomeColdTaigaHills, BiomeInit.BiomeIceMountain, BiomeInit.BiomeIceSpike, BiomeInit.BiomeJungle, BiomeInit.BiomeMesaBryce, BiomeInit.BiomeMesaClearRock, BiomeInit.BiomeEnd, BiomeInit.BiomeStoneBeach, BiomeInit.BiomeRoofedForest, BiomeInit.BiomeRoofedForestM});

    /** None */
    private int size;
    private int distance;
    private int separation = 8;
    private int subtraction = 8;

    public MapGenVillageUA()
    {
        this.distance = 20;
    }
    
    public MapGenVillageUA(ChunkGeneratorOverworldUA settings)
    {
        this.distance = settings.settings.villageRarity;
        if(this.distance < 9) {
        	this.subtraction = distance-1;
        }
    }

    public String getStructureName()
    {
        return "Village";
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= this.distance - 1;
        }

        if (chunkZ < 0)
        {
            chunkZ -= this.distance - 1;
        }

        int k = chunkX / this.distance;
        int l = chunkZ / this.distance;
        Random random = this.world.setRandomSeed(k, l, 10387312);
        k = k * this.distance;
        l = l * this.distance;
        k = k + random.nextInt(this.distance - this.subtraction);
        l = l + random.nextInt(this.distance - this.subtraction);

        if (k == i && l == j)
        {
            boolean flag = this.world.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, VILLAGE_SPAWN_BIOMES);

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
        return findNearestStructurePosBySpacing(worldIn, this, pos, this.distance, this.separation, 10387312, false, 100, findUnexplored);
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new MapGenVillageUA.Start(this.world, this.rand, chunkX, chunkZ, this.size);
    }
    
    public static class Start extends StructureStart
    {
        private boolean hasMoreThanTwoComponents;

        public Start()
        {
        }

        public Start(World worldIn, Random rand, int x, int z, int size)
        {
            super(x, z);

            
            List<StructureVillagePiecesUA.PieceWeight> list = StructureVillagePiecesUA.getStructureVillageWeightedPieceList(rand, size);
            StructureVillagePiecesUA.Start structurevillagepieces$start = new StructureVillagePiecesUA.Start(worldIn.getBiomeProvider(), 0, rand, (x << 4) + 2, (z << 4) + 2, list, size);
            this.components.add(structurevillagepieces$start);
            structurevillagepieces$start.buildComponent(structurevillagepieces$start, this.components, rand);
            List<StructureComponent> list1 = structurevillagepieces$start.pendingRoads;
            List<StructureComponent> list2 = structurevillagepieces$start.pendingHouses;

            while (!list1.isEmpty() || !list2.isEmpty())
            {
                if (list1.isEmpty())
                {
                    int i = rand.nextInt(list2.size());
                    StructureComponent structurecomponent = (StructureComponent)list2.remove(i);
                    structurecomponent.buildComponent(structurevillagepieces$start, this.components, rand);
                }
                else
                {
                    int j = rand.nextInt(list1.size());
                    StructureComponent structurecomponent2 = (StructureComponent)list1.remove(j);
                    structurecomponent2.buildComponent(structurevillagepieces$start, this.components, rand);
                }
            }

            this.updateBoundingBox();
            int k = 0;

            for (StructureComponent structurecomponent1 : this.components)
            {
                if (!(structurecomponent1 instanceof StructureVillagePiecesUA.Road))
                {
                    ++k;
                }
            }

            this.hasMoreThanTwoComponents = k > 2;
        }

        public boolean isSizeableStructure()
        {
            return this.hasMoreThanTwoComponents;
        }

        public void writeToNBT(NBTTagCompound tagCompound)
        {
            super.writeToNBT(tagCompound);
            tagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
        }

        public void readFromNBT(NBTTagCompound tagCompound)
        {
            super.readFromNBT(tagCompound);
            this.hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
        }
    }
}

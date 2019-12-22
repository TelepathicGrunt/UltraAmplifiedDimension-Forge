package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorOverworldUA;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenStrongholdUA extends MapGenStructure
{
    private final List<Biome> allowedBiomes;

    /**
     * is spawned false and set true once the defined BiomeGenBases were compared with the present ones
     */
    private boolean ranBiomeCheck;
    private ChunkPos[] structureCoords;
    private float distance;
    private int spread;

    public MapGenStrongholdUA(ChunkGeneratorOverworldUA settings)
    {
        this.structureCoords = new ChunkPos[settings.settings.strongholdCount];
        this.distance = settings.settings.strongholdDistance;
        this.spread = settings.settings.strongholdSpread;
        this.allowedBiomes = Lists.<Biome>newArrayList();

        for (Biome biome : Biome.REGISTRY)
        {
            if (biome != null && biome.getBaseHeight() > 0.0F && !net.minecraftforge.common.BiomeManager.strongHoldBiomesBlackList.contains(biome))
            {
                this.allowedBiomes.add(biome);
            }
        }
        
        for (Biome biome : net.minecraftforge.common.BiomeManager.strongHoldBiomes)
        {
            if (!this.allowedBiomes.contains(biome))
            {
                this.allowedBiomes.add(biome);
            }
        }
    }

    public String getStructureName()
    {
        return "Stronghold";
    }

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
        if (!this.ranBiomeCheck)
        {
            this.generatePositions();
            this.ranBiomeCheck = true;
        }

        BlockPos blockpos = null;
        BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);
        double d0 = Double.MAX_VALUE;

        for (ChunkPos chunkpos : this.structureCoords)
        {
            blockpos$mutableblockpos.setPos((chunkpos.x << 4) + 8, 32, (chunkpos.z << 4) + 8);
            double d1 = blockpos$mutableblockpos.distanceSq(pos);

            if (blockpos == null)
            {
                blockpos = new BlockPos(blockpos$mutableblockpos);
                d0 = d1;
            }
            else if (d1 < d0)
            {
                blockpos = new BlockPos(blockpos$mutableblockpos);
                d0 = d1;
            }
        }

        return blockpos;
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        if (!this.ranBiomeCheck)
        {
            this.generatePositions();
            this.ranBiomeCheck = true;
        }

        for (ChunkPos chunkpos : this.structureCoords)
        {
            if (chunkX == chunkpos.x && chunkZ == chunkpos.z)
            {
                return true;
            }
        }

        return false;
    }

    private void generatePositions()
    {
        this.initializeStructureData(this.world);
        int i = 0;
        ObjectIterator<StructureStart> lvt_2_1_ = this.structureMap.values().iterator();

        while (lvt_2_1_.hasNext())
        {
            StructureStart structurestart = (StructureStart)lvt_2_1_.next();

            if (i < this.structureCoords.length)
            {
                this.structureCoords[i++] = new ChunkPos(structurestart.getChunkPosX(), structurestart.getChunkPosZ());
            }
        }

        Random random = new Random();
        random.setSeed(this.world.getSeed());
        double d1 = random.nextDouble() * Math.PI * 2.0D;
        int j = 0;
        int k = 0;
        int l = this.structureMap.size();

        if (l < this.structureCoords.length)
        {
            for (int i1 = 0; i1 < this.structureCoords.length; ++i1)
            {
                float f0 = 4.0F * this.distance + this.distance * (float)j * 6.0F + (random.nextFloat() - 0.5F) * this.distance * 2.5F;
                int j1 = (int)Math.round(Math.cos(d1) * f0);
                int k1 = (int)Math.round(Math.sin(d1) * f0);
                BlockPos blockpos = this.world.getBiomeProvider().findBiomePosition((j1 << 4) + 8, (k1 << 4) + 8, 112, this.allowedBiomes, random);

                if (blockpos != null)
                {
                    j1 = blockpos.getX() >> 4;
                    k1 = blockpos.getZ() >> 4;
                }

                if (i1 >= l)
                {
                    this.structureCoords[i1] = new ChunkPos(j1, k1);
                }

                d1 += (Math.PI * 2D) / (double)this.spread;
                ++k;

                if (k == this.spread)
                {
                    ++j;
                    k = 0;
                    this.spread += 2 * this.spread / (j + 1);
                    this.spread = Math.min(this.spread, this.structureCoords.length - i1);
                    d1 += random.nextDouble() * Math.PI * 2.0D;
                }
            }
        }
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
    	//System.out.println("Stronghold | "+chunkX*16+" "+chunkZ*16);
    	
        MapGenStrongholdUA.Start mapgenstronghold$start;

        for (mapgenstronghold$start = new MapGenStrongholdUA.Start(this.world, this.rand, chunkX, chunkZ); mapgenstronghold$start.getComponents().isEmpty() || ((StructureStrongholdPiecesUA.Stairs2)mapgenstronghold$start.getComponents().get(0)).strongholdPortalRoom == null; mapgenstronghold$start = new MapGenStrongholdUA.Start(this.world, this.rand, chunkX, chunkZ))
        {
            ;
        }

        return mapgenstronghold$start;
    }

    public static class Start extends StructureStart
    {
        public Start()
        {
        }

        public Start(World worldIn, Random random, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);

            StructureStrongholdPiecesUA.prepareStructurePieces();
            StructureStrongholdPiecesUA.Stairs2 structurestrongholdpieces$stairs2 = new StructureStrongholdPiecesUA.Stairs2(0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2);
            this.components.add(structurestrongholdpieces$stairs2);
            structurestrongholdpieces$stairs2.buildComponent(structurestrongholdpieces$stairs2, this.components, random);
            List<StructureComponent> list = structurestrongholdpieces$stairs2.pendingChildren;

            while (!list.isEmpty())
            {
                int i = random.nextInt(list.size());
                StructureComponent structurecomponent = (StructureComponent)list.remove(i);
                structurecomponent.buildComponent(structurestrongholdpieces$stairs2, this.components, random);
            }

            this.updateBoundingBox();
            
        }
    }
}

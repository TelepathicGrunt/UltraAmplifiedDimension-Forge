package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.init.Biomes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureStart;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorOverworldUA;

public class StructureOceanMonumentUA extends MapGenStructure
{
    private int spacing;
    private int separation;
    private int subtraction = 8;
    public static final List<Biome> SPAWN_BIOMES = Arrays.<Biome>asList(new Biome[] { BiomeInit.BiomeJungle, BiomeInit.BiomeJungleEdge, BiomeInit.BiomeJungleEdgeM, BiomeInit.BiomeJungleHills, BiomeInit.BiomeJungleM, Biomes.DEEP_OCEAN});
    private static final List<Biome.SpawnListEntry> MONUMENT_ENEMIES = Lists.<Biome.SpawnListEntry>newArrayList();

    public StructureOceanMonumentUA(ChunkGeneratorOverworldUA settings)
    {
        this.spacing = settings.settings.monumentsRarity;
        if(this.spacing < 9) {
        	this.subtraction = spacing-1;
        }
        
        this.separation = 8;
    }

    public String getStructureName()
    {
        return "Monument";
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= this.spacing - 1;
        }

        if (chunkZ < 0)
        {
            chunkZ -= this.spacing - 1;
        }

        int k = chunkX / this.spacing;
        int l = chunkZ / this.spacing;
        Random random = this.world.setRandomSeed(k, l, 10387313);
        k = k * this.spacing;
        l = l * this.spacing;
        k = k + random.nextInt(this.spacing - this.subtraction);
        l = l + random.nextInt(this.spacing - this.subtraction);

        if (i == k && j == l)
        {
            
            boolean flag = this.world.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, SPAWN_BIOMES);

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
        return findNearestStructurePosBySpacing(worldIn, this, pos, this.spacing, this.separation, 10387313, true, 100, findUnexplored);
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new StructureOceanMonumentUA.StartMonument(this.world, this.rand, chunkX, chunkZ);
    }

    /**
     * Gets the scattered feature spawn list
     */
    public List<Biome.SpawnListEntry> getMonsters()
    {
        return MONUMENT_ENEMIES;
    }

    static
    {
        MONUMENT_ENEMIES.add(new Biome.SpawnListEntry(EntityGuardian.class, 1, 2, 4));
    }

    public static class StartMonument extends StructureStart
    {
        private final Set<ChunkPos> processed = Sets.<ChunkPos>newHashSet();
        private boolean wasCreated;

        public StartMonument()
        {
        }

        public StartMonument(World worldIn, Random random, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            this.create(worldIn, random, chunkX, chunkZ);
        }

        private void create(World worldIn, Random random, int chunkX, int chunkZ)
        {
        	//System.out.println("Ocean Monument | "+chunkX*16+" "+chunkZ*16);
        	
            random.setSeed(worldIn.getSeed());
            long i = random.nextLong();
            long j = random.nextLong();
            long k = (long)chunkX * i;
            long l = (long)chunkZ * j;
            random.setSeed(k ^ l ^ worldIn.getSeed());
            int x = chunkX * 16 + 8 - 29;
            int z = chunkZ * 16 + 8 - 29;
            EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(random);
            this.components.add(new StructureOceanMonumentPiecesUA.MonumentBuilding(worldIn, random, x, z, enumfacing));
            this.updateBoundingBox();
            this.wasCreated = true;
        }

        public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb)
        {
            if (!this.wasCreated)
            {
                this.components.clear();
                this.create(worldIn, rand, this.getChunkPosX(), this.getChunkPosZ());
            }

            super.generateStructure(worldIn, rand, structurebb);
        }

        public boolean isValidForPostProcess(ChunkPos pair)
        {
            return this.processed.contains(pair) ? false : super.isValidForPostProcess(pair);
        }

        public void notifyPostProcessAt(ChunkPos pair)
        {
            super.notifyPostProcessAt(pair);
            this.processed.add(pair);
        }

        public void writeToNBT(NBTTagCompound tagCompound)
        {
            super.writeToNBT(tagCompound);
            NBTTagList nbttaglist = new NBTTagList();

            for (ChunkPos chunkpos : this.processed)
            {
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setInteger("X", chunkpos.x);
                nbttagcompound.setInteger("Z", chunkpos.z);
                nbttaglist.appendTag(nbttagcompound);
            }

            tagCompound.setTag("Processed", nbttaglist);
        }

        public void readFromNBT(NBTTagCompound tagCompound)
        {
            super.readFromNBT(tagCompound);

            if (tagCompound.hasKey("Processed", 9))
            {
                NBTTagList nbttaglist = tagCompound.getTagList("Processed", 10);

                for (int i = 0; i < nbttaglist.tagCount(); ++i)
                {
                    NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
                    this.processed.add(new ChunkPos(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Z")));
                }
            }
        }
    }
}

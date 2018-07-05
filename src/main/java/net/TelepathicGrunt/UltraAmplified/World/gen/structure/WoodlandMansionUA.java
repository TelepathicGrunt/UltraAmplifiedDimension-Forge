package net.TelepathicGrunt.UltraAmplified.World.gen.structure;

import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeInit;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorOverworldUA;
import net.minecraft.init.Biomes;
import net.minecraft.init.Blocks;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureStart;
import net.minecraft.world.gen.structure.WoodlandMansion;

public class WoodlandMansionUA extends MapGenStructure
{
    private final int spacing = 24;
    private final int field_191074_d = 1;
    public static final List<Biome> field_191072_a = Arrays.<Biome>asList(new Biome[] {BiomeInit.BiomePlains, BiomeInit.BiomeBirchForestHillsM, BiomeInit.BiomeBirchForestM, BiomeInit.BiomeRoofedForest, BiomeInit.BiomeRoofedForestM, BiomeInit.BiomeSwampland, BiomeInit.BiomeSwamplandM});
    private final ChunkGeneratorOverworldUA provider;

    public WoodlandMansionUA(ChunkGeneratorOverworldUA provider)
    {
    	this.provider = provider;
    }

    public String getStructureName()
    {
        return "Mansion";
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
        	chunkX = spacing - 1;
        }

        if (chunkZ < 0)
        {
        	chunkZ = spacing - 1;
        }

        int k = chunkX / spacing;
        int l = chunkZ / spacing;
        Random random = this.world.setRandomSeed(k, l, 10387319);
        k = k * spacing;
        l = l * spacing;
        k = k + random.nextInt(spacing - 8);
        l = l + random.nextInt(spacing - 8);
        
        if (k == i && l == j)
        {
            boolean flag = this.world.getBiomeProvider().areBiomesViable(chunkX * 16 + 8, chunkZ * 16 + 8, 5, field_191072_a);

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
        BiomeProvider biomeprovider = worldIn.getBiomeProvider();
        return biomeprovider.isFixedBiome() && biomeprovider.getFixedBiome() != Biomes.ROOFED_FOREST ? null : findNearestStructurePosBySpacing(worldIn, this, pos, 80, 20, 10387319, true, 100, findUnexplored);
    }

    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new WoodlandMansionUA.Start(this.world, this.provider, this.rand, chunkX, chunkZ);
    }

    public static class Start extends StructureStart
    {
        private boolean field_191093_c;

        public Start()
        {
        }

        public Start(World p_i47235_1_, ChunkGeneratorOverworldUA provider, Random p_i47235_3_, int p_i47235_4_, int p_i47235_5_)
        {
            super(p_i47235_4_, p_i47235_5_);
            this.create(p_i47235_1_, provider, p_i47235_3_, p_i47235_4_, p_i47235_5_);
        }

        private void create(World p_191092_1_, ChunkGeneratorOverworldUA provider, Random p_191092_3_, int chunkX, int chunkZ)
        {
        	System.out.println("Woodland Mansion | "+chunkX*16+" "+chunkZ*16);
        	
            Rotation rotation = Rotation.values()[p_191092_3_.nextInt(Rotation.values().length)];
            ChunkPrimer chunkprimer = new ChunkPrimer();
            provider.setBlocksInChunk(chunkX, chunkZ, chunkprimer);
            int i = 5;
            int j = 5;

            if (rotation == Rotation.CLOCKWISE_90)
            {
                i = -5;
            }
            else if (rotation == Rotation.CLOCKWISE_180)
            {
                i = -5;
                j = -5;
            }
            else if (rotation == Rotation.COUNTERCLOCKWISE_90)
            {
                j = -5;
            }

            int k = chunkprimer.findGroundBlockIdx(7, 7);
            int l = chunkprimer.findGroundBlockIdx(7, 7 + j);
            int i1 = chunkprimer.findGroundBlockIdx(7 + i, 7);
            int j1 = chunkprimer.findGroundBlockIdx(7 + i, 7 + j);
            int k1 = Math.min(Math.min(k, l), Math.min(i1, j1));
            k1 =  Math.min(k1, 220);
            
            if (k1 < 60)
            {
                this.field_191093_c = false;
            }
            else
            {
                BlockPos blockpos = new BlockPos(chunkX * 16 + 8, k1 + 1, chunkZ * 16 + 8); 
                List<WoodlandMansionPiecesUA.MansionTemplate> list = Lists.<WoodlandMansionPiecesUA.MansionTemplate>newLinkedList();
                WoodlandMansionPiecesUA.generateMansion(p_191092_1_.getSaveHandler().getStructureTemplateManager(), blockpos, rotation, list, p_191092_3_);
                this.components.addAll(list);
                this.updateBoundingBox();
                this.field_191093_c = true;
            }
        }

        public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb)
        {
            super.generateStructure(worldIn, rand, structurebb);
            int i = this.boundingBox.minY;

            for (int j = structurebb.minX; j <= structurebb.maxX; ++j)
            {
                for (int k = structurebb.minZ; k <= structurebb.maxZ; ++k)
                {
                    BlockPos blockpos = new BlockPos(j, i, k);

                    if (!worldIn.isAirBlock(blockpos) && this.boundingBox.isVecInside(blockpos))
                    {
                        boolean flag = false;

                        for (StructureComponent structurecomponent : this.components)
                        {
                            if (structurecomponent.getBoundingBox().isVecInside(blockpos))
                            {
                                flag = true;
                                break;
                            }
                        }

                        if (flag)
                        {
                            for (int l = i - 1; l > 1; --l)
                            {
                                BlockPos blockpos1 = new BlockPos(j, l, k);

                                if (!worldIn.isAirBlock(blockpos1) && !worldIn.getBlockState(blockpos1).getMaterial().isLiquid())
                                {
                                    break;
                                }

                                worldIn.setBlockState(blockpos1, Blocks.COBBLESTONE.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }
        }

        public boolean isSizeableStructure()
        {
            return this.field_191093_c;
        }
    }
}

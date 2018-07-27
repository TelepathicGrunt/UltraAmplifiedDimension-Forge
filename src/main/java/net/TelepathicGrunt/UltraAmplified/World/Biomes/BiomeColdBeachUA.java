package net.TelepathicGrunt.UltraAmplified.World.Biomes;


import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenIcePathBeachUA;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.chunk.ChunkPrimer;

//ugliest biome so far lol
public class BiomeColdBeachUA extends BiomeExtendedUA
{
    private final WorldGenIcePathBeachUA icePatch = new WorldGenIcePathBeachUA(3);
    
    public BiomeColdBeachUA(Biome.BiomeProperties properties)
    {
    	super(properties);
    	
    	this.decorator = new BiomeDecoratorUA();
    	
        this.topBlock = Blocks.SAND.getDefaultState();
        this.fillerBlock = Blocks.SANDSTONE.getDefaultState().withProperty(BlockSandStone.TYPE, BlockSandStone.EnumType.SMOOTH);
        
        this.decorator.treesPerChunk = -999;
        this.decorator.deadBushPerChunk = 0;
        this.decorator.reedsPerChunk = 0;
        this.decorator.cactiPerChunk = 0;
        this.decorator.clayPerChunk = 0;
        this.decorator.waterlilyPerChunk = 3;
        this.decorator.sandPatchesPerChunk = 0;
        this.decorator.gravelPatchesPerChunk = 0;
    }
    
    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
    	//generates the small ice patches.
    	if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.ICE))
	    {
    		for (int l = 0; l < 3; ++l)
	        {
	            int i1 = rand.nextInt(16) + 8;
	            int j1 = rand.nextInt(16) + 8;
	            int y = rand.nextInt(175) + 75;
	            this.icePatch.generate(worldIn, rand, pos.add(i1, y, j1));
	        }
	    }		

    	//runs the rest of the biome decorations 
        super.decorate(worldIn, rand, pos);
	}
    
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        this.generateBiomeTerrainUA(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
}

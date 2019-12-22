package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorSettingsUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenMinableNetherUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenNetherWartUA;

public class BiomeHellDecoratorUA extends BiomeDecoratorUA
{

	//Fixed value. Could be too high...
    protected int netherWartPerChunk = 250;
	
    protected WorldGenerator netherWartGen = new WorldGenNetherWartUA();
    protected WorldGenerator glowStone1 = new WorldGenGlowStone1();
    protected WorldGenerator glowStone2 = new WorldGenGlowStone2();

	private final WorldGenerator magma = new WorldGenMinableNetherUA(Blocks.MAGMA.getDefaultState(), 55);
	private final WorldGenerator quartz = new WorldGenMinableNetherUA(Blocks.QUARTZ_ORE.getDefaultState(), 25);
	private final WorldGenerator lava = new lavaGenerator();
	
	
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    { 
        BiomeDecoratorUA.chunkProviderSettingsUA = new ChunkGeneratorSettingsUA();
        
        
        //generates large blobs of magma blocks below Y = 100
        for (int currentCount = random.nextInt(3); currentCount < BiomeDecoratorUA.chunkProviderSettingsUA.magmaCount; ++currentCount)
        {
            int x = random.nextInt(16);
            int y = random.nextInt(101);
            int z = random.nextInt(16);
            this.magma.generate(worldIn, random, pos.add(x, y, z));
        }

        
    	//generates quartz below Y = 240
    	for (int currentCount = random.nextInt(3); currentCount < BiomeDecoratorUA.chunkProviderSettingsUA.quartzCount; ++currentCount)
        {
            int x = random.nextInt(16);
            int y = random.nextInt(241);
            int z = random.nextInt(16);
            if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, quartz, pos.add(x, y, z), net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.QUARTZ))
            this.quartz.generate(worldIn, random, pos.add(x, y, z));
        }
        
    	
    	//spawns a single block of lava and how many can spawn may vary greatly chunk by chunk.
        for (int currentCount = random.nextInt(50); currentCount < BiomeDecoratorUA.chunkProviderSettingsUA.lavaCount; currentCount++)
        {
        	int x = random.nextInt(16) + 8;
            int y = random.nextInt(236) + 4;
            int z = random.nextInt(16) + 8;
            lava.generate(worldIn, random, pos.add(x, y, z));
        }
    	

        this.chunkPos = pos;
        this.genDecorations(biome, worldIn, random);
        this.decorating = false;
    }
    
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
    	//generates netherwart 
    	for (int currentCount = 0; currentCount < this.netherWartPerChunk; ++currentCount)
        {
            int x = random.nextInt(16) + 8;
            int z = random.nextInt(16) + 8;
            int y = random.nextInt(255);
            this.netherWartGen.generate(worldIn, random, this.chunkPos.add(x, y, z));
        }
    	
    	//generates glowstone in the first pattern
    	for (int currentCount = 0; currentCount < BiomeDecoratorUA.chunkProviderSettingsUA.glowstoneCount; ++currentCount)
        {
            int x = random.nextInt(16) + 8;
            int z = random.nextInt(16) + 8;
            int y = random.nextInt(180) + 60;
            this.glowStone1.generate(worldIn, random, this.chunkPos.add(x, y, z));
        }

    	//generates glowstone in the second pattern
    	for (int currentCount = 0; currentCount < BiomeDecoratorUA.chunkProviderSettingsUA.glowstoneCount; ++currentCount)
        {
            int x = random.nextInt(16) + 8;
            int z = random.nextInt(16) + 8;
            int y = random.nextInt(180) + 60;
            this.glowStone2.generate(worldIn, random, this.chunkPos.add(x, y, z));
        }
    }
    
    
    private static class lavaGenerator extends WorldGenerator
    {
    	//will spawn one block of lava in given position but only if the block position is netherrack first
		@Override
		public boolean generate(World worldIn, Random rand, BlockPos position) {
			
			    net.minecraft.block.state.IBlockState state = worldIn.getBlockState(position);
			    
                if (state.getBlock().isReplaceableOreGen(state, worldIn, position, net.minecraft.block.state.pattern.BlockMatcher.forBlock(Blocks.NETHERRACK)))
                {
                    worldIn.setBlockState(position, Blocks.FLOWING_LAVA.getDefaultState(), 16 | 2);
                }
                
			return false;
		}
    }
    
}

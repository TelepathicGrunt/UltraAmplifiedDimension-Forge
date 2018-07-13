package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.UltraAmplified;
import net.TelepathicGrunt.UltraAmplified.Config.UAConfig;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorSettingsUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenMinableNetherUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenNetherWartUA;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenGlowStone1;
import net.minecraft.world.gen.feature.WorldGenGlowStone2;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BiomeHellDecoratorUA extends BiomeDecoratorUA
{

    protected int netherWartPerChunk = 250;
	
    protected WorldGenerator netherWartGen = new WorldGenNetherWartUA();
    protected WorldGenerator glowStone1 = new WorldGenGlowStone1();
    protected WorldGenerator glowStone2 = new WorldGenGlowStone2();

	private final WorldGenerator magma = new WorldGenMinableNetherUA(Blocks.MAGMA.getDefaultState(), 55);
	private final WorldGenerator quartz = new WorldGenMinableNetherUA(Blocks.QUARTZ_ORE.getDefaultState(), 25);
	private final WorldGenerator lava = new lavaGenerator();
	
	
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    { 
        this.chunkProviderSettingsUA = new ChunkGeneratorSettingsUA();
        
    	int i = 3 + random.nextInt(6);
    	
        for (i = 0; i < 5; ++i)
        {
            int j1 = random.nextInt(16);
            int k1 = random.nextInt(101);
            int l1 = random.nextInt(16);
            this.magma.generate(worldIn, random, pos.add(j1, k1, l1));
        }

        
    	i = 3 + random.nextInt(8);
    	
    	for (i = 0; i < this.chunkProviderSettingsUA.quartzCount; ++i)
        {
            int j1 = random.nextInt(16);
            int k1 = random.nextInt(240);
            int l1 = random.nextInt(16);
            this.quartz.generate(worldIn, random, pos.add(j1, k1, l1));
        }
        
    	
    	
    	lava.generate(worldIn, random, pos);

        
        this.chunkPos = pos;
        this.genDecorations(biome, worldIn, random);
        this.decorating = false;
    }
    
    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
    {
    	
    	for (int j5 = 0; j5 < this.netherWartPerChunk; ++j5)
        {
            int l9 = random.nextInt(16) + 8;
            int k13 = random.nextInt(16) + 8;
            int l16 = worldIn.getHeight(this.chunkPos.add(l9, 0, k13)).getY() * 2;

            if (l16 > 0)
            {
                int j19 = random.nextInt(l16);
                this.netherWartGen.generate(worldIn, random, this.chunkPos.add(l9, j19, k13));
            }
        }
    	
    	for (int j5 = 0; j5 < this.chunkProviderSettingsUA.glowstoneCount; ++j5)
        {
            int l9 = random.nextInt(16) + 8;
            int k13 = random.nextInt(16) + 8;
            int l16 = worldIn.getHeight(this.chunkPos.add(l9, 0, k13)).getY() + 20;

            if (l16 > 60)
            {
                int j19 = random.nextInt(l16 - 60) + 60;
                this.glowStone1.generate(worldIn, random, this.chunkPos.add(l9, j19, k13));
            }
        }
    	
    	for (int j5 = 0; j5 < this.chunkProviderSettingsUA.glowstoneCount; ++j5)
        {
            int l9 = random.nextInt(16) + 8;
            int k13 = random.nextInt(16) + 8;
            int l16 = worldIn.getHeight(this.chunkPos.add(l9, 0, k13)).getY() + 20;

            if (l16 > 60)
            {
                int j19 = random.nextInt(l16 - 60) + 60;
                this.glowStone2.generate(worldIn, random, this.chunkPos.add(l9, j19, k13));
            }
        }
    }
    
    
    private static class lavaGenerator extends WorldGenerator
    {
        @Override
        public boolean generate(World worldIn, Random rand, BlockPos pos)
        {
        	 
            int i = 10 + rand.nextInt(50);
            for (; i < 100; i++)
            {
                BlockPos blockpos = pos.add(rand.nextInt(16) + 8, rand.nextInt(236) + 4, rand.nextInt(16) + 8);

                net.minecraft.block.state.IBlockState state = worldIn.getBlockState(blockpos);
                if (state.getBlock().isReplaceableOreGen(state, worldIn, blockpos, net.minecraft.block.state.pattern.BlockMatcher.forBlock(Blocks.NETHERRACK)))
                {
                    worldIn.setBlockState(blockpos, Blocks.FLOWING_LAVA.getDefaultState(), 16 | 2);
                }
            }
            return true;
        }
    }
    
}

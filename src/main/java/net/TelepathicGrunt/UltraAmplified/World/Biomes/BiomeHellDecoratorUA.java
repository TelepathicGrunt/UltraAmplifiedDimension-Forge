package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import java.util.Random;

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

public class BiomeHellDecoratorUA extends BiomeDecorator
{

    protected int netherWartPerChunk = 250;
    protected int glowStonePerChunk = 20;
	
    protected WorldGenerator netherWartGen = new WorldGenNetherWartUA();
    protected WorldGenerator glowStone1 = new WorldGenGlowStone1();
    protected WorldGenerator glowStone2 = new WorldGenGlowStone2();

	private final WorldGenerator magma = new WorldGenMinableNetherUA(Blocks.MAGMA.getDefaultState(), 55);
	private final WorldGenerator quartz = new WorldGenMinableNetherUA(Blocks.QUARTZ_ORE.getDefaultState(), 25);
	private final WorldGenerator lava = new WorldGenMinableNetherUA(Blocks.FLOWING_LAVA.getDefaultState(), 3);
	
    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
    {
    	int i = 3 + random.nextInt(6);
    	
        for (i = 0; i < 5; ++i)
        {
            int j1 = random.nextInt(16);
            int k1 = random.nextInt(101);
            int l1 = random.nextInt(16);
            this.magma.generate(worldIn, random, pos.add(j1, k1, l1));
        }

        
    	i = 3 + random.nextInt(8);
    	
    	for (i = 0; i < 14; ++i)
        {
            int j1 = random.nextInt(16);
            int k1 = random.nextInt(240);
            int l1 = random.nextInt(16);
            this.quartz.generate(worldIn, random, pos.add(j1, k1, l1));
        }
        
        i = 10 + random.nextInt(50);
    	
        for (i = 0; i < 100; ++i)
        {
            int j1 = random.nextInt(16);
            int k1 = random.nextInt(240);
            int l1 = random.nextInt(16);
            this.lava.generate(worldIn, random, pos.add(j1, k1, l1));
        }

        
        
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
    	
    	for (int j5 = 0; j5 < this.glowStonePerChunk; ++j5)
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
    	
    	for (int j5 = 0; j5 < this.glowStonePerChunk; ++j5)
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
    
}

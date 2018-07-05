package net.TelepathicGrunt.UltraAmplified.World.Biome;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorSettingsUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenBushUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenCactusUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenDeadBushUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenLiquidsUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenTallGrassUA;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeDecoratorUA extends BiomeDecorator{
	
	    public ChunkGeneratorSettingsUA chunkProviderSettings;

	    /** Field that holds WorldGenCactus2 */
	    public WorldGenerator cactusGenUA = new WorldGenCactusUA();
	    /** Field that holds mushroomBrown WorldGenFlowers */
	    public WorldGenerator mushroomBrownGenUA = new WorldGenBushUA(Blocks.BROWN_MUSHROOM);
	    /** Field that holds mushroomRed WorldGenFlowers */
	    public WorldGenerator mushroomRedGenUA = new WorldGenBushUA(Blocks.RED_MUSHROOM);
	    
	    @Override
	    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
	    {
	        if (this.decorating)
	        {
	            throw new RuntimeException("Already decorating");
	        }
	        else
	        {
	            this.chunkProviderSettings = new ChunkGeneratorSettingsUA();
	            this.chunkPos = pos;
	            this.dirtGen = new WorldGenMinable(Blocks.DIRT.getDefaultState(), this.chunkProviderSettings.dirtSize);
	            this.gravelOreGen = new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), this.chunkProviderSettings.gravelSize);
	            this.graniteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
	            this.dioriteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), this.chunkProviderSettings.dioriteSize);
	            this.andesiteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize);
	            this.coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), this.chunkProviderSettings.coalSize);
	            this.ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), this.chunkProviderSettings.ironSize);
	            this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
	            this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize);
	            this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize);
	            this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize);
	            this.genDecorations(biome, worldIn, random);
	            this.decorating = false;
	        }
	    }

	    @Override
	    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
	    {
	        net.minecraft.util.math.ChunkPos forgeChunkPos = new net.minecraft.util.math.ChunkPos(chunkPos); // actual ChunkPos instead of BlockPos, used for events
	        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre(worldIn, random, forgeChunkPos));
	        this.generateOres(worldIn, random);

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SAND))
	        for (int i = 0; i < this.sandPatchesPerChunk; ++i)
	        {
	            int j = random.nextInt(16) + 8;
	            int k = random.nextInt(16) + 8;
	            this.sandGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
	        }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CLAY))
	        for (int i1 = 0; i1 < this.clayPerChunk; ++i1)
	        {
	            int l1 = random.nextInt(16) + 8;
	            int i6 = random.nextInt(16) + 8;
	            this.clayGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(l1, 0, i6)));
	        }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SAND_PASS2))
	        for (int j1 = 0; j1 < this.gravelPatchesPerChunk; ++j1)
	        {
	            int i2 = random.nextInt(16) + 8;
	            int j6 = random.nextInt(16) + 8;
	            this.gravelGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(i2, 0, j6)));
	        }

	        int k1 = this.treesPerChunk;

	        if (random.nextFloat() < this.extraTreeChance)
	        {
	            ++k1;
	        }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE))
	        for (int j2 = 0; j2 < k1; ++j2)
	        {
	            int k6 = random.nextInt(16) + 8;
	            int l = random.nextInt(16) + 8;
	            WorldGenAbstractTree worldgenabstracttree = biomeIn.getRandomTreeFeature(random);
	            worldgenabstracttree.setDecorationDefaults();
	            BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(k6, 0, l));

	            if (worldgenabstracttree.generate(worldIn, random, blockpos))
	            {
	                worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
	            }
	            
	            
	            for(int f = 75; f < worldIn.getHeight(this.chunkPos.add(k6, 0, l)).getY() - 10; f++) {
	            	
	            	if(biomeIn == BiomeInit.BiomeSwampland || biomeIn == BiomeInit.BiomeSwamplandM) 
	            	{
	            		if(random.nextInt(4) == 0) 
	            		{
	    	            	k6 = random.nextInt(16) + 8;
	    	                l = random.nextInt(16) + 8;
	    	            	
	    	                blockpos = this.chunkPos.add(k6, f, l);
	    	                
	    	                if (worldgenabstracttree.generate(worldIn, random, blockpos))
	    	                {
	    	                    worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
	    	                }
	    	                
	                	}
	            	}
	            	else 
	            	{
	            		if(random.nextInt(10) == 0) 
	            		{
	    	            	k6 = random.nextInt(16) + 8;
	    	                l = random.nextInt(16) + 8;
	    	            	
	    	                blockpos = this.chunkPos.add(k6, f, l);
	    	                
	    	                if (worldgenabstracttree.generate(worldIn, random, blockpos))
	    	                {
	    	                    worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
	    	                }
	    	                
	                	}
	            	}
	            }
	            
	        }
	        
	        

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM))
	        for (int k2 = 0; k2 < this.bigMushroomsPerChunk; ++k2)
	        {
	            int l6 = random.nextInt(16) + 8;
	            int k10 = random.nextInt(16) + 8;
	            BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(l6, 0, k10));
	            
	            this.bigMushroomGen.generate(worldIn, random, blockpos);
	            
	            for(int f = 75; f < worldIn.getHeight(this.chunkPos.add(l6, 0, k10)).getY() - 10; f++) {
            	
            	
            		if(random.nextInt(3) == 0) 
            		{
    	            	l6 = random.nextInt(16) + 8;
    	                k10 = random.nextInt(16) + 8;
    	            	
    	                blockpos = this.chunkPos.add(l6, f, k10);
    	                
    	                this.bigMushroomGen.generate(worldIn, random, blockpos);
    	                
                	}
	            }
	        }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
	        for (int l2 = 0; l2 < this.flowersPerChunk; ++l2)
	        {
	            int i7 = random.nextInt(16) + 8;
	            int l10 = random.nextInt(16) + 8;
	            int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

	            if (j14 > 0)
	            {
	                int k17 = random.nextInt(j14);
	                BlockPos blockpos1 = this.chunkPos.add(i7, k17, l10);
	                BlockFlower.EnumFlowerType blockflower$enumflowertype = biomeIn.pickRandomFlower(random, blockpos1);
	                BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();

	                if (blockflower.getDefaultState().getMaterial() != Material.AIR)
	                {
	                    this.flowerGen.setGeneratedBlock(blockflower, blockflower$enumflowertype);
	                    this.flowerGen.generate(worldIn, random, blockpos1);
	                }
	            }
	        }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
	        for (int i3 = 0; i3 < this.grassPerChunk; ++i3)
          {
            
             int l6 = random.nextInt(16) + 8;
             int k10 = random.nextInt(16) + 8;
            BlockPos blockpos;

             for(int f = 75; f < 260; f+=6) {
            	
	            	l6 = random.nextInt(16) + 8;
	                k10 = random.nextInt(16) + 8;
	            	
	                blockpos = this.chunkPos.add(l6, f, k10);
	                
	                new WorldGenTallGrassUA(BlockTallGrass.EnumType.GRASS).generate(worldIn, random, blockpos);
        		
            }
          }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.DEAD_BUSH))
	        for (int j3 = 0; j3 < this.deadBushPerChunk; ++j3)
	        {
	            int k7 = random.nextInt(16) + 8;
            int j11 = random.nextInt(16) + 8;
            BlockPos blockpos;

            for(int f = 75; f < 260; f+=6) {
            	
            	k7 = random.nextInt(16) + 8;
            	j11 = random.nextInt(16) + 8;
            	
                blockpos = this.chunkPos.add(k7, f, j11);
                
                new WorldGenDeadBushUA().generate(worldIn, random, blockpos);
            }
	        }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LILYPAD))
	        for (int k3 = 0; k3 < this.waterlilyPerChunk; ++k3)
	        {
	            int l7 = random.nextInt(16) + 8;
	            int k11 = random.nextInt(16) + 8;
	            int i15 = worldIn.getHeight(this.chunkPos.add(l7, 0, k11)).getY() * 2;

	            if (i15 > 0)
	            {
	                int j18 = random.nextInt(i15);
	                BlockPos blockpos4;
	                BlockPos blockpos7;

	                for (blockpos4 = this.chunkPos.add(l7, j18, k11); blockpos4.getY() > 0; blockpos4 = blockpos7)
	                {
	                    blockpos7 = blockpos4.down();

	                    if (!worldIn.isAirBlock(blockpos7))
	                    {
	                        break;
	                    }
	                }

	                this.waterlilyGen.generate(worldIn, random, blockpos4);
	            }
	        }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SHROOM))
	        {
	        	for (int l3 = 0; l3 < this.mushroomsPerChunk; ++l3)
	            {
	                if (random.nextInt(4) == 0)
	                {
	                    int i8 = random.nextInt(16) + 8;
	                    int l11 = random.nextInt(16) + 8;
	                    BlockPos blockpos2 = worldIn.getHeight(this.chunkPos.add(i8, 0, l11));
	                    this.mushroomBrownGen.generate(worldIn, random, blockpos2);
	                }

	                if (random.nextInt(8) == 0)
	                {
	                    int j8 = random.nextInt(16) + 8;
	                    int i12 = random.nextInt(16) + 8;
	                    BlockPos blockpos5 = worldIn.getHeight(this.chunkPos.add(j8, 0, i12));
	                    this.mushroomRedGen.generate(worldIn, random, blockpos5);
	                    
	                }
	                
	                if (random.nextInt(3) == 0)
	            {
	                int i4 = random.nextInt(16) + 8;
	                int k8 = random.nextInt(16) + 8;

	            	int height = (worldIn.getHeight(this.chunkPos.add(i4, 0, k8))).getY();
	            	if(height > 75) {
	            		height -= 75;
	            	}

	                int k15 = random.nextInt(height)+75;
	                
	                this.mushroomBrownGen.generate(worldIn, random, this.chunkPos.add(i4, k15, k8));
	                
	            }

	            if (random.nextInt(6) == 0)
	            {
	                int j4 = random.nextInt(16) + 8;
	                int l8 = random.nextInt(16) + 8;
	                
	                int height = (worldIn.getHeight(this.chunkPos.add(j4, 0, l8))).getY();
	            	if(height > 75) {
	            		height -= 75;
	            	}

	                int l15 = random.nextInt(height)+75;
	                this.mushroomRedGen.generate(worldIn, random, this.chunkPos.add(j4, l15, l8));
	                
	            }
	            }

	           
	            
	            if (random.nextInt(3) == 0)
	            {
	                int i4 = random.nextInt(16) + 8;
	                int k8 = random.nextInt(16) + 8;

	            	int height = (worldIn.getHeight(this.chunkPos.add(i4, 0, k8))).getY();
	            	if(height > 75) {
	            		height -= 75;
	            	}

	                int k15 = random.nextInt(height)+75;
	                
	                this.mushroomBrownGen.generate(worldIn, random, this.chunkPos.add(i4, k15, k8));
	                
	            }

	            if (random.nextInt(6) == 0)
	            {
	                int j4 = random.nextInt(16) + 8;
	                int l8 = random.nextInt(16) + 8;
	                
	                int height = (worldIn.getHeight(this.chunkPos.add(j4, 0, l8))).getY();
	            	if(height > 75) {
	            		height -= 75;
	            	}

	                int l15 = random.nextInt(height)+75;
	                this.mushroomRedGen.generate(worldIn, random, this.chunkPos.add(j4, l15, l8));
	                
	            }
	            
	            
	            if (random.nextInt(6) == 0)
	            {
	                int i4 = random.nextInt(16) + 8;
	                int k8 = random.nextInt(16) + 8;
	                int k15 = random.nextInt(75);
	                this.mushroomBrownGen.generate(worldIn, random, this.chunkPos.add(i4, k15, k8));
	                
	            }

	            if (random.nextInt(12) == 0)
	            {
	                int j4 = random.nextInt(16) + 8;
	                int l8 = random.nextInt(16) + 8;
	                int l15 = random.nextInt(75);
	                this.mushroomRedGen.generate(worldIn, random, this.chunkPos.add(j4, l15, l8));
	                
	            }
	        
	        } // End of Mushroom generation
	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.REED))
	        {
	        for (int k4 = 0; k4 < this.reedsPerChunk; ++k4)
	        {
	            int i9 = random.nextInt(16) + 8;
	            int l12 = random.nextInt(16) + 8;
	            int i16 = worldIn.getHeight(this.chunkPos.add(i9, 0, l12)).getY() * 2;

	            if (i16 > 0)
	            {
	                int l18 = random.nextInt(i16);
	                this.reedGen.generate(worldIn, random, this.chunkPos.add(i9, l18, l12));
	            }
	        }

	        for (int l4 = 0; l4 < 10; ++l4)
	        {
	            int j9 = random.nextInt(16) + 8;
	            int i13 = random.nextInt(16) + 8;
	            int j16 = worldIn.getHeight(this.chunkPos.add(j9, 0, i13)).getY() * 2;

	            if (j16 > 0)
	            {
	                int i19 = random.nextInt(j16);
	                this.reedGen.generate(worldIn, random, this.chunkPos.add(j9, i19, i13));
	            }
	        }
	        } // End of Reed generation
	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.PUMPKIN))
	        if (random.nextInt(32) == 0)
	        {
	            int i5 = random.nextInt(16) + 8;
	            int k9 = random.nextInt(16) + 8;
	            int j13 = worldIn.getHeight(this.chunkPos.add(i5, 0, k9)).getY() * 2;

	            if (j13 > 0)
	            {
	                int k16 = random.nextInt(j13);
	                (new WorldGenPumpkin()).generate(worldIn, random, this.chunkPos.add(i5, k16, k9));
	            }
	        }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CACTUS))
	        for (int j5 = 0; j5 < this.cactiPerChunk; ++j5)
	        {
	            int l9 = random.nextInt(16) + 8;
	            int k13 = random.nextInt(16) + 8;
	            int l16 = worldIn.getHeight(this.chunkPos.add(l9, 0, k13)).getY() * 2;

	            if (l16 > 0)
	            {
	                int j19 = random.nextInt(l16);
	                this.cactusGenUA.generate(worldIn, random, this.chunkPos.add(l9, j19, k13));
	            }
	        }

	        
            for (int k5 = 0; k5 < 35; ++k5)
            {
                int i10 = random.nextInt(16) + 8;
                int l13 = random.nextInt(16) + 8;
                int i17 = random.nextInt(random.nextInt(175) + 8) + 75;
                BlockPos blockpos6 = this.chunkPos.add(i10, i17, l13);
                new WorldGenLiquidsUA(Blocks.FLOWING_WATER).generate(worldIn, random, blockpos6);
                
            }

            for (int l5 = 0; l5 < 14; ++l5)
            {
                int j10 = random.nextInt(16) + 8;
                int i14 = random.nextInt(16) + 8;
                int j17 = random.nextInt(random.nextInt(175) + 8) + 75;
                BlockPos blockpos3 = this.chunkPos.add(j10, j17, i14);
                new WorldGenLiquidsUA(Blocks.FLOWING_LAVA).generate(worldIn, random, blockpos3);
            }
            
            if (random.nextInt(3) == 0)
            {
                int i10 = random.nextInt(16) + 8;
                int l13 = random.nextInt(16) + 8;
                int i17 = random.nextInt(random.nextInt(70) + 8) + 8;

                BlockPos blockpos6 = this.chunkPos.add(i10, i17, l13);
                new WorldGenLiquidsUA(Blocks.FLOWING_WATER).generate(worldIn, random, blockpos6);
            }

            for (int l5 = 0; l5 < 4; ++l5)
            {
                int j10 = random.nextInt(16) + 8;
                int i14 = random.nextInt(16) + 8;
                int j17 = random.nextInt(random.nextInt(70) + 8) + 8;
                BlockPos blockpos3 = this.chunkPos.add(j10, j17, i14);
                new WorldGenLiquidsUA(Blocks.FLOWING_LAVA).generate(worldIn, random, blockpos3);
            }
	        
	        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post(worldIn, random, forgeChunkPos));
	    }

	    /**
	     * Generates ores in the current chunk
	     */
	    @Override
	    protected void generateOres(World worldIn, Random random)
	    {
	        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, random, chunkPos));
	        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dirtGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIRT))
	        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight);
	        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, gravelOreGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRAVEL))
	        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.gravelCount, this.gravelOreGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight);
	        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dioriteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIORITE))
	        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.dioriteCount, this.dioriteGen, this.chunkProviderSettings.dioriteMinHeight, this.chunkProviderSettings.dioriteMaxHeight);
	        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, graniteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRANITE))
	        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.graniteCount, this.graniteGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight);
	        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, andesiteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.ANDESITE))
	        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.andesiteCount, this.andesiteGen, this.chunkProviderSettings.andesiteMinHeight, this.chunkProviderSettings.andesiteMaxHeight);
	        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, coalGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL))
	        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.coalCount, this.coalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);
	        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, ironGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON))
	        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);
	        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, goldGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD))
	        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.goldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);
	        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, redstoneGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.REDSTONE))
	        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight);
	        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, diamondGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIAMOND))
	        this.genStandardOre1(worldIn, random, this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight);
	        if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, lapisGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.LAPIS))
	        this.genStandardOre2(worldIn, random, this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread);
	        net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Post(worldIn, random, chunkPos));
	    }

	    /**
	     * Standard ore generation helper. Vanilla uses this to generate most ores.
	     * The main difference between this and {@link #genStandardOre2} is that this takes min and max heights, while
	     * genStandardOre2 takes center and spread.
	     */
	    @Override
	    protected void genStandardOre1(World worldIn, Random random, int blockCount, WorldGenerator generator, int minHeight, int maxHeight)
	    {
	        if (maxHeight < minHeight)
	        {
	            int i = minHeight;
	            minHeight = maxHeight;
	            maxHeight = i;
	        }
	        else if (maxHeight == minHeight)
	        {
	            if (minHeight < 255)
	            {
	                ++maxHeight;
	            }
	            else
	            {
	                --minHeight;
	            }
	        }

	        for (int j = 0; j < blockCount; ++j)
	        {
	            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(maxHeight - minHeight) + minHeight, random.nextInt(16));
	            generator.generate(worldIn, random, blockpos);
	        }
	    }

	    /**
	     * Standard ore generation helper. Vanilla uses this to generate Lapis Lazuli.
	     * The main difference between this and {@link #genStandardOre1} is that this takes takes center and spread, while
	     * genStandardOre1 takes min and max heights.
	     */
	    @Override
	    protected void genStandardOre2(World worldIn, Random random, int blockCount, WorldGenerator generator, int centerHeight, int spread)
	    {
	        for (int i = 0; i < blockCount; ++i)
	        {
	            BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(spread) + random.nextInt(spread) + centerHeight - spread, random.nextInt(16));
	            generator.generate(worldIn, random, blockpos);
	        }
	    }
}
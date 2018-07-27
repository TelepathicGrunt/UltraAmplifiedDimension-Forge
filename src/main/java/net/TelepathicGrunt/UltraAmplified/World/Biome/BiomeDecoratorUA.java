package net.TelepathicGrunt.UltraAmplified.World.Biome;

import java.util.Random;

import net.TelepathicGrunt.UltraAmplified.World.Generation.ChunkGeneratorSettingsUA;
import net.TelepathicGrunt.UltraAmplified.World.gen.feature.WorldGenBetterCactusUA;
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
	
		//used to know how much to spawn stuff and where
		//will be removed in 1.13 because configs will be able to be set per world 
	    public static ChunkGeneratorSettingsUA chunkProviderSettingsUA;

	    /** Field that is used to generate better cactus because it's better!*/
		public WorldGenerator shortCactus = new WorldGenBetterCactusUA(6);
	    
	    
	    @Override
	    public void decorate(World worldIn, Random random, Biome biome, BlockPos pos)
	    {
	        if (this.decorating)
	        {
	            throw new RuntimeException("Already decorating");
	        }
	        else
	        {
	        	//sets the settings for ores and stones
	            this.chunkProviderSettingsUA = new ChunkGeneratorSettingsUA();
	            this.chunkPos = pos;
	            this.dirtGen = new WorldGenMinable(Blocks.DIRT.getDefaultState(), this.chunkProviderSettingsUA.dirtSize);
	            this.gravelOreGen = new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), this.chunkProviderSettingsUA.gravelSize);
	            this.graniteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.GRANITE), this.chunkProviderSettingsUA.graniteSize);
	            this.dioriteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.DIORITE), this.chunkProviderSettingsUA.dioriteSize);
	            this.andesiteGen = new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty(BlockStone.VARIANT, BlockStone.EnumType.ANDESITE), this.chunkProviderSettingsUA.andesiteSize);
	            this.coalGen = new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), this.chunkProviderSettingsUA.coalSize);
	            this.ironGen = new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), this.chunkProviderSettingsUA.ironSize);
	            this.goldGen = new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), this.chunkProviderSettingsUA.goldSize);
	            this.redstoneGen = new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), this.chunkProviderSettingsUA.redstoneSize);
	            this.diamondGen = new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), this.chunkProviderSettingsUA.diamondSize);
	            this.lapisGen = new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), this.chunkProviderSettingsUA.lapisSize);
	            
	            //actual decorating starts
	            this.genDecorations(biome, worldIn, random);
	            this.decorating = false;
	        }
	    }
	    
		

	    //generates all the decorations for biomes
	    @Override
	    protected void genDecorations(Biome biomeIn, World worldIn, Random random)
	    {
	        net.minecraft.util.math.ChunkPos forgeChunkPos = new net.minecraft.util.math.ChunkPos(chunkPos); // actual ChunkPos instead of BlockPos, used for events
	        
	        
	        //generates ores
	        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre(worldIn, random, forgeChunkPos));
	        this.generateOres(worldIn, random);

	        
	        //generates patches
	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SAND))
	        for (int i = 0; i < this.sandPatchesPerChunk; ++i)
	        {
	            int x = random.nextInt(16) + 8;
	            int z = random.nextInt(16) + 8;
	            this.sandGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(x, 0, z)));
	        }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CLAY))
	        for (int i1 = 0; i1 < this.clayPerChunk; ++i1)
	        {
	            int x = random.nextInt(16) + 8;
	            int z = random.nextInt(16) + 8;
	            this.clayGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(x, 0, z)));
	        }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SAND_PASS2))
	        for (int j1 = 0; j1 < this.gravelPatchesPerChunk; ++j1)
	        {
	            int x = random.nextInt(16) + 8;
	            int z = random.nextInt(16) + 8;
	            this.gravelGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(x, 0, z)));
	        }

	        
	        //generate trees and large mushrooms
	        int treeCount = this.treesPerChunk;

	        if (random.nextFloat() < this.extraTreeChance)
	        {
	            ++treeCount;
	        }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE))
	        for (int j2 = 0; j2 < treeCount; ++j2)
	        {
	            int x = random.nextInt(16) + 8;
	            int z = random.nextInt(16) + 8;
	            WorldGenAbstractTree worldgenabstracttree = biomeIn.getRandomTreeFeature(random);
	            worldgenabstracttree.setDecorationDefaults();
	            BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(x, 0, z));

	            if (worldgenabstracttree.generate(worldIn, random, blockpos))
	            {
	                worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
	            }
	            
	            
	            for(int height = 75; height < worldIn.getHeight(this.chunkPos.add(x, 0, z)).getY() - 10; height++) {
	            	
	            	if(biomeIn == BiomeInit.BiomeSwampland || biomeIn == BiomeInit.BiomeSwamplandM) 
	            	{
	            		//increased spawnrate of trees under terrain in swamps (due to swamp's already low tree spawnrate. Other biomes are fine)
	            		if(random.nextInt(4) == 0) 
	            		{
	    	            	x = random.nextInt(16) + 8;
	    	                z = random.nextInt(16) + 8;
	    	            	
	    	                blockpos = this.chunkPos.add(x, height, z);
	    	                
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
	    	            	x = random.nextInt(16) + 8;
	    	                z = random.nextInt(16) + 8;
	    	            	
	    	                blockpos = this.chunkPos.add(x, height, z);
	    	                
	    	                if (worldgenabstracttree.generate(worldIn, random, blockpos))
	    	                {
	    	                    worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
	    	                }
	    	                
	                	}
	            	}
	            }
	            
	        }
	        
	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM))
	        for (int currentBigMushroomCount = 0; currentBigMushroomCount < this.bigMushroomsPerChunk; ++currentBigMushroomCount)
	        {
	            int x = random.nextInt(16) + 8;
	            int z = random.nextInt(16) + 8;
	            BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(x, 0, z));
	            
	            this.bigMushroomGen.generate(worldIn, random, blockpos);
	            
	            for(int height = 75; height < worldIn.getHeight(this.chunkPos.add(x, 0, z)).getY() - 10; height++) {
            	
            	
            		if(random.nextInt(3) == 0) 
            		{
    	            	x = random.nextInt(16) + 8;
    	                z = random.nextInt(16) + 8;
    	            	
    	                blockpos = this.chunkPos.add(x, height, z);
    	                
    	                this.bigMushroomGen.generate(worldIn, random, blockpos);
    	                
                	}
	            }
	        }

	        
	        
	        //generates flowers, grass, deadbush, and waterlily
	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
	        for (int currentFlowerCount = 0; currentFlowerCount < this.flowersPerChunk; ++currentFlowerCount)
	        {
	            int x = random.nextInt(16) + 8;
	            int z = random.nextInt(16) + 8;
	            int maxHeight = worldIn.getHeight(this.chunkPos.add(x, 0, z)).getY() + 32;

	            if (maxHeight > 0)
	            {
	                int height = random.nextInt(maxHeight);
	                BlockPos blockpos1 = this.chunkPos.add(x, height, z);
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
	        for (int currentCount = 0; currentCount < this.grassPerChunk; ++currentCount)
            {
				BlockPos blockpos;

				for (int height = 75; height <= 255; height += 6) {

					int x = random.nextInt(16) + 8;
					int z = random.nextInt(16) + 8;

					blockpos = this.chunkPos.add(x, height, z);

					new WorldGenTallGrassUA(BlockTallGrass.EnumType.GRASS).generate(worldIn, random, blockpos);

            }
          }

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.DEAD_BUSH))
				for (int currentCount = 0; currentCount < this.deadBushPerChunk; ++currentCount) {

					BlockPos blockpos;
	
					for (int height = 75; height < 260; height += 6) {
	
						int x = random.nextInt(16) + 8;
						int z = random.nextInt(16) + 8;
	
						blockpos = this.chunkPos.add(x, height, z);
	
						new WorldGenDeadBushUA().generate(worldIn, random, blockpos);
					}
				}

	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LILYPAD))
		        for (int currentCount = 0; currentCount < this.waterlilyPerChunk; ++currentCount)
		        {
		            int x = random.nextInt(16) + 8;
		            int z = random.nextInt(16) + 8;
	                int y = random.nextInt(255);
	                
	                BlockPos blockpos4;
	                BlockPos blockpos7;

	                for (blockpos4 = this.chunkPos.add(x, y, z); blockpos4.getY() > 0; blockpos4 = blockpos7)
	                {
	                    blockpos7 = blockpos4.down();

	                    if (!worldIn.isAirBlock(blockpos7))
	                    {
	                        break;
	                    }
	                }

	                this.waterlilyGen.generate(worldIn, random, blockpos4);
	            }
		        

	        
	        
	        //generates mushrooms
	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SHROOM))
	        {
	        	for (int currentCount = 0; currentCount < this.mushroomsPerChunk; ++currentCount)
	            {
	        		//generates mushrooms at top of landscape
	                if (random.nextInt(4) == 0)
	                {
	                    int x = random.nextInt(16) + 8;
	                    int z = random.nextInt(16) + 8;
	                    BlockPos blockpos2 = worldIn.getHeight(this.chunkPos.add(x, 0, z));
	                    this.mushroomBrownGen.generate(worldIn, random, blockpos2);
	                }

	                if (random.nextInt(8) == 0)
	                {
	                    int x = random.nextInt(16) + 8;
	                    int z = random.nextInt(16) + 8;
	                    BlockPos blockpos5 = worldIn.getHeight(this.chunkPos.add(x, 0, z));
	                    this.mushroomRedGen.generate(worldIn, random, blockpos5);
	                    
	                }
	                
	                
	                //generates mushroom under landscape based on biome's mushroomPerChunk
					if (random.nextInt(3) == 0) {
						int x = random.nextInt(16) + 8;
						int z = random.nextInt(16) + 8;
	
						int height = (worldIn.getHeight(this.chunkPos.add(x, 0, z))).getY();
						
						if (height > 75) {
							height -= 75;
						}
	
		                int y = random.nextInt(height)+75;
		                
		                this.mushroomBrownGen.generate(worldIn, random, this.chunkPos.add(x, y, z));
		                
		            }

		            if (random.nextInt(6) == 0)
		            {
		                int x = random.nextInt(16) + 8;
		                int z = random.nextInt(16) + 8;
		                
		                int height = (worldIn.getHeight(this.chunkPos.add(x, 0, z))).getY();
		            	if(height > 75) {
		            		height -= 75;
		            	}
	
		                int y = random.nextInt(height)+75;
		                this.mushroomRedGen.generate(worldIn, random, this.chunkPos.add(x, y, z));
		                
		            }
	            }

	           //underground mushrooms that generate in all biomes but under terrain but not underground
	            if (random.nextInt(3) == 0)
	            {
	                int x = random.nextInt(16) + 8;
	                int z = random.nextInt(16) + 8;

	            	int height = (worldIn.getHeight(this.chunkPos.add(x, 0, z))).getY();
	            	if(height > 75) {
	            		height -= 75;
	            	}

	                int y = random.nextInt(height)+75;
	                
	                this.mushroomBrownGen.generate(worldIn, random, this.chunkPos.add(x, y, z));
	                
	            }

	            if (random.nextInt(6) == 0)
	            {
	                int x = random.nextInt(16) + 8;
	                int z = random.nextInt(16) + 8;
	                
	                int height = (worldIn.getHeight(this.chunkPos.add(x, 0, z))).getY();
	            	if(height > 75) {
	            		height -= 75;
	            	}

	                int y = random.nextInt(height)+75;
	                this.mushroomRedGen.generate(worldIn, random, this.chunkPos.add(x, y, z));
	                
	            }
	            
	            
	            //generates mushrooms underground at a different rate than above
	            if (random.nextInt(6) == 0)
	            {
	                int x = random.nextInt(16) + 8;
	                int z = random.nextInt(16) + 8;
	                int y = random.nextInt(75);
	                this.mushroomBrownGen.generate(worldIn, random, this.chunkPos.add(x, y, z));
	                
	            }

	            if (random.nextInt(12) == 0)
	            {
	                int x = random.nextInt(16) + 8;
	                int z = random.nextInt(16) + 8;
	                int y = random.nextInt(75);
	                this.mushroomRedGen.generate(worldIn, random, this.chunkPos.add(x, y, z));
	                
	            }
	        
	        } // End of Mushroom generation
	        
	        
	        
	        //generates reeds
	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.REED))
	        {
		        for (int currentCount = 0; currentCount < this.reedsPerChunk; ++currentCount)
		        {
		            int x = random.nextInt(16) + 8;
		            int z = random.nextInt(16) + 8;
	                int y = random.nextInt(180)+75;
	                
	                this.reedGen.generate(worldIn, random, this.chunkPos.add(x, y, z));
		        }
	        } // End of Reed generation
	        
	        
	        
	        //generates pumpkins
	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.PUMPKIN))
		        if (random.nextInt(20) == 0)
		        {
		            int x = random.nextInt(16) + 8;
		            int z = random.nextInt(16) + 8;
	                int y = random.nextInt(245)+10;
	                new WorldGenPumpkin().generate(worldIn, random, this.chunkPos.add(x, y, z));
		        }

	        
	        
	        //generates cactus
	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CACTUS))
     	        for (int currentCount = 0; currentCount < this.cactiPerChunk; ++currentCount)
     	        {
 	                int y = random.nextInt(180) + 70;
 	                shortCactus.generate(worldIn, random, this.chunkPos.add(16, y, 16));
     	        }

	        
	        
	        //generates waterfalls and lavafalls
            for (int currentCount = 0; currentCount < this.chunkProviderSettingsUA.waterfallCount; ++currentCount)
            {
                int x = random.nextInt(16) + 8;
                int z = random.nextInt(16) + 8;
                int y = random.nextInt(random.nextInt(175) + 8) + 75;
                BlockPos blockpos6 = this.chunkPos.add(x, y, z);
                new WorldGenLiquidsUA(Blocks.FLOWING_WATER).generate(worldIn, random, blockpos6);
                
            }

            for (int currentCount = 0; currentCount < this.chunkProviderSettingsUA.lavafallCount; ++currentCount)
            {
                int x = random.nextInt(16) + 8;
                int z = random.nextInt(16) + 8;
                int y = random.nextInt(random.nextInt(175) + 8) + 75;
                BlockPos blockpos3 = this.chunkPos.add(x, y, z);
                new WorldGenLiquidsUA(Blocks.FLOWING_LAVA).generate(worldIn, random, blockpos3);
            }
            
            //if statement is like this to make underground waterfalls not attempt to spawn in every chunk to increase its rarity
            if (this.chunkProviderSettingsUA.undergroundWaterfallCount != 0 && random.nextInt(this.chunkProviderSettingsUA.undergroundWaterfallCount) == 0)
            {
                int x = random.nextInt(16) + 8;
                int z = random.nextInt(16) + 8;
                int y = random.nextInt(random.nextInt(70) + 8) + 8;

                BlockPos blockpos6 = this.chunkPos.add(x, y, z);
                new WorldGenLiquidsUA(Blocks.FLOWING_WATER).generate(worldIn, random, blockpos6);
            }

            for (int currentCount = 0; currentCount < this.chunkProviderSettingsUA.undergroundLavafallCount; ++currentCount)
            {
                int x = random.nextInt(16) + 8;
                int z = random.nextInt(16) + 8;
                int y = random.nextInt(random.nextInt(70) + 8) + 8;
                BlockPos blockpos3 = this.chunkPos.add(x, y, z);
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
	    	//very messy. Even I go crossed-eye looking at this lol. This is Vanilla coding
			
	    	
	    	net.minecraftforge.common.MinecraftForge.ORE_GEN_BUS.post(new net.minecraftforge.event.terraingen.OreGenEvent.Pre(worldIn, random, chunkPos));
			
			//dirt
	    	if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dirtGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIRT))
				this.genStandardOre1(worldIn, random, this.chunkProviderSettingsUA.dirtCount, this.dirtGen,
						this.chunkProviderSettingsUA.dirtMinHeight, this.chunkProviderSettingsUA.dirtMaxHeight);
			
	    	//gravel
			if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, gravelOreGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRAVEL))
				this.genStandardOre1(worldIn, random, this.chunkProviderSettingsUA.gravelCount, this.gravelOreGen,
						this.chunkProviderSettingsUA.gravelMinHeight, this.chunkProviderSettingsUA.gravelMaxHeight);
			
			//diorite
			if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, dioriteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIORITE))
				this.genStandardOre1(worldIn, random, this.chunkProviderSettingsUA.dioriteCount, this.dioriteGen,
						this.chunkProviderSettingsUA.dioriteMinHeight, this.chunkProviderSettingsUA.dioriteMaxHeight);
			
			//granite
			if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, graniteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GRANITE))
				this.genStandardOre1(worldIn, random, this.chunkProviderSettingsUA.graniteCount, this.graniteGen,
						this.chunkProviderSettingsUA.graniteMinHeight, this.chunkProviderSettingsUA.graniteMaxHeight);
			
			//andersite
			if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, andesiteGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.ANDESITE))
				this.genStandardOre1(worldIn, random, this.chunkProviderSettingsUA.andesiteCount, this.andesiteGen,
						this.chunkProviderSettingsUA.andesiteMinHeight, this.chunkProviderSettingsUA.andesiteMaxHeight);
			
			//coal
			if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, coalGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.COAL))
				this.genStandardOre1(worldIn, random, this.chunkProviderSettingsUA.coalCount, this.coalGen,
						this.chunkProviderSettingsUA.coalMinHeight, this.chunkProviderSettingsUA.coalMaxHeight);
			
			//iron
			if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, ironGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.IRON))
				this.genStandardOre1(worldIn, random, this.chunkProviderSettingsUA.ironCount, this.ironGen,
						this.chunkProviderSettingsUA.ironMinHeight, this.chunkProviderSettingsUA.ironMaxHeight);
			
			//gold
			if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, goldGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.GOLD))
				this.genStandardOre1(worldIn, random, this.chunkProviderSettingsUA.goldCount, this.goldGen,
						this.chunkProviderSettingsUA.goldMinHeight, this.chunkProviderSettingsUA.goldMaxHeight);
			
			//redstone
			if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, redstoneGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.REDSTONE))
				this.genStandardOre1(worldIn, random, this.chunkProviderSettingsUA.redstoneCount, this.redstoneGen,
						this.chunkProviderSettingsUA.redstoneMinHeight, this.chunkProviderSettingsUA.redstoneMaxHeight);
			
			//diamond
			if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, diamondGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.DIAMOND))
				this.genStandardOre1(worldIn, random, this.chunkProviderSettingsUA.diamondCount, this.diamondGen,
						this.chunkProviderSettingsUA.diamondMinHeight, this.chunkProviderSettingsUA.diamondMaxHeight);
			
			//lapis
			if (net.minecraftforge.event.terraingen.TerrainGen.generateOre(worldIn, random, lapisGen, chunkPos, net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.LAPIS))
				this.genStandardOre2(worldIn, random, this.chunkProviderSettingsUA.lapisCount, this.lapisGen,
						this.chunkProviderSettingsUA.lapisCenterHeight, this.chunkProviderSettingsUA.lapisSpread);
			
			
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
	     * 
	     * used only for lapis ore
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
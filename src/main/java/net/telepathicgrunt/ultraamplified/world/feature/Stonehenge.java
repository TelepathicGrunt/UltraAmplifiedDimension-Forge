package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;
import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.server.ServerWorld;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class Stonehenge extends Feature<NoFeatureConfig> {
	
	public Stonehenge(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}



	private static int perfectStoneCount = 0;
	private static boolean markedForPerfection = false;
	
	protected static final Set<BlockState> unAcceptableBlocks = 
    		Stream.of(
	    		Blocks.AIR.getDefaultState(),
	    		Blocks.WATER.getDefaultState(),
	    		Blocks.LAVA.getDefaultState(),
	    		Blocks.SLIME_BLOCK.getDefaultState(),
				Blocks.CAVE_AIR.getDefaultState()
    		).collect(Collectors.toCollection(HashSet::new));
	
	private static enum StoneHengeType {
        SIDE,
        CORNER
    }
	
	//this structure is a bit more complicated than usual.
	//The goal is to have 8 stone blocks in a circle with a high chance at being unbroken. 
	//If all are unbroken, generates enchanting table in center
	
	

    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_) 
    {	
        
		//makes sure this stonehenge does not spawn too close to world height border or it will get cut off.
  		if(!ConfigUA.miniStructureGeneration || position.getY() > 248) {
  			return false;
  		}
    	
		//makes sure it generates with land around it instead of cutting into cliffs or hanging over an edge by checking if block at north, east, west, and south are acceptable terrain blocks that appear only at top of land.
		for(int x = -10; x <= 10; x = x + 5) 
		{
			for(int z = -10; z <= 10; z = z + 5) 
			{
				if( Math.abs(x * z) != 100 && 
						(unAcceptableBlocks.contains(worldIn.getBlockState(position.down(2).west(x).north(z))) &&
						 unAcceptableBlocks.contains(worldIn.getBlockState(position.down(1).west(x).north(z))) &&
						 unAcceptableBlocks.contains(worldIn.getBlockState(position.west(x).north(z)))
						)) {
					return false;
				}
			}
		}
		
		
	 
		//UltraAmplified.LOGGER.debug("Stonehenge | " + position.getX() + " "+position.getZ());
		
		//field
		//6.7% of being a perfect stonehenge right off the bat
		markedForPerfection = rand.nextInt(15) == 0;
		perfectStoneCount = 0;
		TemplateManager templatemanager = ((ServerWorld)worldIn.getWorld()).getSaveHandler().getStructureTemplateManager();
		Template template;
		BlockState iblockstate = worldIn.getBlockState(position);
		
		worldIn.setBlockState(position, iblockstate, 3);
		
		
		
		//SIDE STONES
		
		//north stone
		template = pickStonehengeStyle(StoneHengeType.SIDE, rand, templatemanager);
		if(template == null)
		{
			UltraAmplified.LOGGER.warn("a side stonehenge NTB does not exist!");
			return false;
		}
		
		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos) null);
		
		
		template.addBlocksToWorld(worldIn, position.down(2).north(11).west(2), placementsettings);


		//East stone - rotated 90 degrees
		template = pickStonehengeStyle(StoneHengeType.SIDE, rand, templatemanager);;
		if(template == null)
		{
			UltraAmplified.LOGGER.warn("a side stonehenge NTB does not exist!");
			return false;
		}

		placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.CLOCKWISE_90).setIgnoreEntities(false).setChunk((ChunkPos) null);
		
		
		template.addBlocksToWorld(worldIn, position.down(2).north(2).east(11), placementsettings);
		
		
		//south stone - rotated 180 degrees
		template = pickStonehengeStyle(StoneHengeType.SIDE, rand, templatemanager);;
		if(template == null)
		{
			UltraAmplified.LOGGER.warn("a side stonehenge NTB does not exist!");
			return false;
		}

		placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.CLOCKWISE_180).setIgnoreEntities(false).setChunk((ChunkPos) null);
		
		
		template.addBlocksToWorld(worldIn, position.down(2).south(11).east(2), placementsettings);
		
		
		
		//West stone - rotated 270 degrees
		template = pickStonehengeStyle(StoneHengeType.SIDE, rand, templatemanager);;
		if(template == null)
		{
			UltraAmplified.LOGGER.warn("a side stonehenge NTB does not exist!");
			return false;
		}

		placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.COUNTERCLOCKWISE_90).setIgnoreEntities(false).setChunk((ChunkPos) null);
		
		
		template.addBlocksToWorld(worldIn, position.down(2).south(2).west(11), placementsettings);
		
		
		
		
		//CORNER STONE
		
		//north west stone
		template = pickStonehengeStyle(StoneHengeType.CORNER, rand, templatemanager);;
		if(template == null)
		{
			UltraAmplified.LOGGER.warn("a corner stonehenge NTB does not exist!");
			return false;
		}

		placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos) null);
		
		
		template.addBlocksToWorld(worldIn, position.down(2).north(9).west(9), placementsettings);


		//north east stone
		template = pickStonehengeStyle(StoneHengeType.CORNER, rand, templatemanager);;
		if(template == null)
		{
			UltraAmplified.LOGGER.warn("a corner stonehenge NTB does not exist!");
			return false;
		}

		placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.CLOCKWISE_90).setIgnoreEntities(false).setChunk((ChunkPos) null);
		
		
		template.addBlocksToWorld(worldIn, position.down(2).north(9).east(9), placementsettings);

		
		//south east stone
		template = pickStonehengeStyle(StoneHengeType.CORNER, rand, templatemanager);;
		if(template == null)
		{
			UltraAmplified.LOGGER.warn("a corner stonehenge NTB does not exist!");
			return false;
		}

		placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.CLOCKWISE_180).setIgnoreEntities(false).setChunk((ChunkPos) null);
		
		
		template.addBlocksToWorld(worldIn, position.down(2).south(9).east(9), placementsettings);
		
		
		//south west stone
		template = pickStonehengeStyle(StoneHengeType.CORNER, rand, templatemanager);;
		if(template == null)
		{
			UltraAmplified.LOGGER.warn("a corner stonehenge NTB does not exist!");
			return false;
		}

		placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.COUNTERCLOCKWISE_90).setIgnoreEntities(false).setChunk((ChunkPos) null);
		
		
		template.addBlocksToWorld(worldIn, position.down(2).south(9).west(9), placementsettings);
		
		
		
		
		
		//center of stonehenge.
		//If all stones are perfect, generates crafting table, otherwise, place a small patch of stones
		if(perfectStoneCount == 8) 
		{
			template =  templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":stonehengeperfectcenter"));
		}
		else 
		{
			template =  templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":stonehengecenter"));
		}
		
		if(template == null)
		{
			UltraAmplified.LOGGER.warn("a center stonehenge NTB does not exist!");
			return false;
		}
		
	    placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos) null);
		
		
		template.addBlocksToWorld(worldIn, position.down().north(2).west(2), placementsettings);
		
		return true;
	}
	

	
	
	//picks one out of four templates for the stone henge
	private Template pickStonehengeStyle(StoneHengeType type, Random rand, TemplateManager templatemanager) 
	{
		int hengeType;
		
		//Generates only perfect stones if markedForPerfection is true.
		//otherwise, chance of being a perfect stone increases as more perfect stones are chosen and picks randomly from the 3 broken stones types otherwise
		if(markedForPerfection ? true : rand.nextInt(8-(perfectStoneCount/2+3)) == 0) 
		{
			perfectStoneCount++;
			hengeType = 0;
		}
		else 
		{
			hengeType = rand.nextInt(3)+1;
		}
		
		
		//chooses the correct template for if the stone is on side or corner
		//if the perfect stone is chosen, increased the count for perfect stones
		if(type == StoneHengeType.SIDE) 
		{
			if(hengeType == 0) 
			{
				return templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":stonehenge1"));
			}
			else if(hengeType == 1) 
			{
				return templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":stonehenge2"));
			}
			else if(hengeType == 2) 
			{
				return templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":stonehenge3"));
			}
			else
			{
				return templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":stonehenge4"));
			}
		}
		else 
		{
			if(hengeType == 0) 
			{
				return templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":stonehenge5"));
			}
			else if(hengeType == 1) 
			{
				return templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":stonehenge6"));
			}
			else if(hengeType == 2) 
			{
				return templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":stonehenge7"));
			}
			else
			{
				return templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":stonehenge8"));
			}
		}
	}
	
}

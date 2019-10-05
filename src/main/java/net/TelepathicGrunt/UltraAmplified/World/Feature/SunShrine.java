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

public class SunShrine extends Feature<NoFeatureConfig> {

	

	public SunShrine(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	protected static final Set<BlockState> acceptableBlocks = 
    		Stream.of(
	    		Blocks.DIRT.getDefaultState(),
	    		Blocks.GRASS_BLOCK.getDefaultState(),
	    		Blocks.PODZOL.getDefaultState(),
	    		Blocks.COARSE_DIRT.getDefaultState(),
	    		Blocks.SAND.getDefaultState(),
	    		Blocks.SANDSTONE.getDefaultState(),
				Blocks.GRASS_PATH.getDefaultState()
    		).collect(Collectors.toCollection(HashSet::new));
	
	
	//first NTB structure I made to work by watching tutorials lol. 
	//PRAISE THE SUN!!!
	
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_) 
    {	
		if(!ConfigUA.miniStructureGeneration || position.getY() > 248) {
			return false;
		}
	
		//makes sure this shrine does not spawn too close to world height border or it will get cut off.
		//Also makes sure it generates with land around it instead of cutting into cliffs or hanging over an edge by checking if block at north, east, west, and south are acceptable terrain blocks that appear only at top of land.
		for(int x = -4; x <= 4; x++) 
		{
			for(int z = -4; z <= 4; z++)  
			{
				if( Math.abs(x*z) > 2 && Math.abs(x*z) < 8 && !acceptableBlocks.contains(worldIn.getBlockState(position.down(1).west(x).north(z)))) 
				{
					return false;
				}
			}
		}
		
		//UltraAmplified.LOGGER.debug("Sun Shrine | " + position.getX() + " "+position.getZ());
		
		TemplateManager templatemanager = ((ServerWorld)worldIn.getWorld()).getSaveHandler().getStructureTemplateManager();
		Template template = templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":sunshrine"));
		
		if(template == null)
		{
			UltraAmplified.LOGGER.warn("sunshrine NTB does not exist!");
			return false;
		}
		
		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos) null);
		
		template.addBlocksToWorld(worldIn, position.down().north(3).west(3), placementsettings);
		
		return true;
		
	}

}

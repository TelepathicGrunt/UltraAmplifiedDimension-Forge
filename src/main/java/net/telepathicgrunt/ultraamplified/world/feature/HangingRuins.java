package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;
import com.telepathicgrunt.ultraamplified.UltraAmplified;

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

public class HangingRuins extends Feature<NoFeatureConfig> {

	public HangingRuins(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	
	
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_) 
    {	
		//makes sure this ruins does not spawn too close to world height border.
		if(!ConfigUA.miniStructureGeneration) {
			return false;
		}
	
		//makes sure there is enough solid blocks on ledge to hold this feature.
		for(int x = -5; x <= 5; x++) 
		{
			for(int z = -5; z <= 5; z++)  
			{
				if( Math.abs(x*z) > 9 && Math.abs(x*z) < 20 && !worldIn.getBlockState(position.up(1).west(x).north(z)).isSolid()) 
				{
					return false;
				}
			}
		}
		
		UltraAmplified.LOGGER.debug("Hanging Ruins | " + position.getX() + " "+position.getZ());
		
		TemplateManager templatemanager = ((ServerWorld)worldIn.getWorld()).getSaveHandler().getStructureTemplateManager();
		Template template = templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":hanging_ruins"));
		
		if(template == null)
		{
			UltraAmplified.LOGGER.warn("hanging ruins NTB does not exist!");
			return false;
		}
		
		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
				.setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos) null);
		
		template.addBlocksToWorld(worldIn, position.down(8).north(4).west(4), placementsettings);
		
		return true;
		
	}

}

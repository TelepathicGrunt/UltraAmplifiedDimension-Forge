package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

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
import net.telepathicgrunt.ultraamplified.UltraAmplified;
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

		Rotation rot = Rotation.values()[rand.nextInt(Rotation.values().length)];
		BlockPos.Mutable offset = new BlockPos.Mutable();
	     
		//makes sure there is enough solid blocks on ledge to hold this feature.
		for(int x = -5; x <= 5; x++) 
		{
			for(int z = -5; z <= 5; z++)  
			{
				if( Math.abs(x*z) > 9 && Math.abs(x*z) < 20) 
				{
					//match rotation of structure as it rotates around 0, 0 I think.
					//The -4 is to make the check rotate the same way as structure and 
					//then we do +4 to get the actual position again.
					offset.setPos(x-4, 0, z-4).setPos(offset.rotate(rot));
					if(!worldIn.getBlockState(position.up(1).west(offset.getX()+4).north(offset.getZ()+4)).isSolid()) {
						return false;
					}
				}
			}
		}
		
		//if we are on a 1 block thick ledge at any point, move down one so ruins ceiling isn't exposed 
		for(int x = -5; x <= 5; x++) 
		{
			for(int z = -5; z <= 5; z++)  
			{
				if(!worldIn.getBlockState(position.up(2)).isSolid()) {
					position = position.down();
					z = 6;
					x = 6;
					break;
				}
			}
		}
		
		
		//UltraAmplified.LOGGER.debug("Hanging Ruins | " + position.getX() + " " + position.getY() + " "+position.getZ());
		
		TemplateManager templatemanager = ((ServerWorld)worldIn.getWorld()).getSaveHandler().getStructureTemplateManager();
		Template template = templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID+":hanging_ruins"));
		
		if(template == null)
		{
			UltraAmplified.LOGGER.warn("hanging ruins NTB does not exist!");
			return false;
		}
		
		PlacementSettings placementsettings = (
				new PlacementSettings())
				.setMirror(Mirror.NONE)
				.setRotation(rot)
				.setIgnoreEntities(false)
				.setChunk((ChunkPos)null);
		
		template.addBlocksToWorld(worldIn, position.down(8).north(4).west(4), placementsettings);
		
		return true;
		
	}

}

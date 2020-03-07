package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.util.Direction;
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


public class HangingRuins extends Feature<NoFeatureConfig>
{

	public HangingRuins(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory)
	{
		super(configFactory);
	}


	@Override
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_)
	{
		//makes sure this ruins does not spawn too close to world height border.
		if (!ConfigUA.miniStructureGeneration || position.getY() < ConfigUA.seaLevel + 5)
		{
			return false;
		}

		Rotation rot = Rotation.values()[rand.nextInt(Rotation.values().length)];
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);
		BlockPos.Mutable offset = new BlockPos.Mutable();

		//makes sure there is enough solid blocks on ledge to hold this feature.
		for (int x = -5; x <= 5; x++)
		{
			for (int z = -5; z <= 5; z++)
			{
				if (Math.abs(x * z) > 9 && Math.abs(x * z) < 20)
				{
					//match rotation of structure as it rotates around 0, 0 I think.
					//The -4 is to make the check rotate the same way as structure and 
					//then we do +4 to get the actual position again.
					offset.setPos(x - 4, 0, z - 4).setPos(offset.rotate(rot));
					if (!world.getBlockState(blockpos$Mutable.add(-offset.getX() + 4, 1, -offset.getZ() + 4)).isSolid())
					{
						return false;
					}
				}
			}
		}

		//makes sure top won't be exposed to air
		if (shouldMoveDownOne(world, blockpos$Mutable, offset, rot))
		{
			blockpos$Mutable.move(Direction.DOWN);
		}

		//UltraAmplified.LOGGER.debug("Hanging Ruins | " + position.getX() + " " + position.getY() + " "+position.getZ());
		TemplateManager templatemanager = ((ServerWorld) world.getWorld()).getSaveHandler().getStructureTemplateManager();
		Template template = templatemanager.getTemplate(new ResourceLocation(UltraAmplified.MODID + ":hanging_ruins"));

		if (template == null)
		{
			UltraAmplified.LOGGER.warn("hanging ruins NTB does not exist!");
			return false;
		}

		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE).setRotation(rot).setIgnoreEntities(false).setChunk((ChunkPos) null);

		template.addBlocksToWorld(world, blockpos$Mutable.move(4, -8, 4), placementsettings, 2);

		return true;

	}


	private boolean shouldMoveDownOne(IWorld world, BlockPos.Mutable blockpos$Mutable, BlockPos.Mutable offset, Rotation rot)
	{

		//if we are on a 1 block thick ledge at any point, move down one so ruins ceiling isn't exposed 
		for (int x = -5; x <= 5; x++)
		{
			for (int z = -5; z <= 5; z++)
			{
				offset.setPos(x - 4, 0, z - 4).setPos(offset.rotate(rot));
				if (Math.abs(x * z) < 20 && !world.getBlockState(blockpos$Mutable.add(-offset.getX() + 4, 2, -offset.getZ() + 4)).isSolid())
				{
					//world.setBlockState(blockpos$Mutable.add(-offset.getX() + 4, 2, -offset.getZ() + 4), Blocks.REDSTONE_BLOCK.getDefaultState(), 2);
					return true;
				}
			}
		}
		return false;
	}
}

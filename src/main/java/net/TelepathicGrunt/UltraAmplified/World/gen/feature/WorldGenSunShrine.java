package net.telepathicgrunt.ultraamplified.World.gen.feature;

import java.util.ArrayList;
import java.util.Random;

import net.telepathicgrunt.ultraamplified.UltraAmplified;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class WorldGenSunShrine extends WorldGenerator{

	private static final ArrayList<IBlockState> acceptableBlocks = new ArrayList<IBlockState>();
	
	//first NTB structure I made to work by watching tutorials lol. 
	//PRAISE THE SUN!!!
	public WorldGenSunShrine()
    {
       super(false);
       acceptableBlocks.add(Blocks.DIRT.getDefaultState());
       acceptableBlocks.add(Blocks.GRASS.getDefaultState());
       acceptableBlocks.add(Blocks.GRASS.getDefaultState().withProperty(BlockGrass.SNOWY, true));
       acceptableBlocks.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL));
       acceptableBlocks.add(Blocks.DIRT.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT));
       acceptableBlocks.add(Blocks.SAND.getDefaultState());
    }
	
	@Override
	public boolean generate(World worldIn, Random rand, BlockPos position) {
		
		//makes sure this shrine does not spawn too close to world height border or it will get cut off.
		//Also makes sure it generates with land around it instead of cutting into cliffs or hanging over an edge by checking if block at north, east, west, and south are acceptable terrain blocks that appear only at top of land.
		if(position.getY() < 248 && acceptableBlocks.contains(worldIn.getBlockState(position.down().west(4))) && acceptableBlocks.contains(worldIn.getBlockState(position.down().north(4))) && acceptableBlocks.contains(worldIn.getBlockState(position.down().east(4))) && acceptableBlocks.contains(worldIn.getBlockState(position.down().south(4)))) 
		{
			//System.out.println("Sun Shrine | " + position.getX() + " "+position.getZ());
			
			WorldServer worldserver = (WorldServer) worldIn;
			MinecraftServer minecraftserver = worldIn.getMinecraftServer();
			TemplateManager templatemanager = worldserver.getStructureTemplateManager();
			Template template = templatemanager.getTemplate(minecraftserver, new ResourceLocation(UltraAmplified.MOD_ID+":sunshrine"));
			
			if(template == null)
			{
				UltraAmplified.logger.warn("sunshrine NTB does not exist!");
				return false;
			}
			
			IBlockState iblockstate = worldIn.getBlockState(position);
			worldIn.notifyBlockUpdate(position, iblockstate, iblockstate, 3);
			
			PlacementSettings placementsettings = (new PlacementSettings()).setMirror(Mirror.NONE)
					.setRotation(Rotation.NONE).setIgnoreEntities(false).setChunk((ChunkPos) null)
					.setReplacedBlock((Block) null).setIgnoreStructureBlock(false);
			
			template.getDataBlocks(position, placementsettings);
			template.addBlocksToWorld(worldIn, position.down().north(3).west(3), placementsettings);
		}
		return false;
	}

}

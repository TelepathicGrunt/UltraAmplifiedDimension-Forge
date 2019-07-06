package net.TelepathicGrunt.UltraAmplified.World.gen.feature;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityLockableLoot;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.IChunkGenSettings;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.storage.loot.LootTableList;

public class MarkedTreasureChest extends Feature<NoFeatureConfig> {

	private static final IBlockState RED_SAND = Blocks.RED_SAND.getDefaultState();

	public boolean func_212245_a(IWorld worldIn, IChunkGenerator<? extends IChunkGenSettings> chunkSettings, Random random, BlockPos blockpos, NoFeatureConfig config) {

		//surface block must be solid with water above
		if(!worldIn.getBlockState(blockpos).isSolid() || 
			worldIn.getBlockState(blockpos.up()).getFluidState().isEmpty()) 
		{
			return false;
		}
		
		//chest position must be surrounded by solid blocks
		for (EnumFacing face : EnumFacing.values()){
			//skip above block as we already checked it
			if(face == EnumFacing.UP) {
				continue;
			}
			
			if(!worldIn.getBlockState(blockpos.down().offset(face)).isSolid()) {
				return false;
			}
		}
		
		
		//if we reached here, then the placement is good for generation.
		
		//creates the x marker
		int size = 5;
		for(int x = -size; x <= size; x++) {
			for(int z = -size; z <= size; z++) {
				int absx = Math.abs(x);
				int absz = Math.abs(z);
				
				//doesn't place red sand on corners
				if(absx == size && absz == size) {
					continue;
				}
				
				//creates a thick x shape
				if(random.nextFloat() < 0.85 && Math.abs(absx-absz) < 2) {
					worldIn.setBlockState(blockpos.add(x, 0, z), RED_SAND, 2);
				}
			}
		}
		
		//places chest with a 50/50 split between treasure chest and end city loot
		worldIn.setBlockState(blockpos.down(), StructurePiece.func_197528_a(worldIn, blockpos.down(), Blocks.CHEST.getDefaultState()), 2);
		if(random.nextFloat() < 0.5f) {
			TileEntityLockableLoot.setLootTable(worldIn, random, blockpos.down(), LootTableList.CHESTS_BURIED_TREASURE);
		}
		else {
			TileEntityLockableLoot.setLootTable(worldIn, random, blockpos.down(), LootTableList.CHESTS_END_CITY_TREASURE);
		}

		return true;
	}
}
package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.function.Function;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.storage.loot.LootTables;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class MarkedTreasureChest extends Feature<NoFeatureConfig> {

	public MarkedTreasureChest(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	private static final BlockState RED_SAND = Blocks.RED_SAND.getDefaultState();

	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> chunkSettings, Random random, BlockPos blockpos, NoFeatureConfig config) {

		if(!ConfigUA.chestGeneration) {
			return false;
		}
		
		
		//surface block must be solid with water above
		if(!worldIn.getBlockState(blockpos).isSolid() || 
			worldIn.getBlockState(blockpos.up()).getFluidState().isEmpty()) 
		{
			return false;
		}
		
		//chest position must be surrounded by solid blocks
		for (Direction face : Direction.values()){
			//skip above block as we already checked it
			if(face == Direction.UP) {
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
			LockableLootTileEntity.setLootTable(worldIn, random, blockpos.down(), LootTables.CHESTS_BURIED_TREASURE);
		}
		else {
			LockableLootTileEntity.setLootTable(worldIn, random, blockpos.down(), LootTables.CHESTS_END_CITY_TREASURE);
		}
        //UltraAmplified.Logger.log(Level.DEBUG, "Marked Treasure Chest "+" | "+blockpos.getX()+" "+blockpos.getZ());

		return true;
	}
}
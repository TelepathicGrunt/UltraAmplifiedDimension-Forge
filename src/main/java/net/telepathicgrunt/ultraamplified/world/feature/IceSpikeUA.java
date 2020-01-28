package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class IceSpikeUA extends Feature<NoFeatureConfig> {
	
	public IceSpikeUA(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactory) {
		super(configFactory);
	}

	protected static final Set<BlockState> ALLOWED_BLOCKS = 
			ImmutableSet.of(
	    		Blocks.AIR.getDefaultState(),
	    		Blocks.CAVE_AIR.getDefaultState(),
	    		Blocks.ICE.getDefaultState(),
	    		Blocks.PACKED_ICE.getDefaultState(),
	    		Blocks.SNOW.getDefaultState(),
	    		Blocks.SNOW_BLOCK.getDefaultState(),
				Blocks.DIRT.getDefaultState(),
				Blocks.COARSE_DIRT.getDefaultState(),
				Blocks.WATER.getDefaultState()
    		);
    
    private static final BlockState WATER = Blocks.WATER.getDefaultState();
    private static final BlockState ICE = Blocks.ICE.getDefaultState();
    private static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
	
	//ice spike code was changed to only generate taller ice spikes and to have spikes go all the way to Y = 5 if path is clear.
	public boolean place(IWorld world, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_) {
		
		//System.out.println(position.getX()+", "+position.getY()+", "+position.getZ());
		
		BlockPos.Mutable blockpos$Mutable = new BlockPos.Mutable(position);
		BlockPos.Mutable blockpos$Mutable2 = new BlockPos.Mutable(position);
        while ((world.isAirBlock(blockpos$Mutable) || world.getBlockState(blockpos$Mutable) == Blocks.WATER.getDefaultState())&& blockpos$Mutable.getY() > 2)
        {
        	blockpos$Mutable.move(Direction.DOWN);
        }

        if (world.getBlockState(blockpos$Mutable).getBlock() != Blocks.SNOW_BLOCK)
        {
            return false;
        }
        else
        {
        	blockpos$Mutable.move(Direction.UP, rand.nextInt(4));
            int i = rand.nextInt(4) + 7;
            int j = i / 4 + rand.nextInt(2);

            if (j > 1 && rand.nextInt(40) == 0)
            {
            	//if ice spike has the potential to generate over 245, then set the position to 245
            	if(blockpos$Mutable.getY()+130 > 245) {
            		blockpos$Mutable.move(Direction.UP, (245 - blockpos$Mutable.getY()));
            	}
            	else {
            		blockpos$Mutable.move(Direction.UP, 30 + rand.nextInt(100));
            	}
                
            }

            for (int y = 0; y < i; ++y)
            {
                float f = (1.0F - (float)y / (float)i) * (float)j;
                int l = MathHelper.ceil(f);

                for (int x = -l; x <= l; ++x)
                {
                    float f1 = (float)MathHelper.abs(x) - 0.25F;

                    for (int z = -l; z <= l; ++z)
                    {
                        float f2 = (float)MathHelper.abs(z) - 0.25F;

                        if ((x == 0 && z == 0 || f1 * f1 + f2 * f2 <= f * f) && (x != -l && x != l && z != -l && z != l || rand.nextFloat() <= 0.75F))
                        {
                        	blockpos$Mutable2.setPos(blockpos$Mutable).move(x, y, z);
                            BlockState iblockstate = world.getBlockState(blockpos$Mutable2);
                            if (ALLOWED_BLOCKS.contains(iblockstate) && blockpos$Mutable2.getY() > ConfigUA.seaLevel-2)
                            {
                                this.setBlockState(world, blockpos$Mutable2, PACKED_ICE);
                            }
                            else if(iblockstate == WATER) {
                            	this.setBlockState(world, blockpos$Mutable2, ICE);
                            }

                            if (y != 0 && l > 1)
                            {
                            	blockpos$Mutable2.setPos(blockpos$Mutable).move(x, -y, z);
                                iblockstate = world.getBlockState(blockpos$Mutable2);

                                if (ALLOWED_BLOCKS.contains(iblockstate) && blockpos$Mutable2.getY() > ConfigUA.seaLevel-2)
                                {
                                    this.setBlockState(world, blockpos$Mutable2, PACKED_ICE);
                                }
                                else if(iblockstate == WATER) {
                                	this.setBlockState(world, blockpos$Mutable2, ICE);
                                }
                            }
                        }
                    }
                }
            }

            int k1 = j - 1;

            if (k1 < 0)
            {
                k1 = 0;
            }
            else if (k1 > 1)
            {
                k1 = 1;
            }

        	blockpos$Mutable.setPos(position);
            for (int x = -k1; x <= k1; ++x)
            {
                for (int z = -k1; z <= k1; ++z)
                {
                	blockpos$Mutable2.setPos(blockpos$Mutable).move(x, -1, z);
                    int j2 = 50;

                    if (Math.abs(x) == 1 && Math.abs(z) == 1)
                    {
                        j2 = rand.nextInt(5);
                    }

                    //how far down the ice spike can generate
                    while (blockpos$Mutable2.getY() > 5)
                    {
                        BlockState iblockstate1 = world.getBlockState(blockpos$Mutable2);

                        if (!ALLOWED_BLOCKS.contains(iblockstate1))
                        {
                            break;
                        }

                        if(iblockstate1 == WATER || iblockstate1 == ICE) {
                        	this.setBlockState(world, blockpos$Mutable2, Blocks.ICE.getDefaultState());
                        }else {
                            this.setBlockState(world, blockpos$Mutable2, Blocks.PACKED_ICE.getDefaultState());
                        }
                        blockpos$Mutable2.move(Direction.DOWN);
                        --j2;

                        if (j2 <= 0)
                        {
                            blockpos$Mutable2.move(Direction.DOWN, rand.nextInt(5) + 1);
                            j2 = rand.nextInt(5);
                        }
                    }
                }
            }

            return true;
        }
    }
}

package net.telepathicgrunt.ultraamplified.world.feature;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.mojang.datafixers.Dynamic;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.telepathicgrunt.ultraamplified.config.ConfigUA;

public class IceSpike extends Feature<NoFeatureConfig> {
	
	public IceSpike(Function<Dynamic<?>, ? extends NoFeatureConfig> configFactoryIn) {
		super(configFactoryIn);
	}

	protected static final Set<BlockState> ALLOWED_BLOCKS = 
    		Stream.of(
	    		Blocks.AIR.getDefaultState(),
	    		Blocks.CAVE_AIR.getDefaultState(),
	    		Blocks.ICE.getDefaultState(),
	    		Blocks.PACKED_ICE.getDefaultState(),
	    		Blocks.SNOW.getDefaultState(),
	    		Blocks.SNOW_BLOCK.getDefaultState(),
				Blocks.DIRT.getDefaultState(),
				Blocks.COARSE_DIRT.getDefaultState(),
				Blocks.WATER.getDefaultState()
    		).collect(Collectors.toCollection(HashSet::new));
    
    private static final BlockState WATER = Blocks.WATER.getDefaultState();
    private static final BlockState ICE = Blocks.ICE.getDefaultState();
    private static final BlockState PACKED_ICE = Blocks.PACKED_ICE.getDefaultState();
	
	//ice spike code was changed to only generate taller ice spikes and to have spikes go all the way to Y = 5 if path is clear.
	public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> changedBlock, Random rand, BlockPos position, NoFeatureConfig p_212245_5_) {
		//System.out.println(position.getX()+", "+position.getY()+", "+position.getZ());
        while ((worldIn.isAirBlock(position) || worldIn.getBlockState(position) == Blocks.WATER.getDefaultState())&& position.getY() > 2)
        {
            position = position.down();
        }

        if (worldIn.getBlockState(position).getBlock() != Blocks.SNOW_BLOCK)
        {
            return false;
        }
        else
        {
            position = position.up(rand.nextInt(4));
            int i = rand.nextInt(4) + 7;
            int j = i / 4 + rand.nextInt(2);

            if (j > 1 && rand.nextInt(40) == 0)
            {
            	//if ice spike has the potential to generate over 245, then set the position to 245
            	if(position.getY()+130 > 245) {
            		position = position.up((245 - position.getY()));
            	}
            	else {
            		position = position.up(30 + rand.nextInt(100));
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
                            BlockState iblockstate = worldIn.getBlockState(position.add(x, y, z));
                            if (ALLOWED_BLOCKS.contains(iblockstate) && position.add(x, y, z).getY() > ConfigUA.seaLevel-2)
                            {
                                this.setBlockState(worldIn, position.add(x, y, z), PACKED_ICE);
                            }
                            else if(iblockstate == WATER) {
                            	this.setBlockState(worldIn, position.add(x, y, z), ICE);
                            }

                            if (y != 0 && l > 1)
                            {
                                iblockstate = worldIn.getBlockState(position.add(x, -y, z));

                                if (ALLOWED_BLOCKS.contains(iblockstate) && position.add(x, -y, z).getY() > ConfigUA.seaLevel-2)
                                {
                                    this.setBlockState(worldIn, position.add(x, -y, z), PACKED_ICE);
                                }
                                else if(iblockstate == WATER) {
                                	this.setBlockState(worldIn, position.add(x, -y, z), ICE);
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

            for (int x = -k1; x <= k1; ++x)
            {
                for (int z = -k1; z <= k1; ++z)
                {
                    BlockPos blockpos = position.add(x, -1, z);
                    int j2 = 50;

                    if (Math.abs(x) == 1 && Math.abs(z) == 1)
                    {
                        j2 = rand.nextInt(5);
                    }

                    //how far down the ice spike can generate
                    while (blockpos.getY() > 5)
                    {
                        BlockState iblockstate1 = worldIn.getBlockState(blockpos);

                        if (!ALLOWED_BLOCKS.contains(iblockstate1))
                        {
                            break;
                        }

                        if(iblockstate1 == WATER || iblockstate1 == ICE) {
                        	this.setBlockState(worldIn, blockpos, Blocks.ICE.getDefaultState());
                        }else {
                            this.setBlockState(worldIn, blockpos, Blocks.PACKED_ICE.getDefaultState());
                        }
                        blockpos = blockpos.down();
                        --j2;

                        if (j2 <= 0)
                        {
                            blockpos = blockpos.down(rand.nextInt(5) + 1);
                            j2 = rand.nextInt(5);
                        }
                    }
                }
            }

            return true;
        }
    }
}

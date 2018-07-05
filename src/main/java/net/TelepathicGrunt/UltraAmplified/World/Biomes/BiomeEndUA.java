package net.TelepathicGrunt.UltraAmplified.World.Biomes;

import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeDecoratorUA;
import net.TelepathicGrunt.UltraAmplified.World.Biome.BiomeExtendedUA;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.Biome;

public class BiomeEndUA extends BiomeExtendedUA
{

	protected static final IBlockState END_STONE = Blocks.END_STONE.getDefaultState();
	
    public BiomeEndUA(Biome.BiomeProperties properties)
    {
        super(properties);
        
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCaveCreatureList.clear();
        this.spawnableMonsterList.add(new Biome.SpawnListEntry(EntityEnderman.class, 10, 4, 4));
        this.topBlock = END_STONE;
        this.fillerBlock = END_STONE;
        this.decorator = new BiomeEndDecoratorUA();
    }

    @Override
    public int getModdedBiomeGrassColor(int original) {
    	return 7625106;
    }
    
    @Override
    public int getModdedBiomeFoliageColor(int original) {
    	return 5329028;
    }
    
    /**
     * takes temperature, returns color
     */
    public int getSkyColorByTemp(float currentTemperature)
    {
        return 0;
    }
}

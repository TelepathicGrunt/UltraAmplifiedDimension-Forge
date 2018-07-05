package net.TelepathicGrunt.UltraAmplified.World;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

public class WorldSettingsUA
{
    public BiomeSize biomeSize = BiomeSize.MEDIUM;
	
	public int seaLevel;
    public boolean useCaves;
    public boolean useDungeons;
    public int dungeonChance;
    public boolean useStrongholds;
    public boolean useVillages;
    public boolean useMineShafts;
    public boolean useTemples;
    public boolean useMonuments;
    public boolean useRavines;
    public boolean useWaterLakes;
    public int waterLakeChance;
    public boolean useLavaLakes;
    public int lavaLakeChance;
    public boolean useLavaOceans;
    public boolean useMansions;

    public float coordinateScale;
    public float heightScale;
    public float upperLimitScale;
    public float lowerLimitScale;
    public float mainNoiseScaleX;
    public float mainNoiseScaleY;
    public float mainNoiseScaleZ;

	
	public static Gson serializer = new GsonBuilder().create();
	
	
	public enum BiomeSize
	{
        SMALL (3),
        MEDIUM (4),
        LARGE (5);
  
        private final int value;
        BiomeSize(int value)
        {
            this.value = value;
        }
        public int getValue()
        {
            return this.value;
        }
	}
	  
	public WorldSettingsUA()
    {
        this.setDefault();
    }
    
    public WorldSettingsUA(String jsonString)
    {
        this.setDefault();
    }
    
    public void setDefault()
    {
    	this.biomeSize = BiomeSize.MEDIUM;
    	
    	this.seaLevel = 63;
        this.useCaves = true;
        this.useDungeons = true;
        this.dungeonChance = 8;
        this.useStrongholds = true;
        this.useVillages = true;
        this.useMineShafts = true;
        this.useTemples = true;
        this.useMonuments = true;
        this.useMansions = true;
        this.useRavines = true;
        this.useWaterLakes = true;
        this.waterLakeChance = 4;
        this.useLavaLakes = true;
        this.lavaLakeChance = 80;
        this.useLavaOceans = false;

        this.mainNoiseScaleX = 80.0F;
        this.mainNoiseScaleY = 160.0F;
        this.mainNoiseScaleZ = 80.0F;
        this.coordinateScale = 684.412F;
        this.heightScale = 684.412F;
        this.upperLimitScale = 512.0F;
        this.lowerLimitScale = 512.0F;

    }
}

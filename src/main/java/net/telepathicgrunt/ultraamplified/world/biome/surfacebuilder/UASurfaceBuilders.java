package net.telepathicgrunt.ultraamplified.world.biome.surfacebuilder;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.telepathicgrunt.ultraamplified.utils.RegUtil;


public class UASurfaceBuilders
{
	protected static final BlockState							SANDSTONE										= Blocks.SANDSTONE.getDefaultState();
	protected static final BlockState							WATER											= Blocks.WATER.getDefaultState();
	protected static final BlockState							SNOW_BLOCK										= Blocks.SNOW_BLOCK.getDefaultState();
	protected static final BlockState							ICE												= Blocks.ICE.getDefaultState();
	protected static final BlockState							SAND											= Blocks.SAND.getDefaultState();
	protected static final BlockState							DIRT											= Blocks.DIRT.getDefaultState();
	protected static final BlockState							GRAVEL											= Blocks.GRAVEL.getDefaultState();
	protected static final BlockState							STONE											= Blocks.STONE.getDefaultState();
	protected static final BlockState							GRASS_BLOCK										= Blocks.GRASS_BLOCK.getDefaultState();
	protected static final BlockState							COARSE_DIRT										= Blocks.COARSE_DIRT.getDefaultState();
	protected static final BlockState							RED_TERRACOTTA									= Blocks.RED_TERRACOTTA.getDefaultState();
	protected static final BlockState							WHITE_TERRACOTTA								= Blocks.WHITE_TERRACOTTA.getDefaultState();

	public static final SurfaceBuilderConfig					SAND_SAND_SANDSTONE_SURFACE						= new SurfaceBuilderConfig(SAND, SAND, SANDSTONE);
	public static final SurfaceBuilderConfig					SAND_SANDSTONE_SANDSTONE_SURFACE				= new SurfaceBuilderConfig(SAND, SANDSTONE, SANDSTONE);
	public static final SurfaceBuilderConfig					SANDSTONE_SURFACE								= new SurfaceBuilderConfig(SANDSTONE, SANDSTONE, SANDSTONE);
	public static final SurfaceBuilderConfig					THIN_WATER_SURFACE								= new SurfaceBuilderConfig(WATER, DIRT, GRAVEL);
	public static final SurfaceBuilderConfig					SNOWBLOCK_ICE_ICE_SURFACE						= new SurfaceBuilderConfig(SNOW_BLOCK, ICE, ICE);
	public static final SurfaceBuilderConfig					GRASS_GRAVEL_STONE_SURFACE						= new SurfaceBuilderConfig(GRASS_BLOCK, GRAVEL, STONE);
	public static final SurfaceBuilderConfig					ICE_GRAVEL_STONE_SURFACE						= new SurfaceBuilderConfig(ICE, GRAVEL, STONE);
	public static final SurfaceBuilderConfig					GRASS_GRAVEL_DEAD_CORAL_SURFACE					= new SurfaceBuilderConfig(GRASS_BLOCK, GRAVEL, Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState());
	public static final SurfaceBuilderConfig					GRASS_SAND_SANDSTONE_SURFACE					= new SurfaceBuilderConfig(GRASS_BLOCK, SAND, SANDSTONE);
	public static final SurfaceBuilderConfig					GRASS_SAND_DEAD_CORAL_SURFACE					= new SurfaceBuilderConfig(GRASS_BLOCK, SAND, Blocks.DEAD_HORN_CORAL_BLOCK.getDefaultState());
	public static final SurfaceBuilderConfig					COARSE_DIRT_COARSE_DIRT_GRAVEL_SURFACE			= new SurfaceBuilderConfig(COARSE_DIRT, COARSE_DIRT, GRAVEL);
	public static final SurfaceBuilderConfig					RED_TERRACOTTA_WHITE_TERRACOTTA_GRAVEL_SURFACE	= new SurfaceBuilderConfig(RED_TERRACOTTA, WHITE_TERRACOTTA, GRAVEL);

	public static final SurfaceBuilder<SurfaceBuilderConfig>	DESERT_LAKE_SURFACE_BUILDER						= new DesertLakesSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	BADLANDS_SURFACE_BUILDER_UA						= new BadlandsSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	MODIFIED_BADLANDS_SURFACE_BUILDER_UA			= new ModifiedBadlandsSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	ERODED_BADLANDS									= new MesaBryceSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	ICED_TERRAIN_SURFACE_BUILDER					= new IcedTerrainSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	NETHER_SURFACE_BUILDER_UA						= new NetherSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	END_SURFACE_BUILDER_UA							= new EndSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	OCEAN_SURFACE_BUILDER_UA						= new OceanSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	DEEP_OCEAN_SURFACE_BUILDER_UA					= new DeepOceanSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	SAND_SURFACE_BUILDER							= new SandSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	GRAVEL_SURFACE_BUILDER							= new GravelSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	EXTREME_HILLS_MUTATED_SURFACE_BUILDER_UA		= new ExtremeHillsMutatedSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	PLATEAU_SURFACE_BUILDER							= new PlateauSurfaceBuilder(SurfaceBuilderConfig::deserialize);
	public static final SurfaceBuilder<SurfaceBuilderConfig>	SHATTERED_SAVANNA_SURFACE_BUILDER_UA			= new ShatteredSavannaSurfaceBuilderUA(SurfaceBuilderConfig::deserialize);


	public static void registerSurfaceBuilders(RegistryEvent.Register<SurfaceBuilder<?>> event)
	{
		IForgeRegistry<SurfaceBuilder<?>> registry = event.getRegistry();

		RegUtil.register(registry, DESERT_LAKE_SURFACE_BUILDER, "desert_lake_surface_builder");
		RegUtil.register(registry, BADLANDS_SURFACE_BUILDER_UA, "badlands_surface_builder");
		RegUtil.register(registry, MODIFIED_BADLANDS_SURFACE_BUILDER_UA, "modified_badlands_surface_builder");
		RegUtil.register(registry, ERODED_BADLANDS, "eroded_badlands");
		RegUtil.register(registry, ICED_TERRAIN_SURFACE_BUILDER, "iced_terrain_surface_builder");
		RegUtil.register(registry, NETHER_SURFACE_BUILDER_UA, "nether_surface_builder");
		RegUtil.register(registry, END_SURFACE_BUILDER_UA, "end_surface_builder");
		RegUtil.register(registry, OCEAN_SURFACE_BUILDER_UA, "ocean_surface_builder");
		RegUtil.register(registry, DEEP_OCEAN_SURFACE_BUILDER_UA, "deep_ocean_surface_builder");
		RegUtil.register(registry, SAND_SURFACE_BUILDER, "sand_surface_builder");
		RegUtil.register(registry, GRAVEL_SURFACE_BUILDER, "gravel_surface_builder");
		RegUtil.register(registry, EXTREME_HILLS_MUTATED_SURFACE_BUILDER_UA, "extreme_hills_mutated_surface_builder");
		RegUtil.register(registry, PLATEAU_SURFACE_BUILDER, "plateau_surface_builder");
		RegUtil.register(registry, SHATTERED_SAVANNA_SURFACE_BUILDER_UA, "shattered_savanna_surface_builder");

	}
}

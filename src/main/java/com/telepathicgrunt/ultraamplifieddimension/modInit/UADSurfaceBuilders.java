package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.world.surfacebuilder.*;
import com.telepathicgrunt.ultraamplifieddimension.world.surfacebuilder.configs.QuadrarySurfaceBuilderConfig;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;


public class UADSurfaceBuilders
{
	public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, UltraAmplifiedDimension.MODID);

	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> BADLANDS_SURFACE_BUILDER = SURFACE_BUILDERS.register("badlands_surface_builder", () -> new BadlandsSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> DEEP_OCEAN_SURFACE_BUILDER = SURFACE_BUILDERS.register("deep_ocean_surface_builder", () -> new DeepOceanSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> DESERT_LAKE_SURFACE_BUILDER = SURFACE_BUILDERS.register("desert_lake_surface_builder", () -> new DesertLakesSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> DISSECTED_BADLANDS_SURFACE_BUILDER = SURFACE_BUILDERS.register("dissected_badlands_surface_builder", () -> new DissectedBadlandsSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> SPIKY_BADLANDS = SURFACE_BUILDERS.register("spiky_badlands_surface_builder", () -> new SpikyBadlandsSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> ICED_TERRAIN_SURFACE_BUILDER = SURFACE_BUILDERS.register("iced_terrain_surface_builder", () -> new IcedTerrainSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> NETHER_WASTES_SURFACE_BUILDER = SURFACE_BUILDERS.register("nether_surface_builder", () -> new NetherwastesSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> GRASSY_END_SURFACE_BUILDER = SURFACE_BUILDERS.register("grassy_end_surface_builder", () -> new GrassyEndSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> OCEAN_SURFACE_BUILDER = SURFACE_BUILDERS.register("ocean_surface_builder", () -> new OceanSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> SAND_SURFACE_BUILDER = SURFACE_BUILDERS.register("sand_surface_builder", () -> new SandSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> GRAVEL_SURFACE_BUILDER = SURFACE_BUILDERS.register("gravel_surface_builder", () -> new GravelSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> MOUNTAINS_MUTATED_SURFACE_BUILDER = SURFACE_BUILDERS.register("mountains_mutated_surface_builder", () -> new MountainsMutatedSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<QuadrarySurfaceBuilderConfig>> PLATEAU_SURFACE_BUILDER = SURFACE_BUILDERS.register("plateau_surface_builder", () -> new PlateauSurfaceBuilder(QuadrarySurfaceBuilderConfig.CODEC));
	public static final RegistryObject<SurfaceBuilder<QuadrarySurfaceBuilderConfig>> SHATTERED_SAVANNA_SURFACE_BUILDER = SURFACE_BUILDERS.register("shattered_savanna_surface_builder", () -> new ShatteredSavannaSurfaceBuilder(QuadrarySurfaceBuilderConfig.CODEC));

	public static final SurfaceBuilderConfig SAND_SANDSTONE_SANDSTONE_SURFACE = new SurfaceBuilderConfig(Blocks.SAND.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState());
	public static final QuadrarySurfaceBuilderConfig COARSE_DIRT_COARSE_DIRT_GRAVEL_DIRT_SURFACE = new QuadrarySurfaceBuilderConfig(Blocks.COARSE_DIRT.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.DIRT.getDefaultState());
	public static final QuadrarySurfaceBuilderConfig GRASS_BLOCK_DIRT_GRAVEL_COARSE_DIRT_SURFACE = new QuadrarySurfaceBuilderConfig(Blocks.GRASS_BLOCK.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState());
}

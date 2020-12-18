package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.world.surfacebuilder.*;
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

	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> BADLANDS_SURFACE_BUILDER = createDecorator("badlands_surface_builder", () -> new BadlandsSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> DEEP_OCEAN_SURFACE_BUILDER = createDecorator("deep_ocean_surface_builder", () -> new DeepOceanSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> DESERT_LAKE_SURFACE_BUILDER = createDecorator("desert_lake_surface_builder", () -> new DesertLakesSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> MODIFIED_BADLANDS_SURFACE_BUILDER = createDecorator("modified_badlands_surface_builder", () -> new ModifiedBadlandsSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> ERODED_BADLANDS = createDecorator("eroded_badlands", () -> new ErodedBadlandsSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> ICED_TERRAIN_SURFACE_BUILDER = createDecorator("iced_terrain_surface_builder", () -> new IcedTerrainSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> NETHER_WASTES_SURFACE_BUILDER = createDecorator("nether_wastes_surface_builder", () -> new NetherwastesSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> END_SURFACE_BUILDER = createDecorator("end_surface_builder", () -> new EndSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> OCEAN_SURFACE_BUILDER = createDecorator("ocean_surface_builder", () -> new OceanSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> SAND_SURFACE_BUILDER = createDecorator("sand_surface_builder", () -> new SandSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> GRAVEL_SURFACE_BUILDER = createDecorator("gravel_surface_builder", () -> new GravelSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> MOUNTAINS_MUTATED_SURFACE_BUILDER = createDecorator("mountains_mutated_surface_builder", () -> new MountainsMutatedSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> PLATEAU_SURFACE_BUILDER = createDecorator("plateau_surface_builder", () -> new PlateauSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));
	public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> SHATTERED_SAVANNA_SURFACE_BUILDER = createDecorator("shattered_savanna_surface_builder", () -> new ShatteredSavannaSurfaceBuilder(SurfaceBuilderConfig.field_237203_a_));

	public static <D extends SurfaceBuilder<?>> RegistryObject<D> createDecorator(String name, Supplier<? extends D> surfacebuilder) {
		return SURFACE_BUILDERS.register(name, surfacebuilder);
	}

	public static final SurfaceBuilderConfig SAND_SANDSTONE_SANDSTONE_SURFACE = new SurfaceBuilderConfig(Blocks.SAND.getDefaultState(), Blocks.SANDSTONE.getDefaultState(), Blocks.SANDSTONE.getDefaultState());
	public static final SurfaceBuilderConfig COARSE_DIRT_COARSE_DIRT_GRAVEL_SURFACE = new SurfaceBuilderConfig(Blocks.COARSE_DIRT.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState());
}

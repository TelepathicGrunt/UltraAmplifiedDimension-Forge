package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.world.carver.*;
import com.telepathicgrunt.ultraamplifieddimension.world.carver.configs.CaveConfig;
import com.telepathicgrunt.ultraamplifieddimension.world.carver.configs.RavineConfig;
import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;


public class UADCarvers
{
	public static final DeferredRegister<WorldCarver<?>> WORLD_CARVERS = DeferredRegister.create(ForgeRegistries.WORLD_CARVERS, UltraAmplifiedDimension.MODID);

	public static final RegistryObject<WorldCarver<?>> RAVINE_CARVER = createCarver("ravine", () -> new RavineCarver(RavineConfig.CODEC));
	public static final RegistryObject<WorldCarver<?>> LONG_RAVINE_CARVER = createCarver("long_ravine", () -> new SuperLongRavineCarver(RavineConfig.CODEC));
	public static final RegistryObject<WorldCarver<?>> CAVE_CAVITY_CARVER = createCarver("cave_cavity", () -> new CaveCavityCarver(CaveConfig.CODEC));
	public static final RegistryObject<WorldCarver<?>> UNDERWATER_CAVE_CARVER = createCarver("underwater_cave", () -> new UnderwaterCaveCarver(ProbabilityConfig.CODEC));

	public static <B extends WorldCarver<?>> RegistryObject<B> createCarver(String name, Supplier<B> carver) {
		return WORLD_CARVERS.register(name, carver);
	}
}

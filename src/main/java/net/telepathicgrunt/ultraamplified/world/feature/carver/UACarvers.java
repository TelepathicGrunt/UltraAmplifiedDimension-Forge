package net.telepathicgrunt.ultraamplified.world.feature.carver;

import net.minecraft.world.gen.carver.WorldCarver;
import net.minecraft.world.gen.feature.ProbabilityConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.telepathicgrunt.ultraamplified.RegUtil;

public class UACarvers
{
	public static final WorldCarver<ProbabilityConfig> RAVINE_CARVER = new RavineCarver(ProbabilityConfig::deserialize, 70);
	public static final WorldCarver<ProbabilityConfig> LONG_RAVINE_CARVER = new SuperLongRavineCarver(ProbabilityConfig::deserialize, 50);
	public static final WorldCarver<ProbabilityConfig> CAVE_CAVITY_CARVER = new CaveCavityCarver(ProbabilityConfig::deserialize, 70);
	public static final WorldCarver<ProbabilityConfig> UNDERWATER_CAVE_CARVER = new UnderwaterCaveCarver(ProbabilityConfig::deserialize);
	

	public static void registerCarvers(RegistryEvent.Register<WorldCarver<?>> event)
	{
		IForgeRegistry<WorldCarver<?>> registry = event.getRegistry();
		RegUtil.register(registry, RAVINE_CARVER, "ravine_ua");
		RegUtil.register(registry, LONG_RAVINE_CARVER, "long_ravine_ua");
		RegUtil.register(registry, CAVE_CAVITY_CARVER, "cave_cavity_ua");
		RegUtil.register(registry, UNDERWATER_CAVE_CARVER, "underwater_cave_ua");
	}
}

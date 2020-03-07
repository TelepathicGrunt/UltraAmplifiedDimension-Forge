package net.telepathicgrunt.ultraamplified.world.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.placement.IPlacementConfig;


public class LapisCountRangeConfig implements IPlacementConfig
{
	public final float countModifier;
	public final int baseline;
	public final int spread;
	public final boolean sealevelBased;


	public LapisCountRangeConfig(float countModifier, int baseline, int spread, boolean sealevelBasedIn)
	{
		this.countModifier = countModifier;
		this.baseline = baseline;
		this.spread = spread;
		this.sealevelBased = sealevelBasedIn;
	}


	//cannot fit boolean in
	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops)
	{
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("count"), ops.createFloat(this.countModifier), ops.createString("baseline"), ops.createInt(this.baseline), ops.createString("spread"), ops.createInt(this.spread), ops.createString("sealevelbased"), ops.createBoolean(sealevelBased))));
	}


	public static LapisCountRangeConfig deserialize(Dynamic<?> p_214733_0_)
	{
		float count = p_214733_0_.get("count").asFloat(0);
		int baseline = p_214733_0_.get("baseline").asInt(0);
		int spread = p_214733_0_.get("spread").asInt(0);
		boolean sealevelBased = p_214733_0_.get("sealevelbased").asBoolean(false);
		return new LapisCountRangeConfig(count, baseline, spread, sealevelBased);
	}
}
package net.telepathicgrunt.ultraamplified.world.feature.config;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;

import net.minecraft.world.gen.placement.IPlacementConfig;


public class CountRangeAndTypeConfig implements IPlacementConfig
{
	public final float countModifier;
	public final int bottomOffset;
	public final int topOffset;
	public final int maximum;
	public final boolean sealevelBased;
	public final Type type;


	public CountRangeAndTypeConfig(float countModifier, int bottomOffset, int topOffset, int maximum, boolean sealevelBasedIn, Type typeIn)
	{
		this.countModifier = countModifier;
		this.bottomOffset = bottomOffset;
		this.topOffset = topOffset;
		this.maximum = maximum;
		this.sealevelBased = sealevelBasedIn;
		this.type = typeIn;
	}

	public static enum Type
	{
		GLOWSTONE_VARIANT_PATCH, GLOWSTONE, MAGMA, QUARTZ, EMERALD, SILVERFISH, COAL, IRON, GOLD, REDSTONE, DIAMOND, NOCONFIG;
	}


	//cannot fit boolean in
	@Override
	public <T> Dynamic<T> serialize(DynamicOps<T> ops)
	{
		return new Dynamic<>(ops, ops.createMap(ImmutableMap.of(ops.createString("count"), ops.createFloat(this.countModifier), ops.createString("bottom_offset"), ops.createInt(this.bottomOffset), ops.createString("top_offset"), ops.createInt(this.topOffset), ops.createString("maximum"), ops.createInt(this.maximum), ops.createString("type"), ops.createString(this.type.name()))));
	}


	public static CountRangeAndTypeConfig deserialize(Dynamic<?> ops)
	{
		float count = ops.get("count").asFloat(0);
		int bottom_offset = ops.get("bottom_offset").asInt(0);
		int top_offset = ops.get("top_offset").asInt(0);
		int maximum = ops.get("maximum").asInt(0);
		Type type = Type.valueOf(ops.get("type").asString("COAL"));
		return new CountRangeAndTypeConfig(count, bottom_offset, top_offset, maximum, false, type);
	}
}
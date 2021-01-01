package com.telepathicgrunt.ultraamplifieddimension.modInit;

import com.telepathicgrunt.ultraamplifieddimension.UltraAmplifiedDimension;
import com.telepathicgrunt.ultraamplifieddimension.world.features.treedecorators.DiskGroundDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class UADTreeDecoratorTypes {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPES = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, UltraAmplifiedDimension.MODID);

    public static final RegistryObject<TreeDecoratorType<DiskGroundDecorator>> DISK_GROUND_DECORATOR = createTreeDecoratorType("disk_ground_decorator", () -> new TreeDecoratorType<>(DiskGroundDecorator.CODEC));

    public static <B extends TreeDecoratorType<?>> RegistryObject<B> createTreeDecoratorType(String name, Supplier<B> type) {
        return TREE_DECORATOR_TYPES.register(name, type);
    }
}

package net.telepathicgrunt.ultraamplified.world.dimension;

import java.lang.reflect.Field;
import java.util.function.BiFunction;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.ImmutableList;
import com.telepathicgrunt.ultraamplified.UltraAmplified;

import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.network.FMLHandshakeMessages;
import net.minecraftforge.fml.network.FMLNetworkConstants;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.minecraftforge.registries.ForgeRegistries;


@Mod.EventBusSubscriber(modid = UltraAmplified.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class UltraAmplifiedDimension {

	public static final ModDimension ULTRAAMPLIFIED = new ModDimension() {
        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return UltraAmplifiedWorldProvider::new;
        }
    };

    private static final ResourceLocation ULTRAAMPLIFIED_ID = new ResourceLocation(UltraAmplified.MODID, "ultraamplified");
	
    
    //registers the dimension
    @Mod.EventBusSubscriber(modid = UltraAmplified.MODID)
    private static class ForgeEvents {
        @SubscribeEvent
        public static void registerDimensions(RegisterDimensionsEvent event) {
            if (DimensionType.byName(ULTRAAMPLIFIED_ID) == null) {
                DimensionManager.registerDimension(ULTRAAMPLIFIED_ID, ULTRAAMPLIFIED, null, true);
            }
        }
    }

    @SubscribeEvent
    public static void registerModDimensions(RegistryEvent.Register<ModDimension> event) {
        RegUtil.generic(event.getRegistry()).add("ultraamplified", ULTRAAMPLIFIED);
    }

    
    //This forge fix is from this open source code: https://github.com/Cryptic-Mushroom/The-Midnight/blob/1.14.4-dev/src/main/java/com/mushroom/midnight/common/registry/MidnightDimensions.java
    //Credit to Corail31 and Gegy
    static {
        // TODO: Temporary hack until Forge fix
        try {
            Field channelField = FMLNetworkConstants.class.getDeclaredField("handshakeChannel");
            channelField.setAccessible(true);

            SimpleChannel handshakeChannel = (SimpleChannel) channelField.get(null);
            handshakeChannel.messageBuilder(S2CDimensionSync.class, 100)
                    .loginIndex(S2CDimensionSync::getLoginIndex, S2CDimensionSync::setLoginIndex)
                    .decoder(S2CDimensionSync::decode)
                    .encoder(S2CDimensionSync::encode)
                    .buildLoginPacketList(isLocal -> {
                        if (isLocal) return ImmutableList.of();
                        return ImmutableList.of(Pair.of("Ultra Amplified Dim Sync", new S2CDimensionSync(UltraAmplifiedDimension.ultraamplified())));
                    })
                    .consumer((msg, ctx) -> {
                        if (DimensionManager.getRegistry().getByValue(msg.id) == null) {
                            DimensionManager.registerDimensionInternal(msg.id, msg.name, msg.dimension, null, msg.skyLight);
                        }
                        ctx.get().setPacketHandled(true);
                        handshakeChannel.reply(new FMLHandshakeMessages.C2SAcknowledge(), ctx.get());
                    })
                    .add();
        } catch (ReflectiveOperationException e) {
            UltraAmplified.LOGGER.error("Failed to add dimension sync to handshake channel", e);
        }
    }
	

    public static DimensionType ultraamplified() {
        return DimensionType.byName(ULTRAAMPLIFIED_ID);
    }
    
    
    //This forge fix is from this open source code: https://github.com/Cryptic-Mushroom/The-Midnight/blob/1.14.4-dev/src/main/java/com/mushroom/midnight/common/registry/MidnightDimensions.java
    //Credit to Corail31 and Gegy
    public static class S2CDimensionSync {
        final int id;
        final ResourceLocation name;
        final ModDimension dimension;
        final boolean skyLight;

        private int loginIndex;

        public S2CDimensionSync(DimensionType dimensionType) {
            this.id = dimensionType.getId() + 1;
            this.name = DimensionType.getKey(dimensionType);
            this.dimension = dimensionType.getModType();
            this.skyLight = dimensionType.func_218272_d();
        }

        S2CDimensionSync(int id, ResourceLocation name, ModDimension dimension, boolean skyLight) {
            this.id = id;
            this.name = name;
            this.dimension = dimension;
            this.skyLight = skyLight;
        }

        void setLoginIndex(final int loginIndex) {
            this.loginIndex = loginIndex;
        }

        int getLoginIndex() {
            return this.loginIndex;
        }

        void encode(PacketBuffer buffer) {
            buffer.writeInt(this.id);
            buffer.writeResourceLocation(this.name);
            buffer.writeResourceLocation(this.dimension.getRegistryName());
            buffer.writeBoolean(this.skyLight);
        }

        public static S2CDimensionSync decode(PacketBuffer buffer) {
            int id = buffer.readInt();
            ResourceLocation name = buffer.readResourceLocation();
            ModDimension dimension = ForgeRegistries.MOD_DIMENSIONS.getValue(buffer.readResourceLocation());
            boolean skyLight = buffer.readBoolean();

            return new S2CDimensionSync(id, name, dimension, skyLight);
        }
    }
}

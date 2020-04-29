package net.telepathicgrunt.ultraamplified.world.dimension;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorld;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.PacketDistributor;
import net.minecraftforge.fml.network.simple.SimpleChannel;
import net.telepathicgrunt.ultraamplified.UltraAmplified;


public class TimeSyncNetworkPacket
{
	//setup channel to send packages through
	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel DEFAULT_CHANNEL = NetworkRegistry.newSimpleChannel(
																			new ResourceLocation(UltraAmplified.MODID, "networking"), 
																			() -> PROTOCOL_VERSION, PROTOCOL_VERSION::equals, PROTOCOL_VERSION::equals
																		);

	/*
	 * Register the channel so it exists
	 */
	public static void init()
	{
		int channelID = -1;
		DEFAULT_CHANNEL.registerMessage(++channelID, UpdateTimePacket.class, UpdateTimePacket::compose, UpdateTimePacket::parse, UpdateTimePacket.Handler::handle);
	}

	/*
	 * updates the time for all clients from server
	 */
	public static class UpdateTimePacket
	{
		private long time = 0;

		public static void sendToClient(long timeIn)
		{
			TimeSyncNetworkPacket.DEFAULT_CHANNEL.send(PacketDistributor.ALL.noArg(), new UpdateTimePacket(timeIn));
		}


		/*
		 * Sets block location and resource location
		 */
		public UpdateTimePacket(long timeIn)
		{
			this.time = timeIn;
		}


		/*
		 * How the server will read the packet. 
		 */
		public static UpdateTimePacket parse(final PacketBuffer buf)
		{
			return new UpdateTimePacket(buf.readLong());
		}
		

		/*
		 * creates the packet buffer and sets its values
		 */
		public static void compose(final UpdateTimePacket pkt, final PacketBuffer buf)
		{
			buf.writeLong(pkt.time);
		}

		
		/*
		 * What the client will do with the packet
		 */
		public static class Handler
		{
			//this is what gets run on the client
			public static void handle(final UpdateTimePacket pkt, final Supplier<NetworkEvent.Context> ctx)
			{
				Minecraft.getInstance().deferTask(() -> {
					@SuppressWarnings("resource")
					IWorld world = Minecraft.getInstance().world;
					if(world.getDimension().getType() == UADimensionRegistration.ultraamplified()) {
						((UADimension)world.getDimension()).setWorldTimeClientSided(pkt.time);
					}
				});
				ctx.get().setPacketHandled(true);
			}
		}
	}
}
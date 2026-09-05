package logisticspipes.network.to_client.debug;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import logisticspipes.LPConstants;
import logisticspipes.client.debug.PipeLogBuffer;
import logisticspipes.client.gui.debug.PipeLogScreen;
import logisticspipes.client.gui.debug.RoutingDebugScreen;

/**
 * Reopens one of the debug screens.
 *
 * <p>The screens read client-side buffers, so there is nothing to send but which one to show. This
 * is what {@code /logisticspipes debug show} and {@code /logisticspipes debug pipe show} do: the
 * command runs on the server, and only the client can put a screen on the screen.
 */
public record OpenDebugScreenMessage(Screen screen) implements CustomPacketPayload {

    /** Which screen to open. */
    public enum Screen {
        /** The candidate list of a routing table update being stepped through. */
        ROUTING,
        /** The log of the pipe watched most recently. */
        PIPE_LOG,
    }

    public static final Type<OpenDebugScreenMessage> TYPE = new Type<>(LPConstants.rl("open_debug_screen"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenDebugScreenMessage> STREAM_CODEC =
            StreamCodec.composite(
                    NeoForgeStreamCodecs.<RegistryFriendlyByteBuf, Screen>enumCodec(Screen.class),
                    OpenDebugScreenMessage::screen,
                    OpenDebugScreenMessage::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(OpenDebugScreenMessage message, IPayloadContext context) {
        switch (message.screen) {
            case ROUTING -> RoutingDebugScreen.open();
            case PIPE_LOG -> {
                final PipeLogBuffer buffer = PipeLogBuffer.mostRecent();
                if (buffer == null) {
                    context.player().sendSystemMessage(Component.literal(
                            "No pipe log yet. Point at a pipe and run /" + LPConstants.ID
                                    + " debug pipe log to start one."));
                } else {
                    PipeLogScreen.open(buffer);
                }
            }
        }
    }
}

package logisticspipes.network.to_client.debug;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import logisticspipes.LPConstants;
import logisticspipes.client.debug.PipeLogBuffer;
import logisticspipes.client.gui.debug.PipeLogScreen;

/**
 * Opens the log of a pipe the player just started watching.
 *
 * <p>Sent when the player starts watching a pipe, which is also when the log screen opens — the
 * Swing window this replaced appeared at the same point. It can be reopened later with
 * {@code /logisticspipes debug pipe show}.
 */
public record OpenPipeLogMessage(int logId, String title) implements CustomPacketPayload {

    public static final Type<OpenPipeLogMessage> TYPE = new Type<>(LPConstants.rl("open_pipe_log"));

    public static final StreamCodec<RegistryFriendlyByteBuf, OpenPipeLogMessage> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, OpenPipeLogMessage::logId,
                    ByteBufCodecs.STRING_UTF8, OpenPipeLogMessage::title,
                    OpenPipeLogMessage::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(OpenPipeLogMessage message, IPayloadContext context) {
        final PipeLogBuffer buffer = PipeLogBuffer.of(message.logId);
        buffer.setTitle(message.title);
        PipeLogScreen.open(buffer);
    }
}

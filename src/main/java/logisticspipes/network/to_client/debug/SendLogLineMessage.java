package logisticspipes.network.to_client.debug;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import logisticspipes.LPConstants;
import logisticspipes.client.debug.PipeLogBuffer;

/**
 * One more line for a pipe's log.
 *
 * <p>The id says which pipe, since a player can follow several at once. The client keeps the lines
 * whether or not the log screen is open.
 */
public record SendLogLineMessage(int logId, String line) implements CustomPacketPayload {

    public static final Type<SendLogLineMessage> TYPE = new Type<>(LPConstants.rl("log_line"));

    public static final StreamCodec<RegistryFriendlyByteBuf, SendLogLineMessage> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, SendLogLineMessage::logId,
                    ByteBufCodecs.STRING_UTF8, SendLogLineMessage::line,
                    SendLogLineMessage::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(SendLogLineMessage message, IPayloadContext context) {
        PipeLogBuffer.of(message.logId).addLine(message.line);
    }
}

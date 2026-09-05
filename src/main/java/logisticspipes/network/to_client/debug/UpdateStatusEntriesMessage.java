package logisticspipes.network.to_client.debug;

import java.util.List;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import net.neoforged.neoforge.network.handling.IPayloadContext;

import logisticspipes.LPConstants;
import logisticspipes.client.debug.PipeLogBuffer;
import logisticspipes.pipes.basic.debug.StatusEntry;

/**
 * The current status tree of a pipe being watched.
 */
public record UpdateStatusEntriesMessage(int logId, List<StatusEntry> status) implements CustomPacketPayload {

    public static final Type<UpdateStatusEntriesMessage> TYPE = new Type<>(LPConstants.rl("update_status_entries"));

    public static final StreamCodec<RegistryFriendlyByteBuf, UpdateStatusEntriesMessage> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, UpdateStatusEntriesMessage::logId,
                    StatusEntry.STREAM_CODEC.apply(ByteBufCodecs.list()), UpdateStatusEntriesMessage::status,
                    UpdateStatusEntriesMessage::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public static void handle(UpdateStatusEntriesMessage message, IPayloadContext context) {
        PipeLogBuffer.of(message.logId).setStatus(message.status);
    }
}

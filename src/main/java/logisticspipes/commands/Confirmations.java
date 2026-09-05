package logisticspipes.commands;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.CustomClickActionEvent;

import org.jspecify.annotations.Nullable;

import logisticspipes.LPConstants;

/**
 * A yes/no question asked in chat, answered by clicking one of two buttons.
 *
 * <p>The buttons carry a custom click action rather than a command. A question is something the
 * game asked you, so answering it is not a command anybody should have to type -- and the earlier
 * command form had to expose the question's id as an argument, which no player could know.
 *
 * <p>That id is still what makes a stale button safe: it travels in the click payload, so a
 * {@code [Yes]} left further up the chat cannot answer the question that has since replaced it.
 * Nothing here holds on to the player; the click brings them back.
 */
public final class Confirmations {

    public static final Confirmations INSTANCE = new Confirmations();

    private static final Identifier CONFIRM = LPConstants.rl("confirm");
    private static final Identifier CANCEL = LPConstants.rl("cancel");

    private static final Map<UUID, Pending> PENDING = new ConcurrentHashMap<>();
    private static final AtomicInteger NEXT_ID = new AtomicInteger();

    private Confirmations() {
    }

    /**
     * Asks {@code question} and runs {@code action} if the player clicks yes.
     */
    public static void ask(ServerPlayer player, String question, Consumer<ServerPlayer> action) {
        final int id = NEXT_ID.incrementAndGet();
        PENDING.put(player.getUUID(), new Pending(id, action));
        player.sendSystemMessage(Component.literal(question + "? ").withStyle(ChatFormatting.AQUA)
            .append(ComponentUtils.formatList(List.of(
                    ChatUi.actionButton("Yes", ChatFormatting.GREEN, CONFIRM, IntTag.valueOf(id), question),
                    ChatUi.actionButton("No", ChatFormatting.RED, CANCEL, null, "Forget it")),
                CommonComponents.SPACE)));
    }

    /**
     * Handles the two buttons.
     *
     * <p>The event has to be cancelled once we act on it, or vanilla logs the click as unhandled.
     */
    @SubscribeEvent
    public void onCustomClick(CustomClickActionEvent event) {
        final ServerPlayer player = event.getPlayer();
        if (player == null) {
            // Sent during configuration, before the player is in a world; no question can be open.
            return;
        }
        if (event.getIdentifier().equals(CANCEL)) {
            cancel(player);
            event.setCanceled(true);
        } else if (event.getIdentifier().equals(CONFIRM)) {
            confirm(player, event.getPayload());
            event.setCanceled(true);
        }
    }

    private static void cancel(ServerPlayer player) {
        if (PENDING.remove(player.getUUID()) != null) {
            player.sendSystemMessage(Component.literal("Cancelled.").withStyle(ChatFormatting.GRAY));
        }
    }

    private static void confirm(ServerPlayer player, @Nullable Tag payload) {
        final Pending pending = PENDING.get(player.getUUID());
        // The payload comes from the client, so it is only ever a hint about which question was
        // clicked: a wrong or missing one costs the player a click, never someone else's answer.
        if (pending == null || !(payload instanceof IntTag id) || pending.id() != id.intValue()) {
            player.sendSystemMessage(
                Component.literal("That question is no longer open.").withStyle(ChatFormatting.RED));
            return;
        }
        PENDING.remove(player.getUUID(), pending);
        pending.action().accept(player);
    }

    private record Pending(int id, Consumer<ServerPlayer> action) {}
}

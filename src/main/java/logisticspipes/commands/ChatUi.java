package logisticspipes.commands;

import java.util.Optional;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

import org.jspecify.annotations.Nullable;

public final class ChatUi {

    private ChatUi() {
    }

    /**
     * A {@code [Label]} that runs {@code command} when clicked.
     */
    public static MutableComponent button(String label, ChatFormatting color, String command, String tooltip) {
        return Component.literal("[" + label + "]").withStyle(style -> style
            .withColor(color)
            .withClickEvent(new ClickEvent.RunCommand(command))
            .withHoverEvent(new HoverEvent.ShowText(Component.literal(tooltip))));
    }

    /**
     * A {@code [Label]} that tells the server {@code id} was clicked, with no command behind it.
     *
     * <p>For answers that belong to a conversation the game started, and so have no business being
     * typeable: the server picks them up from
     * {@link net.neoforged.neoforge.event.entity.player.CustomClickActionEvent}.
     */
    public static MutableComponent actionButton(String label, ChatFormatting color, Identifier id,
        @Nullable Tag payload, String tooltip) {
        return Component.literal("[" + label + "]").withStyle(style -> style
            .withColor(color)
            .withClickEvent(new ClickEvent.Custom(id, Optional.ofNullable(payload)))
            .withHoverEvent(new HoverEvent.ShowText(Component.literal(tooltip))));
    }

    /**
     * Text that copies {@code value} to the clipboard when clicked.
     */
    public static MutableComponent copyable(String label, String value, String tooltip) {
        return Component.literal(label).withStyle(style -> style
            .withUnderlined(true)
            .withClickEvent(new ClickEvent.CopyToClipboard(value))
            .withHoverEvent(new HoverEvent.ShowText(Component.literal(tooltip))));
    }
}

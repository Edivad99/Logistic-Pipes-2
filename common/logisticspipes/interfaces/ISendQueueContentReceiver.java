package logisticspipes.interfaces;

import org.jspecify.annotations.Nullable;

import java.util.Collection;

import logisticspipes.utils.item.ItemIdentifierStack;

public interface ISendQueueContentReceiver {

	void handleSendQueueItemIdentifierList(Collection<@Nullable ItemIdentifierStack> allItems);
}

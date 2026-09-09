package logisticspipes.interfaces;

import java.util.Collection;

import logisticspipes.utils.item.ItemIdentifierStack;

public interface ISendQueueContentReceiver {

	void handleSendQueueItemIdentifierList(Collection<ItemIdentifierStack> allItems);
}

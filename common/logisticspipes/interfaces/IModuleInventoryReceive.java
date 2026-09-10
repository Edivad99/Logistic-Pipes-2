package logisticspipes.interfaces;

import org.jspecify.annotations.Nullable;

import java.util.Collection;

import logisticspipes.utils.item.ItemIdentifierStack;

public interface IModuleInventoryReceive {

	void handleInvContent(Collection<@Nullable ItemIdentifierStack> allItems);
}

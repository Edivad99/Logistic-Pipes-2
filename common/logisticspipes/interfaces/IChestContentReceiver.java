package logisticspipes.interfaces;

import org.jspecify.annotations.Nullable;

import java.util.Collection;

import logisticspipes.utils.item.ItemIdentifierStack;

public interface IChestContentReceiver {

	void setReceivedChestContent(Collection<@Nullable ItemIdentifierStack> allItems);

}

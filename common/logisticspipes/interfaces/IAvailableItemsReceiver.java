package logisticspipes.interfaces;

import org.jspecify.annotations.Nullable;

import java.util.Collection;

import logisticspipes.utils.item.ItemIdentifierStack;

/**
 * A screen showing the items an orderer can currently request.
 */
public interface IAvailableItemsReceiver {

    void setAvailableItems(Collection<@Nullable ItemIdentifierStack> allItems);
}

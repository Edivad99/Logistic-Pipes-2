package logisticspipes.interfaces.routing;

import net.minecraft.core.BlockPos;

import logisticspipes.request.resources.IResource;
import logisticspipes.utils.item.ItemIdentifier;

public interface IFilter {

	boolean isBlocked();

	boolean isFilteredItem(ItemIdentifier item);

	boolean isFilteredItem(IResource resultItem);

	boolean blockProvider();

	boolean blockCrafting();

	boolean blockRouting();

	boolean blockPower();

	BlockPos getPos();
}

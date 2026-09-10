package logisticspipes.interfaces;

import org.jspecify.annotations.Nullable;

import logisticspipes.request.resources.IResource;
import logisticspipes.routing.order.LinkedLogisticsOrderList;

public interface IRequestWatcher {

	void handleOrderList(@Nullable IResource stack, LinkedLogisticsOrderList orders);

	void handleClientSideListInfo(int id, IResource stack, LinkedLogisticsOrderList orders);

	void handleClientSideRemove(int id);
}

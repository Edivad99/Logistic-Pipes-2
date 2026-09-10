package logisticspipes.request;

import java.util.List;

import org.jspecify.annotations.Nullable;

import logisticspipes.request.resources.IResource;
import logisticspipes.routing.order.LinkedLogisticsOrderList;

public interface RequestLog {

	void handleMissingItems(List<IResource> resources);

	void handleSucessfullRequestOf(IResource item, @Nullable LinkedLogisticsOrderList paticipating);

	void handleSucessfullRequestOfList(List<IResource> resources, LinkedLogisticsOrderList paticipating);
}

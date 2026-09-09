package logisticspipes.interfaces;

import org.jspecify.annotations.Nullable;

public interface IHUDModuleHandler {

	void startHUDWatching();

	void stopHUDWatching();

	@Nullable IHUDModuleRenderer getHUDRenderer();
}

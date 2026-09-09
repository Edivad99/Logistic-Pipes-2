package logisticspipes.interfaces;

import net.minecraft.world.entity.player.Player;

public interface IModuleWatchReceiver {

	void startWatching(Player player);

	void stopWatching(Player player);
}

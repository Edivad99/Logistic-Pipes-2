package logisticspipes.interfaces;

import java.util.List;
import java.util.UUID;

import net.minecraft.world.entity.player.Player;

import org.jspecify.annotations.Nullable;

import logisticspipes.world.level.block.entity.LogisticsSecurityBlockEntity;

public interface ISecurityStationManager {

	void add(LogisticsSecurityBlockEntity tile);

	@Nullable
	LogisticsSecurityBlockEntity getStation(UUID id);

	void remove(LogisticsSecurityBlockEntity tile);

	void deauthorizeUUID(UUID id);

	void authorizeUUID(UUID id);

	boolean isAuthorized(UUID id);

	boolean isAuthorized(String id);

	void sendClientAuthorizationList();

	void sendClientAuthorizationList(Player player);

	void setClientAuthorizationList(List<String> list);
}

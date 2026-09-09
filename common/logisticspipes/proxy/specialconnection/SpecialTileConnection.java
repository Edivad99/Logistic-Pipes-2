package logisticspipes.proxy.specialconnection;

import java.util.Collection;
import java.util.List;

import net.minecraft.world.level.block.entity.BlockEntity;

import logisticspipes.interfaces.routing.ISpecialTileConnection;
import logisticspipes.logisticspipes.IRoutedItem;

public class SpecialTileConnection {

	private final List<ISpecialTileConnection> handler;

	private SpecialTileConnection(List<ISpecialTileConnection> handler) {
		this.handler = handler;
	}

	/** Collects everything registered on {@code event}, which is closed from here on. */
	public static SpecialTileConnection from(RegisterSpecialConnectionsEvent event) {
		return new SpecialTileConnection(event.registeredTileConnections());
	}

	public Collection<BlockEntity> getConnectedPipes(BlockEntity tile) {
		for (ISpecialTileConnection connectionHandler : handler) {
			if (connectionHandler.isType(tile)) {
				return connectionHandler.getConnections(tile);
			}
		}
		return List.of();
	}

	public boolean needsInformationTransition(BlockEntity tile) {
		for (ISpecialTileConnection connectionHandler : handler) {
			if (connectionHandler.isType(tile)) {
				return connectionHandler.needsInformationTransition();
			}
		}
		return false;
	}

	public void transmit(BlockEntity tile, IRoutedItem arrivingItem) {
		for (ISpecialTileConnection connectionHandler : handler) {
			if (connectionHandler.isType(tile)) {
				connectionHandler.transmit(tile, arrivingItem);
				break;
			}
		}
	}

	public boolean isType(BlockEntity tile) {
		for (ISpecialTileConnection connectionHandler : handler) {
			if (connectionHandler.isType(tile)) {
				return true;
			}
		}
		return false;
	}
}

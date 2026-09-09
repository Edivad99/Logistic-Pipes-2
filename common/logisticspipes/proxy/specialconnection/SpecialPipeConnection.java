package logisticspipes.proxy.specialconnection;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.core.Direction;

import lombok.AllArgsConstructor;
import lombok.Data;

import logisticspipes.interfaces.routing.ISpecialPipedConnection;
import logisticspipes.routing.PipeRoutingConnectionType;
import logisticspipes.routing.pathfinder.IPipeInformationProvider;

public class SpecialPipeConnection {

	private final List<ISpecialPipedConnection> handler;

	private SpecialPipeConnection(List<ISpecialPipedConnection> handler) {
		this.handler = handler;
	}

	/** Collects everything registered on {@code event}, which is closed from here on. */
	public static SpecialPipeConnection from(RegisterSpecialConnectionsEvent event) {
		return new SpecialPipeConnection(event.registeredPipedConnections());
	}

	public List<ConnectionInformation> getConnectedPipes(IPipeInformationProvider startPipe, EnumSet<PipeRoutingConnectionType> connection, Direction side) {
		for (ISpecialPipedConnection connectionHandler : handler) {
			if (connectionHandler.isType(startPipe)) {
				return connectionHandler.getConnections(startPipe, connection, side);
			}
		}
		return List.of();
	}

	@Data
	@AllArgsConstructor
	public static class ConnectionInformation {

		private IPipeInformationProvider connectedPipe;
		private EnumSet<PipeRoutingConnectionType> connectionFlags;
		private Direction insertOrientation;
		private Direction exitOrientation;
		private double distance;
	}
}

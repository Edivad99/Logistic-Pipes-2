package logisticspipes.routing.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

import org.jspecify.annotations.Nullable;

import logisticspipes.interfaces.IDebugHUDProvider;
import logisticspipes.interfaces.IHeadUpDisplayRendererProvider;
import logisticspipes.particle.Particles;
import logisticspipes.particle.PipeFXRenderHandler;
import logisticspipes.renderer.LogisticsHUDRenderer;
import logisticspipes.routing.PipeRoutingConnectionType;
import logisticspipes.util.DoubleCoordinates;

public class ClientViewController implements IDebugHUDProvider {

	private static ClientViewController instance;

	private ClientViewController() {}

	private @Nullable DoubleCoordinates mainPipe = null;
	private int tick = 0;
	private final List<DoubleCoordinates> canidates = new ArrayList<>();
	/** The candidate list as the debug screen shows it, rebuilt on every step. */
	private final List<Component> candidateLines = new ArrayList<>();

	private List<IHeadUpDisplayRendererProvider> listHUD = new ArrayList<>();
	private HashMap<DoubleCoordinates, DebugInformation> HUDPositions = new HashMap<>();

	public static class DebugInformation {

		public boolean isNew = false;
		public int newIndex = -1;
		public List<Integer> positions = new ArrayList<>();
		public List<RouteDebugInfo> routes = new ArrayList<>();
		public Set<PipeRoutingConnectionType> closedSet;
		public Map<PipeRoutingConnectionType, List<List<BlockPos>>> filters;
		public Set<PipeRoutingConnectionType> nextFlags;
	}

	public static ClientViewController instance() {
		if (ClientViewController.instance == null) {
			ClientViewController.instance = new ClientViewController();
		}
		return ClientViewController.instance;
	}

	private DebugInformation getDebugInformation(DoubleCoordinates pos) {
		DebugInformation info = HUDPositions.get(pos);
		if (info == null) {
			info = new DebugInformation();
			HUDPositions.put(pos, info);
		}
		return info;
	}

	public void tick() {
		if (tick++ % 5 != 0) {
			return;
		}
		if (mainPipe != null) {
			PipeFXRenderHandler.spawnGenericParticle(Particles.WHITE_SPARKLE, mainPipe.getXInt(), mainPipe.getYInt(), mainPipe.getZInt(), 1);
		}
		for (DoubleCoordinates pos : canidates) {
			PipeFXRenderHandler.spawnGenericParticle(Particles.ORANGE_SPARKLE, pos.getXInt(), pos.getYInt(), pos.getZInt(), 1);
		}
	}

	public void clear() {
		mainPipe = null;
		canidates.clear();
		listHUD.clear();
		HUDPositions.clear();
	}

	public void setSource(RouteDebugInfo route) {
		mainPipe = new DoubleCoordinates(route.destination().getX(), route.destination().getY(),
				route.destination().getZ());
		getDebugInformation(mainPipe).nextFlags = route.flags();
	}

	public void addCandidate(RouteDebugInfo route) {
		DoubleCoordinates pos = new DoubleCoordinates(route.destination().getX(), route.destination().getY(),
				route.destination().getZ());
		canidates.add(pos);
		getDebugInformation(pos).isNew = true;
		getDebugInformation(pos).newIndex = route.index();
	}

	public void init() {
		candidateLines.clear();
		LogisticsHUDRenderer.instance().debugHUD = this;
	}

	public List<Component> candidateLines() {
		return List.copyOf(candidateLines);
	}

	public void done() {
		candidateLines.clear();
		LogisticsHUDRenderer.instance().debugHUD = null;
		listHUD.clear();
		HUDPositions.clear();
	}

	public void setClosedSet(BlockPos pos, Set<PipeRoutingConnectionType> closed) {
		getDebugInformation(new DoubleCoordinates(pos.getX(), pos.getY(), pos.getZ())).closedSet = closed;
	}

	public void setFilters(BlockPos pos, Map<PipeRoutingConnectionType, List<List<BlockPos>>> filters) {
		getDebugInformation(new DoubleCoordinates(pos.getX(), pos.getY(), pos.getZ())).filters = filters;
	}

	public void updateList(List<RouteDebugInfo> routes) {
		candidateLines.clear();
		int i = 0;
		for (RouteDebugInfo route : routes) {
			i++;
			candidateLines.add(Component.literal(route.destinationName())
					.withStyle(route.newlyAddedCandidate() ? ChatFormatting.AQUA : ChatFormatting.WHITE));
			candidateLines.add(Component.literal("    " + route.networkDescription())
					.withStyle(ChatFormatting.GRAY));
			DoubleCoordinates pos = new DoubleCoordinates(route.destination().getX(),
					route.destination().getY(), route.destination().getZ());
			getDebugInformation(pos).routes.add(route);
			getDebugInformation(pos).positions.add(i);
		}
		listHUD.addAll(HUDPositions.entrySet().stream()
				.map(entry -> new HUDRoutingTableDebugProvider(new HUDRoutingTableGeneralInfo(entry.getValue()), entry.getKey()))
				.collect(Collectors.toList()));
	}

	@Override
	public List<IHeadUpDisplayRendererProvider> getHUDs() {
		return listHUD;
	}
}

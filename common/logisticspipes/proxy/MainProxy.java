package logisticspipes.proxy;

import java.util.Map;
import java.util.WeakHashMap;
import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.SectionPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;

import net.neoforged.fml.LogicalSide;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.event.level.LevelEvent;

import com.google.common.collect.Maps;
import org.jspecify.annotations.Nullable;

import logisticspipes.LogisticsEventListener;
import logisticspipes.routing.debug.RoutingTableDebugUpdateThread;
import logisticspipes.routing.pathfinder.IPipeInformationProvider;
import logisticspipes.ticks.RoutingTableUpdateThread;
import logisticspipes.utils.PlayerCollectionList;
import logisticspipes.world.item.LPItems;

@Deprecated(forRemoval = true)
public class MainProxy {

	private MainProxy() {}


	private static final WeakHashMap<Thread, LogicalSide> threadSideMap = new WeakHashMap<>();

	// ── Side detection ────────────────────────────────────────────────────────

	private static LogicalSide getEffectiveSide() {
		Thread thr = Thread.currentThread();
		if (MainProxy.threadSideMap.containsKey(thr)) {
			return MainProxy.threadSideMap.get(thr);
		}
		LogicalSide side = MainProxy.getEffectiveSide(thr);
		if (MainProxy.threadSideMap.size() > 50) {
			MainProxy.threadSideMap.clear();
		}
		MainProxy.threadSideMap.put(thr, side);
		return side;
	}

	private static LogicalSide getEffectiveSide(Thread thr) {
		if (thr.getName().equals("Server thread")
				|| (thr instanceof RoutingTableUpdateThread)
				|| (thr instanceof RoutingTableDebugUpdateThread)) {
			return LogicalSide.SERVER;
		}
		// ComputerCraft Lua-thread check removed — CC has no 1.20.1 port (former dummy always returned false).
		return LogicalSide.CLIENT;
	}

	/** Use {@link #isClient(Level)} when a level is available; this thread-based fallback is slow. */
	@Deprecated
	public static boolean isClient() {
		return MainProxy.getEffectiveSide() == LogicalSide.CLIENT;
	}

	/** Use {@link #isServer(Level)} when a level is available; this thread-based fallback is slow. */
	@Deprecated
	public static boolean isServer() {
		return MainProxy.getEffectiveSide() == LogicalSide.SERVER;
	}

	public static boolean isClient(@Nullable Level level) {
		// Mirror isServer(Level): fall back to thread detection when no level is available
		// (e.g. a pipe queried before its container is bound).
		if (level == null) {
            return MainProxy.getEffectiveSide() == LogicalSide.CLIENT;
        }
		return level.isClientSide();
	}

	public static boolean isServer(@Nullable Level level) {
		if (level == null) {
            return MainProxy.getEffectiveSide() == LogicalSide.SERVER;
        }
		return !level.isClientSide();
	}

	/**
	 * Accepts any {@link LevelAccessor} (e.g. from {@code BlockEvent.getLevel()}).
	 * Falls back to thread detection if the accessor is not a full {@link Level}.
	 */
	public static boolean isServer(@Nullable LevelAccessor levelAccessor) {
		if (levelAccessor instanceof Level level) {
			return !level.isClientSide();
		}
		return MainProxy.isServer();
	}

	public static boolean isClient(@Nullable LevelAccessor levelAccessor) {
		if (levelAccessor instanceof Level level) {
			return level.isClientSide();
		}
		return MainProxy.isClient();
	}

	public static void runOnServer(@Nullable LevelAccessor level, Supplier<Runnable> runnableConsumer) {
		if (isServer(level)) runnableConsumer.get().run();
	}

	public static void runOnClient(@Nullable LevelAccessor level, Supplier<Runnable> runnableConsumer) {
		if (isClient(level)) runnableConsumer.get().run();
	}

	// ── Chunk-watch / broadcast helpers ──────────────────────────────────────

	public static boolean isAnyoneWatching(BlockPos pos, int dimensionID) {
		ChunkPos chunkPos = ChunkPos.containing(pos);
		PlayerCollectionList list = LogisticsEventListener.watcherList.get(chunkPos);
		return list != null && !list.isEmpty();
	}

	public static boolean isAnyoneWatching(int X, int Z, int dimensionID) {
		ChunkPos chunkPos = new ChunkPos(SectionPos.blockToSectionCoord(X), SectionPos.blockToSectionCoord(Z));
		PlayerCollectionList list = LogisticsEventListener.watcherList.get(chunkPos);
		return list != null && !list.isEmpty();
	}

	// ── Misc ─────────────────────────────────────────────────────────────────

	public static ItemEntity dropItems(Level level, ItemStack stack, int xCoord, int yCoord, int zCoord) {
		ItemEntity item = new ItemEntity(level, xCoord, yCoord, zCoord, stack);
		level.addFreshEntity(item);
		return item;
	}

	public static boolean checkPipesConnections(BlockEntity from, BlockEntity to, Direction way) {
		return MainProxy.checkPipesConnections(from, to, way, false);
	}

	public static boolean checkPipesConnections(@Nullable BlockEntity from, @Nullable BlockEntity to, Direction way, boolean ignoreSystemDisconnection) {
		if (from == null || to == null) return false;
		IPipeInformationProvider fromInfo = SimpleServiceLocator.pipeInformationManager.getInformationProviderFor(from);
		IPipeInformationProvider toInfo   = SimpleServiceLocator.pipeInformationManager.getInformationProviderFor(to);
		if (fromInfo == null && toInfo == null) return false;
		if (fromInfo != null && !fromInfo.canConnect(to, way, ignoreSystemDisconnection)) return false;
		if (toInfo   != null) return toInfo.canConnect(from, way.getOpposite(), ignoreSystemDisconnection);
		return true;
	}

	public static boolean isPipeControllerEquipped(@Nullable Player player) {
		return player != null &&
				player.getItemBySlot(EquipmentSlot.MAINHAND).is(LPItems.PIPE_CONTROLLER.get());
	}

}

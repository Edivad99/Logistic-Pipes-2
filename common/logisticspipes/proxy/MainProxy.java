package logisticspipes.proxy;

import java.util.WeakHashMap;
import java.util.function.Supplier;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;

import net.neoforged.fml.LogicalSide;

import org.jspecify.annotations.Nullable;

import logisticspipes.routing.debug.RoutingTableDebugUpdateThread;
import logisticspipes.ticks.RoutingTableUpdateThread;

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
}

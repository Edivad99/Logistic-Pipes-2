package logisticspipes.proxy;

import java.util.function.Supplier;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;


import org.jspecify.annotations.Nullable;


@Deprecated(forRemoval = true)
public class MainProxy {

	private MainProxy() {}


	/** Accepts any {@link LevelAccessor} (e.g. from {@code BlockEvent.getLevel()}). */
	public static boolean isServer(LevelAccessor levelAccessor) {
		return levelAccessor instanceof Level level && !level.isClientSide();
	}

	/**
	 * Runs {@code runnable} only on the server.
	 *
	 * <p>A null level means the caller is not in a world at all -- a module held in hand, say -- and
	 * so belongs to neither side. This used to fall back to guessing from the calling thread.
	 */
	public static void runOnServer(@Nullable LevelAccessor level, Supplier<Runnable> runnableConsumer) {
		if (level != null && isServer(level)) runnableConsumer.get().run();
	}
}

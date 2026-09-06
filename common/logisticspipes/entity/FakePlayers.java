package logisticspipes.entity;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.event.level.LevelEvent;

import org.jspecify.annotations.Nullable;

/**
 * One fake player per dimension, for the blocks that have to act as somebody.
 */
public final class FakePlayers {

    public static final FakePlayers INSTANCE = new FakePlayers();

    private static final Map<ResourceKey<Level>, FakePlayerLP> PLAYERS = new HashMap<>();

    private FakePlayers() {
    }

    /** The fake player for {@code level}, or null if it is not a server level. */
    public static @Nullable FakePlayer of(Level level) {
        if (!(level instanceof ServerLevel serverLevel)) {
            return null;
        }
        return PLAYERS.computeIfAbsent(level.dimension(), _ -> new FakePlayerLP(serverLevel));
    }

    @SubscribeEvent
    public void onLevelUnload(LevelEvent.Unload event) {
        if (event.getLevel() instanceof Level level) {
            PLAYERS.remove(level.dimension());
        }
    }
}

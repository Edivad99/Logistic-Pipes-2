package logisticspipes.interfaces;

import net.minecraft.world.level.Level;

import org.jspecify.annotations.Nullable;

public interface ILevelProvider {

	@Nullable
    Level getLevel();
}

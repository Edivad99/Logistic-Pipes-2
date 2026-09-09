package logisticspipes.utils;

import net.minecraft.world.level.Level;

import logisticspipes.interfaces.ILevelProvider;

public class DummyLevelProvider implements ILevelProvider {

	private final Level level;

	public DummyLevelProvider(Level level) {
		this.level = level;
	}

	@Override
	public Level getLevel() {
		return level;
	}
}

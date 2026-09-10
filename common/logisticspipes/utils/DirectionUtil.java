package logisticspipes.utils;

import org.jspecify.annotations.Nullable;

import net.minecraft.core.Direction;

public class DirectionUtil {

	@Nullable
	public static Direction getOrientation(int input) {
		if (input < 0 || Direction.values().length <= input) {
			return null;
		}
		return Direction.from3DDataValue(input);
	}
}

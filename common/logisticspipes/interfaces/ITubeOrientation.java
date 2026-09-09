package logisticspipes.interfaces;

import net.minecraft.core.BlockPos;
import logisticspipes.pipes.basic.CoreMultiBlockPipe;
import logisticspipes.utils.IPositionRotateble;

public interface ITubeOrientation {

	ITubeRenderOrientation getRenderOrientation();

	void rotatePositions(IPositionRotateble set);

	BlockPos getOffset();

	void setOnPipe(CoreMultiBlockPipe pipe);
}

package logisticspipes.asm.te;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;


public interface ITileEntityChangeListener {

	void pipeRemoved(BlockPos pos);

	void pipeAdded(BlockPos pos, Direction side);

}

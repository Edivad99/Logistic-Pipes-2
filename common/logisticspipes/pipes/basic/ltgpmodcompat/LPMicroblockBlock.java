package logisticspipes.pipes.basic.ltgpmodcompat;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import org.jspecify.annotations.Nullable;

/**
 * Base class for LP pipe blocks.
 * MCMultiPart integration (IMultipartContainerBlock) was removed for 1.20.1 — no 1.20.1 port exists.
 * Previously extended BlockContainer; now extends Block and implements EntityBlock directly.
 */
@Deprecated(forRemoval = true)
public abstract class LPMicroblockBlock extends Block implements EntityBlock {

	public LPMicroblockBlock(Properties properties) {
		super(properties);
	}

	@Nullable
	@Override
	public abstract BlockEntity newBlockEntity(BlockPos pos, BlockState state);
}

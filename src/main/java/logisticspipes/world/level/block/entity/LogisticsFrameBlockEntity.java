package logisticspipes.world.level.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Minimal BlockEntity for the Logistics Block Frame.
 * Has no logic — exists solely so the frame can use the ENTITYBLOCK_ANIMATED
 * render path and be drawn by LogisticsSolidBlockRenderer.
 */
public class LogisticsFrameBlockEntity extends LogisticsSolidBlockEntity {

    public LogisticsFrameBlockEntity(BlockPos pos, BlockState state) {
        super(LPBlockEntityTypes.FRAME.get(), pos, state);
    }
}

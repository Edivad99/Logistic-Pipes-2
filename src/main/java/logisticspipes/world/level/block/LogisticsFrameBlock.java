package logisticspipes.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import logisticspipes.world.level.block.entity.LogisticsFrameBlockEntity;

public class LogisticsFrameBlock extends LogisticsSolidBlock {

    public LogisticsFrameBlock(Properties properties) {
        super(properties);
    }

    @Override
    public String textureName() {
        return "frame";
    }

    /** The frame is the bare core: it is what the plates are drawn around on everything else. */
    @Override
    public boolean hasCoverPlates() {
        return false;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LogisticsFrameBlockEntity(pos, state);
    }
}

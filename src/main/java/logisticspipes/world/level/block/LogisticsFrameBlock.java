package logisticspipes.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jspecify.annotations.Nullable;

import logisticspipes.world.level.block.entity.LPBlockEntityTypes;
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

    // Its block entity adds nothing to the base tick, which only asks the server for the rotation.
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
        BlockEntityType<T> type) {
        return level.isClientSide()
            ? BaseEntityBlock.createTickerHelper(type, LPBlockEntityTypes.FRAME.get(),
                (_, _, _, be) -> be.clientTick())
            : null;
    }
}

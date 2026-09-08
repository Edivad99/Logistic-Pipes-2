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
import logisticspipes.world.level.block.entity.LogisticsStatisticsBlockEntity;

public class LogisticsStatisticsTableBlock extends LogisticsSolidBlock {

    public LogisticsStatisticsTableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public String textureName() {
        return "statistics_table";
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LogisticsStatisticsBlockEntity(pos, state);
    }

    // Its block entity never chained to the base tick, so there is no client half to run.
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
        BlockEntityType<T> type) {
        return level.isClientSide() ? null
            : BaseEntityBlock.createTickerHelper(type, LPBlockEntityTypes.STATISTICS_TABLE.get(),
                (lvl, pos, st, be) -> be.serverTick());
    }
}

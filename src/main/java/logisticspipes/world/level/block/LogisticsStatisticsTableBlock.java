package logisticspipes.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

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
}

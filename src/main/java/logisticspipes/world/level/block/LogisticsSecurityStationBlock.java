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
import logisticspipes.world.level.block.entity.LogisticsSecurityBlockEntity;

public class LogisticsSecurityStationBlock extends LogisticsSolidBlock {

    public LogisticsSecurityStationBlock(Properties properties) {
        super(properties);
    }

    @Override
    public String textureName() {
        return "security_station";
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LogisticsSecurityBlockEntity(pos, state);
    }

    // Its block entity adds nothing to the base tick, which only asks the server for the rotation.
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
        BlockEntityType<T> type) {
        return level.isClientSide()
            ? BaseEntityBlock.createTickerHelper(type, LPBlockEntityTypes.SECURITY_STATION.get(),
                (lvl, pos, st, be) -> be.clientTick())
            : null;
    }
}

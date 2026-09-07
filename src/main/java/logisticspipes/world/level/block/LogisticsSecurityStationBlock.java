package logisticspipes.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

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
}

package logisticspipes.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import logisticspipes.world.level.block.entity.LogisticsRFPowerProviderBlockEntity;

public class LogisticsRFPowerProviderBlock extends LogisticsSolidBlock {

    public LogisticsRFPowerProviderBlock(Properties properties) {
        super(properties);
    }

    @Override
    public String textureName() {
        return "power_provider_rf";
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LogisticsRFPowerProviderBlockEntity(pos, state);
    }
}

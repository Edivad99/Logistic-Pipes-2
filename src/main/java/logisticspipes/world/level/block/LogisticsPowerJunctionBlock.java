package logisticspipes.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import logisticspipes.world.level.block.entity.LogisticsPowerJunctionBlockEntity;

public class LogisticsPowerJunctionBlock extends LogisticsSolidBlock {

    public LogisticsPowerJunctionBlock(Properties properties) {
        super(properties);
    }

    @Override
    public String textureName() {
        return "power_junction";
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LogisticsPowerJunctionBlockEntity(pos, state);
    }
}

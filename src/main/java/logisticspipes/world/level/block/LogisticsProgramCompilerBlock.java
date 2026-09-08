package logisticspipes.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import org.jspecify.annotations.Nullable;

import logisticspipes.world.level.block.entity.LPBlockEntityTypes;
import logisticspipes.world.level.block.entity.LogisticsProgramCompilerBlockEntity;

public class LogisticsProgramCompilerBlock extends LogisticsSolidBlock implements EntityBlock {

    public LogisticsProgramCompilerBlock(Properties properties) {
        super(properties);
    }

    @Override
    public String textureName() {
        return "program_compiler";
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LogisticsProgramCompilerBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
        BlockEntityType<T> type) {
        return BaseEntityBlock.createTickerHelper(type, LPBlockEntityTypes.PROGRAM_COMPILER.get(),
            (_, _, _, be) -> {
                if (level.isClientSide()) {
                    be.clientTick();
                } else {
                    be.serverTick();
                }
            });
    }
}

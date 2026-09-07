package logisticspipes.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

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


}

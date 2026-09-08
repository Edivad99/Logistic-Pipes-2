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
import logisticspipes.world.level.block.entity.LogisticsCraftingTableBlockEntity;

/**
 * The autocrafting table, in its plain and fuzzy flavours.
 *
 * <p>Both run the same block entity and differ only in the texture, so the flavour is a flag rather
 * than a second class.
 */
public class LogisticsCraftingTableBlock extends LogisticsSolidBlock {

    private final boolean fuzzy;

    public LogisticsCraftingTableBlock(boolean fuzzy, Properties properties) {
        super(properties);
        this.fuzzy = fuzzy;
    }

    @Override
    public String textureName() {
        return fuzzy ? "crafting_table_fuzzy" : "crafting_table";
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new LogisticsCraftingTableBlockEntity(pos, state);
    }

    // Its block entity adds nothing to the base tick, which only asks the server for the rotation.
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
        BlockEntityType<T> type) {
        return level.isClientSide()
            ? BaseEntityBlock.createTickerHelper(type, LPBlockEntityTypes.CRAFTING_TABLE.get(),
                (_, _, _, be) -> be.clientTick())
            : null;
    }
}

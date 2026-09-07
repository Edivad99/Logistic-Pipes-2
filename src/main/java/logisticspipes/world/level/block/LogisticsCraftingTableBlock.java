package logisticspipes.world.level.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

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
}

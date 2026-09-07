package logisticspipes.world.inventory;

import net.minecraft.world.entity.player.Inventory;

import lombok.Getter;

import logisticspipes.world.level.block.entity.LogisticsStatisticsBlockEntity;

/**
 * The statistics table has no slots of its own -- not even the player's inventory -- but still
 * needs a menu, so the screen has something to be attached to.
 */
public class StatisticsMenu extends DummyMenu {

    @Getter
    private final LogisticsStatisticsBlockEntity blockEntity;

    public StatisticsMenu(int containerId, Inventory inventory, LogisticsStatisticsBlockEntity blockEntity) {
        super(LPMenuTypes.STATISTICS.get(), containerId, inventory.player, blockEntity);
        this.blockEntity = blockEntity;
    }
}

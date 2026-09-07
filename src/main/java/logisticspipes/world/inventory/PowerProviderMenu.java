package logisticspipes.world.inventory;

import net.minecraft.world.entity.player.Inventory;

import lombok.Getter;

import logisticspipes.world.level.block.entity.LogisticsPowerProviderBlockEntity;

public class PowerProviderMenu extends DummyMenu {

    @Getter
    private final LogisticsPowerProviderBlockEntity blockEntity;

    public PowerProviderMenu(int containerId, Inventory inventory, LogisticsPowerProviderBlockEntity blockEntity) {
        super(LPMenuTypes.POWER_PROVIDER.get(), containerId, inventory.player, blockEntity);
        this.blockEntity = blockEntity;
        addNormalSlotsForPlayerInventory(inventory, 8, 80);
    }
}

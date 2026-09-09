package logisticspipes.api;

import net.minecraft.world.item.ItemStack;

/** A helmet that shows the Logistics Pipes head-up display while it is worn. */
public interface IHUDArmor {

    /** Whether the HUD should be drawn for this particular helmet, which the player can toggle. */
    boolean isEnabled(ItemStack item);
}

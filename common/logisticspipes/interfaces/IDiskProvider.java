package logisticspipes.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

import org.jspecify.annotations.Nullable;

import logisticspipes.utils.gui.ItemDisplay;

public interface IDiskProvider {

	ItemStack getDisk();

	BlockPos getBlockPos();

	@Nullable
	ItemDisplay getItemDisplay();
}

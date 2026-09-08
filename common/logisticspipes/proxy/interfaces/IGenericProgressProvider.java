package logisticspipes.proxy.interfaces;

import net.minecraft.world.level.block.entity.BlockEntity;

public interface IGenericProgressProvider {

	boolean isType(BlockEntity blockEntity);

	byte getProgress(BlockEntity blockEntity);
}

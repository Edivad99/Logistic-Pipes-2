package logisticspipes.proxy.progressprovider;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.level.block.entity.BlockEntity;

import logisticspipes.api.IGenericProgressProvider;
import logisticspipes.api.IProgressProvider;

public class MachineProgressProvider {

	private final List<IGenericProgressProvider> providers = new ArrayList<>();

	public void registerProgressProvider(IGenericProgressProvider provider) {
		providers.add(provider);
	}

	public byte getProgressForBlockEntity(BlockEntity blockEntity) {
		if (blockEntity instanceof IProgressProvider provider) {
			return provider.getMachineProgressForLP();
		}
		for (IGenericProgressProvider provider : providers) {
			if (provider.isType(blockEntity)) {
				return provider.getProgress(blockEntity);
			}
		}
		return 0;
	}
}

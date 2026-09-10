package logisticspipes.renderer;

import org.jspecify.annotations.Nullable;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public interface IIconProvider {

	@Nullable
	TextureAtlasSprite getIcon(int iconIndex);

	void registerIcons(Object textureMap);
}

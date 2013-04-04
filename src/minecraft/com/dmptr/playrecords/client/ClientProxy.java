package com.dmptr.playrecords.client;

import net.minecraftforge.client.MinecraftForgeClient;

import com.dmptr.playrecords.CommonProxy;

public class ClientProxy extends CommonProxy {
	@Override
	public void registerRenderers() {
		// Preload textures.
		MinecraftForgeClient.preloadTexture(ITEMS_PNG);
	}
}

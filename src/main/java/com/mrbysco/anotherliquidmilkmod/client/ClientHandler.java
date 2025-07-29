package com.mrbysco.anotherliquidmilkmod.client;

import com.mrbysco.anotherliquidmilkmod.registry.MilkRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(MilkRegistry.MILK.get(), ChunkSectionLayer.TRANSLUCENT);
		ItemBlockRenderTypes.setRenderLayer(MilkRegistry.FLOWING_MILK.get(), ChunkSectionLayer.TRANSLUCENT);
	}
}

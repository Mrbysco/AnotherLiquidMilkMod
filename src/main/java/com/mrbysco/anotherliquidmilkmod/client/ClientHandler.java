package com.mrbysco.anotherliquidmilkmod.client;

import com.mrbysco.anotherliquidmilkmod.registry.MilkRegistry;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		ItemBlockRenderTypes.setRenderLayer(MilkRegistry.MILK.get(), RenderType.translucent());
		ItemBlockRenderTypes.setRenderLayer(MilkRegistry.FLOWING_MILK.get(), RenderType.translucent());
	}
}

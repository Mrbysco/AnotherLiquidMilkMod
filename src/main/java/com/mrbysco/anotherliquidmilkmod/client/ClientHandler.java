package com.mrbysco.anotherliquidmilkmod.client;

import com.mrbysco.anotherliquidmilkmod.registry.MilkRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientHandler {
	public static void onClientSetup(final FMLClientSetupEvent event) {
		RenderTypeLookup.setRenderLayer(MilkRegistry.MILK.get(), RenderType.translucent());
		RenderTypeLookup.setRenderLayer(MilkRegistry.FLOWING_MILK.get(), RenderType.translucent());
	}
}

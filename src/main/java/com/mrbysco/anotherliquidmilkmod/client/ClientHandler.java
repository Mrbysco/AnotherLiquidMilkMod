package com.mrbysco.anotherliquidmilkmod.client;

import com.mrbysco.anotherliquidmilkmod.registry.MilkRegistry;
import net.minecraft.client.renderer.block.FluidModel;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterFluidModelsEvent;

@EventBusSubscriber(Dist.CLIENT)
public class ClientHandler {

	@SubscribeEvent
	public static void registerFluidModels(final RegisterFluidModelsEvent event) {
		Fluid stillFluid = MilkRegistry.MILK.value();
		Fluid flowingFluid = MilkRegistry.FLOWING_MILK.value();
		event.register(new FluidModel.Unbaked(
				new Material(neoForgeId("block/milk_still")),
				new Material(neoForgeId("block/milk_flowing")),
				null,
				null), stillFluid, flowingFluid);
	}

	private static Identifier neoForgeId(String path) {
		return Identifier.fromNamespaceAndPath("neoforge", path);
	}
}

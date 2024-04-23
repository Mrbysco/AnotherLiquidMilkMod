package com.mrbysco.anotherliquidmilkmod;

import com.mrbysco.anotherliquidmilkmod.client.ClientHandler;
import com.mrbysco.anotherliquidmilkmod.config.MilkConfig;
import com.mrbysco.anotherliquidmilkmod.handler.MilkHandler;
import com.mrbysco.anotherliquidmilkmod.registry.MilkRegistry;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.NeoForgeMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AnotherLiquidMilkMod.MOD_ID)
public class AnotherLiquidMilkMod {
	public static final String MOD_ID = "almm";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


	public AnotherLiquidMilkMod(IEventBus eventBus, Dist dist, ModContainer container) {
		NeoForgeMod.enableMilkFluid(); //Enable milk from forge

		container.registerConfig(Type.COMMON, MilkConfig.commonSpec, "anotherliquidmilkmod.toml");
		eventBus.register(MilkConfig.class);

		MilkRegistry.FLUIDS.register(eventBus);
		MilkRegistry.BLOCKS.register(eventBus);
//		MilkRegistry.FLUID_TYPES.register(eventBus);

		NeoForge.EVENT_BUS.addListener(MilkHandler::onRightClick);

		if (dist.isClient()) {
			eventBus.addListener(ClientHandler::onClientSetup);
		}
	}
}

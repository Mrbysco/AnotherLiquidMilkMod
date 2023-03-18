package com.mrbysco.anotherliquidmilkmod;

import com.mrbysco.anotherliquidmilkmod.client.ClientHandler;
import com.mrbysco.anotherliquidmilkmod.config.MilkConfig;
import com.mrbysco.anotherliquidmilkmod.handler.MilkHandler;
import com.mrbysco.anotherliquidmilkmod.registry.MilkRegistry;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(AnotherLiquidMilkMod.MOD_ID)
public class AnotherLiquidMilkMod {
	public static final String MOD_ID = "almm";
	public static final Logger LOGGER = LogManager.getLogger(MOD_ID);


	public AnotherLiquidMilkMod() {
		ForgeMod.enableMilkFluid(); //Enable milk from forge

		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, MilkConfig.commonSpec, "anotherliquidmilkmod.toml");
		eventBus.register(MilkConfig.class);

		MilkRegistry.FLUIDS.register(eventBus);
		MilkRegistry.BLOCKS.register(eventBus);

		MinecraftForge.EVENT_BUS.addListener(EventPriority.LOWEST, MilkHandler::onRightClick);

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			eventBus.addListener(ClientHandler::onClientSetup);
		});
	}
}

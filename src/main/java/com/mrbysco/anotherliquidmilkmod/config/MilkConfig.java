package com.mrbysco.anotherliquidmilkmod.config;

import com.mrbysco.anotherliquidmilkmod.AnotherLiquidMilkMod;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

public class MilkConfig {
	public static class Common {
		public final BooleanValue liquidCuresEffects;

		Common(ForgeConfigSpec.Builder builder) {
			builder.comment("General settings")
					.push("General");


			liquidCuresEffects = builder
					.comment("Makes the liquid milk cure effects [default: true]")
					.define("liquidCuresEffects", true);

			builder.pop();
		}
	}

	public static final ForgeConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final ModConfigEvent.Loading configEvent) {
		AnotherLiquidMilkMod.LOGGER.debug("Loaded Another Liquid Milk Mod's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final ModConfigEvent.Reloading configEvent) {
		AnotherLiquidMilkMod.LOGGER.debug("Another Liquid Milk Mod's config just got changed on the file system!");
	}
}

package com.mrbysco.anotherliquidmilkmod.config;

import com.mrbysco.anotherliquidmilkmod.AnotherLiquidMilkMod;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.event.config.ModConfigEvent.Loading;
import net.neoforged.fml.event.config.ModConfigEvent.Reloading;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import org.apache.commons.lang3.tuple.Pair;

public class MilkConfig {
	public static class Common {
		public final BooleanValue liquidCuresEffects;

		Common(Builder builder) {
			builder.comment("General settings")
					.push("General");


			liquidCuresEffects = builder
					.comment("Makes the liquid milk cure effects [default: true]")
					.define("liquidCuresEffects", true);

			builder.pop();
		}
	}

	public static final ModConfigSpec commonSpec;
	public static final Common COMMON;

	static {
		final Pair<Common, ModConfigSpec> specPair = new Builder().configure(Common::new);
		commonSpec = specPair.getRight();
		COMMON = specPair.getLeft();
	}

	@SubscribeEvent
	public static void onLoad(final Loading configEvent) {
		AnotherLiquidMilkMod.LOGGER.debug("Loaded Another Liquid Milk Mod's config file {}", configEvent.getConfig().getFileName());
	}

	@SubscribeEvent
	public static void onFileChange(final Reloading configEvent) {
		AnotherLiquidMilkMod.LOGGER.debug("Another Liquid Milk Mod's config just got changed on the file system!");
	}
}

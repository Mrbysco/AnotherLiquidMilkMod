package com.mrbysco.anotherliquidmilkmod.data;

import com.mrbysco.anotherliquidmilkmod.AnotherLiquidMilkMod;
import com.mrbysco.anotherliquidmilkmod.registry.MilkRegistry;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class MilkDatagen {
	@SubscribeEvent
	public static void gatherData(GatherDataEvent.Client event) {
		DataGenerator generator = event.getGenerator();
		PackOutput packOutput = generator.getPackOutput();

		generator.addProvider(true, new MilkLanguage(packOutput));
		generator.addProvider(true, new MilkModels(packOutput));
	}

	private static class MilkLanguage extends LanguageProvider {
		public MilkLanguage(PackOutput packOutput) {
			super(packOutput, AnotherLiquidMilkMod.MOD_ID, "en_us");
		}

		@Override
		protected void addTranslations() {
			addConfig("General", "General", "General Settings");
			addConfig("liquidCuresEffects", "Liquid Cures Effects", "Makes the liquid milk cure effects [default: true]");
		}

		/**
		 * Add the translation for a config entry
		 *
		 * @param path        The path of the config entry
		 * @param name        The name of the config entry
		 * @param description The description of the config entry (optional in case of targeting "title" or similar entries that have no tooltip)
		 */
		private void addConfig(String path, String name, @Nullable String description) {
			this.add("almm.configuration." + path, name);
			if (description != null && !description.isEmpty())
				this.add("almm.configuration." + path + ".tooltip", description);
		}
	}

	private static class MilkModels extends ModelProvider {
		public MilkModels(PackOutput packOutput) {
			super(packOutput, AnotherLiquidMilkMod.MOD_ID);
		}

		@Override
		protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
			blockModels.createNonTemplateModelBlock(MilkRegistry.MILK_FLUID_BLOCK.get());
		}
	}
}

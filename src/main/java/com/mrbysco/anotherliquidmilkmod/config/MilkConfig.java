package com.mrbysco.anotherliquidmilkmod.config;

import com.mrbysco.anotherliquidmilkmod.AnotherLiquidMilkMod;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = AnotherLiquidMilkMod.MOD_ID, category = "", name = "AnotherLiquidMilkMod")
@Config.LangKey("almm.config.title")
public class MilkConfig {
    @Config.Comment({"General settings"})
    public static General general = new General();

    public static class General{
        @Config.Comment("Makes the liquid milk cure effects [default: true]")
        public boolean liquidCuresEffects = true;
    }

    @Mod.EventBusSubscriber(modid = AnotherLiquidMilkMod.MOD_ID)
    private static class EventHandler {

        @SubscribeEvent
        public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
            if (event.getModID().equals(AnotherLiquidMilkMod.MOD_ID)) {
                ConfigManager.sync(AnotherLiquidMilkMod.MOD_ID, Config.Type.INSTANCE);
            }
        }
    }
}

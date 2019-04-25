package com.mrbysco.anotherliquidmilkmod;

import com.mrbysco.anotherliquidmilkmod.config.MilkConfig;
import com.mrbysco.anotherliquidmilkmod.proxy.CommonProxy;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = AnotherLiquidMilkMod.MOD_ID,
        name = AnotherLiquidMilkMod.MOD_NAME,
        version = AnotherLiquidMilkMod.VERSION,
        acceptedMinecraftVersions = AnotherLiquidMilkMod.ACCEPTED_VERSIONS)
public class AnotherLiquidMilkMod
{
    public static final String MOD_ID = "almm";
    public static final String MOD_NAME = "Another Liquid Milk Mod";
    public static final String MOD_PREFIX = MOD_ID + ":";
    public static final String VERSION = "1.1";
    public static final String ACCEPTED_VERSIONS = "[1.12]";
    public static final String CLIENT_PROXY_CLASS = "com.mrbysco.anotherliquidmilkmod.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.mrbysco.anotherliquidmilkmod.proxy.ServerProxy";

    @Instance(AnotherLiquidMilkMod.MOD_ID)
    public static AnotherLiquidMilkMod instance;

    @SidedProxy(clientSide = AnotherLiquidMilkMod.CLIENT_PROXY_CLASS, serverSide = AnotherLiquidMilkMod.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    public static final Logger logger = LogManager.getLogger(AnotherLiquidMilkMod.MOD_ID);

    static {
        FluidRegistry.enableUniversalBucket();
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger.info("Registering config");
        MinecraftForge.EVENT_BUS.register(new MilkConfig());

        proxy.Preinit();
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        proxy.Init();
    }
}

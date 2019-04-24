package com.mrbysco.anotherliquidmilkmod.init;

import com.mrbysco.anotherliquidmilkmod.AnotherLiquidMilkMod;
import com.mrbysco.anotherliquidmilkmod.blocks.BlockLiquidMilk;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = AnotherLiquidMilkMod.MOD_ID)
public class MilkRegistry {
    public static final Set<Fluid> FLUIDS = new HashSet<>();
    public static final Set<IFluidBlock> FLUID_BLOCKS = new HashSet<>();

    public static final Fluid liquid_milk = createFluid("liquid_milk", true,
            fluid -> fluid.setDensity(1050),
            fluid -> new BlockLiquidMilk(fluid, new MaterialLiquid(MapColor.IRON)));

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        final IForgeRegistry<Block> registry = event.getRegistry();

        for (final IFluidBlock fluidBlock : FLUID_BLOCKS) {
            final Block block = (Block) fluidBlock;
            block.setRegistryName(AnotherLiquidMilkMod.MOD_ID, "fluid." + fluidBlock.getFluid().getName());
            System.out.println("APPLE: " + AnotherLiquidMilkMod.MOD_PREFIX + fluidBlock.getFluid().getUnlocalizedName());
            block.setUnlocalizedName(AnotherLiquidMilkMod.MOD_PREFIX + fluidBlock.getFluid().getUnlocalizedName());
            registry.register(block);
        }
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry<Item> registry = event.getRegistry();

        FluidRegistry.addBucketForFluid(liquid_milk);
    }

    private static <T extends Block & IFluidBlock> Fluid createFluid(final String name, final boolean hasFlowIcon, final Consumer<Fluid> fluidPropertyApplier, final Function<Fluid, T> blockFactory) {
        final String texturePrefix = AnotherLiquidMilkMod.MOD_PREFIX + "blocks/";

        final ResourceLocation still = new ResourceLocation(texturePrefix + name + "_still");
        final ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(texturePrefix + name + "_flow") : still;

        Fluid fluid = new Fluid(name, still, flowing);
        final boolean useOwnFluid = FluidRegistry.registerFluid(fluid);

        if (useOwnFluid) {
            fluidPropertyApplier.accept(fluid);
            FLUID_BLOCKS.add(blockFactory.apply(fluid));
        } else {
            fluid = FluidRegistry.getFluid(name);
        }

        FLUIDS.add(fluid);

        return fluid;
    }
}

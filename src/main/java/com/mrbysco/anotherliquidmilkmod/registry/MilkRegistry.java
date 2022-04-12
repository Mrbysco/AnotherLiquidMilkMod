package com.mrbysco.anotherliquidmilkmod.registry;

import com.mrbysco.anotherliquidmilkmod.AnotherLiquidMilkMod;
import com.mrbysco.anotherliquidmilkmod.block.FlowingMilkBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MilkRegistry {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, AnotherLiquidMilkMod.MOD_ID);

	public static final RegistryObject<ForgeFlowingFluid> MILK = FLUIDS.register(ForgeMod.MILK.getId().getPath(), () -> new ForgeFlowingFluid.Source(createProperties()));
	public static final RegistryObject<ForgeFlowingFluid> FLOWING_MILK = FLUIDS.register(ForgeMod.FLOWING_MILK.getId().getPath(), () -> new ForgeFlowingFluid.Flowing(createProperties()));

	public static final RegistryObject<LiquidBlock> MILK_FLUID_BLOCK = BLOCKS.register(ForgeMod.MILK.getId().getPath(), () ->
			new FlowingMilkBlock(MILK, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noDrops()));

	public static ForgeFlowingFluid.Properties createProperties() {
		FluidAttributes.Builder attributesBuilder = FluidAttributes.builder(new ResourceLocation("forge", "block/milk_still"), new ResourceLocation("forge", "block/milk_flowing")).density(1024).viscosity(1024);
		return new ForgeFlowingFluid.Properties(MILK, FLOWING_MILK, attributesBuilder).bucket(() -> Items.MILK_BUCKET).block(MILK_FLUID_BLOCK);
	}
}

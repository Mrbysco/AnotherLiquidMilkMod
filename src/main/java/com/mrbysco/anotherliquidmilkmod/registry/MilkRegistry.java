package com.mrbysco.anotherliquidmilkmod.registry;

import com.mrbysco.anotherliquidmilkmod.AnotherLiquidMilkMod;
import com.mrbysco.anotherliquidmilkmod.block.FlowingMilkBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid.Flowing;
import net.neoforged.neoforge.fluids.BaseFlowingFluid.Source;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MilkRegistry {

	public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks("minecraft");
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, AnotherLiquidMilkMod.MOD_ID);

	public static final DeferredHolder<Fluid, BaseFlowingFluid> MILK = FLUIDS.register(NeoForgeMod.MILK.getId().getPath(), () -> new Source(createProperties()));
	public static final DeferredHolder<Fluid, BaseFlowingFluid> FLOWING_MILK = FLUIDS.register(NeoForgeMod.FLOWING_MILK.getId().getPath(), () -> new Flowing(createProperties()));

	public static final DeferredBlock<LiquidBlock> MILK_FLUID_BLOCK = BLOCKS.registerBlock(NeoForgeMod.MILK.getId().getPath(), (properties) ->
			new FlowingMilkBlock(MILK.get(), properties.mapColor(MapColor.CLAY).replaceable().liquid().noCollision()
					.pushReaction(PushReaction.DESTROY).strength(100.0F).noLootTable()));

	public static BaseFlowingFluid.Properties createProperties() {
		return new BaseFlowingFluid.Properties(NeoForgeMod.MILK_TYPE, MILK, FLOWING_MILK)
				.bucket(() -> Items.MILK_BUCKET).block(MILK_FLUID_BLOCK);
	}
}

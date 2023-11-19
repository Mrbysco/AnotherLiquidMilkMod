package com.mrbysco.anotherliquidmilkmod.registry;

import com.mrbysco.anotherliquidmilkmod.AnotherLiquidMilkMod;
import com.mrbysco.anotherliquidmilkmod.block.FlowingMilkBlock;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.NeoForgeMod;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid.Flowing;
import net.neoforged.neoforge.fluids.BaseFlowingFluid.Source;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.fluids.FluidType.Properties;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class MilkRegistry {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.createBlocks("minecraft");
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(Registries.FLUID, AnotherLiquidMilkMod.MOD_ID);
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, "minecraft");

	public static final Supplier<FluidType> MILK_TYPE = FLUID_TYPES.register(NeoForgeMod.MILK.getId().getPath(), () -> new FluidType(createFluidTypeProperties()) {
		@Override
		public void initializeClient(Consumer<IClientFluidTypeExtensions> consumer) {
			consumer.accept(new IClientFluidTypeExtensions() {
				private static final ResourceLocation
						MILK_STILL = new ResourceLocation("forge", "block/milk_still"),
						MILK_FLOW = new ResourceLocation("forge", "block/milk_flowing");

				@Override
				public ResourceLocation getStillTexture() {
					return MILK_STILL;
				}

				@Override
				public ResourceLocation getFlowingTexture() {
					return MILK_FLOW;
				}

				@Override
				public int getTintColor() {
					return 0xFFFFFFFF;
				}
			});
		}
	});

	public static final Supplier<BaseFlowingFluid> MILK = FLUIDS.register(NeoForgeMod.MILK.getId().getPath(), () -> new Source(createProperties()));
	public static final Supplier<BaseFlowingFluid> FLOWING_MILK = FLUIDS.register(NeoForgeMod.FLOWING_MILK.getId().getPath(), () -> new Flowing(createProperties()));

	public static final Supplier<LiquidBlock> MILK_FLUID_BLOCK = BLOCKS.register(NeoForgeMod.MILK.getId().getPath(), () ->
			new FlowingMilkBlock(MILK, BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).replaceable().liquid().noCollission().pushReaction(PushReaction.DESTROY).strength(100.0F).noLootTable()));

	public static Properties createFluidTypeProperties() {
		return Properties.create()
				.canSwim(true)
				.canDrown(false)
				.pathType(BlockPathTypes.WATER)
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
				.sound(net.neoforged.neoforge.common.SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
				.rarity(Rarity.COMMON)
				.density(1024)
				.viscosity(1024)
				.lightLevel(0);
	}

	public static net.neoforged.neoforge.fluids.BaseFlowingFluid.Properties createProperties() {
		return new net.neoforged.neoforge.fluids.BaseFlowingFluid.Properties(MILK_TYPE, MILK, FLOWING_MILK).bucket(() -> Items.MILK_BUCKET).block(MILK_FLUID_BLOCK);
	}
}

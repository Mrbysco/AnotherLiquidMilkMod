package com.mrbysco.anotherliquidmilkmod.registry;

import com.mrbysco.anotherliquidmilkmod.AnotherLiquidMilkMod;
import com.mrbysco.anotherliquidmilkmod.block.FlowingMilkBlock;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.client.extensions.common.IClientFluidTypeExtensions;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Consumer;

public class MilkRegistry {

	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, "minecraft");
	public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, AnotherLiquidMilkMod.MOD_ID);
	public static final DeferredRegister<FluidType> FLUID_TYPES = DeferredRegister.create(ForgeRegistries.Keys.FLUID_TYPES, "minecraft");

	public static final RegistryObject<FluidType> MILK_TYPE = FLUID_TYPES.register(ForgeMod.MILK.getId().getPath(), () -> new FluidType(createFluidTypeProperties()) {
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

	public static final RegistryObject<ForgeFlowingFluid> MILK = FLUIDS.register(ForgeMod.MILK.getId().getPath(), () -> new ForgeFlowingFluid.Source(createProperties()));
	public static final RegistryObject<ForgeFlowingFluid> FLOWING_MILK = FLUIDS.register(ForgeMod.FLOWING_MILK.getId().getPath(), () -> new ForgeFlowingFluid.Flowing(createProperties()));

	public static final RegistryObject<LiquidBlock> MILK_FLUID_BLOCK = BLOCKS.register(ForgeMod.MILK.getId().getPath(), () ->
			new FlowingMilkBlock(MILK, Block.Properties.of(Material.WATER).noCollission().strength(100.0F).noLootTable()));

	public static FluidType.Properties createFluidTypeProperties() {
		return FluidType.Properties.create()
				.canSwim(true)
				.canDrown(false)
				.pathType(BlockPathTypes.WATER)
				.sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL)
				.sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY)
				.rarity(Rarity.COMMON)
				.density(1024)
				.viscosity(1024)
				.lightLevel(0);
	}

	public static ForgeFlowingFluid.Properties createProperties() {
		return new ForgeFlowingFluid.Properties(MILK_TYPE, MILK, FLOWING_MILK).bucket(() -> Items.MILK_BUCKET).block(MILK_FLUID_BLOCK);
	}
}

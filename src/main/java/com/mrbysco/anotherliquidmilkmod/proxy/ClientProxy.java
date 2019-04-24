package com.mrbysco.anotherliquidmilkmod.proxy;

import com.mrbysco.anotherliquidmilkmod.AnotherLiquidMilkMod;
import com.mrbysco.anotherliquidmilkmod.init.MilkRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy{

	@Override
	public void Preinit() {

	}

	@Override
	public void Init() {

	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		for(IFluidBlock fluid : MilkRegistry.FLUID_BLOCKS) {
			registerFluidModel(fluid);
		}
	}

	private static final String FLUID_MODEL_PATH = AnotherLiquidMilkMod.MOD_PREFIX + "fluid_block";

	private static void registerFluidModel(final IFluidBlock fluidBlock) {
		final Item item = Item.getItemFromBlock((Block) fluidBlock);
		assert item != Items.AIR;

		ModelBakery.registerItemVariants(item);

		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(FLUID_MODEL_PATH, fluidBlock.getFluid().getName());

		ModelLoader.setCustomMeshDefinition(item, stack -> modelResourceLocation);

		ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(final IBlockState state) {
				return modelResourceLocation;
			}
		});
	}
}

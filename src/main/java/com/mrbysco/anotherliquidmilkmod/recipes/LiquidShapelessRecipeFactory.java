package com.mrbysco.anotherliquidmilkmod.recipes;

import com.google.gson.JsonObject;
import com.mrbysco.anotherliquidmilkmod.AnotherLiquidMilkMod;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;

public class LiquidShapelessRecipeFactory implements IRecipeFactory{
	
    @Override
    public IRecipe parse( JsonContext context, JsonObject json )
    {
    	ShapelessOreRecipe recipe = ShapelessOreRecipe.factory(context, json);
    	return new BucketlessShapelessRecipe(new ResourceLocation(AnotherLiquidMilkMod.MOD_ID, "liquid_ore_bucket_shapeless"), recipe.getRecipeOutput(), recipe.getIngredients().toArray());
    }
    
    public static class BucketlessShapelessRecipe extends ShapelessOreRecipe{
    	
    	public BucketlessShapelessRecipe(ResourceLocation group, ItemStack result, Object[] recipe) {
			super(group, result, recipe);
		}

		@Override
    	@Nonnull
    	public NonNullList<ItemStack> getRemainingItems(InventoryCrafting inv) {
    		NonNullList<ItemStack> ret = super.getRemainingItems(inv);
    		for (int i = 0; i < ret.size(); i++) {
    			if (ret.get(i).getItem() == Items.BUCKET || ret.get(i).getItem() == Items.GLASS_BOTTLE) {
    				ret.set(i, ItemStack.EMPTY);
    				break;
    			}
    		}
    		return ret;
    	}
    }
}

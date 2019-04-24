package com.mrbysco.anotherliquidmilkmod.blocks;

import com.mrbysco.anotherliquidmilkmod.config.MilkConfig;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

import javax.annotation.Nonnull;

public class BlockLiquidMilk  extends BlockFluidClassic {

    public BlockLiquidMilk(Fluid fluid, Material material) {
        super(fluid, material);
    }

    @Nonnull
    @Override
    public String getUnlocalizedName() {
        Fluid fluid = FluidRegistry.getFluid(fluidName);
        if(fluid != null) {
            return fluid.getUnlocalizedName();
        }
        return super.getUnlocalizedName();
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
        if(!worldIn.isRemote) {
            if(MilkConfig.general.liquidCuresEffects) {
                if (entityIn instanceof EntityLivingBase)
                {
                    EntityLivingBase entity = (EntityLivingBase) entityIn;
                    if(!entity.getActivePotionEffects().isEmpty()) {
                        entity.clearActivePotions();
                    }
                }
            }
        }
    }
}
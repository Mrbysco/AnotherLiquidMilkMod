package com.mrbysco.anotherliquidmilkmod.block;

import com.mrbysco.anotherliquidmilkmod.config.MilkConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Supplier;

public class FlowingMilkBlock extends FlowingFluidBlock {
	public FlowingMilkBlock(Supplier<? extends FlowingFluid> supplier, Properties properties) {
		super(supplier, properties);
	}

	@Override
	public void entityInside(BlockState state, World world, BlockPos pos, Entity entity) {
		super.entityInside(state, world, pos, entity);
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			if (MilkConfig.COMMON.liquidCuresEffects.get()) {
				if (!livingEntity.getActiveEffects().isEmpty()) {
					livingEntity.removeAllEffects();
				}
			}
		}
	}
}

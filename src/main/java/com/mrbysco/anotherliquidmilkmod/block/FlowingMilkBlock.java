package com.mrbysco.anotherliquidmilkmod.block;

import com.mrbysco.anotherliquidmilkmod.config.MilkConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

public class FlowingMilkBlock extends LiquidBlock {
	public FlowingMilkBlock(FlowingFluid fluid, Properties properties) {
		super(fluid, properties);
	}

	@Override
	public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
		if (entity instanceof LivingEntity livingEntity && MilkConfig.COMMON.liquidCuresEffects.get()) {
			if (!livingEntity.getActiveEffects().isEmpty()) {
				livingEntity.removeAllEffects();
			}
		}
	}
}

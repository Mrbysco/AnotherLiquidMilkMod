package com.mrbysco.anotherliquidmilkmod.handler;

import com.mrbysco.anotherliquidmilkmod.registry.MilkRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.attribute.EnvironmentAttributes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import org.jspecify.annotations.Nullable;

@EventBusSubscriber
public class MilkHandler {
	@SubscribeEvent
	public static void onRightClick(PlayerInteractEvent.RightClickItem event) {
		final ItemStack itemstack = event.getItemStack();
		if (itemstack.is(Items.MILK_BUCKET)) {
			final Level level = event.getLevel();
			final Player player = event.getEntity();
			BlockHitResult hitResult = Item.getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);
			if (hitResult.getType() == HitResult.Type.MISS) {
				event.setCancellationResult(InteractionResult.PASS);
			} else if (hitResult.getType() != HitResult.Type.BLOCK) {
				event.setCancellationResult(InteractionResult.PASS);
			} else {
				BlockPos pos = hitResult.getBlockPos();
				Direction direction = hitResult.getDirection();
				BlockPos relativePos = pos.relative(direction);
				if (!level.mayInteract(player, pos) || !player.mayUseItemAt(relativePos, direction, itemstack)) {
					event.setCancellationResult(InteractionResult.FAIL);
				} else {
					BlockState blockstate = level.getBlockState(pos);
					BlockPos blockpos2 = canBlockContainFluid(player, level, pos, blockstate) ? pos : relativePos;
					if (emptyContents(player, level, blockpos2, hitResult, itemstack)) {
						if (player instanceof ServerPlayer) {
							CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, blockpos2, itemstack);
						}

						player.awardStat(Stats.ITEM_USED.get(itemstack.getItem()));
						ItemStack filledResult = ItemUtils.createFilledResult(itemstack, player, BucketItem.getEmptySuccessItem(itemstack, player));
						itemstack.consume(1, player);
						if (itemstack.isEmpty()) {
							player.setItemInHand(event.getHand(), filledResult);
						}
						event.setCancellationResult(InteractionResult.SUCCESS);
					} else {
						event.setCancellationResult(InteractionResult.FAIL);
					}
				}
			}
		}
	}

	private static boolean emptyContents(@Nullable LivingEntity livingEntity, Level level, BlockPos pos,
	                                     @Nullable BlockHitResult hitResult, @Nullable ItemStack container) {
		Fluid content = MilkRegistry.MILK.get();
		if (!(content instanceof FlowingFluid flowingFluid)) {
			return false;
		} else {
			BlockState blockstate = level.getBlockState(pos);
			Block block = blockstate.getBlock();
			boolean canBeReplaced = blockstate.canBeReplaced(content);
			boolean flag1 = livingEntity != null && livingEntity.isShiftKeyDown();
			boolean flag2 = canBeReplaced
					|| block instanceof LiquidBlockContainer liquidBlockContainer
					&& liquidBlockContainer.canPlaceLiquid(livingEntity, level, pos, blockstate, content);
			var containedFluidStack = container != null ? net.neoforged.neoforge.transfer.fluid.FluidUtil.getFirstStackContained(container) : net.neoforged.neoforge.fluids.FluidStack.EMPTY;
			boolean flag3 = blockstate.isAir() || flag2 && (!flag1 || hitResult == null);
			if (!flag3) {
				return hitResult != null && emptyContents(livingEntity, level, hitResult.getBlockPos().relative(hitResult.getDirection()), null, container);
			} else if (!containedFluidStack.isEmpty() && content.getFluidType().isVaporizedOnPlacement(level, pos, containedFluidStack)) {
				content.getFluidType().onVaporize(livingEntity, level, pos, containedFluidStack);
				return true;
			} else if (level.environmentAttributes().getValue(EnvironmentAttributes.WATER_EVAPORATES, pos) && content.is(FluidTags.WATER)) {
				int l = pos.getX();
				int i = pos.getY();
				int j = pos.getZ();
				level.playSound(
						livingEntity,
						pos,
						SoundEvents.FIRE_EXTINGUISH,
						SoundSource.BLOCKS,
						0.5F,
						2.6F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.8F
				);

				for (int k = 0; k < 8; k++) {
					level.addParticle(
							ParticleTypes.LARGE_SMOKE,
							l + level.getRandom().nextFloat(),
							i + level.getRandom().nextFloat(),
							j + level.getRandom().nextFloat(),
							0.0,
							0.0,
							0.0
					);
				}

				return true;
			} else if (block instanceof LiquidBlockContainer placeLiquid && placeLiquid.canPlaceLiquid(livingEntity, level, pos, blockstate, content)) {
				placeLiquid.placeLiquid(level, pos, blockstate, flowingFluid.getSource(false));
				playEmptySound(livingEntity, level, pos);
				return true;
			} else {
				if (!level.isClientSide() && canBeReplaced && !blockstate.liquid()) {
					level.destroyBlock(pos, true);
				}

				if (!level.setBlock(pos, content.defaultFluidState().createLegacyBlock(), 11) && !blockstate.getFluidState().isSource()) {
					return false;
				} else {
					playEmptySound(livingEntity, level, pos);
					return true;
				}
			}
		}
	}

	protected static void playEmptySound(@Nullable LivingEntity entity, LevelAccessor level, BlockPos pos) {
		Fluid content = MilkRegistry.MILK.get();
		SoundEvent soundevent = content.getFluidType().getSound(entity, level, pos, net.neoforged.neoforge.common.SoundActions.BUCKET_EMPTY);
		if (soundevent == null)
			soundevent = content.is(FluidTags.LAVA) ? SoundEvents.BUCKET_EMPTY_LAVA : SoundEvents.BUCKET_EMPTY;
		level.playSound(entity, pos, soundevent, SoundSource.BLOCKS, 1.0F, 1.0F);
		level.gameEvent(entity, GameEvent.FLUID_PLACE, pos);
	}

	protected static boolean canBlockContainFluid(@Nullable Player player, Level level, BlockPos posIn, BlockState blockstate) {
		return blockstate.getBlock() instanceof LiquidBlockContainer liquidBlockContainer &&
				liquidBlockContainer.canPlaceLiquid(player, level, posIn, blockstate, MilkRegistry.MILK.get());
	}
}

package com.mrbysco.anotherliquidmilkmod.handler;

import com.mrbysco.anotherliquidmilkmod.registry.MilkRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

public class MilkHandler {
	public static void onRightClick(RightClickBlock event) {
		final ItemStack itemstack = event.getItemStack();
		if (itemstack.getItem() instanceof MilkBucketItem milkBucketItem) {
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
				if (level.mayInteract(player, pos) && player.mayUseItemAt(relativePos, direction, itemstack)) {
					if (player instanceof ServerPlayer) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayer) player, relativePos, itemstack);
					}

					player.awardStat(Stats.ITEM_USED.get(milkBucketItem));
					if (!player.getAbilities().instabuild) {
						itemstack.shrink(1);
						ItemStack bucketStack = new ItemStack(Items.BUCKET);
						if (!player.addItem(bucketStack)) {
							Containers.dropItemStack(player.level(), player.getX(), player.getY(), player.getZ(), bucketStack);
						}
					}
					level.setBlock(relativePos, MilkRegistry.MILK.get().defaultFluidState().createLegacyBlock(), 11);
					level.playSound(null, relativePos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
					event.setCancellationResult(InteractionResult.SUCCESS);
				}
			}
		}
	}
}

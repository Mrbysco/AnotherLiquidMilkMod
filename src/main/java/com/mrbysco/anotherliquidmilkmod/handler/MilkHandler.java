package com.mrbysco.anotherliquidmilkmod.handler;

import com.mrbysco.anotherliquidmilkmod.registry.MilkRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.MilkBucketItem;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

public class MilkHandler {
	public static void onRightClick(RightClickBlock event) {
		ItemStack itemstack = event.getItemStack();
		if (itemstack.getItem() instanceof MilkBucketItem) {
			MilkBucketItem milkBucketItem = (MilkBucketItem) itemstack.getItem();
			World world = event.getWorld();
			PlayerEntity player = event.getPlayer();
			BlockRayTraceResult blockRayTraceResult = Item.getPlayerPOVHitResult(world, player, RayTraceContext.FluidMode.NONE);
			if (blockRayTraceResult.getType() == RayTraceResult.Type.MISS) {
				event.setCancellationResult(ActionResultType.PASS);
			} else if (blockRayTraceResult.getType() != RayTraceResult.Type.BLOCK) {
				event.setCancellationResult(ActionResultType.PASS);
			} else {
				BlockPos blockpos = blockRayTraceResult.getBlockPos();
				Direction direction = blockRayTraceResult.getDirection();
				BlockPos blockpos1 = blockpos.relative(direction);
				if (world.mayInteract(player, blockpos) && player.mayUseItemAt(blockpos1, direction, itemstack)) {
					if (player instanceof ServerPlayerEntity) {
						CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, blockpos1, itemstack);
					}

					player.awardStat(Stats.ITEM_USED.get(milkBucketItem));
					if (!player.abilities.instabuild) {
						itemstack.shrink(1);
						ItemStack bucketStack = new ItemStack(Items.BUCKET);
						if (!player.addItem(bucketStack)) {
							InventoryHelper.dropItemStack(player.level, player.getX(), player.getY(), player.getZ(), bucketStack);
						}
					}
					world.setBlock(blockpos1, MilkRegistry.MILK.get().defaultFluidState().createLegacyBlock(), 11);
					world.playSound(null, blockpos1, SoundEvents.BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
					event.setCancellationResult(ActionResultType.SUCCESS);
				}
			}
		}
	}
}

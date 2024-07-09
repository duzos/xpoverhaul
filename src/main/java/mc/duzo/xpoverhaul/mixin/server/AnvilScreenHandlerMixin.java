package mc.duzo.xpoverhaul.mixin.server;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.ForgingSlotsManager;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreenHandler.class)
public abstract class AnvilScreenHandlerMixin extends ForgingScreenHandler {
	@Final
	@Shadow
	private Property levelCost;

	private AnvilScreenHandlerMixin(@Nullable ScreenHandlerType<?> type, int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
		super(type, syncId, playerInventory, context);
	}

	@Shadow protected abstract ForgingSlotsManager getForgingSlotsManager();

	@Inject(method = "updateResult", at = @At("TAIL"))
	private void xpoverhaul$updateResult(CallbackInfo ci) {
		this.levelCost.set(0);

		// Cancel all book enchants
		if (this.input.getStack(1).isOf(Items.ENCHANTED_BOOK)) {
			this.output.setStack(0, ItemStack.EMPTY);
		}
	}
	@Inject(method = "getLevelCost", at = @At("RETURN"), cancellable = true)
	private void xpoverhaul$getLevelCost(CallbackInfoReturnable<Integer> cir) {
		cir.setReturnValue(0);
	}

	@Inject(method = "canTakeOutput", at = @At("HEAD"), cancellable = true)
	private void xpoverhaul$canTakeOutput(PlayerEntity player, boolean present, CallbackInfoReturnable<Boolean> cir) {
		cir.setReturnValue(true);
	}
}

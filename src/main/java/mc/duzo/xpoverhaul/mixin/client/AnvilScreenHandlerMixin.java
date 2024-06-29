package mc.duzo.xpoverhaul.mixin.client;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
	@Final
	@Shadow
	private Property levelCost;

	@Inject(method = "updateResult", at = @At("TAIL"))
	private void xpoverhaul$updateResult(CallbackInfo ci) {
		this.levelCost.set(0);
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

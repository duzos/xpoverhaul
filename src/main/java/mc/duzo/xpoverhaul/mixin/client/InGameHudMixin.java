package mc.duzo.xpoverhaul.mixin.client;

import mc.duzo.xpoverhaul.client.EnchantingOverhaulClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
	private void xpoverhaul$renderExperienceBar(DrawContext context, int x, CallbackInfo ci) {
		ci.cancel(); // todo override ClientPlayerInteractionManager#hasExperienceBar instead
	}

	@Inject(method = "renderStatusBars", at = @At("HEAD"))
	private void xpoverhaul$renderStatusBarsHead(DrawContext context, CallbackInfo ci) {
		if (EnchantingOverhaulClient.SHOULD_MOVE_HEARTS) {
			context.getMatrices().push();
			context.getMatrices().translate(0, -9, 0);
		}
	}
	@Inject(method = "renderStatusBars", at = @At("TAIL"))
	private void xpoverhaul$renderStatusBarsTail(DrawContext context, CallbackInfo ci) {
		if (EnchantingOverhaulClient.SHOULD_MOVE_HEARTS) {
			context.getMatrices().pop();
		}
	}
}

package mc.duzo.xpoverhaul.mixin.client;

import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DeathScreen.class)
public class DeathScreenMixin {
	@Shadow private Text scoreText = Text.literal("");

	@Inject(method = "<init>", at = @At("TAIL"))
	private void xpoverhaul$init(CallbackInfo ci) {
		this.scoreText = Text.literal("");
	}

	@Inject(method = "render", at = @At("HEAD"))
	private void xpoverhaul$render(CallbackInfo ci) {
		this.scoreText = Text.literal("");
	}
}

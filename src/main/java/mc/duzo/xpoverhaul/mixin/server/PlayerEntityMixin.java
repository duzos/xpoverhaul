package mc.duzo.xpoverhaul.mixin.server;

import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

	@Inject(method = "tick", at = @At("TAIL"))
	private void xpoverhaul$tick(CallbackInfo ci) {
		((PlayerEntity) (Object) this).experienceLevel = 9999;
	}
}

package mc.duzo.xpoverhaul.mixin.server;

import net.minecraft.entity.ExperienceOrbEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbMixin {
	@Inject(method = "tick", at = @At("HEAD"), cancellable = true)
	private void xpoverhaul$tick(CallbackInfo ci) {
		((ExperienceOrbEntity) (Object) this).discard(); // insta-kill all XP orbs
	}
}

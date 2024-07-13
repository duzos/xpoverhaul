package mc.duzo.xpoverhaul.mixin.server;

import mc.duzo.xpoverhaul.util.damage.DamageChanger;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin implements DamageChanger { // removed
	@Unique
	public int defaultDamage = -1;
	@Unique
	public int targetDamage = -1;

	@Override
	public int xpoverhaul$getMaxDamage() {
		return targetDamage;
	}

	@Override
	public void xpoverhaul$setMaxDamage(int target) {
		targetDamage = target;
	}

	@Override
	public int xpoverhaul$getDefaultMaxDamage() {
		if (defaultDamage == -1) {
			((Item) (Object) this).getMaxDamage();
		}

		return defaultDamage;
	}

	@Override
	public void xpoverhaul$setDefaultMaxDamage(int target) {
		defaultDamage = target;
	}

	@Inject(method = "getMaxDamage", at = @At("RETURN"), cancellable = true)
	private void xpoverhaul$getMaxDamage(CallbackInfoReturnable<Integer> cir) {
		if (this.defaultDamage == -1) {
			((DamageChanger) this).xpoverhaul$setDefaultMaxDamage(cir.getReturnValue());
		}

		int target = ((DamageChanger) this).xpoverhaul$getMaxDamage();
		if (target == -1) return;
		cir.setReturnValue(target);
	}

	@Inject(method = "writeNbt", at = @At("HEAD"))
	private void xpoverhaul$writeNbt(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
		nbt.putInt("XpOverhaulDamage", this.targetDamage);
	}
	@Inject(method = "<init>(Lnet/minecraft/nbt/NbtCompound;)V", at = @At("HEAD"))
	private void xpoverhaul$nbtInit(NbtCompound nbt, CallbackInfo ci) {
		if (nbt.contains("XpOverhaulDamage")) {
			((DamageChanger) this).xpoverhaul$setMaxDamage(nbt.getInt("XpOverhaulDamage"));
		}
	}
}

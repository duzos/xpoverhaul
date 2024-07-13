package mc.duzo.xpoverhaul.mixin.server;

import mc.duzo.xpoverhaul.util.removed.RemovedItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(ItemGroup.class)
public class ItemGroupMixin {
	@Shadow private Collection<ItemStack> displayStacks;

	@Inject(method = "getDisplayStacks", at = @At("HEAD"))
	private void xpoverhaul$getDisplayStacks(CallbackInfoReturnable<Collection<ItemStack>> cir) {
		this.displayStacks.removeIf(RemovedItems::isRemoved);
	}
}

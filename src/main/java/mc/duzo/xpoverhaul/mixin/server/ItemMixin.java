package mc.duzo.xpoverhaul.mixin.server;

import mc.duzo.xpoverhaul.util.removed.RemovedItems;
import net.minecraft.entity.Entity;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Item.class)
public class ItemMixin {
	@Inject(method = "inventoryTick", at = @At("HEAD"), cancellable = true)
	private void xpoverhaul$inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected, CallbackInfo ci) {
		if (RemovedItems.isRemoved(stack)) { // todo could be costly.
			stack.setCount(0);
			ci.cancel();
		}

		if (RemovedItems.hasBannedEnchant(stack)) {
			RemovedItems.removeBannedEnchants(stack);
		}
	}
}

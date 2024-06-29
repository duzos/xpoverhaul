package mc.duzo.xpoverhaul.util.removed;

import net.minecraft.enchantment.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class RemovedItems {
	private static final List<Item> REMOVED_ITEMS = List.of(Items.EXPERIENCE_BOTTLE);

	public static boolean isRemoved(ItemStack stack) {
		return REMOVED_ITEMS.contains(stack.getItem());
	}

	public static boolean hasBannedEnchant(ItemStack stack) {
		Set<Enchantment> enchantments = EnchantmentHelper.get(stack).keySet();

		return enchantments.stream().anyMatch(RemovedItems::isBannedEnchant);
	}
	private static boolean isBannedEnchant(Enchantment enchant) {
		return enchant instanceof MendingEnchantment ||
				enchant instanceof BindingCurseEnchantment ||
				enchant instanceof VanishingCurseEnchantment;
	}

	public static void removeBannedEnchants(ItemStack stack) {
		Map<Enchantment, Integer> enchantments = EnchantmentHelper.get(stack);

		enchantments.keySet().removeIf(RemovedItems::isBannedEnchant);

		EnchantmentHelper.set(enchantments, stack);
	}
}

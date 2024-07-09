package mc.duzo.xpoverhaul.util.merge;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Map;
import java.util.Optional;

public class MergingItems {
	public static Optional<ItemStack> merge(ItemStack held, ItemStack target) {
		if (!shouldMerge(held, target)) return Optional.empty();

		Map<Enchantment, Integer> heldEnchants = cleanseList(EnchantmentHelper.get(held), target);

		if (heldEnchants.isEmpty()) return Optional.empty();

		Map<Enchantment, Integer> targetEnchants = EnchantmentHelper.get(target);

		targetEnchants.putAll(heldEnchants);
		EnchantmentHelper.set(targetEnchants, target);

		return Optional.of(target);
	}

	private static boolean shouldMerge(ItemStack held, ItemStack target) {
		return (held.isOf(target.getItem()) || held.isOf(Items.ENCHANTED_BOOK)) && !target.isEmpty();
	}

	/**
	 * Removes all enchantments that aren't compatible with the target
	 */
	private static Map<Enchantment, Integer> cleanseList(Map<Enchantment, Integer> enchantments, ItemStack target) {
		for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
			if (!entry.getKey().target.isAcceptableItem(target.getItem())) {
				enchantments.remove(entry.getKey());
			}
		}

		return enchantments;
	}
}

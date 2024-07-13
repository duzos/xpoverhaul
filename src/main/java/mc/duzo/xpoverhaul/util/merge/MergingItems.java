package mc.duzo.xpoverhaul.util.merge;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Map;
import java.util.Optional;

public class MergingItems {
	public static Optional<ItemStack> merge(ItemStack held, ItemStack target) {
		if (!shouldMerge(held, target)) return Optional.empty();

		if (!(target.isOf(Items.ENCHANTED_BOOK) && held.isOf(Items.ENCHANTED_BOOK))) {
			Map<Enchantment, Integer> heldEnchants = cleanseList(EnchantmentHelper.get(held), target);

			if (heldEnchants.isEmpty()) return Optional.empty();
		}

		return Optional.of(createMergedStack(held, target));
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

	private static ItemStack createMergedStack(ItemStack held, ItemStack target) {
		Map<Enchantment, Integer> map = EnchantmentHelper.get(target);
		Map<Enchantment, Integer> map2 = EnchantmentHelper.get(held);
		boolean bl22 = false;
		boolean bl3 = false;
		boolean bl = held.isOf(Items.ENCHANTED_BOOK) && !EnchantedBookItem.getEnchantmentNbt(held).isEmpty();
		for (Enchantment enchantment : map2.keySet()) {
			int r;
			if (enchantment == null) continue;
			int q = map.getOrDefault(enchantment, 0);
			r = q == (r = map2.get(enchantment).intValue()) ? r + 1 : Math.max(r, q);
			boolean bl4 = enchantment.isAcceptableItem(target);
			if (target.isOf(Items.ENCHANTED_BOOK)) {
				bl4 = true;
			}
			for (Enchantment enchantment2 : map.keySet()) {
				if (enchantment2 == enchantment || enchantment.canCombine(enchantment2)) continue;
				bl4 = false;
			}
			if (!bl4) {
				bl3 = true;
				continue;
			}
			bl22 = true;
			if (r > enchantment.getMaxLevel()) {
				r = enchantment.getMaxLevel();
			}
			map.put(enchantment, r);
			int s = 0;
			switch (enchantment.getRarity()) {
				case COMMON: {
					s = 1;
					break;
				}
				case UNCOMMON: {
					s = 2;
					break;
				}
				case RARE: {
					s = 4;
					break;
				}
				case VERY_RARE: {
					s = 8;
				}
			}
			if (bl) {
				s = Math.max(1, s / 2);
			}
		}

		EnchantmentHelper.set(map, target);

		return target;
	}
}

package net.smackplays.smacksutil.inventories;

import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;

public interface IEnchantmentToolInventory extends IInventoryBase {
    static IEnchantmentToolInventory of(NonNullList<ItemStack> items) {
        return () -> items;
    }

    static IEnchantmentToolInventory ofSize(int size) {
        return of(NonNullList.withSize(size, ItemStack.EMPTY));
    }
}
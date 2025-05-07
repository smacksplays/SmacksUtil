package net.smackplays.smacksutil.inventories;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;

public class EnchantmentToolInventory extends AInventoryBase {

    public EnchantmentToolInventory(ItemStack stack, RegistryAccess registryAccess, int inventorySize) {
        super(stack, registryAccess, inventorySize);
    }
}
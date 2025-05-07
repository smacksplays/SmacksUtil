package net.smackplays.smacksutil.inventories;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;

import static net.smackplays.smacksutil.Constants.Backpack.C_BACKPACK_COL_NUM;
import static net.smackplays.smacksutil.Constants.Backpack.C_BACKPACK_ROW_NUM;

public class BackpackInventory extends AbstractBackpackInventoryBase {
    public BackpackInventory(ItemStack stack, RegistryAccess registryAccess) {
        super(stack, registryAccess, C_BACKPACK_ROW_NUM * C_BACKPACK_COL_NUM + 4);
    }
}
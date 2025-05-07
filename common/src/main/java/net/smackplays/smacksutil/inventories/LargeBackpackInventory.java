package net.smackplays.smacksutil.inventories;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.ItemStack;

import static net.smackplays.smacksutil.Constants.Backpack.C_LARGE_BACKPACK_COL_NUM;
import static net.smackplays.smacksutil.Constants.Backpack.C_LARGE_BACKPACK_ROW_NUM;

public class LargeBackpackInventory extends AbstractBackpackInventoryBase {
    public LargeBackpackInventory(ItemStack stack, RegistryAccess registryAccess) {
        super(stack, registryAccess, C_LARGE_BACKPACK_ROW_NUM * C_LARGE_BACKPACK_COL_NUM + 4);
    }
}
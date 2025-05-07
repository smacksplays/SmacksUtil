package net.smackplays.smacksutil.inventories;

import net.minecraft.core.RegistryAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.platform.Services;

import java.util.ArrayList;

public class BackpackInventory extends AInventoryBase{
    public BackpackInventory(ItemStack stack, RegistryAccess registryAccess, int inventorySize) {
        super(stack, registryAccess, inventorySize);
    }

    public int getMaxStackSize() {
        int baseStackSize = 64;
        ArrayList<ItemStack> upgrades = new ArrayList<>(){{
            add(getItem(0));
            add(getItem(1));
            add(getItem(2));
            add(getItem(3));
        }};
        for (int i = 0; i < 4; i++){
            ItemStack upgrade = upgrades.get(i);
            if (!upgrade.isEmpty()){
                Item upgradeItem = upgrade.getItem();
                if (upgradeItem.equals(Services.PLATFORM.getUpgrade1Item())) {
                    baseStackSize *= 4;
                } else if (upgradeItem.equals(Services.PLATFORM.getUpgrade2Item())) {
                    baseStackSize *= 8;
                } else if (upgradeItem.equals(Services.PLATFORM.getUpgrade3Item())){
                    baseStackSize *= 16;
                }
            }
        }
        return baseStackSize;
    }

    public boolean checkRemoveUpgrade(int corrCount) {
        for (int i = 0; i < getItems().size(); i++){
            if (getItems().get(i).getCount() > corrCount){
                return false;
            }
        }
        return true;
    }
}

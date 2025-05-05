package net.smackplays.smacksutil.menus;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class BackpackMenu extends AbstractBackpackMenu {

    public BackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv);
    }

    @SuppressWarnings("unused")
    public static BackpackMenu createGeneric9x6(int syncId, Inventory playerInventory, Vector3f vec) {
        ItemStack backpack = playerInventory.getSelectedItem();

        NonNullList<Slot> slots = playerInventory.player.inventoryMenu.slots;
        for (int i = slots.size() - 1; i >= 0; i--){
            if (slots.get(i).getItem().is(SmacksUtil.BACKPACK_ITEM)){
                backpack = slots.get(i).getItem();
                break;
            }
        }
        return new BackpackMenu(SmacksUtil.BACKPACK_MENU, syncId, playerInventory, new BackpackInventory(backpack, playerInventory.player.registryAccess()));
    }
}
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

import static net.smackplays.smacksutil.Constants.C_BACKPACK_COL_NUM;
import static net.smackplays.smacksutil.Constants.C_BACKPACK_ROW_NUM;

/**
 * Class BackpackMenu */
public class BackpackMenu extends AbstractBackpackMenu {
    /** Constructor
     * @param menuType menuType
     * @param syncId syncId
     * @param playerInv playerInv
     * @param inv inv*/
    public BackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv);
    }

    /** Create generic Menu
     * @param syncId syncId
     * @param playerInventory playerInventory
     * @param vec vec
     * @return BackpackMenu*/
    public static BackpackMenu createGeneric9x6(int syncId, Inventory playerInventory, Vector3f vec) {
        vec.floor();
        ItemStack backpack = playerInventory.getSelectedItem();

        NonNullList<Slot> slots = playerInventory.player.inventoryMenu.slots;
        for (int i = slots.size() - 1; i >= 0; i--){
            if (slots.get(i).getItem().is(SmacksUtil.BACKPACK_ITEM)){
                backpack = slots.get(i).getItem();
                break;
            }
        }
        return new BackpackMenu(SmacksUtil.BACKPACK_MENU, syncId, playerInventory,
                new BackpackInventory(backpack, playerInventory.player.registryAccess(), C_BACKPACK_ROW_NUM * C_BACKPACK_COL_NUM + 4));
    }

}
package net.smackplays.smacksutil.menus;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import org.jetbrains.annotations.Nullable;

import static net.smackplays.smacksutil.Constants.C_BACKPACK_COL_NUM;
import static net.smackplays.smacksutil.Constants.C_BACKPACK_ROW_NUM;
import static net.smackplays.smacksutil.SmacksUtil.BACKPACK_ITEM;
import static net.smackplays.smacksutil.SmacksUtil.BACKPACK_MENU;

public class BackpackMenu extends AbstractBackpackMenu {

    public BackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv);
    }

    public static BackpackMenu createGeneric9x6(int syncId, Inventory playerInventory) {
        ItemStack backpack = playerInventory.getSelectedItem();
        if (!backpack.is(BACKPACK_ITEM.get())){
//            if (Services.PLATFORM.isModLoaded("curios")){
//                List<SlotResult> slotResults = CuriosApi.getCuriosHelper().findCurios(playerInventory.player, "back");
//                if (!slotResults.isEmpty()){
//                    backpack = slotResults.getFirst().stack();
//                    return new BackpackMenu(SmacksUtil.BACKPACK_MENU.get(), syncId, playerInventory, new BackpackInventory(backpack,playerInventory.player.registryAccess()));
//                }
//            }
            for (int i = playerInventory.getContainerSize(); i >= 0; i--){
                if (playerInventory.getItem(i).is(BACKPACK_ITEM.get())){
                    backpack = playerInventory.getItem(i);
                    break;
                }
            }
        }
        return new BackpackMenu(BACKPACK_MENU.get(), syncId, playerInventory,
                new BackpackInventory(backpack, playerInventory.player.registryAccess(), C_BACKPACK_ROW_NUM * C_BACKPACK_COL_NUM + 4));
    }
}
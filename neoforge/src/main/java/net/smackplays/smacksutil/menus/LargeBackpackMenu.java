package net.smackplays.smacksutil.menus;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.BackpackInventory;
import net.smackplays.smacksutil.platform.Services;
import org.jetbrains.annotations.Nullable;


import static net.smackplays.smacksutil.Constants.Backpack.C_LARGE_BACKPACK_COL_NUM;
import static net.smackplays.smacksutil.Constants.Backpack.C_LARGE_BACKPACK_ROW_NUM;

public class LargeBackpackMenu extends AbstractLargeBackpackMenu {

    public LargeBackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv);
    }

    @SuppressWarnings("unused")
    public static LargeBackpackMenu createGeneric13x9(int syncId, Inventory playerInventory) {
        ItemStack backpack = playerInventory.getSelectedItem();
        if (!backpack.is(SmacksUtil.LARGE_BACKPACK_ITEM.get())){
            if (Services.PLATFORM.isModLoaded("curios")){
//                List<SlotResult> slotResults = CuriosApi.getCuriosHelper().findCurios(playerInventory.player, "back");
//                if (!slotResults.isEmpty()){
//                    backpack = slotResults.getFirst().stack();
//                    return new LargeBackpackMenu(SmacksUtil.LARGE_BACKPACK_MENU.get(), syncId, playerInventory, new LargeBackpackInventory(backpack,playerInventory.player.registryAccess()));
//                }
            }
            for (int i = playerInventory.getContainerSize(); i >= 0; i--){
                if (playerInventory.getItem(i).is(SmacksUtil.LARGE_BACKPACK_ITEM.get())){
                    backpack = playerInventory.getItem(i);
                    break;
                }
            }
        }
        return new LargeBackpackMenu(SmacksUtil.LARGE_BACKPACK_MENU.get(), syncId, playerInventory,
                new BackpackInventory(backpack,playerInventory.player.registryAccess(), C_LARGE_BACKPACK_ROW_NUM * C_LARGE_BACKPACK_COL_NUM + 4));
    }
}
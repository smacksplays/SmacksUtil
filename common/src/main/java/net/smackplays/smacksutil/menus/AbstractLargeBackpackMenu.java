package net.smackplays.smacksutil.menus;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.smackplays.smacksutil.slots.BackpackSlot;
import net.smackplays.smacksutil.slots.BackpackUpgradeSlot;
import net.smackplays.smacksutil.slots.InvSlot;
import org.jetbrains.annotations.Nullable;

import static net.smackplays.smacksutil.Constants.Backpack.C_LARGE_BACKPACK_COL_NUM;
import static net.smackplays.smacksutil.Constants.Backpack.C_LARGE_BACKPACK_ROW_NUM;

public abstract class AbstractLargeBackpackMenu extends AbstractBackpackMenuBase {

    public AbstractLargeBackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv, C_LARGE_BACKPACK_ROW_NUM, C_LARGE_BACKPACK_COL_NUM);
        checkContainerSize(inv, this.rows * this.cols + 4);
        this.inventory = inv;
        this.playerInventory = playerInv;
        inventory.startOpen(playerInventory.player);
        addSlots();
    }

    private void addSlots(){
        int i = (this.rows - 4) * 18;
        for (int j = 0; j < 4; ++j){
            this.addSlot(new BackpackUpgradeSlot(inventory, j, 199, 21 + j * 18 - 3 - 3 * 18));
        }
        for (int j = 0; j < this.rows; ++j) {
            for (int k = 0; k < this.cols; ++k) {
                this.addSlot(new BackpackSlot(inventory, 4 + k + j * this.cols, -2 + k * 18 - 18 * 2, 21 + j * 18 - 3 - 3 * 18));
            }
        }
        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new InvSlot(playerInventory, k + j * 9 + 9, - 2 + k * 18, 49 + j * 18 + i));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.addSlot(new InvSlot(playerInventory, j, - 2 + j * 18, 107 + i));
        }
    }
}
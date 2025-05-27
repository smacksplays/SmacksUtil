package net.smackplays.smacksutil.menus;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.smackplays.smacksutil.slots.BackpackSlot;
import net.smackplays.smacksutil.slots.BackpackUpgradeSlot;
import net.smackplays.smacksutil.slots.InvSlot;
import org.jetbrains.annotations.Nullable;

import static net.smackplays.smacksutil.Constants.Backpack.C_BACKPACK_COL_NUM;
import static net.smackplays.smacksutil.Constants.Backpack.C_BACKPACK_ROW_NUM;

/**
 * Abstract BackpackMenu class
 */
public class AbstractBackpackMenu extends AbstractBackpackMenuBase {

    /**
     * Constructor
     * @param menuType menuType
     * @param syncId syncId
     * @param playerInv playerInv
     * @param inv inv
     */
    public AbstractBackpackMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv, Container inv) {
        super(menuType, syncId, playerInv, inv, C_BACKPACK_ROW_NUM, C_BACKPACK_COL_NUM);
        checkContainerSize(inv, rows * cols + 4);
        this.inventory = inv;
        this.playerInventory = playerInv;
        inventory.startOpen(playerInventory.player);
        addSlots();
    }

    /**
     * Adding slots
     */
    private void addSlots(){
        int i = (this.rows - 4) * 18;
        for (int j = 0; j < 4; ++j){
            this.addSlot(new BackpackUpgradeSlot(inventory, j, 163, 18 + j * 18 - 9 * 3));
        }
        for (int j = 0; j < this.rows; ++j) {
            for (int k = 0; k < this.cols; ++k) {
                this.addSlot(new BackpackSlot(inventory, 4 + k + j * this.cols, -2 + k * 18, 18 + j * 18 - 9 * 3));
            }
        }
        for (int j = 0; j < 3; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new InvSlot(playerInventory, k + j * 9 + 9, -2 + k * 18, 76 + j * 18 + i));
            }
        }
        for (int j = 0; j < 9; ++j) {
            this.addSlot(new InvSlot(playerInventory, j, -2 + j * 18, 134 + i));
        }
    }
}

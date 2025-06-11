package net.smackplays.smacksutil.menus;


import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** abstract class AbstractTeleportationTabletMenu */
public abstract class AbstractTeleportationTabletMenu extends AbstractContainerMenu {
    /** playerInventory */
    public final Inventory playerInventory;

    /** Constructor
     * @param menuType menuType
     * @param syncId syncId
     * @param playerInv playerInv */
    protected AbstractTeleportationTabletMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv) {
        super(menuType, syncId);
        this.playerInventory = playerInv;
        playerInventory.startOpen(playerInventory.player);
    }

    /** Quick move stack
     * @param player player
     * @param index index
     * @return ItemStack */
    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player player, int index) {
        return ItemStack.EMPTY;
    }

    /** stillValid
     * @param player player
     * @return boolean */
    @Override
    public boolean stillValid(@NotNull Player player) {
        return playerInventory.stillValid(player);
    }
}

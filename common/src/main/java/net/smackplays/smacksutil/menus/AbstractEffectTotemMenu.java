package net.smackplays.smacksutil.menus;


import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** abstract class AbstractEffectTotemMenu */
public abstract class AbstractEffectTotemMenu extends AbstractContainerMenu {
    /** Inventory */
    public final Inventory playerInventory;

    /**
     * Constructor
     * @param menuType menuType
     * @param syncId syncId
     * @param playerInv playerInv
     */
    protected AbstractEffectTotemMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv) {
        super(menuType, syncId);
        this.playerInventory = playerInv;
        playerInventory.startOpen(playerInventory.player);
    }

    /**
     * Quick move stack
     * @param var1 var1
     * @param var2 var2
     * @return ItemStack
     */
    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player var1, int var2) {
        return ItemStack.EMPTY;
    }

    /**
     * Still valid
     * @param player player
     * @return true if valid
     */
    @Override
    public boolean stillValid(@NotNull Player player) {
        return playerInventory.stillValid(player);
    }
}

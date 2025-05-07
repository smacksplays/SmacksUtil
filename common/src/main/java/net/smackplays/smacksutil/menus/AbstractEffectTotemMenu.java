package net.smackplays.smacksutil.menus;


import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractEffectTotemMenu extends AbstractContainerMenu {
    public final Inventory playerInventory;
    protected AbstractEffectTotemMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv) {
        super(menuType, syncId);
        this.playerInventory = playerInv;
        playerInventory.startOpen(playerInventory.player);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(@NotNull Player var1, int var2) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(@NotNull Player player) {
        return playerInventory.stillValid(player);
    }
}

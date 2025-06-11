package net.smackplays.smacksutil.menus;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

import static net.smackplays.smacksutil.SmacksUtil.TELEPORTATION_TABLET_MENU;

public class EffectTotemMenu extends AbstractEffectTotemMenu{
    public EffectTotemMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv) {
        super(menuType, syncId, playerInv);
    }

    public static EffectTotemMenu create(int syncId, Inventory playerInventory) {
        return new EffectTotemMenu(TELEPORTATION_TABLET_MENU.get(), syncId, playerInventory);
    }
}

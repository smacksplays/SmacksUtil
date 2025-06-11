package net.smackplays.smacksutil.menus;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;

import static net.smackplays.smacksutil.SmacksUtil.TELEPORTATION_TABLET_MENU;

public class TeleportationTabletMenu extends AbstractTeleportationTabletMenu {
    public TeleportationTabletMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv) {
        super(menuType, syncId, playerInv);
    }

    public static TeleportationTabletMenu create(int syncId, Inventory playerInventory) {
        return new TeleportationTabletMenu(TELEPORTATION_TABLET_MENU.get(), syncId, playerInventory);
    }
}

package net.smackplays.smacksutil.menus;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.smackplays.smacksutil.SmacksUtil;
import org.jetbrains.annotations.Nullable;

public class EffectTotemMenu extends AbstractEffectTotemMenu{
    public EffectTotemMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv) {
        super(menuType, syncId, playerInv);
    }

    public static EffectTotemMenu create(int syncId, Inventory playerInventory) {
        return new EffectTotemMenu(SmacksUtil.TELEPORTATION_TABLET_MENU.get(), syncId, playerInventory);
    }
}

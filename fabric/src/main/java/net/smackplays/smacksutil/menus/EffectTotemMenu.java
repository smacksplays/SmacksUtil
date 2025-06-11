package net.smackplays.smacksutil.menus;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import static net.smackplays.smacksutil.SmacksUtil.EFFECT_TOTEM_MENU;

public class EffectTotemMenu extends AbstractEffectTotemMenu{
    public EffectTotemMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv) {
        super(menuType, syncId, playerInv);
    }

    public static EffectTotemMenu create(int syncId, Inventory playerInventory, Vector3f vec) {
        vec.floor();
        return new EffectTotemMenu(EFFECT_TOTEM_MENU, syncId, playerInventory);
    }
}

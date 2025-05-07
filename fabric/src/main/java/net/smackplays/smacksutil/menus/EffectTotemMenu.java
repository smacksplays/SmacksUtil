package net.smackplays.smacksutil.menus;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.MenuType;
import net.smackplays.smacksutil.SmacksUtil;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class EffectTotemMenu extends AbstractEffectTotemMenu{
    public EffectTotemMenu(@Nullable MenuType<?> menuType, int syncId, Inventory playerInv) {
        super(menuType, syncId, playerInv);
    }

    @SuppressWarnings("unused")
    public static EffectTotemMenu create(int syncId, Inventory playerInventory, Vector3f vec) {
        return new EffectTotemMenu(SmacksUtil.EFFECT_TOTEM_MENU, syncId, playerInventory);
    }
}

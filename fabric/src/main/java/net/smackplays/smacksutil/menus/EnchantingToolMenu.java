package net.smackplays.smacksutil.menus;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.smackplays.smacksutil.SmacksUtil;
import net.smackplays.smacksutil.inventories.EnchantmentToolInventory;
import org.joml.Vector3f;

public class EnchantingToolMenu extends AbstractEnchantingToolMenu {

    public EnchantingToolMenu(int syncId, Inventory playerInv, Container inv) {
        super(SmacksUtil.ENCHANTING_TOOL_MENU, syncId, playerInv, inv);
    }

    @SuppressWarnings("unused")
    public static EnchantingToolMenu create(int syncId, Inventory playerInventory, Vector3f vec) {
        return new EnchantingToolMenu(syncId, playerInventory, new EnchantmentToolInventory(playerInventory.getSelected(), playerInventory.player.registryAccess()));
    }
}